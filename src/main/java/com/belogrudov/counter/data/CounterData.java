package com.belogrudov.counter.data;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CounterData implements Dao<String> {

    private static ConcurrentHashMap<String, BigInteger> data = new ConcurrentHashMap<>();

    @Override
    public void create(String name) {
        data.putIfAbsent(name, BigInteger.ZERO);
    }

    @Override
    public void update(String name) {
        data.computeIfPresent(name, (k, v) -> v.add(BigInteger.ONE));
    }

    @Override
    public String read(String name) {
        return data.get(name).toString();
    }

    @Override
    public void delete(String name) {
        data.remove(name);
    }

    @Override
    public List<String> readKeys() {
        return new ArrayList<>(data.keySet());
    }

    @Override
    public String sum() {
        return data.values()
                .stream()
                .reduce(BigInteger::add)
                .orElse(BigInteger.ZERO).toString();
    }

    public boolean notContainsKey(String name) {
        return !data.containsKey(name);
    }
}
