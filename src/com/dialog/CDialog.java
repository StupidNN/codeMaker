package com.dialog;

import com.CommonConfig;
import com.domain.GTreeModel;
import com.domain.ITreeEntry;
import com.util.MySqlDBUtil;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class CDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JTextField urlAndPort;
    private JTextField entityUrl;
    private JTextField daoUrl;
    private JCheckBox entityCheckBox;
    private JCheckBox daoCheckBox;
    private JCheckBox managerCheckBox;
    private JCheckBox serviceCheckBox;
    private JTextField codeSaveUrl;
    private JCheckBox dateCheckBox;

    public CDialog(String title) {
        super();
        setSize(500, 280);
        setTitle(title);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

    @Override
    public void show() {
        if (StringUtils.isNotBlank(MySqlDBUtil.userName) && StringUtils.isNotBlank(MySqlDBUtil.ip) && StringUtils.isNotBlank(MySqlDBUtil.port) && StringUtils.isNotBlank(MySqlDBUtil.password)) {
            this.urlAndPort.setText(MySqlDBUtil.ip + "," + MySqlDBUtil.port);
            this.textField1.setText(MySqlDBUtil.userName);
            this.passwordField1.setText(MySqlDBUtil.password);
            if (CommonConfig.entityUrl != null) {
                this.entityUrl.setText(CommonConfig.entityUrl);
            }
            if (CommonConfig.codeSaveUrl != null) {
                this.codeSaveUrl.setText(CommonConfig.codeSaveUrl);
            }
            if (CommonConfig.daoUrl != null) {
                this.daoUrl.setText(CommonConfig.daoUrl);
            }

            if (CommonConfig.daoCheckBox) {
                this.daoCheckBox.setSelected(CommonConfig.daoCheckBox);
            }
            if (CommonConfig.entityCheckBox) {
                this.entityCheckBox.setSelected(CommonConfig.entityCheckBox);
            }
            if (CommonConfig.managerCheckBox) {
                this.managerCheckBox.setSelected(CommonConfig.managerCheckBox);
            }
            if (CommonConfig.serviceCheckBox) {
                this.serviceCheckBox.setSelected(CommonConfig.serviceCheckBox);
            }
            if (CommonConfig.dateCheckBox) {
                this.dateCheckBox.setSelected(CommonConfig.dateCheckBox);
            }

        } else {
        }
        super.show();
    }

    private void onOK() {
        connDB();
        List<ITreeEntry> cataLog = MySqlDBUtil.getAllCatalogs();
        GTreeDialog d = new GTreeDialog(cataLog);
        d.setVisible(true);
        dispose();
    }

    private void onCancel() {
        MySqlDBUtil.close();
        dispose();
    }

    private void connDB() {
        String userName = textField1.getText().trim();
        String password = passwordField1.getText();
        try {
            setConfig();
            String ip = urlAndPort.getText().trim().split(",")[0];
            String port = urlAndPort.getText().trim().split(",")[1];
            MySqlDBUtil.connect(userName, password, ip, port);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "url端口号必须用逗号隔开");
        }
    }

    private void setConfig() {
        CommonConfig.daoUrl = daoUrl.getText();
        CommonConfig.entityCheckBox = entityCheckBox.isSelected();
        CommonConfig.daoCheckBox = daoCheckBox.isSelected();
        CommonConfig.managerCheckBox = managerCheckBox.isSelected();
        CommonConfig.serviceCheckBox = serviceCheckBox.isSelected();
        CommonConfig.dateCheckBox = dateCheckBox.isSelected();
        CommonConfig.codeSaveUrl = codeSaveUrl.getText();
    }

    private GTreeModel getTreeModel(List<ITreeEntry> list) {
        return new GTreeModel(list);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
