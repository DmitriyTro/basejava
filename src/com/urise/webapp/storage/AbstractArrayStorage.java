package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
	protected static final int STORAGE_LIMIT = 10000;

	protected Resume[] storage = new Resume[STORAGE_LIMIT];
	protected int size;

	public Resume get(String uuid) {
		int index = getIndex(uuid);
		if (index < 0) {
			return null;
		}
		return storage[index];
	}

	protected abstract int getIndex(String uuid);

	public int size() {
		return size;
	}
}

