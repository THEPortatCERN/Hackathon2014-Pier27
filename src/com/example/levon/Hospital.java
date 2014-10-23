package com.example.levon;

import java.util.UUID;

public class Hospital {

	public static final String SERVICE_NAME = "FixedSafeZone";
	public static final UUID SERVICE_UUID = UUID.fromString("2fb33440-5a04-11e4-a33a-0002a5d5c51b");
	
	public static byte[] getMessage()
	{
		// TODO: define message format and add proper message here
		return "message".getBytes();
	}

	public static byte[] getSignature()
	{
		// TODO: sign message and return proper signature
		return "signatue".getBytes();
	}
}
