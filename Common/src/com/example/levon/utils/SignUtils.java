package com.example.levon.utils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import android.util.Base64;

import com.example.levon.actors.TrustedParty;

public class SignUtils {

	private static CertificateFactory certFactory = null;
	private static KeyFactory keyFactory = null;

	static {
		try {
			certFactory = CertificateFactory.getInstance("X.509");
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (CertificateException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		}
	}

	private static boolean matches(String[] lines, String first, String last) {
		return (lines[0].equals(first) && lines[lines.length - 1].equals(last));
	}

	private static PrivateKey getPrivateKey(String pem) {
		String[] lines = pem.split("\n");
		try {
			if (lines.length > 2) {
				StringBuffer sb = new StringBuffer();
				for (int i = 1; i < lines.length - 1; i++)
					sb.append(lines[i]);

				if (matches(lines, "-----BEGIN PRIVATE KEY-----", "-----END PRIVATE KEY-----")) {
					return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(sb.toString(),
							Base64.DEFAULT)));
				}
			}
		} catch (InvalidKeySpecException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		}
		throw new RuntimeException("Unrecognized pem format: " + lines[0]);
	}

	private static PublicKey getPublicKey(String pem) {
		String[] lines = pem.split("\n");
		try {
			if (lines.length > 2) {
				StringBuffer sb = new StringBuffer();
				for (int i = 1; i < lines.length - 1; i++)
					sb.append(lines[i]);

				if (matches(lines, "-----BEGIN PUBLIC KEY-----", "-----END PUBLIC KEY-----")) {
					return keyFactory.generatePublic(new X509EncodedKeySpec(
							Base64.decode(sb.toString(), Base64.DEFAULT)));
				} else if (matches(lines, "-----BEGIN CERTIFICATE-----", "-----END CERTIFICATE-----")) {
					Certificate certificate = certFactory.generateCertificate(new ByteArrayInputStream(pem
							.getBytes("UTF-8")));
					return certificate.getPublicKey();
				}
			}
		} catch (UnsupportedEncodingException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		} catch (CertificateException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		}
		throw new RuntimeException("Unrecognized pem format: " + lines[0]);
	}

	private static Certificate getCertificate(String pem) {
		String[] lines = pem.split("\n");
		try {
			if (lines.length > 2) {
				StringBuffer sb = new StringBuffer();
				for (int i = 1; i < lines.length - 1; i++)
					sb.append(lines[i]);

				if (matches(lines, "-----BEGIN CERTIFICATE-----", "-----END CERTIFICATE-----")) {
					return certFactory.generateCertificate(new ByteArrayInputStream(pem.getBytes("UTF-8")));
				}
			}
		} catch (UnsupportedEncodingException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		} catch (CertificateException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		}
		throw new RuntimeException("Unrecognized pem format: " + lines[0]);
	}

	public static byte[] sign(String message, String privateKey) {
		try {
			PrivateKey privKey = getPrivateKey(privateKey);
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
		} catch (InvalidKeyException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		}
	}

	public static boolean verify(String message, byte[] signature, String publicKey) {
		try {
			PublicKey pubKey = getPublicKey(publicKey);
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
		} catch (InvalidKeyException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		}
	}

	public static boolean verifyCertificate(String certificate) {
		try {
			Certificate received = getCertificate(certificate);
			Certificate trusted = getCertificate(TrustedParty.PUBLIC_CERTIFICATE);
			certFactory.generateCertPath(Arrays.asList(received, trusted));
			return true;
		} catch (CertificateException e) {
			// This is not expected to happen, no error handling
			throw new RuntimeException(e);
		}
	}
}
