package org.bubblecloud.zigbee.api.cluster.impl.general.OTA;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZBDeserializer;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;
import org.bubblecloud.zigbee.api.cluster.impl.core.ByteArrayOutputStreamSerializer;
import org.bubblecloud.zigbee.api.cluster.impl.core.DefaultDeserializer;
import org.bubblecloud.zigbee.network.ClusterMessage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yaronshani on 4/3/15.
 */
public abstract class OTACommand implements Command {

    private List<Object[]> sortedProperties;

    public OTACommand()
    {
        this.sortedProperties = this.getAllSortedProperties();
    }

    public OTACommand(byte[] payload)
    {
        this();
        ZBDeserializer deserializer = new DefaultDeserializer(payload, 0);
        for (Object[] fieldTypePair : this.sortedProperties) {
            OTAFieldType otaFieldType = (OTAFieldType)fieldTypePair[0];
            Field field = (Field)fieldTypePair[1];
            if (otaFieldType.type() != ZigBeeType.NULL) {
                try {
                    field.set(this, deserializer.readZigBeeType(otaFieldType.type()));
                } catch (ArrayIndexOutOfBoundsException e) {
                    //field.set(this,null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Constructor constructor = field.getType().getDeclaredConstructor(new Class[]{});
                    OTACommand cmd = (OTACommand)constructor.newInstance();
                    int classLength = cmd.getClassByteLength();
                    byte[] classPayload = new byte[classLength];
                    for(int i=0;i<classLength;i++) {
                        classPayload[i] = deserializer.readByte();
                    }
                    Class[] constructArg = {
                            byte[].class
                    };
                    constructor = cmd.getClass().getDeclaredConstructor(constructArg);
                    field.set(this, constructor.newInstance(classPayload));

                } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                } catch (InstantiationException e) {
                        e.printStackTrace();
                } catch (InvocationTargetException e) {
                        e.printStackTrace();
                } catch (IllegalAccessException e) {
                        e.printStackTrace();
                }
            }
        }
    }

    public byte[] getAllowedResponseId() {
        return new byte[] { };
    }

    public byte getHeaderCommandId() {
        for(Field f : this.getClass().getDeclaredFields()) {
            if(f.getName() == "id") {
                try {
                    return f.getByte(this);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public boolean isClusterSpecific() {
        return true;
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

    private List<Object[]> getAllSortedProperties()
    {
        Class cl = this.getClass();
        List<Object[]> otaFieldTypeList = new ArrayList<Object[]>();
        //while(cl.getName() != "java.lang.Object") {
        for (Field f : cl.getDeclaredFields()) {
            if (f.getAnnotation(OTAFieldType.class) != null ) {
                otaFieldTypeList.add(new Object[]{f.getAnnotation(OTAFieldType.class), f});
            }
        }
            //cl = cl.getSuperclass();
        //}

        otaFieldTypeList.sort(new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                OTAFieldType o1FieldType = (OTAFieldType)o1[0];
                OTAFieldType o2FieldType = (OTAFieldType)o2[0];
                return o1FieldType.index() - o2FieldType.index();
            }
        });
        return otaFieldTypeList;
    }

    public int getClassByteLength()
    {
        int byteLength = 0;
        for (Object[] fieldTypePair : this.sortedProperties) {
            OTAFieldType otaFieldType = (OTAFieldType)fieldTypePair[0];
            Field field = (Field)fieldTypePair[1];
            if (otaFieldType.type() != null) {
                byteLength += otaFieldType.type().getLength();
            } else {
                try {
                    OTACommand cmd = (OTACommand)(field.get(this));
                    byteLength += cmd.getClassByteLength();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return byteLength;
    }

    public byte[] getPayload() {
        ByteArrayOutputStreamSerializer byteArrayOutputStreamSerializer = new ByteArrayOutputStreamSerializer();
        for (Object[] fieldTypePair : this.sortedProperties) {
            OTAFieldType otaFieldType = (OTAFieldType)fieldTypePair[0];
            Field field = (Field)fieldTypePair[1];
            if (otaFieldType.type() != ZigBeeType.NULL) {
                try {
                    byteArrayOutputStreamSerializer.appendZigBeeType(field.get(this), otaFieldType.type());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    byte[] localPayload;
                    if (field.get(this).getClass() == byte[].class) {
                        localPayload = (byte[])field.get(this);
                        for(int i=0;i<localPayload.length;i++) {
                            byteArrayOutputStreamSerializer.append_byte(localPayload[i]);
                        }
                    } else {

                        OTACommand cmd = (OTACommand)(field.get(this));
                        localPayload = cmd.getPayload();

                        for(int i=0;i<localPayload.length;i++) {
                            byteArrayOutputStreamSerializer.append_byte(localPayload[i]);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return byteArrayOutputStreamSerializer.getPayload();
    }
}
