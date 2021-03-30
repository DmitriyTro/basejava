import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size;
    int index;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if (!check(r.getUuid())) {
            storage[size] = r;
            if (size < storage.length) {
                size++;
            } else {
                System.out.println("Хранилище переполнено.");
            }
        } else {
            System.out.println("Резюме с таким uuid " + r.getUuid() + " уже существует.");
        }
    }

    Resume get(String uuid) {
        if (check(uuid)) {
            return storage[index];
        }
        return null;
    }

    void delete(String uuid) {
        if(check(uuid)) {
            if (size - index - 1 > 0) {
                System.arraycopy(storage, index + 1, storage, index, size - index - 1);
                size--;
            }
        } else {
            System.out.println("Резюме с таким uuid не найден.");
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    int size() {
        return size;
    }

    boolean check(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                return true;
            }
        }
        return false;
    }
}