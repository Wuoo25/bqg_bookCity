package com.banyuan.bean.opus;

import java.util.Arrays;

public class BookShelfBean {

    private int id;
    private int readerId;
    private int opusId;

    private String icon;
    private String name;
    private int[] ids;

    public int[] getIds() {
        return ids;
    }

    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getOpusId() {
        return opusId;
    }

    public void setOpusId(int opusId) {
        this.opusId = opusId;
    }

    @Override
    public String toString() {
        return "BookShelfBean{" +
                "id=" + id +
                ", readerId=" + readerId +
                ", opusId=" + opusId +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", ids=" + Arrays.toString(ids) +
                '}';
    }
}
