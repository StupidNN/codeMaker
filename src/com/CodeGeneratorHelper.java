package com;

import com.domain.EntityInfoVO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ��������
 *
 * @author zdh
 */
public class CodeGeneratorHelper {
    private static Configuration cfg = new Configuration();
    static Logger logger = LoggerFactory.getLogger(CodeGeneratorHelper.class);

    public static void inputstreamtofile(InputStream ins, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    /**
     * ���ɴ���
     *
     * @param infoList      ���ݿ���Ϣʵ���б�
     * @param originalTable
     * @param tableName     ����
     * @throws Exception
     */
    public static void generateCode(ArrayList<EntityInfoVO> infoList, String originalTable, String tableName, String dir, String type) throws Exception {
        if (infoList.size() == 0) {
            return;
        }
        cfg.setClassForTemplateLoading(CodeGeneratorHelper.class, "template");

        Template voTemplate = cfg.getTemplate(type + "Template/" + "voTemplate.ftl", "utf-8");
        Template daoTemplate = cfg.getTemplate(type + "Template/" + "daoTemplate.ftl", "utf-8");
        Template daoXMLTemplate = cfg.getTemplate(type + "Template/" + "daoXMLTemplate.ftl", "utf-8");
        Template managerTemplate = cfg.getTemplate(type + "Template/" + "managerTemplate.ftl", "utf-8");
        Template serviceTemplate = cfg.getTemplate(type + "Template/" + "serviceTemplate.ftl", "utf-8");
        Template serviceImplTemplate = cfg.getTemplate(type + "Template/" + "serviceImplTemplate.ftl", "utf-8");

        Map<String, Map<String, Serializable>> table = new HashMap<String, Map<String, Serializable>>();
        Map<String, Serializable> tableMap = new HashMap<String, Serializable>();
        table.put("table", tableMap);

        tableMap.put("name", tableName);
        tableMap.put("originalTable", originalTable);
        String[] names = tableName.split("_");
        StringBuffer sb = new StringBuffer();
        for (String n : names) {
            sb.append(n.subSequence(0, 1).toString().toUpperCase().concat(n.substring(1)));
        }
        tableMap.put("className", sb.toString());
        tableMap.put("lstEntityInfo", infoList);
        tableMap.put("now", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //通用配置
        tableMap.put("commonConfig", new CommonConfig());

        createSuiCode(dir, voTemplate, daoTemplate, daoXMLTemplate, managerTemplate, serviceTemplate, serviceImplTemplate, table, sb);
        return;
    }

//    private static void createVillaCode(String dir, Template voTemplate, Template daoTemplate, Template daoXMLTemplate, Template managerTemplate, Map<String, Map<String, Serializable>> table, StringBuffer sb) throws TemplateException, IOException {
//        StringBuffer vofileName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append(".java");
//        StringBuffer daofileName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append("Dao.java");
//        StringBuffer daoXMLName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append("Dao.xml");
//        StringBuffer managerName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append("Manager.java");
//
//
//        FileOutputStream vofileNameo = new FileOutputStream(new File(vofileName.toString()));
//        FileOutputStream daofileNameo = new FileOutputStream(new File(daofileName.toString()));
//        FileOutputStream daoXMLNameo = new FileOutputStream(new File(daoXMLName.toString()));
//        FileOutputStream managerNameo = new FileOutputStream(new File(managerName.toString()));
//        voTemplate.process(table, new OutputStreamWriter(vofileNameo, "utf-8"));
//        daoTemplate.process(table, new OutputStreamWriter(daofileNameo, "utf-8"));
//        daoXMLTemplate.process(table, new OutputStreamWriter(daoXMLNameo, "utf-8"));
//        managerTemplate.process(table, new OutputStreamWriter(managerNameo, "utf-8"));
//
//        managerNameo.close();
//        daoXMLNameo.close();
//        daofileNameo.close();
//        vofileNameo.close();
//    }

    private static void createSuiCode(String dir, Template voTemplate, Template daoTemplate, Template daoXMLTemplate, Template managerTemplate, Template serviceTemplate, Template serviceImplTemplate, Map<String, Map<String, Serializable>> table, StringBuffer sb) throws TemplateException, IOException {
        StringBuffer vofileName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append(".java");
        StringBuffer daofileName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append("Mapper.java");
        StringBuffer daoXMLName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append("Mapper.xml");
        StringBuffer serviceName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append("Service.java");
        StringBuffer ServiceImpl = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append("ServiceImpl.java");
        StringBuffer managerName = new StringBuffer(dir.replace("\\", "/")).append("/").append(sb.toString()).append("Manager.java");

        if (CommonConfig.entityCheckBox) {
            FileOutputStream vofileNameo = new FileOutputStream(new File(vofileName.toString()));
            voTemplate.process(table, new OutputStreamWriter(vofileNameo, "utf-8"));
            vofileNameo.close();
        }
        if (CommonConfig.daoCheckBox) {
            FileOutputStream daofileNameo = new FileOutputStream(new File(daofileName.toString()));
            FileOutputStream daoXMLNameo = new FileOutputStream(new File(daoXMLName.toString()));
            daoTemplate.process(table, new OutputStreamWriter(daofileNameo, "utf-8"));
            daoXMLTemplate.process(table, new OutputStreamWriter(daoXMLNameo, "utf-8"));
            daofileNameo.close();
            daoXMLNameo.close();
        }
        if (CommonConfig.serviceCheckBox) {
            FileOutputStream serviceNameo = new FileOutputStream(new File(serviceName.toString()));
            FileOutputStream ServiceImplo = new FileOutputStream(new File(ServiceImpl.toString()));
            serviceTemplate.process(table, new OutputStreamWriter(serviceNameo, "utf-8"));
            serviceImplTemplate.process(table, new OutputStreamWriter(ServiceImplo, "utf-8"));
            serviceNameo.close();
            ServiceImplo.close();
        }
        if (CommonConfig.managerCheckBox) {
            FileOutputStream managerNameo = new FileOutputStream(new File(managerName.toString()));
            managerTemplate.process(table, new OutputStreamWriter(managerNameo, "utf-8"));
            managerNameo.close();
        }
    }

}
