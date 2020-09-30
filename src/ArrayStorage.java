/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Array is overflow");
        } else {
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid))
                return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        int indexOfStorage = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                indexOfStorage = i;
                break;
            }
        }
        if (indexOfStorage > -1) {
            System.arraycopy(storage, indexOfStorage + 1, storage, indexOfStorage, size - indexOfStorage - 1);
            storage[size] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        if (resumes.length >= 0) {
            System.arraycopy(storage, 0, resumes, 0, resumes.length);
        }
        return resumes;
    }

    int size() {
        return size;
    }
}
