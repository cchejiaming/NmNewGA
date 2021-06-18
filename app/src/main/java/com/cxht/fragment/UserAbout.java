package com.cxht.fragment;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.cxht.dao.HistoryDao;
import com.cxht.dao.UserDao;
import com.cxht.entity.User;
import com.cxht.image.ImageViewPager;
import com.cxht.unit.StringUtil;
import com.gongan.manage.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserAbout extends Fragment {
	private Context context;
	private int user_id;
	private ImageView userImage;
	private TextView descText;
	private TextView nationText;
	private TextView jobText;
	private TextView rdText;
	private TextView fullText;
	private TextView depthText;
	private View view;
	private User user;// 用户对象
	protected ImageLoader imageLoader = ImageLoader.getInstance(); // ImageLoader初始化
	private DisplayImageOptions options; // 参数设置
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	/**
	 * 创建新实例
	 *
	 * @return
	 */
	// private static ArticleFragment fragment;

	public static UserAbout newInstance(int userId) {
		Bundle bundle = new Bundle();
		bundle.putInt("UserId", userId);
		UserAbout fragment = new UserAbout();
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.user_id = args != null ? args.getInt("UserId", 0) : 0;
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.context = getActivity();
		view = LayoutInflater.from(context).inflate(R.layout.fragment_about,null);
		userImage = (ImageView) view.findViewById(R.id.userImage);
		// 根据物理屏幕分辨率设置ImageView 高度
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		LayoutParams params = userImage.getLayoutParams();
		params.width = LayoutParams.WRAP_CONTENT;
		params.height = metrics.heightPixels / 2;
		userImage.setLayoutParams(params);
		descText = (TextView) view.findViewById(R.id.descText);
		nationText = (TextView) view.findViewById(R.id.nationText);
		jobText = (TextView) view.findViewById(R.id.jobText);
		rdText = (TextView) view.findViewById(R.id.rdText);
		fullText = (TextView) view.findViewById(R.id.fullText);
		depthText = (TextView) view.findViewById(R.id.depthText);
		initData();
		// 历史记录
		HistoryDao.wirteHistoryToBase(context, descText.getText().toString(),
				user_id, 1);
		return view;
	}

	private void initData() {

		user = UserDao.queryUser(user_id);
		if (user != null) {
			// 初始化数据
			// 图片
			options = GonganApplication.defaultOptionsConfig();
			final String imageUrl = GonganApplication.getImagePath(context)+ user.getImage();

			imageLoader.displayImage(imageUrl, userImage, options,	animateFirstListener);
			// 文字
			Log.i("date", user.getBirth_date());
			String sr = StringUtil.toShowData(user.getBirth_date());
			Log.i("date", sr);
			String birth = StringUtil.toShowData(user.getBirth_date());
			String age = "["
					+ StringUtil.getYearNum(birth, Setting.SH_TIME_FORMAT)
					+ "岁]";
			String userDesc = user.getUser_name() + " " + user.getSex() + " "
					+ birth + "" + age + " " + user.getNation();
			descText.setText(StringUtil.stringFilter(userDesc));
			nationText.setText(StringUtil.stringFilter("籍贯："
					+ user.getNativeplace()));
			String jobTime = StringUtil.toShowData(user.getJob_time());
			String jobYear = "["
					+ StringUtil.getYearNum(jobTime, Setting.SH_TIME_FORMAT)
					+ "年]";
			jobText.setText("参加工作时间：" + jobTime + jobYear);

			String rdTime = StringUtil.toShowData(user.getRd_time());
			String rdYear = "["
					+ StringUtil.getYearNum(rdTime, Setting.SH_TIME_FORMAT)
					+ "年]";
			rdText.setText("入党时间：" + rdTime + rdYear);
			fullText.setText(StringUtil.stringFilter(user.getPosition()));
			depthText.setText(StringUtil.stringFilter(user.getDepth()));
			userImage.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {

					String json = "[{'ImagePath':'" + imageUrl + "'}]";

					if (user.getPhoto() != null && !"".equals(user.getPhoto())) {
						String photoUrl = GonganApplication
								.getImagePath(context) + user.getPhoto();
						json = "[{'ImagePath':'" + imageUrl
								+ "'},{'ImagePath':'" + photoUrl + "'}]";
					}
					Intent intent = new Intent(context, ImageViewPager.class);
					intent.putExtra("Json", json);
					startActivity(intent);

				}
			});

		} else {
			Toast.makeText(context, "未查询到用户信息", Toast.LENGTH_LONG).show();
		}
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
