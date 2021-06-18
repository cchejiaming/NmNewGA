package com.cxht.app;

import com.cxht.config.ScreenManager;
import com.cxht.config.Setting;
import com.gongan.manage.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditPassActivity extends BaseActivity {
  private EditText oldPass; //旧密码
  private EditText newPass; //新密码
  private EditText resPass; //校验密码
  private Button okButton;//确认
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_pass_edit);
		ScreenManager.getScreenManager().pushActivity(this); //添加到Activity栈
		oldPass = (EditText)findViewById(R.id.old_pwd);
		newPass = (EditText)findViewById(R.id.new_pwd);
		resPass = (EditText) findViewById(R.id.res_pwd);
		okButton =(Button) findViewById(R.id.ok_button);
		okButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			 	SharedPreferences sharePreferences = getSharedPreferences("user",MODE_PRIVATE);
			   	String _oldPass = sharePreferences.getString("pass", "");
			   	if (_oldPass.equals("")){
	 	           if (oldPass.getText().toString().equals(Setting.INIT_LOGIN_PASS)){
				   		//正确
	 	        	     validPassWord();
				   	}else{
				   		//错误
				   		Toast.makeText(EditPassActivity.this, "旧密码不正确", Toast.LENGTH_SHORT).show();
				   	}
			   	}else{
			   	 	if (oldPass.getText().toString().equals(_oldPass)){
				   		//正确
			   	 	   validPassWord();
				   	}else{
				   		//错误
				   		Toast.makeText(EditPassActivity.this, "旧密码不正确", Toast.LENGTH_SHORT).show();
				   	}
			   	}
			  
				 
			}

		
 		});
	}
	private void validPassWord() {
		String pass = newPass.getText().toString();
		if (resPass.getText().toString().equals(pass)){
			//设置密码成功并保存新密码
			SharedPreferences sp = this.getSharedPreferences("user", MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("pass", pass);
			editor.commit();
			Toast.makeText(this,"新密码设置成功", Toast.LENGTH_LONG).show();
			finish();
		}else{
			//两次验证不一致
			Toast.makeText(this,"两次密码验证不一致", Toast.LENGTH_SHORT).show();
		}
		
	}

}
