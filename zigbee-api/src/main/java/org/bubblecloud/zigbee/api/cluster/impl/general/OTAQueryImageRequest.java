package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.apache.commons.lang.ArrayUtils;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAQueryImageRequest extends OTACommand {


    @OTAFieldType(type=ZigBeeType.Data8bit, index=0)
    Object fieldControl;

    @OTAFieldType(index=1)
    OTAFileID fileID;

    @OTAFieldType(type=ZigBeeType.Data16bit, index=2)
    Object hardwareVersion;


    public OTAQueryImageRequest(Object fieldControl, OTAFileID fileID, Object hardwareVersion) {
        this.fieldControl = fieldControl;
        this.fileID = fileID;
        this.hardwareVersion = hardwareVersion;
    }

    public OTAQueryImageRequest(byte[] payload)
    {
        super(payload);
    }
}
