package com.hajihamedan.loan.model;

public class User extends Domain {

	private int userId;
	private String username;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String mobile;

	public String[] dbProps = { "userId", "username", "email", "password", "firstName", "lastName", "mobile" };

	public User() {
		this.repoName = "UserRepo";
	}

	@Override
	public int getIdProperty() {
		return this.getUserId();
	}

	@Override
	public void setIdProperty(int id) {
		this.setUserId(id);
	}

	@Override
	public String[] getDbProps() {
		return this.dbProps;
	}

	/**
	 * 
	 * @param id
	 * @return User
	 * @throws Exception
	 */
	public static User loadById(int id) throws Exception {
		UserRepo userRepo = new UserRepo();
		User user = (User) userRepo.loadById(id);
		return user;
	}

	public static User loadByUsername(String username) throws Exception {
		UserRepo userRepo = new UserRepo();
		User user = userRepo.loadByUsername(username);
		return user;
	}

	public static User loadByEmail(String email) throws Exception {
		UserRepo userRepo = new UserRepo();
		User user = userRepo.loadByEmail(email);
		return user;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
