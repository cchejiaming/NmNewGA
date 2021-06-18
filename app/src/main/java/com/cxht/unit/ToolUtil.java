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
     * 整理奖励信息和惩戒信息为任免表输出奖惩信息，最大只支持4条显示数据
     * 因为任免表中奖励和惩戒在一个信息集了，而内蒙这里确是分开二个集合
     *
     * @param es 奖励信息集
     * @param ps 惩戒信息集
     * @return
     */
    public static List <Examine> rmbExamine(List <Examine> es, List <Punish> ps) {
        List <Examine> data = null;
        if (es != null || ps != null) {
            data = new ArrayList <>( );
            if (es != null && ps != null) {
                //如果奖励和惩戒都有，每项最多取二个
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
                //如果只有一项填充其中一项不超过4条
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
     * 数据销毁
     */
    public void delAppData(Context context) {
        UserDao.deleteAll(context); // 删除基础表
        JobDao.deleteAll(context);// 删除单位组织表
        AssessDao.deleteAll(context);// 删除考核信息表
        ExamineDao.deleteAll(context);// 删除奖惩信息表
        FamilyDao.deleteAll(context);// 删除家庭成员
        ResumeDao.deleteAll(context);// 删除履历表
        UserFieldDao.deleteAll(context);// 删除用户自定义信息集
        HistoryDao.deleteAllHistory(context);//删除历史记录
        DegreeDao.deleteAll(context); //删除学位信息
        QualificationDao.deleteAll(context);//删除职称信息
        GroupNumberDao.deleteAll(context);//刪除職數
        PunishDao.deleteAll(context);//刪除懲戒
        RankDao.deleteAll(context);//删除警衔信息
        MarshalsDao.deleteAll(context); //删除执法资格信息
        GunDao.deleteAll(context);//删除持枪证信息
        TrainDao.deleteAll(context);//删除培训信息
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
