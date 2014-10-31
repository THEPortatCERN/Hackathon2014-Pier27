package com.example.levon.actors;

import com.example.levon.utils.SignUtils;
import com.example.levon.utils.SignedMessage;

public class FakeHospital {
	private static final String message = "Hospital name: Red Cross\nLocation: 0°,0°\nSigned by: safezone.org";

	//
	// Fake private key, generated using:
	//
	// $ openssl genrsa -out fake.key 1024
	// $ openssl pkcs8 -topk8 -inform pem -in fake.key -outform pem -nocrypt
	// -out fake.pk8
	//
	private static final String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n"
			+ "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMB5nKithBoYpnMU\n"
			+ "7VjpYigaqgGoIKJeymOHF7D0ff5wIq19/qTvORm6qSsq+q9Hwdgy2HANBqGkGtkh\n"
			+ "59LP0XjZE5g2AZNmPXMmbYveNnJtfaioJxbeiNULCMx8oBJI9lcFZxbLkxQe7KHy\n"
			+ "FgW7NwNcYUiKmW/WYX1LoGnvYG/TAgMBAAECgYBSgw/so84Q3eZlSWBikFJa/OtK\n"
			+ "oIe088vxlNVML9PfgrTrCStkC4XACOxCrD8hazKne0yb5ImmO9KiqM1X621RkZzk\n"
			+ "nXIem5coUYv4Z8+2Ta4Fz2uXvJkeqOXr0DP9+idOHJiDiOnGvbiGqWkYp5nrF0Wr\n"
			+ "ZIv7lyZPgYqOgEJHuQJBAOue7aYbUSU3RwVQBUCG0JIZzP0w9GkNO1fG6YricmuG\n"
			+ "ei+HJCZgaIOQyYuxe8nU+NFAVU4jcYO6SHF5D66dZU8CQQDRH1ux9lmZ98kTfYea\n"
			+ "H0n3/6P2ZUMtm++y1Tqumw+tIcoC1ia8AJXMJpnK2qWxB3r7rCgFCeWxLBbLEFOH\n"
			+ "sPQ9AkBGSWyfJnr4XRsEjLBkZVMGokeyJ85hIB9Zue6lDZQQW1dLoka4Y/4JcUGO\n"
			+ "OG2y8aMf2M8Zth0ArKs7nl99OVqlAkB4tXYTSlS8CAgb5PiR4P+sU/aLsjXNrNX2\n"
			+ "Txh5Uu4AjKFzc7OWD2xoE9vv+LdtcRjvDpdnGShpVT6u5fShPWYtAkAl+nK7qLV/\n"
			+ "ZRxC6oKlsFEyyeFeCXJ7GgE/FhC4a3iiFAbsjS8q5Io8PRxomnP1jNsUo288eyZM\n"
			+ "NjuLKVmhqpQm\n" + "-----END PRIVATE KEY-----\n";

	//
	// Take the signed hospital message and change the message
	//
	public static SignedMessage getFakeHospitalMessage() {
		return new SignedMessage(message, Hospital.getMessage().getSignature());
	}

	public static SignedMessage getMessageSignedWithFakeCertificate() {
		return new SignedMessage(message, SignUtils.sign(message, PRIVATE_KEY));
	}
}
