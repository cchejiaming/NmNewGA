package com.cxht.app;

import com.cxht.config.ScreenManager;
import com.gongan.manage.R;

import android.os.Bundle;

public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		ScreenManager.getScreenManager().pushActivity(this); //ÃÌº”µΩActivity’ª
	}

}
