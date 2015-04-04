package org.bubblecloud.zigbee.api.cluster.impl.general.OTA;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAUpgradeEndRequest extends OTACommand{

    @OTAFieldType(type = ZigBeeType.Data8bit, index = 0)
    public Object status;

    @OTAFieldType(index = 1)
    public OTAFileID fileID;

    public OTAUpgradeEndRequest(Object status, OTAFileID fileID) {
        this.status = status;
        this.fileID = fileID;
    }

    public OTAUpgradeEndRequest(byte[] payload) {
        super(payload);
    }
}
