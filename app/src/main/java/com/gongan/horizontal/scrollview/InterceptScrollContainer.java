package com.gongan.horizontal.scrollview;

import com.gongan.manage.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
 * 
 * 一个视图容器控件
 * 阻止 拦截 ontouch事件传递给其子控件
 * */
public class InterceptScrollContainer extends LinearLayout {

	public interface OnItemClickListener {

		public void OnItemClick(int x, int y); // 传递点击坐标
	}

	private OnItemClickListener listener;

	public InterceptScrollContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public InterceptScrollContainer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		try {
			listener.OnItemClick((int) ev.getX(), (int) ev.getY());

		} catch (Exception ex) {
		}

		return true;

		// return super.onInterceptTouchEvent(ev);
	}

}