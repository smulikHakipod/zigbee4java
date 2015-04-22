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

import org.bubblecloud.zigbee.api.cluster.impl.ClusterMessageImpl;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Attribute;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.Status;
import org.bubblecloud.zigbee.api.cluster.impl.api.core.ZigBeeClusterException;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;
import org.bubblecloud.zigbee.api.cluster.impl.attribute.Attributes;
import org.bubblecloud.zigbee.api.cluster.impl.core.AttributeImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ResponseImpl;
import org.bubblecloud.zigbee.api.cluster.impl.core.ZCLClusterBase;
import org.bubblecloud.zigbee.api.cluster.impl.general.OTA.*;
import org.bubblecloud.zigbee.network.ClusterFilter;
import org.bubblecloud.zigbee.network.ClusterListener;
import org.bubblecloud.zigbee.network.ClusterMessage;
import org.bubblecloud.zigbee.network.ZigBeeEndpoint;
import org.bubblecloud.zigbee.network.impl.ZigBeeNetworkManagerException;
import org.bubblecloud.zigbee.util.Threaded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;



public class OTACluster extends ZCLClusterBase implements OTA{

	private static AttributeImpl description;
	private final Attribute[] attributes;
	private OTAListenerNotifier bridge;
	private List<OTAFirmware> firmwareList = new ArrayList<OTAFirmware>();

	public class OTAListenerNotifier implements ClusterListener {

		public void handleCluster(ZigBeeEndpoint endpoint, ClusterMessage c) {
			try {
				ResponseImpl response = new ResponseImpl(c, ID);
				byte commandID = response.getHeaderCommandId();

				//TODO: implement page request
				switch(commandID){
					case COMMAND_QUERY_NEXT_IMAGE_REQ:
						this.processQueryNextImageReq(response);
						break;
					case COMMAND_IMAGE_BLOCK_REQ:
						this.processImageBlockReq(response);
						break;
					case COMMAND_UPGRADE_END_REQ:
						this.processUpgradeEndReq(response);
						break;
				}
			} catch (ZigBeeClusterException e) {
				e.printStackTrace();
			}
		}

		private Status processQueryNextImageReq(ResponseImpl response) throws ZigBeeClusterException {
			int payloadLength = response.getPayload().length;
			if (payloadLength < OTA.PAYLOAD_MAX_LEN_QUERY_NEXT_IMAGE_REQ
					&& payloadLength > PAYLOAD_MIN_LEN_QUERY_NEXT_IMAGE_REQ)
				return Status.MALFORMED_COMMAND;

			//parse the request
			final OTAQueryImageRequest queryImageRequest = new OTAQueryImageRequest(response.getPayload());
			//OTACluster.this.

			//OTACluster.this.tempO = new OTAQueryImageRequest(response.getPayload());


			//Thread.currentThread().getThreadGroup().getParent().
			//send the response
			//TODO: send actual values

			//try {

			OTACluster.this.invoke(new OTAQueryImageResponse(Status.SUCCESS.id, queryImageRequest.fileID, firmwareList.get(0).firmwareContent.length));

			//} catch (ZigBeeNetworkManagerException e) {
			//	e.printStackTrace();
			//}

			return Status.SUCCESS;
		}

		private Status processImageBlockReq(ResponseImpl response)
		{
			int payloadLength = response.getPayload().length;
			if (payloadLength > OTA.PAYLOAD_MAX_LEN_IMAGE_BLOCK_REQ
					&& payloadLength < PAYLOAD_MIN_LEN_IMAGE_BLOCK_REQ)
				return Status.MALFORMED_COMMAND;

			//parse the request
			OTAImageBlockRequest imageBlockRequest = new OTAImageBlockRequest(response.getPayload());

			//TODO: get the right index from the list instead of the first one
			// get the firmware part



			byte[] chunkFirmware;
			if ((((Integer)imageBlockRequest.fileOffset) + (Integer)(imageBlockRequest.maxDataSize)-1)
					< firmwareList.get(0).firmwareContent.length)
			{

				chunkFirmware = Arrays.copyOfRange(firmwareList.get(0).firmwareContent,
					(Integer) imageBlockRequest.fileOffset,
					(Integer)(imageBlockRequest.maxDataSize) + (Integer)imageBlockRequest.fileOffset);
			} else {
				chunkFirmware = Arrays.copyOfRange(firmwareList.get(0).firmwareContent,
						(Integer) imageBlockRequest.fileOffset,
						(Integer)firmwareList.get(0).firmwareContent.length - (Integer)imageBlockRequest.fileOffset);
			}
			//try {
				// send the firmware chunk
				logger.warn("OTA: FileOffsset: " + imageBlockRequest.fileOffset);
				//enableDefaultResponse();
				//enableDefaultResponse();
			try {
				OTACluster.this.invoke(new OTAImageBlockResponse((byte) Status.SUCCESS.id, imageBlockRequest.fileID,
						imageBlockRequest.fileOffset,
						(byte)((Integer)imageBlockRequest.maxDataSize - 0),
						chunkFirmware));
			} catch (ZigBeeClusterException e) {
				e.printStackTrace();
			}
			//} catch (ZigBeeClusterException e) {
			//	e.printStackTrace();
			//}

			return Status.SUCCESS;
		}

		private Status processUpgradeEndReq(ResponseImpl response)
		{
			int payloadLength = response.getPayload().length;
			if (payloadLength < OTA.PAYLOAD_MAX_LEN_UPGRADE_END_REQ
					&& payloadLength > PAYLOAD_MIN_LEN_UPGRADE_END_REQ)
				return Status.MALFORMED_COMMAND;

			//parse the request
			OTAUpgradeEndRequest upgradeEndRequest = new OTAUpgradeEndRequest(response.getPayload());

			try {
				invoke(new OTAUpgradeEndResponse(upgradeEndRequest.fileID,
						(int)(new Date().getTime()),
						(int)(new Date().getTime()) + 60));
			} catch (ZigBeeClusterException e) {
				e.printStackTrace();
			}

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
		bridge = new OTAListenerNotifier();
		firmwareList.add(new OTAFirmware(0, 0, 0, 0, "/Users/yaronshani/Downloads/5678-1234-00000001.zigbee"));
	}

	public void addNewFirmware(OTAFirmware firmware)
	{
		firmwareList.add(firmware);
		this.sendImageNotify(firmware);
	}

	public void sendImageNotify(OTAFirmware firmware)
	{

		try {
			getZigBeeEndpoint().bindToLocal(ID);
		} catch (ZigBeeNetworkManagerException e) {
			e.printStackTrace();
		}
		getZigBeeEndpoint().addClusterListener(bridge);

		OTAFileID fileID = new OTAFileID(0x0, 0xffff, 0xffffffff);
		try {
			invoke(new OTAImageNotifyRequest((byte) 0x0, (byte) 0x0, fileID));
		} catch (ZigBeeClusterException e) {
			e.printStackTrace();
		}
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
