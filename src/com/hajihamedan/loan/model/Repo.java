package com.hajihamedan.loan.model;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Vector;

public abstract class Repo {
	protected String className;
	protected String tableName;
	protected String idProperty;

	public void persist(Object object) throws Exception {

		DataHandler db = DataHandler.getInstance();

		Class<?> ctrlClass = Class.forName("com.hajihamedan.loan.model."
				+ this.className);
		Method m = ctrlClass.getMethod("getDbProps");
		String[] dbProps = (String[]) m.invoke(object);

		String[] columns = new String[dbProps.length];
		String[] values = new String[dbProps.length];
		int idIndex = -1;

		for (int i = 0; i < dbProps.length; i++) {
			columns[i] = dbProps[i];
			String getterName = "get" + dbProps[i].substring(0, 1).toUpperCase()
					+ dbProps[i].substring(1);
			Method getter = ctrlClass.getMethod(getterName);
			values[i] = String.valueOf(getter.invoke(object));

			if (getter.getReturnType().getName() == "java.lang.String") {
				values[i] = "'" + values[i] + "'";
			}

			if (columns[i] == this.idProperty) {
				idIndex = i;
			}
		}

		if (values[idIndex].equals("0")) {
			ResultSet result = db.select(this.tableName);
			int id = 0;
			while (result.next()) {
				id = result.getInt(this.idProperty);
			}
			values[idIndex] = String.valueOf(id + 1);

			db.insert(this.tableName, columns, values);
		} else {
			db.update(this.tableName, columns, values, idIndex);
		}
	}

	public Vector<Object> loadAll() throws Exception {
		DataHandler db = DataHandler.getInstance();
		ResultSet results = db.select(this.tableName, this.idProperty, "DESC");
		Vector<Object> objects = createInstance(results);
		return objects;
	}

	private Vector<Object> createInstance(ResultSet result) throws Exception {

		Class<?> ctrlClass = Class.forName("com.hajihamedan.loan.model."
				+ this.className);
		Method m = ctrlClass.getMethod("getDbProps");

		String[] dbProps = (String[]) m.invoke(ctrlClass.newInstance());

		Vector<Object> objects = new Vector<>();

		int index = 0;
		while (result.next()) {
			Object object = ctrlClass.newInstance();
			for (int i = 0; i < dbProps.length; i++) {
				Class<?> type = ctrlClass.getDeclaredField(dbProps[i]).getType();

				String setterName = "set" + dbProps[i].substring(0, 1).toUpperCase()
						+ dbProps[i].substring(1);

				Method setter = ctrlClass.getMethod(setterName, type);

				String typeName = type.getName();
				if (typeName == "int") {
					int value = result.getInt(dbProps[i]);
					setter.invoke(object, value);
				} else if (typeName == "long") {
					long value = result.getLong(dbProps[i]);
					setter.invoke(object, value);
				} else if (typeName == "short") {
					short value = result.getShort(dbProps[i]);
					setter.invoke(object, value);
				} else if (typeName == "byte") {
					byte value = result.getByte(dbProps[i]);
					setter.invoke(object, value);
				} else if (typeName == "float") {
					float value = result.getFloat(dbProps[i]);
					setter.invoke(object, value);
				} else {
					String value = result.getString(dbProps[i]);
					setter.invoke(object, value);
				}

			}
			objects.add(object);
		}

		return objects;
	}
}
