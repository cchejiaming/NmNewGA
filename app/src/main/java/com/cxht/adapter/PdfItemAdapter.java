package com.cxht.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cxht.bean.PdfItem;
import com.cxht.config.Setting;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

/**
 * PDF文件列表适配器
 * Created by HeJiaMing on 2019/12/26 15:32
 * E-Mail Address：1774690@qq.com
 */
public class PdfItemAdapter extends ArrayAdapter<PdfItem> {
    private LayoutInflater inflater;
    public PdfItemAdapter(Context context) {
        super(context, 0);
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //Log.d("InfoAdapter1.java", "Code goes here...");
        PdfItemAdapter.InfoHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.pdf_item, null);
            viewHolder = new InfoHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.pdf_title);
            // 设置tag
            view.setTag(viewHolder);
        } else {

            // 从convertView中得到我们的viewHolder
            viewHolder = (InfoHolder) view.getTag();
        }
        PdfItem item = getItem(position);

        viewHolder.title.setText(item.getDisplay());

        return view;
    }

    public class InfoHolder {
        public TextView title;
    }

}
