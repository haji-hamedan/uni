package com.hajihamedan.loan.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Repo {
	protected String className;
	protected String tableName;
	protected String idProperty;

	public void persist(Object object) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, SQLException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Class<?> ctrlClass = Class.forName("com.hajihamedan.loan.model."
				+ this.className);
		Method m = ctrlClass.getMethod("getDbProps");
		String[] dbProps = (String[]) m.invoke(object);

		String[] columns = new String[dbProps.length];
		String[] values = new String[dbProps.length];
		int idIndex = 0;

		for (int i = 0; i < dbProps.length; i++) {
			columns[i] = dbProps[i];
			String getterName = "get" + dbProps[i].substring(0, 1).toUpperCase()
					+ dbProps[i].substring(1);
			Method getter = ctrlClass.getMethod(getterName);
			values[i] = String.valueOf(getter.invoke(object));
			if (columns[i] == this.idProperty) {
				idIndex = i;
			}
		}

		DataHandler db = DataHandler.getInstance();
		ResultSet result = db.select(this.tableName);
		int id = 0;
		while (result.next()) {
			id = result.getInt(this.idProperty);
		}

		values[idIndex] = String.valueOf(id + 1);

		db.insert(this.tableName, columns, values);
	}
}
