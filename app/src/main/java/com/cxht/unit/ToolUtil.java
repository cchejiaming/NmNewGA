package com.cxht.unit;

import android.content.Context;
import android.util.Log;

import com.cxht.config.GonganApplication;
import com.cxht.dao.AssessDao;
import com.cxht.dao.DegreeDao;
import com.cxht.dao.ExamineDao;
import com.cxht.dao.FamilyDao;
import com.cxht.dao.GroupNumberDao;
import com.cxht.dao.GunDao;
import com.cxht.dao.HistoryDao;
import com.cxht.dao.JobDao;
import com.cxht.dao.MarshalsDao;
import com.cxht.dao.PunishDao;
import com.cxht.dao.QualificationDao;
import com.cxht.dao.RankDao;
import com.cxht.dao.ResumeDao;
import com.cxht.dao.TrainDao;
import com.cxht.dao.UserDao;
import com.cxht.dao.UserFieldDao;
import com.cxht.entity.Examine;
import com.cxht.entity.Marshals;
import com.cxht.entity.Punish;

import java.util.ArrayList;
import java.util.List;

public class ToolUtil {
    /**
     * ��������Ϣ�ͳͽ���ϢΪ��������������Ϣ�����ֻ֧��4����ʾ����
     * ��Ϊ������н����ͳͽ���һ����Ϣ���ˣ�����������ȷ�Ƿֿ���������
     *
     * @param es ������Ϣ��
     * @param ps �ͽ���Ϣ��
     * @return
     */
    public static List <Examine> rmbExamine(List <Examine> es, List <Punish> ps) {
        List <Examine> data = null;
        if (es != null || ps != null) {
            data = new ArrayList <>( );
            if (es != null && ps != null) {
                //��������ͳͽ䶼�У�ÿ�����ȡ����
                int idx = 0;
				for(Punish p:ps){
					++idx;
					if (idx > 2) break;
					Examine ex = new Examine();
					ex.setExamine_time(p.getPunish_time());
					String desc = p.getPunish_name() +" "+ p.getPunish_type();
					ex.setExamine_name(desc);
					data.add(ex);
				}
                for (Examine e : es) {
                    ++idx;
                    if (idx > 4) break;
                    data.add(e);
                }


            } else {
                //���ֻ��һ���������һ�����4��
				if(es!= null){
					int idx = 0;
					for(Examine e:es){
						++idx;
						if (idx > 4) break;
						data.add(e);
					}
				}
				else{
					if(ps!=null){
						int idx = 0;
						for(Punish p:ps){
							++idx;
							if (idx > 4) break;
							Examine ex = new Examine();
							ex.setExamine_time(p.getPunish_time());
							String desc = p.getPunish_name() +" "+ p.getPunish_type();
							ex.setExamine_name(desc);
							data.add(ex);
						}
					}
				}
            }
        }
        return data;
    }

    /***
     * ��������
     */
    public void delAppData(Context context) {
        UserDao.deleteAll(context); // ɾ��������
        JobDao.deleteAll(context);// ɾ����λ��֯��
        AssessDao.deleteAll(context);// ɾ��������Ϣ��
        ExamineDao.deleteAll(context);// ɾ��������Ϣ��
        FamilyDao.deleteAll(context);// ɾ����ͥ��Ա
        ResumeDao.deleteAll(context);// ɾ��������
        UserFieldDao.deleteAll(context);// ɾ���û��Զ�����Ϣ��
        HistoryDao.deleteAllHistory(context);//ɾ����ʷ��¼
        DegreeDao.deleteAll(context); //ɾ��ѧλ��Ϣ
        QualificationDao.deleteAll(context);//ɾ��ְ����Ϣ
        GroupNumberDao.deleteAll(context);//�h����
        PunishDao.deleteAll(context);//�h���ͽ�
        RankDao.deleteAll(context);//ɾ��������Ϣ
        MarshalsDao.deleteAll(context); //ɾ��ִ���ʸ���Ϣ
        GunDao.deleteAll(context);//ɾ����ǹ֤��Ϣ
        TrainDao.deleteAll(context);//ɾ����ѵ��Ϣ
        deleteSDFile(context);
    }

    private void deleteSDFile(Context context) {
        // TODO Auto-generated method stub
        String path = GonganApplication.getSDPath(context);
        path = path.substring(7);
        Log.i("iPath", path);
        FileUtil fUtil = new FileUtil( );
        fUtil.DeleteFolder(path);

    }
}
