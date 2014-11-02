package com.example.levon.actors;

import com.example.levon.utils.Challenge;
import com.example.levon.utils.Response;
import com.example.levon.utils.SignUtils;

public class FakeAmbulance {

	//
	// Private key, generated using:
	//
	// $ openssl genrsa -out ambulance.key 1024
	// $ openssl pkcs8 -topk8 -inform pem -in ambulance.key -outform pem
	// -nocrypt -out ambulance.pk8
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
			+ "ZRxC6oKlsFEyyeFeCXJ7GgE/FhC4a3iiFAbsjS8q5Io8PRxomnP1jNsUo288eyZM\n" + "NjuLKVmhqpQm\n"
			+ "-----END PRIVATE KEY-----\n";

	//
	// Public certificate, fake and self signed, generated using:
	//
	// $ openssl req -new -key fake.key -out fake.csr
	// $ openssl x509 -req -days 30 -in fake.csr -signkey fake.key -out fake.crt
	//
	public static final String PUBLIC_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
			+ "MIIByTCCATICCQD1+0hZHDeW1DANBgkqhkiG9w0BAQUFADApMQswCQYDVQQGEwJT\n"
			+ "RTEaMBgGA1UEAwwRZmFrZS5zYWZlem9uZS5vcmcwHhcNMTQxMTAyMTM0MDMzWhcN\n"
			+ "MTQxMjAyMTM0MDMzWjApMQswCQYDVQQGEwJTRTEaMBgGA1UEAwwRZmFrZS5zYWZl\n"
			+ "em9uZS5vcmcwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMB5nKithBoYpnMU\n"
			+ "7VjpYigaqgGoIKJeymOHF7D0ff5wIq19/qTvORm6qSsq+q9Hwdgy2HANBqGkGtkh\n"
			+ "59LP0XjZE5g2AZNmPXMmbYveNnJtfaioJxbeiNULCMx8oBJI9lcFZxbLkxQe7KHy\n"
			+ "FgW7NwNcYUiKmW/WYX1LoGnvYG/TAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAIN6W\n"
			+ "a92hfWoaz6DPz2iTaCbMVxDlrOmbE+gZLASFUG9YydIPhLN6JcnxTvlNaA6J+zNX\n"
			+ "EByY3F5G1hiTaTXyYbjziWRIuylNFIYIck2vqcKUHaij4xp5mA1YoRt3mkZ/eJBc\n"
			+ "zmjYhl4TTHsET1KhZUAKq0q9tOn1b/KI5yjqghE=\n" + "-----END CERTIFICATE-----\n";

	private static final String message = "Reg Nr: ABC 123\nDriver Name: Olle Sol\nOrganization: Red Cross";
	private static final byte[] signature = SignUtils.sign(message, PRIVATE_KEY);

	//
	// Take a signed ambulance message and change the message
	//
	public static Response createFakeMessageResponse(Challenge challenge) {
		Response r = Ambulance.createResponse(challenge);
		return new Response(r.getChallengeSignature(), message, r.getSignature(), r.getCertificate());
	}

	//
	// Sign using a fake certificate
	//
	public static Response createFakeCertificateResponse(Challenge challenge) {
		byte[] challengeSignature = SignUtils.sign(challenge.getRandomString(), PRIVATE_KEY);
		return new Response(challengeSignature, message, signature, PUBLIC_CERTIFICATE);
	}
}
