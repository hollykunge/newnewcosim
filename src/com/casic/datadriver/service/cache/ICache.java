package com.casic.datadriver.service.cache;

import java.util.List;

/**
 * 缓存操作接口
 *
 * @author: workhub
 * @Date: 2018/12/20
 */

public interface ICache {
    /**
     * 添加缓存
     *
     * @param key     key
     * @param obj     value
     * @param timeout 最长等待时间
     */
    void add(String key, Object obj, int timeout);

    /**
     * 添加缓存
     *
     * @param key key
     * @param obj value
     */
    void add(String key, Object obj);


    /**
     * 删除缓存
     *
     * @param key key
     */
    void delByKey(String key);

    /**
     * 清除所有的缓存
     */
    void clearAll();

    /**
     * 读取缓存
     *
     * @param key key
     * @return value
     */
    Object getByKey(String key);

    /**
     * 通过关键字读取缓存
     *
     * @param keySection key中包含的关键字
     * @return value
     */
    Object getByKeySection(String keySection);

    /**
     * 是否包含
     *
     * @param key key
     * @return 是否成功
     */
    boolean containKey(String key);
}