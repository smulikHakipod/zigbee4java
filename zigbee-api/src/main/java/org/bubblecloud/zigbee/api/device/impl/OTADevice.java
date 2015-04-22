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

package org.bubblecloud.zigbee.api.device.impl;

import org.bubblecloud.zigbee.ZigBeeApiContext;
import org.bubblecloud.zigbee.api.*;
import org.bubblecloud.zigbee.api.cluster.general.DoorLock;
import org.bubblecloud.zigbee.api.cluster.general.Groups;
import org.bubblecloud.zigbee.api.cluster.general.Scenes;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;
import org.bubblecloud.zigbee.api.cluster.impl.general.OTA.OTAClusterFilter;
import org.bubblecloud.zigbee.api.cluster.impl.general.OTACluster;
import org.bubblecloud.zigbee.api.device.generic.IDoorLock;
import org.bubblecloud.zigbee.network.ClusterFilter;
import org.bubblecloud.zigbee.network.ClusterListener;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;

public class OTADevice extends DeviceBase implements IDoorLock {

    public OTA ota;
    private Scenes scenes;
    private Groups groups;
    //private OccupancySensing occupancySensing;

    public class OTAListenerNotifier implements ClusterListener {

        public void setClusterFilter(ClusterFilter filter) {

        }

        public ClusterFilter getClusterFilter() {
            return OTAClusterFilter.FILTER;
        }

        public void handleCluster(ZigBeeEndpoint endpoint, ClusterMessage clusterMessage) {
            int i = 0;
        }
    }

    public OTADevice(ZigBeeApiContext ctx, ZigBeeEndpoint zbDevice) throws ZigBeeDeviceException {
        super(ctx, zbDevice);
        //onOff = (OnOff) getCluster(ZigBeeApiConstants.CLUSTER_ID_ON_OFF);
        ota = (OTA) new OTACluster(getEndpoint());
        //getEndpoint().invoke(new Cl)
        //occupancySensing = (OccupancySensing) getCluster(ZigBeeApiConstants.CLUSTER_ID_OCCUPANCY_SENSING);
    }

    final static DeviceDescription DEVICE_DESCRIPTOR = new AbstractDeviceDescription() {

        public int[] getCustomClusters() {
            return org.bubblecloud.zigbee.api.device.generic.OTA.CUSTOM;
        }

        public int[] getMandatoryCluster() {
            return org.bubblecloud.zigbee.api.device.generic.OTA.MANDATORY;
        }

        public int[] getOptionalCluster() {
            return org.bubblecloud.zigbee.api.device.generic.OTA.OPTIONAL;
        }

        public int[] getStandardClusters() {
            return org.bubblecloud.zigbee.api.device.generic.OTA.STANDARD;
        }

    };


    public DeviceDescription getDescription() {
        return DEVICE_DESCRIPTOR;
    }

    @Override
    public String getDeviceType() {
        return org.bubblecloud.zigbee.api.device.generic.OTA.NAME;
    }

    public Groups getGroups() {
        return groups;
    }


    public Scenes getScenes() {
        return scenes;
    }

}
