package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAImageBlockRequest {
    byte fieldControl;
    OTAFileID fileID;
    int fileOffset;
    byte maxDataSize;
    byte[] nodeAddr;
    int blockReqDelay;

    public OTAImageBlockRequest(byte[] payload)
    {
        this.fieldControl = ByteBuffer.wrap(payload,0,1).get();
        this.fileID = new OTAFileID(Arrays.copyOfRange(payload,1,9));
        this.fileOffset = ByteBuffer.wrap(payload,9,4).getInt();
        this.maxDataSize = ByteBuffer.wrap(payload,13,1).get();
        this.nodeAddr = Arrays.copyOfRange(payload, 14, 22);
        this.blockReqDelay = ByteBuffer.wrap(payload,22,2).getInt();
    }

}
