package com.cxht.unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import com.cxht.bean.GroupSerializable;
import com.gov.tree.TreeOrgCkTable;
import com.gov.tree.TreeOrgTable;
import android.content.Context;
import android.util.Log;

public class FileUtil {

	public static  void writeCkTable(Context context, TreeOrgCkTable table,
			String fileName) {
	
		FileOutputStream stream;
		try {
			stream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream oos = null;
			oos = new ObjectOutputStream(stream);
			if (oos != null) {
				oos.writeObject(table);
				oos.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void writeOrgZsTable(Context context,TreeOrgCkTable table){
    	writeCkTable(context, table, "orgzs.s");

    } 
	public static void writeOrgCkTable(Context context, TreeOrgCkTable table) {
		writeCkTable(context, table, "orgck.s");

	}

	public static void writeOrgTable(Context context, TreeOrgTable table) {
		FileOutputStream stream;
		try {
			stream = context.openFileOutput("org.s", Context.MODE_PRIVATE);
			ObjectOutputStream oos = null;
			oos = new ObjectOutputStream(stream);
			if (oos != null) {
				oos.writeObject(table);
				oos.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void writeGroupTable(Context context, GroupSerializable table) {
		FileOutputStream stream;
		try {
			if (table != null) {
				stream = context
						.openFileOutput("group.s", Context.MODE_PRIVATE);
				ObjectOutputStream oos = null;
				oos = new ObjectOutputStream(stream);
				if (oos != null) {
					oos.writeObject(table);
					oos.close();
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static TreeOrgCkTable readOrgZsTable(Context context) {
		TreeOrgCkTable db = readOrg(context, "orgzs.s");
		return db;
	}

	public static TreeOrgCkTable readOrgCkTable(Context context) {
		TreeOrgCkTable db = readOrg(context, "orgck.s");
		return db;
	}

	public static TreeOrgCkTable readOrg(Context context, String fileName) {

		TreeOrgCkTable db = null;
		FileInputStream stream;
		try {
			stream = context.openFileInput(fileName);
			ObjectInputStream ois = new ObjectInputStream(stream);
			if (ois != null) {
				db = (TreeOrgCkTable) ois.readObject();
				ois.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return db;
	}

	public static TreeOrgTable readOrgTable(Context context) {
		TreeOrgTable db = null;
		FileInputStream stream;
		try {
			stream = context.openFileInput("org.s");
			ObjectInputStream ois = new ObjectInputStream(stream);
			if (ois != null) {
				db = (TreeOrgTable) ois.readObject();
				ois.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return db;
	}

	public static GroupSerializable readGroupTable(Context context) {
		GroupSerializable db = null;
		FileInputStream stream;
		try {
			stream = context.openFileInput("group.s");
			ObjectInputStream ois = new ObjectInputStream(stream);
			if (ois != null) {
				db = (GroupSerializable) ois.readObject();
				ois.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return db;
	}
	/**
	 * �����ļ�д�뷽��
	 * 
	 * @param fromFile
	 *            Դ���ļ�
	 * @param toFile
	 *            Ŀ���ļ�
	 * @return 0�ɹ� -1 ʧ��
	 */
	public static int copeFile(String fromFile, String toFile) {
		

		try {
			InputStream fosfrom = new FileInputStream(fromFile);
			OutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c);
			}
			fosfrom.close();
			fosto.close();
			return 0;

		} catch (Exception ex) {
			return -1;
		}
	}
    /**
     * ɾ�������ļ�
     * @param   filePath    ��ɾ���ļ����ļ���
     * @return �ļ�ɾ���ɹ�����true�����򷵻�false
     */
    public boolean deleteFile(String filePath) {
    File file = new File(filePath);
        if (file.isFile() && file.exists()) {
        return file.delete();
        }
        return false;
    }
    /**
     * ɾ���ļ����Լ�Ŀ¼�µ��ļ�
     * @param   filePath ��ɾ��Ŀ¼���ļ�·��
     * @return  Ŀ¼ɾ���ɹ�����true�����򷵻�false
     */
    public boolean deleteDirectory(String filePath) {
    boolean flag = false;
        //���filePath�����ļ��ָ�����β���Զ�����ļ��ָ���
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //����ɾ���ļ����µ������ļ�(������Ŀ¼)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
            //ɾ�����ļ�
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
            //ɾ����Ŀ¼
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //ɾ����ǰ��Ŀ¼
        return dirFile.delete();
    }
    /**
     *  ����·��ɾ��ָ����Ŀ¼���ļ������۴������
     *@param filePath  Ҫɾ����Ŀ¼���ļ�
     *@return ɾ���ɹ����� true�����򷵻� false��
     */
    public boolean DeleteFolder(String filePath) {
    File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
            // Ϊ�ļ�ʱ����ɾ���ļ�����
                return deleteFile(filePath);
            } else {
            // ΪĿ¼ʱ����ɾ��Ŀ¼����
                return deleteDirectory(filePath);
            }
        }
    }
    
    
	/**
	 * ��assetsĿ¼�и��������ļ�������
	 * 
	 * @param context
	 *            Context ʹ��CopyFiles���Activity
	 * @param oldPath
	 *            String ԭ�ļ�·�� �磺/aa
	 * @param newPath
	 *            String ���ƺ�·�� �磺xx:/bb/cc
	 */
	public static void copyFilesFassets(Context context, String oldPath, String newPath) {
		try {
			String fileNames[] = context.getAssets().list(oldPath);// ��ȡassetsĿ¼�µ������ļ���Ŀ¼��
			if (fileNames.length > 0) {// �����Ŀ¼
				File file = new File(newPath);
				file.mkdirs();// ����ļ��в����ڣ���ݹ�
				for (String fileName : fileNames) {
					copyFilesFassets(context, oldPath + "/" + fileName, newPath
							+ "/" + fileName);
				}
			} else {// ������ļ�
				InputStream is = context.getAssets().open(oldPath);
				FileOutputStream fos = new FileOutputStream(new File(newPath));
				byte[] buffer = new byte[1024];
				int byteCount = 0;
				while ((byteCount = is.read(buffer)) != -1) {// ѭ������������ȡ
																// buffer�ֽ�
					fos.write(buffer, 0, byteCount);// ����ȡ��������д�뵽�����
				}
				fos.flush();// ˢ�»�����
				is.close();
				fos.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// �����׽��������֪ͨUI�߳�
			// MainActivity.handler.sendEmptyMessage(COPY_FALSE);
		}
	}
    
	/***
	 * �����ļ���
	 * @param filePath
	 */
		public static void makeDirectory(String filePath) {
		    File file = null;
		    try {
		        file = new File(filePath);
		        if (!file.exists()) {
		            file.mkdir();
		        }
		    } catch (Exception e) {
		        Log.i("error:", e+"");
		    }
		}
	
		
}
