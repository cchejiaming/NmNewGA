package com.cxht.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.cxht.adapter.GroupNumberAdapter;
import com.cxht.bean.GroupInfo;
import com.cxht.bean.GroupNumberItem;
import com.gongan.manage.R;
import java.util.List;

public class GroupNumberActivity extends BaseActivity {
   private GroupInfo groupInfo; //机构信息及职数配置信息
    private ListView basicListView; // 视图列表
    private GroupNumberAdapter adapter; //列表适配器
    private List<GroupNumberItem> data;//数据源
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_number);
        groupInfo = (GroupInfo) getIntent().getSerializableExtra("GroupInfo");
        initView();
        bindData();
    }
    private void initView(){
        context = GroupNumberActivity.this;
        basicListView = (ListView) findViewById(R.id.basic_ListView);
    }
    private void bindData(){
        if (groupInfo!=null){
            if(groupInfo.getItems()!=null && groupInfo.getItems().size()>0){
                data = groupInfo.getItems();
                adapter = new GroupNumberAdapter(context,data);
                basicListView.setAdapter(adapter);
                basicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            GroupNumberItem item = data.get(position);
                            String where = item.getAndWhere();
                            Intent intent = new Intent(context, OrganizationMainActivity.class);
                            intent.putExtra("AndWhere", where);
                            intent.putExtra("actionTitle", data.get(position).getName());
                            startActivity(intent);

                        }

                });

            }

        }
    }
}