package com.cgxt.base;

import java.io.Serializable;
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
     * �������ݿ�session����
     * @return
     */
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
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
    
}