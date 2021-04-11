package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage implements Storage {

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            if (size < STORAGE_LIMIT) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("Хранилище переполнено.");
            }
        } else {
            System.out.println("Резюме с таким uuid " + resume.getUuid() + " уже существует.");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if(index >= 0) {
            if (size - index - 1 >= 0) {
                System.arraycopy(storage, index + 1, storage, index, size - index - 1);
                size--;
            }
        } else {
            System.out.println("Резюме с таким uuid не найден.");
        }
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