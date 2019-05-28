package org.xtuml.bp.mc.masl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SingleEntryStringMap implements Map<String, String> {

    private String key;
    private String value;

    public SingleEntryStringMap(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.key.equals(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.value.equals(value);
    }

    @Override
    public String get(Object key) {
        if (containsKey(key))
            return value;
        else
            return null;
    }

    @Override
    public String put(String key, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> keySet() {
        return Stream.of(key).collect(Collectors.toSet());
    }

    @Override
    public Collection<String> values() {
        return Stream.of(value).collect(Collectors.toList());
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return Stream.of(new Entry<String, String>() {
            @Override
            public String getKey() {
                return key;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }
        }).collect(Collectors.toSet());
    }

}
