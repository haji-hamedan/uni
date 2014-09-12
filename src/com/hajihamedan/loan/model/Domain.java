package com.hajihamedan.loan.model;


abstract public class Domain {

	protected String repoName = "";
	protected String[] dbProps;

	abstract public int getIdProperty();

	abstract public void setIdProperty(int id);

	// public Object persist() {
	// try {
	// Class<?> ctrlClass = Class.forName("com.hajihamedan.loan.model."
	// + this.repoName);
	// Method m = ctrlClass.getMethod("persist", Domain.class);
	// return m.invoke(ctrlClass.newInstance(), this);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	// $ci = &get_instance();
	// $ci->load->model($this->repo_name);
	// $array = explode("/", $this->repo_name);
	// $classname = end($array);
	// $instance = new $classname();
	// return $instance->persist($this);
	// return new Domain();
	// }

	abstract public String[] getDbProps();
}
