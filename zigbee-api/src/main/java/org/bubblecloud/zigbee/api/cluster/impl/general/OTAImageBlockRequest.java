package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAImageBlockRequest extends OTACommand {

    @OTAFieldType(type = ZigBeeType.Data8bit, index = 0)
    Object fieldControl;

    @OTAFieldType(index = 1)
    OTAFileID fileID;

    @OTAFieldType(type= ZigBeeType.Data32bit, index=2)
    Object fileOffset;

    @OTAFieldType(type= ZigBeeType.Data8bit, index=3)
    Object maxDataSize;

    @OTAFieldType(type= ZigBeeType.IEEEAddress, index=4)
    Object[] nodeAddr;

    @OTAFieldType(type= ZigBeeType.Data16bit, index=5)
    Object blockReqDelay;


    public OTAImageBlockRequest(Object fieldControl, OTAFileID fileID, Object fileOffset, Object maxDataSize, Object[] nodeAddr, Object blockReqDelay) {
        this.fieldControl = fieldControl;
        this.fileID = fileID;
        this.fileOffset = fileOffset;
        this.maxDataSize = maxDataSize;
        this.nodeAddr = nodeAddr;
        this.blockReqDelay = blockReqDelay;
    }

    public OTAImageBlockRequest(byte[] payload)
    {
        super(payload);
    }

}
