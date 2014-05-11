package com.hackaton.activityadvisor;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hackaton.activityadvisor.adapter.ActivityAdapter;
import com.hackaton.activityadvisor.model.Acts;

public class ActivityList extends ListActivity {
	private TextView mWhatText;
	private TextView mWhereText;
	private TextView mWhenText;
	private Spinner mSpinner;
	private View mGoButton;
	private View mSuggestionButton;
	private ArrayAdapter<String> mSpinnerAdapter;
	private Bundle mBundle;

	public void onCreate(Bundle arg) {
	    super.onCreate(arg);
	    setContentView(R.layout.activity_list);
	    mBundle = getIntent().getExtras();
	    mWhatText = (TextView) findViewById(R.id.whatText);
	    mWhereText = (TextView) findViewById(R.id.whereText);
	    mWhenText = (TextView) findViewById(R.id.whenText);
	    if (mBundle != null) {
	    	String what = mBundle.getString(MainActivity.EXTRA_WHAT);
	    	String where = mBundle.getString(MainActivity.EXTRA_WHERE);
	    	String when = mBundle.getString(MainActivity.EXTRA_WHEN);
	    	System.out.println(what + " " + where + " " + when);
	    	mWhatText.setText(what);
	    	mWhereText.setText(where);
	    	mWhenText.setText(when);
	    }
	    ArrayList<Acts> values = new ArrayList<Acts>();
	    values.add(new Acts("Fun Hiking","There will be a fun hiking to merano","Jack"));
	    values.add(new Acts("Swimming Party","There will be a swimming in the pool nearby","Michael"));
	    values.add(new Acts("Trekking to Verano","See the alps, go trekking to knottnkino","+3910222000"));
	    values.add(new Acts("Snowboarding Vipiteno","Feel the snow on this winter, snowboard in Vipiteno","+3910222222"));
	    values.add(new Acts("Fun Hiking","There will be a fun hiking to merano","Jack"));
	    values.add(new Acts("Swimming Party","There will be a swimming in the pool nearby","Michael"));
	    values.add(new Acts("Trekking to Verano","See the alps, go trekking to knottnkino","+3910222000"));
	    values.add(new Acts("Snowboarding Vipiteno","Feel the snow on this winter, snowboard in Vipiteno","+3910222222"));
	    values.add(new Acts("Fun Hiking","There will be a fun hiking to merano","Jack"));
	    values.add(new Acts("Swimming Party","There will be a swimming in the pool nearby","Michael"));
	    values.add(new Acts("Trekking to Verano","See the alps, go trekking to knottnkino","+3910222000"));
	    values.add(new Acts("Snowboarding Vipiteno","Feel the snow on this winter, snowboard in Vipiteno","+3910222222"));
	    values.add(new Acts("Fun Hiking","There will be a fun hiking to merano","Jack"));
	    values.add(new Acts("Swimming Party","There will be a swimming in the pool nearby","Michael"));
	    values.add(new Acts("Trekking to Verano","See the alps, go trekking to knottnkino","+3910222000"));
	    values.add(new Acts("Snowboarding Vipiteno","Feel the snow on this winter, snowboard in Vipiteno","+3910222222"));
	    
	    View footer = getLayoutInflater().inflate(R.layout.list_activity_footer, null);
	    mSpinner = (Spinner)footer.findViewById(R.id.similarSpinner);
	    mGoButton = footer.findViewById(R.id.goSimilarButton);
	    mSuggestionButton = footer.findViewById(R.id.suggestion);
	    mGoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSpinner.getSelectedItemPosition() != mSpinnerAdapter.getCount()) {
					String selectedSimilar = (String) mSpinner.getItemAtPosition(mSpinner.getSelectedItemPosition());
					Intent activity = new Intent(ActivityList.this,ActivityList.class);
					activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					mBundle.putString(MainActivity.EXTRA_WHAT, selectedSimilar);
					activity.putExtras(mBundle);
					startActivity(activity);
//					Toast.makeText(ActivityList.this, "You selected " + selectedSimilar, Toast.LENGTH_SHORT).show();
				} else {
					//hint selected, ignore this case!
				}
			}
		});
	    mSuggestionButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	    mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {

	            View v = super.getView(position, convertView, parent);
	            if (position == getCount()) {
	                ((TextView)v.findViewById(android.R.id.text1)).setText("");
	                ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
	            }

	            return v;
	        }       

	        @Override
	        public int getCount() {
	            return super.getCount()-1; // you dont display last item. It is used as hint.
	        }

	    };

	    mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    ArrayList<String> similarItems = new ArrayList<String>();
	    similarItems.add("Hiking");
	    similarItems.add("Jogging");
	    similarItems.add("Trekking");
	    similarItems.add("Running");
	    similarItems.add("See similar actities...");
	    mSpinnerAdapter.addAll(similarItems);
	    mSpinner.setAdapter(mSpinnerAdapter);
	    mSpinner.setSelection(mSpinnerAdapter.getCount()); //display hint
	    
	    getListView().addFooterView(footer);
	    
	    ActivityAdapter adapter = new ActivityAdapter(this, values);
	    setListAdapter(adapter);
	  }
}
