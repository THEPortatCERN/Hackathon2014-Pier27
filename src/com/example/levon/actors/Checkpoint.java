package com.example.levon.actors;

import java.math.BigInteger;
import java.security.SecureRandom;

import android.annotation.SuppressLint;
import com.example.levon.utils.Challenge;

public class Checkpoint {

	@SuppressLint("TrulyRandom") // Ignore this warning for now
	private static final SecureRandom random = new SecureRandom();
	
	public static Challenge createChallenge() {
		String randomString = new BigInteger(260, random).toString(32);
		return new Challenge(randomString);
	}
}
