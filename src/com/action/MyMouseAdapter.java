package com.action;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseAdapter implements MouseListener {

    JPopupMenu menu;
    JTree tree;

    public MyMouseAdapter(JPopupMenu menu, JTree tree) {
        this.menu = menu;
        this.tree = tree;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        int count = event.getClickCount();
        if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0) && (tree.getSelectionCount() > 0)) {
            menu.show(tree, event.getX(), event.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
