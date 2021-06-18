package com.cxht.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cxht.bean.StatResultBean;
import com.cxht.dao.UserDao;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gongan.manage.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class StatisticsPieChart extends Fragment implements
		OnChartValueSelectedListener {

	private PieChart mChart;
	private View view;
	private Context context;
	private int id;
	private String title;
	private int all = 0;

	public static StatisticsPieChart newInstance(int id, String title) {
		Bundle bundle = new Bundle();
		bundle.putInt("id", id);
		bundle.putString("title", title);
		StatisticsPieChart fragment = new StatisticsPieChart();
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
		view = LayoutInflater.from(context).inflate(R.layout.fragment_piechart,
				null);
		((Activity) context).getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mChart = (PieChart) view.findViewById(R.id.chart1);
		mChart.setUsePercentValues(true);
		mChart.setDescription("");
		mChart.setExtraOffsets(5, 10, 5, 5);

		mChart.setDragDecelerationFrictionCoef(0.95f);

		mChart.setDrawHoleEnabled(true);
		mChart.setHoleColorTransparent(true);

		mChart.setTransparentCircleColor(Color.WHITE);
		mChart.setTransparentCircleAlpha(110);

		mChart.setHoleRadius(58f);
		mChart.setTransparentCircleRadius(61f);

		mChart.setDrawCenterText(true);

		mChart.setRotationAngle(0);
		// enable rotation of the chart by touch
		mChart.setRotationEnabled(true);
		mChart.setHighlightPerTapEnabled(true);

		// mChart.setUnit(" 鈧?");
		// mChart.setDrawUnitsInChart(true);

		// add a selection listener
		mChart.setOnChartValueSelectedListener(this);

		setData();
		mChart.setCenterText(generateCenterSpannableText());
		mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
		// mChart.spin(2000, 0, 360);

		Legend l = mChart.getLegend();
		l.setPosition(LegendPosition.RIGHT_OF_CHART);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(0f);
		l.setYOffset(0f);
		return view;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		LayoutInflater.from(context).inflate(R.menu.pie, (ViewGroup) menu);
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
		case R.id.actionToggleHole: {
			if (mChart.isDrawHoleEnabled())
				mChart.setDrawHoleEnabled(false);
			else
				mChart.setDrawHoleEnabled(true);
			mChart.invalidate();
			break;
		}
		case R.id.actionDrawCenter: {
			if (mChart.isDrawCenterTextEnabled())
				mChart.setDrawCenterText(false);
			else
				mChart.setDrawCenterText(true);
			mChart.invalidate();
			break;
		}
		case R.id.actionToggleXVals: {

			mChart.setDrawSliceText(!mChart.isDrawSliceTextEnabled());
			mChart.invalidate();
			break;
		}
		case R.id.actionSave: {
			// mChart.saveToGallery("title"+System.currentTimeMillis());
			mChart.saveToPath("title" + System.currentTimeMillis(), "");
			break;
		}
		case R.id.actionTogglePercent:
			mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
			mChart.invalidate();
			break;
		case R.id.animateX: {
			mChart.animateX(1400);
			break;
		}
		case R.id.animateY: {
			mChart.animateY(1400);
			break;
		}
		case R.id.animateXY: {
			mChart.animateXY(1400, 1400);
			break;
		}
		}
		return true;
	}

	private void setData() {

		ArrayList<Entry> yVals1 = new ArrayList<Entry>();

		List<StatResultBean> list = UserDao.getDataList(id);
		if (list == null)
			return;
		ArrayList<String> xVals = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			yVals1.add(new Entry((float) (list.get(i).getCount()), i));
			xVals.add(list.get(i).getName() + "(" + list.get(i).getCount()
					+ "人)");
			all += list.get(i).getCount();
		}
		PieDataSet dataSet = new PieDataSet(yVals1, "参与统计：" + all + "人");
		dataSet.setSliceSpace(2f);
		dataSet.setSelectionShift(5f);
		dataSet.setValueTextSize(13f);

		// add a lot of colors

		ArrayList<Integer> colors = new ArrayList<Integer>();

		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);

		dataSet.setColors(colors);

		PieData data = new PieData(xVals, dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(14f);
		data.setValueTextColor(Color.WHITE);

		mChart.setData(data);

		mChart.highlightValues(null);

		mChart.invalidate();
	}

	private SpannableString generateCenterSpannableText() {

		String info = "参与统计：" + all + "人";
		SpannableString s = new SpannableString(title + info);
		s.setSpan(new RelativeSizeSpan(1.7f), 0, title.length(), 0);
		int t_count = title.length();
		int e_count = info.length() + title.length();
		s.setSpan(new StyleSpan(Typeface.ITALIC), t_count, e_count, 0);
		s.setSpan(new ForegroundColorSpan(Color.BLUE), t_count, e_count, 0);
		s.setSpan(new RelativeSizeSpan(1.4f), t_count, e_count, 0);
		// s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14,
		// s.length(), 0);
		// s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()),
		// s.length() - 14, s.length(), 0);
		return s;
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
