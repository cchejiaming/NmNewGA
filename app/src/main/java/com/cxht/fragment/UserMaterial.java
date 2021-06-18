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
 * 文件材料页
 * Created by HeJiaMing on 2019/12/26 10:24
 * E-Mail Address：1774690@qq.com
 */
public class UserMaterial extends Fragment {
    private int user_id;
    private Context context;
    private View view;
    private ListView basicListView; // 视图列表
    private List <PdfItem> data;//数据对象
    private TextView topTv;
    private PdfItemAdapter adapter;
    private User user;// 用户对象

    /**
     * 创建新实例
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
        topTv.setText("文件材料");
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
                    Toast.makeText(context, "请安装office", Toast.LENGTH_SHORT).show( );
                }
            }

        });
        return view;
    }

    /**
     * 判断Intent 是否存在 防止崩溃
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
     * 打开文件材料方法
     *
     * @param url  文件路径
     * @param type 文件类型 0 pdf ,1 word ,2 execl
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
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//这里加入flag
                intent.setDataAndType(uri, fileType);
            }

        }

        return intent;
    }

    /**
     * 获取文件类型定义字符串
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
            String userCode = user.getUser_code( ); //身份证号码
            File file = new File(GonganApplication.getPdfPath(context)); //pdf文件夹
            data = new ArrayList <PdfItem>( );


            //扫描pdf文件夹，把符合要求的数据添加的集合中
            HashMap <String, String> pdfInfo = PdfDao.getPdfInfo( );
            File[] files = file.listFiles( );
            //循环扫描文件找出和用户身份证号码匹配的文件
            for (File f : files) {
                if (f.getName( ) != null) {
                    String fn = f.getName( ).substring(0, f.getName( ).lastIndexOf("."));
                    int idx = fn.indexOf(userCode);
                    if (idx >= 0) {
                        //找到匹配文件
                        try {
                            String filePath = f.getPath( );
                            String fileName = null;
                            String[] arFile = fn.split("-");
                            if (arFile.length > 1) {
                                String c = arFile[1];
                                fileName = pdfInfo.get(c);
                                if (fileName == null) fileName = "未知分类材料";
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
