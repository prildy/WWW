package com.hackaton.activityadvisor.adapter;

import java.util.ArrayList;

import com.hackaton.activityadvisor.R;
import com.hackaton.activityadvisor.model.Acts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ActivityAdapter extends ArrayAdapter<Acts> {
  private final Activity context;
  private final ArrayList<Acts> acts;

  static class ViewHolder {
    public TextView title;
    public TextView desc;
    public TextView user;
  }

  public ActivityAdapter(Activity context, ArrayList<Acts> acts) {
    super(context, R.layout.row_activity, acts);
    this.context = context;
    this.acts = acts;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View rowView = convertView;
    // reuse views
    if (rowView == null) {
      LayoutInflater inflater = context.getLayoutInflater();
      rowView = inflater.inflate(R.layout.row_activity, null);
      // configure view holder
      ViewHolder viewHolder = new ViewHolder();
      viewHolder.title = (TextView) rowView.findViewById(R.id.titleTextView);
      viewHolder.desc = (TextView) rowView.findViewById(R.id.descTextView);
      viewHolder.user = (TextView) rowView.findViewById(R.id.userTextView);
      rowView.setTag(viewHolder);
    }

    // fill data
    ViewHolder holder = (ViewHolder) rowView.getTag();
    Acts s = acts.get(position);
    holder.title.setText(s.getTitle());
    holder.desc.setText(s.getDesc());
    holder.user.setText(s.getUser());
    return rowView;
  }
} 
