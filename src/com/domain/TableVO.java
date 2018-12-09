package com.domain;

import java.util.List;

/**
 * ������Ϣ��Ϣ��
 *
 * @author zdh
 */
public class TableVO implements ITreeEntry {
    private Long id; //
    private String name; //

    private List<ITreeEntry> cities;

    public TableVO() {
    }

    public TableVO(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setChildren(List<ITreeEntry> children) {
        this.cities = children;
    }

    @Override
    public List<ITreeEntry> getChildren() {
        return cities;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
