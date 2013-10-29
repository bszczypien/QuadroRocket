package com.example.quadrorocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class ConnectedThread extends Thread 
{
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private Handler mHandler;

        public ConnectedThread(BluetoothSocket socket, String socketType, Handler pHandler) 
        {
        	mHandler = pHandler;
            Log.d(LogTags.CONNECTION_THREAD, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) 
            {
                Log.e(LogTags.CONNECTION_THREAD, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        
        public void write(byte[] buffer)
        {
        	Log.i("DEBUG", buffer.length + " :" + mmOutStream);
	        try 
	        {
	        	mmOutStream.write(buffer);
	
	        	// Share the sent message back to the UI Activity
	        	mHandler.obtainMessage(CustomMessageHandler.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
	        } catch (IOException e) 
	        {
	        	Log.e(LogTags.CONNECTED_THREAD, "Exception during write", e);
	        }
        }

        public void run() 
        {
            Log.i(LogTags.CONNECTION_THREAD, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) 
            {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    
                    // Send the obtained bytes to the UI Activity
                    mHandler.obtainMessage(CustomMessageHandler.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) 
                {
                    Log.e(LogTags.CONNECTED_THREAD, "disconnected", e);
                    //connectionLost();
                    // Start the service over to restart listening mode
                    //BluetoothChatService.this.start();
                    break;
                }
            }
        }
}