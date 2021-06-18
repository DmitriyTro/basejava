package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Experience implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private Link pageOrganization;
	private List<Position> positions;

	public Experience() {
	}

	public Experience(String name, String url, Position... positions) {
		this(new Link(name, url), Arrays.asList(positions));
	}

	public Experience(Link pageOrganization, List<Position> positions) {
		this.pageOrganization = pageOrganization;
		this.positions = positions;
	}

	public Link getPageOrganization() {
		return pageOrganization;
	}

	public List<Position> getPositions() {
		return positions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Experience that = (Experience) o;
		return Objects.equals(pageOrganization, that.pageOrganization) &&
				Objects.equals(positions, that.positions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pageOrganization, positions);
	}

	@Override
	public String toString() {
		return "" +
				"\n" + "Organization: " + pageOrganization + "\n" +
				"Positions: " + positions +
				"";
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Position implements Serializable {
		@XmlJavaTypeAdapter(LocalDateAdapter.class)
		private LocalDate startedDate;
		@XmlJavaTypeAdapter(LocalDateAdapter.class)
		private LocalDate endedDate;
		private String title;
		private String description;

		public Position() {
		}

		public Position(int startYear, Month startMonth, String title, String description) {
			this(of(startYear, startMonth), NOW, title, description);
		}

		public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
			this(of(startYear, startMonth), of(endYear, endMonth), title, description);
		}

		public Position(LocalDate startedDate, LocalDate endedDate, String title, String description) {
			Objects.requireNonNull(startedDate, "startedDate must not be null");
			Objects.requireNonNull(endedDate, "endedDate must not be null");
			Objects.requireNonNull(title, "title must not be null");
			this.startedDate = startedDate;
			this.endedDate = endedDate;
			this.title = title;
			this.description = description == null ? "" : description;
		}

		public LocalDate getStartedDate() {
			return startedDate;
		}

		public LocalDate getEndedDate() {
			return endedDate;
		}

		public String getTitle() {
			return title;
		}

		public String getDescription() {
			return description;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Position position = (Position) o;
			return Objects.equals(startedDate, position.startedDate) &&
					Objects.equals(endedDate, position.endedDate) &&
					Objects.equals(title, position.title) &&
					Objects.equals(description, position.description);
		}

		@Override
		public int hashCode() {
			return Objects.hash(startedDate, endedDate, title, description);
		}

		@Override
		public String toString() {
			return
					"StartedDate: " + startedDate +
							", EndedDate: " + endedDate +
							", Title: '" + title + '\'' +
							", Description: '" + description + '\'' +
							'}';
		}
	}
}
