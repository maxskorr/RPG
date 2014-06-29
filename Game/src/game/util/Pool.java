package game.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Semyon Danilov on 28.06.2014.
 */
public class Pool<T extends Pool.Poolable<T>> {

    private List<T> pool;
    private Factory<T> factory;
    private int poolSize;

    public Pool(final int size, final Factory<T> factory) {
        this.poolSize = size;
        this.factory = factory;
        pool = new ArrayList<>(size);
    }

    public void free(final T object) {
        if (pool.size() < poolSize) {
            pool.add(object);
            object.clear();
        }
    }

    public T get() {
        T obj = null;
        if (pool.size() > 0) {
            obj = pool.remove(pool.size() - 1);
        } else {
            obj = factory.newInstance();
        }
        return obj;
    }

    public static interface Poolable<T> {

        void clear();

    }

    public static interface Factory<T> {

        T newInstance();

    }

}
