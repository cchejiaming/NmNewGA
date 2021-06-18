package com.cxht.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.cxht.adapter.SpinnerAdapter;
import com.cxht.bean.SearchItem;
import com.cxht.bean.SearchParams;
import com.cxht.config.ScreenManager;
import com.cxht.config.Setting;
import com.cxht.unit.DialogUtil;
import com.cxht.unit.StringUtil;
import com.cxht.unit.DialogUtil.OnDialogResultListener;
import com.gongan.manage.R;

public class UserSearchActivity extends Activity implements
        OnSeekBarChangeListener, OnClickListener, OnDialogResultListener {

    private TextView mSubmit, mCancel;
    private ImageView backImage;
    private EditText content_text;
    private EditText age_min;
    private EditText age_max;
    private SeekBar age_min_seek;
    private SeekBar age_max_seek;
    private RadioGroup sexGroup;

    private TextView nationTv, fullEduTv, jobEduTv, orgTv, quaTv, higEduTv, preTv, resTv, jobYearTv, posYearTv, ageTv, famTv,
            majorTv, schoolTv, degreeTv, workYearTv, partyYearTv,jobDescTv,nativeplaceTv,birthplaceTv,familyNameTv,stateTv,
            examineTv,punishTv,assessTv,assessYearTv,punishTypeTv,userNumberTv,userRankTv,marshalsTv,gunTv,userTypeTv,jobLxTv,
            depthLxTv,userCodeTv,jobGwyTv,depthRatifyTv;
    private LinearLayout nationLay,  fullEduLay, jobEduLay, posYearLay, quaLay,  higEduLay,  preLay, resLay, jobYearLay,orgLay, ageLay, famLay,
            majorLay, schoolLay, degreeLay, workYearLay, partyYearLay,jobDescLay,nativeplaceLay,birthplaceLay, familyNameLay,stateLay,examineLay,punishLay,
            assessLay,assessYearLay,punishTypeLay,userNumberLay,userRankLay,marshalsLay,gunLay,userTypeLay,jobLxLay,depthLxLay,userCodeLay,jobGwyLay,depthRatifyLay;
    private SearchParams param;
    private Spinner yearSpr, monthSpr;
    private List <String> year, month;
    private SpinnerAdapter yAdapter, mAdapter;
    private String TAG = "SearchActivity";
    private String andWhere = null; //�������� ����Ƕ������ϲ�ѯ���дβ���

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_search);
        ScreenManager.getScreenManager( ).pushActivity(this); // ��ӵ�Activityջ
        andWhere = getIntent( ).getStringExtra("AndWhere"); //�ϴβ�ѯ����
        initView( );
        initData( );
        initListener( );

    }

    private void initListener() {
        yAdapter = new SpinnerAdapter(this, year);
        mAdapter = new SpinnerAdapter(this, month);
        yearSpr.setAdapter(yAdapter);
        monthSpr.setAdapter(mAdapter);
        setSprSelect(yearSpr, StringUtil.getNowDataYear( ));
        setSprSelect(monthSpr, StringUtil.getNowDataMonth( ));

        mSubmit.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View arg0) {
                loaderSearchParam( ); // װ�ز�ѯ����
                Intent intent = new Intent(UserSearchActivity.this,
                        OrganizationMainActivity.class);
                Bundle mBundle = new Bundle( );
                mBundle.putSerializable("SearchParam", param);
                intent.putExtras(mBundle);
                if (andWhere != null) intent.putExtra("AndWhere", andWhere); //Ͷ���ϴβ�ѯ����
                startActivity(intent);

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                cancelParam( );
            }


        });
        backImage.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View arg0) {
                // ����ҳ��
                UserSearchActivity.this.finish( );
            }
        });
    }

    /**
     * ��ղ�ѯ����
     */
    private void cancelParam() {
        initData( );
        setSprSelect(yearSpr, StringUtil.getNowDataYear( ));
        setSprSelect(monthSpr, StringUtil.getNowDataMonth( ));
        resetRadioGroup(sexGroup);
        age_min_seek.setProgress(0);
        age_max_seek.setProgress(50);
        content_text.setText("");
        age_min.setText("20");
        age_max.setText("70");
        nationTv.setText("����");
        fullEduTv.setText("����");
        jobEduTv.setText("����");
        higEduTv.setText("����");
        preTv.setText("����");
        resTv.setText("����");
        jobYearTv.setText("����");
        orgTv.setText("����");
        posYearTv.setText("����");
        ageTv.setText("����");
        famTv.setText("����");
        majorTv.setText("����");
        schoolTv.setText("����");
        degreeTv.setText("����");
        quaTv.setText("����");
        workYearTv.setText("����");
        partyYearTv.setText("����");
        familyNameTv.setText("����");
        nativeplaceTv.setText("����");
        birthplaceTv.setText("����");
        jobDescTv.setText("����");
        stateTv.setText("����");
        examineTv.setText("����");
        assessTv.setText("����");
        punishTv.setText("����");
        assessYearTv.setText("����");
        punishTypeTv.setText("����");
        userNumberTv.setText("����");
        userRankTv.setText("����");
        marshalsTv.setText("����");
        gunTv.setText("����");
        userTypeTv.setText("����");
        jobLxTv.setText("����");
        depthLxTv.setText("����");
        userCodeTv.setText("����");
        jobGwyTv.setText("����");
        depthRatifyTv.setText("����");
    }

    /**
     * ��ֹʱ��ؼ�����
     *
     * @param sp spinner�ؼ�
     * @param vl ѡ��ֵ
     */
    private void setSprSelect(Spinner sp, String vl) {
        SpinnerAdapter apsAdapter = (SpinnerAdapter) sp.getAdapter( );
        int k = apsAdapter.getCount( );
        for (int i = 0; i < k; i++) {
            if (vl.equals(apsAdapter.getItem(i).toString( ))) {
                sp.setSelection(i, true);// Ĭ��ѡ����
                break;
            }
        }
    }

    /**
     * ��ͼ��ʼ��
     */
    private void initView() {
        mSubmit = (TextView) findViewById(R.id.submit_button);
        mCancel = (TextView) findViewById(R.id.cancel_button);
        backImage = (ImageView) findViewById(R.id.back_imageView);
        sexGroup = (RadioGroup) findViewById(R.id.sex_radiogroup);
        age_min_seek = (SeekBar) findViewById(R.id.min_age_seekBar);
        age_max_seek = (SeekBar) findViewById(R.id.max_age_seekBar);
        content_text = (EditText) findViewById(R.id.content_text);
        age_min = (EditText) findViewById(R.id.min_age_text);
        age_max = (EditText) findViewById(R.id.max_age_text);
        nationTv = (TextView) findViewById(R.id.nation_text_tv);
        fullEduTv = (TextView) findViewById(R.id.fullEdu_text_tv);
        jobEduTv = (TextView) findViewById(R.id.job_text_tv);
        higEduTv = (TextView) findViewById(R.id.hedu_text_tv);
        preTv = (TextView) findViewById(R.id.pre_text_tv);
        resTv = (TextView) findViewById(R.id.res_text_tv);
        jobYearTv = (TextView) findViewById(R.id.job_year_text_tv);
        orgTv = (TextView) findViewById(R.id.org_text_tv);
        posYearTv = (TextView) findViewById(R.id.pos_year_text_tv);
        ageTv = (TextView) findViewById(R.id.age_text_tv);
        famTv = (TextView) findViewById(R.id.fam_text_tv);
        majorTv = (TextView) findViewById(R.id.major_text_tv);
        schoolTv = (TextView) findViewById(R.id.school_text_tv);
        degreeTv = (TextView) findViewById(R.id.degree_text_tv);
        quaTv = (TextView) findViewById(R.id.qua_text_tv);
        workYearTv = (TextView) findViewById(R.id.work_year_text_tv);
        partyYearTv = (TextView) findViewById(R.id.party_year_text_tv);
        jobDescTv =(TextView) findViewById(R.id.job_desc_text_tv);
        nativeplaceTv =(TextView) findViewById(R.id.native_text_tv);
        birthplaceTv = (TextView) findViewById(R.id.birthplace_text_tv);
        familyNameTv = (TextView) findViewById(R.id.fam_name_text_tv);
        stateTv = (TextView) findViewById(R.id.state_text_tv);
        examineTv = (TextView) findViewById(R.id.examine_text_tv);
        assessTv = (TextView) findViewById(R.id.assess_text_tv);
        punishTv = (TextView) findViewById(R.id.punish_text_tv);
        assessYearTv = (TextView) findViewById(R.id.assess_year_text_tv);
        punishTypeTv = (TextView) findViewById(R.id.punish_type_text_tv);
        userNumberTv = (TextView) findViewById(R.id.user_number_text_tv);
        userRankTv = (TextView) findViewById(R.id.user_rank_text_tv);
        marshalsTv = (TextView) findViewById(R.id.marshals_text_tv);
        gunTv = (TextView) findViewById(R.id.gun_text_tv);
        userTypeTv =(TextView) findViewById(R.id.user_type_text_tv);
        jobLxTv =(TextView) findViewById(R.id.job_lx_text_tv);
        depthLxTv =(TextView) findViewById(R.id.depth_lx_text_tv);
        userCodeTv = (TextView) findViewById(R.id.user_code_text_tv);
        jobGwyTv =(TextView) findViewById(R.id.job_gwy_text_tv);
        depthRatifyTv =(TextView) findViewById(R.id.depth_ratify_text_tv);

        nationLay = (LinearLayout) findViewById(R.id.nation_layout);
        fullEduLay = (LinearLayout) findViewById(R.id.full_hjm_edu_layout);
        jobEduLay = (LinearLayout) findViewById(R.id.job_edu_layout);
        higEduLay = (LinearLayout) findViewById(R.id.hedu_edu_layout);
        preLay = (LinearLayout) findViewById(R.id.pre_layout);
        resLay = (LinearLayout) findViewById(R.id.res_layout);
        jobYearLay = (LinearLayout) findViewById(R.id.job_year_layout);
        orgLay = (LinearLayout) findViewById(R.id.org_layout);
        posYearLay = (LinearLayout) findViewById(R.id.pos_year_layout);
        ageLay = (LinearLayout) findViewById(R.id.age_layout);
        famLay = (LinearLayout) findViewById(R.id.fam_layout);
        majorLay = (LinearLayout) findViewById(R.id.major_layout);
        schoolLay = (LinearLayout) findViewById(R.id.school_layout);
        degreeLay = (LinearLayout) findViewById(R.id.degree_layout);
        quaLay = (LinearLayout) findViewById(R.id.qua_layout);
        yearSpr = (Spinner) findViewById(R.id.year_spr);
        monthSpr = (Spinner) findViewById(R.id.month_spr);
        workYearLay = (LinearLayout) findViewById(R.id.work_year_layout);
        partyYearLay = (LinearLayout) findViewById(R.id.party_year_layout);
        jobDescLay = (LinearLayout) findViewById(R.id.job_desc_layout);
        nativeplaceLay = (LinearLayout) findViewById(R.id.native_layout);
        birthplaceLay = (LinearLayout) findViewById(R.id.birthplace_layout);
        familyNameLay = (LinearLayout) findViewById(R.id.fam_name_layout);
        stateLay = (LinearLayout) findViewById(R.id.state_layout);
        examineLay =(LinearLayout) findViewById(R.id.examine_layout);
        assessLay = (LinearLayout) findViewById(R.id.assess_layout);
        punishLay = (LinearLayout) findViewById(R.id.punish_layout);
        punishTypeLay = (LinearLayout) findViewById(R.id.punish_type_layout);
        assessYearLay = (LinearLayout) findViewById(R.id.assess_year_layout);
        userNumberLay = (LinearLayout) findViewById(R.id.user_number_layout);
        userRankLay = (LinearLayout) findViewById(R.id.user_rank_layout);
        marshalsLay = (LinearLayout) findViewById(R.id.marshals_layout);
        gunLay = (LinearLayout) findViewById(R.id.gun_layout);
        userTypeLay =(LinearLayout) findViewById(R.id.user_type_layout);
        jobLxLay = (LinearLayout) findViewById(R.id.job_lx_layout);
        depthLxLay =(LinearLayout) findViewById(R.id.depth_lx_layout);
        userCodeLay = (LinearLayout) findViewById(R.id.user_code_layout);
        jobGwyLay = (LinearLayout) findViewById(R.id.job_gwy_layout);
        depthRatifyLay = (LinearLayout) findViewById(R.id.depth_ratify_layout);

        nationLay.setOnClickListener(this);
        fullEduLay.setOnClickListener(this);
        jobEduLay.setOnClickListener(this);
        higEduLay.setOnClickListener(this);
        preLay.setOnClickListener(this);
        resLay.setOnClickListener(this);
        jobYearLay.setOnClickListener(this);
        orgLay.setOnClickListener(this);
        posYearLay.setOnClickListener(this);
        ageLay.setOnClickListener(this);
        famLay.setOnClickListener(this);
        majorLay.setOnClickListener(this);
        schoolLay.setOnClickListener(this);
        degreeLay.setOnClickListener(this);
        quaLay.setOnClickListener(this);
        age_min_seek.setOnSeekBarChangeListener(this);
        age_max_seek.setOnSeekBarChangeListener(this);
        workYearLay.setOnClickListener(this);
        partyYearLay.setOnClickListener(this);
        jobDescLay.setOnClickListener(this);
        birthplaceLay.setOnClickListener(this);
        nativeplaceLay.setOnClickListener(this);
        familyNameLay.setOnClickListener(this);
        stateLay.setOnClickListener(this);
        examineLay.setOnClickListener(this);
        assessLay.setOnClickListener(this);
        punishLay.setOnClickListener(this);
        assessYearLay.setOnClickListener(this);
        punishTypeLay.setOnClickListener(this);
        userNumberLay.setOnClickListener(this);
        userRankLay.setOnClickListener(this);
        marshalsLay.setOnClickListener(this);
        gunLay.setOnClickListener(this);
        userTypeLay.setOnClickListener(this);
        jobLxLay.setOnClickListener(this);
        depthLxLay.setOnClickListener(this);
        userCodeLay.setOnClickListener(this);
        jobGwyLay.setOnClickListener(this);
        depthRatifyLay.setOnClickListener(this);

        DialogUtil.setOnDialogResultListener(this);
    }

    /**
     * װ����������
     */
    private void loaderSearchParam() {

        if (param != null) {
            param.setContent(content_text.getText( ).toString( ));
            param.setAge_min(Integer.parseInt(age_min.getText( ).toString( )));
            param.setAge_max(Integer.parseInt(age_max.getText( ).toString( )));
            param.setSt_time(getStTime( ));
            Log.i("outStTime", param.getSt_time( ));
            param.setSex(getRadioGroup(sexGroup));

        }
    }

    /**
     * ��ȡͳ��ʱ��
     *
     * @return
     */
    private String getStTime() {
        String y = StringUtil.getNowDataYear( );
        String m = StringUtil.getNowDataMonth( );
        try {
            y = yearSpr.getSelectedItem( ).toString( );
            m = monthSpr.getSelectedItem( ).toString( );
            y = StringUtil.frontCompWithZore(Integer.parseInt(y), 4);
            m = StringUtil.frontCompWithZore(Integer.parseInt(m), 2);

        } catch (Exception ex) {
            ex.getStackTrace( );
        }
        return y + "-" + m + "-01";
    }

    /**
     * ��ʼ��Ĭ������
     */

    private void initData() {

        param = new SearchParams( );
        param.setAge_min(Setting.MIN_AGE);
        param.setAge_max(Setting.MAX_AGE);
        param.setSex(0);
        param.setSt_time(getStTime( ));
        setRadioGroup(sexGroup, param.getSex( ));
        year = new ArrayList <String>( );
        month = new ArrayList <String>( );
        for (int i = 1; i < 13; i++) {
            month.add(String.valueOf(i));
        }
        Calendar now = Calendar.getInstance( );
        int yr = now.get(Calendar.YEAR);
        int sYear = yr - 10;
        for (int i = sYear; i < sYear + 21; i++) {
            year.add(String.valueOf(i));
        }
        // setRadioGroup(depthGroup,param.getDepth());

    }

    private void resetRadioGroup(RadioGroup rg) {
        for (int i = 0; i < rg.getChildCount( ); i++) {
            View v = rg.getChildAt(i);
            if (v instanceof RadioButton) {
                RadioButton rb = (RadioButton) v;
                if (rb.getText( ).equals("ȫ��")) {
                    rb.setChecked(true);
                }
            }
        }
    }

    /**
     * ��ȡRadioGroup �������
     *
     * @param rg
     * @return
     */
    private int getRadioGroup(RadioGroup rg) {
        int result = 0;

        for (int i = 0; i < rg.getChildCount( ); i++) {
            View v = rg.getChildAt(i);
            if (v instanceof RadioButton) {
                RadioButton rb = (RadioButton) v;
                if (rb.isChecked( )) {

                    result = i;
                }
            }
        }
        return result;
    }

    /**
     * RadioGroup���
     *
     * @param value
     */
    private void setRadioGroup(RadioGroup rg, int value) {

        for (int i = 0; i < rg.getChildCount( ); i++) {
            View v = rg.getChildAt(i);
            if (v instanceof RadioButton) {
                RadioButton rb = (RadioButton) v;
                if (i == value) {
                    rb.setChecked(true);
                }
            }
        }

    }

    @Override
    public void onProgressChanged(SeekBar seek, int progress, boolean user) {
        Log.i(TAG, String.valueOf(progress));
        switch (seek.getId( )) {
            case R.id.min_age_seekBar:
                setEditValue(age_min, Setting.MIN_AGE, progress);
                Log.i(TAG, String.valueOf(progress));
                break;
            case R.id.max_age_seekBar:
                setEditValue(age_max, Setting.MIN_AGE, progress);
                break;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seek) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seek) {
        // TODO Auto-generated method stub

    }

    private void setEditValue(EditText et, int startValue, int value) {
        et.setText(String.valueOf(startValue + value));
    }

    public void onClick(View v) {

        switch (v.getId( )) {
            case R.id.nation_layout:
                DialogUtil.createNationDialog(this);
                break;
            //case R.id.pol_layout:
            //	DialogUtil.createPoliticalDialog(this);
            //	break;
            case R.id.full_hjm_edu_layout:
                DialogUtil.createTreeFullEduDialog(this);
                break;
            //case R.id.fdeg_edu_layout:
            //	DialogUtil.createFullDegreeDialog(this);
            //	break;
            case R.id.job_edu_layout:
                DialogUtil.createTreeJobEduDialog(this);
                break;
            case R.id.hedu_edu_layout:
                DialogUtil.createTreeHighestEduDialog(this);
                break;
            case R.id.pre_layout:
                DialogUtil.createPrePositionDialog(this);
                break;

            case R.id.res_layout:
                DialogUtil.createResumeDialog(this);
                break;
            case R.id.job_year_layout:
                DialogUtil.createJobYear(this);
                break;
            case R.id.pos_year_layout:
                DialogUtil.createPosYear(this);
                break;
            case R.id.fam_layout:
                DialogUtil.createfamilyDialog(this);
                break;
            case R.id.org_layout:
                DialogUtil.createTreeGroupDialog(this);
                break;
            case R.id.age_layout:
                DialogUtil.createFamilyAgeDialog(this);
                break;
            case R.id.major_layout:
                DialogUtil.createMajorDialog(this);
                break;
            case R.id.school_layout:
                DialogUtil.createSchoolDialog(this);
                break;
            case R.id.degree_layout:
                DialogUtil.createDegreeDialog(this);
                break;
            case R.id.qua_layout:
                DialogUtil.createQualificationDialog(this);
                break;
            case R.id.work_year_layout:
                DialogUtil.createWorkYear(this);
                break;
            case R.id.party_year_layout:
                DialogUtil.createPartyYear(this);
                break;
            case R.id.job_desc_layout:
                DialogUtil.createJobDescDialog(this);
                break;
            case R.id.birthplace_layout:
                DialogUtil.createBirthDialog(this);
                break;
            case R.id.native_layout:
                DialogUtil.createNativeDialog(this);
                break;
            case R.id.fam_name_layout:
                DialogUtil.createFamilyNameDialog(this);
                break;
            case R.id.state_layout:
                DialogUtil.createStateDialog(this);
                break;
            case R.id.examine_layout:
                DialogUtil.createExamineDialog(this);
                break;
            case R.id.assess_layout:
                DialogUtil.createAssessResultDialog(this);
                break;
            case R.id.assess_year_layout:
                DialogUtil.createAssessYearDialog(this);
                break;
            case R.id.punish_layout:
                DialogUtil.createPunishNameDialog(this);
                break;
            case R.id.punish_type_layout:
                DialogUtil.createPunishTypeDialog(this);
                break;
            case R.id.user_number_layout:
                DialogUtil.createUserNumberDialog(this);
                break;
            case R.id.user_rank_layout:
                DialogUtil.createUserRankDialog(this);
                break;
            case R.id.marshals_layout:
                DialogUtil.createMarshalsDialog(this);
                break;
            case R.id.gun_layout:
                DialogUtil.createGunLicenceDialog(this);
                break;
            case R.id.user_type_layout:
                DialogUtil.createUserTypeDialog(this);
                break;
            case R.id.job_lx_layout:
                DialogUtil.createJobLxYear(this);
                break;
            case R.id.depth_lx_layout:
                DialogUtil.createDepthLxYear(this);
                break;
            case R.id.user_code_layout:
                DialogUtil.createUserCodeDialog(this);
                break;
            case R.id.job_gwy_layout:
                DialogUtil.createJobGwyDialog(this);
                break;
            case R.id.depth_ratify_layout:
                DialogUtil.createDepthRatifyYear(this);
                break;

        }
    }

    /**
     * ���ÿؼ���ʾ��Ϣ
     *
     * @param tv   textView �ؼ�
     * @param data ����������Դ
     */
    public void setContentText(TextView tv, List <SearchItem> data) {
        tv.setText("����");
        if (data != null && data.size( ) > 0) {
            String title = "";
            for (SearchItem item : data) {
                title += item.getDisplay( ) + "��";
            }
            tv.setText(title);
        }
    }

    public void setContentText2(TextView tv, List <SearchItem> data, String dc) {
        tv.setText("����");
        if (data != null && data.size( ) == 2) {
            String title = data.get(0).getValue( ) + "��" + data.get(1).getValue( ) + dc;
            tv.setText(title);
        }
    }

    /**
     * �Ի���ѡ�񷵻ػص�����¼�
     *
     * @param n    �ص���ǩ
     * @param data ���ؽ����
     */
    @Override
    public void OnDialogResult(String n, List <SearchItem> data) {
        // TODO Auto-generated method stub
        if (n.equals("nation")) {
            param.setNation(data);
            setContentText(nationTv, data);
        }  else if (n.equals("fullEdu")) {
            param.setFullEdu(data);
            setContentText(fullEduTv, data);
        } else if (n.equals("fullDegree")) {
            param.setFullDegree(data);
            setContentText(degreeTv, data);
        } else if (n.equals("jobEdu")) {
            param.setJobEdu(data);
            setContentText(jobEduTv, data);
        } else if (n.equals("jobDegree")) {
            param.setJobDegree(data);
            setContentText(degreeTv, data);
        } else if (n.equals("highestEdu")) {
            param.setHighestEdu(data);
            setContentText(higEduTv, data);
        } else if (n.equals("highestDegree")) {
            param.setHighestDegree(data);
            setContentText(degreeTv, data);
        } else if (n.equals("prePosition")) {
            param.setPosition(data);
            setContentText(preTv, data);
        } else if (n.equals("resumeSearch")) {
            param.setResumeKey(data);
            setContentText(resTv, data);
        } else if (n.equals("jobYear")) {
            param.setJobYear(data);
            setContentText2(jobYearTv, data, "��");
        } else if (n.equals("posYear")) {
            param.setPosYear(data);
            setContentText2(posYearTv, data, "��");
        }  else if (n.equals("jobOrg")) {
            param.setOrgs(data);
            setContentText(orgTv, data);
        } else if (n.equals("familyAge")) {
            param.setFamilyAge(data);
            setContentText2(ageTv, data, "��");
        } else if (n.equals("familySearch")) {
            param.setFamilyJob(data);
            setContentText(famTv, data);
        } else if (n.equals("major")) {
            param.setMajor(data);
            setContentText(majorTv, data);
        } else if (n.equals("fullSchool")) {
            param.setFullSchool(data);
            setContentText(schoolTv, data);
        } else if (n.equals("jobSchool")) {
            param.setJobSchool(data);
            setContentText(schoolTv, data);
        } else if (n.equals("qualification")) {
            param.setQualification(data);
            setContentText(quaTv, data);
        }else if (n.equals("workYear")){
            param.setWorkYear(data);
            setContentText(workYearTv,data);
        }else if(n.equals("partyYear")){
            param.setPartyYear(data);
            setContentText(partyYearTv,data);
        }else if(n.equals("jobDescSearch")){
            param.setJobDesc(data);
            setContentText(jobDescTv,data);
        }else if(n.equals("familyNameSearch")){
            param.setFamilyName(data);
            setContentText(familyNameTv,data);
        }else if(n.equals("birthSearch")){
            param.setBirthplace(data);
            setContentText(birthplaceTv,data);
        }else if(n.equals("nativeSearch")){
            param.setNativeplace(data);
            setContentText(nativeplaceTv,data);
        }else if(n.equals("state")){
            param.setState(data);
            setContentText(stateTv,data);
        }else if(n.equals("examineSearch")){
            param.setExamine(data);
            setContentText(examineTv,data);
        }else if(n.equals("assessYear")){
            param.setAssessYear(data);
            setContentText(assessYearTv,data);
        }else if(n.equals("assessResult")){
            param.setAssessResult(data);
            setContentText(assessTv,data);
        }else if(n.equals("punishNameSearch")){
            param.setPunishName(data);
            setContentText(punishTv,data);
        }else if(n.equals("punishType")){
            param.setPunishType(data);
            setContentText(punishTypeTv,data);
        }else if(n.equals("userNumber")){
            param.setUserNumber(data);
            setContentText(userNumberTv,data);
        }else if(n.equals("userRank")){
            param.setUserRank(data);
            setContentText(userRankTv,data);
        }else if(n.equals("marshals")){
            param.setMarshals(data);
            setContentText(marshalsTv,data);
        }else if(n.equals("gunLicence")){
            param.setGunLicence(data);
            setContentText(gunTv,data);
        }else if (n.equals("userType")){
            param.setUserType(data);
            setContentText(userTypeTv,data);
        }else if (n.equals("jobLx")){
            param.setJobLxYear(data);
            setContentText2(jobLxTv, data, "��");
        }else if (n.equals("depthLx")){
            param.setDepthLxYear(data);
            setContentText2(depthLxTv, data, "��");
        }else if (n.equals("userCode")){
            param.setUserCode(data);
            setContentText(userCodeTv, data);
        }else if (n.equals("jobGwy")){
            param.setJobGwy(data);
            setContentText(jobGwyTv, data);
        }else if (n.equals("depthRatifyYear")){
            param.setRatifyYear(data);
            setContentText(depthRatifyTv,data);
        }
    }


}
