package com.example.levon.utils;

public class Response {
	private byte[] challengeSignature;
	private String message;
	private byte[] signature;
	private String certificate;

	public Response(byte[] challengeSignature, String message, byte[] signature, String certificate) {
		this.challengeSignature = challengeSignature;
		this.message = message;
		this.signature = signature;
		this.certificate = certificate;
	}

	public byte[] getChallengeSignature() {
		return challengeSignature;
	}
	
	public String getMessage() {
		return message;
	}
	
	public byte[] getSignature() {
		return signature;
	}
	
	public String getCertificate() {
		return certificate;
	}
}
