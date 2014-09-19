package com.hajihamedan.loan.model;

import java.lang.reflect.Method;

abstract public class Domain {

	protected String repoName = "";
	protected String[] dbProps;

	abstract public int getIdProperty();

	abstract public void setIdProperty(int id);

	public Domain persist() throws Exception {

		Class<?> ctrlClass = Class.forName("com.hajihamedan.loan.model."
				+ this.repoName);

		Method m = ctrlClass.getMethod("persist", Domain.class);
		return (Domain) m.invoke(ctrlClass.newInstance(), this);
	}

	abstract public String[] getDbProps();

}
