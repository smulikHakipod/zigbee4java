package org.bubblecloud.zigbee.web.api.commands;

import org.bubblecloud.zigbee.ZigBeeApi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by yaronshani on 4/6/15.
 */
@Path("/Devices")
public class Devices
{
    @Inject ZigBeeApi zigBeeApi;

    @GET
    @Path("List")
    @Produces(MediaType.APPLICATION_JSON)
    public Object[] list()
    {
        return zigBeeApi.getDevices().toArray();
    }
}