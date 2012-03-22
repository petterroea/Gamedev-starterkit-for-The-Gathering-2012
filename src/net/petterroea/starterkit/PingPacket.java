package net.petterroea.starterkit;

public class PingPacket extends Packet {
	public String prefix = "ping";
	public PingPacket(String[] array)
	{
		super(array);
		prefix = "ping";
	}
	public PingPacket(String raw) {
		super(raw);
		// TODO Auto-generated constructor stub
	}

}
