package com.cxht.bean;

import com.cxht.entity.GroupNumber;
import com.cxht.tree.TreeNodeId;
import com.cxht.tree.TreeNodeLabel;
import com.cxht.tree.TreeNodePid;

import java.io.Serializable;
import java.util.List;

/**
 * ������Ϣ��ְ������ʵ����
 * Created by HeJiaMing on 2020/11/16 10:03
 * E-Mail Address��1774690@qq.com
 */
public class GroupInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TreeNodeId
    private String id; //����ID
    @TreeNodePid
    private String parentId; //��ID
    @TreeNodeLabel
    private String title; //��������
    private String group_code; //��������
    private GroupNumber groupNumber;//ְ����Ϣ
    private List <GroupNumberItem> items;//ְ��������Ϣ������Ŀ


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
