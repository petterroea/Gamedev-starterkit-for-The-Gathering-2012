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
	 * Clear the list after reading from it. This is a linkedList with all new packets
	 * 
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
	 * Constructor for a connection to a server
	 * @param ip The ip adress
	 * @param port The port to be used. REMEMBER TO TELL USERS WHAT PORT THE GAME USES FOR COMMUNICATION. http://en.wikipedia.org/wiki/List_of_TCP_and_UDP_port_numbers is a important reference. Also, here is a tip: Let the user choose port, but supply a standardport
	 * @throws IOException if something goes wrong while connecting
	 */
	public ClientSideConnection(String ip, int port) throws IOException
	{
		client = new Socket();
		endPoint = new InetSocketAddress(ip, port);
		client.connect(endPoint);
		reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		writer = new PrintWriter(client.getOutputStream());
		connected = !client.isClosed() && client.isConnected();
	}
	/**
	 * Starts listening. This uses synchronization to make sure nothing is breaking the game. ALL PARTS THAT ACESS THE TWO LINKEDLISTS MUST SYNCHRONIZE TO THE SYNCH OBJECT
	 */
	public void startListening()
	{
		new Thread(){ //Listener
			@Override
			public void run()
			{
				synchronized(synch)
				{
					//TODO
				}
			}
		}.start();
		new Thread(){ //Writer
			@Override
			public void run()
			{
				synchronized(synch)
				{
					//TODO
				}
			}
		}.start();
	}
}
