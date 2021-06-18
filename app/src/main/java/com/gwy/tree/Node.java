package com.gwy.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deqiang.wang on 2018/4/7.
 */

public class Node <T>{
    private String _id;
    private Node _parent;
    private List<Node> _children=new ArrayList<>();
    private T obj;
    private String orgId;
    private int _size_all;
    private int _size_expand;
    private boolean isExpand=true;
    private int _childCount = 0;
    private boolean checked = false;

    public Node(String id,String orgId ,T obj){
        this._id = id;
        this.obj = obj;
        this.orgId = orgId;
        _size_expand=1;
        _size_all=1;
    }
    public Node get_parent(){return _parent;}
    void set_parent(Node node){
        this._parent = node;
    }
    public List<Node> get_children(){return _children;}
    int get_size(boolean isExpand){return isExpand?_size_expand:_size_all;}
    void set_size(int size,boolean isExpand){
        if(isExpand)
            _size_expand = size;
        else
            _size_all=size;
    }

    Node find_Node_By_Id(String id,boolean isExpand){
        if(this.get_id()==id)
            return this;

        List<Node> list=this.get_children();
        if(list.size()==0){
            return null;
        }else{
            if((isExpand && this.getExpand()) || !isExpand){
                for(Node node: this.get_children()){
                    Node result=node.find_Node_By_Id(id,isExpand);
                    if(result!=null)
                        return result;
                }
            }
        }
        return null;
    }

    Node get(int position,boolean isExpand){
        if(position==0)
            return this;
        position--;
        List<Node> list=this.get_children();
        if(list.size()==0){
            return null;
        }else{
            if(!isExpand || (isExpand && this.getExpand())){
                for(Node node:this.get_children()){
                    int size = position - node.get_size(isExpand);
                    if(size<0){
                        return node.get(position,isExpand);
                    }else{
                        position=size;
                    }
                }//for
            }//if
        }//if
        return null;
    }
    void setExpand(boolean expand) {
        isExpand = expand;
    }
    boolean getExpand(){
        return isExpand;
    }
    public boolean isLeaf(){
        return this.get_children().size()==0;
    }
    public boolean isExpand(){
        return isExpand;
    }
    public String get_id(){return _id;}
    public int get_level(){
        if(_parent!=null)
            return _parent.get_level()+1;
        else
            return 0;
    }
    public T getObject(){
        return obj;
    }
    public int get_childCount() {
        return _childCount;
    }
    public void set_childCount(int _childCount) {
        this._childCount = _childCount;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String getOrgId() {
        return orgId;
    }
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

}