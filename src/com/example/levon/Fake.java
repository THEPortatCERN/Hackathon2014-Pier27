package com.example.levon;

public class Fake {
	public static byte[] getFakeHospitalMessage()
	{
		// TODO: return a proper fake message, changing the location
		return "fake".getBytes();
	}
	
	public static byte[] sign(byte[] message) {
		// TODO: do real signing with a fake certificate
		return "fake".getBytes();
	}
}
