package com.cxht.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cxht.bean.SearchParams;
import com.cxht.bean.StatColumnBean;
import com.cxht.bean.StatResultBean;
import com.cxht.config.Setting;
import com.cxht.dao.UserDao;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gongan.manage.R;
import com.mpchar.listitem.ChartItem;
import com.mpchar.listitem.PieChartItem;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 * 
 * @author Philipp Jahoda
 */
public class ListViewPieChart extends Fragment {
	private Context context;
	private View view;
	private ListView lv;
	private String sqlItems = null;
	private SearchParams param = null;
	private String andWhere = null;
	
	public static ListViewPieChart newInstance(String sqlItems,SearchParams param,String andWhere) {
		Bundle bundle = new Bundle();
		bundle.putString("SqlItems",sqlItems);
		bundle.putString("AndWhere", andWhere);
		bundle.putSerializable("SearchParam",param);  //序列化
		ListViewPieChart fragment = new ListViewPieChart();
		fragment.setArguments(bundle);
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.sqlItems = args.getString("SqlItems");
		this.param = (SearchParams) args.getSerializable("SearchParam");
		this.andWhere = args.getString("AndWhere"); // sql语句Where参数
	    super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(
				R.layout.fragment_listview_chart, null);
		((Activity) context).getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		lv = (ListView) view.findViewById(R.id.listView1);

		List<StatColumnBean> column = Setting.getStatisticsList();
		ArrayList<ChartItem> list = new ArrayList<ChartItem>();

		for (int i = 0; i < column.size(); i++) {
			PieData pd = generateDataPie(column.get(i));
			if(pd!= null){
				PieChartItem pi =new PieChartItem(pd, context,
						column.get(i).getTitle());
				list.add(pi);
			}
			
		}
		ChartDataAdapter cda = new ChartDataAdapter(context, list);
		lv.setAdapter(cda);
		return view;
	}


	/** adapter that supports 3 different item types */
	private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

		public ChartDataAdapter(Context context, List<ChartItem> objects) {
			super(context, 0, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getItem(position).getView(position, convertView,
					getContext());
		}

		@Override
		public int getItemViewType(int position) {
			// return the views type
			return getItem(position).getItemType();
		}

		@Override
		public int getViewTypeCount() {
			return 3; // we have 3 different item-types
		}
	}

	/**
	 * generates a random ChartData object with just one DataSet
	 * 
	 * @return
	 */
	private PieData generateDataPie(StatColumnBean column) {

 		ArrayList<Entry> yVals = new ArrayList<Entry>();
		ArrayList<String> xVals = new ArrayList<String>();
		int all = 0;
		List<StatResultBean> temp = null;
		 if (sqlItems!=null){
			 temp = UserDao.getDataList(column.getId(),
						sqlItems);
		 }
		 if (param !=null){
			 temp = UserDao.getDataList1(column.getId(),
						param.toWhereSql());
		 }	
		 if (param == null && andWhere!= null){
			 String where = " where 1=1 "+andWhere;
			 temp = UserDao.getDataList1(column.getId(),
						where);
		 }
		if (temp!=null){
			for (int i = 0; i < temp.size(); i++) {
				yVals.add(new Entry((int) (temp.get(i).getCount()), i));
				xVals.add(temp.get(i).getName()+"("+temp.get(i).getCount()+")");
				all+=temp.get(i).getCount();
			}
		}
	
        column.setTitle(column.getTitle()+"("+all+")");
		PieDataSet d = new PieDataSet(yVals, "");

		// space between slices
		d.setSliceSpace(2f);
		d.setColors(ColorTemplate.COLORFUL_COLORS);
	
	
		PieData cd = new PieData(xVals, d);
		return cd;
	}

}
