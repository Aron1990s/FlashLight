package cn.eoe.superflashlight;

import java.util.HashMap;
import java.util.Map;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Morse extends WarningLight {
	private final int DOT_TIME = 200; // 点停留的时间，单位：毫秒
	private final int LINE_TIME = DOT_TIME * 3; // 线停留的时间
	private final int DOT_LINE_TIME = DOT_TIME; // 点到线的时间间隔

	private final int CHAR_CHAR_TIME = DOT_TIME * 3; // 字符对哦啊字符之间的时间间隔

	private final int WORD_WORD_TIME = DOT_TIME * 7; // 单词到单词直接的时间间隔

	private String mMorseCode;

	private Map<Character, String> mMorseCodeMap = new HashMap<Character, String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mMorseCodeMap.put('a', ".-");
		mMorseCodeMap.put('b', "-...");
		mMorseCodeMap.put('c', "-.-.");
		mMorseCodeMap.put('d', "-..");
		mMorseCodeMap.put('e', ".");
		mMorseCodeMap.put('f', "..-.");
		mMorseCodeMap.put('g', "--.");
		mMorseCodeMap.put('h', "....");
		mMorseCodeMap.put('i', "..");
		mMorseCodeMap.put('j', ".---");
		mMorseCodeMap.put('k', "-.-");
		mMorseCodeMap.put('l', ".-..");
		mMorseCodeMap.put('m', "--");
		mMorseCodeMap.put('n', "-.");
		mMorseCodeMap.put('o', "---");
		mMorseCodeMap.put('p', ".--.");
		mMorseCodeMap.put('q', "--.-");
		mMorseCodeMap.put('r', ".-.");
		mMorseCodeMap.put('s', "...");
		mMorseCodeMap.put('t', "-");
		mMorseCodeMap.put('u', "..-");
		mMorseCodeMap.put('v', "...-");
		mMorseCodeMap.put('w', ".--");
		mMorseCodeMap.put('x', "-..-");
		mMorseCodeMap.put('y', "-.--");
		mMorseCodeMap.put('z', "--..");

		mMorseCodeMap.put('0', "-----");
		mMorseCodeMap.put('1', ".----");
		mMorseCodeMap.put('2', "..---");
		mMorseCodeMap.put('3', "...--");
		mMorseCodeMap.put('4', "....-");
		mMorseCodeMap.put('5', ".....");
		mMorseCodeMap.put('6', "-....");
		mMorseCodeMap.put('7', "--...");
		mMorseCodeMap.put('8', "---..");
		mMorseCodeMap.put('9', "----.");
	}

	public void onClick_SendMorseCode(View view) {
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA_FLASH)) {
			Toast.makeText(this, "当前设备没有闪光灯", Toast.LENGTH_LONG).show();
			return;
		}
		if (verifyMorseCode()) {
			sendSentense(mMorseCode);
		}
	}

	protected void sleepExt(long t) {
		try {
			Thread.sleep(t);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 发送点
	private void sendDot() {
		openFlashlight();
		sleepExt(DOT_TIME);
		closeFlashlight();
	}

	// 发送线
	private void sendLine() {
		openFlashlight();
		sleepExt(LINE_TIME);
		closeFlashlight();
	}

	// 发送字符
	private void sendChar(char c) {
		String morseCode = mMorseCodeMap.get(c);
		if (morseCode != null) {
			char lastChar = ' ';
			for (int i = 0; i < morseCode.length(); i++)

			{
				char dotLine = morseCode.charAt(i);
				if (dotLine == '.') {
					sendDot();
				} else if (dotLine == '-') {
					sendLine();
				}
				if (i > 0 && i < morseCode.length() - 1) {
					if (lastChar == '.' && dotLine == '-') {
						sleepExt(DOT_LINE_TIME);
					}
				}
				lastChar = dotLine;
			}
		}
	}

	private void sendWord(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			sendChar(c);
			if (i < s.length() - 1) {
				sleepExt(CHAR_CHAR_TIME);
			}
		}
	}

	private void sendSentense(String s) {
		String[] words = s.split(" +");
		for (int i = 0; i < words.length; i++) {
			sendWord(words[i]);
			if (i < words.length - 1) {
				sleepExt(WORD_WORD_TIME);
			}
		}
		Toast.makeText(this, "Morse Code has been sent successfully！", Toast.LENGTH_LONG).show();
	}

	private boolean verifyMorseCode() {
		mMorseCode = mEditTextMorseCode.getText().toString().toLowerCase();
		if ("".equals(mMorseCode)) {
			Toast.makeText(this, "Please input your Morse characters here！", Toast.LENGTH_LONG).show();
			return false;
		}

		for (int i = 0; i < mMorseCode.length(); i++) {
			char c = mMorseCode.charAt(i);
			if (!(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9') && c != ' ') {
				Toast.makeText(this, "Only digits and characters are allowed！", Toast.LENGTH_LONG)
						.show();
				return false;
			}
		}
		return true;
	}
}
