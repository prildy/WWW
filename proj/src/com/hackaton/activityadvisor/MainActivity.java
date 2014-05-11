package com.hackaton.activityadvisor;

import java.text.DateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

public class MainActivity extends Activity implements DatePickerDialog.OnDateSetListener{
	private static final String ARG_YEAR = "MAIN.ARG.YEAR";
	private static final String ARG_MONTH = "MAIN.ARG.MONTH";
	private static final String ARG_DAY = "MAIN.ARG.DAY";
	public static final String EXTRA_WHAT = "MAIN.EXTRA.WHAT";
	public static final String EXTRA_WHERE = "MAIN.EXTRA.WHERE";
	public static final String EXTRA_WHEN = "MAIN.EXTRA.WHEN";
	private Calendar mDate;
	private TextView mDateTextView;
	private TextView mWhatTextView;
	private TextView mWhereTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDateTextView = (TextView) findViewById(R.id.when_button);
		mDateTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mDate == null) {
					showDatePickerDialog(0, 0, 0);
				} else {
					showDatePickerDialog(mDate.get(Calendar.YEAR),mDate.get(Calendar.MONTH),mDate.get(Calendar.DAY_OF_MONTH));
				}				
			}
		});
		mWhatTextView = (TextView) findViewById(R.id.whatEditText);
		mWhereTextView = (TextView) findViewById(R.id.whereEditText);
		
		View mGoButton = findViewById(R.id.go_button);
		mGoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent listIntent = new Intent(MainActivity.this, ActivityList.class);
				listIntent.putExtra(MainActivity.EXTRA_WHAT, mWhatTextView.getText().toString());
				listIntent.putExtra(MainActivity.EXTRA_WHERE, mWhereTextView.getText().toString());
				listIntent.putExtra(MainActivity.EXTRA_WHEN, mDateTextView.getText().toString());
		    	System.out.println("intent bundle: "+mWhatTextView.getText() + " " + mWhereTextView.getText() + " " + mDateTextView.getText());
				startActivity(listIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		return true;
	}
	
	public void showDatePickerDialog(int year, int month, int day) {
		DialogFragment newFragment = new DatePickerFragment(this);
		Bundle args = new Bundle();
		args.putInt(ARG_YEAR, year);
		args.putInt(ARG_MONTH, month);
		args.putInt(ARG_DAY, day);
		newFragment.setArguments(args);
		newFragment.show(getFragmentManager(), "datePicker");
	}
	

	public static class DatePickerFragment extends DialogFragment  {
		private OnDateSetListener listener;
		
		public DatePickerFragment(OnDateSetListener listener) {
			super();
			this.listener = listener;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			if (savedInstanceState != null) {
				year = savedInstanceState.getInt(ARG_YEAR);

				month = savedInstanceState.getInt(ARG_MONTH);

				day = savedInstanceState.getInt(ARG_DAY);
			}

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), listener, year, month, day);
		}

		
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Calendar newDate = Calendar.getInstance();
		newDate.set(year, month, day);
		mDate = newDate;
		mDateTextView.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(mDate.getTime()));
	}
}
