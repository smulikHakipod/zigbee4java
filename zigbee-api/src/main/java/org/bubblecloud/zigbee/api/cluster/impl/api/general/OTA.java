/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.bubblecloud.zigbee.api.cluster.impl.api.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZCLCluster;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.general.OTAFirmware;


/**
 * This class represent the <b>Door Lock</b> Cluster as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 18:00:05 +0200 (mar, 06 ago 2013) $)
 * @since 0.8.0
 */

public interface OTA extends ZCLCluster {

   public static final short  ID = 0x19;
   static final String NAME = "OTA";
   static final String DESCRIPTION = "Over the air software update.";

   static final byte COMMAND_QUERY_NEXT_IMAGE_REQ = 0x1;
   static final byte COMMAND_QUERY_NEXT_IMAGE_RSP = 0x2;
   static final byte COMMAND_IMAGE_BLOCK_REQ = 0x3;
   static final byte COMMAND_IMAGE_BLOCK_RSP = 0x5;
   static final byte COMMAND_UPGRADE_END_REQ = 0x6;
   static final byte COMMAND_UPGRADE_END_RSP = 0x7;
   static final byte NO_IMAGE = (byte) 0x98;



   static final byte PAYLOAD_MAX_LEN_IMAGE_BLOCK_REQ = 24;
   static final byte PAYLOAD_MIN_LEN_IMAGE_BLOCK_REQ = 14;

   static final byte PAYLOAD_MAX_LEN_QUERY_NEXT_IMAGE_REQ = 11;
   static final byte PAYLOAD_MIN_LEN_QUERY_NEXT_IMAGE_REQ = 9;

   static final byte PAYLOAD_MAX_LEN_UPGRADE_END_REQ = 9;
   static final byte PAYLOAD_MIN_LEN_UPGRADE_END_REQ = 1;

   public void sendImageNotify(OTAFirmware firmware);
	
   public Attribute getAttributeDescription();
}
