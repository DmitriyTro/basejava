package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable <Resume> {
	private final String uuid;
	private final String fullName;

	private final Map <ContactType, String> contacts = new EnumMap <>(ContactType.class);
	private final Map <SectionType, AbstractSection> sections = new EnumMap <>(SectionType.class);

	public Resume(String fullName) {
		this(UUID.randomUUID().toString(), fullName);
	}

	public Resume(String uuid, String fullName) {
		Objects.requireNonNull(uuid, "uuid must not be null");
		Objects.requireNonNull(fullName, "fullName must not be null");
		this.uuid = uuid;
		this.fullName = fullName;
	}

	public String getUuid() {
		return uuid;
	}

	public String getFullName() {
		return fullName;
	}

	public Map <SectionType, AbstractSection> getSections() {
		return sections;
	}

	public Map <ContactType, String> getContacts() {
		return contacts;
	}

	public void addContacts(ContactType type, String contact) {
		contacts.put(type, contact);
	}

	public void addSection(SectionType type, AbstractSection section) {
		sections.put(type, section);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Resume resume = (Resume) o;

		if (!uuid.equals(resume.uuid)) return false;
		return fullName.equals(resume.fullName);
	}

	@Override
	public int hashCode() {
		int result = uuid.hashCode();
		result = 31 * result + fullName.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return uuid + " " + fullName;
	}

	@Override
	public int compareTo(Resume o) {
		int compare = fullName.compareTo(o.fullName);
		return compare != 0 ? compare : uuid.compareTo(o.uuid);
	}
}

