package com.cgxt.base.baseJDBC;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
/**
 * 混合流springJDBC基本封装和实现，主要优化了批量add，以及批量删除等方法
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
     * 批量删除
     * @param ids
     */
    void batchDelete(Serializable[] ids);

    /**
     * 批量修改
     * @param ids
     */
    void batchUpdate(List<T> list);

    /**
     * 批量保存（新增）
     * @param ids
     */
    void batchSave(List<T> list);

    Map<String, Object> findOne(String sql, Object... args);

    List<Map<String, Object>> findListMap(String sql, Object... args);

    void execSQL(String sql);

    SqlRowSet rowSet(String sql, Object... args);

    JdbcTemplate getJdbcTemplate();

}