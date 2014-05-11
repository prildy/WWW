package com.hackaton.activityadvisor;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.GetChars;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hackaton.activityadvisor.adapter.ActivityAdapter;
import com.hackaton.activityadvisor.model.Acts;
import com.hackaton.activityadvisor.request.GetActivityRequest;

public class ActivityList extends ListActivity implements
		GetActivityRequest.OnActivityRequestCallback {
	private static final int REQUEST_CODE_ADD = 0x1993123;
	private TextView mWhatText;
	private TextView mWhereText;
	private TextView mWhenText;
	private Spinner mSpinner;
	private View mGoButton;
	private View mSuggestionButton;
	private ArrayAdapter<String> mSpinnerAdapter;
	private Bundle mBundle;
	private String mWhat;
	private String mWhere;
	private ActivityAdapter mAdapter;

	public void onCreate(Bundle arg) {
		super.onCreate(arg);
		setContentView(R.layout.activity_list);
		mBundle = getIntent().getExtras();
		mWhatText = (TextView) findViewById(R.id.whatText);
		mWhereText = (TextView) findViewById(R.id.whereText);
		mWhenText = (TextView) findViewById(R.id.whenText);
		if (mBundle != null) {
			mWhat = mBundle.getString(MainActivity.EXTRA_WHAT);
			mWhere = mBundle.getString(MainActivity.EXTRA_WHERE);
			String when = mBundle.getString(MainActivity.EXTRA_WHEN);
			if (!TextUtils.isEmpty(mWhat)) {
				mWhatText.setText(mWhat);
			} else {
				mWhatText.setText("Anything");
			}
			if (!TextUtils.isEmpty(mWhere)) {
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
		ArrayList<Acts> values = new ArrayList<Acts>();
		// values.add(new Acts("Fun Hiking",
		// "There will be a fun hiking to merano", "Jack"));
		// values.add(new Acts("Swimming Party",
		// "There will be a swimming in the pool nearby", "Michael"));
		// values.add(new Acts("Trekking to Verano",
		// "See the alps, go trekking to knottnkino", "+3910222000"));
		// values.add(new Acts("Snowboarding Vipiteno",
		// "Feel the snow on this winter, snowboard in Vipiteno",
		// "+3910222222"));
		// values.add(new Acts("Fun Hiking",
		// "There will be a fun hiking to merano", "Jack"));
		// values.add(new Acts("Swimming Party",
		// "There will be a swimming in the pool nearby", "Michael"));
		// values.add(new Acts("Trekking to Verano",
		// "See the alps, go trekking to knottnkino", "+3910222000"));
		// values.add(new Acts("Snowboarding Vipiteno",
		// "Feel the snow on this winter, snowboard in Vipiteno",
		// "+3910222222"));
		// values.add(new Acts("Fun Hiking",
		// "There will be a fun hiking to merano", "Jack"));
		// values.add(new Acts("Swimming Party",
		// "There will be a swimming in the pool nearby", "Michael"));
		// values.add(new Acts("Trekking to Verano",
		// "See the alps, go trekking to knottnkino", "+3910222000"));
		// values.add(new Acts("Snowboarding Vipiteno",
		// "Feel the snow on this winter, snowboard in Vipiteno",
		// "+3910222222"));
		// values.add(new Acts("Fun Hiking",
		// "There will be a fun hiking to merano", "Jack"));
		// values.add(new Acts("Swimming Party",
		// "There will be a swimming in the pool nearby", "Michael"));
		// values.add(new Acts("Trekking to Verano",
		// "See the alps, go trekking to knottnkino", "+3910222000"));
		// values.add(new Acts("Snowboarding Vipiteno",
		// "Feel the snow on this winter, snowboard in Vipiteno",
		// "+3910222222"));

		View footer = getLayoutInflater().inflate(
				R.layout.list_activity_footer, null);
		mSpinner = (Spinner) footer.findViewById(R.id.similarSpinner);
		mGoButton = footer.findViewById(R.id.goSimilarButton);
		mSuggestionButton = footer.findViewById(R.id.suggestion);
		mGoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSpinner.getSelectedItemPosition() != mSpinnerAdapter
						.getCount()) {
					String selectedSimilar = (String) mSpinner
							.getItemAtPosition(mSpinner
									.getSelectedItemPosition());
					Intent activity = new Intent(ActivityList.this,
							ActivityList.class);
					activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mBundle.putString(MainActivity.EXTRA_WHAT, selectedSimilar);
					activity.putExtras(mBundle);
					startActivity(activity);
					// Toast.makeText(ActivityList.this, "You selected " +
					// selectedSimilar, Toast.LENGTH_SHORT).show();
				} else {
					// hint selected, ignore this case!
				}
			}
		});
		mSuggestionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		mSpinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				View v = super.getView(position, convertView, parent);
				if (position == getCount()) {
					((TextView) v.findViewById(android.R.id.text1)).setText("");
					((TextView) v.findViewById(android.R.id.text1))
							.setHint(getItem(getCount())); // "Hint to be displayed"
				}

				return v;
			}

			@Override
			public int getCount() {
				return super.getCount() - 1; // you dont display last item. It
												// is used as hint.
			}

		};

		mSpinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayList<String> similarItems = new ArrayList<String>();
		similarItems.add("Hiking");
		similarItems.add("Jogging");
		similarItems.add("Trekking");
		similarItems.add("Running");
		similarItems.add("See similar actities...");
		mSpinnerAdapter.addAll(similarItems);
		mSpinner.setAdapter(mSpinnerAdapter);
		mSpinner.setSelection(mSpinnerAdapter.getCount()); // display hint

		getListView().addFooterView(footer);

		// footer for empty view
		View emptyView = findViewById(android.R.id.empty);
		final Spinner mSpinner2 = (Spinner) emptyView
				.findViewById(R.id.similarSpinner);
		View mGoButton2 = emptyView.findViewById(R.id.goSimilarButton);
		View mSuggestionButton2 = emptyView.findViewById(R.id.suggestion);

		mSpinner2.setAdapter(mSpinnerAdapter);
		mSpinner2.setSelection(mSpinnerAdapter.getCount()); // display hint
		mGoButton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSpinner2.getSelectedItemPosition() != mSpinnerAdapter
						.getCount()) {
					String selectedSimilar = (String) mSpinner2
							.getItemAtPosition(mSpinner2
									.getSelectedItemPosition());
					Intent activity = new Intent(ActivityList.this,
							ActivityList.class);
					activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mBundle.putString(MainActivity.EXTRA_WHAT, selectedSimilar);
					activity.putExtras(mBundle);
					startActivity(activity);
					// Toast.makeText(ActivityList.this, "You selected " +
					// selectedSimilar, Toast.LENGTH_SHORT).show();
				} else {
					// hint selected, ignore this case!
				}
			}
		});
		mSuggestionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mAdapter = new ActivityAdapter(this, values);
		setListAdapter(mAdapter);
		new GetActivityRequest(this, this, mWhat, mWhere).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_add:
			Intent addNew = new Intent(this, ActivityAddNew.class);
			addNew.putExtras(mBundle);
			startActivityForResult(addNew, REQUEST_CODE_ADD);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void callback(ArrayList<Acts> activities) {
		System.out.println("on callback get act request");
		for (Acts ac : activities) {
			System.out.println(ac.getTitle() + " " + ac.getDesc() + " "
					+ ac.getUser());
		}
		ArrayList<Acts> currentAct = mAdapter.getActs();
		currentAct.clear();
		currentAct.addAll(activities);
		mAdapter.notifyDataSetChanged();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE_ADD) {
			if (resultCode == RESULT_OK) {
				finish();
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}
}
