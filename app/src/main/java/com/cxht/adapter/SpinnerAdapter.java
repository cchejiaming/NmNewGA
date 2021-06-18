package com.cxht.adapter;

import java.util.List;

import com.gongan.manage.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class SpinnerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> list;
	public SpinnerAdapter(Context context,List<String> list) {
		inflater = LayoutInflater.from(context);
		this.list = list;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder;
		if (view == null){
			viewHolder = new ViewHolder();
			view =inflater.inflate(R.layout.spinner_text, null);
			viewHolder.titleText = (TextView)view.findViewById(R.id.descTv);
			// …Ë÷√tag
		    view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}
	    viewHolder.titleText.setText( list.get(position));	
		return view;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	public class ViewHolder {

	
	    public TextView titleText;
		
	}
}
