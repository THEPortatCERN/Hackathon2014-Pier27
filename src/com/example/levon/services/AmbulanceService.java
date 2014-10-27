package com.example.levon.services;

import static com.example.levon.utils.BluetoothUtils.AMBULANCE_SERVICE_NAME;
import static com.example.levon.utils.BluetoothUtils.AMBULANCE_SERVICE_UUID;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.example.levon.utils.BluetoothUtils;
import com.example.levon.utils.Challenge;
import com.example.levon.utils.Response;

public class AmbulanceService {

	private BluetoothAdapter adapter = null;
	private Activity activity = null;

	public AmbulanceService(Activity a) {
		activity = a;
	}

	private void log(String msg) {

	}

	private Challenge read(BluetoothSocket socket) throws IOException {
		try {
			ObjectInputStream i = new ObjectInputStream(socket.getInputStream());
			String message = (String) i.readObject();
			byte[] signature = (byte[]) i.readObject();
			return new Challenge();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private void send(BluetoothSocket socket, Response response)
			throws IOException {
		ObjectOutputStream o = new ObjectOutputStream(socket.getOutputStream());
	}

	private class ReceiveThread extends Thread {
		private BluetoothServerSocket serverSocket;

		public void run() {
			try {
				serverSocket = adapter
						.listenUsingInsecureRfcommWithServiceRecord(
								AMBULANCE_SERVICE_NAME, AMBULANCE_SERVICE_UUID);
				log("Waiting for connection ...");
				BluetoothSocket socket = serverSocket.accept();
				serverSocket.close(); // TODO: continue listen for connections
				log("Connected");

				Challenge challenge = read(socket);
				log("Challenge received");

//				Response response = new Response(challenge);
//				send(socket, response);
//				log("Response sent");
			} catch (IOException e) {
				log("IOException: " + e.getMessage());
			}
		}
	}

	public void start() {
		adapter = BluetoothAdapter.getDefaultAdapter();

		if (adapter != null) {
			BluetoothUtils.beginDiscoverable(activity);
			new ReceiveThread().start();
		} else {
			log("ERROR: no bluetooth adapter found");
		}
	}

	public void stop() {
		if (adapter != null) {
			BluetoothUtils.endDiscoverable(activity);
			// TODO: stop thread listening for messages
		}
	}
}
