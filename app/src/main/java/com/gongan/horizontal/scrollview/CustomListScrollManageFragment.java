package com.gongan.horizontal.scrollview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gongan.horizontal.scrollview.MyHScrollView.OnScrollChangedListener;
import com.gongan.horizontal.scrollview.bean.HeadItem;
import com.gongan.horizontal.scrollview.bean.HeadRows;
import com.gongan.horizontal.scrollview.bean.RecordColumn;
import com.gongan.horizontal.scrollview.bean.RecordRows;
import com.gongan.horizontal.scrollview.imp.SetLinearLayoutWidth;
import com.gongan.manage.R;
import com.gongan.manage.R.drawable;
import com.cxht.app.OrganizationMainActivity;

public abstract class CustomListScrollManageFragment extends Fragment {
	public static final String TAG = "MyFragment";
	private Context context;
	// ҳ����ͼ
	private View view;
	// ѡ��������ı��ؼ�
	private static TextView itemText = null;
	// �ı��ؼ����
	private int itemTextWidth = 0;
	// ��������
	protected HeadRows mHeadRows = new HeadRows();
	// �б����������
	private LinearLayout mListHead = null;
	// �б����������������
	private MyHScrollView mHScrollView = null;
	// �б�����listView
	private ListView listItem;
	// �Ƿ���ʾ��һ��
	protected boolean mIsShowTextIndex = true;
	// listview��������
	protected ListNumScrollAdapter mAdapter;
	// ����ĸ�����
	protected int mRootLayout = R.layout.layout_common_sroll;;
	// �����Ƿ�֧�ֳ���
	protected boolean mItemCanLongClick = true;
	// ���ڳ���״̬true�����ǳ���״̬false
	protected boolean mLongClickStatus = false;
	// ����X����,������������������
	private int curentX;
	// ����ͼ
	private LinearLayout root;
	// ͷ����ʽ
	public final static int HEAD_DEFAULT = 0;
	public final static int HEAD_CHILD_HEAD = 1;
	public final static int HEAD_CHILD = 2;
	public final static int HEAD_CHILD_FOOT = 3;

	private View footView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(mRootLayout, null);
		((Activity) context).getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ��ʱ����UI����ֹҳ�濨��
		startInitView();

