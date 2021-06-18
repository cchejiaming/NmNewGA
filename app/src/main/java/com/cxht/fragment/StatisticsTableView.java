package com.cxht.fragment;

import android.os.Bundle;

import com.gongan.horizontal.scrollview.CustomListScrollManageFragment;
import com.gongan.horizontal.scrollview.bean.HeadRows;
import com.gongan.horizontal.scrollview.bean.HoriScViewTable;
import com.gongan.horizontal.scrollview.bean.RecordRows;
import com.gongan.horizontal.scrollview.util.DataUtil;

public class StatisticsTableView extends CustomListScrollManageFragment {
	private String TAG = "tableView";
	private int id;
	private int type;
	private String title;
	private HoriScViewTable table = null;
	private String inWhere;

	public StatisticsTableView() {
	}

	/**
	 * 单例模式
	 * 
	 * @param id
	 *            统计ID编号
	 * @param title
	 *            统计名称
	 * @return
	 */
	public StatisticsTableView newInstance(int id, int type, String title,
			String inWhere) {
		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		bundle.putInt("type", type);
		bundle.putString("title", title);
		bundle.putString("inWhere", inWhere);
		StatisticsTableView fragment = new StatisticsTableView();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.id = args != null ? args.getInt("id", 0) : 0;
		this.type = args != null ? args.getInt("type", 0) : 0;
		this.title = args != null ? args.getString("title") : "";
		this.inWhere = args != null ? args.getString("inWhere") : "";
		super.onCreate(savedInstanceState);

	}

	public void getDataSource() {

		table = DataUtil.getHorScViewTable(getActivity(), id, type, inWhere);

	}

	/**
	 * 父类抽象方法实现 获取数据总数
	 */
	@Override
	public int getItemsCount() {
		if (table == null) {
			getDataSource();
		}
		return table.getBodyRows().size();
	}

	/**
	 * 父类抽象方法实现 获取头部数据信息
	 */
	public HeadRows getReadHeadRows() {
		if (table == null) {
			getDataSource();
		}
		return table.getHeadRow();
	}

	/**
	 * 父类抽象方法 获取指定ID数据对象
	 */
	@Override
	public RecordRows getRecordRow(int recordIndex) {

		if (table == null) {
			getDataSource();
		}
		return table.getBodyRows().get(recordIndex);
	}

}
