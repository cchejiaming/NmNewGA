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
 * 解锁页面
 *
 * @author Administrator 用户程序绘制锁设图案和设置解锁图案
 */
public class LockLoginActivity extends Activity {
	private LockPatternView lockPatternView;
	private TextView loginType;
	private LockPatternUtils lockPatternUtils;
	private TextView hintTextView;
	private String lockStr;
	private int errorNum = 0; // 解锁错误次数
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
		// 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			// 检查该权限是否已经获取
			int i = ContextCompat.checkSelfPermission(this, permissions[0]);
			// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
			if (i != PackageManager.PERMISSION_GRANTED) {
				// 如果没有授予该权限，就去提示用户请求
				showDialogTipUserRequestPermission();
			}
		}
		isLock = getIntent().getBooleanExtra("OFF", false);// 是否为锁屏状态
		hintTextView = (TextView) findViewById(R.id.hintTextView);
		lockPatternView = (LockPatternView) findViewById(R.id.lpv_lock);
		lockPatternUtils = new LockPatternUtils(this);
		lockStr = lockPatternUtils.getLockPaternString();
		if (lockStr.equals("")) {
			hintTextView.setText("请设置解锁图案");
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
						// 解锁错误
						errorNum += 1;
						lockPatternView.setDisplayMode(DisplayMode.Wrong);
						if (errorNum < 3) {
							Toast.makeText(context,
									"密码错误" + errorNum + "次,",
									Toast.LENGTH_SHORT).show();
						} else if (errorNum < 5) {
							int num = 5 - errorNum;
							Toast.makeText(
									context,
									"密码错误" + errorNum + "次,在输入错误" + num
											+ "次后,系统将被锁死！", Toast.LENGTH_SHORT)
									.show();
						} else if (errorNum >= 5) {
							lockStatus();
							delAppData();
							GonganApplication.saveShareLockStatus(
									context, false);

						}

						lockPatternView.clearPattern();
					} else {

						// 设置解锁图案
						lockPatternUtils.saveLockPattern(pattern);
						hintTextView.setText("请绘制解锁图案");
						hintTextView.setTextColor(android.graphics.Color.WHITE);
						Toast.makeText(context, "密码已经设置",
								Toast.LENGTH_SHORT).show();
						lockPatternView.clearPattern();
					}

				} else {
					Log.i("ISLOCK", String.valueOf(isLock));
					if (isLock) {
						finish();
					} else {
						// 解锁成功！
						Toast.makeText(context, "密码正确",
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
	 * 锁屏状态
	 */
	private void lockStatus() {
		lockPatternView.setEnabled(false);
		hintTextView.setText("系统已经锁死,请联系管理员！");
		hintTextView.setTextColor(android.graphics.Color.RED);
	}

	/***
	 * 数据销毁
	 */
	private void delAppData() {
		ToolUtil tool = new ToolUtil();
		tool.delAppData(this);
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Toast.makeText(getApplicationContext(), "请输入解锁密码！",
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	// 提示用户该请求权限的弹出框
	private void showDialogTipUserRequestPermission() {

		new AlertDialog.Builder(this)
				.setTitle("存储权限不可用")
				.setMessage("由于掌上查询需要获取存储空间信息；\n否则，您将无法正常使！")
				.setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startRequestPermission();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setCancelable(false).show();
	}

	// 开始提交请求权限
	private void startRequestPermission() {
		ActivityCompat.requestPermissions(this, permissions, 321);
	}

	// 用户权限 申请 的回调方法
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == 321) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					// 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
					boolean b = shouldShowRequestPermissionRationale(permissions[0]);
					if (!b) {
						// 用户还是想用我的 APP 的
						// 提示用户去应用设置界面手动开启权限
						showDialogTipUserGoToAppSettting();
					} else
						finish();
				} else {
					Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	// 提示用户去应用设置界面手动开启权限

	private void showDialogTipUserGoToAppSettting() {

		dialog = new AlertDialog.Builder(this)
				.setTitle("存储权限不可用")
				.setMessage("请在-应用设置-权限-中，允许APP使用存储权限来保存用户数据")
				.setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 跳转到应用设置界面
						goToAppSetting();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setCancelable(false).show();
	}

	// 跳转到当前应用的设置界面
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
				// 检查该权限是否已经获取
				int i = ContextCompat.checkSelfPermission(this, permissions[0]);
				// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
				if (i != PackageManager.PERMISSION_GRANTED) {
					// 提示用户应该去应用设置界面手动开启权限
					showDialogTipUserGoToAppSettting();
				} else {
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
					Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
