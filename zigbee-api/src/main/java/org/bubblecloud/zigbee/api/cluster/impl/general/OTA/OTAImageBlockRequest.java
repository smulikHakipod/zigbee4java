package org.bubblecloud.zigbee.api.cluster.impl.general.OTA;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAImageBlockRequest extends OTACommand {

    @OTAFieldType(type = ZigBeeType.Data8bit, index = 0)
    public Object fieldControl;

    @OTAFieldType(index = 1)
    public OTAFileID fileID;

    @OTAFieldType(type= ZigBeeType.Data32bit, index=2)
    public Object fileOffset;

    @OTAFieldType(type= ZigBeeType.UnsignedInteger8bit, index=3)
    public Object maxDataSize;

    //@OTAFieldType(type= ZigBeeType.Data8bit, index=4)
    //public Object nodeAddr;

    @OTAFieldType(type= ZigBeeType.Data16bit, index=5)
    public Object blockReqDelay;


    public OTAImageBlockRequest(Object fieldControl, OTAFileID fileID, Object fileOffset, Object maxDataSize, Object blockReqDelay) {
        this.fieldControl = fieldControl;
        this.fileID = fileID;
        this.fileOffset = fileOffset;
        this.maxDataSize = maxDataSize;
        //this.nodeAddr = nodeAddr;
        this.blockReqDelay = blockReqDelay;
    }

    public OTAImageBlockRequest(byte[] payload)
    {
        super(payload);
    }

}
