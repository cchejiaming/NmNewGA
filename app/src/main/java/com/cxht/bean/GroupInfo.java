package com.cxht.bean;

import com.cxht.entity.GroupNumber;
import com.cxht.tree.TreeNodeId;
import com.cxht.tree.TreeNodeLabel;
import com.cxht.tree.TreeNodePid;

import java.io.Serializable;
import java.util.List;

/**
 * 机构信息及职数配置实体类
 * Created by HeJiaMing on 2020/11/16 10:03
 * E-Mail Address：1774690@qq.com
 */
public class GroupInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TreeNodeId
    private String id; //索引ID
    @TreeNodePid
    private String parentId; //父ID
    @TreeNodeLabel
    private String title; //机构名称
    private String group_code; //机构编码
    private GroupNumber groupNumber;//职数信息
    private List <GroupNumberItem> items;//职数配置信息整理项目


    public GroupNumber getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(GroupNumber groupNumber) {
        this.groupNumber = groupNumber;
    }

    public List <GroupNumberItem> getItems() {
        return items;
    }

    public void setItems(List <GroupNumberItem> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

}
