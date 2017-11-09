package com.cglib.base;

import java.util.ArrayList;
import java.util.List;

/**
 * ��ҳ����
 */
public class Pagination {
	/**
     * �ܼ�¼��
     */
    private Long total = 0l;
    
    /**
     * ��¼����
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
