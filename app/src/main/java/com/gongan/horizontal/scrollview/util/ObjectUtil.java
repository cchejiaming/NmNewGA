package com.gongan.horizontal.scrollview.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

import com.gongan.horizontal.scrollview.bean.HoriScViewTable;
/**
 * �������ʵ����
 * @author hejiaming
 *
 */
public class ObjectUtil {
   /**
    * ��һ�����󱣴浽���� 
    * @param context
    * @param table ����
    * @param name ��������
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
			// �����Ǳ����ļ������쳣
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// fos���ر��쳣
					e.printStackTrace();
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// oos���ر��쳣
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * �ӱ��ض�ȡ����
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
	            //�����Ƕ�ȡ�ļ������쳣  
	        } finally {  
	            if (fis != null){  
	                try {  
	                    fis.close();  
	                } catch (IOException e) {  
	                    //fis���ر��쳣  
	                    e.printStackTrace();  
	                }  
	            }  
	            if (ois != null){  
	                try {  
	                    ois.close();  
	                } catch (IOException e) {  
	                    //ois���ر��쳣  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	        //��ȡ�����쳣������null  
	        return null;  
		
	}
}
