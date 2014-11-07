package com.hajihamedan.loan.helper;

import java.util.Enumeration;
import java.util.Vector;

public class Validation {

	Vector<Item> items = new Vector<Item>();
	private boolean isValid = true;
	private String message = "";

	public void setItem(String name, String value, String[] rules) {
		Item newItem = new Item(name, value, rules);
		items.addElement(newItem);
	}

	private void run() {
		Enumeration<Item> allItems = items.elements();

		while (allItems.hasMoreElements()) {
			Item thisItem = allItems.nextElement();

			String[] thisItemRules = thisItem.getRules();
			for (int i = 0; i < thisItemRules.length; i++) {
				String rule = thisItemRules[i];

				if (rule == "required") {
					this.checkRequired(thisItem);
				} else if (rule == "long") {
					this.checkLong(thisItem);
				} else if (rule == "byte") {
					this.checkByte(thisItem);
				} else if (rule == "short") {
					this.checkShort(thisItem);
				} else if (rule == "date") {
					this.checkDate(thisItem);
				} else if (rule == "email") {
					this.checkEmail(thisItem);
				}
			}

		}
	}

	public boolean isValid() {
		this.run();
		return this.isValid;
	}

	public String getMessage() {
		return this.message;
	}

	private class Item {
		private String value;
		private String name;
		private String[] rules;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String[] getRules() {
			return rules;
		}

		public void setRules(String[] rules) {
			this.rules = rules;
		}

		public Item(String itemName, String itemValue, String[] itemRules) {
			this.setName(itemName);
			this.setValue(itemValue);
			this.setRules(itemRules);
		}
	}

	private void checkRequired(Item item) {
		if (item.getValue() == null || item.getValue() == "") {
			this.isValid = false;
			this.message += "<p>پر کردن فیلد " + item.getName() + " اجباری است.</p>";
		}
	}

	private void checkByte(Item item) {
		if (item.getValue() != null && item.getValue() != "") {
			try {
				Byte.parseByte(item.getValue());
			} catch (Exception e) {
				this.isValid = false;
				this.message += "<p>لطفاً مقدار " + item.getName() + " را به صورت عددی و بدون نقطه وارد نمایید.</p>";
			}
		}
	}

	private void checkLong(Item item) {
		if (item.getValue() != null && item.getValue() != "") {
			try {
				Long.parseLong(item.getValue());
			} catch (Exception e) {
				this.isValid = false;
				this.message += "<p>لطفاً مقدار " + item.getName() + " را به صورت عددی و بدون نقطه وارد نمایید.</p>";
			}
		}
	}

	private void checkShort(Item item) {
		if (item.getValue() != null && item.getValue() != "") {
			try {
				Short.parseShort(item.getValue());
			} catch (Exception e) {
				this.isValid = false;
				this.message += "<p>لطفاً مقدار " + item.getName() + " را به صورت عددی و بدون نقطه وارد نمایید.</p>";
			}
		}
	}

	private void checkDate(Item item) {
		if (item.getValue() != null && item.getValue() != "") {
			String pattern = "(13|14)[0-9]{2}[- /.](0[1-9]|1[012])[- /.]((0)[1-9]|[12][0-9]|3[01])";
			if (!item.getValue().matches(pattern)) {
				this.isValid = false;
				this.message += "<p>مقدار " + item.getName() + " را به صورت تاریخ معتبر با فرمت روز-ماه-سال وارد نمایید.</p>";
			}
		}
	}

	private void checkEmail(Item item) {
		String pattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (!item.getValue().matches(pattern)) {
			this.isValid = false;
			this.message += "<p>ایمیل وارد شده معتبر نیست. لطفا ایمیل معتبر وارد نمایید.</p>";
		}
	}
}
