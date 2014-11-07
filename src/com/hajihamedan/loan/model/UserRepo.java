package com.hajihamedan.loan.model;

import java.util.Vector;

public class UserRepo extends Repo {

	public UserRepo() {
		this.className = "User";
		this.idProperty = "userId";
		this.tableName = "users";
	}

	public User loadByUsername(String username) throws Exception {
		String condition = "username = '" + username + "'";
		Vector<Domain> user = this.loadByCondition(condition);

		if (user.size() == 1) {
			return (User) user.elementAt(0);
		} else {
			return null;
		}
	}

	public User loadByEmail(String email) throws Exception {
		String condition = "email = '" + email + "'";
		Vector<Domain> user = this.loadByCondition(condition);

		if (user.size() == 1) {
			return (User) user.elementAt(0);
		} else {
			return null;
		}
	}

}
