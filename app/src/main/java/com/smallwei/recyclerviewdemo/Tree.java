package com.smallwei.recyclerviewdemo;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    private T data;
    private int level = 0;
    private List<Tree<?>> children;
    private boolean expand = false;

    public Tree(T data) {
        this.data = data;
        children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Tree<?>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<?>> children) {
        this.children = children;
    }
    public boolean isExpandable(){
        return children != null && !children.isEmpty();
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
    public void addChild(Tree<?> child){
        child.setLevel(level + 1);
        children.add(child);
    }
}
