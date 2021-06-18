package com.cxht.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cxht.config.GonganApplication;
import com.cxht.config.ScreenManager;
import com.cxht.config.Setting;
import com.cxht.dao.TableDao;
import com.cxht.db.DBManager;
import com.cxht.unit.DateUtil;
import com.gongan.manage.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;



public class DataBaseInitializeActivity extends Activity {

	private String TAG = this.getClass().getName();
	public DBManager dbHelper;
	public String databaseFilename = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_init);
		ScreenManager.getScreenManager().pushActivity(this); //添加到Activity栈
		initDatabase();
	}

	// 初始化数据库对象
	@SuppressLint("ShowToast")
	public void initDatabase() {

		Intent intent = getIntent();
		// 得到数据库的完整路径名
		databaseFilename = intent.getStringExtra("path");
		//异步初始化找到数据库
		new initDataBaseTask().execute(databaseFilename);
	}
     /**
      * 异步数据库文件初始化操作,用于把Sqlite数据库文件，写到应用程序中data文件中。
      * @author hejiaming
      *
      */
	public class initDataBaseTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = false;
			if (params.length > 0) {
				String dbPath = params[0].toString(); //获取数据库路径
				  int dot = dbPath.lastIndexOf('.');  //获取.所在位置
		            if ((dot >-1) && (dot < (dbPath.length()))) { 
		            	//获取数据库后缀
		               String fix = dbPath.substring(dot,dbPath.length());
		               
		              if (fix.equals(".db")){
		            		try {
		    					File dbFile = new File(dbPath);
		    					// 导入的数据库文件,写入应用程序data文件夹中，用户程序调用外部数据库
		    					InputStream is = new FileInputStream(dbFile);
		    					FileOutputStream fos = new FileOutputStream(
		    							GonganApplication.dataBassPath);
		    					byte[] buffer = new byte[Setting.BUFFER_SIZE];
		    					int count = 0;
		    					while ((count = is.read(buffer)) > 0) {
		    						fos.write(buffer, 0, count);
		    					}
		    					fos.close();
		    					is.close();
		    			
		    					
		    					result = true;
		    				} catch (FileNotFoundException e) {
		    					Log.i("Database", "File not found");
		    					e.printStackTrace();
		    				} catch (IOException e) {
		    					Log.i("Database", "IO exception");
		    					e.printStackTrace();
		    				} 
		              }
		              
		            } 
			
			}

			return result;
		}
 
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				if (databaseFilename != null) {
					
				    String dbFullPath ="/db/"+ Setting.DB_NAME;
					int index = databaseFilename.indexOf(dbFullPath);
					if (index>-1){
						//找到数据文件图片路径，并保存到SharedPreferences中
						String sdPath = "file://"+databaseFilename.substring(1,index);//+"/image/";
						savePath(sdPath);
					}
					
				}
				GonganApplication.isExistsDataBase = true; //初始化完毕状态
				Toast.makeText(DataBaseInitializeActivity.this, "数据库导入成功！",
						Toast.LENGTH_LONG).show();
			
				//更新数据库表内容
				TableDao.updateDBTable(DataBaseInitializeActivity.this);
				//更新缓存文件
				GonganApplication.clearImageLoaderCache();
				//保存数据生命周期（内蒙古公安厅定制功能）
				DateUtil  du = new DateUtil();
				du.saveImportDate(DataBaseInitializeActivity.this);
				//跳转到应用程序主页面
				//Intent mainIntent = new Intent(DataBaseInitializeActivity.this,
						//MainActivity.class);
				//startActivity(mainIntent);
				DataBaseInitializeActivity.this.finish(); 
			} else {
				Toast.makeText(DataBaseInitializeActivity.this, "数据库导入失败！",
						Toast.LENGTH_LONG).show();
			}
		}
       /**
        * 保存文件路径
        * @param sdPath
        */
		private void savePath(String sdPath) {

			SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("SDPATH", sdPath);
			editor.commit();
			

		}

	}

}
