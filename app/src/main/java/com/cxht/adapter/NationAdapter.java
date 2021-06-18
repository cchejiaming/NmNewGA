package com.cxht.adapter;

import java.util.List;

import com.cxht.bean.CheckBean;
import com.gongan.manage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class NationAdapter extends BaseAdapter {
	private Context mContext;
	private List<CheckBean> mList;
	private LayoutInflater mInflater;

	public NationAdapter(Context context, List<CheckBean> list) {
		mContext = context;
		mList = list;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		  if (convertView == null) {  
	            convertView = mInflater.inflate(R.layout.dialog_nation_item, parent, false);  
	        }  
	        TextView tvName = (TextView) convertView.findViewById(  
	                R.id.dispatch_item_select_user_name);  
	        final CheckBox ckbItem = (CheckBox) convertView.findViewById(  
	                R.id.dispatch_item_select_user_ckb);  
	        CheckBean nation = mList.get(position);  
	        tvName.setText(nation.getName());  
	      
	        ckbItem.setChecked(nation.isChecked());  
	        ckbItem.setOnClickListener(new View.OnClickListener() {  
	              
	            @Override  
	            public void onClick(View v) {  
	                mList.get(position).setChecked(ckbItem.isChecked());//保存checkbox状态至位置对应的列表对象Person中  
	                  
	            }  
	        });  
	        nation = null;  
	        return convertView;  
	}

}
