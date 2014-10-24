package com.example.levon;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class SignUtils {

	public static byte[] sign(String message, String privateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privKey = keyFactory.generatePrivate(new X509EncodedKeySpec(privateKey.getBytes()));
			
			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initSign(privKey);
			sign.update(message.getBytes());
			return sign.sign();
		} catch (NoSuchAlgorithmException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		}
	}
}
