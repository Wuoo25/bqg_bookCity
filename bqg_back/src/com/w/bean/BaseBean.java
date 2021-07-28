package com.w.bean;

/**
 * 数据管理经常涉及分页
 * 所以需要单独建立，其他JavaBean可以选择继承此方法,方便统一管理
 */
public class BaseBean {

    private int pageNo;
    private int pageSize=2;//每页数据条数
    private int start;//数据库查询的起始位置

    public int getPageNo() {
        return pageNo;
    }

    //BeanUtils会调用set方法放数据
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        start=(pageNo-1)*pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", start=" + start +
                '}';
    }
}
