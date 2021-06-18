package com.cxht.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cxht.bean.StatResultBean;
import com.cxht.dao.UserDao;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.filter.Approximator;
import com.github.mikephil.charting.data.filter.Approximator.ApproximatorType;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.gongan.manage.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

public class StatisticsAnotherChart extends Fragment implements
		OnChartValueSelectedListener {

	private Context context;
	private View view;
	private BarChart mChart;
	private int id;
	private String title;

	public static StatisticsAnotherChart newInstance(int id, String title) {
		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		bundle.putString("title", title);
		StatisticsAnotherChart fragment = new StatisticsAnotherChart();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.id = args != null ? args.getInt("id", 0) : 0;
		this.title = args != null ? args.getString("title") : "";
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(R.layout.fragment_barchart,
				null);
		((Activity) context).getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mChart = (BarChart) view.findViewById(R.id.chart1);

		mChart.setDescription("");
		mChart.setDrawValueAboveBar(true);
		// if more than 60 entries are displayed in the chart, no values will be
		// drawn
		mChart.setMaxVisibleValueCount(60);

		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(false);

		mChart.setDrawBarShadow(false);
		mChart.setDrawGridBackground(false);

		XAxis xAxis = mChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setSpaceBetweenLabels(0);
		xAxis.setDrawGridLines(false);

		mChart.getAxisLeft().setDrawGridLines(false);

		// setting data

		setData();
		// add a nice and smooth animation
		mChart.animateY(2500);

		mChart.getLegend().setEnabled(false);

		return view;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		LayoutInflater.from(context).inflate(R.menu.bar, (ViewGroup) menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.actionToggleValues: {

			for (DataSet<?> set : mChart.getData().getDataSets())
				set.setDrawValues(!set.isDrawValuesEnabled());

			mChart.invalidate();
			break;
		}
		case R.id.actionToggleHighlight: {

			if (mChart.getData() != null) {
				mChart.getData().setHighlightEnabled(
						!mChart.getData().isHighlightEnabled());
				mChart.invalidate();
			}
			break;
		}
		case R.id.actionTogglePinch: {
			if (mChart.isPinchZoomEnabled())
				mChart.setPinchZoom(false);
			else
				mChart.setPinchZoom(true);

			mChart.invalidate();
			break;
		}
		case R.id.actionToggleAutoScaleMinMax: {
			mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
			mChart.notifyDataSetChanged();
			break;
		}
		case R.id.actionToggleHighlightArrow: {
			if (mChart.isDrawHighlightArrowEnabled())
				mChart.setDrawHighlightArrow(false);
			else
				mChart.setDrawHighlightArrow(true);
			mChart.invalidate();
			break;
		}
		case R.id.actionToggleStartzero: {

			mChart.getAxisLeft().setStartAtZero(
					!mChart.getAxisLeft().isStartAtZeroEnabled());
			mChart.getAxisRight().setStartAtZero(
					!mChart.getAxisRight().isStartAtZeroEnabled());
			mChart.invalidate();
			break;
		}
		case R.id.animateX: {
			mChart.animateX(3000);
			break;
		}
		case R.id.animateY: {
			mChart.animateY(3000);
			break;
		}
		case R.id.animateXY: {

			mChart.animateXY(3000, 3000);
			break;
		}
		case R.id.actionToggleFilter: {

			Approximator a = new Approximator(ApproximatorType.DOUGLAS_PEUCKER,
					25);

			if (!mChart.isFilteringEnabled()) {
				mChart.enableFiltering(a);
			} else {
				mChart.disableFiltering();
			}
			mChart.invalidate();
			break;
		}
		case R.id.actionSave: {
			if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
				Toast.makeText(context, "Saving SUCCESSFUL!",
						Toast.LENGTH_SHORT).show();
			} else
				Toast.makeText(context, "Saving FAILED!", Toast.LENGTH_SHORT)
						.show();
			break;
		}
		}
		return true;
	}

	private void setData() {

		ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
		List<StatResultBean> list = UserDao.getDataList(id);
		if (list == null)
			return;
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			float val1 = (float) (list.get(i).getCount());
			yVals1.add(new BarEntry((int) val1, i));
			xVals.add(list.get(i).getName());
		}
		BarDataSet set1 = new BarDataSet(yVals1, "Data Set");
		set1.setBarSpacePercent(20f);
		set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
		set1.setBarShadowColor(Color.rgb(203, 203, 203));
		set1.setValueFormatter(new ValueFormatter() {

			@Override
			public String getFormattedValue(float value, Entry arg1, int arg2,
					ViewPortHandler arg3) {
				return String.valueOf((int) value);
			}
		});
		ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(set1);

		BarData data = new BarData(xVals, dataSets);
		data.setValueTextSize(14f);
		mChart.setData(data);

		mChart.invalidate();
	}

	@Override
	public void onNothingSelected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onValueSelected(Entry arg0, int arg1, Highlight arg2) {
		// TODO Auto-generated method stub

	}

}
