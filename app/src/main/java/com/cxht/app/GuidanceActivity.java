package com.cxht.app;

import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.gongan.manage.R;
import com.wgs.jiesuo.LockLoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuidanceActivity extends Activity {
private LinearLayout ll;
private TextView descTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guidance);
		descTv = findViewById(R.id.descTv);
		String desc = Setting.APP_DATE+" 版本:" + GonganApplication.getAppVerisonName();
		descTv.setText(desc);
		ll = (LinearLayout) findViewById(R.id.img_lay);
		if (!GonganApplication.isScreenOriatationPortrait(this)){
			ll.setBackgroundResource(R.drawable.guidance);
		}
		overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out); // 淡入淡出
		if (GonganApplication.isRegApp(this)){
			//正常启动
			startMainAvtivity();
		}else{
			//跳转激活页面
			Intent intent = new Intent(GuidanceActivity.this,
					RegActivity.class);
			startActivity(intent);
			GuidanceActivity.this.finish();// 结束本Activity
		}
		
	}
	 private void startMainAvtivity() {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(GuidanceActivity.this,
						LockLoginActivity.class);
				startActivity(intent);
				GuidanceActivity.this.finish();// 结束本Activity
			}
		}, 	3500);// 设置执行时间
	}

}
