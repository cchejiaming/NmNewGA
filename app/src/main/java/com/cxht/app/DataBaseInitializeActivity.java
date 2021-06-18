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
		ScreenManager.getScreenManager().pushActivity(this); //��ӵ�Activityջ
		initDatabase();
	}

	// ��ʼ�����ݿ����
	@SuppressLint("ShowToast")
	public void initDatabase() {

		Intent intent = getIntent();
		// �õ����ݿ������·����
		databaseFilename = intent.getStringExtra("path");
		//�첽��ʼ���ҵ����ݿ�
		new initDataBaseTask().execute(databaseFilename);
	}
     /**
      * �첽���ݿ��ļ���ʼ������,���ڰ�Sqlite���ݿ��ļ���д��Ӧ�ó�����data�ļ��С�
      * @author hejiaming
      *
      */
	public class initDataBaseTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = false;
			if (params.length > 0) {
				String dbPath = params[0].toString(); //��ȡ���ݿ�·��
				  int dot = dbPath.lastIndexOf('.');  //��ȡ.����λ��
		            if ((dot >-1) && (dot < (dbPath.length()))) { 
		            	//��ȡ���ݿ��׺
		               String fix = dbPath.substring(dot,dbPath.length());
		               
		              if (fix.equals(".db")){
		            		try {
		    					File dbFile = new File(dbPath);
		    					// ��������ݿ��ļ�,д��Ӧ�ó���data�ļ����У��û���������ⲿ���ݿ�
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
						//�ҵ������ļ�ͼƬ·���������浽SharedPreferences��
						String sdPath = "file://"+databaseFilename.substring(1,index);//+"/image/";
						savePath(sdPath);
					}
					
				}
				GonganApplication.isExistsDataBase = true; //��ʼ�����״̬
				Toast.makeText(DataBaseInitializeActivity.this, "���ݿ⵼��ɹ���",
						Toast.LENGTH_LONG).show();
			
				//�������ݿ������
				TableDao.updateDBTable(DataBaseInitializeActivity.this);
				//���»����ļ�
				GonganApplication.clearImageLoaderCache();
				//���������������ڣ����ɹŹ��������ƹ��ܣ�
				DateUtil  du = new DateUtil();
				du.saveImportDate(DataBaseInitializeActivity.this);
				//��ת��Ӧ�ó�����ҳ��
				//Intent mainIntent = new Intent(DataBaseInitializeActivity.this,
						//MainActivity.class);
				//startActivity(mainIntent);
				DataBaseInitializeActivity.this.finish(); 
			} else {
				Toast.makeText(DataBaseInitializeActivity.this, "���ݿ⵼��ʧ�ܣ�",
						Toast.LENGTH_LONG).show();
			}
		}
       /**
        * �����ļ�·��
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
