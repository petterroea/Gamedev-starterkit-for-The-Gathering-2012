/*
 * ServerClient.java
 * 
 * Part of the GameSDK starterkit made for the seminar at "The Gathering 2012 - At the end of the universe"
 * 
 * This source code is provided AS-IS and without any warranty. Use at own risk.
 * You can use and modify this source any way you want, but please send me an E-mail to petterroea@skymiastudios.com
 * if you make a game from it - I would like to keep a list of all games, as i am curious. You may also use this for games
 * that cost money. So it is basically public domain, except that i own the rights to it, but give you the rights to use
 * it for the purposes above. You cant sell this kit. You can sell a game based on it, but not the source code to this kit.
 * Also, this big comment must stay in all code files that originated from the starterkit, no matter how much they are
 * modified.
 * 
 * If you want support for this SDK, feel free to contact me. If you are at "The Gathering 2012", you can
 * look for me in the creative lounge(Where i sit). If not, feel free to send me an E-Mail to the above
 * adress. If you need normal java help, ask a mentor.
 * 
 */
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
