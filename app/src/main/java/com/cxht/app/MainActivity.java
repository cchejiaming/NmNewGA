package com.cxht.app;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.cxht.adapter.MiLancherAdapter;
import com.cxht.config.GonganApplication;
import com.cxht.config.ScreenManager;
import com.cxht.config.Setting;
import com.cxht.dao.UserDao;
import com.cxht.entity.NaviClass;
import com.cxht.unit.ScreenListener;
import com.cxht.unit.ScreenListener.ScreenStateListener;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;
import com.wgs.jiesuo.LockLoginActivity;

public class MainActivity extends Activity {

	private GridView gridView;
	private MiLancherAdapter adapter;
	private List<NaviClass> list;
	private TranslateAnimation left, right;
	private ImageView runImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 if (!GonganApplication.isExistsDataBase){
	        	Intent intent = new Intent(this,FileListActivity.class);
	        	startActivity(intent);
	        	this.finish();
	     }
		setContentView(R.layout.main);
		ScreenManager.getScreenManager().pushActivity(this); //添加到Activity栈
		getGridItem();
		initView();
		runAnimation();
		//reLock();
	}
	
	 @Override
	protected void onRestart() {
		if (!GonganApplication.isLock()){
			Intent intent = new Intent(this,LockLoginActivity.class);
        	startActivity(intent);
        
		}
		super.onRestart();
	}

	private void reLock(){
	    	ScreenListener l = new ScreenListener(this);
	        l.begin(new ScreenStateListener() {

	            @Override
	            public void onScreenOn() {
	            	
					
	            }

				@Override
				public void onScreenOff() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(MainActivity.this,LockLoginActivity.class);
					intent.putExtra("OFF",true);
				    startActivity(intent);
				    GonganApplication.setLock(false);
					//ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity.class);
					//android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
					//System.exit(0); // 常规java、c#的标准退出法，返回值为0代表正常退出
				}

				@Override
				public void onUserPresent() {
					// TODO Auto-generated method stub
					
				}

	           
	        });
	    }
	private void getGridItem() {
		list = GonganApplication.getNaviActivity();
	}

	private void initView() {
		runImage = (ImageView) findViewById(R.id.run_image);
		gridView = (GridView) findViewById(R.id.main_gridView);
		adapter = new MiLancherAdapter(MainActivity.this,list);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent intent = new Intent();
				intent.putExtra("actionTitle", list.get(position).getTitle());
				if (list.get(position).getTitle().equals("筛选查询")) {
					intent.setClass(MainActivity.this,
							UserSearchActivity.class);
					startActivity(intent);
				}

				if (list.get(position).getTitle().equals("统计分析")) {
					intent.setClass(MainActivity.this,GroupTreeActivity.class);
					intent.putExtra("id", 0);
					intent.putExtra("type", 0);
					intent.putExtra("title", Setting.TABLE_ZS_TITLE);
					startActivity(intent);
					/**
					 intent.setClass(MainActivity.this,
					 StatisticsClassActivity.class);
					 startActivity(intent);
					 **/
				}
				if (list.get(position).getTitle().equals("人员对比")) {
					String andWhere = " and 1<>1";
					String where = StringUtil.toAndWhere(UserDao.getFavoriteIdList(MainActivity.this));
					if (!"".equals(where)) andWhere = " and user_id in ("+where+") ";
					intent.setClass(MainActivity.this,OrganizationMainActivity.class);
					intent.putExtra("AndWhere",andWhere);
					intent.putExtra("IsFav", true);
				    startActivity(intent);

				}
				if (list.get(position).getTitle().equals("部门查询")) {
					//intent.setClass(MainActivity.this,GroupSearchActivity.class);
					intent.setClass(MainActivity.this,GroupInfoActivity.class);
					startActivity(intent);

				}
				if (list.get(position).getTitle().equals("部门职数")) {
					intent.setClass(MainActivity.this,GroupNumberTreeActivity.class);
					startActivity(intent);
				}
				if (list.get(position).getTitle().equals("系统设置")) {
					//GonganApplication.isExistsDataBase = false;
					intent.setClass(MainActivity.this,SystemSetActivity.class);
					startActivity(intent);
				}
				if (list.get(position).getTitle().equals("组织收藏")) {
				
					intent.setClass(MainActivity.this,FavoriteGroupActivity.class);
					startActivity(intent);
				}
				if (list.get(position).getTitle().equals("历史记录")) {
					
					intent.setClass(MainActivity.this,HistoryListActivity.class);
					startActivity(intent);
				}
				if (list.get(position).getTitle().equals("系统退出")) {
					//ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity.class);
					android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
					System.exit(0); // 常规java、c#的标准退出法，返回值为0代表正常退出

				}
			}

		});
	}
	private long exitTime = 0;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				//ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity.class);
				android.os.Process.killProcess(android.os.Process.myPid()); // 获取PID
				System.exit(0); // 常规java、c#的标准退出法，返回值为0代表正常退出

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void runAnimation() {
	
		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(30000);
		left.setDuration(30000);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(right);
			}
		});
		runImage.startAnimation(right);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exit, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case R.id.sys_exit:
				ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity.class);
				android.os.Process.killProcess(android.os.Process.myPid());
				// 获取PID
				System.exit(0); // 常规java、c#的标准退出法，返回值为0代表正常退出
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
