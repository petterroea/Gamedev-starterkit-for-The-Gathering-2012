/*
 * Packet.java
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
/**
 * Basic packet class. Extend this and make your own class for each type of packet.<br />
 * <br />
 * This is used for recieving and sending packets
 * 
 * @author petterroea
 *
 */
public class Packet {
	/**
	 * Contents of the packet
	 */
	private String[] contents;
	/**
	 * The prefix used to identify the packet when recieved
	 */
	public final String prefix = "packet";
	/**
	 * Constructor for a packet. This is used for recieving
	 * @param contents The data in the packet
	 * @param ip The ip adress of the sender
	 */
	public Packet(String[] contents)
	{
		this.contents = new String[contents.length - 1];
		for(int i = 0; i < this.contents.length; i++)
		{
			this.contents[i] = contents[i + 1];
		}
	}
	/**
	 * Constructor that allows you to enter the raw code. Use this for sending
	 * @param raw Raw data that is not splitted
	 */
	public Packet(String raw)
	{
		contents = raw.split(" ");
	}
	/**
	 * Get the raw data of this packet
	 * @return The raw data
	 */
	public String getRaw()
	{
		String returnme = "";
		for(int i = 0; i < contents.length; i++)
		{
			returnme = returnme + contents[i];
		}
		return returnme;
	}
	/**
	 * Get the entire packet data array
	 * @return The array of data
	 */
	public String[] getEntirePacket()
	{
		return contents;
	}
	/**
	 * Get the packet data at the following index
	 * @param index The index you want packet data from
	 * @return The packet data at index.
	 */
	public String getPacketDataAt(int index)
	{
		return contents[index];
	}
	/**
	 * Get the length of the packet data array
	 * @return The length of the packet data array
	 */
	public int getLength()
	{
		return contents.length;
	}
	/**
	 * Sets packet data at the following index
	 * @param index The index to set data from
	 * @param data The data to insert into the array at position <i>index</i>
	 */
	public void setPacketDataAt(int index, String data)
	{
		contents[index] = data;
	}
}
