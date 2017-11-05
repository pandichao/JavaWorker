package com.cgxt.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.cgxt.base.BaseDaoImpl.Sqltype;

/** 
 * BaseService ����Service��ͨ�ò��� 
 *  
 * @author Monday 
 */  
public interface BaseService<T extends Serializable> { 
T findByPk(Long sid);
	
	List<T> findAll();
	
	public List<T> findList(String hql);
	
	public List<T> findList(String hql, Map<String, Object> params);
	
	public List<T> findTopList(String hql, int topCount);
	
	public List<T> findAll(String tableName);
	
	public List<T> findList(String hql, Map<String, Object> params, int page, int rows);
	
	public List<T> findList(String hql, int page, int rows);
	
	public Long getCountByHql(String hql, Map<String, Object> params);
	
	public Object getOneByHql(String hql, Object... params);
	
	public T getOneBySql(String sql);
	
	public T getOneBySql(String sql, Object... params);
	
	public T getOneBySql(String sql,Map<String,Object> params);
	
	public List<Map<String, Object>> findListBySql(String sql);
	
	public List<Map<String, Object>> findListBySql(String sql,Map<String,Object> params);
	
	public List<Map<String, Object>> findListBySql(String sql, Object... params);
	
	public void updateAll(List<T> entitys);
	
	public void deleteAll(List<T> entitys);
	
	public void insertAll(List<T> entitys);
	
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
    
    /**=========================================================================*/
    void JdbcUpdate(T entity);
    
    /**
     *�����ã�ͬʱ������ӱ��ϵ����hibernate��ɣ�һ��һ���������ӱ���������������������� 
     * @param <k>
     * @param primary  �������
     * @param fmarys   �ӱ����
     * @param pkName   �ӱ��������й������ֶΣ������������������id��
     * @param isId     �������Ƿ�Ϊ����
     */
    public <V, K> void saveOneToOneTable(K primary,V fmary,String pkName,boolean isId);
    
    /**
     * ���������ӱ�һ����룬hibernateʵ�֣�����һ�����ݣ��ӱ��������,�������ӱ����������������������
     * @param primary  �������
     * @param fmarys   �ӱ���󼯺� 
     * @param pkName   �ӱ��������й������ֶ�
     * @param isId     �������Ƿ�Ϊ����
     */
    public <V, k> void saveOneToMoneyTable(k primary,List<V> fmarys,String pkName,boolean isId);
    
    /**
     * ʹ��SpringJDBC����save
     * @param entity
     * @param IdName
     */
    public <E> int JDBCsave(E entity,String IdName,Sqltype type);
}  
