package com.util;

import com.CommonConfig;
import com.domain.DataBaseVO;
import com.domain.ITreeEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class MySqlDBUtil {
    static Logger logger = LoggerFactory.getLogger(MySqlDBUtil.class);
    private static Connection conn;

    public static String driver = "com.mysql.jdbc.Driver";

    public static String url = "jdbc:mysql://%s:%s";
    public static String userName = null;
    public static String password = null;
    public static String port;
    public static String ip;

    public static boolean connect(String userName, String password, String dbName) {

        try {
            close();
            Class.forName(driver);
            conn = DriverManager.getConnection(url + dbName, userName, password);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static ArrayList<ITreeEntry> getAllCatalogs() {
        ArrayList<ITreeEntry> rList = new ArrayList<ITreeEntry>();
        try {
            ResultSet rs = getMetaData().getCatalogs();
            while (rs.next()) {
                rList.add(new DataBaseVO((rs.getString("TABLE_CAT"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rList;
    }

    public static DatabaseMetaData getMetaData() throws SQLException {
        return conn.getMetaData();
    }

    public static void close() {
        try {
            if (conn != null && !conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean connect(String userName, String password, String ip, String port) {

        try {
            close();
            Class.forName(driver);
            MySqlDBUtil.url = String.format("jdbc:mysql://%s:%s", ip, port);
            MySqlDBUtil.ip = ip;
            MySqlDBUtil.port = port;
            conn = DriverManager.getConnection(MySqlDBUtil.url, userName, password);
//            File configFile = new File(MySqlDBUtil.class.getResourceAsStream("../../META-INF/config.properties").toString());

//            logger.info("getUrlsss:{}", url.toURI().toString().replace("jar:file:/", ""));

            //dev
//            ObjectOutputStream o = new ObjectOutputStream(url.openConnection().getOutputStream());

            URL url = MySqlDBUtil.class.getResource("../");
            logger.info("getUrlsss:{}", url.toURI().toString().replace("codeMaker.jar!/com/", "").replace("jar:file:/", ""));
            File configFile = new File(url.toURI().toString().replace("codeMaker.jar!/com/", "").replace("jar:", "").replace("file:/", "") + "config.properties");
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(configFile));
            Map map = new HashMap();
            map.put("ip", ip);
            map.put("port", port);
            map.put("userName", userName);
            map.put("password", password);
            map.put("entityUrl", CommonConfig.entityUrl);
            map.put("daoUrl", CommonConfig.daoUrl);
            map.put("entityCheckBox", CommonConfig.entityCheckBox);
            map.put("daoCheckBox", CommonConfig.daoCheckBox);
            map.put("managerCheckBox", CommonConfig.managerCheckBox);
            map.put("serviceCheckBox", CommonConfig.serviceCheckBox);
            map.put("daoCheckBox", CommonConfig.daoCheckBox);
            map.put("codeSaveUrl", CommonConfig.codeSaveUrl);
            map.put("dateCheckBox", CommonConfig.dateCheckBox);
            logger.error("开始写入配置文件,:{}", map);
            o.writeObject(map);
            o.close();
            logger.error("写入配置文件成功,:{}", map);
        } catch (Exception e) {
            logger.error("写入配置文件异常:{}", e);
            return false;
        }
        return true;
    }

    public static ArrayList<String> getAllTables(String catalog) {
        ArrayList<String> rList = new ArrayList<String>();
        try {
            ResultSet rs = getMetaData().getTables(catalog, "", "", null);
            while (rs.next()) {
                rList.add(new String(rs.getString("TABLE_NAME")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rList;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
//        JarFile jarFile = new JarFile("jar:file:/C:/Users/Administrator/.IntelliJIdea2018.1/config/plugins/codeMaker/lib/codeMaker.jar");
//        Enumeration<JarEntry> s = jarFile.entries();
//        while (s.hasMoreElements()) {
//            JarEntry a = s.nextElement();
//            if ("META-INF/config.properties".equals(a.toString())) {
//                JarOutputStream outputStream = new JarOutputStream(new FileOutputStream(new File("C:\\Users\\Administrator\\.IntelliJIdea2018.1\\config\\plugins\\codeMaker\\lib\\codeMaker.jar")));
//                outputStream.putNextEntry(a);
//                outputStream.close();
//                System.out.println(a);
//            }
//        }


        JarOutputStream outputStream = new JarOutputStream(new FileOutputStream("C:/Users/Administrator/.IntelliJIdea2018.1/config/plugins/codeMaker/lib/codeMaker.jar"));
        outputStream.putNextEntry(new JarEntry("/META-INF/config.properties"));
        ObjectOutputStream o = new ObjectOutputStream(outputStream);
        //dev
//            ObjectOutputStream o = new ObjectOutputStream(url.openConnection().getOutputStream());
//            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File((MySqlDBUtil.class.getResource("/config.properties").toString().replace("file:/", "")))));
        logger.error("配置文件写入路径:{}", MySqlDBUtil.class.getResource("/config.properties").toString().replace("file:/", ""));
        Map map = new HashMap();
        map.put("ip", ip);
        map.put("port", port);
        map.put("userName", userName);
        map.put("password", password);
        logger.error("开始写入配置文件,:{}", map);
        o.writeObject(map);
        outputStream.close();

//        JarOutputStream outputStream = new JarOutputStream(new FileOutputStream(new File("C:\\Users\\Administrator\\.IntelliJIdea2018.1\\config\\plugins\\codeMaker\\lib\\codeMaker.jar")));
//        outputStream.putNextEntry(new JarEntry("/META-INF/config.properties"));
//        outputStream.write(65);
//        outputStream.close();
    }

}
