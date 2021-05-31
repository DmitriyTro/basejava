package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.util.DateUtil.of;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
	protected Storage storage;

	private static final String UUID_1 = "uuid1";
	private static final String UUID_2 = "uuid2";
	private static final String UUID_3 = "uuid3";
	private static final String UUID_4 = "uuid4";

	private static final Resume RESUME_1;
	private static final Resume RESUME_2;
	private static final Resume RESUME_3;
	private static final Resume RESUME_4;

	static {
		RESUME_1 = new Resume(UUID_1, "name1");
		RESUME_2 = new Resume(UUID_2, "name2");
		RESUME_3 = new Resume(UUID_3, "name3");
		RESUME_4 = new Resume(UUID_4, "name4");

		RESUME_1.addContacts(ContactType.PHONE, "1234");
		RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("objective_text"));
		RESUME_1.addSection(SectionType.PERSONAL, new TextSection("personal_text"));
		RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("achievement_text1", "achievement_text2",
				"achievement_text3"));
		RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection("qualification_text1", "qualification_text2",
				"qualification_text3"));
		RESUME_1.addSection(SectionType.EXPERIENCE,
				new Organization(
						new Experience("Organization_exp", "http://Organization_exp.com",
								new Experience.Position(of(2021, Month.JUNE), of(2021, Month.JULY),
										"title_text_exp",
										"description_text_exp")))
		);
		RESUME_1.addSection(SectionType.EDUCATION,
				new Organization(
						new Experience("Organization_edu", "http://Organization_edu.com",
								new Experience.Position(of(2021, Month.AUGUST), of(2021, Month.SEPTEMBER),
										"title_text_edu",
										"description_text_edu")))
		);
	}

	protected AbstractStorageTest(Storage storage) {
		this.storage = storage;
	}

	@Before
	public void setUp() {
		storage.clear();
		storage.save(RESUME_1);
		storage.save(RESUME_2);
		storage.save(RESUME_3);
	}

	@Test
	public void clear() {
		storage.clear();
		assertSize(0);
	}

	@Test
	public void update() {
		Resume resume = new Resume(UUID_3, "Name3");
		storage.update(resume);
		assertEquals(resume, storage.get(UUID_3));
	}

	@Test(expected = NotExistStorageException.class)
	public void updateNotExist() {
		storage.update(RESUME_4);
	}

	@Test
	public void save() {
		storage.save(RESUME_4);
		assertSize(4);
		assertEquals(RESUME_4, storage.get(UUID_4));
	}

	@Test(expected = ExistStorageException.class)
	public void saveExist() {
		storage.save(RESUME_1);
	}

	@Test()
	public void get() {
		assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
	}

	@Test(expected = NotExistStorageException.class)
	public void getNotExist() {
		storage.get("dummy");
	}

	@Test(expected = NotExistStorageException.class)
	public void delete() {
		storage.delete(UUID_1);
		assertSize(2);
		storage.get(UUID_1);
	}

	@Test(expected = NotExistStorageException.class)
	public void deleteNotExist() {
		storage.delete("dummy");
	}

	@Test
	public void getAllSorted() {
		List <Resume> list = storage.getAllSorted();
		assertEquals(3, list.size());
		assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
	}

	@Test
	public void size() {
		assertEquals(3, storage.size());
	}

	private void assertSize(int size) {
		assertEquals(size, storage.size());
	}
}