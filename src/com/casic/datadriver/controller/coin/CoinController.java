package com.casic.datadriver.controller.coin;

import com.casic.datadriver.dao.coin.DdScoreInflowDao;

import com.casic.datadriver.model.coin.DdScoreInflow;
import com.casic.datadriver.publicClass.ScoreRegulation;
import com.casic.datadriver.service.coin.DdScoreInflowService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.controller.GenericController;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.ResultMessage;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.sun.corba.se.impl.orbutil.CorbaResourceUtil.getText;

/**
 * @Author: hollykunge
 * @Description:
 * @Date: 创建于 2018/9/20
 * @Modified:
 */
@Controller
@RequestMapping("/coin/")
public class CoinController extends GenericController {

    @Autowired
    DdScoreInflowService ddScoreInflowService;
    /**
     * 赚取积分接口
     * @throws Exception the exception
     */
    @RequestMapping("add")
    @ResponseBody
    public void save(String uid, String sourceScore, String sourceType, String sourceDetail, String updTime) throws Exception {
        String resultMsg = null;
        try {
            if (uid != null) {
                if (sourceScore != null){
                    DdScoreInflow ddScoreInflow = new DdScoreInflow();
                    ddScoreInflow.setId(UniqueIdUtil.genId());
                    ddScoreInflow.setUid(Long.parseLong(uid));
                    ddScoreInflow.setSourceScore(Long.valueOf(sourceScore));
                    ddScoreInflow.setSourceDetail(sourceDetail);
                    ddScoreInflow.setSourceType(sourceType);
                    ddScoreInflow.setUpdTime(updTime);

                    if (ScoreRegulation.isOverFlow(sourceScore, todayScore)){
                        ddScoreInflowService.add(ddScoreInflow);
                        resultMsg = getText("赚取积分成功", ddScoreInflow.toString());
                    }else {
                        resultMsg = getText("单日积分总量已满", ddScoreInflow.toString());
                    }


                } else {
                    resultMsg = getText("积分为空", uid);
                }
            }else {
                resultMsg = getText("用户id为空", sourceType);
            }
            writeResultMessage(null, resultMsg, ResultMessage.Success);
        } catch (Exception e) {
            writeResultMessage(null, resultMsg + "," + e.getMessage(), ResultMessage.Fail);
        }
    }
}
