package com.example.quadrorocket;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/*
 * 
 * */
public class ConnectThread extends Thread 
{
        private BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;
        private BluetoothAdapter mAdapter;
        private BluetoothController _bController;
        
        private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
        private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

        public ConnectThread(BluetoothDevice device, boolean secure, BluetoothController pBController) 
        {
        	secure = false;
        	_bController = pBController;
        	mAdapter = BluetoothAdapter.getDefaultAdapter();
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";
            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            
            UUID lUuid = mmDevice.getUuids()[0].getUuid();
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(
                    		lUuid);
                } else {
                    tmp = device.createInsecureRfcommSocketToServiceRecord(
                    		lUuid);
                }
                pBController.showError("connection's been succesfully created!");
            } catch (IOException e) {
                pBController.showError("error -> func createRfcommSocketToServiceRecord() failed");
            }
            mmSocket = tmp;
        }

        /*
         * run - function that is performed in this thread
         * */
        public void run() 
        {
            Log.i(LogTags.CONNECTION_THREAD, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();
            
            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try 
                {
                    mmSocket.close();
                    
                } catch (IOException e2) 
                {
                	Log.e("RETURN", "A: " + mAdapter + " : " + mmSocket);
                    Log.e(LogTags.CONNECTION_THREAD, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                    _bController.showError("error -> connection using socket failed");
                }
                mmSocket = null;
                Log.e("RETURN", e.getMessage());
                return;
            }
            
            _bController.startListening(mmSocket, mSocketType);
            //_bController.showError("socket is opened...");
            //connected(mmSocket, mmDevice, mSocketType);
        }
}