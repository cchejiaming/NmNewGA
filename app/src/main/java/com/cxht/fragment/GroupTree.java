package com.cxht.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.cxht.adapter.GroupTreeAdapter;
import com.cxht.app.OrganizationMainActivity;
import com.cxht.bean.GroupBean;
import com.cxht.config.GonganApplication;
import com.cxht.tree.Node;
import com.cxht.tree.TreeListViewAdapter;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 组织结构树.Fragment
 * Created by HeJiaMing on 2019/10/22 16:18
 * E-Mail Address：1774690@qq.com
 */
public class GroupTree extends Fragment {
    private List <GroupBean> mDatas = new ArrayList<GroupBean>();
    private ListView mTree;
    private TextView loadTv,hintTv;
    private TreeListViewAdapter mAdapter;
    private Context context;
    private View view;

    /**
     * 创建新实例
     *
     * @return
     */
    // private static ArticleFragment fragment;
    public static GroupTree newInstance() {
        Bundle bundle = new Bundle( );
        GroupTree fragment = new GroupTree( );
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = getActivity( );
        initView();
        initDatas();
        initListener();
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 视图初始化
     */
    private void initView(){
        view = LayoutInflater.from(context).inflate(R.layout.group_list_tree, null);
        mTree = (ListView) view.findViewById(R.id.mlistView);
        loadTv = (TextView) view.findViewById(R.id.loadTv);
        hintTv = (TextView) view.findViewById(R.id.hintTv);
        hintTv.setVisibility(View.GONE);

    }

    /**
     * 监听事件初始化
     */
    private void initListener(){
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

                        String items = String.valueOf(node.getId());
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


    private void initDatas() {
        mDatas= GonganApplication.getGroupBeanList(context);
        if (mDatas!= null){
            loadTv.setVisibility(View.GONE);
            mTree.setVisibility(View.VISIBLE);
        }else{
            loadTv.setText("没有机构数据！");
        }
    }
}