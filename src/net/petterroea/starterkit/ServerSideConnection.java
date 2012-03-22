package net.petterroea.starterkit;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Used for a server side connection that can handle tonnes of connections. Use this if you want a game option to host a game, or if you are making a dedicated server
 * @author petterroea
 *
 */
public class ServerSideConnection{
	/**
	 * The writing-buffer
	 */
    private ByteBuffer writeBuffer;
    /**
     * Char decoder
     */
    private CharsetDecoder asciiDecoder;
    /**
     * The read buffer
     */
    private ByteBuffer readBuffer;
	/**
	 * The thread that listens to the network
	 */
	Thread listener;
	/**
	 * The clients connected to the server
	 */
	LinkedList<ServerClient> clients;
	/**
	 * Some random stuff. I dont know what it is, i just use it.
	 */
	private Selector readSelector;
	/**
	 * True if we are monitoring for commections and listening to packets
	 */
	private boolean running = true;
	/**
	 * The server socket channel
	 */
	private ServerSocketChannel sSChannel;
	/**
	 * List of packets going in
	 */
	LinkedList<Packet> in;
	/**
	 * List of packets going out
	 */
	LinkedList<Packet> out;
	/**
	 * Synchronization object
	 */
	public static Object sync = new Object();
	/**
	 * Constructor for a serverside connection
	 * @param port The port to bind to
	 */
	public ServerSideConnection(int port)
	{
		in = new LinkedList<Packet>();
		out = new LinkedList<Packet>();
		readBuffer = ByteBuffer.allocateDirect(255);
		out = new LinkedList<Packet>();
		clients = new LinkedList<ServerClient>();
		try {
			//Open a non-blocking server socket channel
			sSChannel = ServerSocketChannel.open();
			sSChannel.configureBlocking(false);
			System.out.println("Opened the socket channel");
			asciiDecoder = Charset.forName( "UTF-8").newDecoder();
			//Bind to localhost
			InetAddress iAddr = InetAddress.getLocalHost();
			sSChannel.socket().bind(new InetSocketAddress(iAddr, port));
			System.out.println("Binded to locahost");
			System.out.println("Use localhost to connect from this pc, or " + InetAddress.getLocalHost().getHostAddress());
			//Some random stuff for "Multiplexing client channels"
			readSelector = Selector.open();
		} catch (java.net.BindException be) {
			System.out.println("FAILED TO BIND TO PORT(" + port + ")! Is the port allready used?");
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Runs the server network stuff
	 */
	public void start()
	{
		listener = new Thread(){
			@Override
			public void run()
			{
				while(running)
				{
					synchronized(sync)
					{
						acceptNewPeeps();
						readIncomingMessages();
						sendPackets();
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		listener.start();
	}
	/**
	 * Used by the seperate thread to send packets in the queue
	 */
	private void sendPackets()
	{
		for(int i = 0; i < clients.size(); i++)
		{
			for(int a = 0; a < clients.get(i).out.size(); a++)
			{
				sendMessage(clients.get(i).socket, clients.get(i).out.get(a).prefix + " " + clients.get(i).out.get(a).getRaw());
			}
			clients.get(i).out.clear();
		}
		for(int i = 0; i < out.size(); i++)
		{
			for(int a = 0; a < clients.size(); a++)
			{
				sendMessage(clients.get(a).socket, out.get(i).prefix + " " + out.get(i).getRaw());
			}
		}
		out.clear();
		
	}
	/**
	 * Sends a message to the client. Again, dont touch this
	 * @param channel The channel from the client
	 * @param mesg The message to be sendt
	 */
	private void sendMessage(SocketChannel channel, String mesg) {
    	prepWriteBuffer(mesg);
    	channelWrite(channel, writeBuffer);
        }
	/**
	 * Reads incoming packets
	 */
	private void readIncomingMessages() {
		try {
		    // non-blocking select, returns immediately regardless of how many keys are ready
		    readSelector.selectNow();
		    
		    // fetch the keys
		    Set readyKeys = readSelector.selectedKeys();
		    
		    // run through the keys and process
		    Iterator i = readyKeys.iterator();
		    while (i.hasNext()) {
			SelectionKey key = (SelectionKey) i.next();
			i.remove();
			SocketChannel channel = (SocketChannel) key.channel();
			readBuffer.clear();
			
			// read from the channel into our buffer
			long nbytes = channel.read(readBuffer);
			
			// check for end-of-stream
			if (nbytes == -1) { 
			    System.out.println("disconnect: " + channel.socket().getInetAddress() + ", end-of-stream");
			    for(int a = 0; a < clients.size(); a++)
			    {
			    	if(clients.get(a).socket.hashCode() == channel.hashCode())
			    	{
			    		clients.remove(a);
			    		System.out.println("Removed the socket sucessfully");
			    		break;
			    	}
			    }
			    channel.close();
			    clients.remove(channel);
			}
			else {
			    // grab the StringBuffer we stored as the attachment
			    StringBuffer sb = (StringBuffer)key.attachment();
			    
			    // use a CharsetDecoder to turn those bytes into a string
			    // and append to our StringBuffer
			    readBuffer.flip( );
			    String str = asciiDecoder.decode( readBuffer).toString( );
			    readBuffer.clear( );
			    sb.append( str);
			    // check for a full line
			    String line = sb.toString();
			    if ((line.indexOf("\n") != -1) || (line.indexOf("\r") != -1)) {
				line = line.trim();
				in.add(getPacket(line.split("\\s+")));
				    sb.delete(0,sb.length());
				    
			    }
			}
			
		    }		
		}
		catch (IOException ioe) {
		    System.out.println("error during select(): " + ioe);
		}
		catch (Exception e) {
		    //System.out.println("exception in run()" + e);
			e.printStackTrace();
		}
		
	    }
	/**
	 * Used to recognize packets by their prefix. For each new packet you add, you need to declare it here.
	 * @param msg The contents of the packet that has just arrived from the serverside
	 * @return A fresh, new packet object containing the data from the packet sendt over the internet
	 */
	public Packet getPacket(String[] msg)
	{
		if(msg[0].equalsIgnoreCase("ping"))
		{
			return new PingPacket(msg);
		}
		return null;
	}
	/**
	 * Writes to a channel
	 * @param channel The channel
	 * @param writeBuffer The buffer
	 */
	private void channelWrite(SocketChannel channel, ByteBuffer writeBuffer) {
    	long nbytes = 0;
    	long toWrite = writeBuffer.remaining();

    	// loop on the channel.write() call since it will not necessarily
    	// write all bytes in one shot
    	try {
    	    while (nbytes != toWrite) {
    		nbytes += channel.write(writeBuffer);
    		
    		try {
    		    Thread.sleep(30);
    		}
    		catch (InterruptedException e) {}
    	    }
    	}
    	catch (ClosedChannelException cce) {
    	}
    	catch (Exception e) {
    	} 
    	
    	// get ready for another write if needed
    	writeBuffer.rewind();
        }
	/**
	 * Used for preparing writing to the client
	 * @param mesg
	 */
	private void prepWriteBuffer(String mesg) {
    	// fills the buffer from the given string
    	// and prepares it for a channel write
    	writeBuffer.clear();
    	writeBuffer.put(mesg.getBytes());
    	writeBuffer.putChar('\n');
    	writeBuffer.flip();
        }
	/**
	 * Used by the seperate thread to accept new connections
	 */
	private void acceptNewPeeps()
	{
		try {
		    SocketChannel clientChannel;
		    // since sSockChan is non-blocking, this will return immediately 
		    // regardless of whether there is a connection available
		    while ((clientChannel = sSChannel.accept()) != null) {
			System.out.println("got connection from: " + clientChannel.socket().getInetAddress()); 
			clientChannel.configureBlocking( false);
		    SelectionKey readKey = clientChannel.register(readSelector, SelectionKey.OP_READ, new StringBuffer());
		    clients.add(new ServerClient(clientChannel));
		    }		
		}
		catch (IOException ioe) {
		    System.out.println("error during accept(): " + ioe);
		}
		catch (Exception e) {
		    System.out.println("exception in acceptNewConnections()" + e);
		}
	}
	/**
	 * Stops the server network thingie. Use this before disposing the network code
	 * @throws InterruptedException If faied to join thread
	 * @throws IOException If failed to close the socket
	 */
	public void stop() throws InterruptedException, IOException
	{
		running = false;
		listener.join();
		sSChannel.close();
	}
}
