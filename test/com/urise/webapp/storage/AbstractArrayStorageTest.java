package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class AbstractArrayStorageTest {
	private final Storage storage;

	private static final String UUID_1 = "uuid1";
	private static final String UUID_2 = "uuid2";
	private static final String UUID_3 = "uuid3";
	private static final String UUID_4 = "uuid4";

	public static final Resume RESUME_1 = new Resume(UUID_1);
	public static final Resume RESUME_2 = new Resume(UUID_2);
	public static final Resume RESUME_3 = new Resume(UUID_3);
	public static final Resume RESUME_4 = new Resume(UUID_4);

	protected AbstractArrayStorageTest(Storage storage) {
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
		Resume resume = new Resume(UUID_3);
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

	@Test(expected = StorageException.class)
	public void saveOverflow() {
		try {
			for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
				storage.save(new Resume());
			}
		} catch (StorageException e) {
			Assert.fail("Storage overflow.");
		}
		storage.save(RESUME_4);
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
	public void getAll() {
		Resume[] resume = storage.getAll();
		assertEquals(3, resume.length);
		assertEquals(RESUME_1, resume[0]);
		assertEquals(RESUME_2, resume[1]);
		assertEquals(RESUME_3, resume[2]);
	}

	@Test
	public void size() {
		assertEquals(3, storage.size());
	}

	private void assertSize(int size) {
		assertEquals(size, storage.size());
	}
}