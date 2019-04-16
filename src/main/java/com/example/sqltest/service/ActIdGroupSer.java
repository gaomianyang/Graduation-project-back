package com.example.sqltest.service;

import com.example.sqltest.bean.ActIdGroupBean;
import com.example.sqltest.bean.ActIdMembershipBean;
import com.example.sqltest.dao.ActIdGroupDao;
import com.example.sqltest.enums.UserEnum;
import com.example.sqltest.vo.CascasderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by T016 on 2019/3/12.
 */
@Service
public class ActIdGroupSer {

    @Autowired
    private ActIdGroupDao actIdGroupDao;

    @Autowired
    private ActIdMembershipSer actIdMembershipSer;

    public ActIdGroupBean findByUserName(String userName){
        ActIdMembershipBean actIdMembershipBean = actIdMembershipSer.findByUsername(userName);
        return Optional.ofNullable(actIdMembershipBean)
                .map(a -> actIdGroupDao.findById(a.getGroupId()).get())
                .orElse(null);
    }

    public ActIdGroupBean findById(String id){
        return actIdGroupDao.findById(id).get();
    }

    public List<CascasderVo> findGroupList(){
        List<ActIdGroupBean> actIdGroupBeans = actIdGroupDao.findAll();
        List<CascasderVo> cascasderVos = new ArrayList<>();
        Map<String, CascasderVo> cascasderVoMap = new HashMap<>((int)(actIdGroupBeans.size()/0.75F + 1.0F));
        String name;
        for(int i = 0; i < actIdGroupBeans.size(); i++){
            if(UserEnum.ADMIN.getName().equals(actIdGroupBeans.get(i).getType())){
                continue;
            }
            String[] sourceStrArray = actIdGroupBeans.get(i).getGroupName().split("/");
            CascasderVo nodeCascasderVo;
            if (sourceStrArray.length == 1){
                nodeCascasderVo = new CascasderVo();
                nodeCascasderVo.setLabel(sourceStrArray[0]);
                nodeCascasderVo.setValue(actIdGroupBeans.get(i).getId());
                cascasderVoMap.put(sourceStrArray[0], nodeCascasderVo);
                cascasderVos.add(nodeCascasderVo);
            } else if (null != cascasderVoMap.get(sourceStrArray[0])) {
                nodeCascasderVo = cascasderVoMap.get(sourceStrArray[0]);
            } else {
                nodeCascasderVo = new CascasderVo();
                nodeCascasderVo.setLabel(sourceStrArray[0]);
                nodeCascasderVo.setValue(UserEnum.PROCESSVALUE.getName());
                cascasderVoMap.put(sourceStrArray[0], nodeCascasderVo);
                cascasderVos.add(nodeCascasderVo);
            }
            for(int j = 1; j < sourceStrArray.length; j++) {
                name = sourceStrArray[j];
                CascasderVo tempCascasder;
                if (null == nodeCascasderVo.getTestMap()){
                    nodeCascasderVo.setTestMap(new HashMap<>());
                } else if (null != nodeCascasderVo.getTestMap().get(name) && j != sourceStrArray.length-1) {
                    tempCascasder = nodeCascasderVo.getTestMap().get(name);
                    if(!UserEnum.PROCESSVALUE.getName().equals(tempCascasder.getValue())){
                        tempCascasder = new CascasderVo();
                        tempCascasder.setLabel(name);
                        tempCascasder.setValue(UserEnum.PROCESSVALUE.getName());
                        nodeCascasderVo.getTestMap().put(name, tempCascasder);
                        nodeCascasderVo.getChildren().add(tempCascasder);
                    }
                    nodeCascasderVo = nodeCascasderVo.getTestMap().get(name);
                    continue;
                }
                if (null == nodeCascasderVo.getChildren()){
                    nodeCascasderVo.setChildren(new ArrayList<>());
                }
                tempCascasder = new CascasderVo();
                tempCascasder.setLabel(name);
                if (j == sourceStrArray.length-1) {
                    tempCascasder.setValue(actIdGroupBeans.get(i).getId());
                } else {
                    tempCascasder.setValue(UserEnum.PROCESSVALUE.getName());
                }
                nodeCascasderVo.getTestMap().put(name, tempCascasder);
                nodeCascasderVo.getChildren().add(tempCascasder);
                nodeCascasderVo = tempCascasder;
            }
        }
        return cascasderVos;
    }

}
