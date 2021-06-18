package com.cxht.image;


import com.gongan.manage.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ShowImageFragment extends Fragment {

	private String networkImageUrl= null;
	private View view;
	private Activity activity;
	private int window_width, window_height;// �ؼ����
	private DragImageView dragImageView;// �Զ���ؼ�
	private int state_height;// ״̬���ĸ߶�
	private ViewTreeObserver viewTreeObserver;
    private DisplayImageOptions options;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Bundle args = getArguments();
	    networkImageUrl =args.getString("networkImage");
	    super.onCreate(savedInstanceState);
	}
	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
	    view = LayoutInflater.from(activity).inflate(R.layout.image_matrix_view, null);
		
	    dragImageView = (DragImageView) view.findViewById(R.id.div_main);
	    
		/** ��ȡ��Ҋ����߶� **/
		WindowManager manager = activity.getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight();
	
	    if(networkImageUrl!= null){
	    
	    	showNetworkImage();
	    	
	    }
	// ����ͼƬ
		
		dragImageView.setmActivity(activity);// ע��Activity.
		/** ����״̬���߶� **/
		viewTreeObserver = dragImageView.getViewTreeObserver();
		viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					public void onGlobalLayout() {
						if (state_height == 0) {
							// ��ȡ״�����߶�
							Rect frame = new Rect();
							activity.getWindow().getDecorView()
									.getWindowVisibleDisplayFrame(frame);
							state_height = frame.top;
							dragImageView.setScreen_H(window_height
									- state_height);
							dragImageView.setScreen_W(window_width);
						}

					}
				});
		
		return view;
	}

	private void showNetworkImage(){
		
		final ProgressBar spinner = (ProgressBar)view.findViewById(R.id.loading);
		initOption();
		ImageLoader imageLoader = ImageLoader.getInstance();
		
        imageLoader.displayImage(networkImageUrl, dragImageView, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				spinner.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = null;
				switch (failReason.getType()) {		// ��ȡͼƬʧ������
					case IO_ERROR:				// �ļ�I/O����
						message = "Input/Output error";
						break;
					case DECODING_ERROR:		// �������
						message = "Image can't be decoded";
						break;
					case NETWORK_DENIED:		// �����ӳ�
						message = "Downloads are denied";
						break;
					case OUT_OF_MEMORY:		    // �ڴ治��
						message = "Out Of Memory error";
						break;
					case UNKNOWN:				// ԭ����
						message = "Unknown error";
						break;
				}
				Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

				spinner.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				spinner.setVisibility(View.GONE);		// ����ʾԲ�ν�����
			}
		});
	}
	private void initOption() {
		options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.resetViewBeforeLoading(true)
		.cacheOnDisc(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300))
		.build();
	}

}
