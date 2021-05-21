package com.urise.webapp.model;

import java.time.LocalDate;

public class Experience {
	private final String title;
	private final String description;
	private final LocalDate startedDate;
	private final LocalDate endedDate;
	private final String pageOrganization;

	public Experience(String title, String description, LocalDate startedDate, LocalDate endedDate, String pageOrganization) {
		this.title = title;
		this.description = description;
		this.startedDate = startedDate;
		this.endedDate = endedDate;
		this.pageOrganization = pageOrganization;
	}

	@Override
	public String toString() {
		return '\n' + "{" +
				" Название организации = '" + title + '\'' +
				", Описание = '" + description + '\'' +
				", Дата начала = " + startedDate +
				", Дата окончания = " + endedDate +
				", Веб сайт = '" + pageOrganization + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Experience that = (Experience) o;

		if (!title.equals(that.title)) return false;
		if (!description.equals(that.description)) return false;
		if (!startedDate.equals(that.startedDate)) return false;
		if (!endedDate.equals(that.endedDate)) return false;
		return pageOrganization.equals(that.pageOrganization);
	}

	@Override
	public int hashCode() {
		int result = title.hashCode();
		result = 31 * result + description.hashCode();
		result = 31 * result + startedDate.hashCode();
		result = 31 * result + endedDate.hashCode();
		result = 31 * result + pageOrganization.hashCode();
		return result;
	}
}
