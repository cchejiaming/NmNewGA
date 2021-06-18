package com.wgs.jiesuo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.cxht.app.BaseActivity;
import com.gongan.manage.R;
import com.wgs.jiesuo.LockPatternView.Cell;
import com.wgs.jiesuo.LockPatternView.OnPatternListener;

public class LockPatternActivity extends BaseActivity {

	// private OnPatternListener onPatternListener;

	private LockPatternView lockPatternView;

	private LockPatternUtils lockPatternUtils;
    private TextView hintTextView;
    
    private  int index = 0; //������
    
    private  List<Cell> oPass = new ArrayList<Cell>(); //��һ���ֻ�����
    private  List<Cell> tPass = new ArrayList<Cell>(); //�ڶ����ֻ�����
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_pattern);
		lockPatternView = (LockPatternView) findViewById(R.id.lpv_lock);
		hintTextView =(TextView)findViewById(R.id.hintTextView);
	    lockPatternUtils = new LockPatternUtils(this);
		lockPatternView.setOnPatternListener(new OnPatternListener() {

			public void onPatternStart() {

			}

			public void onPatternDetected(List<Cell> pattern) {
			        index++;
					
					if (index%2==0){
						//ż��
						tPass = pattern;
						if(tPass.equals(oPass)){
							//���λ��ƽ��һ����ɹ������½���ͼ��
							lockPatternUtils.saveLockPattern(pattern);
							Toast.makeText(LockPatternActivity.this, "�����Ѿ�����", Toast.LENGTH_LONG)
							.show();
							finish();
						}else{
							//У�鲻һ��
							hintTextView.setText("�������½���ͼ��");
							hintTextView.setTextColor(android.graphics.Color.RED);
							Toast.makeText(LockPatternActivity.this,"���λ��ƽ���ͼ����һ��",Toast.LENGTH_LONG).show();
						}
					
					}else{
						//����
						oPass.removeAll(oPass);
						oPass.addAll(pattern);
						hintTextView.setText("���ٴ�ȷ�ϻ���ͼ��");
						hintTextView.setTextColor(android.graphics.Color.WHITE);
					}
					
					 lockPatternView.clearPattern();
			
			
			}

			public void onPatternCleared() {

			}

			public void onPatternCellAdded(List<Cell> pattern) {

			}
		});
	}


}
