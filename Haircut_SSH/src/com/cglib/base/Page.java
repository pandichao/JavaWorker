package com.cglib.base;

import java.util.List;

public class Page<T> {
	 
    private int page;
    private int rows;
    private int total;
    private List<T> list;
 
    public Page() {
        super();
    }
 
    public Page(int page, int rows, int total, List<T> list) {
        super();
        this.page = page;
        this.rows = rows;
        this.total = total;
        this.list = list;
    }
    
    public int getOffset() {
        return (rows - 1) * page;
    }
 
    public int getPage() {
        return page;
    }
 
    public void setPage(int page) {
        this.page = page;
    }
 
    public int getRows() {
        return rows;
    }
 
    public void setRows(int rows) {
        this.rows = rows;
    }
 
    public List<T> getList() {
        return list;
    }
 
    public void setList(List<T> list) {
        this.list = list;
    }
 
    public int size(){
        return null==list?0:list.size();
    }
 
    public int getTotal() {
        return total;
    }
 
    public void setTotal(int total) {
        this.total = total;
    }
 
}
