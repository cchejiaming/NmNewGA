package com.cxht.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxht.tree.Node;
import com.cxht.tree.TreeListViewAdapter;
import com.gongan.manage.R;

public class GroupTreeAdapter<T> extends TreeListViewAdapter<T> {

	public GroupTreeAdapter(ListView mTree, Context context, List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel);
	}

	@Override
	public View getConvertView(Node node, int position, View convertView,
			ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.group_item1, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.item_icon);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.item_text);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (node.getIcon() == -1) {
			viewHolder.icon.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.icon.setVisibility(View.VISIBLE);
		
	         viewHolder.icon.setImageResource(node.getIcon());
		}
		if (node.isLeaf()){
			viewHolder.label.setTextSize(17);
			TextPaint tp = viewHolder.label.getPaint(); 
			tp.setFakeBoldText(false);
		}else{
			viewHolder.label.setTextSize(17);
			TextPaint tp = viewHolder.label.getPaint(); 
			tp.setFakeBoldText(true);
		}
		viewHolder.label.setText(Html.fromHtml(node.getName()));
		return convertView;
	}

	private final class ViewHolder {
		ImageView icon;
		TextView label;
	}

}
