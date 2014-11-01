package com.example.levon.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import android.util.Base64;

public class SignUtils {

	private static boolean matches(String[] lines, String first, String last) {
		return (lines[0].equals(first) && lines[lines.length - 1].equals(last));
	}

	private static byte[] pem2der(String pem) {
		String[] lines = pem.split("\n");

		if (lines.length > 2) {
			StringBuffer sb = new StringBuffer();
			for (int i = 1; i < lines.length - 1; i++)
				sb.append(lines[i]);

			if (matches(lines, "-----BEGIN PRIVATE KEY-----",
					"-----END PRIVATE KEY-----")
					|| matches(lines, "-----BEGIN PUBLIC KEY-----",
							"-----END PUBLIC KEY-----"))
				return Base64.decode(sb.toString(), Base64.DEFAULT);
		}
		throw new RuntimeException("Unrecognized pem format");
	}

	public static byte[] sign(String message, String privateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privKey = keyFactory
					.generatePrivate(new PKCS8EncodedKeySpec(
							pem2der(privateKey)));

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

	public static boolean verify(String message, byte[] signature,
			String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory
					.generatePublic(new X509EncodedKeySpec(pem2der(publicKey)));

			Signature sign = Signature.getInstance("SHA256withRSA");
			sign.initVerify(pubKey);
			sign.update(message.getBytes());
			return sign.verify(signature);
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
