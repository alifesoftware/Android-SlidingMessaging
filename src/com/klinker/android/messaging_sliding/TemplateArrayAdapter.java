package com.klinker.android.messaging_sliding;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.klinker.android.messaging_donate.R;

public class TemplateArrayAdapter  extends ArrayAdapter<String> {
	  private final Activity context;
	  private final ArrayList<String> text;
	  public SharedPreferences sharedPrefs;

	  static class ViewHolder {
	    public TextView text;
	    public TextView text2;
	  }

	  public TemplateArrayAdapter(Activity context, ArrayList<String> text) {
	    super(context, R.layout.custom_template, text);
	    this.context = context;
	    this.text = text;
	    this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
	  }

	  @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	    View rowView = convertView;
	    if (rowView == null) {
	      LayoutInflater inflater = context.getLayoutInflater();
	      rowView = inflater.inflate(R.layout.custom_template, null);
	      
	      ViewHolder viewHolder = new ViewHolder();
	      viewHolder.text = (TextView) rowView.findViewById(R.id.template);
	      
	      viewHolder.text.setTextSize(Integer.parseInt(sharedPrefs.getString("text_size", "14").substring(0,2)));
	      
	      rowView.setTag(viewHolder);
	    }

	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    holder.text.setText(text.get(position));

	    return rowView;
	  }
	} 
