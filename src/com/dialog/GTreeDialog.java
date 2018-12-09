package com.dialog;

import com.CodeGeneratorHelper;
import com.CommonConfig;
import com.action.MyMouseAdapter;
import com.domain.DataBaseVO;
import com.domain.EntityInfoVO;
import com.domain.ITreeEntry;
import com.domain.TableVO;
import com.util.MySqlDBUtil;
import com.util.MysqlDataMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GTreeDialog extends JDialog implements ActionListener {
    static Logger logger = LoggerFactory.getLogger(GTreeDialog.class);
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTree tree;
    private java.util.List<ITreeEntry> list;
    private JScrollPane scrollPane;
    JMenuItem m1;

    public GTreeDialog(java.util.List<ITreeEntry> list) {
        setSize(300, 400);
        setLocationRelativeTo(null);
        tree = new JTree(createTreeNode(list));
        JPopupMenu menu = new JPopupMenu();
        m1 = new JMenuItem("生成代码");
        menu.add(m1);
        m1.addActionListener(this);
        tree.setComponentPopupMenu(new JPopupMenu());
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (node.getChildCount() == 0 && node.getUserObject() instanceof DataBaseVO) {
                    //获取该数据库下所有的表名
                    List<String> tableList = MySqlDBUtil.getAllTables(node.toString());
                    DefaultMutableTreeNode childs = new DefaultMutableTreeNode();
                    for (String table : tableList) {
                        DefaultMutableTreeNode child = new DefaultMutableTreeNode(new TableVO(table));
                        childs.add(child);
                    }
                    //把表插入到该数据库节点下面
                    ((DefaultTreeModel) tree.getModel()).insertNodeInto(childs, node, 0);
                }
            }
        });

        MyMouseAdapter mouseAdapter = new MyMouseAdapter(menu, tree);
        tree.addMouseListener(mouseAdapter);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tree);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
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

    private void onOK() {
        // add your code here
        dispose();
    }

    public void setTree(JTree tree) {
        this.tree = tree;
    }

    private void onCancel() {

        // add your code here if necessary
        dispose();
    }

    private DefaultMutableTreeNode createTreeNode(List<ITreeEntry> list) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new DataBaseVO("数据库"));
        for (ITreeEntry o : list) {
            root.add(new DefaultMutableTreeNode(o, true));
        }
        return root;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(m1)) {
            CProgressDialog c = new CProgressDialog();
            //交给进度条窗口进行 代码生成
            c.doCreateCode(tree);
        }
    }

    public static void doCreateCode(TreePath path) {
        int count = path.getPathCount();
        String db = null;
        String table = null;
        String pathName = null;
        for (int i = 0; i < count; i++) {
            String o = path.getPathComponent(i).toString();
            pathName = StringUtils.isNotBlank(o) ? ((ITreeEntry) ((DefaultMutableTreeNode) path.getPathComponent(i)).getUserObject()).getName() : null;
            if (StringUtils.isNotBlank(pathName)) {
                if (pathName.equals("数据库")) {
                    continue;
                }
                if (db == null) {
                    db = pathName;
                } else {
                    table = pathName;
                }
            }
        }

        //连接数据库
        try {
            ArrayList<EntityInfoVO> entityArrToGen = new ArrayList<>();
            ResultSet colRet = MySqlDBUtil.getMetaData().getColumns(db, "%", table, "%");
            while (colRet.next()) {
                EntityInfoVO entity = new EntityInfoVO();
                entity.setName(colRet.getString("COLUMN_NAME"));
                entity.setType(MysqlDataMap.mysqlDataMap.get(colRet.getString("TYPE_NAME")));
                entity.setJdbcType(colRet.getString("TYPE_NAME"));
                entity.setNullable(colRet.getBoolean("NULLABLE"));
                entity.setComment(colRet.getString("REMARKS"));
                entityArrToGen.add(entity);
            }

            for (EntityInfoVO entityInfoVO : entityArrToGen) {
                System.out.println(entityInfoVO.getName() + " " + entityInfoVO.getType() + " " + entityInfoVO.getJdbcType());
            }

            String originalTable = table;
            if (table.startsWith("t_")) {
                table = table.replaceFirst("t_", "");
            }

            // 通过freemaker生成代码
            FileSystemView fsv = FileSystemView.getFileSystemView();
            File com = fsv.getHomeDirectory();    //这便是读取桌面路径的方法了

            String dir = com.getPath() + "\\codes";
            if (!new File(dir).exists()) {
                new File(dir).mkdir();
            }

            try {
                if (!new File(CommonConfig.codeSaveUrl).isDirectory()) {
                    throw new RuntimeException();
                }
                if (!new File(CommonConfig.codeSaveUrl).exists()) {
                    new File(CommonConfig.codeSaveUrl).mkdir();
                }
                dir = CommonConfig.codeSaveUrl;
            } catch (Exception e1) {
                dir = com.getPath() + "\\codes";
            }
            CodeGeneratorHelper.generateCode(entityArrToGen, originalTable, table, dir, "sui");

        } catch (Exception e1) {
            logger.error("牛逼" + e1);
        }
    }
}
