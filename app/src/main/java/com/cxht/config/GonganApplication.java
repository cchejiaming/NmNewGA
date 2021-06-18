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
	private static List<GroupBean> groupList = null; // 组织列表
	private static List<NaviClass> naviActivity;
	private static boolean isLock = false;// 锁屏状态
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
		//数据生命周期计算
		//productData(context,Setting.MAX_IMPORT_DATE);
		new Thread(new MyThread()).start(); 
		Log.i("Application", "existsDataBase:" + isExistsDataBase);
	}

	/**
	 * 监控线程用于监控wifi,实施自动关闭
	 * @author Administrator
	 *
	 */
	public class MyThread implements Runnable {  
	    @Override  
	    public void run() {  
	        // TODO Auto-generated method stub  
	        while (true) {  
	            try {  
	            	//关闭线程
	            	closeWifi();
	                Thread.sleep(1000);// 线程暂停1秒，单位毫秒  
	              
	            } catch (InterruptedException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	}  
	/**
	 * 关闭wifi
	 */
	public void closeWifi(){
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
		    wifiManager.setWifiEnabled(false);
		} 
	}
	/**
	 * 数据生命周期计算
	 * @param context
	 * @param num
	 */
		public static void productData(Context context,int num){
			DateUtil du = new DateUtil();
			Date currdate=  du.readImportDate(context);
			if (currdate!=null){
				Calendar ca = Calendar.getInstance();
				ca.setTime(currdate);
		        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
		        currdate = ca.getTime();
		        Date nowdate = new Date();
		        if(nowdate.getTime()> currdate.getTime()){
		        	  if(!DateUtil.isWeekend(nowdate)){
		    			  //删除所有书
		        		  ToolUtil tool = new ToolUtil();
		        		  tool.delAppData(context);
		    		  }
		        }
			}
			
		}
    public static boolean isRegApp(Context context){
    	boolean ret = true;
    	//计算是否到截止日期
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
     * 获取是否有KEY状态
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

		// 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				// 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty)
				// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error)
				// 设置图片加载或解码过程中发生错误显示的图片
				.resetViewBeforeLoading(false)
				// default 设置图片在加载前是否重置、复位
				.delayBeforeLoading(300)
				// 下载前的延迟时间
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象
		return options;
	}

	/**
	 * 清除ImageLoader缓存
	 */
	public static void clearImageLoaderCache() {
		ImageLoader.getInstance().clearDiscCache();
	}

	/**
	 * ImageLoader初始化参数设置
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
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();

		ImageLoader.getInstance().init(config);
	}

	/**
	 * 获得系统SD卡路径t
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
	 * 判断是否有SD卡
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
	 * 根据传入的uniqueName获取硬盘缓存的路径地址。
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
	 * 获得当前年份
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
	 * 获得组织列表
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
			// 装入对象写入文件
			table = new GroupSerializable();
			table.setGroup(mGroup);
			FileUtil.writeGroupTable(context, table);
		}
		// return mDatas;
		return mGroup;
	}
	/**
	 * 保存KEY到Share文件
	 * @param context
	 */
	public static void saveRegKey (Context context,String key){
		saveShareText(context,"regKey",key);
	}
	/**
	 * 读取KEY
	 * @param context
	 * @return
	 */
    public static String getRegKey(Context context){
    	return  readShareText(context,"regKey");
    }

	/**
	 * 获取PDF文件夹存储路径
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
	 * 返回当前屏幕是否为竖屏。
	 * 
	 * @param context
	 * @return 当且仅当当前屏幕为竖屏时返回true,否则返回false。
	 */
	public static boolean isScreenOriatationPortrait(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
	}

	/**
	 * 保存文本到共享文件
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
	 * 读取共享保存文本
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
	 * 保存程序可用状态
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
	 * 读取程序可用状态
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
