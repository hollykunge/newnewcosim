package com.casic.datadriver.dao.score;

import com.casic.datadriver.model.coin.DdScore;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/19
 */

@Repository
public class DdScoreDao extends BaseDao<DdScore> {

    @Override
    public Class getEntityClass() {
        return DdScore.class;
    }

    /**
     * 增加使用add
     * 删除使用delById
     * 更新使用update
     * 查询使用getById, getAll
     */


    /**
     * 删光一类积分所有的
     *
     * @param sourceType 一级类型
     */
    public void delByType(String sourceType) {
        this.update("delByType", sourceType);
    }

    /**
     * 获取一类积分所有的
     *
     * @param sourceType 一级类型
     */
    public List<DdScore> getByType(String sourceType) {
        return this.getBySqlKey("getByType", sourceType);
    }

    /**
     * 根据用户id查他积分
     */
    public List<DdScore> getPersonal(long userId) {
        return this.getBySqlKey("getPersonal", userId);
    }

    /**
     * 根据uid和type查找唯一一个ddScore
     *
     * @param userId     uid
     * @param sourceType 一级类型
     * @return 唯一对象
     */
    public DdScore getByUidAndType(long userId, String sourceType) {
        List<DdScore> ddScores = this.getBySqlKey("getPersonal", userId);
        for (DdScore ddScore : ddScores) {
            if (ddScore.getScoreType().equals(sourceType)) {
                return ddScore;
            }
        }
        return null;
    }
}