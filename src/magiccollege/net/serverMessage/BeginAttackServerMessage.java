package magiccollege.net.serverMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import magiccollege.net.GameConstants;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

public class BeginAttackServerMessage extends ServerMessage implements
		GameConstants {
	private int size;
	private ArrayList<Integer> list = new ArrayList<Integer>();

	public void set(ArrayList<Integer> al) {
		list.addAll(al);
		size = al.size();
	}

	public ArrayList<Integer> get() {
		return list;
	}

	@Override
	public short getFlag() {
		return FLAG_SERVERMESSAGE_BEGINATTACK;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)
			throws IOException {
		list.clear();
		size = pDataInputStream.readInt();
		for (int i = 0; i < size; i++) {
			list.add(pDataInputStream.readInt());
		}
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)
			throws IOException {
		pDataOutputStream.writeInt(size);
		for (Integer i : list) {
			pDataOutputStream.writeInt(i);
		}
	}
}
