package com.cgxt.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

/** 
 * BaseServiceImpl 定义Service的通用操作的实现 
 *  
 * @author Monday 
 */  
@Transactional  
public class BaseServiceImpl<T extends Serializable> implements BaseService<T> {
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
      
}  