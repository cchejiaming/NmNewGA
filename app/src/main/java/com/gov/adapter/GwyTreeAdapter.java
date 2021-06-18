package com.gov.adapter;

import com.gongan.manage.R;
import com.gwy.tree.Node;
import com.gwy.tree.Tree;
import com.gwy.tree.TreeListViewAdapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by deqiang.wang on 2018/4/6.
 */

public class GwyTreeAdapter extends TreeListViewAdapter<String> {
    public GwyTreeAdapter(ListView mTree, Context context, Tree treeNode)
    {
        super(mTree, context, treeNode);
    }
    @Override
    public View getConvertView(Node<String> node , int position, View convertView, ViewGroup parent)
    {

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.group_item1, parent, false);
            viewHolder = new ViewHolder();
        	viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.item_icon);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.item_text);
			convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (node.isLeaf())
        {
        	viewHolder.label.setTextColor(Color.BLACK);
            viewHolder.icon.setVisibility(View.INVISIBLE);
            if (node.get_childCount()>0){
            	 viewHolder.label.setTextColor(Color.RED);
            	 viewHolder.icon.setVisibility(View.VISIBLE);
            	 viewHolder.icon.setImageResource(R.drawable.tree_ec);
            }
        } else
        {
        	viewHolder.label.setTextColor(Color.RED);
            viewHolder.icon.setVisibility(View.VISIBLE);
            viewHolder.icon.setImageResource(node.isExpand()?R.drawable.tree_ex:R.drawable.tree_ec);
        }
      
      
        String str = node.getObject();
        if(str!=null) {
            viewHolder.label.setText(str);
        }else{
            viewHolder.label.setText("null");
        }
        if(node.get_id() == "0" ) convertView.setVisibility(View.GONE); else convertView.setVisibility(View.VISIBLE);
        return convertView;
    }

    private final class ViewHolder
    {
    	ImageView icon;
		TextView label;
    }


}
