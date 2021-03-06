package com.gov.tree;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
/**
 * http://blog.csdn.net/lmj623565791/article/details/40212367
 * @author zhy
 *
 * @param <T>
 */
public abstract class TreeListViewAdapter extends BaseAdapter
{

	protected Context mContext;
    /** 
     * 存储所有可见的Node 
     */  
	protected List<TreeNodeCheck> mNodes;
	protected LayoutInflater mInflater;
	 /** 
     * 存储所有的Node 
     */  
	public List<TreeNodeCheck> mAllNodes;

    /** 
     * 点击的回调接口 
     */  
	private OnTreeNodeClickListener onTreeNodeClickListener;
    private OnTreeNodeLongClickListener onTreeNodeLongClickListener;
	public interface OnTreeNodeClickListener
	{
		void onClick(TreeNodeCheck node, int position);
	}
    public interface OnTreeNodeLongClickListener
    {
    	void onLongClick(TreeNodeCheck node,int position);
    }
	public void setOnTreeNodeClickListener(
			OnTreeNodeClickListener onTreeNodeClickListener)
	{
		this.onTreeNodeClickListener = onTreeNodeClickListener;
	}
	public void setOnTreeNodeLongClickListener(
			OnTreeNodeLongClickListener onTreeNodeLongClickListener)
	{
		this.onTreeNodeLongClickListener = onTreeNodeLongClickListener;
	}
	   /** 
     *  
     * @param mTree 
     * @param context 
     * @param datas 
     * @param defaultExpandLevel 
     *            默认展开几级树 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
	public TreeListViewAdapter(ListView mTree, Context context, List<TreeNodeCheck> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		mContext = context;
		/** 
         * 对所有的Node进行排序 
         */  
		mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		/** 
         * 过滤出可见的Node 
         */  
		mNodes = TreeHelper.filterVisibleNode(mAllNodes);
		mInflater = LayoutInflater.from(context);
		/** 
         * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布 
         */  
		mTree.setOnItemClickListener(new OnItemClickListener()
		{
		
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				expandOrCollapse(position);

				if (onTreeNodeClickListener != null)
				{
					onTreeNodeClickListener.onClick(mNodes.get(position),
							position);
				}
				
			}

		});
		mTree.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (onTreeNodeLongClickListener != null)
				{
					onTreeNodeLongClickListener.onLongClick(mNodes.get(position),
							position);
				}
				return true;
			}

			
			
		});
	    
	}

	/** 
     * 相应ListView的点击事件 展开或关闭某节点 
     *  
     * @param position 
     */  
	public void expandOrCollapse(int position)
	{
		TreeNodeCheck n = mNodes.get(position);

		if (n != null)// 閹烘帡娅庢导鐘插弳閸欏倹鏆熼柨娆掝嚖瀵倸鐖?
		{
			if (!n.isLeaf())
			{
				n.setExpand(!n.isExpand());
				mNodes = TreeHelper.filterVisibleNode(mAllNodes);
				notifyDataSetChanged();// 閸掗攱鏌婄憴鍡楁禈
			}
		}
		
	}


	public int getCount()
	{
		return mNodes.size();
	}

	public Object getItem(int position)
	{
		return mNodes.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		TreeNodeCheck node = mNodes.get(position);
		convertView = getConvertView(node, position, convertView, parent);
		  // 设置内边距  
		convertView.setPadding(node.getLevel() * 50, 3, 3, 3);
		return convertView;
	}

	public abstract View getConvertView(TreeNodeCheck node, int position,
			View convertView, ViewGroup parent);

}
