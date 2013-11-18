package edu.hsbremen.mobile.viergewinnt.googleplay;

import java.util.Hashtable;

/**
 * Possible network header.
 */
public enum Header
{
	PLACE_TOKEN((byte)0);
	
	private byte value;
	
	private Header(byte value)
	{
		this.value = value;
	}
	
	public byte getByteValue()
	{
		return value;
	}
	
	//the following  is used for opposite mapping
	//i.e. map byte value to enum value
	
	private static final Hashtable<Byte, Header> byteToHeader = new Hashtable<Byte, Header>();
	
	static 
	{
		for (Header header : Header.values())
		{
			byteToHeader.put(header.value, header);
		}
		
	}
	
	
	public static Header fromValue(Byte value)
	{
		return byteToHeader.get(value);
	}
	
}
