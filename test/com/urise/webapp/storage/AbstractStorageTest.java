package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.ResumeTestData;
import com.urise.webapp.util.Config;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
	protected static final File STORAGE_DIR = Config.get().getStorageDir().getAbsoluteFile();

	protected Storage storage;

	private static final String UUID_1 = "uuid1";
	private static final String UUID_2 = "uuid2";
	private static final String UUID_3 = "uuid3";
	private static final String UUID_4 = "uuid4";

	private static final Resume RESUME_1 = ResumeTestData.getResume(UUID_1, "fullName1");
	private static final Resume RESUME_2 = ResumeTestData.getResume(UUID_2, "fullName2");
	private static final Resume RESUME_3 = ResumeTestData.getResume(UUID_3, "fullName3");
	private static final Resume RESUME_4 = ResumeTestData.getResume(UUID_4, "fullName4");

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
		Resume newResume = new Resume(UUID_1, "New Name");
		storage.update(newResume);
		assertEquals(newResume, storage.get(UUID_1));
	}

	@Test(expected = NotExistStorageException.class)
	public void updateNotExist() {
		storage.update(RESUME_4);
	}

	@Test
	public void save() {
		storage.save(RESUME_4);
		assertSize(4);
		assertGet(RESUME_4);
	}

	@Test(expected = ExistStorageException.class)
	public void saveExist() {
		storage.save(RESUME_1);
	}

	@Test()
	public void get() {
		assertGet(RESUME_1);
		assertGet(RESUME_2);
		assertGet(RESUME_3);
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
		List<Resume> list = storage.getAllSorted();
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

	private void assertGet(Resume r) {
		assertEquals(r, storage.get(r.getUuid()));
	}
}