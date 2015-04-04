package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;

import java.nio.ByteBuffer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAUpgradeEndResponse extends OTACommand {

    static byte id = OTA.COMMAND_UPGRADE_END_RSP;

    @OTAFieldType(index = 0)
    OTAFileID fileID;

    @OTAFieldType(type = ZigBeeType.Data32bit, index = 1)
    Object currentTime;

    @OTAFieldType(type = ZigBeeType.Data32bit, index = 1)
    Object upgradeTime;

    public OTAUpgradeEndResponse(OTAFileID fileID, Object currentTime, Object upgradeTime) {
        this.fileID = fileID;
        this.currentTime = currentTime;
        this.upgradeTime = upgradeTime;
    }

}
