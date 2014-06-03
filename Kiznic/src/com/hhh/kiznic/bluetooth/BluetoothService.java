package com.hhh.kiznic.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class BluetoothService {
	// Debugging
	private static final String TAG = "BluetoothService";

	// Intent request code
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	
	// Message code
	private static final int SET_CONNECT_SUCCESS = 3;
	private static final int SET_CONNECT_FAIL = 4;


	// RFCOMM Protocol
	//base main 
	//private static final UUID MY_UUID = UUID.fromString("00000003-0000-1000-8000-00805F9B34FB");
	
	// SDP_OBEXObjectPushServiceClass_UUID
	private static final UUID MY_UUID = UUID.fromString("00001105-0000-1000-8000-00805F9B34FB");
	
	private BluetoothAdapter btAdapter;

	private Activity mActivity;
	private Handler mHandler;

	private ConnectThread mConnectThread; // ������ �ٽ�
	private ConnectedThread mConnectedThread; // ������ �ٽ�

	private int mState;
	private Context mContext;

	// ���¸� ��Ÿ���� ���� ����
	private static final int STATE_NONE = 0; // we're doing nothing
	private static final int STATE_LISTEN = 1; // now listening for incoming
												// connections
	private static final int STATE_CONNECTING = 2; // now initiating an outgoing
													// connection
	private static final int STATE_CONNECTED = 3; // now connected to a remote
													// device

	// Constructors
	public BluetoothService(Activity ac, Handler h) {
		mActivity = ac;
		mHandler = h;
		mContext = ac.getApplicationContext();
		// BluetoothAdapter ���
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		mHandler = new SendMassgeHandler();
	}

	/**
	 * Check the Bluetooth support
	 * 
	 * @return boolean
	 */
	public boolean getDeviceState() {
		Log.i(TAG, "Check the Bluetooth support");

		if (btAdapter == null) {
			Log.d(TAG, "Bluetooth is not available");

			return false;

		} else {
			Log.d(TAG, "Bluetooth is available");

			return true;
		}
	}

	/**
	 * Check the enabled Bluetooth
	 */
	public void enableBluetooth() {
		Log.i(TAG, "Check the enabled Bluetooth");

		if (btAdapter.isEnabled()) {
			// ����� ������� ���°� On�� ���
			Log.d(TAG, "Bluetooth Enable Now");

			// Next Step
			scanDevice();
		} else {
			// ����� ������� ���°� Off�� ���
			Log.d(TAG, "Bluetooth Enable Request");

			Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			Log.d(TAG, "위에께 문제야?");
			mActivity.startActivityForResult(i, REQUEST_ENABLE_BT);
			Log.d(TAG, "아래가 문제야?");
		}
	}

	/**
	 * Available device search
	 */
	public void scanDevice() {
		Log.d(TAG, "Scan Device");

		Intent serverIntent = new Intent(mActivity, DeviceListActivity.class);
		mActivity.startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	/**
	 * after scanning and get device info
	 * 
	 * @param data
	 */
	public void getDeviceInfo(Intent data) {
		// Get the device MAC address
		String address = data.getExtras().getString(
				DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// Get the BluetoothDevice object
		// BluetoothDevice device = btAdapter.getRemoteDevice(address);
		BluetoothDevice device = btAdapter.getRemoteDevice(address);

		Log.d(TAG, "Get Device Info \n" + "address : " + address);

		connect(device);
	}

	// Bluetooth ���� set
	private synchronized void setState(int state) {
		Log.d(TAG, "setState() " + mState + " -> " + state);
		mState = state;
	}

	// Bluetooth ���� get
	public synchronized int getState() {
		return mState;
	}
	

	public synchronized void start() {
		Log.d(TAG, "start");

		// Cancel any thread attempting to make a connection
		if (mConnectThread == null) {

		} else {
			mConnectThread.cancel();
			mConnectThread = null;
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread == null) {

		} else {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}
	}

	// ConnectThread �ʱ�ȭ device�� ��� ���� ����
	public synchronized void connect(BluetoothDevice device) {
		Log.d(TAG, "connect to: " + device);

		// Cancel any thread attempting to make a connection
		if (mState == STATE_CONNECTING) {
			if (mConnectThread == null) {

			} else {
				mConnectThread.cancel();
				mConnectThread = null;
			}
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread == null) {

		} else {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Start the thread to connect with the given device
		mConnectThread = new ConnectThread(device);

		mConnectThread.start();
		setState(STATE_CONNECTING);
	}

	// ConnectedThread �ʱ�ȭ
	public synchronized void connected(BluetoothSocket socket,
			BluetoothDevice device) {
		Log.d(TAG, "connected");

		// Cancel the thread that completed the connection
		if (mConnectThread == null) {

		} else {
			mConnectThread.cancel();
			mConnectThread = null;
		}

		// Cancel any thread currently running a connection
		if (mConnectedThread == null) {

		} else {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		// Start the thread to manage the connection and perform transmissions
		mConnectedThread = new ConnectedThread(socket);
		mConnectedThread.start();

		setState(STATE_CONNECTED);
	}

	// ��� thread stop
	public synchronized void stop() {
		Log.d(TAG, "stop");

		if (mConnectThread != null) {
			mConnectThread.cancel();
			mConnectThread = null;
		}

		if (mConnectedThread != null) {
			mConnectedThread.cancel();
			mConnectedThread = null;
		}

		setState(STATE_NONE);
	}

	// ���� ���� �κ�(������ �κ�)
	public void write(byte[] out) { // Create temporary object
		ConnectedThread r; // Synchronize a copy of the ConnectedThread
		synchronized (this) {
			if (mState != STATE_CONNECTED)
				return;
			r = mConnectedThread;
		} // Perform the write unsynchronized r.write(out); }
	}

	// ���� ����������
	private void connectionFailed() {
		setState(STATE_LISTEN);
	}

	// ������ �Ҿ��� �� 
	private void connectionLost() {
		setState(STATE_LISTEN);

	}

	
	class SendMassgeHandler extends Handler {
        
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
             
            switch (msg.what) {
            case SET_CONNECT_SUCCESS:
            	Vibrator vibe_SUCCESS = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);         
            	//long[] pattern_success = {1000, 200, 1000, 2000, 1200};          // 진동, 무진동, 진동 무진동 숫으로 시간을 설정한다.
            	//vibe_SUCCESS.vibrate(pattern_success, 0);                                         // 패턴을 지정하고 반복횟수를 지정
            	vibe_SUCCESS.vibrate(3000);                                  
            	Toast.makeText(mContext, "아이 보호 서비스 시작", Toast.LENGTH_LONG).show();
                break;
            case SET_CONNECT_FAIL:
            	Vibrator vibe_Fail = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);         
            	//long[] pattern_fail = {1000, 200, 1000, 2000, 1200};          // 진동, 무진동, 진동 무진동 숫으로 시간을 설정한다.
            	//vibe_Fail.vibrate(pattern_fail, 0);                                         // 패턴을 지정하고 반복횟수를 지정
            	vibe_Fail.vibrate(3000);                                  
            	Toast.makeText(mContext, "아이 보호 서비스 해제", Toast.LENGTH_LONG).show();
            	break;
                
            default:
                break;
            }
        }
         
    };
	
	
	
	
	
	private class ConnectThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;

		public ConnectThread(BluetoothDevice device) {
			mmDevice = device;
			BluetoothSocket tmp = null;

			/*
			 * / // Get a BluetoothSocket to connect with the given
			 * BluetoothDevice try { // MY_UUID is the app's UUID string, also
			 * used by the server // code tmp =
			 * device.createRfcommSocketToServiceRecord(MY_UUID);
			 * 
			 * try { Method m = device.getClass().getMethod(
			 * "createInsecureRfcommSocket", new Class[] { int.class }); try {
			 * tmp = (BluetoothSocket) m.invoke(device, 15); } catch
			 * (IllegalArgumentException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (InvocationTargetException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 * 
			 * } catch (NoSuchMethodException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); } } catch (IOException e) { } /
			 */

			// ����̽� ������ �� BluetoothSocket ��
			try {
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
				Log.e(TAG, "create() failed", e);
			}
			mmSocket = tmp;
		}

		public void run() {
			Log.i(TAG, "BEGIN mConnectThread");
			setName("ConnectThread");

			// ������ �õ��ϱ� ��� �׻� ��� �˻��� �����Ѵ�.
			// ��� �˻��� ��ӵǸ� ����ӵ��� �������� �����̴�.
			btAdapter.cancelDiscovery();

			// BluetoothSocket ���� �õ�
			try {
				// BluetoothSocket ���� �õ��� ���� return ���� succes �Ǵ� exception�̴�.
				mmSocket.connect();
				
				Message msg = mHandler.obtainMessage();
                msg.what = SET_CONNECT_SUCCESS;
				mHandler.sendMessage(msg);
				
				Log.d(TAG, "Connect Success");

			} catch (IOException e) {
				connectionFailed(); // ���� ���н� �ҷ����� �޼ҵ�
				Log.d(TAG, "Connect Fail");

				// socket�� �ݴ´�.
				try {
					mmSocket.close();
				} catch (IOException e2) {
					Log.e(TAG,
							"unable to close() socket during connection failure",
							e2);
				}
				// ������? Ȥ�� ���� �������� �޼ҵ带 ȣ���Ѵ�.
				BluetoothService.this.start();
				return;
			}

			// ConnectThread Ŭ������ reset�Ѵ�.
			synchronized (BluetoothService.this) {
				mConnectThread = null;
			}

			// ConnectThread�� �����Ѵ�.
			connected(mmSocket, mmDevice);
		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

	private class ConnectedThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;

		public ConnectedThread(BluetoothSocket socket) {
			Log.d(TAG, "create ConnectedThread");
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;

			// BluetoothSocket�� inputstream �� outputstream�� ��´�.
			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException e) {
				Log.e(TAG, "temp sockets not created", e);
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}

		public void run() {
			Log.i(TAG, "BEGIN mConnectedThread");
			byte[] buffer = new byte[1024];
			int bytes;

			// Keep listening to the InputStream while connected
			while (true) {
				try {
					// InputStream���κ��� ���� �޴� �д� �κ�(���� �޴´�)
					bytes = mmInStream.read(buffer);

				} catch (IOException e) {
					Log.e(TAG, "disconnected", e);
					connectionLost();
					Message msg = mHandler.obtainMessage();
	                msg.what = SET_CONNECT_FAIL;
					mHandler.sendMessage(msg);
					break;
				}
			}
		}

		/**
		 * Write to the connected OutStream.
		 * 
		 * @param buffer
		 *            The bytes to write
		 */
		public void write(byte[] buffer) {
			try {
				// ���� ���� �κ�(���� ������)
				mmOutStream.write(buffer);

			} catch (IOException e) {
				Log.e(TAG, "Exception during write", e);
			}
		}

		public void cancel() {
			try {
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "close() of connect socket failed", e);
			}
		}
	}

}