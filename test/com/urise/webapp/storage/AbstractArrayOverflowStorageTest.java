package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayOverflowStorageTest extends AbstractStorageTest {
	protected AbstractArrayOverflowStorageTest(Storage storage) {
		super(storage);
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
}