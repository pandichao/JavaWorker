package com.cgxt.base.baseJDBC;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
/**
 * �����springJDBC������װ��ʵ�֣���Ҫ�Ż�������add���Լ�����ɾ���ȷ���
 * @author Administrator
 *
 */
public interface JdbcBaseDao<T> extends SqlHandle{

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    void delete(Serializable id);

    void deleteAll();

    T findById(Serializable id);

    List<T> findAll();

    /**
     * ����ɾ��
     * @param ids
     */
    void batchDelete(Serializable[] ids);

    /**
     * �����޸�
     * @param ids
     */
    void batchUpdate(List<T> list);

    /**
     * �������棨������
     * @param ids
     */
    void batchSave(List<T> list);

    Map<String, Object> findOne(String sql, Object... args);

    List<Map<String, Object>> findListMap(String sql, Object... args);

    void execSQL(String sql);

    SqlRowSet rowSet(String sql, Object... args);

    JdbcTemplate getJdbcTemplate();

}