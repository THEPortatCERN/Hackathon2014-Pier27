package com.example.levon.services;

import static com.example.levon.utils.BluetoothUtils.HOSPITAL_SERVICE_NAME;
import static com.example.levon.utils.BluetoothUtils.HOSPITAL_SERVICE_UUID;

import java.io.IOException;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

public class FighterJetService {

	private BluetoothAdapter adapter = null;
	private Activity activity = null;
	private FighterJetDelegate delegate = null;

	public FighterJetService(Activity a, FighterJetDelegate d) {
		activity = a;
		delegate = d;
	}

	private void log(String msg) {

	}

	private class ReceiveThread extends Thread {
		private BluetoothServerSocket serverSocket;

		public void run() {
			try {
				serverSocket = adapter
						.listenUsingInsecureRfcommWithServiceRecord(
								HOSPITAL_SERVICE_NAME, HOSPITAL_SERVICE_UUID);
				log("Waiting for connection ...");
				BluetoothSocket socket = serverSocket.accept();
				serverSocket.close(); // TODO: continue listen for connections
				log("Connected");

				ObjectInputStream in = new ObjectInputStream(
						socket.getInputStream());
				// TODO: sync transfer protocol
				byte[] message = in.readLine().getBytes();
				byte[] signature = in.readLine().getBytes();
				log("Message received");

				// TODO: validate message
				// TODO: post to UI thread
				delegate.onHospitalDiscovered(true, new String(message));
			} catch (IOException e) {
				log("IOException: " + e.getMessage());
			}
		}
	}

	public void start() {
		adapter = BluetoothAdapter.getDefaultAdapter();

		if (adapter != null) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
			activity.startActivity(discoverableIntent);

			new ReceiveThread().start();
		} else {
			log("ERROR: no bluetooth adapter found");
		}
	}

	public void stop() {
		// TODO: stop being discoverable
		// TODO: stop thread listening for messages
	}
}
