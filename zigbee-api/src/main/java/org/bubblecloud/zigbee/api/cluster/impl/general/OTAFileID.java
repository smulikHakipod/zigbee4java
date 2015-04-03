package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAFileID {
    public int manufacturer;
    public int type;
    public int version;

    public OTAFileID(int manufacturer, int type, int version)
    {
        this.manufacturer = manufacturer;
        this.type = type;
        this.version = version;
    }

    public OTAFileID(byte[] payload)
    {
        this.manufacturer = ByteBuffer.wrap(payload, 0, 2).getInt();
        this.type = ByteBuffer.wrap(payload, 2, 2).getInt();
        this.version = ByteBuffer.wrap(payload, 4, 4).getInt();
    }

    public byte[] getPayload()
    {
        byte[] manufacturer, type, version;
        manufacturer = ByteBuffer.allocate(2).putInt(this.manufacturer).array();
        type = ByteBuffer.allocate(2).putInt(this.type).array();
        version = ByteBuffer.allocate(4).putInt(this.version).array();
        return ArrayUtils.addAll(ArrayUtils.addAll(manufacturer, type), version);
    }
}
