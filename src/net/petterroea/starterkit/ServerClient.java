package net.petterroea.starterkit;

import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * Client object used by serverside. Did you expect that?
 * @author petterroea
 *
 */
public class ServerClient {
	LinkedList<Packet> out;
	SocketChannel socket;
	public ServerClient(SocketChannel socket)
	{
		out = new LinkedList<Packet>();
		this.socket = socket;
	}
}
