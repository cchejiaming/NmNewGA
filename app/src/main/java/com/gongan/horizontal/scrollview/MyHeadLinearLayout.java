package com.gongan.horizontal.scrollview;


import com.gongan.horizontal.scrollview.imp.SetLinearLayoutWidth;
import com.gongan.manage.R;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyHeadLinearLayout extends LinearLayout implements SetLinearLayoutWidth {
    private TextView descTv; //上部标题描述
    private TextView titleTv;//标题
    private LinearLayout sepView; //上部分割线
    

	public MyHeadLinearLayout(Context context) {
       super(context);
	    LayoutInflater mInflater =(LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.layout_scroll_list_head, null);
        addView(view);
		descTv = (TextView) view.findViewById(R.id.descTv);
		titleTv =(TextView) view.findViewById(R.id.titleTv);
		sepView = (LinearLayout) view.findViewById(R.id.sepView);
	    //addView(view);
	}
    public void setDescText(String desc, boolean isSep,int gravity){
           descTv.setText(desc);
    		//是否可见分隔符
    		if (!isSep){
    			descTv.setGravity(gravity);
    			sepView.setVisibility(View.GONE);
    		}else{
    			descTv.setGravity(gravity);
    			sepView.setVisibility(View.VISIBLE);
    		}
    
    }
    public void setTitleText(String title){
    	titleTv.setText(title);
    }
    public void setTextWidth(int width){
    	
    	descTv.setWidth(width);
    	titleTv.setWidth(width);
    }
}
