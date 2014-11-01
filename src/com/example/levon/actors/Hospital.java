package com.example.levon.actors;

import com.example.levon.utils.SignedMessage;

public class Hospital {

	private static final String message = "Name: General Hospital\nLatitude: -18.898449\nLongitude: 47.504368\nLocation: Antananarivo, Madagascar\nSigned by: nostrike.org";
	private static final byte[] signature = TrustedParty.sign(message);
	
	public static SignedMessage getMessage()
	{
		return new SignedMessage(message, signature);
	}
}
