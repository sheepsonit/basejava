/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size(); ++i) {
                storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); ++i) {
            if (storage[i].uuid.equals(uuid))
                return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        int indexOfStorage = -1;
        for (int i = 0; i < storage.length; ++i) {
            if (storage[i].uuid.equals(uuid)) {
                indexOfStorage = i;
                break;
            }
        }
        if (indexOfStorage > -1) {
            System.arraycopy(storage, indexOfStorage + 1, storage, indexOfStorage, storage.length - indexOfStorage - 1);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] newStorage = new Resume[size()];
        for (int i = 0; i < newStorage.length; ++i) {
            newStorage[i] = storage[i];
        }
        return newStorage;
    }

    int size() {
        for (int i = 0; i < storage.length; ++i) {
            if (storage[i] == null)
                return i;
        }
        return storage.length;
    }
}
