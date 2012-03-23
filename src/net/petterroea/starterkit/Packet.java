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
 * Basic packet class.<br />
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
	 * The prefix
	 */
	public String prefix = "";
	/**
	 * Type, used for checking
	 */
	public Packettype type;
	/**
	 * Packet types
	 */
	public enum Packettype{
		PING
	}
	/**
	 * Constructor for a packet. This is used for recieving, not by you.
	 * @param temp The data in the packet
	 * @param type The packet type
	 */
	public Packet(String[] temp, Packettype type)
	{
		this.contents = new String[temp.length - 1];
		for(int i = 0; i < this.contents.length; i++)
		{
			this.contents[i] = temp[i + 1];
		}
		prefix = type.toString();
		this.type = type;
	}
	/**
	 * Constructor that allows you to enter the raw code. Use this for sending
	 * @param raw Raw data that is not splitted
	 * @param type The packet type
	 */
	public Packet(String raw, Packettype type)
	{
		contents = raw.split(" ");
		prefix = type.toString();
		this.type = type;
	}
	/**
	 * Constructor for a packet. This is used for recieving, not by you.
	 * @param temp The data in the packet
	 * @param type The packet type
	 */
	public Packet(String[] temp)
	{
		prefix = Packettype.valueOf(temp[0]).toString();
		this.contents = new String[temp.length - 1];
		for(int i = 0; i < this.contents.length; i++)
		{
			this.contents[i] = temp[i + 1];
		}
		this.type = Packettype.valueOf(temp[0]);
	}
	/**
	 * Constructor that allows you to enter the raw code. This is used for recieving
	 * @param raw Raw data that is not splitted
	 * @param type The packet type
	 */
	public Packet(String raw)
	{
		String[] temp = raw.split(" ");
		prefix = Packettype.valueOf(temp[0]).toString();
		this.contents = new String[temp.length - 1];
		for(int i = 0; i < this.contents.length; i++)
		{
			this.contents[i] = temp[i + 1];
		}
		this.type = Packettype.valueOf(temp[0]);
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
			returnme = returnme + " " + contents[i];
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
