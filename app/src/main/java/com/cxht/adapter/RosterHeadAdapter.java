package com.cxht.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cxht.app.UserInfoActivity;
import com.cxht.bean.RosterRow;
import com.cxht.config.GonganApplication;
import com.gongan.manage.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class RosterHeadAdapter extends BaseAdapter {

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private LayoutInflater inflater;
	private List<RosterRow> data;
	private Context context;
	private String TAG = "RosterHeadAdapter";

	public RosterHeadAdapter(Context context, List<RosterRow> data) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.data = data;
		options = GonganApplication.defaultOptionsConfig();

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
				view = inflater.inflate(R.layout.gov_user_list_item1, null);
				viewHolder = new ViewHolder();
				viewHolder.photoImage = (ImageView) view
						.findViewById(R.id.photoIv);
				viewHolder.jobTv = (TextView) view.findViewById(R.id.jobTv);
				viewHolder.nameTv = (TextView) view.findViewById(R.id.nameTv);

				// 设置tag
				view.setTag(viewHolder);
			} else {
				// 从convertView中得到我们的viewHolder
				viewHolder = (ViewHolder) view.getTag();
			}

			final RosterRow user = data.get(arg0);
			if (user != null) {

				/**
				 * 显示图片 参数1：图片url 参数2：显示图片的控件 参数3：显示图片的设置 参数4：监听器
				 */

				String photoName = user.getUser().getImage();
				String imageUrl = GonganApplication.getImagePath(context)
						+ photoName;
				// Log.i(TAG, imageUrl);

				imageLoader.displayImage(imageUrl, viewHolder.photoImage,
						options, animateFirstListener);
				viewHolder.photoImage
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(context,
										UserInfoActivity.class);
								int uId = user.getUser().getUser_id();
								intent.putExtra("UserId", uId);
								// ScreenManager.getScreenManager().popActivityAppoint(UserSearchActivity.class);
								context.startActivity(intent);
							}
						});
				viewHolder.jobTv.setText(user.getUser().getPosition());
				int rh = user.getRowHight();
				if (rh > 0) {
					LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewHolder.jobTv
							.getLayoutParams(); // 取控件textView当前的布局参数
					linearParams.height = rh;// 控件的高度
					viewHolder.jobTv.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件
				}

				String title = user.getUser().getUser_name();
				if (user.getUser().getSex().equals("女")) {
					title += "\n(女)";
				}

				viewHolder.nameTv.setText(title);
				// viewHolder.sexTv.setText(user.getUser().getSex());

				// String birth
				// =StringUtil.toShowData(user.getUser().getBirth_date());
				// viewHolder.birthTv.setText(birth+"\n["+
				// StringUtil.getYearNum(birth,Setting.SH_TIME_FORMAT)+"岁]");
				// viewHolder.birthTv.setText(StringUtil.toShowData(user.getUser().getBirth_date()));
			}
		}

		return view;
	}

	public class ViewHolder {
		public ImageView photoImage;
		public TextView nameTv;
		public TextView jobTv;

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
