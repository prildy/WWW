package com.hackaton.activityadvisor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityAddNew extends Activity {

	private Bundle mBundle;
	private TextView mWhatText;
	private TextView mWhereText;
	private TextView mWhenText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		mBundle = getIntent().getExtras();
		mWhatText = (TextView) findViewById(R.id.whatText);
		mWhereText = (TextView) findViewById(R.id.whereText);
		mWhenText = (TextView) findViewById(R.id.whenText);
		if (mBundle != null) {
			String mWhat = mBundle.getString(MainActivity.EXTRA_WHAT);
			String mWhere = mBundle.getString(MainActivity.EXTRA_WHERE);
			String when = mBundle.getString(MainActivity.EXTRA_WHEN);
			mWhatText.setText(mWhat);
			mWhereText.setText(mWhere);
			mWhenText.setText(when);
		}
	}
	
}
