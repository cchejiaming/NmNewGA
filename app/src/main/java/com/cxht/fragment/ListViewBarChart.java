package com.cxht.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.gongan.manage.R;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 * 
 * @author Philipp Jahoda
 */
public class ListViewBarChart extends Fragment {
	private Context context;
	private View view;
	private ListView lv;
	private String sqlItems = null;
	private SearchParams param = null;
    
	public static ListViewBarChart newInstance(String sqlItems,SearchParams param) {
		Bundle bundle = new Bundle();
		bundle.putString("SqlItems", sqlItems);
		bundle.putSerializable("SearchParam",param);  //–Ú¡–ªØ
		ListViewBarChart fragment = new ListViewBarChart();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.sqlItems = args != null ? args.getString("SqlItems") : "";
		this.param = (SearchParams) args.getSerializable("SearchParam");
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
		ArrayList<BarData> list = new ArrayList<BarData>();

		for (int i = 0; i < column.size(); i++) {
			list.add(generateData(column.get(i)));
		}

		ChartDataAdapter cda = new ChartDataAdapter(context, list);
		lv.setAdapter(cda);
		return view;
	}

	private class ChartDataAdapter extends ArrayAdapter<BarData> {

		public ChartDataAdapter(Context context, List<BarData> objects) {
			super(context, 0, objects);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			BarData data = getItem(position);

			ViewHolder holder = null;

			if (convertView == null) {

				holder = new ViewHolder();

				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.list_item_barchart, null);
				holder.chart = (BarChart) convertView.findViewById(R.id.chart);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// apply styling

			data.setValueTextColor(Color.BLACK);
			holder.chart.setDescription("");
			holder.chart.setDrawGridBackground(false);

			XAxis xAxis = holder.chart.getXAxis();
			xAxis.setPosition(XAxisPosition.BOTTOM);

			xAxis.setDrawGridLines(false);

			YAxis leftAxis = holder.chart.getAxisLeft();

			leftAxis.setLabelCount(5, false);
			leftAxis.setSpaceTop(15f);

			YAxis rightAxis = holder.chart.getAxisRight();

			rightAxis.setLabelCount(5, false);
			rightAxis.setSpaceTop(15f);

			// set data
			holder.chart.setData(data);

			// do not forget to refresh the chart
			// holder.chart.invalidate();
			holder.chart.animateY(700, Easing.EasingOption.EaseInCubic);

			return convertView;
		}

		private class ViewHolder {

			BarChart chart;
		}
	}

	/**
	 * generates a random ChartData object with just one DataSet
	 * 
	 * @return
	 */
	private BarData generateData(StatColumnBean column) {

		ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
		ArrayList<String> xVals = new ArrayList<String>();
		List<StatResultBean> temp =null;
		 if (sqlItems!=null){
			 temp = UserDao.getDataList(column.getId(),
						sqlItems);
		 }
		 if (param !=null){
			 temp = UserDao.getDataList1(column.getId(),
						param.toWhereSql());
		 }	
		 
		if (temp != null) {

			for (int i = 0; i < temp.size(); i++) {
				yVals.add(new BarEntry((int) (temp.get(i).getCount()), i));
				xVals.add(temp.get(i).getName());
			}

		}

		BarDataSet y = new BarDataSet(yVals, column.getTitle());
		y.setBarSpacePercent(20f);
		y.setColors(ColorTemplate.JOYFUL_COLORS);
		y.setBarShadowColor(Color.rgb(203, 203, 203));
		y.setValueFormatter(new ValueFormatter() {

			@Override
			public String getFormattedValue(float value, Entry arg1, int arg2,
					ViewPortHandler arg3) {
				return String.valueOf((int) value);
			}
		});

		ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
		sets.add(y);

		BarData cd = new BarData(xVals, sets);

		return cd;
	}

}
