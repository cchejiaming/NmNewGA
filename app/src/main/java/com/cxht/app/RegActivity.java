package com.cxht.app;

import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.gongan.manage.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegActivity extends Activity {
	private TextView retTv;
	private EditText keyEt;
	private Button okBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_reg_key);
		retTv = (TextView) findViewById(R.id.reg_tv);
		okBtn = (Button) findViewById(R.id.ok_button);
		keyEt = (EditText) findViewById(R.id.old_pwd);
		retTv.setText("������ý�ֹ���ڣ� " + Setting.END_DATE + " ���ڳ��Ź�˾��ϵ��");
		okBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String inkey = keyEt.getText().toString();
				if (inkey.equals(Setting.REG_KEY)) {
                   GonganApplication.saveRegKey(RegActivity.this, inkey);
                    Toast.makeText(RegActivity.this, "�������ɹ���",
							Toast.LENGTH_SHORT).show();
                   RegActivity.this.finish();
				} else {
					// �������
					Toast.makeText(RegActivity.this, "����������",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

}
