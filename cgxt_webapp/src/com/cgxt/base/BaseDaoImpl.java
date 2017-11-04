package com.cgxt.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cgxt.utils.ComUtil;
import com.cgxt.utils.classUtils.FieldUtils;

/*@Repository("baseDao")*/
public class BaseDaoImpl<T extends Serializable> implements BaseDao<T>{          
	private static final String SQL_UPDATE = "update";
	private static final String SQL_INSERT = "insert";
	private static final String SQL_DELETE = "delete";

	private Class<T> entityClass;
	private String entityClassName;

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public BaseDaoImpl(){
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
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
		if(ComUtil.isEmpty(entityClass)){
			ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			entityClass = (Class<T>) type.getActualTypeArguments()[0];
		}
		if(ComUtil.isEmpty(entityClassName) && !ComUtil.isEmpty(entityClass)){
			entityClassName = entityClass.getSimpleName();
		}
		return sessionFactory.getCurrentSession();
	}

	public T findByPk(Long sid) {
		return (T) this.getSession().get(entityClass, sid);
	}

	public List<T> findAll() {
		List<T> list = this.getSession()
				.createQuery("from " + entityClassName).setCacheable(true)
				.list();
		return list;
	}

	public List<T> findList(String hql) {  
		Query q = this.getSession().createQuery(hql);  
		return q.list();  
	}  

	public List<T> findList(String hql, Map<String, Object> params) {  
		Query q = this.getSession().createQuery(hql);  
		this.setParameterToQuery(q, params);  
		return q.list();  
	}

	/** 
	 *  
	 * @param hql 
	 * @param topCount 返回前topCount条记录 
	 * @return 
	 */  
	public List<T> findTopList(String hql, int topCount) {  
		// 获取当前页的结果集  
		Query query = this.getSession().createQuery(hql);  
		query.setFirstResult(0);  
		if(topCount<0) topCount=0;  
		query.setMaxResults(topCount);  
		return  query.list();  
	}

	/** 
	 * 用hql语句,得到当前表的所有记录 
	 * @param tableName 
	 * @return 
	 */  
	public List<T> findAll(String tableName){  
		String hqlString="select * from "+tableName;  
		return this.findList(hqlString);  
	} 

	/** 
	 *  
	 * @param hql 
	 * @param params 
	 * @param page 当前页码 
	 * @param rows 每页显示的记录数量 
	 * @return 
	 */  
	public List<T> findList(String hql, Map<String, Object> params, int page, int rows) {  
		Query q = this.getSession().createQuery(hql);  
		this.setParameterToQuery(q, params);  
		if(page<1) page=1;  
		if(rows<0) rows=0;  
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();  
	}  

	public List<T> findList(String hql, int page, int rows) {  
		return this.findList(hql, null, page,rows);  
	} 

	public Long getCountByHql(String hql) {  
		Query q = this.getSession().createQuery(hql);  
		return (Long) q.uniqueResult();  
	}  


	public Long getCountByHql(String hql, Map<String, Object> params) {  
		Query q = this.getSession().createQuery(hql);  
		this.setParameterToQuery(q, params);  
		return (Long) q.uniqueResult();  
	}         
	/** 
	 * 根据HQL语句返回一个值,如分布获取总页数 
	 */  
	 public Object getOneByHql(String hql, Object... params) {  
		 Query query = getSession().createQuery(hql);  
		 this.setParameterToQuery(query, params);  
		 return query.uniqueResult();  
	 }

	 public T getOneBySql(String sql) {  
		 return this.getOneBySql(sql,new HashMap<String, Object>());  
	 } 

	 /** 
	  * 根据SQL语句返回一个值,如分布获取总页数 
	  */  
	 public T getOneBySql(String sql, Object... params) {  
		 Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		 this.setParameterToQuery(query, params);  
		 return (T) query.uniqueResult();  
	 }  
	 /** 
	  * 根据SQL语句返回一个值,如分布获取总页数 
	  */  
	 public T getOneBySql(String sql,Map<String,Object> params) {  
		 Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		 this.setParameterToQuery(query, params);  
		 return (T) query.uniqueResult();  
	 } 

	 public List<Map<String, Object>> findListBySql(String sql) {  
		 return this.findListBySql(sql, new HashMap<String, Object>());  
	 }  

	 public List<Map<String, Object>> findListBySql(String sql,Map<String,Object> params) {  
		 SQLQuery query = this.getSession().createSQLQuery(sql);  
		 query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		 this.setParameterToQuery(query, params);  
		 return query.list();  
	 }  

	 /** 
	  * 根据SQL语句返回一个集合 
	  */  
	 public List<Map<String, Object>> findListBySql(String sql, Object... params) {  
		 Query query = this.getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		 this.setParameterToQuery(query, params);  
		 return query.list();  
	 } 

	 @Override
	 public void updateAll(List<T> entitys) {
		 for (T entity : entitys) {
			 this.update(entity);
		 }
	 }

	 @Override
	 public void deleteAll(List<T> entitys) {
		 for (T entity : entitys) {
			 this.delete(entity);
		 }
	 }

