package com.cgxt.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 */
public class Pagination {
	/**
     * 总记录数
     */
    private Long total = 0l;
    
    /**
     * 记录集合
     */
    private List rows = new ArrayList();
    
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }    
    
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }            
}
