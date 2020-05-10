package main.resources.utilities.collections;

import java.util.Map;
import java.util.WeakHashMap;

public class ReversibleWeakHashMap<K, V> extends WeakHashMap<K,V> {

    private Map<V, K> reverseMap = new WeakHashMap<>();

    @Override
    public V put(K key, V value){
        reverseMap.put(value, key);
        return super.put(key, value);
    }

    public K getKey(V value){
        return reverseMap.get(value);
    }


}
