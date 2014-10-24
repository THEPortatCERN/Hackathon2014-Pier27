package com.example.levon.actors;

import com.example.levon.utils.SignUtils;
import com.example.levon.utils.SignedMessage;

public class FakeHospital {
	private static final String message = "";

	//
	// Fake private key, generated using:
	//
	// $ openssl genrsa -out fake_key.pem 1024
	//
	private static final String PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n"
			+ "MIICWwIBAAKBgQDAeZyorYQaGKZzFO1Y6WIoGqoBqCCiXspjhxew9H3+cCKtff6k\n"
			+ "7zkZuqkrKvqvR8HYMthwDQahpBrZIefSz9F42ROYNgGTZj1zJm2L3jZybX2oqCcW\n"
			+ "3ojVCwjMfKASSPZXBWcWy5MUHuyh8hYFuzcDXGFIiplv1mF9S6Bp72Bv0wIDAQAB\n"
			+ "AoGAUoMP7KPOEN3mZUlgYpBSWvzrSqCHtPPL8ZTVTC/T34K06wkrZAuFwAjsQqw/\n"
			+ "IWsyp3tMm+SJpjvSoqjNV+ttUZGc5J1yHpuXKFGL+GfPtk2uBc9rl7yZHqjl69Az\n"
			+ "/fonThyYg4jpxr24hqlpGKeZ6xdFq2SL+5cmT4GKjoBCR7kCQQDrnu2mG1ElN0cF\n"
			+ "UAVAhtCSGcz9MPRpDTtXxumK4nJrhnovhyQmYGiDkMmLsXvJ1PjRQFVOI3GDukhx\n"
			+ "eQ+unWVPAkEA0R9bsfZZmffJE32Hmh9J9/+j9mVDLZvvstU6rpsPrSHKAtYmvACV\n"
			+ "zCaZytqlsQd6+6woBQnlsSwWyxBTh7D0PQJARklsnyZ6+F0bBIywZGVTBqJHsifO\n"
			+ "YSAfWbnupQ2UEFtXS6JGuGP+CXFBjjhtsvGjH9jPGbYdAKyrO55ffTlapQJAeLV2\n"
			+ "E0pUvAgIG+T4keD/rFP2i7I1zazV9k8YeVLuAIyhc3Ozlg9saBPb7/i3bXEY7w6X\n"
			+ "ZxkoaVU+ruX0oT1mLQJAJfpyu6i1f2UcQuqCpbBRMsnhXglyexoBPxYQuGt4ohQG\n"
			+ "7I0vKuSKPD0caJpz9YzbFKNvPHsmTDY7iylZoaqUJg==\n"
			+ "-----END RSA PRIVATE KEY-----\n";

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
