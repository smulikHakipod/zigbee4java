package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAQueryImageResponse {
    OTAFileID fileID;
    byte[] imageSize;

    public OTAQueryImageResponse(OTAFileID fileID, int imageSize)
    {
        this.fileID = fileID;
        this.imageSize = ByteBuffer.allocate(4).putInt(imageSize).array();
    }

    public byte[] getPayload()
    {
        return ArrayUtils.addAll(this.fileID.getPayload(), this.imageSize);
    }
}
