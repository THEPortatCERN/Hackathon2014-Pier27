package com.example.levon.actors;

import com.example.levon.utils.SignedMessage;

public class Hospital {

	private static final String message = "Hospital name: Red Cross\nLocation: +38�,-9�\nSigned by: safezone.org";
	private static final byte[] signature = TrustedParty.sign(message);
	
	public static SignedMessage getMessage()
	{
		return new SignedMessage(message, signature);
	}
}
