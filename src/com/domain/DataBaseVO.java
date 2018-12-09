package com.domain;

import java.util.List;

/**
 * @author zdh
 */
public class DataBaseVO implements ITreeEntry {
    private Long id;
    private String name;

    private List<ITreeEntry> table;

    public DataBaseVO(String name) {
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
        this.table = children;
    }

    @Override
    public List<ITreeEntry> getChildren() {
        return table;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
