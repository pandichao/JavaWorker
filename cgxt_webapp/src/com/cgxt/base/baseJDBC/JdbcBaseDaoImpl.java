package com.cgxt.base.baseJDBC;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import com.cgxt.utils.ComUtil;
import com.cgxt.utils.classUtils.FieldUtils;

/**
 * 混合流springJDBC基本封装和实现，主要优化了批量add，以及批量删除等方法
 * @author Administrator
 *
 */
public class JdbcBaseDaoImpl<T> implements JdbcBaseDao<T>{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public JdbcBaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
		System.out.println("Dao实现类是：" + entityClass.getName());
	}

	@Override
	public void save(T entity) {
		String sql = this.makeSql(SQL_INSERT);
		Object[] args = this.setArgs(entity, SQL_INSERT);
		int[] argTypes = this.setArgTypes(entity, SQL_INSERT);
		jdbcTemplate.update(sql.toString(), args, argTypes);
	}

	@Override
	public void update(T entity) {
		String sql = this.makeSql(SQL_UPDATE);
		Object[] args = this.setArgs(entity, SQL_UPDATE);
		int[] argTypes = this.setArgTypes(entity, SQL_UPDATE);
		jdbcTemplate.update(sql, args, argTypes);
	}

	@Override
	public void delete(T entity) {
		String sql = this.makeSql(SQL_DELETE);
		Object[] args = this.setArgs(entity, SQL_DELETE);
		int[] argTypes = this.setArgTypes(entity, SQL_DELETE);
		jdbcTemplate.update(sql, args, argTypes);
	}

	@Override
	public void deleteAll() {
		String sql = " TRUNCATE TABLE " + entityClass.getSimpleName();
		jdbcTemplate.execute(sql);
	}

	@Override
	public void delete(Serializable id) {
		String sql = " DELETE FROM " + entityClass.getSimpleName() + " WHERE id=?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public void batchSave(List<T> lt) {
		Assert.notEmpty(lt);
		StringBuilder sb = new StringBuilder();
		sb.append("insert into ").append(entityClass.getSimpleName().toLowerCase()).append("( ");
		List<Object[]> params = new ArrayList<Object[]>();
		String val = "";

		for (T t : lt) {
			int index = 0;
			Field[] fields = t.getClass().getDeclaredFields();
			if (fields != null && fields.length > 0) {
				Object[] objVal = new Object[fields.length];

				for (Field field : fields) {
					try {
						field.setAccessible(true);
						Object obj = field.get(t);
						if (params.size() == 0) {
							sb.append(field.getName()).append(" ,");
							val += ", ? ";
						}
						objVal[index++] = obj;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				params.add(objVal);
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		val = val.substring(1);
		sb.append(") value (").append(val).append(")");
		String sql = sb.toString();
		jdbcTemplate.batchUpdate(sql, params);
	}

	@Override
	public void batchUpdate(List<T> lt) {
		Assert.notEmpty(lt);
		StringBuilder sb = new StringBuilder();
		sb.append("update ").append(entityClass.getSimpleName().toLowerCase()).append(" set ");
		List<Object[]> params = new ArrayList<Object[]>();
		Object primaryKey = "id";
		for (T t : lt) {
			int index = 0;
			Field[] fields = entityClass.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				Object id = null;
				Object[] objVal = new Object[fields.length];
				for (Field field : fields) {
					try {
						field.setAccessible(true);
						Object obj = field.get(t);
						if (field.getName().equalsIgnoreCase("id")) {
							primaryKey = obj;
							id = obj;
						} else {
							if (params.size() == 0) {
								sb.append(field.getName()).append(" = ? ,");
							}
							objVal[index++] = obj;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					objVal[index] = id;
				}
				params.add(objVal);
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where ").append(primaryKey).append(" = ? ");
		String sql = sb.toString();
		jdbcTemplate.batchUpdate(sql, params);
	}

	@Override
	public T findById(Serializable id) {
		String sql = "SELECT * FROM " + entityClass.getSimpleName() + " WHERE id=?";
		RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
		return jdbcTemplate.query(sql, rowMapper, id).get(0);
	}

	@Override
	public List<T> findAll() {
		String sql = "SELECT * FROM " + entityClass.getSimpleName();
		RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public Map<String, Object> findOne(String sql, Object... args) {
		return jdbcTemplate.queryForMap(sql, args);
	}

	@Override
	public List<Map<String, Object>> findListMap(String sql, Object... args) {
		return jdbcTemplate.queryForList(sql, args);
	}

	@Override
	public void execSQL(String sql) {
		jdbcTemplate.execute(sql);
	}

	@Override
	public SqlRowSet rowSet(String sql, Object... args) {
		return this.jdbcTemplate.queryForRowSet(sql, args);
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	/**me
	 * @param entity
	 * @param IdName
	 */
	public <E> void save(E entity,String IdName) {
		Map<String, List> valueMap = FieldUtils.getNotnullFieldsAndValueMap(entity);
		List<String> fieldsNames = valueMap.get("fields");
		Object[] args = valueMap.get("values").toArray();
		String sql = this.makeSql(SQL_INSERT,IdName,entity.getClass(),fieldsNames,null,null);
		
		//Object[] args = this.setArgs(entity, SQL_INSERT);
		//int[] argTypes = this.setArgTypes(entity, SQL_INSERT);
		int[] argTypes = this.setArgTypes(entity.getClass(), (String[])fieldsNames.toArray());
		jdbcTemplate.update(sql.toString(), args, argTypes);
	}
	
	/**me
	 * 重新定义的组装sql的方法
	 * @param sqlFlag  取值 SQL_INSERT:需要进行原生新增sql语句拼接，只要前三个参数，最后一个参数可以不传 
	 *                     SQL_UPDATE ： 需要进行部分修改和delete操作的时候可以使用这个方法，bean传之前的修改之前的对象，setMap参数可以只传需要修改的字段以及修改的值
	 *                     SQL_DELETE ： 如果需要删除，直接传之前的实体就可以   
	 * @param IdName   主键的字段名
	 * @param bean     需要操作的实体
	 * @param setMap   执行
	 * @param whereMap
	 * @return
	 */
	public String makeSql(String sqlFlag,String IdName,Class<?> clazz,List<String> fieldsNames,List<String> setList,List<String> whereList){
		StringBuilder builder = new StringBuilder();
			//开始进行拼接
			if(sqlFlag.equals(SQL_INSERT)){
				builder.append(" INSERT INTO " + entityClass.getSimpleName()+" (");
				builder.append(IdName+",");
				//获取工具类封装截取的数据
				for (String field : fieldsNames) {
					builder.append(field+",");
				}
				builder = builder.deleteCharAt(builder.length() - 1);
				String select_id = "(select MAX(ab."+IdName+") from "+clazz.getSimpleName()+" as ab)";
				builder.append(") VALUES (");
				//自动设置当前表最大id+1
				builder.append(select_id);
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
				builder.append(" DELETE FROM " + entityClass.getSimpleName() + " WHERE ");
				for (int i = 0;i < whereList.size();i ++) {
					if(i < whereList.size() - 1){
						builder.append(whereList.get(i)+"=? and ");
					}else{
						builder.append(whereList.get(i)+"=?");
					}
				}
				builder.append(";");
			}
		return builder.toString();
	}
	/**
	 * 组装SQl
	 * 
	 * @param entityClass
	 * @param sqlFlag
	 * @return
	 */
	public String makeSql(String sqlFlag) {
		StringBuilder sql = new StringBuilder();
		Field[] fields = entityClass.getDeclaredFields();
		if (sqlFlag.equals(SQL_INSERT)) {
			sql.append(" INSERT INTO " + entityClass.getSimpleName());
			sql.append("(");
			for (int i = 0; fields != null && i < fields.length; i++) {
				fields[i].setAccessible(true); // 暴力反射
				String column = fields[i].getName();
				sql.append(column).append(",");
			}
			//删除最后一位的,
			sql = sql.deleteCharAt(sql.length() - 1);
			sql.append(") VALUES (");
			for (int i = 0; fields != null && i < fields.length; i++) {
				sql.append("?,");
			}
			//删除最后一位的,
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
	 * 重写的动态获取在数据库中应该为什么类型的方法
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
					//如果名字一样，获取类型
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

	@Override
	public void batchDelete(Serializable[] ids) {
		String idStr="";
		for (int i = 0; i < ids.length; i++) {
			idStr+=",'"+ids[i]+"'";
		}
		String sql = " DELETE FROM " + entityClass.getSimpleName() + " WHERE id in (?)";
		jdbcTemplate.update(sql, idStr.charAt(1));
	}

}
