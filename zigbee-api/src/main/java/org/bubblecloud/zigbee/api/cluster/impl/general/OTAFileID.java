package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAFileID {
    private byte[] manufacturer;
    private byte[] type;
    private byte[] version;

    public OTAFileID(int manufacturer, int type, int version)
    {
        this.manufacturer = ByteBuffer.allocate(2).putInt(manufacturer).array();
        this.type = ByteBuffer.allocate(2).putInt(type).array();
        this.version = ByteBuffer.allocate(4).putInt(version).array();
    }

    public byte[] getPayload()
    {
        return ArrayUtils.addAll(ArrayUtils.addAll(this.manufacturer, this.type),this.version);
    }
}
