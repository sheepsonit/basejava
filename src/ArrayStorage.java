/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; ++i) {
            if (storage[i] != null)
                storage[i] = null;
            else
                break;
        }
    }

    void save(Resume r) {
        int i = 0;
        while (i < storage.length) {
            if (storage[i] != null)
                ++i;
            else {
                storage[i] = r;
                break;
            }
        }

    }

    Resume get(String uuid) {
        for (Resume resume : storage) {
            if (resume != null) {
                if (resume.uuid.equals(uuid))
                    return resume;
            } else
                return null;
        }
        return null;
    }

    void delete(String uuid) {
        //обходим весь массив
        int indexOfStorage = -1;
        for (int i = 0; i < storage.length; ++i) {
            //если такой элемент нашелся, фиксируем индекс
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
        int i = 0;
        int j = 0;
        Resume[] newStorage = new Resume[storage.length];
        while (i < storage.length) {
            if (storage[i] != null) {
                newStorage[j] = storage[i];
                ++j;
            } else
                break;
            ++i;
        }
        Resume[] resultStorage = new Resume[j];
        System.arraycopy(newStorage, 0, resultStorage, 0, j);
        return resultStorage;
    }

    int size() {
        return storage.length;
    }
}
