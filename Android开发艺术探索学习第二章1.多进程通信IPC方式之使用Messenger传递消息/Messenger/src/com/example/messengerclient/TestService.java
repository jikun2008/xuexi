package com.example.messengerclient;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class TestService extends Service {
	private static final String TAG = TestService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Messenger messenger = new Messenger(new MessengerHandler());
		return messenger.getBinder();
	}

	private static class MessengerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				Log.d(TAG, TAG + ":" + msg.getData().getString("request"));
				Messenger client = msg.replyTo;
				Message message = Message.obtain(null, 1);
				Bundle bundle = new Bundle();
				bundle.putString("rely", "收到你的信息：客户端");
				message.setData(bundle);

				try {
					client.send(message);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	}

}
