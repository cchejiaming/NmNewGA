package com.cxht.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cxht.entity.Group;
import com.gongan.manage.R;
/**
 * 收藏数据适配器
 * @author Administrator
 *
 */
public class FavoriteAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private List<Group> data;
	private Context context;
	public FavoriteAdapter(Context context,List<Group> data) {
		inflater = LayoutInflater.from(context);
		this.data =data;
		this.context = context;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//Log.d("InfoAdapter1.java", "Code goes here...");
		InfoHolder viewHolder;
		if (view == null) {
			view = inflater.inflate(R.layout.statistics_item, null);
			viewHolder = new InfoHolder();
			viewHolder.title = (TextView) view.findViewById(R.id.statistics_title);
		
			// 设置tag
			view.setTag(viewHolder);
		} else {

			// 从convertView中得到我们的viewHolder
			viewHolder = (InfoHolder) view.getTag();
		}
		Group item = data.get(position);
		
		viewHolder.title.setText(item.getGroup_name());
	   
		return view;
	}

	public class InfoHolder {
		
		public TextView title;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
}
