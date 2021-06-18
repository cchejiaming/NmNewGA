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
	 * 拷贝文件写入方法
	 * 
	 * @param fromFile
	 *            源标文件
	 * @param toFile
	 *            目标文件
	 * @return 0成功 -1 失败
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
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String filePath) {
    File file = new File(filePath);
        if (file.isFile() && file.exists()) {
        return file.delete();
        }
        return false;
    }
    /**
     * 删除文件夹以及目录下的文件
     * @param   filePath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String filePath) {
    boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
            //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
            //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }
    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param filePath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String filePath) {
    File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
            // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
            // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }
    
    
	/**
	 * 从assets目录中复制整个文件夹内容
	 * 
	 * @param context
	 *            Context 使用CopyFiles类的Activity
	 * @param oldPath
	 *            String 原文件路径 如：/aa
	 * @param newPath
	 *            String 复制后路径 如：xx:/bb/cc
	 */
	public static void copyFilesFassets(Context context, String oldPath, String newPath) {
		try {
			String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
			if (fileNames.length > 0) {// 如果是目录
				File file = new File(newPath);
				file.mkdirs();// 如果文件夹不存在，则递归
				for (String fileName : fileNames) {
					copyFilesFassets(context, oldPath + "/" + fileName, newPath
							+ "/" + fileName);
				}
			} else {// 如果是文件
				InputStream is = context.getAssets().open(oldPath);
				FileOutputStream fos = new FileOutputStream(new File(newPath));
				byte[] buffer = new byte[1024];
				int byteCount = 0;
				while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
																// buffer字节
					fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
				}
				fos.flush();// 刷新缓冲区
				is.close();
				fos.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 如果捕捉到错误则通知UI线程
			// MainActivity.handler.sendEmptyMessage(COPY_FALSE);
		}
	}
    
	/***
	 * 生成文件夹
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
