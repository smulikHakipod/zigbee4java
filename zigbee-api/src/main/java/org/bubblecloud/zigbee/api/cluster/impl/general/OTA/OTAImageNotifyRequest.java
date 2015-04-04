package org.bubblecloud.zigbee.api.cluster.impl.general.OTA;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAImageNotifyRequest extends OTACommand {

    static byte id = OTA.COMMAND_IMAGE_NOTIFY;
    @OTAFieldType(type = ZigBeeType.Data8bit, index = 0)
    public Object payloadType;
    @OTAFieldType(type = ZigBeeType.Data8bit, index = 1)
    public Object queryJitter;
    @OTAFieldType(index = 2)
    public OTAFileID fileID;


    public OTAImageNotifyRequest(Object payloadType, Object queryJitter, OTAFileID fileID) {
        this.payloadType = payloadType;
        this.queryJitter = queryJitter;
        this.fileID = fileID;
    }

    public OTAImageNotifyRequest(byte[] payload) {
        super(payload);
    }
}
