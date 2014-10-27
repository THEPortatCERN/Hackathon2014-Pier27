package com.example.levon.services;

import static com.example.levon.utils.BluetoothUtils.HOSPITAL_SERVICE_NAME;
import static com.example.levon.utils.BluetoothUtils.HOSPITAL_SERVICE_UUID;

import java.io.IOException;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.example.levon.actors.TrustedParty;
import com.example.levon.utils.BluetoothUtils;
import com.example.levon.utils.SignUtils;
import com.example.levon.utils.SignedMessage;

public class HelicopterService extends Service {

	private BluetoothAdapter adapter = null;
	private HelicopterDelegate delegate = null;
	private ReceiveThread thread;

	public HelicopterService(Activity activity, LogDelegate log,
			HelicopterDelegate d) {
		super(activity, log);
		delegate = d;
	}

	private SignedMessage read(BluetoothSocket socket) throws IOException {
		try {
			ObjectInputStream i = new ObjectInputStream(socket.getInputStream());
			String message = (String) i.readObject();
			byte[] signature = (byte[]) i.readObject();
			return new SignedMessage(message, signature);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private class ReceiveThread extends Thread {
		private BluetoothServerSocket serverSocket;

		public void close() {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// Ignore exceptions
			}
		}

		public void run() {
			try {
				serverSocket = adapter
						.listenUsingInsecureRfcommWithServiceRecord(
								HOSPITAL_SERVICE_NAME, HOSPITAL_SERVICE_UUID);
				while (true) {

					log("Waiting for connection ...");
					BluetoothSocket socket = serverSocket.accept();
					log("Connected");

					final SignedMessage msg = read(socket);
					log("Message received");
					socket.close();

					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							delegate.onHospitalDiscovered(SignUtils.verify(
									msg.getMessage(), msg.getSignature(),
									TrustedParty.PUBLIC_KEY), msg.getMessage());
						}
					});
				}
			} catch (IOException e) {
				log("IOException: " + e.getMessage());
			}
		}
	}

	public void start() {
		adapter = BluetoothAdapter.getDefaultAdapter();

		if (adapter != null) {
			BluetoothUtils.beginDiscoverable(activity);
			thread = new ReceiveThread();
			thread.start();
		} else {
			log("ERROR: no bluetooth adapter found");
		}
	}

	public void stop() {
		if (adapter != null) {
			BluetoothUtils.endDiscoverable(activity);
			adapter = null;
		}
		if (thread != null) {
			thread.close();
			thread = null;
		}
	}
}
