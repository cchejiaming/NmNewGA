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
 * Ĭ�Ͻ�ѹ���࣬��ѹ���г�jpg�����е��ļ�
 * jpg�ļ�̫�࣬���½�ѹ�ٶȻ�����ѹʱ�䳤,
 * ���jpgͼƬ���ö�̬��ѹ����ʽ�������ֽ�ѹ����
 * Created by HeJiaMing on 2019/4/1 09:30
 * E-Mail Address��1774690@qq.com
 */
public class ZipUtils {

    /**
     * ��ѹ��
     * ��zipFile�ļ���ѹ��folderPathĿ¼��.
     *
     * @param zipFile    zip�ļ�
     * @param folderPath ��ѹ���ĵ�ַ
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
                String suffix = "";  //�ļ���׺
                //��ȡ�ļ���׺�ж��Ƿ�Ϊjpg��ʽ
                int ipx = entryName.lastIndexOf(".");
                if (ipx > -1) suffix = entryName.substring(ipx, entryName.length( ));

                System.out.println("���ڴ�����ѹ�ļ� - " + entryName);
                String fileDir = path.substring(0, path.lastIndexOf("/"));
                File fileDirFile = new File(fileDir);
                if (!fileDirFile.exists( )) {

                    fileDirFile.mkdirs( );

                }
                //�ж��Ƿ���jpg�ļ�,����������ѹ���ļ�

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
