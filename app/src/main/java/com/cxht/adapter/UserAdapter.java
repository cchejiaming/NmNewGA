package com.cxht.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.cxht.entity.User;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class UserAdapter extends BaseAdapter {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private LayoutInflater inflater;
	private List<User> data;
	private Context context;
    private String TAG="UserAdapter";
	public UserAdapter(Context context, List<User> data) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;
		options=GonganApplication.defaultOptionsConfig();
		
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
				view = inflater.inflate(R.layout.user_item, null);
				viewHolder = new ViewHolder();
				viewHolder.userImage = (ImageView) view
						.findViewById(R.id.news_item_img_default);
				viewHolder.titleText = (TextView) view
						.findViewById(R.id.news_item_title);
				viewHolder.subviewText = (TextView) view
						.findViewById(R.id.news_item_sub_title);
				// 设置tag
				view.setTag(viewHolder);
			} else {
				// 从convertView中得到我们的viewHolder
				viewHolder = (ViewHolder) view.getTag();
			}

			User user = data.get(arg0);
			if (user != null) {

				/**
				 * 显示图片 参数1：图片url 参数2：显示图片的控件 参数3：显示图片的设置 参数4：监听器
				 */
				String imageUrl = GonganApplication.getImagePath(context)+ user.getImage();
				Log.i(TAG,imageUrl);
                imageLoader.displayImage(imageUrl, viewHolder.userImage,
    						options, animateFirstListener);
                String brith = StringUtil.toShowData(user.getBirth_date());
                String year =  "["+StringUtil.getYearNum(brith, Setting.SH_TIME_FORMAT)+"岁]";
				String titleText = user.getUser_name() + " " + user.getSex()
					    + " " +brith+" "+year+ " " + user.getNation() ;
				viewHolder.titleText.setText(StringUtil.getSubString(titleText,
						25));
				String subviewText = StringUtil.toShowData(user.getJob_time()) + "参加工作,"
						+ StringUtil.toShowData(user.getRd_time()) + "入党\n" + user.getPosition()
						+ "\n" + user.getPolitics_status() + " "
						+ user.getDepth();
				subviewText = subviewText.replace("null","");
				viewHolder.subviewText.setText(StringUtil.getSubString(
						subviewText, 80));
			}
		}

		return view;
	}

	public class ViewHolder {
		public ImageView userImage;
		public TextView titleText;
		public TextView subviewText;

	}

	/**
	 * 图片加载第一次显示监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// 是否第一次显示
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					// 图片淡入效果
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
