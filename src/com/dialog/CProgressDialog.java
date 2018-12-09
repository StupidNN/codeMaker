package com.dialog;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.*;

public class CProgressDialog extends JDialog {
    private JPanel contentPane;
    private JProgressBar progressBar1;

    public CProgressDialog() {
        setTitle("代码生成中...");
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setModal(true);
        setSize(200, 150);
        progressBar1.setMinimum(0);
        progressBar1.setMaximum(100);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }


    public JProgressBar getProgressBar1() {
        return progressBar1;
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CProgressDialog dialog = new CProgressDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void doCreateCode(JTree tree) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setVisible(true);
            }
        }).start();
        //生成代码了
        new Thread(new Runnable() {
            @Override
            public void run() {
                TreePath[] paths = tree.getSelectionPaths();
                int i = 0;
                for (TreePath path : paths) {
                    GTreeDialog.doCreateCode(path);
                    i++;
                    getProgressBar1().setValue((int) (100 * (i * 1.0 / paths.length)));
                }
                dispose();
            }
        }).start();
    }
}
