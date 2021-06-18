package com.wgs.jiesuo;

import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cxht.app.LoginActivity;
import com.cxht.app.MainActivity;
import com.cxht.config.GonganApplication;
import com.cxht.unit.ToolUtil;
import com.gongan.manage.R;
import com.wgs.jiesuo.LockPatternView.Cell;
import com.wgs.jiesuo.LockPatternView.DisplayMode;
import com.wgs.jiesuo.LockPatternView.OnPatternListener;

/**
 * ����ҳ��
 *
 * @author Administrator �û������������ͼ�������ý���ͼ��
 */
public class LockLoginActivity extends Activity {
	private LockPatternView lockPatternView;
	private TextView loginType;
	private LockPatternUtils lockPatternUtils;
	private TextView hintTextView;
	private String lockStr;
	private int errorNum = 0; // �����������
	private boolean isLock = false;
	private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
	private AlertDialog dialog;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_login);
		context = LockLoginActivity.this;
		// �汾�жϡ����ֻ�ϵͳ���� 23 ʱ�����б�Ҫȥ�ж�Ȩ���Ƿ��ȡ
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			// ����Ȩ���Ƿ��Ѿ���ȡ
			int i = ContextCompat.checkSelfPermission(this, permissions[0]);
			// Ȩ���Ƿ��Ѿ� ��Ȩ GRANTED---��Ȩ  DINIED---�ܾ�
			if (i != PackageManager.PERMISSION_GRANTED) {
				// ���û�������Ȩ�ޣ���ȥ��ʾ�û�����
				showDialogTipUserRequestPermission();
			}
		}
		isLock = getIntent().getBooleanExtra("OFF", false);// �Ƿ�Ϊ����״̬
		hintTextView = (TextView) findViewById(R.id.hintTextView);
		lockPatternView = (LockPatternView) findViewById(R.id.lpv_lock);
		lockPatternUtils = new LockPatternUtils(this);
		lockStr = lockPatternUtils.getLockPaternString();
		if (lockStr.equals("")) {
			hintTextView.setText("�����ý���ͼ��");
			hintTextView.setTextColor(android.graphics.Color.RED);
		}
		loginType = (TextView) findViewById(R.id.loginTextView);
		loginType.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}

		});
		lockPatternView.setOnPatternListener(new OnPatternListener() {

			public void onPatternStart() {

			}

			public void onPatternDetected(List<Cell> pattern) {

				int result = lockPatternUtils.checkPattern(pattern);
				if (result != 1) {
					if (result == 0) {
						// ��������
						errorNum += 1;
						lockPatternView.setDisplayMode(DisplayMode.Wrong);
						if (errorNum < 3) {
							Toast.makeText(context,
									"�������" + errorNum + "��,",
									Toast.LENGTH_SHORT).show();
						} else if (errorNum < 5) {
							int num = 5 - errorNum;
							Toast.makeText(
									context,
									"�������" + errorNum + "��,���������" + num
											+ "�κ�,ϵͳ����������", Toast.LENGTH_SHORT)
									.show();
						} else if (errorNum >= 5) {
							lockStatus();
							delAppData();
							GonganApplication.saveShareLockStatus(
									context, false);

						}

						lockPatternView.clearPattern();
					} else {

						// ���ý���ͼ��
						lockPatternUtils.saveLockPattern(pattern);
						hintTextView.setText("����ƽ���ͼ��");
						hintTextView.setTextColor(android.graphics.Color.WHITE);
						Toast.makeText(context, "�����Ѿ�����",
								Toast.LENGTH_SHORT).show();
						lockPatternView.clearPattern();
					}

				} else {
					Log.i("ISLOCK", String.valueOf(isLock));
					if (isLock) {
						finish();
					} else {
						// �����ɹ���
						Toast.makeText(context, "������ȷ",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(LockLoginActivity.this,
								MainActivity.class);
						startActivity(intent);
						GonganApplication.setLock(true);
						finish();
					}

				}

			}

			public void onPatternCleared() {

			}

			public void onPatternCellAdded(List<Cell> pattern) {

			}
		});
		if (!GonganApplication.readShareLockStatus(this)) {

			lockStatus();
		}
	}

	/***
	 * ����״̬
	 */
	private void lockStatus() {
		lockPatternView.setEnabled(false);
		hintTextView.setText("ϵͳ�Ѿ�����,����ϵ����Ա��");
		hintTextView.setTextColor(android.graphics.Color.RED);
	}

	/***
	 * ��������
	 */
	private void delAppData() {
		ToolUtil tool = new ToolUtil();
		tool.delAppData(this);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Toast.makeText(getApplicationContext(), "������������룡",
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	// ��ʾ�û�������Ȩ�޵ĵ�����
	private void showDialogTipUserRequestPermission() {

		new AlertDialog.Builder(this)
				.setTitle("�洢Ȩ�޲�����")
				.setMessage("�������ϲ�ѯ��Ҫ��ȡ�洢�ռ���Ϣ��\n���������޷�����ʹ��")
				.setPositiveButton("��������", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startRequestPermission();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setCancelable(false).show();
	}

	// ��ʼ�ύ����Ȩ��
	private void startRequestPermission() {
		ActivityCompat.requestPermissions(this, permissions, 321);
	}

	// �û�Ȩ�� ���� �Ļص�����
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == 321) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					// �ж��û��Ƿ� ����˲������ѡ�(����Ȩ���Ƿ񻹿�������)
					boolean b = shouldShowRequestPermissionRationale(permissions[0]);
					if (!b) {
						// �û����������ҵ� APP ��
						// ��ʾ�û�ȥӦ�����ý����ֶ�����Ȩ��
						showDialogTipUserGoToAppSettting();
					} else
						finish();
				} else {
					Toast.makeText(this, "Ȩ�޻�ȡ�ɹ�", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	// ��ʾ�û�ȥӦ�����ý����ֶ�����Ȩ��

	private void showDialogTipUserGoToAppSettting() {

		dialog = new AlertDialog.Builder(this)
				.setTitle("�洢Ȩ�޲�����")
				.setMessage("����-Ӧ������-Ȩ��-�У�����APPʹ�ô洢Ȩ���������û�����")
				.setPositiveButton("��������", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ��ת��Ӧ�����ý���
						goToAppSetting();
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setCancelable(false).show();
	}

	// ��ת����ǰӦ�õ����ý���
	private void goToAppSetting() {
		Intent intent = new Intent();

		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", getPackageName(), null);
		intent.setData(uri);

		startActivityForResult(intent, 123);
	}

	//
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 123) {

			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				// ����Ȩ���Ƿ��Ѿ���ȡ
				int i = ContextCompat.checkSelfPermission(this, permissions[0]);
				// Ȩ���Ƿ��Ѿ� ��Ȩ GRANTED---��Ȩ  DINIED---�ܾ�
				if (i != PackageManager.PERMISSION_GRANTED) {
					// ��ʾ�û�Ӧ��ȥӦ�����ý����ֶ�����Ȩ��
					showDialogTipUserGoToAppSettting();
				} else {
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
					Toast.makeText(this, "Ȩ�޻�ȡ�ɹ�", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
