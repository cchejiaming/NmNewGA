package com.gongan.horizontal.scrollview;

import com.gongan.manage.R;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeadLinearLayout extends LinearLayout {
	private LayoutInflater mInflater;
	private LinearLayout root;
	private View view;
	public HeadLinearLayout(Context context) {
		super(context);
		mInflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		view = mInflater.inflate(R.layout.layout_head_item, null);
		addView(view);
		initView(view);
	}
	private void initView(View v) {
		root =(LinearLayout) v.findViewById(R.id.root_lay);
		
	}
 

}
