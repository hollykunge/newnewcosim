package com.casic.datadriver.controller.cache;

import com.hotent.core.cache.ICache;

/**
 * @Author: hollykunge
 * @Description: 用于缓存积分赚取流水
 * @Date: 创建于 2018/12/7
 * @Modified:
 */
public class ScoreInflowCache implements ICache {
    @Override
    public void add(String key, Object obj, int timeout) {

    }

    @Override
    public void add(String key, Object obj) {

    }

    @Override
    public void delByKey(String key) {

    }

    @Override
    public void clearAll() {

    }

    @Override
    public Object getByKey(String key) {
        return null;
    }

    @Override
    public boolean containKey(String key) {
        return false;
    }
}
