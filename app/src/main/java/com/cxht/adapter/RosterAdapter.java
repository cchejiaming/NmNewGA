package com.cxht.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxht.config.Setting;
import com.cxht.bean.RosterRow;
import com.cxht.entity.Degree;
import com.cxht.entity.Qualification;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;

public class RosterAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<RosterRow> data;
	private Context context;
	private String TAG = "RosterAdapter";

	public RosterAdapter(Context context, List<RosterRow> data) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {

		ViewHolder viewHolder;

		if (arg0 < data.size()) {
			// Log.i("ClassList","arg0:"+arg0+" data.size:"+data.size());
			if (view == null) {
				view = inflater.inflate(R.layout.gov_user_list_item, null);
				viewHolder = new ViewHolder();
				viewHolder.birthTv = view.findViewById(R.id.birthTv);
				viewHolder.nationTv = view.findViewById(R.id.nationTv);
				viewHolder.nativeTv =  view.findViewById(R.id.nativeTv);
				viewHolder.polTv =view.findViewById(R.id.polTv);
				viewHolder.jobTimeTv =  view.findViewById(R.id.jobTimeTv);

				viewHolder.eduTv =  view.findViewById(R.id.eduTv);
				viewHolder.fdegTv =view.findViewById(R.id.fdegTv);
				viewHolder.schoolTv =view.findViewById(R.id.schoolTv);
				viewHolder.speTv = view.findViewById(R.id.speTv);

				viewHolder.zeduTv = view.findViewById(R.id.zeduTv);
				viewHolder.jdegTv =view.findViewById(R.id.jdegTv);
				viewHolder.zschoolTv =  view.findViewById(R.id.zschoolTv);
				viewHolder.zspeTv = view.findViewById(R.id.zspeTv);
				viewHolder.preDepthTv =  view.findViewById(R.id.preDepthTv);
				viewHolder.preJobTimeTv =  view.findViewById(R.id.preJobTimeTv);
				viewHolder.lxJobTimeTv = view.findViewById(R.id.lxJobTimeTv);

				viewHolder.depthTimeTv = view.findViewById(R.id.depthTimeTv);
				viewHolder.lxDepthTimeTv = view.findViewById(R.id.lxDepthTimeTv);
				viewHolder.queTv =  view.findViewById(R.id.queTv);
				viewHolder.marshTv =  view.findViewById(R.id.marshTv);
				viewHolder.jcTv =  view.findViewById(R.id.jcTv);
				// 设置tag
				view.setTag(viewHolder);
			} else {
				// 从convertView中得到我们的viewHolder
				viewHolder = (ViewHolder) view.getTag();
			}

			RosterRow user = data.get(arg0);
			if (user != null && user.getUser()!=null) {

				String birth = StringUtil.toShowData(user.getUser().getBirth_date());

				viewHolder.birthTv.setText(birth + "\n["	+ StringUtil.getYearNum(birth, Setting.SH_TIME_FORMAT)+ "岁]");
				viewHolder.nationTv.setText(user.getUser().getNation());
				int rh = user.getRowHight();
				if (rh > 0) {
					LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewHolder.nationTv
							.getLayoutParams(); // 取控件textView当前的布局参数
					linearParams.height = rh;// 控件的高度
					viewHolder.nationTv.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件
				}
				viewHolder.nativeTv.setText(getText(user.getUser().getNativeplace()));

                viewHolder.polTv.setText(getText(user.getUser().getPolitics_status())); //政治面貌

				String time = StringUtil.toShowData(user.getUser().getJob_time());

				viewHolder.jobTimeTv.setText(getText(time + "\n["+ StringUtil.getYearNum(time, Setting.SH_TIME_FORMAT)+ "年]"));

				viewHolder.eduTv.setText(getText(user.getUser().getBefore_education()));

				viewHolder.fdegTv.setText(getList(getFilterDegree(user.getDegrees(),"全日制")));

				viewHolder.schoolTv.setText(getText(user.getUser().getBefore_school()));

				viewHolder.speTv.setText(getText(user.getUser().getBefore_specialty()));

				viewHolder.zeduTv.setText(getText(user.getUser().getLatest_education()));

				viewHolder.jdegTv.setText(getList(getFilterDegree(user.getDegrees(),"在职")));

				viewHolder.zschoolTv.setText(getText(user.getUser().getLatest_school()));

				viewHolder.zspeTv.setText(getText(user.getUser().getLatest_specialty()));

				viewHolder.preDepthTv.setText(getText(user.getUser().getDepth()));

				String jobTime = StringUtil.toShowData(user.getUser().getPre_job_time());
				viewHolder.preJobTimeTv.setText(jobTime	+ "\n"	+ StringUtil.getYearMonthString(jobTime, Setting.SH_TIME_FORMAT));
                if (user.getJob()!=null && user.getJob().getLx_time()!=null){
					String jobLxTime = StringUtil.toShowData(user.getJob().getLx_time());
					viewHolder.lxJobTimeTv.setText(jobLxTime	+ "\n"	+ StringUtil.getYearMonthString(jobLxTime, Setting.SH_TIME_FORMAT));
				}

				String depthTime = StringUtil.toShowData(user.getUser().getPre_depth_time());
				viewHolder.depthTimeTv.setText(depthTime	+ "\n"	+ StringUtil.getYearMonthString(depthTime,	Setting.SH_TIME_FORMAT));

				if(user.getDepth()!=null && user.getDepth().getLxTime()!=null){
					String lxDepthTime = StringUtil.toShowData(user.getDepth().getLxTime());
					viewHolder.lxDepthTimeTv.setText(lxDepthTime	+ "\n"	+ StringUtil.getYearMonthString(lxDepthTime,	Setting.SH_TIME_FORMAT));
				}
				viewHolder.queTv.setText(getList(user.getQualifications()));
				if(user.getMarshals()!=null)
				viewHolder.marshTv.setText(getText(user.getMarshals().getLevel()));

			}
		}

		return view;
	}

	/**
	 * 学位信息过滤
	 * @param data 数据
	 * @param type 学位类型
	 * @return
	 */
	private List<Degree> getFilterDegree(List<Degree>data,String type){
           List<Degree> list = null;
           if (data!= null && data.size()>0){
           	 list = new ArrayList <Degree>();
           	for(Degree deg :data){
           		if(deg.getType().indexOf(type)>-1){
           			list.add(deg);
				}
			}
		   }
           return  list;
	}
	private <T> String  getList(List<T> list){
       String ret ="";
       if (list!= null && list.size()>0){
       	for(Object obj :list){
       		if (obj instanceof Degree){
       			Degree deg = (Degree)obj;
       			ret += deg.getDegree()+ "\n";
			}
       		if (obj instanceof Qualification){
       			Qualification que= (Qualification) obj;
				ret += que.getQfc_name()+ "\n";
			}
		}
		   if (ret.length() > 1)
			   ret = ret.substring(0, ret.length() - 1);
	   }
       return ret;
	}
	private String getText(String name) {
		String ret = "";
		if (name != null) {
			ret = name;
		}
		return ret;
	}

	public class ViewHolder {
		public TextView birthTv;
		public TextView nationTv;
		public TextView nativeTv;
		public TextView polTv;
		public TextView jobTimeTv;
		public TextView eduTv;
        public TextView fdegTv;
		public TextView schoolTv;
		public TextView speTv;
		public TextView zeduTv;
		public TextView jdegTv;
		public TextView zschoolTv;
		public TextView zspeTv;
		public TextView preDepthTv;
		public TextView preJobTimeTv;
		public TextView lxJobTimeTv;
		public TextView depthTimeTv;
		public TextView lxDepthTimeTv;
		public TextView queTv;
		public TextView marshTv;
		public TextView jcTv;
	}
}
