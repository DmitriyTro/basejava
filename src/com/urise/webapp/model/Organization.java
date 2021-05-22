package com.urise.webapp.model;

import java.util.List;

public class Organization extends AbstractSection {
	private final List<Experience> experiences;

	public Organization(List <Experience> experiences) {
		this.experiences = experiences;
	}

	@Override
	public String toString() {
		return experiences.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Organization that = (Organization) o;

		return experiences.equals(that.experiences);
	}

	@Override
	public int hashCode() {
		return experiences.hashCode();
	}
}
