package com.cglib.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.jdbc.Work;

import com.cglib.utils.ComUtil;



public class BaseDaoImpl<T extends Serializable> implements BaseDao<T>{          
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
    
    public BaseDaoImpl(){
        super();
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 返回数据库session对象
     * @return
     */
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    private Class<T> entityClass;
	private String entityClassName;
    
    private Session getCurrentSession() {
		if(ComUtil.isEmpty(entityClass)){
			Object genericClz = getClass().getGenericSuperclass();
			if (genericClz instanceof ParameterizedType) {
				entityClass = (Class<T>) ((ParameterizedType) genericClz)
						.getActualTypeArguments()[0];
			}
			if(ComUtil.isEmpty(entityClassName) && !ComUtil.isEmpty(entityClass)){
				entityClassName = entityClass.getSimpleName();
			}
		}
		return this.sessionFactory.getCurrentSession();
	}
    
    public T findByPk(Long sid) {
		return (T) this.getCurrentSession().get(entityClass, sid);
	}
    

    /**
     * 保存实体对象
     * @param entity 实体对象
     */
    @Override
    public void save(T entity){
        Session session = getSession();
        session.save(entity);        
        session.flush();
        session.evict(entity);
    }
    
    /**
     * 更新实体对象
     * @param 实体对象
     */
    @Override
    public void update(T entity){
        Session session = getSession();
        session.update(entity);        
        session.flush();
        session.evict(entity);
    }
    
    /**
     * 保存或更新实体对象
     * @param entity 实体对象
     */
    @Override
    public void saveOrUpdate(T entity) {        
        Session session = getSession();
        session.saveOrUpdate(entity);        
        session.flush();
        session.evict(entity);    
    }

    /**
     * 删除实体对象
     * @param entity 实体对象
     */
    @Override
    public void delete(T entity){
        Session session = getSession();
        session.delete(entity);        
        session.flush();
        session.evict(entity);
    }    
    
    /**
     * 查询hql语句，返回唯一结果
     * @param hql 
     */
    @Override
    public Object findUniqueResult(String hql){
        Query query = getSession().createQuery(hql);
        return query.uniqueResult();
    }
    
    /**
     * 执行sql语句，更新数据库
     * @param sql
     */
    @Override
    public void updateBySql(final String sql){
        getSession().doWork(new Work() {   
            @Override
            public void execute(Connection connection) throws SQLException {
                connection.prepareStatement(sql).executeUpdate();
            }
        });
    }    
    
    /**
     * 通过Criteria对象查询，返回实体对象结果集
     * @param detachedCriteria 离线的Criteria对象
     * @return 实体对象结果集
     */
    @Override
    public List findByCriteria(DetachedCriteria detachedCriteria){
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        List records = criteria.list();
        return records;
    }
    
    /**
     * 通过sql语句查询，返回map对象结果集
     * @param sql 
     * @return map对象结果集
     */
    @Override
    public List<Map<String, Object>> findBySql(final String sql){
        final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        getSession().doWork(new Work() {   
            @Override
            public void execute(Connection connection) throws SQLException {
                ResultSet rs = connection.prepareStatement(sql).executeQuery();
                result.addAll(RsHelper.rSToList(rs));
            }
        });
        return result;
    }
    
    /**
     * 查询sql语句，返回唯一结果
     * @param sql 
     */
    @Override
    public Object findUniqueResultBySql(String sql) {    
        return getSession().createSQLQuery(sql.toString()).uniqueResult();
    }
    
    /**
     * 通过Criteria对象查询，返回结果集的记录数
     * @param detachedCriteria 离线的Criteria对象
     * @return 结果集的记录数
     */
    @Override
    public long getCount(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        Object object = criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        Long totalRow = Long.valueOf(String.valueOf(object));
        return totalRow;
    }
    
    /**
     * 通过Criteria对象进行分页查询，返回实体对象结果集
     * @param pageNum 第几页
     * @param pageSize 每页大小
     * @param detachedCriteria 离线的Criteria对象
     * @return 实体对象结果集 
     */
    @Override
    public List<T> findPage(int pageNum, int pageSize, 
            DetachedCriteria detachedCriteria){
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        List<T> records = criteria.setFirstResult((pageNum-1) * pageSize).setMaxResults(pageSize).list();
        return records;
    }
    
    /**
     * 通过sql语句，进行分页查询，返回分页对象
     * @param pageNum 第几页
     * @param pageSize 每页大小
     * @param sql
     * @return 分页对象
     */
    @Override
    public Pagination findPage(final int pageNum, final int pageSize,final String sql){
        final Pagination page = new Pagination();
        getSession().doWork(new Work() {   
            @Override
            public void execute(Connection connection) throws SQLException {
                String countSql = MessageFormat.format("select count(*) from ({0}) page", sql);
                ResultSet rs = connection.prepareStatement(countSql).executeQuery();
                page.setTotal(Long.valueOf(RsHelper.getUniqueResult(rs).toString()));
                
                long firstResult = (pageNum - 1)*pageSize;
                String selectSql = MessageFormat.format("select * from ({0}) page limit {1},{2}", sql, firstResult, firstResult+pageSize);
                page.setRows(RsHelper.rSToList(connection.prepareStatement(selectSql).executeQuery()));
            }
        });
        
        return page;
    }
    
    /**
     * 调用存储过程，返回单结果集
     * @param proceName 存储过程名称
     * @param params 输入参数集合
     * @return map对象结果集
     */
    public List<Map<String, Object>> callProcedure(String proceName, final List<Object> params){
        final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        final StringBuffer sql = new StringBuffer();
        sql.append("{call " + proceName + "(");
        for(int i=0; params!=null && i<params.size(); i++){
            sql.append("?");
            if(i+1!=params.size())
                sql.append(",");
        }
        sql.append(")}");
        getSession().doWork(new Work() {   
            @Override
            public void execute(Connection connection) throws SQLException {
                CallableStatement statement = connection.prepareCall(
                        sql.toString());
                for(int i=0; i<params.size(); i++){
                    statement.setObject(i+1, params.get(i));//设置参数
                }
                result.addAll(RsHelper.rSToList(statement.executeQuery()));
            }
        });
        
        return result;
    }
    
}