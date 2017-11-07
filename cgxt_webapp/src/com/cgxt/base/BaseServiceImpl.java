package com.cgxt.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cgxt.base.BaseDaoImpl.Sqltype;

/** 
 * BaseServiceImpl 定义Service的通用操作的实现 
 *  
 * @author Monday 
 */  
@Transactional  
public class BaseServiceImpl<T extends Serializable> implements BaseService<T> {
    @Autowired
	private BaseDao<T> basedao;
    
	@Override
	public void save(T entity) {
		basedao.save(entity);
	}

	@Override
	public void update(T entity) {
		basedao.update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		basedao.saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		basedao.delete(entity);
	}

	@Override
	public Object findUniqueResult(String hql) {
		return basedao.findUniqueResult(hql);
	}

	@Override
	public void updateBySql(String sql) {
		basedao.updateBySql(sql);
	}

	@Override
	public List findByCriteria(DetachedCriteria detachedCriteria) {
		return basedao.findByCriteria(detachedCriteria);
	}

	@Override
	public List<Map<String, Object>> findBySql(String sql) {
		return basedao.findBySql(sql);
	}

	@Override
	public Object findUniqueResultBySql(String sql) {
		return basedao.findUniqueResultBySql(sql);
	}

	@Override
	public long getCount(DetachedCriteria detachedCriteria) {
		return basedao.getCount(detachedCriteria);
	}

	@Override
	public List<T> findPage(int pageNum, int pageSize,
			DetachedCriteria detachedCriteria) {
		return basedao.findPage(pageNum, pageSize, detachedCriteria);
	}

	@Override
	public Pagination findPage(int pageNum, int pageSize, String sql) {
		return basedao.findPage(pageNum, pageSize, sql);
	}

	@Override
	public List<Map<String, Object>> callProcedure(String proceName,
			List<Object> params) {
		return basedao.callProcedure(proceName, params);
	}

	@Override
	public void JdbcUpdate(T entity) {
		basedao.JdbcUpdate(entity);
	}

	@Override
	public T findByPk(Long sid) {
		return basedao.findByPk(sid);
	}

	@Override
	public List<T> findAll() {
		return basedao.findAll();
	}

	@Override
	public List<T> findList(String hql) {
		return basedao.findList(hql);
	}

	@Override
	public List<T> findList(String hql, Map<String, Object> params) {
		return basedao.findList(hql, params);
	}

	@Override
	public List<T> findTopList(String hql, int topCount) {
		return basedao.findTopList(hql,topCount);
	}

	@Override
	public List<T> findAll(String tableName) {
		return basedao.findAll();
	}

	@Override
	public List<T> findList(String hql, Map<String, Object> params, int page,
			int rows) {
		return basedao.findList(hql, params, page, rows);
	}

	@Override
	public List<T> findList(String hql, int page, int rows) {
		return basedao.findList(hql, page, rows);
	}

	@Override
	public Long getCountByHql(String hql, Map<String, Object> params) {
		return basedao.getCountByHql(hql, params);
	}

	@Override
	public Object getOneByHql(String hql, Object... params) {
		return basedao.getOneByHql(hql, params);
	}

	@Override
	public T getOneBySql(String sql) {
		return basedao.getOneBySql(sql);
	}

	@Override
	public T getOneBySql(String sql, Object... params) {
		return basedao.getOneBySql(sql, params);
	}

	@Override
	public T getOneBySql(String sql, Map<String, Object> params) {
		return basedao.getOneBySql(sql, params);
	}

	@Override
	public List<Map<String, Object>> findListBySql(String sql) {
		return basedao.findListBySql(sql);
	}

	@Override
	public List<Map<String, Object>> findListBySql(String sql,
			Map<String, Object> params) {
		return basedao.findListBySql(sql,params);
	}

	@Override
	public List<Map<String, Object>> findListBySql(String sql, Object... params) {
		return basedao.findListBySql(sql, params);
	}

	@Override
	public void updateAll(List<T> entitys) {
		basedao.updateAll(entitys);
	}

	@Override
	public void deleteAll(List<T> entitys) {
		basedao.deleteAll(entitys);
	}

	@Override
	public void insertAll(List<T> entitys) {
       basedao.insertAll(entitys);		
	}

	@Override
	public <V, K> void saveOneToOneTable(K primary,V fmary,String pkName,boolean isId) {
		basedao.saveOneToOneTable(primary, fmary, pkName,isId);
	}

	@Override
	public <V, k> void saveOneToMoneyTable(k primary,List<V> fmarys,String pkName,boolean isId) {
		basedao.saveOneToMoneyTable(primary, fmarys, pkName,isId);
	}

	@Override
	public <E> int JDBCsave(E entity,String IdName,Sqltype type) {
		return basedao.JDBCsave(entity, IdName,type);
	}

	@Override
	public <E> void JdbcUpdate(E entity,Map<String, Object> whereMap) {
		basedao.JdbcUpdate(entity,whereMap);
	}

	@Override
	public <E> void JdbcDelete(E entity) {
		basedao.JdbcDelete(entity);
	}

	@Override
	public <E> void batchJdbcSave(List<E> entitys, String IdName, Sqltype type) {
		basedao.batchJdbcSave(entitys, IdName, type);
	}  
      
}  