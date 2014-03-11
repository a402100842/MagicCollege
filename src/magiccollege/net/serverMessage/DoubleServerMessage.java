package magiccollege.net.serverMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import magiccollege.net.GameConstants;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;


public class DoubleServerMessage extends ServerMessage implements GameConstants{
	private double message;
	
	public void set(final double d){
		message = d;
	}

	public double get(){
		return message;
	}
	
	@Override
	public short getFlag() {
		return FLAG_SERVERMESSAGE_DOUBLE;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)
			throws IOException {
		message = pDataInputStream.readDouble();
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)
			throws IOException {
		pDataOutputStream.writeDouble(message);
	}

}
