package com.gwy.tree;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by deqiang.wang on 2018/4/6.
 */
public class Tree <T>{
    private Node root;
    public Tree(){
        root = new Node("-1","-1",null);
    }
  public Node getRoot(){
	  return root;
  }
   public boolean addRoot(String id,String orgId,T t){
       if(id=="-1"){
           Log.w(TAG, "addRoot: node.id cannot be less than 0" );
           return false;
       }

        if(findNode(id)==null){
            addNodeToParent(new Node(String.valueOf(id),orgId,t),root);
        }else {
            Log.w(TAG, String.format("addRoot: node.id=%d exists!",id));
            return false;
        }

        return true;
   }

   public boolean addLeaf(String id, String pid, int childCount,String orgId,Object object){
        if(id=="-1" || pid=="-1"){
            Log.w(TAG, "addNode: id or pid should not be less than 0");
            return false;
        }
        Node node = findNode(id);
        if(node!=null){
            Log.w(TAG, String.format("addNode: node.id=%d exists",id));
            return false;
        }
        Node parent = findNode(pid);
        if(parent==null){
            Log.w(TAG, String.format("addNode: cannot find parent with id=",pid));
            return false;
        }
        node = new Node(String.valueOf(id),orgId,object);
        node.set_childCount(childCount);
        addNodeToParent(node,parent);
        return true;
   }

    private void addNodeToParent(Node node, Node parent){
        node.set_parent(parent);
        parent.get_children().add(node);

        while(parent!=null){
            parent.set_size(parent.get_size(false)+node.get_size(false),false);
            parent.set_size(parent.get_size(true)+node.get_size(true),true);
            parent = parent.get_parent();
        }
    }
    //åˆ é™¤ä¸?¸ªèŠ‚ç‚¹
    public void deleteNode(String id){
        if(id=="-1"){
            Log.w(TAG, "deleteNode: id should not be less than 0");
            return;
        }
        Node node = findNode(id);
        if(node==null){
            Log.w(TAG, "deleteNode: cannot find the node.id="+id );
            return;
        }
        Node parent = node.get_parent();
        parent.get_children().remove(node);
        while(parent!=null){
            parent.set_size(parent.get_size(false)-node.get_size(false),false);
            parent.set_size(parent.get_size(true)-node.get_size(true),true);
            parent=parent.get_parent();
        }
    }
    public Node findNode(String id){
        return root.find_Node_By_Id(id,false);
    }

    public Node getInAll(int position){
        return get(position,false);
    }

    public Node getInCollapse(int position){
        return get(position,true);
    }
    private Node get(int position, boolean isExpand){
        return root.get(position+1,isExpand);
    }
    public int sizeOfAll(){
        return size(false);
    }
    public int sizeOfCollapse(){
        return size(true);
    }

    private int size(boolean isExpand){
        return root.get_size(isExpand) - 1;
    }

    public boolean expandOrCollapse(int position){
        Node node = getInCollapse(position);
        if(node==null) {
            Log.w(TAG, "expandOrCollapse: cannot find node at position="+position );
            return false;
        }
        if(node.isLeaf())
            return false;
        if(node.getExpand()){

            int sizedelta = node.get_size(true) - 1;
            node.set_size(1,true);
            Node parent = node.get_parent();
            while(parent!=null){
                if(parent.getExpand()==false){
                    Log.e(TAG, String.format("expandOrCollapse: node.id should be collapsed!",parent.get_id()) );
                    return false;
                }
                parent.set_size(parent.get_size(true) - sizedelta,true);
                parent = parent.get_parent();
            }
        }else{

            int sizedelta=0;
            List<Node> children=node.get_children();
            for(Node son:children){
                sizedelta +=son.get_size(true);
            }
            node.set_size(1+sizedelta,true);
            Node parent = node.get_parent();
            while(parent!=null){
                if(parent.getExpand()==false){
                    Log.e(TAG, String.format("expandOrCollapse: node.id should be collapsed!",parent.get_id()) );
                    return false;
                }
                parent.set_size(parent.get_size(true) + sizedelta,true);
                parent = parent.get_parent();
            }
        }
        node.setExpand(!node.getExpand());
        return true;
    }
}

