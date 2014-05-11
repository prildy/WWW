package com.hackaton.activityadvisor;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hackaton.activityadvisor.request.AddActivityRequest;

public class ActivityAddNew extends Activity implements AddActivityRequest.OnActivityRequestCallback, OnClickListener {

	private Bundle mBundle;
	private TextView mWhatText;
	private TextView mWhereText;
	private TextView mWhenText;
	private View mSaveButton;
	private EditText mTitleEditText;
	private EditText mDescBox;
	private String mWhat;
	private String mWhere;
	private String when;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		mBundle = getIntent().getExtras();
		mWhatText = (TextView) findViewById(R.id.whatText);
		mWhereText = (TextView) findViewById(R.id.whereText);
		mWhenText = (TextView) findViewById(R.id.whenText);
		mSaveButton = (View) findViewById(R.id.save_button);
		mTitleEditText = (EditText) findViewById(R.id.titleEditText);
		mDescBox = (EditText) findViewById(R.id.descBox);
		if (mBundle != null) {
			mWhat = mBundle.getString(MainActivity.EXTRA_WHAT);
			mWhere = mBundle.getString(MainActivity.EXTRA_WHERE);
			when = mBundle.getString(MainActivity.EXTRA_WHEN);
			if (!TextUtils.isEmpty(mWhat)) {
				mWhatText.setText(mWhat);
			} else {
				mWhatText.setText("Anything");
			}
			if (!TextUtils.isEmpty(mWhat)) {
				mWhereText.setText(mWhere);
			} else {
				mWhereText.setText("Anywhere");
			}
			if (!TextUtils.isEmpty(when)) {
				mWhenText.setText(when);
			} else {
				mWhenText.setText("Anytime");
			}
		}
		mSaveButton.setOnClickListener(this);
		
	}

	@Override
	public void callback(String error) {
		if (error != null) {
			Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Succesfully create activity", Toast.LENGTH_SHORT).show();
			setResult(RESULT_OK);
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		String title = mTitleEditText.getText().toString();
		String desc = mDescBox.getText().toString();
		if (TextUtils.isEmpty(title)) {
			mTitleEditText.setError("please specify title");
		}
		else if (TextUtils.isEmpty(desc)) {
			mDescBox.setError("please give description");
		}
		else {
			new AddActivityRequest(this, this, mWhat, mWhere, title, desc).execute();
		}		
	}
	
}
