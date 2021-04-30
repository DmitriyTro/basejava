package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

	@Override
	protected void insertElement(Resume resume, int index) {
		int insertIndex = -index - 1;
		System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
		storage[insertIndex] = resume;
	}

	@Override
	protected void fillDeletedElement(int index) {
		int numMove = size - index - 1;
		if (numMove > 0) {
			System.arraycopy(storage, index + 1, storage, index, numMove);
		}
	}

	@Override
	protected Integer getSearchKey(String uuid) {
		Resume searchKey = new Resume(uuid);
		return Arrays.binarySearch(storage, 0, size, searchKey);
	}
}
