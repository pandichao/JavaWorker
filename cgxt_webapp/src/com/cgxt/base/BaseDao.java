package com.cgxt.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

import com.cgxt.base.BaseDaoImpl.Sqltype;



/**
 * 统一数据访问接口
 */
public interface BaseDao<T extends Serializable> {
    
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
/** =====================================================================================    */
    //从这里开始都是SpringJDBC
    void JdbcUpdate(T entity);
    
    
    /**
     *测试用，同时添加主子表关系（用hibernate完成，一对一，适用于子表关联插入的是主表的主键） 
     * @param <k>
     * @param primary  主表对象
     * @param fmarys   子表对象
     * @param pkName   子表和主表进行关联的字段（关联的是主表的主键id）
     * @param isId     关联的是否为主键
     */
    public <V, k> void saveOneToOneTable(k primary,V fmary,String pkName,boolean isId);
    
    /**
     * 适用于主子表一起插入，hibernate实现（主表一条数据，子表多条数据,适用于子表关联插入的是主表的主键）
     * @param primary  主表对象
     * @param fmarys   子表对象集合 
     * @param pkName   子表和主表进行关联的字段
     * @param isId     关联的是否为主键
     */
    public <V, k> void saveOneToMoneyTable(k primary,List<V> fmarys,String pkName,boolean isId);
    
    /**
     * SpringJDBC的insert方法
     * @param entity
     * @param IdName
     */
    public <E> int JDBCsave(E entity,String IdName,Sqltype type);
    
    /**
	 * SpringJdbc的修改的方法
	 * @param entity
	 * @param IdName
	 * @param type
	 */
	public <E> int JdbcUpdate(E entity,Map<String,Object> whereMap);
	
	/**
	 * jdbc的删除方法
	 * @param <E>
	 * @return
	 */
	public <E> int JdbcDelete(E	entity);
	
	public <E> void batchJdbcSave(List<E> entitys,String IdName,Sqltype type);
	
	public <E> void batchInsertJDBC3(List<E> entitys,String IdName,Sqltype type,boolean defultId) throws DataAccessException;
	
}