		return view;
	}

	/**
	 * �첽������ʱ
	 */
	private void startInitView() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				initView();

			}
		}, 40);// ������ʱִ��ʱ��
	}

	/***
	 * ҳ�淽���л������¼�
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		itemTextWidth = 0;
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// ���Ϊ������ʾ
			recreateHeadControl(); // ��������ͷ�����
			addListViewFoot(listItem, 30);

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// ���Ϊ������ʾ
			recreateHeadControl();
			addListViewFoot(listItem);
		}
	}

	/**
	 * ���listView�ײ������հ�
	 */
	private void addListViewFoot(ListView lv) {
		addListViewFoot(lv, 0);
	}

	private void addListViewFoot(ListView lv, int h) {
		if (lv != null) {
			if (footView != null)
				listItem.removeFooterView(footView);

			DisplayMetrics metric = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metric);
			int basicScreenHeight = metric.heightPixels; // ��Ļ�߶ȣ����أ�
			int screenHeight = basicScreenHeight - dip2px(context, 200);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			footView = inflater.inflate(R.layout.layout_list_foot, null);
			TextView footTv = (TextView) footView.findViewById(R.id.footTv);
			footTv.setHeight(screenHeight + h);
			lv.addFooterView(footView, null, false);
		}
	}

	/***
	 * ����̳к��ʼ���������ݺ���Ҫ�����������ʼ�����ࡣ
	 */
	protected void initView() {
		mListHead = (LinearLayout) view
				.findViewById(R.id.surfaceManagerListHead);
		mHScrollView = (MyHScrollView) mListHead
				.findViewById(R.id.MyHScrollView_head);
		// �����б�ListView�ؼ�
		listItem = (ListView) view
				.findViewById(R.id.listViewCommonFeatureItems);
		// ��ӵײ��հ�����
		addListViewFoot(listItem);
		// ���ñ�ͷ�Ĵ��������¼�
		mHScrollView
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		// �����б��������¼�
		listItem.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		// �б�������
		listItem.setOnItemClickListener(new OnItemClickListener());
		mAdapter = new ListNumScrollAdapter(context);
		listItem.setAdapter(mAdapter);

		// ��ȡͷ���б�����
		mHeadRows = getReadHeadRows();
		// ��ʾ���ݸ�Ŀ¼��ͼ����UI
		root = (LinearLayout) view.findViewById(R.id.rootLayout);
		root.setVisibility(View.VISIBLE);
		// ����ͷ�����
		recreateHeadControl();
		// ���ؿ�ʼ����������Ϣ
		TextView load = (TextView) view.findViewById(R.id.loadingText);
		load.setVisibility(View.GONE);

	}

	/***
	 * ����ϲ���ͷ����
	 */
	protected void recreateHeadControl() {

		TextView headIndex = (TextView) mListHead.findViewById(R.id.textIndex);
		setTextParams(headIndex, mHeadRows.getRows().size());
		headIndex.setText(mHeadRows.getHeadText());
		headIndex.setVisibility(mIsShowTextIndex == true ? View.VISIBLE
				: View.GONE);

		LinearLayout layout_items = (LinearLayout) mListHead
				.findViewById(R.id.layoutTextHeadItems);
		layout_items.removeAllViews();
		// ��ӱ�ͷ��ͼ������ͼ
		for (int i = 0; i < mHeadRows.getRows().size(); i++) {
			MyHeadLinearLayout layout = new MyHeadLinearLayout(context);
			setHeadParams(layout, mHeadRows.getRows().size());
			HeadItem headRows = mHeadRows.getRows().get(i);
			layout.setTitleText(headRows.getTitle());
			layout.setDescText(headRows.getDesc(), headRows.getDescIsSpe(),
					headRows.getDescGra());
			layout_items.addView(layout);
		}
		// ����Ҳ�հ�
		setScreetWidthToRightPadding(layout_items, mHeadRows.getRows().size());

	}

	/**
	 * ���㲢�����Ҳ�հ�����
	 *
	 * @param ll
	 *            Ҫ��ӿհ�����ؼ�
	 * @param c
	 *            �ؼ�����,���ڼ���ֽ�ƫ����
	 */
	private void setScreetWidthToRightPadding(LinearLayout ll, int c) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		int basicScreenWidth = metric.widthPixels; // ��Ļ��ȣ����أ�
		int screenWidth = basicScreenWidth - dip2px(context, 120 + c);
		ll.setPadding(0, 0, screenWidth, 0);
	}

	/**
	 * ListView ��������������
	 *
	 * @author hejiaming
	 *
	 */
	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// ������ͷ �� listView�ؼ���touchʱ�������touch���¼��ַ��� ScrollView
			try {
				mHScrollView.onTouchEvent(event);
				listItem.onTouchEvent(event);
				// �ȸı���һ�ε��itemText������ɫΪȡ��״̬����ɫ
				if (itemText != null) {
					itemText.setBackgroundColor(Color.WHITE);

				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}
	}

	// ����ӿڣ�����ͷ��������
	public abstract HeadRows getReadHeadRows();

	// ����ӿڣ�listview����������
	public abstract int getItemsCount();

	/**
	 * ����ӿڣ�RecordRows������ݶ������
	 *
	 * @param recordIndex
	 * @return RecordRows
	 */
	public abstract RecordRows getRecordRow(int recordIndex);

	// //////////////////////////////////////////////////////////
	// ��Ҫÿһ��༭��������,ɾ�����ݵĳ���ӿڣ��뿴��������������

	/**
	 * listview������
	 *
	 * @author Administrator
	 *
	 */
	public class ListNumScrollAdapter extends BaseAdapter {
		private Context mContext;
		private LayoutInflater mLayoutInflater;

		public ListNumScrollAdapter(Context context) {
			this.mContext = context;
			mLayoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// Log.e(Show, String.valueOf(getItemsCount()));
			return getItemsCount();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int nHeadItemCount = mHeadRows.getRows().size();
			// �������������layout��textview�Ĵ�С
			TextView lisTextView[] = new TextView[nHeadItemCount];
			ViewHolder viewHolder = null;

			// �ڲ���ViewHolder������ֻ����������ID�����ز��֡�����Ч�ʵ�
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mLayoutInflater.inflate(
						R.layout.layout_scroll_list_item, null);
				viewHolder.hscrollView = (MyHScrollView) convertView
						.findViewById(R.id.MyHScrollView_item);
				viewHolder.headIndex = (TextView) convertView
						.findViewById(R.id.textIndex);
				viewHolder.headLayout = (LinearLayout) convertView
						.findViewById(R.id.boderLayout);
				viewHolder.headSpe = (View) convertView
						.findViewById(R.id.leftSpx);
				viewHolder.scrollContainer = (InterceptScrollContainer) convertView
						.findViewById(R.id.scroollContainter);

				viewHolder.layoutItems = (LinearLayout) viewHolder.hscrollView
						.findViewById(R.id.layoutTextItems);
				viewHolder.layoutItems.removeAllViews();
				for (int i = 0; i < nHeadItemCount; i++) {
					MyItemLinearLayout item = new MyItemLinearLayout(context);
					setHeadParams(item, nHeadItemCount);
					viewHolder.layoutItems.addView(item);
					lisTextView[i] = item.getTextView();

				}
				convertView.setTag(viewHolder);
			} else {

				viewHolder = (ViewHolder) convertView.getTag();
				for (int i = 0; i < nHeadItemCount; i++) {
					MyItemLinearLayout item = (MyItemLinearLayout) viewHolder.layoutItems
							.getChildAt(i);
					setHeadParams(item, nHeadItemCount);
					lisTextView[i] = item.getTextView();
				}
			}

			// ��ȡ����������ݶ���
			RecordRows rows = getRecordRow(position);
			// ����ұ�
			setScreetWidthToRightPadding(viewHolder.layoutItems, nHeadItemCount);
			// �б���������������װ��
			setTextParams(viewHolder.headIndex, nHeadItemCount);
			viewHolder.headIndex.setText(rows.getHeadText());
			viewHolder.headIndex
					.setVisibility(mIsShowTextIndex == true ? View.VISIBLE
							: View.GONE);
			setHeadViewStyle(rows.getHeadStyle(), viewHolder.headLayout,
					viewHolder.headSpe);

			for (int i = 0; i < nHeadItemCount; i++) {
				String value = rows.getRows().get(i).getValue();
				lisTextView[i].setText(value);
			}
			// ����������ӵ��λ��X��������,�Ա�����������Ŀ��λ��
			viewHolder.scrollContainer
					.setOnItemClickListener(new InterceptScrollContainer.OnItemClickListener() {

						@Override
						public void OnItemClick(int x, int y) {
							// TODO Auto-generated method stub
							curentX = x;

						}
					});

			// ��ͷ�б�Ĺ������¼���ӵ�listview��ÿһ��item
			mHScrollView
					.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
							viewHolder.hscrollView));

			return convertView;
		}

		/**
		 * ����ͷ����ʽ
		 *
		 * @param index
		 *            ͷ����ʽ����
		 * @param ll
		 *            ��ʽ�����ؼ�
		 * @param leftSpx
		 *            ����϶�ؼ�
		 */
		private void setHeadViewStyle(int index, LinearLayout ll, View leftSpx) {
			LayoutParams laParams = (LayoutParams) ll.getLayoutParams();
			switch (index) {
			case HEAD_DEFAULT:
				ll.setVisibility(View.VISIBLE);
				ll.setBackgroundResource(drawable.background_boder);
				laParams.width = dip2px(context, 60);
				laParams.height = LayoutParams.MATCH_PARENT;
				ll.setLayoutParams(laParams);
				leftSpx.setVisibility(View.GONE);
				break;
			case HEAD_CHILD_HEAD:
				ll.setVisibility(View.VISIBLE);
				ll.setBackgroundResource(drawable.background_boder_gr);
				laParams.width = dip2px(context, 60);
				laParams.height = LayoutParams.MATCH_PARENT;
				ll.setLayoutParams(laParams);
				leftSpx.setVisibility(View.GONE);
				break;
			case HEAD_CHILD:
				ll.setVisibility(View.VISIBLE);
				ll.setBackgroundResource(drawable.background_boder);
				laParams.width = dip2px(context, 40);
				laParams.height = LayoutParams.MATCH_PARENT;
				ll.setLayoutParams(laParams);
				leftSpx.setVisibility(View.GONE);
				break;
			case HEAD_CHILD_FOOT:
				ll.setVisibility(View.VISIBLE);
				ll.setBackgroundResource(drawable.background_boder);
				laParams.width = dip2px(context, 40);
				laParams.height = LayoutParams.MATCH_PARENT;
				ll.setLayoutParams(laParams);
				leftSpx.setVisibility(View.VISIBLE);
				break;

			}
		}

		class OnScrollChangedListenerImp implements OnScrollChangedListener {
			MyHScrollView mScrollViewArg;

			public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
				mScrollViewArg = scrollViewar;
			}

			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				mScrollViewArg.smoothScrollTo(l, t);
			}
		}
	}

	/**
	 * listView���ڲ���
	 *
	 * @author hejiaming
	 */
	static class ViewHolder {
		TextView headIndex;
		LinearLayout headLayout;
		View headSpe;
		MyHScrollView hscrollView;
		InterceptScrollContainer scrollContainer;
		LinearLayout layoutItems;

	}

	/**
	 * ListView �����м�����
	 *
	 * @author hejiaming
	 *
	 */
	private class OnItemClickListener implements
			android.widget.AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// �ȸı���һ�ε��itemText������ɫΪȡ��״̬����ɫ
			if (itemText != null) {
				itemText.setBackgroundColor(Color.WHITE);
			}

			int w = (int) mHScrollView.getScrollX(); // ���������벻�������ز���
			// ͨ������������ľ���,���ҵ������ͼ�ؼ����ı䱳����ɫ
			TextView tv = null;
			LinearLayout layout = (LinearLayout) view
					.findViewById(R.id.layoutTextItems);
			if (layout != null) {
				int left = curentX + w; // curentX ���ز��ֵ�������
				int index = selectItemColumn(left, itemTextWidth);
				View v = layout.getChildAt(index);
				if (v instanceof MyItemLinearLayout) {
					MyItemLinearLayout mLayout = (MyItemLinearLayout) v;
					TextView _tv = mLayout.getTextView();
					tv = _tv;
					if (tv != null && itemText != null) {
						// ���ѡ��2���������Ա��ϸ�б�
						if (itemText.equals(tv)) {

							// ��ȡ����������ݶ���
							RecordRows rows = getRecordRow(position);
							RecordColumn col = rows.getRows().get(index);
							String desc = rows.getHeadText()
									+ mHeadRows.getHeadText(index);
							if (!"".equals(col.getValue())
									&& !"0".equals(col.getValue())) {
								Intent intent = new Intent(context,
										OrganizationMainActivity.class);
								Bundle mBundle = new Bundle();
								String andWhere = col.getExecuteWhereSql();
								andWhere = andWhere.replace(
										"select *  from t_user  where 1=1", "");
								andWhere = andWhere.replace(
										"select *  from t_user  where", "and");
								mBundle.putSerializable("AndWhere", andWhere);
								mBundle.putString("rcDesc", desc);
								intent.putExtras(mBundle);
								startActivity(intent);
							}

						}
					}

					itemText = tv; // ��ǰѡ�е�itemTextΪ�´�ѡ����ȡ���Ķ���

				}
				// ����ѡ�񱳾���ɫ
				if (itemText != null) {
					itemText.setBackgroundColor(Color.parseColor("#00FFFF"));
				}

				// Toast.makeText(context,
				// String.valueOf(position) + " x:" + curentX + " w:" + w,
				// Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * ���㲢���ñ�ͷ���
	 *
	 * @param layout
	 *            �������ֿ�Ƚӿ�
	 * @param col
	 *            ����ؼ�����
	 */
	private void setHeadParams(SetLinearLayoutWidth layout, int col) {
		if (col == 0)
			return;
		if (itemTextWidth == 0) {
			DisplayMetrics metric = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metric);
			int basicScreenWidth = metric.widthPixels; // ��Ļ��ȣ����أ�
			int basicScreenHeight = metric.heightPixels; // ��Ļ�߶ȣ����أ�
			int screenWidth = basicScreenWidth - dip2px(context, 60) - 60;

			if (col <= 4) {
				itemTextWidth = (screenWidth / col);
				layout.setTextWidth(itemTextWidth);
			} else {
				if (screenWidth > basicScreenHeight) {
					if (col > 8) {
						itemTextWidth = (screenWidth / 8);
						layout.setTextWidth(itemTextWidth);
					} else {
						itemTextWidth = (screenWidth / col);
						layout.setTextWidth(itemTextWidth);
					}
				} else {
					itemTextWidth = (screenWidth / 4);
					layout.setTextWidth(itemTextWidth);
				}
			}
		} else {
			layout.setTextWidth(itemTextWidth);
		}

	}

	/**
	 * ���ú��������layout����textView�Ŀ��
	 *
	 * @param textView
	 * @param col
	 */
	private void setTextParams(TextView textView, int col) {
		if (col == 0)
			return;
		textView.setMaxLines(1);
		textView.setTextSize(14);
		//
		textView.setPadding(4, 0, 4, 0);
		textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		if (itemTextWidth == 0) {
			DisplayMetrics metric = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metric);
			int basicScreenWidth = metric.widthPixels; // ��Ļ��ȣ����أ�
			int basicScreenHeight = metric.heightPixels; // ��Ļ�߶ȣ����أ�

			int screenWidth = basicScreenWidth - dip2px(context, 60) - 60;
			if (col <= 4) {
				itemTextWidth = screenWidth / col;
				textView.setWidth(itemTextWidth);
			} else {
				if (screenWidth > basicScreenHeight) {
					if (col > 8) {
						itemTextWidth = screenWidth / 8;
						textView.setWidth(itemTextWidth);
					} else {
						itemTextWidth = screenWidth / col;
						textView.setWidth(itemTextWidth);
					}
				} else {
					itemTextWidth = screenWidth / 4;
					textView.setWidth(itemTextWidth);
				}
			}
		} else {
			textView.setWidth(itemTextWidth);
		}

	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/***
	 * ͨ������������ĿitemText��ȼ�������ĿIndex
	 *
	 * @param left
	 *            �������������������ز���
	 * @param itemTextWidth
	 *            itemText���
	 * @return
	 */
	public static int selectItemColumn(int left, int itemTextWidth) {
		int nub = -1;
		if (itemTextWidth > 0) {
			nub = left / itemTextWidth;
		}
		return nub;
	}
}
