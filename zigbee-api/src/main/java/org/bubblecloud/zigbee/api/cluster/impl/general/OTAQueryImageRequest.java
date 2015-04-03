package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAQueryImageRequest {
    byte fieldControl;
    OTAFileID fileID;
    int hardwareVersion;

    public OTAQueryImageRequest(byte[] payload)
    {
        this.fieldControl = ByteBuffer.wrap(payload,0,1).get();
        this.fileID = new OTAFileID(Arrays.copyOfRange(payload,2,10));
        this.hardwareVersion = ByteBuffer.wrap(payload,10,2).getInt();
    }

}
