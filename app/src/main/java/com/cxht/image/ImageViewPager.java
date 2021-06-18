package com.cxht.image;

import java.util.ArrayList;
import java.util.List;

import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class ImageViewPager extends FragmentActivity {
	private ViewPager viewPager;
	private TextView pageNub;

	private String urlArrays = null;
	private List<String> urlList = new ArrayList<String>();
	private List<Fragment> contents;
	private ImageButton backButton;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_viewpager);
		viewPager = (ViewPager) findViewById(R.id.pager);
		pageNub = (TextView) findViewById(R.id.pager_nub);
		initial();

	}

	private void initial() {
		Intent intent = getIntent();
		urlArrays = intent.getStringExtra("Json");
		if (urlArrays != null)
			urlList = StringUtil.ImagePathFromJson(urlArrays);
		contents = new ArrayList<Fragment>();

		if (urlList != null && urlList.size() > 0) {
			for (int i = 0; i < urlList.size(); i++) {
				ShowImageFragment content = new ShowImageFragment();
				Bundle args = new Bundle();
				args.putString("networkImage", urlList.get(i));
				content.setArguments(args);
				contents.add(content);
			}
		}
		BaseFragmentPagerAdapter adapter = new BaseFragmentPagerAdapter(
				getSupportFragmentManager(), contents);
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(0);
		viewPager.setOnPageChangeListener(pageListener);
		pageNub.setText("1/" + String.valueOf(contents.size()));
		backButton = (ImageButton) findViewById(R.id.backBut);
		backButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	/**
	 * ViewPagerÇÐ»»¼àÌý·½·¨
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(position);
			pageNub.setText((position + 1) + "/" + contents.size());
		}
	};

}
