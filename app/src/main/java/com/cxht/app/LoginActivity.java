package com.cxht.app;

import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.gongan.manage.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private Button loginButton;
	private EditText passEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//ScreenManager.getScreenManager().pushActivity(this); // Ìí¼Óµ½ActivityÕ»
		loginButton = (Button) findViewById(R.id.login);
		passEdit = (EditText) findViewById(R.id.pwd);
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sharePreferences = getSharedPreferences(
						"user", MODE_PRIVATE);
				String pass = sharePreferences.getString("pass", "");
				if (pass.equals("")) {
					pass = Setting.INIT_LOGIN_PASS;
				}

				if (passEdit.getText().toString().equals(pass)) {
					// µÇÂ¼³É¹¦
					GonganApplication.saveShareLockStatus(LoginActivity.this,
							true);
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					LoginActivity.this.finish();
				} else {
					// µÇÂ¼´íÎó£¬ÃÜÂë´íÎó
					Toast.makeText(LoginActivity.this, "ÃÜÂë´íÎó£¡",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

}
