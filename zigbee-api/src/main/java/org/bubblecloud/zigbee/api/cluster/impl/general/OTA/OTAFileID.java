package org.bubblecloud.zigbee.api.cluster.impl.general.OTA;

import org.apache.commons.lang.ArrayUtils;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.device.generic.OTA;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAFileID extends OTACommand{

    @OTAFieldType(type= ZigBeeType.Data16bit, index=0)
    public Object manufacturer;
    @OTAFieldType(type= ZigBeeType.Data16bit, index=1)
    public Object type;
    @OTAFieldType(type= ZigBeeType.Data32bit, index=2)
    public Object version;

    public OTAFileID(int manufacturer, int type, int version) {
        this.manufacturer = manufacturer;
        this.type = type;
        this.version = version;
    }

    public OTAFileID(byte[] payload)
    {
        super(payload);
    }

    public OTAFileID()
    {
        super();
    }
}
