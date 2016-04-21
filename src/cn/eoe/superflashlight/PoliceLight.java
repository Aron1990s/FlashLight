package cn.eoe.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.eoe.superflashlight.widget.HideTextView;

public class PoliceLight extends ColorLight {
	protected boolean mPoliceState;
	protected HideTextView mHideTextviewPoliceLight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHideTextviewPoliceLight = (HideTextView) findViewById(R.id.textview_hide_police_light);

	}

	class PoliceThread extends Thread {
		public void run() {
			mPoliceState = true;
			while(mPoliceState)
			{
				
				mHandler.sendEmptyMessage(Color.BLUE);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.BLACK);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.RED);
				sleepExt(100);
				mHandler.sendEmptyMessage(Color.BLACK);
				sleepExt(100);
				
			}
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			int color = message.what;
			mUIPoliceLight.setBackgroundColor(color);
		}
	};
}
