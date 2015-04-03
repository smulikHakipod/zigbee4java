package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAUpgradeEndRequest{
    byte status;
    OTAFileID fileID;

    public OTAUpgradeEndRequest(byte[] payload)
    {
        this.status = ByteBuffer.wrap(payload,0,1).get();
        this.fileID = new OTAFileID(Arrays.copyOfRange(payload,1,9));
    }

}
