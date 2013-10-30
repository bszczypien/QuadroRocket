package com.example.quadrorocket;

import BFramework.BEvent;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/*
 * author: Bartosz Szczypieñ
 * it's just start point, general flow:
 * 
 * android device tries to connect to the bluetooth module on quadrocopter
 * 		MainActivity should have clear UI with choice of options
 * 		at this point MainActivity should decide which Activity should be called as the next one (according to user choice)
 * 		at this moment the choice should looks like:
 * 			-Simulator (GLESCanvas - start point)
 * 			-RealApplication (with a real and-device to quadrocopter (bluetooth module) connection, not yet created)
 * 			-AdditionalConsole to communicate with bluetooth on quadrocopter (with capabilities to insert special commands, it should help to improve debugging)
 * */
public class MainActivity extends Activity 
{
	private BluetoothController _bluetoothController;
	
	//codes to distinguish results
	public static final int REQUEST_ENABLE_BT = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView lTextView = (TextView)findViewById(R.id.textView1);
		lTextView.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		_bluetoothController = new BluetoothController(this);
	}
	
	@Override     
	   public void onDestroy() {   
	  super.onDestroy();
	   }
	
	@Override protected void onStart() 
	{
		super.onStart();
		_bluetoothController.onCustomStart();
	}
	
	/*
	 * turn on bluetooth on mobile phone
	 * */
	public void intentRequestEnableBT()
	{
		Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    startActivityForResult(enableIntent, MainActivity.REQUEST_ENABLE_BT);
	}
	
	/*
	 * after startActivity is done, it's performed as a feedback with requestCode
	 * it is simple to check if intent is performed properly or if it failed
	 * finish() - this function called from activity performs onActivityResult
	 * */
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
        switch (requestCode) 
        {
	        case REQUEST_ENABLE_BT:
	            // When the request to enable Bluetooth returns
	            if (resultCode == Activity.RESULT_OK) {
	                // Bluetooth is now enabled, so set up a chat session
	            	_bluetoothController.setupChat();
	            } else {
	                // User did not enable Bluetooth or an error occurred
	                //Log.d(TAG, "BT not enabled");
	                //Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
	                //finish();
	            }
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*
	 * send message to message box as READ MODE
	 * */
	public void addMessageToMessageBox(String pMessage)
	{
		pMessage = "BT: " + pMessage;
		TextView lTextView = (TextView)findViewById(R.id.textView1);
		String lText = pMessage;
		String lFinalText = trimLastLine((String)lTextView.getText(), lText);//(String) lTextView.getText();
		lTextView.setText(lFinalText);
	}
	
	public void addInfoToMessageBox(String pMessage)
	{
		pMessage = "INFO: " + pMessage;
		TextView lTextView = (TextView)findViewById(R.id.textView1);
		String lText = pMessage;
		String lFinalText = trimLastLine((String)lTextView.getText(), lText);//(String) lTextView.getText();
		lTextView.setText(lFinalText);
	}
	
	/*
	 * send message to message box as WRITE MODE
	 * it is assigned to "send" button
	 * */
	public void SendMessage(View pView)
	{
		TextView lTextView = (TextView)findViewById(R.id.textView1);
		EditText lEditText = (EditText)findViewById(R.id.editText1);
		//lEditView.getText() + "\n";
		String lText = "ME: " + lEditText.getText().toString();
		String lFinalText = trimLastLine((String)lTextView.getText(), lText);//(String) lTextView.getText();
		lTextView.setText(lFinalText);
		Log.i("DEBUG", lText);
		_bluetoothController.write(lEditText.getText().toString());
		lEditText.setText("");
	}
	
	private String trimLastLine(String pText, String pAppendText)
	{
		int lIndex = pText.indexOf("\n") + 1;
		return pText.substring(lIndex) + pAppendText + "\n";
	}
	
}
