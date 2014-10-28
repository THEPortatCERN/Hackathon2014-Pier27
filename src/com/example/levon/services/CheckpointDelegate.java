package com.example.levon.services;

public interface CheckpointDelegate {
	void onAmbulanceDiscovered(boolean authentic, String message);
}
