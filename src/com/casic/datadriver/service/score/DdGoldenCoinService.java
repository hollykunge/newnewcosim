package com.casic.datadriver.service.score;

import com.casic.datadriver.dao.score.DdGoldenCoinDao;
import com.casic.datadriver.model.coin.DdGoldenCoin;
import com.casic.datadriver.service.cache.Cache;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import com.hotent.platform.dao.system.SysUserDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/27
 */

@Service
public class DdGoldenCoinService extends AbstractService<DdGoldenCoin, Long> {

    private final Log logger = LogFactory.getLog(DdScoreService.class);

    private boolean isUseCache = false;

    @Resource
    private DdGoldenCoinDao ddGoldenCoinDao;

    @Resource
    private SysUserDao sysUserDao;

    @Resource
    private Cache cache;

    @Override
    protected IEntityDao<DdGoldenCoin, Long> getEntityDao() {
        return this.ddGoldenCoinDao;
    }

    /**
     * 在程序启动时与其他缓存初始化一起调用
     */
    public void initGoldenCoinCache() {
        //golden未使用缓存
    }

    /**
     * 获取个人金币数
     *
     * @param uid 用户id
     * @return 如果都有的话应该是四项
     */
    public List<DdGoldenCoin> getPersonal(Long uid) {
        return ddGoldenCoinDao.getPersonal(uid);
    }

    /**
     * 得币
     *
     * @param userId   用户
     * @param coinType 一级类型
     * @param coinNum  数目
     */
    void gainCoin(Long userId, String coinType, Integer coinNum) {
        List<DdGoldenCoin> userCoinList = ddGoldenCoinDao.getPersonal(userId);
        DdGoldenCoin userTypeCoin = new DdGoldenCoin();
        //币表中是否有
        Boolean isHave = false;
        for (DdGoldenCoin ddGoldenCoin : userCoinList) {
            if (coinType.equals(ddGoldenCoin.getCoinType())) {
                userTypeCoin = ddGoldenCoin;
                isHave = true;
                break;
            }
        }
        if (isHave) {
            Long nowCoin = userTypeCoin.getCoinNum();
            userTypeCoin.setCoinNum(nowCoin + coinNum);
            ddGoldenCoinDao.update(userTypeCoin);
        } else {
            userTypeCoin.setId(UniqueIdUtil.genId());
            userTypeCoin.setUserId(userId);
            userTypeCoin.setCoinType(coinType);
            userTypeCoin.setCoinNum(Integer.toUnsignedLong(coinNum));
            ISysUser user = sysUserDao.getById(userId);
            userTypeCoin.setUserName(user.getFullname());
            userTypeCoin.setOrgId(user.getOrgId());
            userTypeCoin.setOrgName(user.getOrgName());
            ddGoldenCoinDao.add(userTypeCoin);
        }
    }
}