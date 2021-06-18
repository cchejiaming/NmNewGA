package com.gongan.horizontal.scrollview.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

import com.gongan.horizontal.scrollview.bean.HoriScViewTable;
/**
 * 对象操作实体类
 * @author hejiaming
 *
 */
public class ObjectUtil {
   /**
    * 将一个对象保存到本地 
    * @param context
    * @param table 对象
    * @param name 保存名称
    */
	public static void saveObject(Context context, HoriScViewTable table,
			String name) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = context.openFileOutput(name, Context.MODE_PRIVATE);
			context.fileList();
			oos = new ObjectOutputStream(fos);
			oos.writeObject(table);
		} catch (Exception e) {
			e.printStackTrace();
			// 这里是保存文件产生异常
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// fos流关闭异常
					e.printStackTrace();
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// oos流关闭异常
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 从本地读取对象
	 * @param context
	 * @param name 
	 * @return
	 */
	public static Object getObject(Context context,String name){
		
		 FileInputStream fis = null;  
	        ObjectInputStream ois = null;  
	        try {  
	            fis = context.openFileInput(name);  
	            ois = new ObjectInputStream(fis);  
	            return ois.readObject();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            //这里是读取文件产生异常  
	        } finally {  
	            if (fis != null){  
	                try {  
	                    fis.close();  
	                } catch (IOException e) {  
	                    //fis流关闭异常  
	                    e.printStackTrace();  
	                }  
	            }  
	            if (ois != null){  
	                try {  
	                    ois.close();  
	                } catch (IOException e) {  
	                    //ois流关闭异常  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	        //读取产生异常，返回null  
	        return null;  
		
	}
}
