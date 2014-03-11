package magiccollege.net.listener;

import java.io.IOException;

import magiccollege.main.MyData;
import magiccollege.net.clientMessage.LogOnClientMessage;

import org.andengine.extension.multiplayer.protocol.client.connector.ServerConnector;
import org.andengine.extension.multiplayer.protocol.client.connector.SocketConnectionServerConnector.ISocketConnectionServerConnectorListener;
import org.andengine.extension.multiplayer.protocol.shared.SocketConnection;
import org.andengine.util.debug.Debug;

public class MySocketConnectionServerConnectorListener implements ISocketConnectionServerConnectorListener {
	@Override
	public void onStarted(final ServerConnector<SocketConnection> pServerConnector) {
		Debug.d("Accepted Server-Connection from: '" + pServerConnector.getConnection().getSocket().getInetAddress().getHostAddress());
		try {
			pServerConnector.sendClientMessage(new LogOnClientMessage(MyData.getInstance().getId()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTerminated(final ServerConnector<SocketConnection> pServerConnector) {
		Debug.d("Closed Server-Connection from: '" + pServerConnector.getConnection().getSocket().getInetAddress().getHostAddress());
	}
}
