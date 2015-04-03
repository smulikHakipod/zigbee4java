package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;

import java.nio.ByteBuffer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAUpgradeEndResponse implements Command {
    OTAFileID fileID;
    int currentTime;
    int upgradeTime;

    public OTAUpgradeEndResponse(OTAFileID fileID, int currentTime, int upgradeTime) {
        this.fileID = fileID;
        this.currentTime = currentTime;
        this.upgradeTime = upgradeTime;
    }


    public byte getHeaderCommandId() {
        return 0;
    }

    public boolean isClusterSpecific() {
        return false;
    }

    public boolean isManufacturerExtension() {
        return false;
    }

    public boolean isClientServerDirection() {
        return false;
    }

    public byte[] getManufacturerId() {
        return new byte[0];
    }

    public byte[] getPayload()
    {
        byte[] currentTime = ByteBuffer.allocate(4).putInt(this.currentTime).array();
        byte[] upgradeTime = ByteBuffer.allocate(4).putInt(this.upgradeTime).array();
        //need to find a better way to do this
        return ArrayUtils.addAll(ArrayUtils.addAll(this.fileID.getPayload(), currentTime), upgradeTime);
    }

    public byte[] getAllowedResponseId() {
        return new byte[0];
    }
}
