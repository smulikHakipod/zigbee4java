package org.bubblecloud.zigbee.api.cluster.impl.general;

import org.bubblecloud.zigbee.api.cluster.impl.api.core.Command;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.DoorLock;
import org.bubblecloud.zigbee.api.cluster.impl.api.general.OTA;

public class OTACommandImpl implements Command {

	private byte id;
	private byte[] payload;

	public OTACommandImpl(OTAQueryImageResponse cmd)
	{
		this.id = OTA.COMMAND_QUERY_NEXT_IMAGE_RSP;
		this.payload = cmd.getPayload();
	}

	@Override
	public boolean isManufacturerExtension() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isClusterSpecific() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isClientServerDirection() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public byte[] getPayload() {
		return this.payload;
		
	}
	
	@Override
	public byte[] getManufacturerId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public byte getHeaderCommandId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public byte[] getAllowedResponseId() {
		// TODO Auto-generated method stub
		return null;
	}


}
