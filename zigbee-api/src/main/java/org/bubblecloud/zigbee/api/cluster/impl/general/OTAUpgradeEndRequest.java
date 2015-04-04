package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAUpgradeEndRequest extends OTACommand{

    @OTAFieldType(type = ZigBeeType.Data8bit, index = 0)
    Object status;

    @OTAFieldType(index = 1)
    OTAFileID fileID;

    public OTAUpgradeEndRequest(Object status, OTAFileID fileID) {
        this.status = status;
        this.fileID = fileID;
    }

    public OTAUpgradeEndRequest(byte[] payload) {
        super(payload);
    }
}
