package com.cxht.config;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.cxht.bean.GroupBean;
import com.cxht.bean.GroupSerializable;
import com.cxht.dao.GroupDao;
import com.cxht.dao.NaviDao;
import com.cxht.entity.NaviClass;
import com.cxht.unit.DateUtil;
import com.cxht.unit.FileUtil;
import com.cxht.unit.StringUtil;
import com.cxht.unit.ToolUtil;
import com.gongan.manage.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class GonganApplication extends Application {

	public static boolean isExistsDataBase;
	public static final String dataBassPath = Setting.DB_PATH + "/"
			+ Setting.DB_NAME;
	private static int appVerisonCode = 0;
	private static String appVerisonName;
	private static List<GroupBean> groupList = null; // ��֯�б�
	private static List<NaviClass> naviActivity;
	private static boolean isLock = false;// ����״̬
	private Context context;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		context = getApplicationContext();
		AppVersionCode();
		isExistsDataBase = isExistsDataBase(dataBassPath);
		initImageLoader(context);
		initNaviList();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
		    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		    StrictMode.setVmPolicy(builder.build());
		}
		//�����������ڼ���
		//productData(context,Setting.MAX_IMPORT_DATE);
		new Thread(new MyThread()).start(); 
		Log.i("Application", "existsDataBase:" + isExistsDataBase);
	}

	/**
	 * ����߳����ڼ��wifi,ʵʩ�Զ��ر�
	 * @author Administrator
	 *
	 */
	public class MyThread implements Runnable {  
	    @Override  
	    public void run() {  
	        // TODO Auto-generated method stub  
	        while (true) {  
	            try {  
	            	//�ر��߳�
	            	closeWifi();
	                Thread.sleep(1000);// �߳���ͣ1�룬��λ����  
	              
	            } catch (InterruptedException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	}  
	/**
	 * �ر�wifi
	 */
	public void closeWifi(){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
		    wifiManager.setWifiEnabled(false);
		} 
	}
	/**
	 * �����������ڼ���
	 * @param context
	 * @param num
	 */
		public static void productData(Context context,int num){
			DateUtil du = new DateUtil();
			Date currdate=  du.readImportDate(context);
			if (currdate!=null){
				Calendar ca = Calendar.getInstance();
				ca.setTime(currdate);
		        ca.add(Calendar.DATE, num);// numΪ���ӵ����������Ըı��
		        currdate = ca.getTime();
		        Date nowdate = new Date();
		        if(nowdate.getTime()> currdate.getTime()){
		        	  if(!DateUtil.isWeekend(nowdate)){
		    			  //ɾ��������
		        		  ToolUtil tool = new ToolUtil();
		        		  tool.delAppData(context);
		    		  }
		        }
			}
			
		}
    public static boolean isRegApp(Context context){
    	boolean ret = true;
    	//�����Ƿ񵽽�ֹ����
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	if (!isRegKey(context)){
    		try
        	{
        	Date d1 = df.parse(Setting.END_DATE);
        	Date d2 = df.parse(StringUtil.getData());
        	   if (d1.getTime()< d2.getTime()){
        		   ret = false;
        	   }
        	}
        	catch (Exception e){
        	}
    	}
    	
    	return ret;
    }
    /**
     * ��ȡ�Ƿ���KEY״̬
     * @param context
     * @return
     */
    public static boolean isRegKey(Context context){
    	boolean ret = false;
    	String key = getRegKey(context);
    	if(key!=null){
    		if (Setting.REG_KEY.equals(key)){
        		ret = true;
        	}
    	}
    	return true;
    }
    
	private void initNaviList() {
		List<NaviClass> list = NaviDao.GetNaviMenuList(getApplicationContext());
		setNaviActivity(list);
		if (list == null) {
			NaviDao.AddNaviListToData(getApplicationContext(),
					Setting.createNaviClass());
			initNaviList();
		}

	}

	private boolean isExistsDataBase(String dbfile) {
		boolean result = false;
		if ((new File(dbfile).exists())) {
			result = true;
		}
		return result;
	}

	private void AppVersionCode() {
		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			// Log.i("DataUtil","getVersion:"+pInfo.versionCode);
			setAppVerisonCode(pInfo.versionCode);
			setAppVerisonName(pInfo.versionName);

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DisplayImageOptions defaultOptionsConfig() {

		// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				// ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.ic_empty)
				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.ic_error)
				// ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.resetViewBeforeLoading(false)
				// default ����ͼƬ�ڼ���ǰ�Ƿ����á���λ
				.delayBeforeLoading(300)
				// ����ǰ���ӳ�ʱ��
				.cacheInMemory(true)
				// �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true)
				// �������ص�ͼƬ�Ƿ񻺴���SD����
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				// .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
				.build(); // �������ù���DisplayImageOption����
		return options;
	}

	/**
	 * ���ImageLoader����
	 */
	public static void clearImageLoaderCache() {
		ImageLoader.getInstance().clearDiscCache();
	}

	/**
	 * ImageLoader��ʼ����������
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		File cacheDir = getDiskCacheDir(context, "ImageLoaderhjm");
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(cacheDir))// �Զ��建��·��
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		ImageLoader.getInstance().init(config);
	}

	/**
	 * ���ϵͳSD��·��t
	 * 
	 * @param context
	 * @return
	 */
	public static String getSDDir(Context context) {
		if (!checkSDcard()) {
			Toast.makeText(context, "no sdcard", Toast.LENGTH_SHORT).show();
			return "";
		}
		try {
			String SD_DIR = Environment.getExternalStorageDirectory()
					.toString();
			return SD_DIR;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * �ж��Ƿ���SD��
	 * 
	 * @return
	 */
	public static boolean checkSDcard() {
		String sdStutusString = Environment.getExternalStorageState();
		if (sdStutusString.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ���ݴ����uniqueName��ȡӲ�̻����·����ַ��
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	public static int getAppVerisonCode() {
		return appVerisonCode;
	}

	public static void setAppVerisonCode(int appVerisonCode) {
		GonganApplication.appVerisonCode = appVerisonCode;
	}

	public static String getAppVerisonName() {
		return appVerisonName;
	}

	public static void setAppVerisonName(String appVerisonName) {
		GonganApplication.appVerisonName = appVerisonName;
	}

	/**
	 * ��õ�ǰ���
	 * 
	 * @return
	 */
	public static int getDateNowYear() {

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	public static List<GroupBean> getGroupList() {
		return groupList;
	}

	public static void setGroupList(List<GroupBean> groupList) {
		GonganApplication.groupList = groupList;
	}

	/**
	 * �����֯�б�
	 * 
	 * @return
	 */
	public static List<GroupBean> getGroupBeanList(Context context) {
		// List<GroupBean> mGroup = null;
		// if (GonganApplication.getGroupList() != null) {
		// mGroup = GonganApplication.getGroupList();
		// } else {
		// mGroup = GroupDao.getGroupList();
		// GonganApplication.setGroupList(mGroup);

		// }
		List<GroupBean> mGroup = null;
		GroupSerializable table = FileUtil.readGroupTable(context);
		if (table != null) {
			mGroup = table.getGroup();
		} else {

			mGroup = GroupDao.getGroupList();
			// װ�����д���ļ�
			table = new GroupSerializable();
			table.setGroup(mGroup);
			FileUtil.writeGroupTable(context, table);
		}
		// return mDatas;
		return mGroup;
	}
	/**
	 * ����KEY��Share�ļ�
	 * @param context
	 */
	public static void saveRegKey (Context context,String key){
		saveShareText(context,"regKey",key);
	}
	/**
	 * ��ȡKEY
	 * @param context
	 * @return
	 */
    public static String getRegKey(Context context){
    	return  readShareText(context,"regKey");
    }

	/**
	 * ��ȡPDF�ļ��д洢·��
	 * @param context
	 * @return
	 */
	public static String getPdfPath(Context context){
		String path = getSDPath(context);
		if (path != null)
			path += "/pdf";
		path = path.replace("file:","");
		return path;
	}
    public static String getImagePath(Context context) {
		String path = getSDPath(context);
		if (path != null)
			path += "/image/";
		return path;
	}


/**
	public static String getImagePath(Context context) {
		String path = null;

		SharedPreferences sp = context.getSharedPreferences("config",
				MODE_PRIVATE);
		path = sp.getString("SDPATH", null);

		return path;
	}
**/
	public static List<NaviClass> getNaviActivity() {
		return naviActivity;
	}

	public static void setNaviActivity(List<NaviClass> naviActivity) {
		GonganApplication.naviActivity = naviActivity;
	}

	/**
	 * ���ص�ǰ��Ļ�Ƿ�Ϊ������
	 * 
	 * @param context
	 * @return ���ҽ�����ǰ��ĻΪ����ʱ����true,���򷵻�false��
	 */
	public static boolean isScreenOriatationPortrait(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	/**
	 * �����ı��������ļ�
	 * 
	 * @param context
	 * @param name
	 * @param txt
	 */
	public static void saveShareText(Context context, String name, String txt) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("txt", txt);
		editor.commit();
	}

	/**
	 * ��ȡ�������ı�
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static String readShareText(Context context, String name) {
		SharedPreferences sp = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		String text = sp.getString("txt", null);
		return text;
	}

	/***
	 * ����������״̬
	 * 
	 * @param context
	 * @param bl
	 */
	public static void saveShareLockStatus(Context context, boolean bl) {
		SharedPreferences sp = context.getSharedPreferences("Lock",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("status", bl);
		editor.commit();
	}

	/***
	 * ��ȡ�������״̬
	 * 
	 * @param context
	 * @return
	 */
	public static boolean readShareLockStatus(Context context) {
		SharedPreferences sp = context.getSharedPreferences("Lock",
				Context.MODE_PRIVATE);
		boolean bl = sp.getBoolean("status", true);
		return bl;
	}

	public static boolean isLock() {
		return isLock;
	}

	public static void setLock(boolean isLock) {
		GonganApplication.isLock = isLock;
	}


	public static String getSDPath(Context context) {
		String path = null;
		SharedPreferences sp = context.getSharedPreferences("config",
				MODE_PRIVATE);
		path = sp.getString("SDPATH", null);
		return path;
	}
}
