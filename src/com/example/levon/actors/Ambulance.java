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
	// -nocrypt
	// -out ambulance.pk8
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
			+ "+2PVaRY+uDiClFYLRrI77MRb3mSzePGmzDCa/DYrnqxNHrjmrxlhBlwQWHB2a/Os\n"
			+ "nhieSGFss/V3jA==\n" + "-----END PRIVATE KEY-----\n";

	//
	// Public certificate, signed by the trusted party, generated using:
	//
	// $ openssl req -new -key ambulance.key -out ambulance.csr
	// $ openssl x509 -req -days 30 -in ambulance.csr -signkey central.key -out
	// ambulance.crt
	//
	public static final String PUBLIC_CERTIFICATE = "-----BEGIN CERTIFICATE-----\n"
			+ "MIIBvzCCASgCCQCtvwsmvHwU+TANBgkqhkiG9w0BAQUFADAkMQswCQYDVQQGEwJT\n"
			+ "RTEVMBMGA1UEAwwMc2FmZXpvbmUub3JnMB4XDTE0MTAyNzIxMjk1MVoXDTE0MTEy\n"
			+ "NjIxMjk1MVowJDELMAkGA1UEBhMCU0UxFTATBgNVBAMMDHNhZmV6b25lLm9yZzCB\n"
			+ "nzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA73d57kU5eMZt91Sh/0gvZlafAY57\n"
			+ "MHTMmkCGEahRbTTe8A/IrXDoDHA8N/fTkcr5ECSLYT4dFar1oDYm0qX5zFWScgGP\n"
			+ "FrXS8PSJ2yzYAgTMeAySh4FBnIu4I1wRMi1WkLLx0S+KYvzfM6frQgDCpipVPyv5\n"
			+ "pxxvr909R4KVlnECAwEAATANBgkqhkiG9w0BAQUFAAOBgQCFbjLT9lg9JpaqLy9r\n"
			+ "RPdHymu8MXxZkwkNtSX0/HTwgDO22w2M0spcIAai84Qj6mzqnn17PBmkV3k0xlFQ\n"
			+ "oKznB0X6yADYhDO8T9Iv/J5MtChXiNFyF98NeWoJ03ecOTGtF3R1HX785Q1fswn5\n"
			+ "hk/2hCTPRCukorKjgrSwlXq1Xg==\n" + "-----END CERTIFICATE-----\n";

	private static final String message = "Reg Nr: ABC 123\nDriver Name: Olle Sol\nOrganization: Red Cross";
	private static final byte[] signature = SignUtils.sign(message, PRIVATE_KEY);
	
	public static Response createResponse(Challenge challenge)
	{
		byte[] challengeSignature = SignUtils.sign(challenge.getRandomString(), PRIVATE_KEY);
		return new Response(challengeSignature, message, signature, PUBLIC_CERTIFICATE);
	}
}
