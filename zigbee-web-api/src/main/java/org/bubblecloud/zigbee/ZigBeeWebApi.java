package org.bubblecloud.zigbee;

import org.apache.commons.io.FileUtils;
import org.bubblecloud.zigbee.ZigBeeApi;
import org.bubblecloud.zigbee.api.Device;
import org.bubblecloud.zigbee.api.DeviceListener;
import org.bubblecloud.zigbee.network.model.DiscoveryMode;
import org.bubblecloud.zigbee.network.port.ZigBeeSerialPortImpl;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by yaronshani on 4/4/15.
 */
public class ZigBeeWebApi extends ZigBeeHttpApi {

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

    public ZigBeeWebApi() {

        mainThread = Thread.currentThread();

        ZigBeeSerialPortImpl serialPort = new ZigBeeSerialPortImpl("/dev/cu.", DefaultBaudRate);
        ZigBeeApi zigbeeApi = new ZigBeeApi(serialPort, 4952, 11, true, DiscoveryMode.ALL);


        final File networkStateFile = new File("network.json");
        if (networkStateFile.exists()) {
            try {
                final String networkState = FileUtils.readFileToString(networkStateFile);
                zigbeeApi.deserializeNetworkState(networkState);
            } catch (final Exception e) {
                e.printStackTrace();
                return;
            }
        }

        if (!zigbeeApi.startup()) {
            print("ZigBee API starting up ... [FAIL]");
            return;
        } else {
            print("ZigBee API starting up ... [OK]");
        }

        if (!zigbeeApi.permitJoin(true)) {
            print("ZigBee API permit join enable ... [FAIL]");
            return;
        } else {
            print("ZigBee API permit join enable ... [OK]");
        }

        zigbeeApi.addDeviceListener(new DeviceListener() {
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

        while (!shutdown && !networkStateFile.exists() && !zigbeeApi.isInitialBrowsingComplete()) {
            print("Browsing network for the first time...");
            System.out.print('.');
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
            print("Browsing network for the first time... [OK]");
        }
        print("There are " + zigbeeApi.getDevices().size() + " known devices in the network.");
    }

    @Override
    void callApi(String apiName, Object params) {

    }
}
