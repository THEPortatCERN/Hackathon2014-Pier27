package com.example.levon.actors;

import com.example.levon.utils.Challenge;
import com.example.levon.utils.Response;
import com.example.levon.utils.SignUtils;

public class Ambulance {

	//
	// Private key, generated using:
	//
	// $ openssl genrsa -out ambulance.key 1024
	// $ openssl pkcs8 -topk8 -inform pem -in ambulance.key -outform pem
	// -nocrypt -out ambulance.pk8
	//
	private static final String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n"
			+ "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPnAeiAWbKhWh1oG\n"
			+ "w38tXMTfJPEnVSQ11KmaQ0I9uhdQjfBGn+CfUJ59RjrBSLUU3HoJ6SizqYedQRvK\n"
			+ "TG/VetXLbAgnDiO4r6ssUqJ6bXK4K2nuDS+swg34c4Up35NZWpC2SXpEGjIoixUV\n"
			+ "pIgBkbfxGfifCKfcsW5IQM4auOZVAgMBAAECgYAY5m3GoTW4Gthny2DWXylun2jJ\n"
			+ "ekhs0ckgF7c49XqGNIvSNthdxsR86LSFyxt6olfQ+wf5/N+cG7uQD71LMReL4s2l\n"
			+ "Vj8lvTAdl+q5lLuMr5N/hTvwQokDdIpoX+TfTy6iljEtNyyKHrMPTPq6NvJKoSw8\n"
			+ "uYvowkGCG0KBtpBMgQJBAP/z6oH+bt43kJ4Myff3mItzhKDxFPRCF93J4pqV9axi\n"
			+ "gS/sqwkT/9+wIDeWVwQSn7ZH9L2Ulk9Q8Usew+s++OUCQQD5zESsBDAtiSfMFfzj\n"
			+ "vQZ7YkjJ9RnrJgFTtq9mGZzj0p0qiN7jgaPba19vQRQCkerhdO2ilZgqylZn6alK\n"
			+ "lpCxAkAOdkK+25h6F9ulAoX669ODtaA908FiV8Wvc3PJVkXOWrZ/9bhoR+gCRJpd\n"
			+ "abI6+a5qh0BN4fJoQuOPYgqh5Sn9AkAylkNx8ocvMY91rR5BoI4nfofvHkvZayDp\n"
			+ "qx1qN9Wl7YkWjBFXUXG1hdEV3sAGpTZD4kIdot3mDIPjF1bJXCSBAkEAxgxr6hBi\n"
			+ "+2PVaRY+uDiClFYLRrI77MRb3mSzePGmzDCa/DYrnqxNHrjmrxlhBlwQWHB2a/Os\n" + "nhieSGFss/V3jA==\n"
			+ "-----END PRIVATE KEY-----\n";

	//
	// Public key, generated using:
	//
	// $ openssl rsa -in ambulance.key -pubout
	//
	public static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n"
			+ "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD5wHogFmyoVodaBsN/LVzE3yTx\n"
			+ "J1UkNdSpmkNCPboXUI3wRp/gn1CefUY6wUi1FNx6Cekos6mHnUEbykxv1XrVy2wI\n"
			+ "Jw4juK+rLFKiem1yuCtp7g0vrMIN+HOFKd+TWVqQtkl6RBoyKIsVFaSIAZG38Rn4\n" + "nwin3LFuSEDOGrjmVQIDAQAB\n"
			+ "-----END PUBLIC KEY-----\n";

	//
	// Public certificate, signed by the trusted party, generated using:
	//
	// $ openssl req -new -key ambulance.key -out ambulance.csr
	// $ openssl ca -cert central.crt -keyfile central.key -in ambulance.csr
	// -out ambulance.crt
	//
	public static final String PUBLIC_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
			+ "MIICWjCCAcOgAwIBAgIBATANBgkqhkiG9w0BAQUFADAkMQswCQYDVQQGEwJTRTEV\n"
			+ "MBMGA1UEAwwMc2FmZXpvbmUub3JnMB4XDTE0MTEwMjEyMTI0OVoXDTE1MTEwMjEy\n"
			+ "MTI0OVowJDELMAkGA1UEBhMCU0UxFTATBgNVBAMMDHNhZmV6b25lLm9yZzCBnzAN\n"
			+ "BgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA+cB6IBZsqFaHWgbDfy1cxN8k8SdVJDXU\n"
			+ "qZpDQj26F1CN8Eaf4J9Qnn1GOsFItRTcegnpKLOph51BG8pMb9V61ctsCCcOI7iv\n"
			+ "qyxSonptcrgrae4NL6zCDfhzhSnfk1lakLZJekQaMiiLFRWkiAGRt/EZ+J8Ip9yx\n"
			+ "bkhAzhq45lUCAwEAAaOBmzCBmDAJBgNVHRMEAjAAMCwGCWCGSAGG+EIBDQQfFh1P\n"
			+ "cGVuU1NMIEdlbmVyYXRlZCBDZXJ0aWZpY2F0ZTAdBgNVHQ4EFgQU3ggtIhTQXhdQ\n"
			+ "x/UTtqXpGxDXS0AwPgYDVR0jBDcwNaEopCYwJDELMAkGA1UEBhMCU0UxFTATBgNV\n"
			+ "BAMMDHNhZmV6b25lLm9yZ4IJAP/4pv4vne6VMA0GCSqGSIb3DQEBBQUAA4GBACGb\n"
			+ "nugYVC7t77VmKqwPO+ZdUpoy0ZWB32hFir8abbhwsZaJk5NSkBpKEQL5uLer92j/\n"
			+ "/LqeBYLVw0bkrwZ7Y8tSZsc9k7s4wTjB9b8HxFvZ7gocAHIGXyA6i3mE8FFfr7mg\n"
			+ "VTIKeWOk8UtHYm3q/oa7lky3aCt79dBMDyDDChCh\n" + "-----END CERTIFICATE-----\n";

	private static final String message = "Marc Robinson\n 475 REV\nEmergency Services Madagascar\n nostrike.org\n";
	private static final byte[] signature = SignUtils.sign(message, PRIVATE_KEY);

	public static Response createResponse(Challenge challenge) {
		byte[] challengeSignature = SignUtils.sign(challenge.getRandomString(), PRIVATE_KEY);
		return new Response(challengeSignature, message, signature, PUBLIC_CERTIFICATE);
	}

	public static String getMessage() {
		return message;
	}
}
