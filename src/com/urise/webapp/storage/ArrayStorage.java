package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage implements Storage {

    protected void insertElement(Resume resume, int index) {
        storage[size] = resume;
    }

    protected void fillDeletedElement(int index) {
        storage[index] = storage[size - 1];
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}