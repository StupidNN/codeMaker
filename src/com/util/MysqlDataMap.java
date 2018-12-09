package com.util;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlDataMap {
    static Logger logger = LoggerFactory.getLogger(MysqlDataMap.class);
    public static Map<String, String> mysqlDataMap = new HashMap<>();

    static {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document d = builder.build(MysqlDataMap.class.getResourceAsStream("../../META-INF/mysql-database-mapping.xml"));
            Element el = d.getRootElement();
            List<Element> lstElement = el.getChildren();
            lstElement.forEach(e -> {
                List<Element> elements = e.getChildren();
                mysqlDataMap.put(e.getChild("database").getValue(), e.getChild("java").getValue());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document d = builder.build(MysqlDataMap.class.getResourceAsStream("../../META-INF/mysql-database-mapping.xml"));
            Element el = d.getRootElement();
            List<Element> lstElement = el.getChildren();
            lstElement.forEach(e -> {
                List<Element> elements = e.getChildren();
                mysqlDataMap.put(e.getChild("database").getValue(), e.getChild("java").getValue());
            });
            System.out.print(mysqlDataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
