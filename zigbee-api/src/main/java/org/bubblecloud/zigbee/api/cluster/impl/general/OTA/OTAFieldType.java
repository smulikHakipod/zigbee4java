package org.bubblecloud.zigbee.api.cluster.impl.general.OTA;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yaronshani on 4/4/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OTAFieldType {
    boolean serializable() default true;
    ZigBeeType type() default  ZigBeeType.NULL;
    int index();
    Class typeClass() default ZigBeeType.class;
}
