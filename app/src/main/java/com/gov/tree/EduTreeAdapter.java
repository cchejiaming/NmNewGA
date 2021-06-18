package com.gov.tree;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gongan.manage.R;

public class EduTreeAdapter extends TreeListViewAdapter {

	public EduTreeAdapter(ListView mTree, Context context,
			List<TreeNodeCheck> datas, int defaultExpandLevel)
			throws IllegalArgumentException, IllegalAccessException {

		super(mTree, context, datas, defaultExpandLevel);
	}

	@Override
	public View getConvertView(final TreeNodeCheck node, int position,
			View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.gov_tree_item_check,
					parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.item_icon);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.item_text);
			viewHolder.check = (CheckBox) convertView
					.findViewById(R.id.item_check);
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

		viewHolder.label.setText(node.getName());
		viewHolder.check.setChecked(node.isCheck());

		viewHolder.check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				node.setCheck(viewHolder.check.isChecked());
				setChildCheck(viewHolder.check.isChecked(), node.getId());
				notifyDataSetChanged();
			}
		});

		return convertView;
	}

	private void setChildCheck(boolean bl, String id) {
		for (TreeNodeCheck et : mAllNodes) {
			if (et.getpId().equals(id)) {
				et.setCheck(bl);
				setChildCheck(bl,et.getId());
			}
		}
	}

	private final class ViewHolder {
		ImageView icon;
		TextView label;
		CheckBox check;
	}

}
