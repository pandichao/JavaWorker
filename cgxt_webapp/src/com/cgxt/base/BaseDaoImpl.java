package com.cgxt.base;

import java.io.Serializable;
import java.lang.reflect.Array;
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
import java.util.Map.Entry;

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
	 * �������ݿ�session����
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
	 * @param topCount ����ǰtopCount����¼ 
	 * @return 
	 */  
	public List<T> findTopList(String hql, int topCount) {  
		// ��ȡ��ǰҳ�Ľ����  
		Query query = this.getSession().createQuery(hql);  
		query.setFirstResult(0);  
		if(topCount<0) topCount=0;  
		query.setMaxResults(topCount);  
		return  query.list();  
	}

	/** 
	 * ��hql���,�õ���ǰ������м�¼ 
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
	 * @param page ��ǰҳ�� 
	 * @param rows ÿҳ��ʾ�ļ�¼���� 
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
	 * ����HQL��䷵��һ��ֵ,��ֲ���ȡ��ҳ�� 
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
	 * ����SQL��䷵��һ��ֵ,��ֲ���ȡ��ҳ�� 
	 */  
	public T getOneBySql(String sql, Object... params) {  
		Query query = getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		this.setParameterToQuery(query, params);  
		return (T) query.uniqueResult();  
	}  
	/** 
	 * ����SQL��䷵��һ��ֵ,��ֲ���ȡ��ҳ�� 
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
	 * ����SQL��䷵��һ������ 
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
	 * @param params ��ǰ֧����ͨ����,����,�����������͵Ĳ��� 
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
	 * @param params ��ǰ֧����ͨ����,��֧�ּ��������� 
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
	 * ����ʵ�����
	 * @param entity ʵ�����
	 */
	@Override
	public void save(T entity){
		Session session = getSession();
		session.save(entity);        
		session.flush();
		session.evict(entity);
	}

	/**
	 * ����ʵ�����
	 * @param ʵ�����
	 */
	@Override
	public void update(T entity){
		Session session = getSession();
		session.update(entity);        
		session.flush();
		session.evict(entity);
	}

	/**
	 * ��������ʵ�����
	 * @param entity ʵ�����
	 */
	@Override
	public void saveOrUpdate(T entity) {        
		Session session = getSession();
		session.saveOrUpdate(entity);        
		session.flush();
		session.evict(entity);    
	}

	/**
	 * ɾ��ʵ�����
	 * @param entity ʵ�����
	 */
	@Override
	public void delete(T entity){
		Session session = getSession();
		session.delete(entity);        
		session.flush();
		session.evict(entity);
	}    

	/**
	 * ��ѯhql��䣬����Ψһ���
	 * @param hql 
	 */
	@Override
	public Object findUniqueResult(String hql){
		Query query = getSession().createQuery(hql);
		return query.uniqueResult();
	}

	/**
	 * ִ��sql��䣬�������ݿ�
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
	 * ͨ��Criteria�����ѯ������ʵ���������
	 * @param detachedCriteria ���ߵ�Criteria����
	 * @return ʵ���������
	 */
	@Override
	public List findByCriteria(DetachedCriteria detachedCriteria){
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		List records = criteria.list();
		return records;
	}

	/**
	 * ͨ��sql����ѯ������map��������
	 * @param sql 
	 * @return map��������
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
	 * ��ѯsql��䣬����Ψһ���
	 * @param sql 
	 */
	@Override
	public Object findUniqueResultBySql(String sql) {    
		return getSession().createSQLQuery(sql.toString()).uniqueResult();
	}

	/**
	 * ͨ��Criteria�����ѯ�����ؽ�����ļ�¼��
	 * @param detachedCriteria ���ߵ�Criteria����
	 * @return ������ļ�¼��
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
	 * ͨ��Criteria������з�ҳ��ѯ������ʵ���������
	 * @param pageNum �ڼ�ҳ
	 * @param pageSize ÿҳ��С
	 * @param detachedCriteria ���ߵ�Criteria����
	 * @return ʵ��������� 
	 */
	@Override
	public List<T> findPage(int pageNum, int pageSize, 
			DetachedCriteria detachedCriteria){
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		List<T> records = criteria.setFirstResult((pageNum-1) * pageSize).setMaxResults(pageSize).list();
		return records;
	}

	/**
	 * ͨ��sql��䣬���з�ҳ��ѯ�����ط�ҳ����
	 * @param pageNum �ڼ�ҳ
	 * @param pageSize ÿҳ��С
	 * @param sql
	 * @return ��ҳ����
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
	 * ���ô洢���̣����ص������
	 * @param proceName �洢��������
	 * @param params �����������
	 * @return map��������
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
					statement.setObject(i+1, params.get(i));//���ò���
				}
				result.addAll(RsHelper.rSToList(statement.executeQuery()));
			}
		});

		return result;
	}


	//========================���Լ���SpringJdbc===================================


	@Override
	public void JdbcUpdate(T entity) {
		String sql = this.makeSql(SQL_UPDATE);
		Object[] args = this.setArgs(entity, SQL_UPDATE);
		int[] argTypes = this.setArgTypes(entity, SQL_UPDATE);
		jdbcTemplate.update(sql, args, argTypes);
	}

	/**
	 * ��װSQl
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
				fields[i].setAccessible(true); // ��������
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
				fields[i].setAccessible(true); // ��������
				String column = fields[i].getName();
				if (column.equals("id")) { // id ��������
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
	 * ���ò���
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
					fields[i].setAccessible(true); // ��������
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
					fields[i].setAccessible(true); // ��������
					tempArr[i] = fields[i].get(entity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Object[] args = new Object[fields.length];
			System.arraycopy(tempArr, 1, args, 0, tempArr.length - 1); // ���鿽��
			args[args.length - 1] = tempArr[0];
			return args;
		} else if (sqlFlag.equals(SQL_DELETE)) {
			Object[] args = new Object[1]; // ������1
			fields[0].setAccessible(true); // ��������
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
	 * ���ò������ͣ�д�Ĳ�ȫ��ֻ��һЩ���õģ�
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
					fields[i].setAccessible(true); // ��������
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
					fields[i].setAccessible(true); // ��������
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
				System.arraycopy(tempArgTypes, 1, argTypes, 0, tempArgTypes.length - 1); // ���鿽��
				argTypes[argTypes.length - 1] = tempArgTypes[0];

			} catch (Exception e) {
				e.printStackTrace();
			}
			return argTypes;

		} else if (sqlFlag.equals(SQL_DELETE)) {
			int[] argTypes = new int[1]; // ������1
			try {
				fields[0].setAccessible(true); // ��������
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
	 *�����ã�ͬʱ������ӱ��ϵ����hibernate��ɣ�һ��һ�� 
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
			//�ӱ�����ֵ�Ժ����save
			nFmary = (V)FieldUtils.setFieldValueByName(pkName, pk.toString(),fmary);
		}else{
			//������ǵĻ�����Ҫ��ȡ������Զ�Ӧ�������ֵ��Ȼ��̬���õ��ӱ���
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
	 * �����������һ�����ݣ��ӱ�����n�����ݵķ���(�����ֹ�����������棬session�ٶȻ������ÿ�ύ20��������һ�Σ���֤session�ٶ�)
	 * @param primary
	 * @param fmary
	 * @param pkId
	 */
	public <V, k> void saveOneToMoneyTable(k primary,List<V> fmarys,String pkName,boolean isId){
		Session session = this.getSession();
		Long pk = (Long)session.save(primary);
		V nFmary = null;
		if(ComUtil.isEmpty(isId)) isId = true;
		//�ӱ�����ֵ�Ժ����save
		int count = 0;
		for (V fmary : fmarys) {
			count ++;
			if(isId){
				nFmary = (V)FieldUtils.setFieldValueByName(pkName, pk.toString(),fmary); 
			}else{
				//��ȡָ��name��Ӧ��value
				Object value = FieldUtils.getFieldValueByName(pkName.trim(),primary);
				if(!ComUtil.isEmpty(value)){
					nFmary = (V)FieldUtils.setFieldValueByName(pkName, pk.toString(),fmary);
				}else{
					throw new NullPointerException("�����Ҳ������ֶΣ�����ù����ֶ������Ƿ���ȷ");
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

	public enum Sqltype{
		MYSQL,
		ORACLE,
		SQLSERVER
	}
	
	/**me
	 * @param entity
	 * @param IdName
	 * @return 
	 */
	@Override
	public <E> int JDBCsave(E entity,String IdName,Sqltype type) {
		System.out.println("entity:"+entity);
		Object IdValue = FieldUtils.getFieldValueByName(IdName,entity);
		if(ComUtil.isEmpty(IdValue)){
			//���Ϊ�գ�˵����ǰû��������������ô����Ĭ���Զ�����
			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			switch (type) {
			case ORACLE:
				buffer.append("nvl(MAX(ab."+IdName+"),0))+1");
				break;
            case SQLSERVER:
            	buffer.append("isnull(MAX(ab."+IdName+"),0)+1");
            	break;
            case MYSQL:
            	buffer.append("ifnull(MAX(ab."+IdName+"),0)+1");
            	break;
			}
			buffer.append(" from "+entity.getClass().getSimpleName()+" as ab;");
			Long Id = jdbcTemplate.queryForObject(buffer.toString(),Long.class);
			entity = FieldUtils.setFieldValueByName(IdName, Id.toString(),entity);
		}
		Map<String, List> valueMap = FieldUtils.getNotnullFieldsAndValueMap(entity);
		List<String> fieldsNames = valueMap.get("fields");
	    List<Object> values = valueMap.get("values");
		String sql = this.makeSql(SQL_INSERT,IdName,entity.getClass(),fieldsNames,null,null);
		String[] toArray = this.listToArray(fieldsNames,String.class);
		int[] argTypes = this.setArgTypes(entity.getClass(),toArray);
		synchronized (jdbcTemplate) {
			return jdbcTemplate.update(sql.toString(), values.toArray(), argTypes);
		}
	}
	
	/**
	 * SpringJdbc���޸ĵķ���
	 * @param entity
	 * @param IdName
	 * @param type
	 */
	public <E> int JdbcUpdate(E entity,Map<String,Object> whereMap){
		//����map�����where������list�����Լ�value������
		List<String> where = new ArrayList<String>();
		List<Object> whereVal = new ArrayList<Object>();
		for (Entry<String,Object> entry : whereMap.entrySet()) {
			where.add(entry.getKey());
			whereVal.add(entry.getValue());
		}
		//��ȡ�����޸ĵĶ��������
		Map<String, List> fieldsAndValueMap = FieldUtils.getNotnullFieldsAndValueMap(entity);
		List<String> setList = (List<String>)fieldsAndValueMap.get("fields");
		List<Object> setValues = fieldsAndValueMap.get("values");
		String sql = this.makeSql(SQL_UPDATE, null,entity.getClass(),null,setList,where);
		//���У����ֶ���ɼ���
		setList.addAll(where);
		//���ж�Ӧ��ֵҲ���һ��
		setValues.addAll(whereVal);
		String[] toArray = this.listToArray(setList,String.class);
		int[] argTypes = this.setArgTypes(entity.getClass(),toArray);
		synchronized (jdbcTemplate) {
			return jdbcTemplate.update(sql, setValues.toArray(), argTypes);
		}
	}
	
	/**
	 * jdbc��ɾ������
	 * @param <E>
	 * @return
	 */
	public <E> int JdbcDelete(E	entity){
		Map<String, List> fieldsAndValueMap = FieldUtils.getNotnullFieldsAndValueMap(entity);
		//��ȡ���е�field����ΪwhereList
		List<String> fieldNames = (List<String>)fieldsAndValueMap.get("fields");
		List<Object> values = fieldsAndValueMap.get("values");
		String sql = this.makeSql(SQL_DELETE, null,entity.getClass(),null, null, fieldNames);
	    //��ȡ���е�����
		System.out.println("��������Ϊ��"+fieldNames.size());
		String[] toArray = this.listToArray(fieldNames,String.class);
		int[] types = this.setArgTypes(entity.getClass(),toArray);
		synchronized (jdbcTemplate) {
		  return jdbcTemplate.update(sql,values,types);	
		}
	}
	/**me
	 * ���¶������װsql�ķ���
	 * @param sqlFlag  ȡֵ SQL_INSERT:��Ҫ����ԭ������sql���ƴ�ӣ�ֻҪǰ�������������һ���������Բ��� 
	 *                    SQL_UPDATE �� ��Ҫ���в����޸ĺ�delete������ʱ�����ʹ�����������bean��֮ǰ���޸�֮ǰ�Ķ���setMap��������ֻ����Ҫ�޸ĵ��ֶ��Լ��޸ĵ�ֵ
	 *                    SQL_DELETE �� �����Ҫɾ����ֱ�Ӵ�֮ǰ��ʵ��Ϳ���   
	 * @param IdName   �������ֶ���
	 * @param bean     ��Ҫ������ʵ��
	 * @param setMap   ִ��
	 * @param whereMap
	 * @return
	 */
	public String makeSql(String sqlFlag,String IdName,Class<?> clazz,List<String> fieldsNames,List<String> setList,List<String> whereList){
		StringBuilder builder = new StringBuilder();
		//��ʼ����ƴ��
		if(sqlFlag.equals(SQL_INSERT)){
			builder.append(" INSERT INTO " + clazz.getSimpleName()+" (");
			//��ȡ�������װ��ȡ������
			for (String field : fieldsNames) {
				builder.append(field+",");
			}
			builder = builder.deleteCharAt(builder.length() - 1);
			builder.append(") VALUES (");
			for (int i = 0; i < fieldsNames.size(); i++) {
				builder.append("?,");
			}
			builder = builder.deleteCharAt(builder.length() - 1);
			builder.append(");");
		}else if(sqlFlag.equals(SQL_UPDATE)){
			builder.append("  UPDATE " + entityClass.getSimpleName() + " SET ");
			for (String setName : setList) {
				builder.append(setName+" = ?,");
			}
			builder = builder.deleteCharAt(builder.length() - 1);
			builder.append(" WHERE ");
			for (int i = 0;i < whereList.size();i ++) {
				if(i < whereList.size() - 1){
					builder.append(whereList.get(i) +" =? and ");
				}else{
					builder.append(whereList.get(i)+"=?");
				}
			}
			builder.append(";");
		}else if(sqlFlag.equals(SQL_DELETE)){
			builder.append(" DELETE FROM " + clazz.getSimpleName() + " WHERE ");
			for (int i = 0;i < whereList.size();i ++) {
				if(i < whereList.size() - 1){
					builder.append(whereList.get(i)+"=? and ");
				}else{
					builder.append(whereList.get(i)+"=?");
				}
			}
			//builder.append(" ;");
		}
		return builder.toString();
	}

	/**
	 * ��д�Ķ�̬��ȡ�����ݿ���Ӧ��Ϊʲô���͵ķ���
	 * @param clazz
	 * @param sqlFlag
	 * @param fieldNames
	 * @return
	 */
	//public int[] setArgTypes(Class clazz, String sqlFlag,String[] fieldNames) {
	public int[] setArgTypes(Class clazz,String[] fieldNames) {
		int[] argTypes = new int[fieldNames.length];
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0;i < fieldNames.length;i ++) {
			for (Field field : fields) {
				if(field.getName().equalsIgnoreCase(fieldNames[i].trim())){
					//�������һ������ȡ����
					String fieldType = field.getType().getSimpleName();  
					if ("String".equals(fieldType)) {
						argTypes[i] = Types.VARCHAR;
					} else if ("Date".equals(fieldType)) { 
						argTypes[i] = Types.DATE;
					} else if ("Integer".equals(fieldType)  
							|| "int".equals(fieldType) || "Short".equals(fieldType)) { 
						argTypes[i] = Types.INTEGER;
					} else if ("Long".equalsIgnoreCase(fieldType)) {
						argTypes[i] = Types.BIGINT;
					} else if ("Double".equalsIgnoreCase(fieldType) || "BigDecimal".equalsIgnoreCase(fieldType)) {
						argTypes[i] = Types.DECIMAL;
					} else if ("Boolean".equalsIgnoreCase(fieldType)) {
						argTypes[i] = Types.BIT;
					}
				}
			}
		}
		return argTypes;
	}

	/**
	 * ����listת��Ϊ����
	 * @param src List<T> ԭList
	 * @return T[] ת���������
	 */
	public <T> T[] listToArray(List<T> src, Class<T> type) {
		if (src == null || src.isEmpty()) {
			return null;
		}
		// ��ʼ����������
		// JAVA�в�����������ʼ���������飺 T[] dest = new T[src.size()];
		T[] dest = (T[]) Array.newInstance(type, src.size());
		for (int i = 0; i < src.size(); i++) {
			dest[i] = src.get(i);
		}
		return (T[]) dest;
	}

}