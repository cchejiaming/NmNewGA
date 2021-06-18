package com.cxht.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxht.adapter.PdfItemAdapter;
import com.cxht.bean.PdfItem;
import com.cxht.config.GonganApplication;
import com.cxht.dao.PdfDao;
import com.cxht.dao.UserDao;
import com.cxht.entity.User;
import com.gongan.manage.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * �ļ�����ҳ
 * Created by HeJiaMing on 2019/12/26 10:24
 * E-Mail Address��1774690@qq.com
 */
public class UserMaterial extends Fragment {
    private int user_id;
    private Context context;
    private View view;
    private ListView basicListView; // ��ͼ�б�
    private List <PdfItem> data;//���ݶ���
    private TextView topTv;
    private PdfItemAdapter adapter;
    private User user;// �û�����

    /**
     * ������ʵ��
     *
     * @return
     */
    public static UserMaterial newInstance(int userId) {
        Bundle bundle = new Bundle( );
        bundle.putInt("UserId", userId);
        UserMaterial fragment = new UserMaterial( );
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle args = getArguments( );
        this.user_id = args != null ? args.getInt("UserId", 0) : 0;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = getActivity( );
        view = LayoutInflater.from(context).inflate(R.layout.fragment_resume,
                null);
        basicListView = (ListView) view.findViewById(R.id.resume_ListView);
        topTv = (TextView) view.findViewById(R.id.top_title_tv);
        topTv.setText("�ļ�����");
        initData( );
        basicListView.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {

            @Override
            public void onItemClick(AdapterView <?> arg0, View view,
                                    int position, long arg3) {
                PdfItem ar = data.get(position);
                int type = getFileType(ar.getValue( ));
                Intent intent = openFileIntent(ar.getValue( ), type);
                if (isIntentAvailable(context, intent)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "�밲װoffice", Toast.LENGTH_SHORT).show( );
                }
            }

        });
        return view;
    }

    /**
     * �ж�Intent �Ƿ���� ��ֹ����
     *
     * @param context
     * @param intent
     * @return
     */
    private static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager( );
        @SuppressLint("WrongConstant") List <ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size( ) > 0;
    }

    private int getFileType(String path) {
        int t = -1;
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        switch (suffix) {
            case "pdf":
                t = 0;
                break;
            case "doc":
                t = 1;
                break;
            case "xls":
                t = 2;
                break;
            case "docx":
                t = 3;
            case "xlsx":
                t = 4;
                break;

        }
        return t;
    }

    /**
     * ���ļ����Ϸ���
     *
     * @param url  �ļ�·��
     * @param type �ļ����� 0 pdf ,1 word ,2 execl
     * @return
     */
    public Intent openFileIntent(String url, int type) {
        Intent intent = null;
        File file = new File(url);
        if (file.exists( )) {
            String fileType = getFileType(type);
            if (fileType != null) {
                intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //Uri uri = Uri.fromFile(file);
                Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext( ).getPackageName( ) + ".provider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//�������flag
                intent.setDataAndType(uri, fileType);
            }

        }

        return intent;
    }

    /**
     * ��ȡ�ļ����Ͷ����ַ���
     *
     * @param type
     * @return
     */
    private String getFileType(int type) {
        String ret = null;
        switch (type) {
            case 0:
                ret = "application/pdf";
                break;
            case 1:
                ret = "application/msword";
                break;
            case 2:
                ret = "applicationnd.ms-excel";
                break;
            case 3:
                ret = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                break;
            case 4:
                ret = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;

        }
        return ret;
    }

    private void initData() {
        // TODO Auto-generated method stub
        user = UserDao.queryUser(user_id);

        if (user != null) {
            String userCode = user.getUser_code( ); //���֤����
            File file = new File(GonganApplication.getPdfPath(context)); //pdf�ļ���
            data = new ArrayList <PdfItem>( );


            //ɨ��pdf�ļ��У��ѷ���Ҫ���������ӵļ�����
            HashMap <String, String> pdfInfo = PdfDao.getPdfInfo( );
            File[] files = file.listFiles( );
            //ѭ��ɨ���ļ��ҳ����û����֤����ƥ����ļ�
            for (File f : files) {
                if (f.getName( ) != null) {
                    String fn = f.getName( ).substring(0, f.getName( ).lastIndexOf("."));
                    int idx = fn.indexOf(userCode);
                    if (idx >= 0) {
                        //�ҵ�ƥ���ļ�
                        try {
                            String filePath = f.getPath( );
                            String fileName = null;
                            String[] arFile = fn.split("-");
                            if (arFile.length > 1) {
                                String c = arFile[1];
                                fileName = pdfInfo.get(c);
                                if (fileName == null) fileName = "δ֪�������";
                                data.add(new PdfItem(filePath, fileName));
                            }
                        } catch (Exception e) {
                            Log.e("UserMaterial", e.toString( ));
                        }

                    }
                }

            }
            if (data != null) {
                adapter = new PdfItemAdapter(context);
                adapter.addAll(data);
                basicListView.setAdapter(adapter);
            }

        }
    }
}
