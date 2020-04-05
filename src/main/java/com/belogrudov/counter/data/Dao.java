package com.belogrudov.counter.data;

import java.util.List;

public interface Dao<T> {

    void create(T name);

    String read(T name);

    void update(T name);

    void delete(T name);

    String sum();

    List<T> readKeys();
}
