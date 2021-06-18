package com.cxht.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxht.adapter.FileManagerAdapter;
import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.cxht.unit.DialogUtil;
import com.cxht.unit.SDCardInfo;
import com.cxht.unit.SDCardUtil;
import com.cxht.unit.ZipUtils;
import com.gongan.manage.R;

/**
 * 
 * @author hejiaming copyFilesFassets() 方法为复制资源库到SD目录 initView()
 *         方法中baseButton.setOnClick方法中添加方法可自动获取资源文件数据库
 */
public class FileListActivity extends Activity {

	private TextView mCurrentPath;
	private TextView mReturn;
	private ListView mList;
	private View mPathLine;
	private String mReturnPath = null;
	private FileManagerAdapter adapter;
	private ArrayList<Map<String, Object>> infos = null;
	private String path = "";
	private TextView result;
	private TextView search;
	private searchHandler mHandler = new searchHandler();
	private Button baseButton;
	private Dialog progressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_list);
		// ScreenManager.getScreenManager().pushActivity(this); // 添加到Activity栈
		if (GonganApplication.isExistsDataBase) {
			Intent intent = new Intent(FileListActivity.this,
					MainActivity.class);
			startActivity(intent);
			this.finish();
		} else {

			initView();
			File sdDir = Environment.getExternalStorageDirectory();
			initList(sdDir);

		}

	}

	/**
	 * 线程内置数据文件拷贝内置存储卡方法 [线程专用]
	 */
	private void copyWSdToNSd() {
		// 获取设备挂接内置和外置存储卡信息
		HashMap<String, SDCardInfo> sdMap = SDCardUtil
				.getSDCardInfo(FileListActivity.this);
		SDCardInfo nSd = sdMap.get(SDCardUtil.SDCARD_INTERNAL); // 内置存储卡
		SDCardInfo wSd = sdMap.get(SDCardUtil.SDCARD_EXTERNAL); // 外置存储卡
		if (nSd != null) {
			if (wSd != null) {
				// 外置SD卡都存在,则需要检查gov文件知否在根目录下，如果文件存在则拷贝到内置SD卡

				String wf = wSd.getMountPoint() + "/"+Setting.DB_ZIP;
				String nf = nSd.getMountPoint()+"/"+Setting.DB_ZIP;
				File wFile = new File(wf);
				if (wFile.exists()) {
					// 如果文件不为空，则进行拷贝

					sendHanderMessage(1, "开始拷贝文件到内置存储卡！...");
					CopySdcardFile(wf,nf);
				}
			}

		}

	}

	private class searchRunnable implements Runnable {

		@Override
		public void run() {
			String sdPath = "";
			// 拷贝外部存储卡数据文件到内部存储
			copyWSdToNSd();
			// 获取设备挂接内置和外置存储卡信息
			HashMap<String, SDCardInfo> sdMap = SDCardUtil
					.getSDCardInfo(FileListActivity.this);
			SDCardInfo nSd = sdMap.get(SDCardUtil.SDCARD_INTERNAL); // 内置存储卡

			if (nSd != null) {

				Log.i("sdCard", nSd.getMountPoint());
				// sendHanderMessage(1,"正在查找内置存储卡数据文件...");
				sdPath = nSd.getMountPoint();

			}

			// String sdPath =
			// GonganApplication.getSDDir(getApplicationContext());
			if (sdPath == "") {
				//sdPath = "/";
				sdPath = Environment.getExternalStorageDirectory().getPath();

			}
			//查找默认路径是否有数据文件，如果没有则递归查找
			File file = new File(Setting.DEFAULT_BASE_PATH);
			if (!file.exists()){
				file = new File(sdPath);
				search(file, Setting.DB_ZIP);
			}else{
				sendHanderMessage(1, "解压:"+Setting.DEFAULT_BASE_PATH);
				unDataZip(Setting.DEFAULT_BASE_PATH);
				sendHanderMessage(0,  Setting.DEFAULT_BASE_PATH);
			}
		}

	}

	private class searchHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 0) {
				if (progressDialog != null)
					progressDialog.dismiss();
				String path = SDCardUtil.getSDPath()+"/cxht/db/"+Setting.DB_NAME;
				File db = new File(path);
				if (!db.exists()) path = (String) msg.obj;

				int dot = path.lastIndexOf('.');
				if ((dot > -1) && (dot < (path.length()))) {
					String fix = path.substring(dot, path.length());
					if (fix.equals(".db")) {

						result.setVisibility(View.VISIBLE);
						result.setText(path);
						Intent intent = new Intent(FileListActivity.this,
								DataBaseInitializeActivity.class);
						intent.putExtra("path", path);
						startActivity(intent);
						Toast.makeText(FileListActivity.this, "已成功找到数据库！",
								Toast.LENGTH_SHORT).show();
						finish();

					}
				}

			} else if (msg.what == 1) {
				String path = (String) msg.obj;
				search.setVisibility(View.VISIBLE);
				search.setText(path);
			}
		}

	}
	private  void unDataZip(String zipPath){
		String unPath = SDCardUtil.getSDPath()+"/"; //默认解压路径，系统根目录
		//解压zip文件
		try {
			ZipUtils zip = new ZipUtils();
			zip.upZipFile(zipPath, unPath);
		} catch (IOException e) {
			e.printStackTrace( );
		}
	}
	/**
	 * 查找文件递归方法
	 * 
	 * @param fileDir
	 *            文件目录
	 * @param key
	 *            查找文件名称
	 */
	private void search(File fileDir, String key) {
		try {
			File[] files = fileDir.listFiles();
			if (files.length > 0) {

				for (int j = 0; j < files.length; j++) {

					if (!files[j].isDirectory()) {

						String fileName = files[j].getName();
						if (fileName.indexOf(key) > -1) {

							path = files[j].getPath();
							sendHanderMessage(0, path);
							break;

						}

					}

					else {

						this.search(files[j], key);

					}

				}

			}

		}

		catch (Exception e) {

		}

	}

	private void sendHanderMessage(int what, Object obj) {
		Message msg = Message.obtain();
		msg.what = what;
		msg.obj = obj;
		mHandler.sendMessage(msg);
	}

	private void deleteObjFile() {
		Context context = FileListActivity.this;
		for (String file : context.fileList()) {
			if (file != null) {
				int index = file.indexOf("org");
				if (index != -1) {
					context.deleteFile(file);
				}
				context.deleteFile(file);

			}
		}
	}

	/**
	 * 视图初始化
	 */
	private void initView() {
		result = (TextView) findViewById(R.id.result_TextView);
		search = (TextView) findViewById(R.id.search_TextView);
		mCurrentPath = (TextView) findViewById(R.id.file_path);
		mPathLine = findViewById(R.id.file_path_line);
		mReturn = (TextView) findViewById(R.id.file_return);
		mList = (ListView) findViewById(R.id.file_list);
		mList.setOnItemClickListener(clickListener);
		baseButton = (Button) findViewById(R.id.base_Button);
		baseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				deleteObjFile();
				// 这句去掉就可不复制资源文件夹
				// FileUtil.copyFilesFassets(FileListActivity.this,"gongan",GonganApplication.getSDDir(FileListActivity.this)+"/gongan/");
				progressDialog = DialogUtil.createProgressDialog(
						FileListActivity.this, "正在查找中,请稍后...");
				progressDialog.show();
				searchRunnable srb = new searchRunnable();
				Thread thread = new Thread(srb);
				thread.start();

			}
		});
		mReturn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String returnStr = mReturn.getText().toString();
				if (returnStr.length() > 0 && returnStr.equals("返回上一级")) {
					File mFile = new File(mReturnPath);
					initList(mFile);
				}

			}
		});

	}
	public File[] orderByName(File file) {
		File[] files = file.listFiles();
		List<File>  fileList1 = Arrays.asList(files);
		Collections.sort(fileList1, new Comparator<File>() {

			@Override
			public int compare(File f1, File f2) {

				//根据文本排序
				String fs1 = f1.getName().toLowerCase();
				String fs2 = f2.getName().toLowerCase();
				return fs1.compareTo(fs2);
			}

		});
		List<File> fileList = Arrays.asList(files);
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});



		return  files;
	}
	/**
	 * 初始化文件列表
	 *
	 */
	private void initList(File file) {
		File[] fileList =orderByName(file);
		infos = new ArrayList<Map<String, Object>>();
		Map<String, Object> item = new HashMap<String, Object>();
		Drawable drawable;
		if (path.equals("/")) { // 如果当前为根目录,返回上一级按钮，不显示
			drawable = getResources().getDrawable(R.drawable.icon);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			mCurrentPath.setCompoundDrawablePadding(10);
			mCurrentPath.setCompoundDrawables(drawable, null, null, null);
			mCurrentPath.setText("根目录列表");
			mReturn.setVisibility(View.GONE);
			mPathLine.setVisibility(View.GONE);
		} else {
			drawable = getResources().getDrawable(R.drawable.back);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			mReturn.setCompoundDrawables(drawable, null, null, null);
			mReturn.setText("返回上一级");
			mReturn.setVisibility(View.VISIBLE);
			mReturnPath = file.getParent(); // 保存该级目录的上一级路径
			mCurrentPath.setVisibility(View.VISIBLE);
			mPathLine.setVisibility(View.VISIBLE);
			mCurrentPath.setText(file.getPath());
		}

		try {
			for (int i = 0; i < fileList.length; i++) {
				item = new HashMap<String, Object>();
				File fileItem = fileList[i];
				if (fileItem.isDirectory()) { // 如果当前文件为文件夹，设置文件夹的图标
					item.put("icon", R.drawable.folder);
				} else
					item.put("icon", R.drawable.doc);
				item.put("name", fileItem.getName());
				item.put("path", fileItem.getAbsolutePath());
				infos.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		adapter = new FileManagerAdapter(this);
		adapter.setFileListInfo(infos);
		mList.setAdapter(adapter);
	}

	/**
	 * 单机文件夹监听事件
	 */
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			File file = new File((String) (infos.get(position).get("path")));
			if (file.isDirectory()) { // 若点击文件夹，则进入下一级目录
				String nextPath = (String) (infos.get(position).get("path"));
				File mFile = new File(nextPath);
				initList(mFile);
			} else {
				// 若点击文件，则将文件名发送至调用文件浏览器的主界面

				Intent intent = new Intent();
				intent.setClass(FileListActivity.this,
						DataBaseInitializeActivity.class);
				intent.putExtra("fileName",
						(String) (infos.get(position).get("name")));
				intent.putExtra("path",
						(String) (infos.get(position).get("path")));
				startActivity(intent);
				finish();

			}

		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 文件夹拷贝方法
	 * 
	 * @param fromFile
	 *            源文件夹地址
	 * @param toFile
	 *            目标文件夹地址
	 * @return 0 成功 -1失败
	 */
	public int copy(String fromFile, String toFile) {
		// 要复制的文件目录
		File[] currentFiles;
		File root = new File(fromFile);
		// 如同判断SD卡是否存在或者文件是否存在
		// 如果不存在则 return出去
		if (!root.exists()) {
			return -1;
		}
		// 如果存在则获取当前目录下的全部文件 填充数组
		currentFiles = root.listFiles();

		// 目标目录
		File targetDir = new File(toFile);
		// 创建目录
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
		// 遍历要复制该目录下的全部文件
		for (int i = 0; i < currentFiles.length; i++) {
			if (currentFiles[i].isDirectory())// 如果当前项为子目录 进行递归
			{
				copy(currentFiles[i].getPath() + "/",
						toFile + currentFiles[i].getName() + "/");

			} else// 如果当前项为文件则进行文件拷贝
			{
				CopySdcardFile(currentFiles[i].getPath(), toFile
						+ currentFiles[i].getName());
			}
		}
		return 0;
	}

	/**
	 * 文件写入方法
	 * 
	 * @param fromFile
	 *            源标文件
	 * @param toFile
	 *            目标文件
	 * @return 0成功 -1 失败
	 */
	public int CopySdcardFile(String fromFile, String toFile) {

		try {

			sendHanderMessage(1, "正在复制:" + fromFile);

			InputStream fosfrom = new FileInputStream(fromFile);
			OutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c);
			}
			fosfrom.close();
			fosto.close();
			return 0;

		} catch (Exception ex) {
			return -1;
		}
	}
}
