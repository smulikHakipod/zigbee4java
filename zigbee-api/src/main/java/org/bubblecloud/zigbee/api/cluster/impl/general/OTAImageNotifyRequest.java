package org.bubblecloud.zigbee.api.cluster.impl.general;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.util.ArraysUtil;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAImageNotifyRequest implements Command {
    byte payloadType;
    byte queryJitter;
    OTAFileID fileID;

    public OTAImageNotifyRequest(byte payloadType, byte queryJitter, OTAFileID fileID) {
        this.payloadType = payloadType;
        this.queryJitter = queryJitter;
        this.fileID = fileID;
    }

    public byte[] getAllowedResponseId() {
        return new byte[0];
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

    public byte[] getPayload() {
        return ArrayUtils.addAll(new byte[] { this.payloadType, this.queryJitter }, fileID.getPayload());
    }
}
