package com.urise.webapp.model;

public enum ContactType {
	PHONE("Телефон"),
	MAIL("Почта"),
	LINKEDIN("LinkedIn"),
	GITHUB("GitHub"),
	STACKOVERFLOW("StackOverFlow"),
	HOME_PAGE("Домашняя страница");

	private final String title;

	ContactType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
