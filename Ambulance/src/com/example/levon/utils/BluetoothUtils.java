package com.example.levon.utils;

import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public class BluetoothUtils {
	public static final String AMBULANCE_SERVICE_NAME = "MobileSafeZone";
	public static final UUID AMBULANCE_SERVICE_UUID = UUID.fromString("2fb33440-5a04-11e4-a33a-0002a5d5c51c");

	public static void beginDiscoverable(Activity activity) {
		Intent discoverableIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(
				BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
		activity.startActivity(discoverableIntent);
	}

	public static void endDiscoverable(Activity activity) {
		// TODO: implement
	}
}
