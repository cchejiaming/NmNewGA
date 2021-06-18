package com.cxht.config;
import java.util.Stack;
import android.app.Activity;
/**
 * Activity 退出栈
 * @author hejiaming
 *
 */
public class ScreenManager {
	
	private static Stack<Activity> activityStack;
    private static ScreenManager instance;
    private ScreenManager() {
       
    }

    public static ScreenManager getScreenManager() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    // 退出栈顶Activity
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    // 获得当前栈顶Activity
    public Activity currentActivity() {
        Activity activity = (Activity) activityStack.lastElement();
        return activity;
    }

    // 将当前Activity推入栈中
    public void pushActivity(Activity activity) {
      //  if (activityStack == null) {
          //  activityStack = new Stack();
    //    }
      //  activityStack.add(activity);
    }
    /**
     * 退出指定栈
     * @param cls
     */
   public void popActivityAppoint(Class<?> cls){
	   
	  //for(int i=0;i<activityStack.size();i++){
		 // Activity activity = activityStack.get(i);
		 
		//  if (activity.getClass().equals(cls)) {
		//	  popActivity(activity);
       ///       break;
       //   }
	 // }
   }
    // 退出栈中所有Activity
    public void popAllActivityExceptOne(Class<?> cls) {
    
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }
}
