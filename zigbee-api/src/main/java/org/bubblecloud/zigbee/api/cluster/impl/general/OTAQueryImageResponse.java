package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAQueryImageResponse {
    byte status;
    OTAFileID fileID;
    int imageSize;

    public OTAQueryImageResponse(byte status, OTAFileID fileID, int imageSize)
    {
        this.status = status ;
        this.fileID = fileID;
        this.imageSize = imageSize;
    }

    public byte[] getPayload()
    {
        byte[] imageSize = ByteBuffer.allocate(2).putInt(this.imageSize).array();
        return ArrayUtils.addAll(ArrayUtils.addAll(new byte[] { this.status }, this.fileID.getPayload()), imageSize);
    }
}
