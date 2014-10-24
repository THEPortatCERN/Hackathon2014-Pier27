package com.example.levon;

public class SignedMessage {
	private String message;
	private byte[] signature;
	
	public SignedMessage(String m, byte[] s) {
		message = m;
		signature = s;
	}
	
	public String getMessage() {
		return message;
	}
	
	public byte[] getSignature() {
		return signature;
	}
}
