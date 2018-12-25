package com.casic.datadriver.service.cache;

import com.fr.bi.cube.engine.third.edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存实现
 *
 * @author workhub
 * @Date: 2018/12/21
 */
@Component
public class CacheImpl implements Cache {

    private Map<String, Object> cache = (Map<String, Object>) Collections.synchronizedMap(new HashMap<String, Object>());

    @Override
    public synchronized void add(String key, Object obj, int timeout) {
        cache.put(key, obj);
    }

    @Override
    public synchronized void add(String key, Object obj) {
        cache.put(key, obj);
    }

    @Override
    public synchronized void delByKey(String key) {
        cache.remove(key);
    }

    @Override
    public void clearAll() {
        cache.clear();
    }

    @Override
    public synchronized Object getByKey(String key) {
        return cache.get(key);
    }

    @Override
    public synchronized Object getByKeySection(String keyPrefix, String keySection) {
        List<Object> objects = new ArrayList<>();
        for (String key : cache.keySet()) {
            if (key.contains(keyPrefix) && key.contains(keySection)) {
                objects.add(cache.get(key));
            }
        }
        return objects;
    }

    @Override
    public boolean containKey(String key) {
        return cache.containsKey(key);
    }
}
