package com.cxht.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cxht.adapter.GroupTreeAdapter;
import com.cxht.bean.GroupBean;
import com.cxht.config.GonganApplication;
import com.cxht.tree.Node;
import com.cxht.tree.TreeListViewAdapter;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门查询页面
 */
public class GroupInfoActivity extends BaseActivity {
    private List <GroupBean> mDatas = new ArrayList <>();
    private ListView mTree;
    private TextView loadTv,hintTv;
    private TreeListViewAdapter mAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        this.context = GroupInfoActivity.this;
        initView();
        initDatas();
        initListener();
    }

    /**
     * 数据初始化
     */
    private void initDatas() {
        mDatas= GonganApplication.getGroupBeanList(context);
        if (mDatas!= null){
            loadTv.setVisibility(View.GONE);
            mTree.setVisibility(View.VISIBLE);
        }else{
            loadTv.setText("没有机构数据！");
        }
    }
    /**
     * 视图初始化
     */
    private void initView() {
        mTree = (ListView) findViewById(R.id.mlistView);
        loadTv = (TextView) findViewById(R.id.loadTv);
        hintTv = (TextView) findViewById(R.id.hintTv);
        hintTv.setVisibility(View.GONE);
    }
    /**
     * 监听事件初始化
     */
    private void initListener() {
        try
        {
            mAdapter = new GroupTreeAdapter <GroupBean>(mTree, context, mDatas, 1);
            mAdapter = new GroupTreeAdapter <GroupBean>(mTree, context, mDatas, 1);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener(){
                @Override
                public void onClick(Node node, int position) {
                    // TODO Auto-generated method stub

                    // String items = StringUtil.getSqlInItems(node.getId());
                    if (node.isLeaf()) {

                        String items = StringUtil.getSqlInItems(node.getId(), context);
                        Toast.makeText(context, node.getName(),
                                Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(context, OrganizationMainActivity.class);
                        intent.putExtra("SqlItems",items);
                        intent.putExtra("Title", node.getName());
                        intent.putExtra("GroupId",node.getId());
                        startActivity(intent);

                    }


                }

            });
            mAdapter.setOnTreeNodeLongClickListener(new TreeListViewAdapter.OnTreeNodeLongClickListener(){

                @Override
                public void onLongClick(Node node, int position) {
                    // TODO Auto-generated method stub
                    if (!node.isLeaf()) {

                        String items = "'"+node.getId()+"'";
                        Toast.makeText(context, node.getName(),Toast.LENGTH_SHORT).show();
                        Intent intent  = new Intent(context,OrganizationMainActivity.class);
                        intent.putExtra("SqlItems",items);
                        intent.putExtra("Title", node.getName());
                        intent.putExtra("GroupId",node.getId());
                        startActivity(intent);

                    }
                }

            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        mTree.setAdapter(mAdapter);
    }
}