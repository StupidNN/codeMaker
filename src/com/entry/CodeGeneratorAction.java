package com.entry;

import com.CommonConfig;
import com.dialog.CDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.util.MySqlDBUtil;
import com.util.MysqlDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Map;

public class CodeGeneratorAction extends AnAction {
    static Logger logger = LoggerFactory.getLogger(CodeGeneratorAction.class);
    Map<String, String> mysqlDataMap = MysqlDataMap.mysqlDataMap;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        CDialog mainWindow = new CDialog("代码生成v3.1");
        try {
            init();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        mainWindow.show();
    }

    private static void init() throws IOException, ClassNotFoundException {
        logger.error("开始读取配置文件。。。");

        InputStream in = null;
        File configFile = null;
        try {
            URL url = MySqlDBUtil.class.getResource("../");
            logger.info("getUrlsss:{}", url.toURI().toString().replace("codeMaker.jar!/com/", "").replace("jar:file:/", ""));
            configFile = new File(url.toURI().toString().replace("codeMaker.jar!/com/", "").replace("jar:", "").replace("file:/", "") + "config.properties");
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
        } catch (Exception e) {
            logger.error("读取配置文件失败！:{}", e);
        }

        logger.error("读取配置文件成功！:{}", configFile);
        if (configFile == null) {
            return;
        }
        ObjectInputStream oin = new ObjectInputStream(new FileInputStream(configFile));
        Map map = (Map) oin.readObject();
        if (map != null) {
            MySqlDBUtil.ip = ((String) map.get("ip"));
            MySqlDBUtil.port = ((String) map.get("port"));
            MySqlDBUtil.userName = ((String) map.get("userName"));
            MySqlDBUtil.password = ((String) map.get("password"));
            CommonConfig.entityUrl = (String) map.get("entityUrl");
            CommonConfig.daoUrl = (String) map.get("daoUrl");
            CommonConfig.daoCheckBox = map.get("daoCheckBox") == null ? false : (boolean) map.get("daoCheckBox");
            CommonConfig.dateCheckBox = map.get("dateCheckBox") == null ? false : (boolean) map.get("daoCheckBox");
            CommonConfig.entityCheckBox = map.get("entityCheckBox") == null ? false : (boolean) map.get("entityCheckBox");
            CommonConfig.managerCheckBox = map.get("managerCheckBox") == null ? false : (boolean) map.get("managerCheckBox");
            CommonConfig.serviceCheckBox = map.get("serviceCheckBox") == null ? false : (boolean) map.get("serviceCheckBox");
            CommonConfig.dateCheckBox = map.get("dateCheckBox") == null ? false : (boolean) map.get("dateCheckBox");
        }
        logger.error("读取到的map:{}", map);
    }
}
