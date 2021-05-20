package com.urise.webapp.model;

import java.time.LocalDate;

public class Experience {
	private final String pageOrganization;
	private final LocalDate startedDate;
	private final LocalDate endedDate;
	private final String title;
	private final String description;

	public Experience(String pageOrganization, LocalDate startedDate, LocalDate endedDate, String title, String description) {
		this.pageOrganization = pageOrganization;
		this.startedDate = startedDate;
		this.endedDate = endedDate;
		this.title = title;
		this.description = description;
	}

	@Override
	public String toString() {
		return "Experience{" +
				"pageOrganization='" + pageOrganization + '\'' +
				", startedDate=" + startedDate +
				", endedDate=" + endedDate +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Experience that = (Experience) o;

		if (!pageOrganization.equals(that.pageOrganization)) return false;
		if (!startedDate.equals(that.startedDate)) return false;
		if (!endedDate.equals(that.endedDate)) return false;
		if (!title.equals(that.title)) return false;
		return description.equals(that.description);
	}

	@Override
	public int hashCode() {
		int result = pageOrganization.hashCode();
		result = 31 * result + startedDate.hashCode();
		result = 31 * result + endedDate.hashCode();
		result = 31 * result + title.hashCode();
		result = 31 * result + description.hashCode();
		return result;
	}
}
