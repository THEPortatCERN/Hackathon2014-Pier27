package com.example.levon.test;

import com.example.levon.actors.Ambulance;
import com.example.levon.actors.Checkpoint;
import com.example.levon.actors.FakeAmbulance;
import com.example.levon.utils.Challenge;
import com.example.levon.utils.Response;
import com.example.levon.utils.SignUtils;

public class Test {
	public static void selfTest() {
		Challenge challenge = Checkpoint.createChallenge();
		Response response = null;
		
		response = Ambulance.createResponse(challenge);
		if (!SignUtils.verify(challenge.getRandomString(), response.getChallengeSignature(), response.getCertificate()))
			throw new RuntimeException("Failed to verify challenge");
		if (!SignUtils.verify(response.getMessage(), response.getSignature(), response.getCertificate()))
			throw new RuntimeException("Failed to verify message");
		if (!SignUtils.verifyCertificate(response.getCertificate()))
			throw new RuntimeException("Failed to verify certificate");
		
		response = FakeAmbulance.createFakeMessageResponse(challenge);
		if (!SignUtils.verify(challenge.getRandomString(), response.getChallengeSignature(), response.getCertificate()))
			throw new RuntimeException("Failed to verify fake challenge");
		if (SignUtils.verify(response.getMessage(), response.getSignature(), response.getCertificate()))
			throw new RuntimeException("Failed to identify fake message");
		if (!SignUtils.verifyCertificate(response.getCertificate()))
			throw new RuntimeException("Failed to verify fake message certificate");

		response = FakeAmbulance.createFakeCertificateResponse(challenge);
		if (!SignUtils.verify(challenge.getRandomString(), response.getChallengeSignature(), response.getCertificate()))
			throw new RuntimeException("Failed to verify fake challenge");
		if (!SignUtils.verify(response.getMessage(), response.getSignature(), response.getCertificate()))
			throw new RuntimeException("Failed to verify fake message");
		if (SignUtils.verifyCertificate(response.getCertificate()))
			throw new RuntimeException("Failed to identify fake message certificate");
	}
}
