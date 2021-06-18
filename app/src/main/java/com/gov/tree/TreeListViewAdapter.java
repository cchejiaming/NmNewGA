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
     * �洢���пɼ���Node 
     */  
	protected List<TreeNodeCheck> mNodes;
	protected LayoutInflater mInflater;
	 /** 
     * �洢���е�Node 
     */  
	public List<TreeNodeCheck> mAllNodes;

    /** 
     * ����Ļص��ӿ� 
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
     *            Ĭ��չ�������� 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     */  
	public TreeListViewAdapter(ListView mTree, Context context, List<TreeNodeCheck> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		mContext = context;
		/** 
         * �����е�Node�������� 
         */  
		mAllNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
		/** 
         * ���˳��ɼ���Node 
         */  
		mNodes = TreeHelper.filterVisibleNode(mAllNodes);
		mInflater = LayoutInflater.from(context);
		/** 
         * ���ýڵ���ʱ������չ���Լ��رգ����ҽ�ItemClick�¼��������⹫�� 
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
     * ��ӦListView�ĵ���¼� չ����ر�ĳ�ڵ� 
     *  
     * @param position 
     */  
	public void expandOrCollapse(int position)
	{
		TreeNodeCheck n = mNodes.get(position);

		if (n != null)// 鎺掗櫎浼犲叆鍙傛暟閿欒寮傚�?
		{
			if (!n.isLeaf())
			{
				n.setExpand(!n.isExpand());
				mNodes = TreeHelper.filterVisibleNode(mAllNodes);
				notifyDataSetChanged();// 鍒锋柊瑙嗗浘
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
		  // �����ڱ߾�  
		convertView.setPadding(node.getLevel() * 50, 3, 3, 3);
		return convertView;
	}

	public abstract View getConvertView(TreeNodeCheck node, int position,
			View convertView, ViewGroup parent);

}
