package com.example.levon.actors;

import com.example.levon.utils.SignUtils;

public class TrustedParty {

	//
	// Private key, generated using:
	//
	// $ openssl genrsa -out central_key.pem 1024
	//
	private static final String PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n"
			+ "MIICXAIBAAKBgQDvd3nuRTl4xm33VKH/SC9mVp8BjnswdMyaQIYRqFFtNN7wD8it\n"
			+ "cOgMcDw399ORyvkQJIthPh0VqvWgNibSpfnMVZJyAY8WtdLw9InbLNgCBMx4DJKH\n"
			+ "gUGci7gjXBEyLVaQsvHRL4pi/N8zp+tCAMKmKlU/K/mnHG+v3T1HgpWWcQIDAQAB\n"
			+ "AoGBAOmEpBWz0dCyCChpkrQkC30LuXFGJWFidOdzRopPjmAlb8XATpy7/r2qcT7O\n"
			+ "4LVKrsiMKpNAhBJOLcr3I7LFgipa1fMOiXeXi5Hat3udEiWkeYZtppal+X9AspI4\n"
			+ "M/nmwymwAtXBriWsYxc4AsbQ8lSaNZXPVADdas7TX/LOG2b1AkEA+olt1hzdnOCX\n"
			+ "1+oG5BFBEmJ78Jkm4ZnNy7NoDXom43Xkk3hS2VUf0B19TCmwdELaOSHw095fb72g\n"
			+ "NEi/ltQYzwJBAPSwQCEHfqkJ9ccFccQXudmwr4swLZvemcIIt7kJXcFASFICIwA1\n"
			+ "zT+/pqbUtV1nVe6m7B7wIkSJaQ/3cT6UrL8CQEk2Bamuz7e7DcQjeBYHULudmdgp\n"
			+ "+Omt9yxSZdKiYmDPn5bUb2lg7aX0KBwshorCymAtoeFXtw3BHOKKa6oYmpMCQFS/\n"
			+ "swZ9nQ4ZSApNxXCgsxtGChcQccuZ1vGQqWmqZ9GmXSYiXVMWT2e0Dpmb9NFMGERX\n"
			+ "hlLU41mPqlcAYjXFsusCQENjyeoXO24F67uXvitmB3Zk8X2C9Y8ulQ3b650awkCX\n"
			+ "Kq5C077q9C/EqigNxMxYgu/McT3zQjBRHZ0cno5QZJ8=\n"
			+ "-----END RSA PRIVATE KEY-----\n";

	//
	// Public key, extracted using:
	//
	// $ openssl rsa -in central_key.pem -pubout
	//
	public static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n"
			+ "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDvd3nuRTl4xm33VKH/SC9mVp8B\n"
			+ "jnswdMyaQIYRqFFtNN7wD8itcOgMcDw399ORyvkQJIthPh0VqvWgNibSpfnMVZJy\n"
			+ "AY8WtdLw9InbLNgCBMx4DJKHgUGci7gjXBEyLVaQsvHRL4pi/N8zp+tCAMKmKlU/\n"
			+ "K/mnHG+v3T1HgpWWcQIDAQAB\n" + "-----END PUBLIC KEY-----\n";
	
	public static byte[] sign(String message) {
		return SignUtils.sign(message, PRIVATE_KEY);
	}
}
