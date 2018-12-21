package com.casic.datadriver.dao.score;

import com.casic.datadriver.model.coin.DdScore;
import com.hotent.core.db.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 目前add getAll delById update getAll使用genericDao里的方法
 *
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
     * 更新积分账户
     *
     * @param ddScore the query filter
     */
    public void updateScore(DdScore ddScore) {
        this.update(ddScore);
    }

    /**
     * 删光一类积分所有的
     *
     * @param sourceType 一级类型
     */
    public void delByType(String sourceType) {
        this.update("delByType", sourceType);
    }

    /**
     * 通过id查一项
     */
    @Override
    public DdScore getById(Long id) {
        return this.getBySqlKey("getById", id).get(0);
    }

    /**
     * 根据用户id查他积分
     *
     * @param userId userId
     */
    public List<DdScore> getPersonal(long userId) {
        return this.getBySqlKey("getPersonal", userId);
    }

    /**
     * 获取一类积分所有的
     *
     * @param sourceType 一级类型
     */
    public List<DdScore> getType(String sourceType) {
        return this.getBySqlKey("getType", sourceType);
    }

    /**
     * 根据uid和type查找唯一一个ddScore
     * @param userId uid
     * @param sourceType 一级类型
     * @return 唯一对象
     */
    public DdScore getByUidAndType(long userId, String sourceType) {
        List<DdScore> ddScores = this.getBySqlKey("getPersonal", userId);
        for(DdScore ddScore : ddScores) {
            if(ddScore.getScoreType().equals(sourceType)) {
                return ddScore;
            }
        }
        return null;
    }

}