package cn.eoe.superflashlight;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Settings extends PoliceLight implements OnSeekBarChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSeekBarPoliceLight.setOnSeekBarChangeListener(this);
		mSeekBarWarningLight.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.seekbar_warning_light:
			mCurrentWarningLightInterval = progress + 100;

			break;
		case R.id.seekbar_police_light:
			mCurrentPoliceLightInterval = progress + 50;
			break;
		default:
			break;
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	private boolean shortcutInScreen() {
		Cursor cursor = getContentResolver()
				.query(Uri
						.parse("content://com.cyanogenmod.trebuchet.settings/favorites"),
						null,
						"intent like ?",
						new String[] { "%component=cn.eoe.superflashlight/.MainActivity%" },
						null);

		if (cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * #Intent;action=android.intent.action.MAIN;category=android.intent.category
	 * .
	 * LAUNCHER;launchFlags=0x10200000;component=cn.eoe.flashlight/.MainActivity
	 * ;end
	 */
	public void onClick_AddShortcut(View view) {
		if (!shortcutInScreen()) {
			Intent installShortcut = new Intent(
					"com.android.launcher.action.INSTALL_SHORTCUT");
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");
			Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
					R.drawable.icon);

			Intent flashlightIntent = new Intent();
			flashlightIntent.setClassName("cn.eoe.superflashlight",
					"cn.eoe.superflashlight.MainActivity");
			flashlightIntent.setAction(Intent.ACTION_MAIN);
			flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
					flashlightIntent);

			sendBroadcast(installShortcut);
			Toast.makeText(this, "已成功将快捷方式添加到桌面！", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "快捷方式已经添加到桌面上，无法再添加快捷方式!", Toast.LENGTH_LONG)
					.show();
		}
	}

	public void onClick_RemoveShortcut(View view) {
		if (shortcutInScreen()) {
			Intent uninstallShortcut = new Intent(
					"com.android.launcher.action.UNINSTALL_SHORTCUT");
			uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");

			Intent flashlightIntent = new Intent();
			flashlightIntent.setClassName("cn.eoe.superflashlight",
					"cn.eoe.superflashlight.MainActivity");

			uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
					flashlightIntent);

			flashlightIntent.setAction(Intent.ACTION_MAIN);
			flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);

			sendBroadcast(uninstallShortcut);
		} else {
			Toast.makeText(this, "没有快捷方式，无法删除", Toast.LENGTH_LONG).show();
		}
	}
}
