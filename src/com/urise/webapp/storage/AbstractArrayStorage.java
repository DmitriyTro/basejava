package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
	protected static final int STORAGE_LIMIT = 10000;

	protected Resume[] storage = new Resume[STORAGE_LIMIT];
	protected int size;

	public void clear() {
		Arrays.fill(storage, 0, size, null);
		size = 0;
	}

	public void update(Resume resume) {
		int index = getIndex(resume.getUuid());
		if (index >= 0) {
			storage[index] = resume;
		} else {
			System.out.println("Резюме с таким uuid: " + resume.getUuid() + " не найдено.");
		}
	}

	public void save(Resume resume) {
		int index = getIndex(resume.getUuid());
		if (index < 0) {
			if (size < STORAGE_LIMIT) {
				insertElement(resume, index);
				size++;
			} else {
				System.out.println("Хранилище переполнено.");
			}
		} else {
			System.out.println("Резюме с таким uuid " + resume.getUuid() + " уже существует.");
		}
	}

	public Resume get(String uuid) {
		int index = getIndex(uuid);
		if (index < 0) {
			return null;
		}
		return storage[index];
	}

	public void delete(String uuid) {
		int index = getIndex(uuid);
		if(index >= 0) {
			if (size - index - 1 >= 0) {
				fillDeletedElement(index);
				size--;
			}
		} else {
			System.out.println("Резюме с таким uuid не найден.");
		}
	}

	protected abstract void insertElement(Resume resume, int index);

	protected abstract void fillDeletedElement(int index);

	public Resume[] getAll() {
		return Arrays.copyOfRange(storage, 0, size);
	}

	protected abstract int getIndex(String uuid);

	public int size() {
		return size;
	}
}

