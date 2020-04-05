package com.belogrudov.counter.data;

//          Создать счетчик с уникальным именем;
//        * Изменить значение счетчика с указанным именем;
//        * Получить значения счетчика с указанным именем;
//        * Удалить счетчик с указанным именем;
//        * Получить суммарное значение всех счетчиков;
//        * Получить уникальные имена счетчиков в виде списка.

public interface Dao<T> {
    void create(String name);
    String read(String name);
    String update(String name);
    void delete(String name);
}
