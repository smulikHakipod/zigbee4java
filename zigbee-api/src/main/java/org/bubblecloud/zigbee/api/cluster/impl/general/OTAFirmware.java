package org.bubblecloud.zigbee.api.cluster.impl.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by yaronshani on 4/3/15.
 */
public class OTAFirmware {
    public int hardwareVersion;
    public int manufacturer;
    public int type;
    public int version;
    public String path;
    public byte[] firmwareContent;

    public OTAFirmware(int hardwareVersion, int manufacturer, int type, int version, String path)
    {
        this.hardwareVersion = hardwareVersion;
        this.manufacturer = manufacturer;
        this.type = type;
        this.version = version;
        this.path = path;

        try {
            this.firmwareContent = Files.readAllBytes(Paths.get(this.path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
