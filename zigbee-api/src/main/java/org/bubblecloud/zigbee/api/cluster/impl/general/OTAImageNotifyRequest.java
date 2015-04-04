package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;
import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAImageNotifyRequest extends OTACommand {

    static byte id = OTA.COMMAND_IMAGE_NOTIFY;
    @OTAFieldType(type = ZigBeeType.Data8bit, index = 0)
    Object payloadType;
    @OTAFieldType(type = ZigBeeType.Data8bit, index = 1)
    Object queryJitter;
    @OTAFieldType(index = 2)
    OTAFileID fileID;


    public OTAImageNotifyRequest(Object payloadType, Object queryJitter, OTAFileID fileID) {
        this.payloadType = payloadType;
        this.queryJitter = queryJitter;
        this.fileID = fileID;
    }

    public OTAImageNotifyRequest(byte[] payload) {
        super(payload);
    }
}
