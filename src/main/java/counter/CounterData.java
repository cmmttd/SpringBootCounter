package counter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CounterData {

    private static ConcurrentHashMap<String, BigInteger> counters = new ConcurrentHashMap<>();

    public static BigInteger create(String name) {
        return counters.putIfAbsent(name, BigInteger.ZERO);
    }

    public static void increment(String name) {
        counters.computeIfPresent(name, (k, v) -> v.add(BigInteger.ONE));
    }

    public static String getForName(String name) {
        return counters.get(name).toString();
    }

    public static void remove(String name) {
        counters.remove(name);
    }

    public static List<String> getNames() {
        return new ArrayList<>(counters.keySet());
    }

    public static String getSum() {
        return counters.values()
                .stream()
                .reduce(BigInteger::add)
                .orElse(BigInteger.ZERO).toString();
    }

    public static boolean notContainsKey(String name){
        return !counters.containsKey(name);
    }

}
