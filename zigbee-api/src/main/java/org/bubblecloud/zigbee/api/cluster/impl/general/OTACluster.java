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

package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;
import org.bubblecloud.zigbee.network.ClusterFilter;
import org.bubblecloud.zigbee.network.ClusterListener;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;


public class OTACluster extends ZCLClusterBase implements OTA{



	private static AttributeImpl description;
	private final Attribute[] attributes;

	private class OTAListenerNotifier implements ClusterListener {

		public void handleCluster(ZigBeeEndpoint endpoint, ClusterMessage c) {
			try {
				ResponseImpl response = new ResponseImpl(c, ID);
				byte commandID = response.getHeaderCommandId();
				switch(commandID){
					case COMMAND_QUERY_NEXT_IMAGE_REQ:
						this.processQueryNextImageReq(response);
						break;
				}
			} catch (ZigBeeClusterException e) {
				e.printStackTrace();
			}
		}

		private Status processQueryNextImageReq(ResponseImpl response) throws ZigBeeClusterException {
			int payloadLength = response.getPayload().length;
			if (payloadLength <= OTA.PAYLOAD_MAX_LEN_QUERY_NEXT_IMAGE_REQ
					&& payloadLength >= PAYLOAD_MIN_LEN_QUERY_NEXT_IMAGE_REQ)
				return Status.MALFORMED_COMMAND;

			//parse the request
			OTAQueryImageRequest queryImageRequest = new OTAQueryImageRequest(response.getPayload());

			//send the response
			//TODO: send actual values
			invoke(new OTACommandImpl(new OTAQueryImageResponse(OTA.NO_IMAGE,queryImageRequest.fileID, 0)));

			return Status.SUCCESS;
		}

		public ClusterFilter getClusterFilter() {
			return OTAClusterFilter.FILTER;
		}

		public void setClusterFilter(ClusterFilter filter) {
		}
	}

	public OTACluster(ZigBeeEndpoint zbDevice){
		super(zbDevice);
		
		description = new AttributeImpl(zbDevice,this, Attributes.DESCRIPTION);
		attributes = new AttributeImpl[]{description};
	}

	public void sendImageNotify()
	{

	}

	@Override
	public short getId() {
		return OTA.ID;
	}

	@Override
	public String getName() {
		return OTA.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {
		return attributes;
	}


	public Attribute getAttributeDescription() {
		return description;
	}

	

}
