package com.github.crackme;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView mTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		mTextView = (TextView) findViewById(R.id.textView1);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		StringBuilder sb = new StringBuilder();
		
		sb.append("Tests\n==========\n\n");
		
		mTextView.setText(sb);
	}
}
