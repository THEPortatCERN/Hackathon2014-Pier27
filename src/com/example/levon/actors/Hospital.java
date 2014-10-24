package com.example.levon.actors;

import com.example.levon.utils.SignedMessage;

public class Hospital {

	private static final String message = "";
	private static final byte[] signature = TrustedParty.sign(message);
	
	public static SignedMessage getMessage()
	{
		return new SignedMessage(message, signature);
	}
}