	 @Override
	 public void insertAll(List<T> entitys) {
		 if(!ComUtil.isEmpty(entitys)){
			 for (T entity : entitys) {
				 this.save(entity);
			 }
		 }
	 }


	 /** 
	  * @param q 
	  * @param params 当前支持普通对象,数组,集合三种类型的参数 
	  */  
	 protected void setParameterToQuery(Query q,Map<String, Object> params){  
		 if (params != null && !params.isEmpty()) {  
			 for (String key : params.keySet()) {  
				 if(params.get(key) instanceof Object[]){  
					 Object[] objs=(Object[]) params.get(key);  
					 q.setParameterList(key, objs);  
				 }else if(params.get(key) instanceof Collection<?>){  
					 Collection<?> collection=(Collection<?>) params.get(key);  
					 q.setParameterList(key, collection);  
				 }else{  
					 q.setParameter(key, params.get(key));  
				 }  
			 }  
		 }  
	 }

	 /** 
	  * @param q 
	  * @param params 当前支持普通对象,不支持集合与数组 
	  */  
	 protected void setParameterToQuery(Query q,Object... params){  
		 if (params != null && params.length>0) {  
			 for (int i=0;i<params.length;i++) {  
				 Object object=params[i];              
				 q.setParameter(i,object);  
			 }             
		 }  
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


	 //========================尝试集成SpringJdbc===================================


	 @Override
	 public void JdbcUpdate(T entity) {
		 String sql = this.makeSql(SQL_UPDATE);
		 Object[] args = this.setArgs(entity, SQL_UPDATE);
		 int[] argTypes = this.setArgTypes(entity, SQL_UPDATE);
		 jdbcTemplate.update(sql, args, argTypes);
	 }

	 /**
	  * 组装SQl
	  * 
	  * @param entityClass
	  * @param sqlFlag
	  * @return
	  */
	 public String makeSql(String sqlFlag) {
		 StringBuffer sql = new StringBuffer();
		 Field[] fields = entityClass.getDeclaredFields();
		 if (sqlFlag.equals(SQL_INSERT)) {
			 sql.append(" INSERT INTO " + entityClass.getSimpleName());
			 sql.append("(");
			 for (int i = 0; fields != null && i < fields.length; i++) {
				 fields[i].setAccessible(true); // 暴力反射
				 String column = fields[i].getName();
				 sql.append(column).append(",");
			 }
			 sql = sql.deleteCharAt(sql.length() - 1);
			 sql.append(") VALUES (");
			 for (int i = 0; fields != null && i < fields.length; i++) {
				 sql.append("?,");
			 }
			 sql = sql.deleteCharAt(sql.length() - 1);
			 sql.append(")");
		 } else if (sqlFlag.equals(SQL_UPDATE)) {
			 sql.append(" UPDATE " + entityClass.getSimpleName() + " SET ");
			 for (int i = 0; fields != null && i < fields.length; i++) {
				 fields[i].setAccessible(true); // 暴力反射
				 String column = fields[i].getName();
				 if (column.equals("id")) { // id 代表主键
					 continue;
				 }
				 sql.append(column).append("=").append("?,");
			 }
			 sql = sql.deleteCharAt(sql.length() - 1);
			 sql.append(" WHERE id=?");
		 } else if (sqlFlag.equals(SQL_DELETE)) {
			 sql.append(" DELETE FROM " + entityClass.getSimpleName() + " WHERE id=?");
		 }
		 System.out.println("SQL=" + sql);
		 return sql.toString();
	 }

	 /**
	  * 设置参数
	  * 
	  * @param entity
	  * @param sqlFlag
	  * @param entityClass
	  * @return
	  */
	 public Object[] setArgs(T entity, String sqlFlag) {
		 Field[] fields = entityClass.getDeclaredFields();
		 if (sqlFlag.equals(SQL_INSERT)) {
			 Object[] args = new Object[fields.length];
			 for (int i = 0; args != null && i < args.length; i++) {
				 try {
					 fields[i].setAccessible(true); // 暴力反射
					 args[i] = fields[i].get(entity);
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
			 }
			 return args;
		 } else if (sqlFlag.equals(SQL_UPDATE)) {
			 Object[] tempArr = new Object[fields.length];
			 for (int i = 0; tempArr != null && i < tempArr.length; i++) {
				 try {
					 fields[i].setAccessible(true); // 暴力反射
					 tempArr[i] = fields[i].get(entity);
				 } catch (Exception e) {
					 e.printStackTrace();
				 }
			 }
			 Object[] args = new Object[fields.length];
			 System.arraycopy(tempArr, 1, args, 0, tempArr.length - 1); // 数组拷贝
			 args[args.length - 1] = tempArr[0];
			 return args;
		 } else if (sqlFlag.equals(SQL_DELETE)) {
			 Object[] args = new Object[1]; // 长度是1
			 fields[0].setAccessible(true); // 暴力反射
			 try {
				 args[0] = fields[0].get(entity);
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 return args;
		 }
		 return null;
	 }

	 /**
	  * 设置参数类型（写的不全，只是一些常用的）
	  * 
	  * @param entity
	  * @param sqlFlag
	  * @param entityClass
	  * @return
	  */
	 public int[] setArgTypes(T entity, String sqlFlag) {
		 Field[] fields = entityClass.getDeclaredFields();
		 if (sqlFlag.equals(SQL_INSERT)) {
			 int[] argTypes = new int[fields.length];
			 try {
				 for (int i = 0; argTypes != null && i < argTypes.length; i++) {
					 fields[i].setAccessible(true); // 暴力反射
					 if (fields[i].get(entity).getClass().getName().equals("java.lang.String")) {
						 argTypes[i] = Types.VARCHAR;
					 } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Double")) {
						 argTypes[i] = Types.DECIMAL;
					 } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Integer")) {
						 argTypes[i] = Types.INTEGER;
					 } else if (fields[i].get(entity).getClass().getName().equals("java.util.Date")) {
						 argTypes[i] = Types.DATE;
					 }
				 }
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 return argTypes;
		 } else if (sqlFlag.equals(SQL_UPDATE)) {
			 int[] tempArgTypes = new int[fields.length];
			 int[] argTypes = new int[fields.length];
			 try {
				 for (int i = 0; tempArgTypes != null && i < tempArgTypes.length; i++) {
					 fields[i].setAccessible(true); // 暴力反射
					 if (fields[i].get(entity).getClass().getName().equals("java.lang.String")) {
						 tempArgTypes[i] = Types.VARCHAR;
					 } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Double")) {
						 tempArgTypes[i] = Types.DECIMAL;
					 } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Integer")) {
						 tempArgTypes[i] = Types.INTEGER;
					 } else if (fields[i].get(entity).getClass().getName().equals("java.util.Date")) {
						 tempArgTypes[i] = Types.DATE;
					 }
				 }
				 System.arraycopy(tempArgTypes, 1, argTypes, 0, tempArgTypes.length - 1); // 数组拷贝
				 argTypes[argTypes.length - 1] = tempArgTypes[0];

			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 return argTypes;

		 } else if (sqlFlag.equals(SQL_DELETE)) {
			 int[] argTypes = new int[1]; // 长度是1
			 try {
				 fields[0].setAccessible(true); // 暴力反射
				 if (fields[0].get(entity).getClass().getName().equals("java.lang.String")) {
					 argTypes[0] = Types.VARCHAR;
				 } else if (fields[0].get(entity).getClass().getName().equals("java.lang.Integer")) {
					 argTypes[0] = Types.INTEGER;
				 }

			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 return argTypes;
		 }
		 return null;
	 }
	 //===================================================================

	 /**
	  *测试用，同时添加主子表关系（用hibernate完成，一对一） 
	  * @param <k>
	  * @param primary
	  * @param fmary
	  * @param pkId
	  */
	 public <V, k> void saveOneToOneTable(k primary,V fmary,String pkName,boolean isId){
		 Session session = this.getSession();
		 Long pk = (Long)session.save(primary);
		 V nFmary = null;
		 if(ComUtil.isEmpty(isId)) isId = true;
		 if(isId){
			 //子表设置值以后进行save
			 nFmary = (V)FieldUtils.setFieldValueByName(pkName, pk.toString(),fmary);
		 }else{
			 //如果不是的话，需要获取这个属性对应的主表的值，然后动态设置到子表中
			 Object value = FieldUtils.getFieldValueByName(pkName.trim(), primary);
			 if(!ComUtil.isEmpty(value)){
				 nFmary = (V)FieldUtils.setFieldValueByName(pkName,value.toString(),fmary);
			 }
		 }
		 session.save(nFmary);
		 session.flush();
		 session.close();
	 }

	 /**
	  * 针对主表新增一条数据，子表新增n条数据的方法(这里防止大数据量保存，session速度会变慢，每提交20个就清理一次，保证session速度)
	  * @param primary
	  * @param fmary
	  * @param pkId
	  */
	 public <V, k> void saveOneToMoneyTable(k primary,List<V> fmarys,String pkName,boolean isId){
		 Session session = this.getSession();
		 Long pk = (Long)session.save(primary);
		 V nFmary = null;
		 if(ComUtil.isEmpty(isId)) isId = true;
		 //子表设置值以后进行save
		 int count = 0;
		 for (V fmary : fmarys) {
			 count ++;
			 if(isId){
				 nFmary = (V)FieldUtils.setFieldValueByName(pkName, pk.toString(),fmary); 
			 }else{
				 //获取指定name对应的value
				 Object value = FieldUtils.getFieldValueByName(pkName.trim(),primary);
				 if(!ComUtil.isEmpty(value)){
					 nFmary = (V)FieldUtils.setFieldValueByName(pkName, pk.toString(),fmary);
				 }else{
					 throw new NullPointerException("主表找不到该字段，请检查该关联字段名称是否正确");
				 }
			 }
			  
			 if(count % 20 !=0 || count == 0){
				 session.save(nFmary);
			 }else if(count % 20 == 0 && count != 0){
				 session.flush();
				 session.save(nFmary);
			 }
		 }
		 
		 session.flush();
		 session.close();
	 }

}