package com.domain;

import java.util.List;

public interface ITreeEntry {
    /*
     */
    public String getName();

    public void setName(String name);

    /*
     */
    public void setChildren(List<ITreeEntry> children);

    public List<ITreeEntry> getChildren();

    public String toString();
}
