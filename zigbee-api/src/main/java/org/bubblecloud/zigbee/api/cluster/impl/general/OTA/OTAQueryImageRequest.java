package org.bubblecloud.zigbee.api.cluster.impl.general.OTA;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAQueryImageRequest extends OTACommand {


    @OTAFieldType(type=ZigBeeType.Data8bit, index=0)
    public Object fieldControl;

    @OTAFieldType(index=1)
    public OTAFileID fileID;

    @OTAFieldType(type=ZigBeeType.Data16bit, index=2)
    public Object hardwareVersion;


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
