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
import com.cxht.app.GroupNumberActivity;
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
 * 职数.Fragment
 * Created by HeJiaMing on 2019/10/22 16:18
 * E-Mail Address：1774690@qq.com
 */
public class GroupJob extends Fragment {
    private List<GroupInfo> mDatas;
    private ListView mTree;
    private TextView loadTv;
    private TreeListViewAdapter mAdapter;
    private Context context;
    private View view;
    /**
     * 创建新实例
     *
     * @return
     */
    // private static ArticleFragment fragment;
    public static GroupJob newInstance() {
        Bundle bundle = new Bundle( );
        GroupJob fragment = new GroupJob( );
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private GroupInfo execFind(String nodeId){
        if (mDatas!=null && mDatas.size()>0){
            for(GroupInfo item:mDatas){
                if(item.getId() == nodeId) return item;
            }
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = getActivity( );
        view = LayoutInflater.from(context).inflate(R.layout.group_list_tree, null);
        mTree = (ListView) view.findViewById(R.id.mlistView);
        loadTv = (TextView) view.findViewById(R.id.loadTv);
        initDatas( ); //数据初始化
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
                            Toast.makeText(context, "未配置职数信息",Toast.LENGTH_SHORT).show();
                        }


                }

            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        mTree.setAdapter(mAdapter);
        return view;
    }

    private void initDatas() {
        List<GroupBean> gbs = GonganApplication.getGroupBeanList(context); //机构信息
        List<GroupNumber> gns = GroupNumberDao.getList(); //职数信息
        mDatas= GroupNumberUtils.execute(gbs,gns);
        if (mDatas!= null){
            loadTv.setVisibility(View.GONE);
            mTree.setVisibility(View.VISIBLE);
        }else{
            loadTv.setText("没有机构数据！");
        }
    }
}