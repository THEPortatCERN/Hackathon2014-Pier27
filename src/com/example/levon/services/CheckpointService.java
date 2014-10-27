package com.example.levon.services;

import static com.example.levon.utils.BluetoothUtils.HOSPITAL_SERVICE_UUID;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.levon.actors.Checkpoint;
import com.example.levon.utils.Challenge;
import com.example.levon.utils.Response;

public class CheckpointService extends Service {

	private BluetoothAdapter adapter = null;
	private BroadcastReceiver receiver = null;

	public CheckpointService(Activity activity, LogDelegate log) {
		super(activity, log);
	}

	public void start() {
		adapter = BluetoothAdapter.getDefaultAdapter();

		if (adapter == null)
			log("ERROR: no bluetooth adapter found");
		else if (adapter.startDiscovery()) {
			log("discovering...");

			// Create a BroadcastReceiver for ACTION_FOUND
			receiver = new BroadcastReceiver() {
				public void onReceive(Context context, Intent intent) {
					String action = intent.getAction();
					if (BluetoothDevice.ACTION_FOUND.equals(action)) {
						BluetoothDevice device = intent
								.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
						log("Discovered: " + device.getName());
						new SendThread(device).start();
					}
				}
			};
			// Register the BroadcastReceiver
			IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			activity.registerReceiver(receiver, filter);
		} else {
			adapter = null;
			log("ERROR: could not start discovery");
		}
	}

	public void stop() {
		if (adapter != null) {
			activity.unregisterReceiver(receiver);
			receiver = null;
			adapter.cancelDiscovery();
			adapter = null;
		}
	}

	private class SendThread extends Thread {
		private BluetoothDevice device;

		public SendThread(BluetoothDevice device) {
			this.device = device;
		}

		private void send(BluetoothSocket socket, Challenge challenge)
				throws IOException {
			ObjectOutputStream o = new ObjectOutputStream(
					socket.getOutputStream());
			o.writeObject(challenge.getRandomString());
			o.close();
		}

		private Response read(BluetoothSocket socket) throws IOException {
			try {
				ObjectInputStream i = new ObjectInputStream(
						socket.getInputStream());
				byte[] challengeSignature = (byte[]) i.readObject();
				String message = (String) i.readObject();
				byte[] signature = (byte[]) i.readObject();
				String certificate = (String) i.readObject();
				return new Response(challengeSignature, message, signature,
						certificate);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		private boolean validate(Challenge challenge, Response response) {
			return true;
		}

		public void run() {
			try {
				BluetoothSocket socket = device
						.createInsecureRfcommSocketToServiceRecord(HOSPITAL_SERVICE_UUID);

				if (socket != null) {
					socket.connect();
					log("Connected to " + device.getName());

					Challenge challenge = Checkpoint.createChallenge();
					send(socket, challenge);
					log("Challenge sent to " + device.getName());

					Response response = read(socket);
					log("Response received from " + device.getName());

					socket.close();
					validate(challenge, response);
				}
			} catch (IOException e) {
				log("IOException: " + e.getMessage());
			}
		}
	}
}
