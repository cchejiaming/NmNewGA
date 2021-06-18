package com.cxht.adapter;

import com.cxht.config.Setting;
import com.cxht.entity.Family;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
/**
 * 家庭成员数据适配
 * @author hejiaming
 *
 */
public class FamilyAdapter extends ArrayAdapter<Family> {
	private LayoutInflater inflater;
	public FamilyAdapter(Context context) {
		super(context, 0);
		inflater = LayoutInflater.from(context);
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		//Log.d("InfoAdapter1.java", "Code goes here...");
		InfoHolder viewHolder;
		if (view == null) {
			view = inflater.inflate(R.layout.family_item, null);
			viewHolder = new InfoHolder();
			viewHolder.title = (TextView) view.findViewById(R.id.family_title);
			viewHolder.subTitle = (TextView) view.findViewById(R.id.family_sub_title);
			// 设置tag
			view.setTag(viewHolder);
		} else {

			// 从convertView中得到我们的viewHolder
			viewHolder = (InfoHolder) view.getTag();
		}
		Family item = getItem(position);
		String age ="";
		String userBirth = item.getBirth_date();
		if(!userBirth.equals("") && userBirth!=null)
		{
			String birth =  StringUtil.longDateFormat(item.getBirth_date());
			age = birth+"["
					+ StringUtil.getAge(birth, Setting.SH_LONG_DATE_FORMAT)
					+ "岁]";

		}
	
		String title = item.getTitle()+" "+item.getFamily_name()+" "
		    +age+" "+item.getPolitics_status();  
		viewHolder.title.setText(title);
	    viewHolder.subTitle.setText(item.getJob_desc());
		return view;
	}

	public class InfoHolder {
		public TextView title;
		public TextView subTitle;

	}
}
