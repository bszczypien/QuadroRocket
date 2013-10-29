package com.example.quadrorocket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class CustomMessageHandler extends Handler
{
	public final static int MESSAGE_STATE_CHANGE = 1; 
	public final static int STATE_CONNECTED = 2;
	public final static int STATE_CONNECTING = 3;
	public final static int STATE_LISTEN = 4;
	public final static int STATE_NONE = 5;
	public final static int MESSAGE_WRITE = 6;
	public final static int MESSAGE_READ = 7;
	public final static int MESSAGE_DEVICE_NAME = 8;
	public final static int MESSAGE_TOAST = 9;
		
	private BluetoothController _bluetoothController;
	
	public CustomMessageHandler(BluetoothController pController)
	{
		_bluetoothController = pController;
	}
	
	 @Override
     public void handleMessage(Message msg) 
	 {
		 Log.e("TEST", msg.what + " : " + msg.obj);
		 switch (msg.what)
		 {
		 case MESSAGE_READ:
		 {
			 byte[] readBuf = (byte[]) msg.obj;
             // construct a string from the valid bytes in the buffer
             String readMessage = new String(readBuf, 0, msg.arg1);
             _bluetoothController.showInChatBox(readMessage, MESSAGE_READ);
             break;
		 }
		 	/*
			case MESSAGE_READ:
			{
	             byte[] readBuf = (byte[]) msg.obj;
	             // construct a string from the valid bytes in the buffer
	             String readMessage = new String(readBuf, 0, msg.arg1);
	             _bluetoothController.showInChatBox(readMessage, MESSAGE_READ);
	             break;
			}
			*/
		 }
		 
		 
		 
		 
		 /*
     	switch (msg.what) 
     	{
         	case CustomMessageHandler.MESSAGE_STATE_CHANGE:
             if(D) Log.i(LogTags.HANDLER, "MESSAGE_STATE_CHANGE: " + msg.arg1);
             switch (msg.arg1) 
             {
	             case STATE_CONNECTED:
	                 setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
	                 mConversationArrayAdapter.clear();
	                 break;
	             case STATE_CONNECTING:
	                 setStatus(R.string.title_connecting);
	                 break;
	             case STATE_LISTEN:
	             case STATE_NONE:
	                 setStatus(R.string.title_not_connected);
	                 break;
             }
             break;
         case MESSAGE_WRITE:
             byte[] writeBuf = (byte[]) msg.obj;
             // construct a string from the buffer
             String writeMessage = new String(writeBuf);
             mConversationArrayAdapter.add("Me:  " + writeMessage);
             break;
			case MESSAGE_READ
			:
             byte[] readBuf = (byte[]) msg.obj;
             // construct a string from the valid bytes in the buffer
             String readMessage = new String(readBuf, 0, msg.arg1);
             mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
             break;
         case MESSAGE_DEVICE_NAME:
             // save the connected device's name
             mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
             Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
             break;
         case MESSAGE_TOAST:
             Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
             break;
         }
         */
	 }
}
