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
  private EditText oldPass; //������
  private EditText newPass; //������
  private EditText resPass; //У������
  private Button okButton;//ȷ��
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_pass_edit);
		ScreenManager.getScreenManager().pushActivity(this); //���ӵ�Activityջ
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
				   		//��ȷ
	 	        	     validPassWord();
				   	}else{
				   		//����
				   		Toast.makeText(EditPassActivity.this, "�����벻��ȷ", Toast.LENGTH_SHORT).show();
				   	}
			   	}else{
			   	 	if (oldPass.getText().toString().equals(_oldPass)){
				   		//��ȷ
			   	 	   validPassWord();
				   	}else{
				   		//����
				   		Toast.makeText(EditPassActivity.this, "�����벻��ȷ", Toast.LENGTH_SHORT).show();
				   	}
			   	}
			  
				 
			}

		
 		});
	}
	private void validPassWord() {
		String pass = newPass.getText().toString();
		if (resPass.getText().toString().equals(pass)){
			//��������ɹ�������������
			SharedPreferences sp = this.getSharedPreferences("user", MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("pass", pass);
			editor.commit();
			Toast.makeText(this,"���������óɹ�", Toast.LENGTH_LONG).show();
			finish();
		}else{
			//������֤��һ��
			Toast.makeText(this,"����������֤��һ��", Toast.LENGTH_SHORT).show();
		}
		
	}

}