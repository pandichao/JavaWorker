package com.cglib.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cglib.utils.AbstractEntity;

/** 
 * BaseServiceImpl ����Service��ͨ�ò�����ʵ�� 
 *  
 * @author Monday 
 */  
@Transactional  
public class BaseServiceImpl<T extends AbstractEntity> implements BaseService<T> {
	@Autowired
    private BaseDao<T> basedao;
    
    //����
	@Override
	public void save(T entity) {
		this.beforeInsertEntity(entity);
		basedao.save(entity);
	}
	
	public void beforeInsertEntity(T entity) {
        entity.setCreate_date(new Date());
		if (entity.getCversion() == null) {
			entity.setCversion(0);
		}
		if (entity.getDel_flg() == null) {
			entity.setDel_flg((short)0);
		}
	}

	//�޸�
	@Override
	public void update(T entity) {
		this.beforeUpdateEntity(entity);
		basedao.update(entity);
	}
	public void beforeUpdateEntity(T entity){
        entity.setModify_date(new Date());
		if (entity.getCversion() != null) {
			entity.setCversion(entity.getCversion()+1);
		}
	}

	//�߼�ɾ��
	@Override
	public void delete(T entity) {
		basedao.delete(entity);
	}
	
	//��������ѯ
	public T findByPk(Long sid) {
		return basedao.findByPk(sid);
	}
	
	
	//-------------------------------------------
	@Override
	public void saveOrUpdate(T entity) {
		basedao.saveOrUpdate(entity);
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