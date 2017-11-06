package com.cglib.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;



/**
 * ͳһ���ݷ��ʽӿ�
 */
public interface BaseDao<T extends Serializable> {
    
	T findByPk(Long sid);
	
    /**
     * ����ʵ�����
     * @param entity ʵ�����
     */
    public void save(T entity); 
    
    /**
     * ����ʵ�����
     * @param ʵ�����
     */
    public void update(T entity);
    
    /**
     * ��������ʵ�����
     * @param entity ʵ�����
     */
    public void saveOrUpdate(T entity);
    
    /**
     * ɾ��ʵ�����
     * @param entity ʵ�����
     */
    public void delete(T entity);
    
    
    
    
    
    
    //-----------------
    
    /**
     * ��ѯhql��䣬����Ψһ���
     * @param hql 
     */
    public Object findUniqueResult(String hql); 
    
    /**
     * ִ��sql��䣬�������ݿ�
     * @param sql
     */
    public void updateBySql(String sql);
    
    /**
     * ͨ��Criteria�����ѯ������ʵ���������
     * @param detachedCriteria ���ߵ�Criteria����
     * @return ʵ���������
     */
    public List findByCriteria(DetachedCriteria detachedCriteria);
    
    /**
     * ͨ��sql����ѯ������map��������
     * @param sql 
     * @return map��������
     */
    public List<Map<String, Object>> findBySql(String sql);
    
    /**
     * ��ѯsql��䣬����Ψһ���
     * @param sql 
     */
    public Object findUniqueResultBySql(String sql);
    
    /**
     * ͨ��Criteria�����ѯ�����ؽ�����ļ�¼��
     * @param detachedCriteria ���ߵ�Criteria����
     * @return ������ļ�¼��
     */
    public long getCount(DetachedCriteria detachedCriteria);
    
    /**
     * ͨ��Criteria������з�ҳ��ѯ������ʵ���������
     * @param pageNum �ڼ�ҳ
     * @param pageSize ÿҳ��С
     * @param detachedCriteria ���ߵ�Criteria����
     * @return ʵ��������� 
     */
    public List<T> findPage(int pageNum, int pageSize, DetachedCriteria detachedCriteria);
    
    /**
     * ͨ��sql��䣬���з�ҳ��ѯ�����ط�ҳ����
     * @param pageNum �ڼ�ҳ
     * @param pageSize ÿҳ��С
     * @param sql
     * @return ��ҳ����
     */
    public Pagination findPage(int pageNum, int pageSize, String sql); // ���ҷ�ҳ�����б�    
    /**
     * ���ô洢���̣����ص������
     * @param proceName �洢��������
     * @param params �����������
     * @return map��������
     */
    public List<Map<String, Object>> callProcedure(String proceName, final List<Object> params);
}