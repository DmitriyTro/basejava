package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization extends AbstractSection {
	@Serial
	private static final long serialVersionUID = 1L;

	private List<Experience> experiences;

	public Organization() {
	}

	public Organization(Experience... experiences) {
		this(Arrays.asList(experiences));
	}

	public Organization(List<Experience> experiences) {
		Objects.requireNonNull(experiences, "organizations must not be null");
		this.experiences = experiences;
	}

	public List<Experience> getOrganizations() {
		return experiences;
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

	@Override
	public String toString() {
		return experiences.toString();
	}
}
