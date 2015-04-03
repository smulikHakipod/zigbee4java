package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;

import java.nio.ByteBuffer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAQueryImageResponse implements Command {
    byte id;
    byte status;
    OTAFileID fileID;
    int imageSize;

    public OTAQueryImageResponse(byte status, OTAFileID fileID, int imageSize)
    {
        this.id = OTA.COMMAND_QUERY_NEXT_IMAGE_RSP;
        this.status = status ;
        this.fileID = fileID;
        this.imageSize = imageSize;
    }

    public byte getHeaderCommandId() {
        return 0;
    }

    public boolean isClusterSpecific() {
        return false;
    }

    public boolean isManufacturerExtension() {
        return false;
    }

    public boolean isClientServerDirection() {
        return false;
    }

    public byte[] getManufacturerId() {
        return new byte[0];
    }

    public byte[] getPayload()
    {
        byte[] imageSize = ByteBuffer.allocate(2).putInt(this.imageSize).array();
        return ArrayUtils.addAll(ArrayUtils.addAll(new byte[] { this.status }, this.fileID.getPayload()), imageSize);
    }

    public byte[] getAllowedResponseId() {
        return new byte[0];
    }
}