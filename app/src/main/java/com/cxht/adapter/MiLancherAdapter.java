package com.cxht.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cxht.entity.NaviClass;
import com.gongan.manage.R;

public class MiLancherAdapter extends BaseAdapter{
	private LayoutInflater inflater;
    private Context context;
    private List<NaviClass> list;
	public MiLancherAdapter(Context context,List<NaviClass> list){
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
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
	public void exchange(int startPosition, int endPosition) {
		Object endObject = getItem(endPosition);
		Object startObject = getItem(startPosition);
		list.add(startPosition, (NaviClass) endObject);
		list.remove(startPosition + 1);
		list.add(endPosition, (NaviClass) startObject);
		list.remove(endPosition + 1);
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder;
		if (view == null){
			view = inflater.inflate(R.layout.mi_laucher_item, null);
			viewHolder = new ViewHolder();
			viewHolder.obj = (TextView)view.findViewById(R.id.txt_userAge);
			// 设置tag
			view.setTag(viewHolder);
		}else{
			// 从convertView中得到我们的viewHolder
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.obj.setText(list.get(position).getTitle());
		setTextViewTopImage(viewHolder.obj,list.get(position).getImage());
		return view;
	}
	/**
	 * 设置图标。
	 */
	private void setTextViewTopImage(TextView tv, int id) {
		Drawable img_off;
		Resources res = context.getResources();
		img_off = res.getDrawable(id);
		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		img_off.setBounds(0, 0, img_off.getMinimumWidth(),
				img_off.getMinimumHeight());
		tv.setCompoundDrawables(null, img_off, null, null); // 设置上图标

	}
	private class ViewHolder{
		public TextView obj;
	}

}
