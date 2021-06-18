package com.cxht.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.cxht.config.GonganApplication;
import com.cxht.config.Setting;
import com.cxht.dao.AssessDao;
import com.cxht.dao.ExamineDao;
import com.cxht.dao.FamilyDao;
import com.cxht.dao.PunishDao;
import com.cxht.dao.ResumeDao;
import com.cxht.dao.TableDao;
import com.cxht.dao.UserDao;
import com.cxht.entity.Assess;
import com.cxht.entity.Examine;
import com.cxht.entity.Family;
import com.cxht.entity.Punish;
import com.cxht.entity.Resume;
import com.cxht.entity.User;
import com.cxht.unit.FileUtil;
import com.cxht.unit.StringUtil;
import com.cxht.unit.ToolUtil;
import com.gongan.manage.R;

public class ReadExcel extends Activity {
    TextView txt = null;
    private String user_id, user_name, birth_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.excel_main);
        user_id = getIntent( ).getStringExtra("user_id");
        user_name = getIntent( ).getStringExtra("user_name");
        birth_date = getIntent( ).getStringExtra("birth_date");
        txt = (TextView) findViewById(R.id.txt_show);
        txt.setMovementMethod(ScrollingMovementMethod.getInstance( ));

        //readExcel();
        createExcel( );

    }

    public List <String> getRmbXls(String path) {
        List <String> list = new ArrayList <String>( );
        File file = new File(path);
        for (File fl : file.listFiles( )) {
            if (fl.isFile( )) {
                int index = fl.getName( ).indexOf(user_name);
                if (index != -1) {
                    list.add(fl.getPath( ));

                }
            }
        }
        return list;
    }

    public void createExcel() {
        String newPath = GonganApplication.getSDDir(this) + "/cxht/";
        FileUtil.copyFilesFassets(ReadExcel.this, "rmb", newPath);
        String filePath = GonganApplication.getSDDir(this) + "/cxht/rmb.xls";
        updateExcel(filePath);

    }

    public void readExcel() {

        Log.i("excel", birth_date);
        String imageUrl = GonganApplication.getImagePath(this);
        String path = imageUrl.replace("image/", "");
        path = path.replace("file:", "");
        path = path + "rmb/";
        Log.i("path", path);
        List <String> list = getRmbXls(path);
        for (String str : list) {
            try {
                InputStream is = new FileInputStream(str);
                // Workbook book = Workbook.getWorkbook(new
                // File("mnt/sdcard/test.xls"));
                Workbook book = Workbook.getWorkbook(is);
                int num = book.getNumberOfSheets( );
                txt.setText("the num of sheets is " + num + "\n");
                // 获得第一个工作表对象
                Sheet sheet = book.getSheet(0);
                //WritableSheet  wSheet =  book.getSheet(0);
                int Rows = sheet.getRows( );
                int Cols = sheet.getColumns( );
                txt.append("the name of sheet is " + sheet.getName( ) + "\n");
                txt.append("total rows is " + Rows + "\n");
                txt.append("total cols is " + Cols + "\n");

                for (int j = 0; j < Rows; ++j) {
                    for (int i = 0; i < Cols; ++i) {
                        // getCell(Col,Row)获得单元格的值
                        String val = sheet.getCell(i, j).getContents( );
                        txt.append(val + " ");
                        String fTime = StringUtil.toShowData(birth_date);
                        if (val.equals(fTime)) {
                            txt.append("找到");
                            openRmb(str, user_id);
                            book.close( );
                            is.close( );
                            this.finish( );
                        }
                    }
                    txt.append("\n");
                }
                book.close( );
                is.close( );
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void updateExcel(String filePath) {
        try {
            Workbook rwb = Workbook.getWorkbook(new File(filePath));
            WritableWorkbook wwb = Workbook.createWorkbook(new File(filePath),
                    rwb);// copy
            WritableSheet ws = wwb.getSheet(0);
            // 写入任免表信息
            writeRmbToExcel(ws);
            wwb.write( );
            wwb.close( );
            rwb.close( );
            Intent intent = UserInfoActivity.getExcelFileIntent(filePath);
            startActivity(intent);
            this.finish( );
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }

    public static WritableImage setWritableImage(double x, double y, double width, double height, File file) {
        WritableImage image = new WritableImage(x, y, width, height, file);
        double w = image.getWidth( );
        image.setWidth(w - 0.1);
        double h = image.getHeight( );
        image.setHeight(h - 0.05);
        return image;
    }

    /**
     * 设置样式
     *
     * @param c    列
     * @param r    行
     * @param cont 值
     * @return
     * @throws WriteException
     */
    public static Label setLabel(int c, int r, String cont, int styleId) throws WriteException {
        if(cont!=null) cont = cont.replace("null","");
        Label bl = new Label(c, r, cont);


        WritableCellFormat cellFormat = new WritableCellFormat( );
        cellFormat.setWrap(true);
        switch (styleId) {
            case 1:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR);
                //cellFormat.setWrap(true);
                break;
            case 2:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                //cellFormat.setWrap(true);
                break;
            case 3:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                //cellFormat.setWrap(true);
                break;
            case 4:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.ALL, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                break;

            case 5:
                cellFormat.setAlignment(jxl.format.Alignment.LEFT);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                break;
            case 6:
                cellFormat.setAlignment(jxl.format.Alignment.LEFT);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR);
                break;
            case 7:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                break;
            case 8:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR);
                break;
            case 9:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                break;
            case 10:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR, jxl.format.Colour.WHITE);
                break;
            case 11:
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR);
                break;
            case 12:
                cellFormat.setAlignment(jxl.format.Alignment.LEFT);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM, jxl.format.Colour.BLACK);
                cellFormat.setBorder(Border.TOP, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.LEFT, BorderLineStyle.HAIR);
                cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.HAIR);
                break;
        }
        bl.setCellFormat(cellFormat);
        return bl;
    }

	/**
	 * Excel任免表模板填充数据
	 * @param ws 任免表模板对象
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
    public void writeRmbToExcel(WritableSheet ws) throws RowsExceededException,
            WriteException {
        if (user_id == null) return;
        int userId = Integer.parseInt(user_id);
        String user_code = UserDao.getUserCode(userId);
        User base = UserDao.queryUser(userId);// 基础信息集
        if (base == null) return;
        //==========开始填充任免表======================
        ws.addCell(setLabel(5, 5, base.getUser_name( ), 10));
        ws.addCell(setLabel(11, 5, base.getSex( ), 10));
        ws.addCell(setLabel(5, 7, base.getNation( ), 1));
        ws.addCell(setLabel(15, 5, StringUtil.toShowData(base.getBirth_date( )), 10));
        String age = "(" + StringUtil.getYearNum(base.getBirth_date( ),
                Setting.DB_TIME_FORMAT) + "岁)";
        ws.addCell(setLabel(15, 6, age, 2));
        ws.addCell(setLabel(11, 7, base.getNativeplace( ), 1));
        ws.addCell(setLabel(15, 7, base.getBirthplace( ), 1));
        ws.addCell(setLabel(5, 8, StringUtil.toShowData(base.getRd_time( )), 1));
        ws.addCell(setLabel(5, 9, base.getPolitics_status( ), 1));
        ws.addCell(setLabel(11, 8, StringUtil.toShowData(base.getJob_time( )), 1));
        ws.addCell(setLabel(15, 8, base.getStatus( ), 1));
        ws.addCell(setLabel(13, 10, base.getSpecialty_desc( ), 1));
        ws.addCell(setLabel(9, 12, base.getBefore_education( ) + base.getBefore_degree( ), 1));
        ws.addCell(setLabel(15, 12, base.getBefore_school( ), 7));
        ws.addCell(setLabel(15, 13, base.getBefore_specialty( ), 8));
        ws.addCell(setLabel(9, 14, base.getLatest_education( ) + base.getLatest_degree( ), 1));
        ws.addCell(setLabel(15, 14, base.getLatest_school( ), 7));
        ws.addCell(setLabel(15, 15, base.getLatest_specialty( ), 8));
        ws.addCell(setLabel(9, 16, base.getPosition( ), 12));
        //开始输出简历
        List <Resume> resAs = ResumeDao.getResumeList(user_code);
        if (resAs!=null){
			int idx = 21;
			for (Resume res : resAs)//简历
			{
				if (idx < 45) {
					ws.addCell(setLabel(4, idx, StringUtil.toShowData(res.getStart_time( )), 9));
					ws.addCell(setLabel(6, idx, "--", 4));
					ws.addCell(setLabel(7, idx, StringUtil.toShowData(res.getOver_time( )), 4));
					ws.addCell(setLabel(10, idx, res.getJob_desc( ), 5));

				}
				idx++;
			}
		}

        //奖惩情况
        List <Examine> examAs = ToolUtil.rmbExamine( ExamineDao.getExamineList(user_code), PunishDao.getPunishList(user_code));
        if(examAs!=null ){
			int idx = 57;
			String examDes = "";
			for (Examine exa : examAs)//奖惩
			{
                examDes += exa.getExamine_time()+" "+ exa.getExamine_name()+ "；";
			}
            if (examDes.length()>1) examDes =examDes.substring(0,examDes.length()-1);
            ws.addCell(setLabel(4, idx, examDes, 1));
		}

        //年度考核
		List<Assess> asseAs = AssessDao.getAssessList(user_code);
        if(asseAs!=null){
			int idx =61;
            String asseDesc="";
			for (Assess asse : asseAs)//考核
			{

                    asseDesc += asse.getYear()+"年度考核 "+ asse.getResult()+ "；";

			}
			if (asseDesc.length()>1) asseDesc =asseDesc.substring(0,asseDesc.length()-1);
            ws.addCell(setLabel(4, idx,  asseDesc, 1));
		}

        //开始输出家庭成员
        List <Family> famAs = FamilyDao.getFamilyList(user_code);
        if(famAs!=null){
			int idx = 70;
			for (Family fam : famAs)//家庭成员
			{
				if (idx < 77) {
					ws.addCell(setLabel(4, idx, fam.getTitle( ), 1));
					ws.addCell(setLabel(7, idx, fam.getFamily_name( ), 1));
					int mAge = StringUtil.getYearNum(fam.getBirth_date( ), Setting.DB_TIME_FORMAT);
					if (mAge > 0) ws.addCell(setLabel(11, idx, String.valueOf(mAge), 1));
					ws.addCell(setLabel(12, idx, fam.getPolitics_status( ), 1));
					ws.addCell(setLabel(14, idx, fam.getJob_desc( ), 6));

				}

				idx++;
			}
		}
        // 添加照片文件
        String dir = GonganApplication.getSDDir(this) + "/cxht";
        FileUtil.makeDirectory(dir + "/png"); //没有则创建文件夹
        String jpgPath = dir + "/image/" + base.getImage( );
        String pngPath = dir + "/png/" + base.getImage( ).replace(".jpg", ".png");
        Log.i("png", pngPath);
        Log.i("png", jpgPath);
        File pfile = new File(pngPath);
        if (!pfile.exists( )) {
            //尝试转换
            File jFile = new File(jpgPath);
            if (jFile.exists( )) {
                FileUtil.copeFile(jpgPath, pngPath);
            }
        }
        File pFile = new File(pngPath);
        if (pFile.exists( )) {

            ws.addImage(setWritableImage(17, 5, 2, 7, pFile));
        }


    }

    public void openRmb(String path, String userId) {
        if (path != null && userId != null) {
            String sql = "update t_user set rmb_path = '" + path
                    + "' where user_id = " + userId;
            TableDao.execSql(this, sql);
            Intent intent = UserInfoActivity.getExcelFileIntent(path);
            startActivity(intent);
        }

    }


}
