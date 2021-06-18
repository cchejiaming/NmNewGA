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
    
    private  int index = 0; //计数器
    
    private  List<Cell> oPass = new ArrayList<Cell>(); //第一次手绘密码
    private  List<Cell> tPass = new ArrayList<Cell>(); //第二次手绘密码
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
						//偶数
						tPass = pattern;
						if(tPass.equals(oPass)){
							//二次绘制结果一致则成功创建新解锁图案
							lockPatternUtils.saveLockPattern(pattern);
							Toast.makeText(LockPatternActivity.this, "密码已经设置", Toast.LENGTH_LONG)
							.show();
							finish();
						}else{
							//校验不一致
							hintTextView.setText("请设置新解锁图案");
							hintTextView.setTextColor(android.graphics.Color.RED);
							Toast.makeText(LockPatternActivity.this,"二次绘制解锁图案不一致",Toast.LENGTH_LONG).show();
						}
					
					}else{
						//奇数
						oPass.removeAll(oPass);
						oPass.addAll(pattern);
						hintTextView.setText("请再次确认绘制图案");
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
