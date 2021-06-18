package com.cxht.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cxht.bean.GroupNumberItem;
import com.cxht.bean.InfoItem;
import com.gongan.manage.R;

import java.util.List;

/**
 * 职数配置适配器
 * Created by HeJiaMing on 2020/11/19 10:24
 * E-Mail Address：1774690@qq.com
 */
public class GroupNumberAdapter extends BaseAdapter {
    private String TAG = "GroupNumberAdapter";
    private List <GroupNumberItem> data;
    private Context context;
    private LayoutInflater inflater;

    public GroupNumberAdapter(Context context, List<GroupNumberItem> data) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;

    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        InfoHolder viewHolder;
        GroupNumberItem item = data.get(position);
        if (position < data.size()) {

            if (view == null) {
                viewHolder = new InfoHolder( );
                view = inflater .inflate(R.layout.group_number_item_text, null);
                viewHolder.titleText = (TextView) view .findViewById(R.id.titleText);
                viewHolder.descText1 = (TextView) view .findViewById(R.id.descText1);
                viewHolder.descText2 = (TextView) view .findViewById(R.id.descText2);
                viewHolder.descText3 = (TextView) view .findViewById(R.id.descText3);
                viewHolder.moreImage = (ImageView) view.findViewById(R.id.moreImage);
                viewHolder.layout = (LinearLayout) view.findViewById(R.id.itemLayout);
                viewHolder.moreImage.setVisibility(View.GONE);

                // 设置tag
                view.setTag(viewHolder);
            }else {
                // 从convertView中得到我们的viewHolder
                viewHolder = (InfoHolder) view.getTag();
            }
            if (item != null) {

                viewHolder.titleText.setText(item.getName());
                viewHolder.descText1.setText("编制 "+item.getNumberOfPeople());
                viewHolder.descText2.setText("实有 "+item.getActualNumberOfPeople());
                String desc ="";
                if(item.getNumberOfPeople()>item.getActualNumberOfPeople()){
                    int q = item.getNumberOfPeople()-item.getActualNumberOfPeople();
                    desc = "<font color=\"#ff0000\">缺 "+q+"</font>";
                }
                else if(item.getNumberOfPeople()< item.getActualNumberOfPeople()){
                    int c =item.getActualNumberOfPeople() - item.getNumberOfPeople();
                    desc = "<font color=\"#0000FF\">超 "+c+"</font>";
                }
                viewHolder.descText3.setText(Html.fromHtml(desc));

            }
        }

        return view;
    }
    public class InfoHolder {
        public LinearLayout layout;
        public TextView titleText;
        public TextView descText1;
        public TextView descText2;
        public TextView descText3;
        public ImageView moreImage;

    }
}
