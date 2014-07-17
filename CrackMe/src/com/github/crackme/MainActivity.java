package com.github.crackme;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
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
	
	private void runTest(StringBuilder sb, String fn) {
		sb.append(fn+": ");

		try {
			boolean rc = (boolean)this.getClass().getMethod(fn).invoke(this);
			if(rc)
					sb.append("pass");
			else	sb.append("fail");
		} catch (Exception e) {
			e.printStackTrace();
			sb.append("EXCEPTION:"+e.getLocalizedMessage());
		}

		sb.append("\n");
	}

	@Override
	protected void onResume() {
		super.onResume();
		StringBuilder sb = new StringBuilder();

		sb.append("Tests\n==========\n\n");

		runTest(sb, "checkProApp");

		mTextView.setText(sb+getApplicationContext().getPackageName());
	}

	public boolean checkProApp() {
		PackageManager pm = getPackageManager();
		String pkgMain = getApplicationContext().getPackageName();
		String pkgPro = pkgMain+".pro";
		
		try {
			pm.getPackageInfo(pkgPro, PackageManager.GET_ACTIVITIES);
		} catch (NameNotFoundException e) {
			return false;
		}
		
		return pm.checkSignatures(pkgMain, pkgPro)==PackageManager.SIGNATURE_MATCH;
	}
}
