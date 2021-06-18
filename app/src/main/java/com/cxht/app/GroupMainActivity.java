package com.cxht.app;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import com.cxht.adapter.BasePageAdapter;
import com.cxht.config.ScreenManager;
import com.cxht.fragment.GroupJob;
import com.cxht.fragment.GroupTree;
import com.gongan.manage.R;

import java.util.ArrayList;

public class GroupMainActivity extends BaseFragmentActivity implements
        View.OnClickListener {
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    /**
     * ����4��FragmentƬ��
     */
    private GroupTree groupTree;//��֯����
    private GroupJob groupJob; //��֯�ṹ����ְ����ʾ��


    /**
     * �ײ���ť
     * @param savedInstanceState
     */
    private RadioButton rbTree;
    private RadioButton rbJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);
        ScreenManager.getScreenManager().pushActivity(this); // ��ӵ�Activityջ
        initView();
        String title = getIntent().getStringExtra("actionTitle");
        if (title != null)  actionBar.setTitle(title);
        setTabSelection(0);
        initFragment();

    }
    private void initFragment() {
        fragments.clear();// ���
        groupTree = GroupTree.newInstance();
        groupJob = GroupJob.newInstance();
        fragments.add(groupTree);
        fragments.add(groupJob);
        BasePageAdapter mAdapetr = new BasePageAdapter(
                getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapetr);
        // mViewPager.setOffscreenPageLimit(1);
        mViewPager.setOnPageChangeListener(pageListener);
    }
    private void setTabSelection(int i) {
        // ���ð�ť
        resetBtn();
        switch (i) {
            case 0:
                // ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
                setRodioButtonState(rbTree,R.drawable.info_jl_h);
                mViewPager.setCurrentItem(i);
                break;
            case 1:
                // ���������Ϣtabʱ���ı�ؼ���ͼƬ��������ɫ
                setRodioButtonState(rbJob,R.drawable.info_jj_h);
                mViewPager.setCurrentItem(i);
                break;


        }
    }
    private void resetBtn() {
        // TODO Auto-generated method stub

        setRodioButtonState(rbTree, R.drawable.info_jl);
        setRodioButtonState(rbJob, R.drawable.info_jj);

    }
    /**
     * ����RoidoButtonͼ��
     */
    private void setRodioButtonState(RadioButton btn, int id) {
        Drawable img_off;
        Resources res = getResources();
        img_off = res.getDrawable(id);
        // ����setCompoundDrawablesʱ���������Drawable.setBounds()����,����ͼƬ����ʾ
        img_off.setBounds(0, 0, img_off.getMinimumWidth(),
                img_off.getMinimumHeight());
        btn.setCompoundDrawables(null, img_off, null, null); // ������ͼ��

    }
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        rbTree =(RadioButton) findViewById(R.id.rbTree);
        rbJob = (RadioButton) findViewById(R.id.rbJob);

        rbTree.setOnClickListener(this);
        rbJob.setOnClickListener(this);
    }
    /**
     * ViewPager�л���������
     * */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {

            setTabSelection(position);
            mViewPager.setCurrentItem(position);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbTree:
                setTabSelection(0);
                break;
            case R.id.rbJob:
                setTabSelection(1);
                break;

        }
    }
}
