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
	// 页面视图
	private View view;
	// 选择的数据文本控件
	private static TextView itemText = null;
	// 文本控件宽度
	private int itemTextWidth = 0;
	// 标题列名
	protected HeadRows mHeadRows = new HeadRows();
	// 列表整体标题栏
	private LinearLayout mListHead = null;
	// 列表整体标题栏滚动条
	private MyHScrollView mHScrollView = null;
	// 列表数据listView
	private ListView listItem;
	// 是否显示第一列
	protected boolean mIsShowTextIndex = true;
	// listview的适配器
	protected ListNumScrollAdapter mAdapter;
	// 整体的根布局
	protected int mRootLayout = R.layout.layout_common_sroll;;
	// 设置是否支持长按
	protected boolean mItemCanLongClick = true;
	// 处于长按状态true，不是长按状态false
	protected boolean mLongClickStatus = false;
	// 隐藏X坐标,这里用作隐藏左侧距离
	private int curentX;
	// 根视图
	private LinearLayout root;
	// 头部样式
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
		// 延时加载UI，防止页面卡顿
		startInitView();

		return view;
	}

	/**
	 * 异步加载延时
	 */
	private void startInitView() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				initView();

			}
		}, 40);// 设置延时执行时间
	}

	/***
	 * 页面方向切换监听事件
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		itemTextWidth = 0;
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 如果为横向显示
			recreateHeadControl(); // 重新设置头部间距
			addListViewFoot(listItem, 30);

		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 如果为竖向显示
			recreateHeadControl();
			addListViewFoot(listItem);
		}
	}

	/**
	 * 添加listView底部滑动空白
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
			int basicScreenHeight = metric.heightPixels; // 屏幕高度（像素）
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
	 * 子类继承后初始化子类数据后，需要调用这个，初始化父类。
	 */
	protected void initView() {
		mListHead = (LinearLayout) view
				.findViewById(R.id.surfaceManagerListHead);
		mHScrollView = (MyHScrollView) mListHead
				.findViewById(R.id.MyHScrollView_head);
		// 数据列表ListView控件
		listItem = (ListView) view
				.findViewById(R.id.listViewCommonFeatureItems);
		// 添加底部空白区域
		addListViewFoot(listItem);
		// 设置表头的触摸滑动事件
		mHScrollView
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		// 设置列表触摸滚动事件
		listItem.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		// 列表单击监听
		listItem.setOnItemClickListener(new OnItemClickListener());
		mAdapter = new ListNumScrollAdapter(context);
		listItem.setAdapter(mAdapter);

		// 获取头部列表数据
		mHeadRows = getReadHeadRows();
		// 显示数据根目录视图加载UI
		root = (LinearLayout) view.findViewById(R.id.rootLayout);
		root.setVisibility(View.VISIBLE);
		// 设置头部间距
		recreateHeadControl();
		// 隐藏开始加载文字信息
		TextView load = (TextView) view.findViewById(R.id.loadingText);
		load.setVisibility(View.GONE);

	}

	/***
	 * 添加上部表头数据
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
		// 添加表头视图到根视图
		for (int i = 0; i < mHeadRows.getRows().size(); i++) {
			MyHeadLinearLayout layout = new MyHeadLinearLayout(context);
			setHeadParams(layout, mHeadRows.getRows().size());
			HeadItem headRows = mHeadRows.getRows().get(i);
			layout.setTitleText(headRows.getTitle());
			layout.setDescText(headRows.getDesc(), headRows.getDescIsSpe(),
					headRows.getDescGra());
			layout_items.addView(layout);
		}
		// 添加右侧空白
		setScreetWidthToRightPadding(layout_items, mHeadRows.getRows().size());

	}

	/**
	 * 计算并设置右侧空白区域
	 *
	 * @param ll
	 *            要添加空白区域控件
	 * @param c
	 *            控件数量,用于计算分界偏移量
	 */
	private void setScreetWidthToRightPadding(LinearLayout ll, int c) {
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		int basicScreenWidth = metric.widthPixels; // 屏幕宽度（像素）
		int screenWidth = basicScreenWidth - dip2px(context, 120 + c);
		ll.setPadding(0, 0, screenWidth, 0);
	}

	/**
	 * ListView 触摸滚动监听类
	 *
	 * @author hejiaming
	 *
	 */
	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
			try {
				mHScrollView.onTouchEvent(event);
				listItem.onTouchEvent(event);
				// 先改变上一次点击itemText背景颜色为取消状态背景色
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

	// 抽象接口，列名头部标题栏
	public abstract HeadRows getReadHeadRows();

	// 抽象接口，listview整体数据项
	public abstract int getItemsCount();

	/**
	 * 抽象接口，RecordRows横滚数据对象加载
	 *
	 * @param recordIndex
	 * @return RecordRows
	 */
	public abstract RecordRows getRecordRow(int recordIndex);

	// //////////////////////////////////////////////////////////
	// 需要每一项编辑或者增加,删除数据的抽象接口，请看具体情况自行添加

	/**
	 * listview适配器
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
			// 合理分配横向滚动layout中textview的大小
			TextView lisTextView[] = new TextView[nHeadItemCount];
			ViewHolder viewHolder = null;

			// 内部类ViewHolder的作用只是用来查找ID，加载布局。提升效率的
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

			// 获取横向滚动数据对象
			RecordRows rows = getRecordRow(position);
			// 填充右边
			setScreetWidthToRightPadding(viewHolder.layoutItems, nHeadItemCount);
			// 列表标题栏，序号数据装载
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
			// 向横滚数据添加点击位置X距离坐标,以便计算点击横滚项目的位置
			viewHolder.scrollContainer
					.setOnItemClickListener(new InterceptScrollContainer.OnItemClickListener() {

						@Override
						public void OnItemClick(int x, int y) {
							// TODO Auto-generated method stub
							curentX = x;

						}
					});

			// 表头列表的滚动条事件添加到listview的每一项item
			mHScrollView
					.AddOnScrollChangedListener(new OnScrollChangedListenerImp(
							viewHolder.hscrollView));

			return convertView;
		}

		/**
		 * 设置头部样式
		 *
		 * @param index
		 *            头部样式索引
		 * @param ll
		 *            样式操作控件
		 * @param leftSpx
		 *            左侧间隙控件
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
	 * listView的内部类
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
	 * ListView 单击行监听类
	 *
	 * @author hejiaming
	 *
	 */
	private class OnItemClickListener implements
			android.widget.AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 先改变上一次点击itemText背景颜色为取消状态背景色
			if (itemText != null) {
				itemText.setBackgroundColor(Color.WHITE);
			}

			int w = (int) mHScrollView.getScrollX(); // 距离左侧距离不包含隐藏部分
			// 通过计算距离左侧的距离,来找到横滚视图控件并改变背景颜色
			TextView tv = null;
			LinearLayout layout = (LinearLayout) view
					.findViewById(R.id.layoutTextItems);
			if (layout != null) {
				int left = curentX + w; // curentX 隐藏部分的左侧距离
				int index = selectItemColumn(left, itemTextWidth);
				View v = layout.getChildAt(index);
				if (v instanceof MyItemLinearLayout) {
					MyItemLinearLayout mLayout = (MyItemLinearLayout) v;
					TextView _tv = mLayout.getTextView();
					tv = _tv;
					if (tv != null && itemText != null) {
						// 如果选择2次则进入人员详细列表
						if (itemText.equals(tv)) {

							// 获取横向滚动数据对象
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

					itemText = tv; // 当前选中的itemText为下次选中做取消的对象

				}
				// 设置选择背景颜色
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
	 * 计算并设置表头间距
	 *
	 * @param layout
	 *            设置文字宽度接口
	 * @param col
	 *            横向控件数量
	 */
	private void setHeadParams(SetLinearLayoutWidth layout, int col) {
		if (col == 0)
			return;
		if (itemTextWidth == 0) {
			DisplayMetrics metric = new DisplayMetrics();
			((Activity) context).getWindowManager().getDefaultDisplay()
					.getMetrics(metric);
			int basicScreenWidth = metric.widthPixels; // 屏幕宽度（像素）
			int basicScreenHeight = metric.heightPixels; // 屏幕高度（像素）
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
	 * 设置横向滚动中layout里面textView的宽度
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
			int basicScreenWidth = metric.widthPixels; // 屏幕宽度（像素）
			int basicScreenHeight = metric.heightPixels; // 屏幕高度（像素）

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
	 * 通过横滚距离和项目itemText宽度计算点击项目Index
	 *
	 * @param left
	 *            点击距离左侧距离包含隐藏部分
	 * @param itemTextWidth
	 *            itemText宽度
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
