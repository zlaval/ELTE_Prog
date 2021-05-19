package com.zlrx.java.advanced.classes.cls20210421;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class BiMap<K extends Comparable<K>, V extends Comparable<V>> implements Map<K, V> {

    private final TreeMap<K, V> keyValue;
    private final TreeMap<V, K> valueKey;

    public static <K extends Comparable<K>, V extends Comparable<V>> BiMap<K, V> create() {
        return new BiMap<K, V>();
    }

    public static <K extends Comparable<K>, V extends Comparable<V>> BiMap<K, V> create(Comparator<K> keyComparator, Comparator<V> valueComparator) {
        return new BiMap<K, V>(keyComparator, valueComparator);
    }

    private BiMap() {
        keyValue = new TreeMap<>();
        valueKey = new TreeMap<>();
    }

    private BiMap(Comparator<K> keyComparator, Comparator<V> valueComparator) {
        this.keyValue = new TreeMap<K, V>(keyComparator);
        this.valueKey = new TreeMap<V, K>(valueComparator);
    }

    public boolean putAll(List<? extends K> keys, List<? extends V> values) {
        if (keys.size() == values.size()) {
            for (int i = 0; i < keys.size(); i++) {
                K key = keys.get(i);
                V value = values.get(i);
                put(key, value);
            }
            return true;
        }
        return false;
    }


    @Override
    public int size() {
        return keyValue.size();
    }

    @Override
    public boolean isEmpty() {
        return keyValue.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keyValue.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return valueKey.containsKey(value);
    }

    @Override
    public V get(Object key) {
        return keyValue.get(key);
    }

    public K get(V value) {
        return valueKey.get(value);
    }

    @Override
    public V put(K key, V value) {
        valueKey.put(value, key);
        return keyValue.put(key, value);
    }

    @Override
    public V remove(Object key) {
        V value = keyValue.remove(key);
        valueKey.remove(value);
        return value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        keyValue.putAll(m);
        m.forEach((k, v) -> valueKey.put(v, k));
    }

    @Override
    public void clear() {
        keyValue.clear();
        valueKey.clear();
    }

    @Override
    public Set<K> keySet() {
        return keyValue.keySet();
    }

    @Override
    public Collection<V> values() {
        return valueKey.keySet();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return keyValue.entrySet();
    }
}
