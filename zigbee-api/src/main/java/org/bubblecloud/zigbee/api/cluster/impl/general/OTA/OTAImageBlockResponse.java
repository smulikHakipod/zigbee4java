package org.bubblecloud.zigbee.api.cluster.impl.general.OTA;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAImageBlockResponse extends OTACommand {

    static byte id = OTA.COMMAND_IMAGE_BLOCK_RSP;

    @OTAFieldType(type = ZigBeeType.Data8bit, index = 0)
    public Object status;

    @OTAFieldType(index = 1)
    public OTAFileID fileID;

    @OTAFieldType(type = ZigBeeType.Data32bit, index = 2)
    public Object fileOffset;

    @OTAFieldType(type = ZigBeeType.Data8bit, index = 3)
    public Object dataSize;

    @OTAFieldType(index = 4)
    public byte[] data;

    public OTAImageBlockResponse(Object status, OTAFileID fileID, Object fileOffset, Object dataSize, byte[] data) {
        this.status = status;
        this.fileID = fileID;
        this.fileOffset = fileOffset;
        this.dataSize = dataSize;
        this.data = data;
    }
}
