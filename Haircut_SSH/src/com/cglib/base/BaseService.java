package com.cglib.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import com.cglib.utils.AbstractEntity;


/** 
 * BaseService 定义Service的通用操作 
 *  
 * @author Monday 
 */  
public interface BaseService<T extends AbstractEntity> {  
	/**
     * 保存实体对象
     * @param entity 实体对象
     */
    public void save(T entity); 
    
    /**
     * 更新实体对象
     * @param 实体对象
     */
    public void update(T entity);
    
    /**
     * 保存或更新实体对象
     * @param entity 实体对象
     */
    public void saveOrUpdate(T entity);
    
    /**
     * 删除实体对象
     * @param entity 实体对象
     */
    public void delete(T entity);
    
    /**
     * 查询hql语句，返回唯一结果
     * @param hql 
     */
    public Object findUniqueResult(String hql); 
    
    /**
     * 执行sql语句，更新数据库
     * @param sql
     */
    public void updateBySql(String sql);
    
    /**
     * 通过Criteria对象查询，返回实体对象结果集
     * @param detachedCriteria 离线的Criteria对象
     * @return 实体对象结果集
     */
    public List findByCriteria(DetachedCriteria detachedCriteria);
    
    /**
     * 通过sql语句查询，返回map对象结果集
     * @param sql 
     * @return map对象结果集
     */
    public List<Map<String, Object>> findBySql(String sql);
    
    /**
     * 查询sql语句，返回唯一结果
     * @param sql 
     */
    public Object findUniqueResultBySql(String sql);
    
    /**
     * 通过Criteria对象查询，返回结果集的记录数
     * @param detachedCriteria 离线的Criteria对象
     * @return 结果集的记录数
     */
    public long getCount(DetachedCriteria detachedCriteria);
    
    /**
     * 通过Criteria对象进行分页查询，返回实体对象结果集
     * @param pageNum 第几页
     * @param pageSize 每页大小
     * @param detachedCriteria 离线的Criteria对象
     * @return 实体对象结果集 
     */
    public List<T> findPage(int pageNum, int pageSize, DetachedCriteria detachedCriteria);
    
    /**
     * 通过sql语句，进行分页查询，返回分页对象
     * @param pageNum 第几页
     * @param pageSize 每页大小
     * @param sql
     * @return 分页对象
     */
    public Pagination findPage(int pageNum, int pageSize, String sql); // 查找分页对象列表    
    /**
     * 调用存储过程，返回单结果集
     * @param proceName 存储过程名称
     * @param params 输入参数集合
     * @return map对象结果集
     */
    public List<Map<String, Object>> callProcedure(String proceName, final List<Object> params);
}  
