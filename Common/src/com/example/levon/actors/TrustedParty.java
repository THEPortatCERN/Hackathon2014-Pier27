package com.example.levon.actors;

import com.example.levon.utils.SignUtils;

public class TrustedParty {

	//
	// Private key, generated using:
	//
	// $ openssl genrsa -out central.key 1024
	// $ openssl pkcs8 -topk8 -inform pem -in central.key -outform pem -nocrypt
	// -out central.pk8
	//
	private static final String PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----\n"
			+ "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAO93ee5FOXjGbfdU\n"
			+ "of9IL2ZWnwGOezB0zJpAhhGoUW003vAPyK1w6AxwPDf305HK+RAki2E+HRWq9aA2\n"
			+ "JtKl+cxVknIBjxa10vD0idss2AIEzHgMkoeBQZyLuCNcETItVpCy8dEvimL83zOn\n"
			+ "60IAwqYqVT8r+accb6/dPUeClZZxAgMBAAECgYEA6YSkFbPR0LIIKGmStCQLfQu5\n"
			+ "cUYlYWJ053NGik+OYCVvxcBOnLv+vapxPs7gtUquyIwqk0CEEk4tyvcjssWCKlrV\n"
			+ "8w6Jd5eLkdq3e50SJaR5hm2mlqX5f0Cykjgz+ebDKbAC1cGuJaxjFzgCxtDyVJo1\n"
			+ "lc9UAN1qztNf8s4bZvUCQQD6iW3WHN2c4JfX6gbkEUESYnvwmSbhmc3Ls2gNeibj\n"
			+ "deSTeFLZVR/QHX1MKbB0Qto5IfDT3l9vvaA0SL+W1BjPAkEA9LBAIQd+qQn1xwVx\n"
			+ "xBe52bCvizAtm96Zwgi3uQldwUBIUgIjADXNP7+mptS1XWdV7qbsHvAiRIlpD/dx\n"
			+ "PpSsvwJASTYFqa7Pt7sNxCN4FgdQu52Z2Cn46a33LFJl0qJiYM+fltRvaWDtpfQo\n"
			+ "HCyGisLKYC2h4Ve3DcEc4oprqhiakwJAVL+zBn2dDhlICk3FcKCzG0YKFxBxy5nW\n"
			+ "8ZCpaapn0aZdJiJdUxZPZ7QOmZv00UwYRFeGUtTjWY+qVwBiNcWy6wJAQ2PJ6hc7\n"
			+ "bgXru5e+K2YHdmTxfYL1jy6VDdvrnRrCQJcqrkLTvur0L8SqKA3EzFiC78xxPfNC\n" + "MFEdnRyejlBknw==\n"
			+ "-----END PRIVATE KEY-----";

	//
	// Public key, extracted using:
	//
	// $ openssl rsa -in central.key -pubout
	//
	public static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n"
			+ "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDvd3nuRTl4xm33VKH/SC9mVp8B\n"
			+ "jnswdMyaQIYRqFFtNN7wD8itcOgMcDw399ORyvkQJIthPh0VqvWgNibSpfnMVZJy\n"
			+ "AY8WtdLw9InbLNgCBMx4DJKHgUGci7gjXBEyLVaQsvHRL4pi/N8zp+tCAMKmKlU/\n" + "K/mnHG+v3T1HgpWWcQIDAQAB\n"
			+ "-----END PUBLIC KEY-----\n";

	//
	// Public certificate, generated using:
	//
	// $ openssl req -new -key central.key -out central.csr
	// $ openssl x509 -req -days 365 -in central.csr -signkey central.key -out
	// central.crt
	//
	public static final String PUBLIC_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
			+ "MIIBvzCCASgCCQD/+Kb+L53ulTANBgkqhkiG9w0BAQUFADAkMQswCQYDVQQGEwJT\n"
			+ "RTEVMBMGA1UEAwwMc2FmZXpvbmUub3JnMB4XDTE0MTAyNzE2MDE0OVoXDTE1MTAy\n"
			+ "NzE2MDE0OVowJDELMAkGA1UEBhMCU0UxFTATBgNVBAMMDHNhZmV6b25lLm9yZzCB\n"
			+ "nzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA73d57kU5eMZt91Sh/0gvZlafAY57\n"
			+ "MHTMmkCGEahRbTTe8A/IrXDoDHA8N/fTkcr5ECSLYT4dFar1oDYm0qX5zFWScgGP\n"
			+ "FrXS8PSJ2yzYAgTMeAySh4FBnIu4I1wRMi1WkLLx0S+KYvzfM6frQgDCpipVPyv5\n"
			+ "pxxvr909R4KVlnECAwEAATANBgkqhkiG9w0BAQUFAAOBgQCB4rGnEF4WbkCw+mPP\n"
			+ "uMYCkvD5ZLoE2E0NfQcNYXJihkguOb75egdUbTXbMciaU0hV6yDbH+wHfyOnnGuq\n"
			+ "bZmz46rg0aLzmyfymHbA0IhrLEMeC2FKmp0KucQZVMbXhskE+5u9cRG8J06OyzLu\n" + "NhVUA8zf06jAQv6y1fx0Bv/y9g==\n"
			+ "-----END CERTIFICATE-----\n";

	public static byte[] sign(String message) {
		return SignUtils.sign(message, PRIVATE_KEY);
	}
}
