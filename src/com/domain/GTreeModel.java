package com.domain;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class GTreeModel implements TreeModel {

    List<ITreeEntry> modelList = new ArrayList<ITreeEntry>();

    public GTreeModel(List<ITreeEntry> modelList) {
        this.modelList = modelList;
    }

    @Override
    public Object getRoot() {
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return getChild(parent).get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        List<ITreeEntry> childs = getChild(parent);
        ;
        return childs.size();
    }

    @Override
    public boolean isLeaf(Object node) {
        return getChild(node).size() != 0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return 0;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }

    private List<ITreeEntry> getChild(Object parent) {
        List<ITreeEntry> childs = null;
        if (parent instanceof ITreeEntry) {
            childs = ((ITreeEntry) parent).getChildren();
        }
        return childs;
    }
}
