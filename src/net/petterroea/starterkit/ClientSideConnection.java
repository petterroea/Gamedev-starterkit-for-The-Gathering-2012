/*
 * ClientSideConnection.java
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

import java.io.*;
import java.net.*;
import java.util.LinkedList;

/**
 * Represents the client side connection in a game if you want multiplayer ;)<br />
 * This is beta code, so i cannot guarantee that it works
 * @author petterroea
 *
 */
public class ClientSideConnection {
	/**
	 * Use this to synchronize
	 */
	public static Object synch = new Object();
	/**
	 * Clear the list after reading from it. This is a linkedList with incoming
	 */
	public LinkedList<Packet> in;
	/**
	 * LinkedList of packets going out
	 */
	public LinkedList<Packet> out;
	/**
	 * The socket that connects to the server
	 */
	private Socket client;
	/**
	 * False if packet listeners should stop
	 */
	private boolean listening = true;
	/**
	 * The thread that listens
	 */
	Thread inListener;
	/**
	 * The thread the sends
	 */
	Thread outListener;
	/**
	 * Server to client packet reader
	 */
	private BufferedReader reader;
	/**
	 * Client to server packet writer
	 */
	private PrintWriter writer;
	/**
	 * Server
	 */
	private InetSocketAddress endPoint;
	/**
	 * True if we are connected
	 */
	public boolean connected = false;
	/**
	 * Constructor for a connection to a server. If the IP is localhost, the game will try to connect to a server on the same ip as the server itself
	 * @param ip The ip adress
	 * @param port The port to be used. REMEMBER TO TELL USERS WHAT PORT THE GAME USES FOR COMMUNICATION. http://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers is a important reference. Also, here is a tip: Let the user choose port, but supply a standardport
	 * @throws IOException if something goes wrong while connecting
	 */
	public ClientSideConnection(String ip, int port) throws IOException
	{
		if(ip.equalsIgnoreCase("localhost"))
		{
			ip = InetAddress.getLocalHost().getHostAddress();
		}
		client = new Socket();
		endPoint = new InetSocketAddress(ip, port);
		client.connect(endPoint);
		reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		writer = new PrintWriter(client.getOutputStream());
		connected = !client.isClosed() && client.isConnected();
	}
	/**
	 * Stops listening for packets. Allways call this before disposing the object, or you will have a listener going forever(as long as the game does)
	 * @throws InterruptedException If the listeners could not be joined
	 */
	public void stopListening() throws InterruptedException
	{
		listening = false;
		inListener.join();
		outListener.join();
	}
	/**
	 * Starts listening. This uses synchronization to make sure nothing is breaking the game. ALL PARTS THAT ACESS THE TWO LINKEDLISTS MUST SYNCHRONIZE TO THE SYNCH OBJECT
	 */
	public void startListening()
	{
		listening = true;
		inListener = new Thread(){
			@Override
			public void run()
			{
				//This is recieving
				while(listening)
				{
					try{
					synchronized(synch)
					{
						String line = reader.readLine();
						if(line != null)
						{
							String[] msg = line.split("\\s+");
							in.add(getPacket(msg));
						}						
					}
					//We are done checking, so sleep a little while ;)
					Thread.sleep(10);
					} catch(Exception e){
						System.out.println("Error in networking: " + e);
					}
				}
			}
		};
		outListener = new Thread(){
			@Override
			public void run()
			{
				//This is sending packets out
				while(listening)
				{
					try{
					synchronized(synch)
					{
						if(out.size() > 0)
						{
							for(int i = 0; i < out.size(); i++)
							{
								writer.println(out.get(i).prefix + " " + out.get(i).getRaw());
								writer.flush();
							}
							out.clear();
						}
					
						
						
					}
					//We are done checking, so sleep a little while ;)
					Thread.sleep(10);
					} catch(Exception e){
						System.out.println("Error in networking: " + e);
					}
				}
			}
		};
		inListener.start();
		outListener.start();
	}
	/**
	 * Used to recognize packets by their prefix. For each new packet you add, you need to declare it here.
	 * @param msg The contents of the packet that has just arrived from the serverside
	 * @return A fresh, new packet object containing the data from the packet sendt over the internet
	 */
	public Packet getPacket(String[] msg)
	{
		if(msg[0].equalsIgnoreCase("packetPrefixHere"))
		{
			return new Packet(msg);
		}
		return null;
	}
}
