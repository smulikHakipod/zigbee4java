package org.bubblecloud.zigbee.web.api;

import org.apache.commons.io.FileUtils;
import org.bubblecloud.zigbee.ZigBeeApi;
import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.DeviceListener;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.network.port.ZigBeeSerialPortImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import java.io.File;
import java.io.IOException;

/**
 * Created by yaronshani on 4/4/15.
 */
public class ZigBeeWebApi {

    public ZigBeeApi getZigBeeApi() {
        return zigBeeApi;
    }

    private ZigBeeApi zigBeeApi;
    /**
     * The main thread.
     */
    private Thread mainThread = null;

    /**
     * The flag reflecting that shutdown is in process.
     */
    private boolean shutdown = false;

    private static final int DefaultBaudRate = 38400;

    private static void print(final String line) {
        System.out.print(line);
    }

    public static class ZigBeeFactory implements Factory<ZigBeeApi> {

        private static ZigBeeApi zigBeeApi;

        @Inject
        public ZigBeeFactory(ServletConfig servletConfig)
        {
            this.zigBeeApi = (ZigBeeApi)servletConfig.getServletContext().getAttribute("org.bubblecloud.zigbee.web.api.ZigBeeApiInstance");
        }

        @Override
        public ZigBeeApi provide() {
            return zigBeeApi;
        }

        @Override
        public void dispose(ZigBeeApi t) {
        }
    }

    public class WebApiConfig extends ResourceConfig {
        public WebApiConfig() {
            property("jersey.config.server.provider.packages", "org.bubblecloud.zigbee.web.api.commands");
            register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bindFactory(ZigBeeFactory.class).to(ZigBeeApi.class);
                }
            });
        }
    }

    public static void main(String [] args)
    {
        new ZigBeeWebApi();
    }

    public void initZigBee()
    {
        mainThread = Thread.currentThread();

        ZigBeeSerialPortImpl serialPort = new ZigBeeSerialPortImpl("/dev/cu.SLAB_USBtoUART", DefaultBaudRate);
        zigBeeApi = new ZigBeeApi(serialPort, 4952, 11, true, DiscoveryMode.ALL);


        final File networkStateFile = new File("network.json");
        if (networkStateFile.exists()) {
            try {
                final String networkState = FileUtils.readFileToString(networkStateFile);
                zigBeeApi.deserializeNetworkState(networkState);
            } catch (final Exception e) {
                e.printStackTrace();
                return;
            }
        }

        if (!zigBeeApi.startup()) {
            print("ZigBee API starting up ... [FAIL]");
            return;
        } else {
            print("ZigBee API starting up ... [OK]");
        }

        if (!zigBeeApi.permitJoin(true)) {
            print("ZigBee API permit join enable ... [FAIL]");
            return;
        } else {
            print("ZigBee API permit join enable ... [OK]");
        }

        zigBeeApi.addDeviceListener(new DeviceListener() {
            @Override
            public void deviceAdded(Device device) {
                print("Device added: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")");
            }

            @Override
            public void deviceUpdated(Device device) {
            }

            @Override
            public void deviceRemoved(Device device) {
                print("Device removed: " + device.getEndpointId() + " (#" + device.getNetworkAddress() + ")");
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shutdown = true;
                try {
                    System.in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mainThread.interrupt();
                    mainThread.join();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }));

        while (!shutdown && !networkStateFile.exists() && !zigBeeApi.isInitialBrowsingComplete()) {
            print("Browsing network for the first time...");
            System.out.print('.');
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
            print("Browsing network for the first time... [OK]");
        }
        print("There are " + zigBeeApi.getDevices().size() + " known devices in the network.");
    }

    public ZigBeeWebApi() {


        this.initZigBee();

        //init http server
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("");
        context.setAttribute("org.bubblecloud.zigbee.web.api.ZigBeeApiInstance", ZigBeeWebApi.this.zigBeeApi);
        Server jettyServer = new Server(8080);


        ServletHolder jerseyServlet = new ServletHolder(new ServletContainer(new WebApiConfig()));

        context.addServlet(jerseyServlet, "/*");
        //ServletHolder jerseyServlet =
        //        context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        //context.addEventListener(new ServletContextClass());

        //jerseyServlet.setInitOrder(1);


        // Tells the Jersey Servlet which REST service/class to load.
        //jerseyServlet.setInitParameter(
        //        "jersey.config.server.provider.packages",
        //        "org.bubblecloud.zigbee.web.api");
        jettyServer.setHandler(context);

        try {
            jettyServer.start();
            jettyServer.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jettyServer.destroy();
        }

    }
}
