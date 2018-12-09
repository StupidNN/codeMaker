package com.domain;

import com.CommonConfig;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author zdh
 */
public class EntityInfoVO {
    private String originName;
    private String name;
    private String type;
    private String jdbcType;
    private boolean nullable;
    private String comment;

    public String getName() {
        return name;
    }

    public String getTuoFengName() {
        String s[] = originName.split("_");
        if (s.length <= 1) {
            return s[0];
        } else {
            StringBuffer sb = new StringBuffer(s[0]);
            for (String s1 : s) {
                if (s1.equals(s[0])) continue;
                sb.append(Character.toUpperCase(s1.charAt(0)) + s1.substring(1));
            }
            return sb.toString();
        }
    }

    public void setName(String name) {
        setOriginName(name);
        this.name = format(name);
    }

    public String getType() {
        if ("dataStatus".equals(name)) {
            return "DataStatus";
        }
        if ("id".equals(name)) {
            return "Long";
        }
        if ("LocalDateTime".equals(type) && !CommonConfig.dateCheckBox) {
            return "Date";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    private String firstUpperCase(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private String format(String name) {
        StringBuffer sb = new StringBuffer();
        int i = 1;
        for (String seg : name.split("_")) {
            if (1 == i) {
                sb.append(seg);
            } else {
                sb.append(firstUpperCase(seg));
            }
        }
        return sb.toString();
    }

    public String getComment() throws UnsupportedEncodingException {
        if (StringUtils.isBlank(this.comment)) {
            return null;
        }
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
