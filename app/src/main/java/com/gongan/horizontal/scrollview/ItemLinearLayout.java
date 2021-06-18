package com.gongan.horizontal.scrollview;

import com.gongan.manage.R;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemLinearLayout extends LinearLayout {
	private LayoutInflater mInflater;
	private TextView descTv;
	private View top, bottom, left, right;
	private View view;

	public ItemLinearLayout(Context context) {
		super(context);
		mInflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		view = mInflater.inflate(R.layout.layout_scroll_item_head, null);
		addView(view);
		initView(view);
	}

	private void initView(View v) {
		descTv = (TextView) v.findViewById(R.id.descTv);
		top = v.findViewById(R.id.top_v);
		bottom = v.findViewById(R.id.bottom_v);
		left = v.findViewById(R.id.left_v);
		right = v.findViewById(R.id.right_v);
	}

	public void setDescText(String desc, int gra) {
		descTv.setText(desc);
		descTv.setGravity(gra);
	}

	public void setTextWidth(int width) {
		descTv.setWidth(width);
	}
	
    public void setFrameStyle(boolean t, boolean b, boolean l, boolean r) {
		if (t) {
			top.setVisibility(View.VISIBLE);
		} else {
			top.setVisibility(View.GONE);
		}
		if (b) {
			bottom.setVisibility(View.VISIBLE);
		} else {
			bottom.setVisibility(View.GONE);
		}
		if (l) {
			left.setVisibility(View.VISIBLE);
		} else {
			left.setVisibility(View.GONE);
		}
		if (r) {
			right.setVisibility(View.VISIBLE);
		} else {
			right.setVisibility(View.GONE);
		}

	}
}
