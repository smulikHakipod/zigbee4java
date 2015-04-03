package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;

import java.nio.ByteBuffer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAImageBlockResponse implements Command {
    byte id;
    byte status;
    OTAFileID fileID;
    int fileOffset;
    byte dataSize;
    byte[] data;

    public OTAImageBlockResponse(byte status, OTAFileID fileID, int fileOffset, byte dataSize, byte[] data)
    {
        this.id = OTA.COMMAND_IMAGE_BLOCK_RSP;
        this.status = status ;
        this.fileID = fileID;
        this.fileOffset = fileOffset;
        this.dataSize = dataSize;
        this.data = data;
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
        byte[] fileOffset = ByteBuffer.allocate(4).putInt(this.fileOffset).array();
        //need to find a better way to do this
        return ArrayUtils.addAll(
                ArrayUtils.addAll(
                        ArrayUtils.addAll(
                                ArrayUtils.addAll(
                                        new byte[]{this.status},
                                        this.fileID.getPayload()),
                                fileOffset),
                        new byte[] {this.dataSize}),
                this.data);
    }

    public byte[] getAllowedResponseId() {
        return new byte[0];
    }
}
