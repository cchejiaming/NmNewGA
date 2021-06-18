package com.gongan.horizontal.scrollview;

import com.gongan.horizontal.scrollview.imp.SetLinearLayoutWidth;
import com.gongan.manage.R;

import android.R.color;
import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyItemLinearLayout extends LinearLayout implements SetLinearLayoutWidth {
    private TextView itemTv; //ÏÔÊ¾ÎÄ±¾
	public MyItemLinearLayout(Context context) {
		super(context);
		   LayoutInflater mInflater =(LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	       View view = mInflater.inflate(R.layout.layout_scroll_list_text, null);
	       addView(view);
	        itemTv = (TextView) view.findViewById(R.id.itemTv);
	}
    public void setItemText(String text){
    	itemTv.setText(text);
    }
    public TextView getTextView(){
    	return itemTv;
    }
    @Override
	public void setTextWidth(int width) {
		// TODO Auto-generated method stub
		itemTv.setWidth(width);
	}
}
