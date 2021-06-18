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
import com.cxht.bean.GroupInfo;
import com.cxht.config.GonganApplication;
import com.cxht.dao.GroupNumberDao;
import com.cxht.entity.GroupNumber;
import com.cxht.tree.Node;
import com.cxht.tree.TreeListViewAdapter;
import com.cxht.unit.GroupNumberUtils;
import com.gongan.manage.R;

import java.util.List;

/**
 * ְ�������б���ҳ��
 */
public class GroupNumberTreeActivity extends BaseActivity {
    private List <GroupInfo> mDatas;
    private ListView mTree;
    private TextView loadTv;
    private TreeListViewAdapter mAdapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_number_tree);
        context = GroupNumberTreeActivity.this;
        initView();
        initData();
        initListener();
    }

    /**
     * ���ݳ�ʼ��
     */
    private void initData() {
        List<GroupBean> gbs = GonganApplication.getGroupBeanList(context); //������Ϣ
        List<GroupNumber> gns = GroupNumberDao.getList(); //ְ����Ϣ
        mDatas= GroupNumberUtils.execute(gbs,gns);
        if (mDatas!= null){
            loadTv.setVisibility(View.GONE);
            mTree.setVisibility(View.VISIBLE);
        }else{
            loadTv.setText("û�л������ݣ�");
        }
    }

    /**
     * ��ͼ��ʼ��
     */
    private void initView() {
        mTree = (ListView) findViewById(R.id.mlistView);
        loadTv = (TextView) findViewById(R.id.loadTv);
    }

    /**
     * �¼���ʼ��
     */
    private void initListener() {
        try
        {
            mAdapter = new GroupTreeAdapter <GroupInfo>(mTree, context, mDatas, 1);;
            mAdapter.setOnTreeNodeLongClickListener(new TreeListViewAdapter.OnTreeNodeLongClickListener(){

                @Override
                public void onLongClick(Node node, int position) {
                    // TODO Auto-generated method stub
                    GroupInfo item =execFind(node.getId());
                    if(item.getItems()!=null && item.getItems().size()>0){
                        Intent intent  = new Intent(context, GroupNumberActivity.class);
                        intent.putExtra("Title", node.getName());
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("GroupInfo",item);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, "δ����ְ����Ϣ",Toast.LENGTH_SHORT).show();
                    }

                }

            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        mTree.setAdapter(mAdapter);
    }
    private GroupInfo execFind(String nodeId){
        if (mDatas!=null && mDatas.size()>0){
            for(GroupInfo item:mDatas){
                if(item.getId() == nodeId) return item;
            }
        }
        return null;
    }
}