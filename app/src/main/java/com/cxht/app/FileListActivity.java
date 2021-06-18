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
 * @author hejiaming copyFilesFassets() ����Ϊ������Դ�⵽SDĿ¼ initView()
 *         ������baseButton.setOnClick��������ӷ������Զ���ȡ��Դ�ļ����ݿ�
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
		// ScreenManager.getScreenManager().pushActivity(this); // ��ӵ�Activityջ
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
	 * �߳����������ļ��������ô洢������ [�߳�ר��]
	 */
	private void copyWSdToNSd() {
		// ��ȡ�豸�ҽ����ú����ô洢����Ϣ
		HashMap<String, SDCardInfo> sdMap = SDCardUtil
				.getSDCardInfo(FileListActivity.this);
		SDCardInfo nSd = sdMap.get(SDCardUtil.SDCARD_INTERNAL); // ���ô洢��
		SDCardInfo wSd = sdMap.get(SDCardUtil.SDCARD_EXTERNAL); // ���ô洢��
		if (nSd != null) {
			if (wSd != null) {
				// ����SD��������,����Ҫ���gov�ļ�֪���ڸ�Ŀ¼�£�����ļ������򿽱�������SD��

				String wf = wSd.getMountPoint() + "/"+Setting.DB_ZIP;
				String nf = nSd.getMountPoint()+"/"+Setting.DB_ZIP;
				File wFile = new File(wf);
				if (wFile.exists()) {
					// ����ļ���Ϊ�գ�����п���

					sendHanderMessage(1, "��ʼ�����ļ������ô洢����...");
					CopySdcardFile(wf,nf);
				}
			}

		}

	}

	private class searchRunnable implements Runnable {

		@Override
		public void run() {
			String sdPath = "";
			// �����ⲿ�洢�������ļ����ڲ��洢
			copyWSdToNSd();
			// ��ȡ�豸�ҽ����ú����ô洢����Ϣ
			HashMap<String, SDCardInfo> sdMap = SDCardUtil
					.getSDCardInfo(FileListActivity.this);
			SDCardInfo nSd = sdMap.get(SDCardUtil.SDCARD_INTERNAL); // ���ô洢��

			if (nSd != null) {

				Log.i("sdCard", nSd.getMountPoint());
				// sendHanderMessage(1,"���ڲ������ô洢�������ļ�...");
				sdPath = nSd.getMountPoint();

			}

			// String sdPath =
			// GonganApplication.getSDDir(getApplicationContext());
			if (sdPath == "") {
				//sdPath = "/";
				sdPath = Environment.getExternalStorageDirectory().getPath();

			}
			//����Ĭ��·���Ƿ��������ļ������û����ݹ����
			File file = new File(Setting.DEFAULT_BASE_PATH);
			if (!file.exists()){
				file = new File(sdPath);
				search(file, Setting.DB_ZIP);
			}else{
				sendHanderMessage(1, "��ѹ:"+Setting.DEFAULT_BASE_PATH);
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
						Toast.makeText(FileListActivity.this, "�ѳɹ��ҵ����ݿ⣡",
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
		String unPath = SDCardUtil.getSDPath()+"/"; //Ĭ�Ͻ�ѹ·����ϵͳ��Ŀ¼
		//��ѹzip�ļ�
		try {
			ZipUtils zip = new ZipUtils();
			zip.upZipFile(zipPath, unPath);
		} catch (IOException e) {
			e.printStackTrace( );
		}
	}
	/**
	 * �����ļ��ݹ鷽��
	 * 
	 * @param fileDir
	 *            �ļ�Ŀ¼
	 * @param key
	 *            �����ļ�����
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
	 * ��ͼ��ʼ��
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
				// ���ȥ���Ϳɲ�������Դ�ļ���
				// FileUtil.copyFilesFassets(FileListActivity.this,"gongan",GonganApplication.getSDDir(FileListActivity.this)+"/gongan/");
				progressDialog = DialogUtil.createProgressDialog(
						FileListActivity.this, "���ڲ�����,���Ժ�...");
				progressDialog.show();
				searchRunnable srb = new searchRunnable();
				Thread thread = new Thread(srb);
				thread.start();

			}
		});
		mReturn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String returnStr = mReturn.getText().toString();
				if (returnStr.length() > 0 && returnStr.equals("������һ��")) {
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

				//�����ı�����
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
	 * ��ʼ���ļ��б�
	 *
	 */
	private void initList(File file) {
		File[] fileList =orderByName(file);
		infos = new ArrayList<Map<String, Object>>();
		Map<String, Object> item = new HashMap<String, Object>();
		Drawable drawable;
		if (path.equals("/")) { // �����ǰΪ��Ŀ¼,������һ����ť������ʾ
			drawable = getResources().getDrawable(R.drawable.icon);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			mCurrentPath.setCompoundDrawablePadding(10);
			mCurrentPath.setCompoundDrawables(drawable, null, null, null);
			mCurrentPath.setText("��Ŀ¼�б�");
			mReturn.setVisibility(View.GONE);
			mPathLine.setVisibility(View.GONE);
		} else {
			drawable = getResources().getDrawable(R.drawable.back);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight());
			mReturn.setCompoundDrawables(drawable, null, null, null);
			mReturn.setText("������һ��");
			mReturn.setVisibility(View.VISIBLE);
			mReturnPath = file.getParent(); // ����ü�Ŀ¼����һ��·��
			mCurrentPath.setVisibility(View.VISIBLE);
			mPathLine.setVisibility(View.VISIBLE);
			mCurrentPath.setText(file.getPath());
		}

		try {
			for (int i = 0; i < fileList.length; i++) {
				item = new HashMap<String, Object>();
				File fileItem = fileList[i];
				if (fileItem.isDirectory()) { // �����ǰ�ļ�Ϊ�ļ��У������ļ��е�ͼ��
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
	 * �����ļ��м����¼�
	 */
	private OnItemClickListener clickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			File file = new File((String) (infos.get(position).get("path")));
			if (file.isDirectory()) { // ������ļ��У��������һ��Ŀ¼
				String nextPath = (String) (infos.get(position).get("path"));
				File mFile = new File(nextPath);
				initList(mFile);
			} else {
				// ������ļ������ļ��������������ļ��������������

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
	 * �ļ��п�������
	 * 
	 * @param fromFile
	 *            Դ�ļ��е�ַ
	 * @param toFile
	 *            Ŀ���ļ��е�ַ
	 * @return 0 �ɹ� -1ʧ��
	 */
	public int copy(String fromFile, String toFile) {
		// Ҫ���Ƶ��ļ�Ŀ¼
		File[] currentFiles;
		File root = new File(fromFile);
		// ��ͬ�ж�SD���Ƿ���ڻ����ļ��Ƿ����
		// ����������� return��ȥ
		if (!root.exists()) {
			return -1;
		}
		// ����������ȡ��ǰĿ¼�µ�ȫ���ļ� �������
		currentFiles = root.listFiles();

		// Ŀ��Ŀ¼
		File targetDir = new File(toFile);
		// ����Ŀ¼
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}
		// ����Ҫ���Ƹ�Ŀ¼�µ�ȫ���ļ�
		for (int i = 0; i < currentFiles.length; i++) {
			if (currentFiles[i].isDirectory())// �����ǰ��Ϊ��Ŀ¼ ���еݹ�
			{
				copy(currentFiles[i].getPath() + "/",
						toFile + currentFiles[i].getName() + "/");

			} else// �����ǰ��Ϊ�ļ�������ļ�����
			{
				CopySdcardFile(currentFiles[i].getPath(), toFile
						+ currentFiles[i].getName());
			}
		}
		return 0;
	}

	/**
	 * �ļ�д�뷽��
	 * 
	 * @param fromFile
	 *            Դ���ļ�
	 * @param toFile
	 *            Ŀ���ļ�
	 * @return 0�ɹ� -1 ʧ��
	 */
	public int CopySdcardFile(String fromFile, String toFile) {

		try {

			sendHanderMessage(1, "���ڸ���:" + fromFile);

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
