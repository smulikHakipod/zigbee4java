package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;

import java.nio.ByteBuffer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAQueryImageResponse extends OTACommand {

    static byte id = OTA.COMMAND_QUERY_NEXT_IMAGE_RSP;

    @OTAFieldType(type = ZigBeeType.Data8bit, index = 0)
    Object status;

    @OTAFieldType(index = 1)
    OTAFileID fileID;

    @OTAFieldType(type = ZigBeeType.Data16bit, index = 2)
    Object imageSize;


    public OTAQueryImageResponse(Object status, OTAFileID fileID, Object imageSize) {
        this.status = status;
        this.fileID = fileID;
        this.imageSize = imageSize;
    }

    public OTAQueryImageResponse(byte[] payload) {
        super(payload);
    }
}
