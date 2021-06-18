package com.cxht.unit;


import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 默认解压缩类，解压所有除jpg外所有的文件
 * jpg文件太多，导致解压速度缓慢解压时间长,
 * 因此jpg图片采用动态解压缩方式，现用现解压缩。
 * Created by HeJiaMing on 2019/4/1 09:30
 * E-Mail Address：1774690@qq.com
 */
public class ZipUtils {

    /**
     * 解压缩
     * 将zipFile文件解压到folderPath目录下.
     *
     * @param zipFile    zip文件
     * @param folderPath 解压到的地址
     * @throws IOException
     */
    public void upZipFile(String zipFile, String folderPath) throws IOException {


        BufferedInputStream bi;

        ZipFile zf = new ZipFile(zipFile, "GBK");
        Enumeration e = zf.getEntries( );
        while (e.hasMoreElements( )) {
            ZipEntry ze2 = (ZipEntry) e.nextElement( );
            String entryName = ze2.getName( );

            String path = folderPath + "/" + entryName;
            path = path.replace("\\", "/");
            if (!ze2.isDirectory( )) {

                String fileName = path.substring(path.lastIndexOf("/") + 1);
                String suffix = "";  //文件后缀
                //获取文件后缀判断是否为jpg格式
                int ipx = entryName.lastIndexOf(".");
                if (ipx > -1) suffix = entryName.substring(ipx, entryName.length( ));

                System.out.println("正在创建解压文件 - " + entryName);
                String fileDir = path.substring(0, path.lastIndexOf("/"));
                File fileDirFile = new File(fileDir);
                if (!fileDirFile.exists( )) {

                    fileDirFile.mkdirs( );

                }
                //判断是否是jpg文件,如果不是则解压缩文件

                String filePath = fileDirFile + "/" + fileName;

                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(filePath));
                bi = new BufferedInputStream(zf.getInputStream(ze2));
                byte[] readContent = new byte[1024];
                int readCount = bi.read(readContent);
                while (readCount != -1) {
                    bos.write(readContent, 0, readCount);
                    readCount = bi.read(readContent);
                }
                bos.close( );
            }
        }
        zf.close( );
    }

}
