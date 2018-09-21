package com.casic.datadriver.service.data;

import com.casic.datadriver.dao.data.OrderDataRelationDao;
import com.casic.datadriver.dao.data.PrivateDataDao;
import com.casic.datadriver.model.data.OrderDataRelation;
import com.casic.datadriver.model.data.PrivateData;
import com.casic.datadriver.publicClass.QueryParameters;
import com.casic.datadriver.service.task.TaskInfoService;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.ContextUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.auth.ISysUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The Class PrivateDataService.
 */
@Service
public class PrivateDataService extends BaseService<PrivateData> {

    /**
     * The privateData dao.
     */
    @Resource
    private PrivateDataDao privateDataDao;

    @Resource
    private OrderDataRelationDao orderDataRelationDao;

    @Resource
    private TaskInfoService taskInfoService;

    public boolean addDDPrivateData(PrivateData privateData) {
        this.privateDataDao.add(privateData);
        return true;
    }


    protected IEntityDao<PrivateData, Long> getEntityDao() {
        return this.privateDataDao;
    }

    /**
     * 添加一个数据
     */
    public void addSingleData(PrivateData privateData) {
        privateDataDao.addSingleData(privateData);
    }

    /**
     * 获取所有数据
     */
    public List<PrivateData> getAll() {
        return privateDataDao.getAll();
    }

    /**
     * 获取任务私有数据
     */
    public List<PrivateData> getPrivateByTaskId(Long taskId) {

        return privateDataDao.getPrivateByTaskId(taskId);
    }

    /**
     * 根据数据ID获取数据
     *
     * @param dataId
     */
    public PrivateData getDataById(Long dataId) {
        return privateDataDao.getDataById(dataId);
    }

    /**
     * 根据数据ParentID获取数据
     */
    public List<PrivateData> getDataListByPId(Long parentId) {
        return privateDataDao.getDataListByPId(parentId);
    }

    /**
     * 根据数据D删除单个数据
     */
    public void delDataById(Long dataID) {
        privateDataDao.delDataById(dataID);
    }

    /**
     * 根据PID删除单个数据
     */
    public void delDataByPId(Long dataPID) {
        privateDataDao.delDataByPId(dataPID);
    }

    /**
     * 获取任务发布的数据
     */
    public String getPubListByTaskId(Long taskId) {

        return null;
    }

    /**
     * 根据任务ID和数据名称获取数据ID
     */
    public Long getDataIdByTaskIdAndDataName(Long taskId, String dataName) {
        QueryParameters queryParameters = new QueryParameters();
        queryParameters.setId(taskId);
        queryParameters.setName(dataName);
        PrivateData privateData = privateDataDao.getDataIdByTaskIdAndDataName(queryParameters);
        return privateData.getDdDataId();
    }

    /**
     * 获取任务未发布的数据
     */
    public List<PrivateData> getUnPubListByTaskId(Long taskId) {
        return privateDataDao.getUnPubListByTaskId(taskId);
    }

    /**
     * 获取任务订阅的数据
     */
    public List<PrivateData> getOrdListByTaskId(Long taskId) {
        return privateDataDao.getOrdListByTaskId(taskId);
    }

    /**
     * 获取任务未订阅的数据
     */
    public List<PrivateData> getUnOrdListByTaskId(Long taskId) {
        return privateDataDao.getUnOrdListByTaskId(taskId);
    }

    /**
     * 获取任务输出数据(私有)
     */
    public String getOutputDataByTaskId(Long taskId) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonMembers = new JSONArray();
        List<PrivateData> privateDataList = privateDataDao.getDataListByTaskId(taskId);
        for (int i = 0; i < privateDataList.size(); i++) {
            PrivateData privateData = privateDataList.get(i);
            jsonObject.put("dataId", privateData.getDdDataId());
            jsonObject.put("dataName", privateData.getDdDataName());
            jsonObject.put("filePath", privateData.getDdDataPath());
            jsonObject.put("parentId", privateData.getDdDataParentId());
            jsonObject.put("taskId", privateData.getDdDataTaskId());
            switch (privateData.getDdDataType()) {
                case 0:
                    jsonObject.put("dataType", "数值");
                    break;
                case 1:
                    jsonObject.put("dataType", "模型");

                    break;
                case 2:
                    jsonObject.put("dataType", "文件");

                    break;
                case 3:
                    jsonObject.put("dataType", "结构型数据");
                    break;
                default:
                    break;
            }

            jsonObject.put("isLeaf", privateData.getDdDataIsLeaf());
            jsonObject.put("dataDescription", privateData.getDdDataDescription());
            jsonObject.put("publishState", privateData.getDdDataPublishState());
            jsonObject.put("orderState", privateData.getDdDataOrderState());
            jsonObject.put("submitState", privateData.getDdDataIsSubmit());
            jsonObject.put("taskName", privateData.getDdDataTaskName());
            jsonObject.put("creator", privateData.getDdDataCreator());
            jsonObject.put("createTime", privateData.getDdDataCreateTime());
            jsonObject.put("projectId", privateData.getDdDataProjId());
            jsonObject.put("creatorId", privateData.getDdDataCreatorId());
            jsonObject.put("dataUnit", privateData.getDdDataUnit());
            jsonObject.put("dataSenMax", privateData.getDdDataSenMax());
            jsonObject.put("dataSenMin", privateData.getDdDataSenMin());
            jsonObject.put("dataValue", privateData.getDdDataLastestValue());
            jsonObject.put("type", 0);
            jsonMembers.add(jsonObject);
        }
        String jsonstring = jsonMembers.toString();
        return jsonstring;
    }

    /**
     * 获取任务输入数据(项目)
     */
    public String getInputDataByTaskId(Long projectId, Long taskId) {
        QueryParameters queryParameters = new QueryParameters();
        queryParameters.setId(projectId);
        queryParameters.setBackupsL(taskId);
        List<PrivateData> allPrivateData = privateDataDao.getDataListByProId(queryParameters);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonMembers = new JSONArray();

        for (int i = 0; i < allPrivateData.size(); i++) {
            PrivateData privateData = allPrivateData.get(i);
            queryParameters.setType(privateData.getDdDataId());
            jsonObject.put("dataId", privateData.getDdDataId());
            jsonObject.put("dataName", privateData.getDdDataName());
            jsonObject.put("filePath", privateData.getDdDataPath());
            jsonObject.put("parentId", privateData.getDdDataParentId());
            jsonObject.put("taskId", privateData.getDdDataTaskId());
            switch (privateData.getDdDataType()) {
                case 0:
                    jsonObject.put("dataType", "数值");
                    break;
                case 1:
                    jsonObject.put("dataType", "模型");
                    break;
                case 2:
                    jsonObject.put("dataType", "文件");
                    break;
                case 3:
                    jsonObject.put("dataType", "结构型数据");
                    break;
                default:
                    break;
            }

            jsonObject.put("dataDescription", privateData.getDdDataDescription());
            jsonObject.put("publishState", privateData.getDdDataPublishState());
            jsonObject.put("orderState", privateData.getDdDataOrderState());
            jsonObject.put("submitState", privateData.getDdDataIsSubmit());
            jsonObject.put("taskName", privateData.getDdDataTaskName());
            jsonObject.put("creator", privateData.getDdDataCreator());
            jsonObject.put("createTime", privateData.getDdDataCreateTime());
            jsonObject.put("projectId", privateData.getDdDataProjId());
            jsonObject.put("creatorId", privateData.getDdDataCreatorId());
            jsonObject.put("dataUnit", privateData.getDdDataUnit());
            jsonObject.put("dataValue", privateData.getDdDataLastestValue());
            jsonObject.put("dataSenMax", privateData.getDdDataSenMax());
            jsonObject.put("dataSenMin", privateData.getDdDataSenMin());
            QueryParameters queryParameters1 = new QueryParameters();
            queryParameters1.setId(taskId);
            queryParameters1.setType(privateData.getDdDataId());
            if (orderDataRelationDao.getDDOrderDataRelation(queryParameters1).size() > 0) {
                jsonObject.put("torderState", 1);//已经订阅该数据
            } else {
                jsonObject.put("torderState", 0);//没有订阅该数据
            }
            jsonMembers.add(jsonObject);
        }
        return jsonMembers.toString();
    }

    /**
     * 更新数据
     */
    public void updateData(PrivateData privateData) {
        privateDataDao.updateData(privateData);
    }

    /**
     * 根据任务ID删除单个数据
     */
    public void delByTaskId(Long taskId) {
        privateDataDao.delByTaskId(taskId);
    }

    public static String getValue(HSSFCell cell) {
        String value = "";
        if (cell == null) {
            value = "";
        } else {
            value = cell.getStringCellValue();
        }
        return value;
    }

    private List<PrivateData> readBrandPeriodSorXls(InputStream is, Long taskId, Long projectId, String taskname)
            throws IOException, ParseException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<PrivateData> privateDatalist = new ArrayList<PrivateData>();
        PrivateData privateData;

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            //根据表单序号获取表单
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            String str = null;
            Long StructId = Long.valueOf(0);
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                privateData = new PrivateData();

                HSSFRow hssfRow = hssfSheet.getRow(rowNum);

                ISysUser sysUser = ContextUtil.getCurrentUser();
                Date now = new Date();
                byte DataType = 0;
                switch (String.valueOf(hssfRow.getCell(2))) {
                    case "结构型数据":
                        DataType = 3;
                        break;
                    case "文件":
                        DataType = 2;
                        break;
                    case "模型":
                        DataType = 1;
                        break;
                    case "数值":
                        DataType = 0;
                        break;
                    default:
                        DataType = 0;
                        break;
                }

                if (!String.valueOf(hssfRow.getCell(0)).equals("null")) {


                    privateData.setDdDataId(UniqueIdUtil.genId());
                    privateData.setDdDataTaskId(taskId);
                    String a = String.valueOf(hssfRow.getCell(7));
                    if (String.valueOf(hssfRow.getCell(7)).equals("null") || String.valueOf(hssfRow.getCell(7)).equals("")) {
                        privateData.setDdDataIsLeaf((short) 0);
                    } else {
                        privateData.setDdDataParentId(getDataIdByTaskIdAndDataName(taskId, String.valueOf(hssfRow.getCell(7))));
                        privateData.setDdDataIsLeaf((short) 1);
                    }
                    privateData.setDdDataProjId(projectId);
                    privateData.setDdDataPublishState((byte) 0);
                    privateData.setDdDataOrderState((short) 0);
                    privateData.setDdDataIsSubmit((short) 0);
                    privateData.setDdDataCreateTime(now);
                    privateData.setDdDataTaskName(taskname);
                    privateData.setDdDataCreatorId(ContextUtil.getCurrentUserId());
                    if (!String.valueOf(hssfRow.getCell(0)).equals("null")) {
                        privateData.setDdDataName(String.valueOf(hssfRow.getCell(0)));
                    }

                    if (!String.valueOf(hssfRow.getCell(1)).equals("null")) {
                        privateData.setDdDataEngName(String.valueOf(hssfRow.getCell(1)));
                    }

                    if (!String.valueOf(hssfRow.getCell(5)).equals("null")) {
                        privateData.setDdDataLastestValue(String.valueOf(hssfRow.getCell(5)));
                    }

                    if (String.valueOf(hssfRow.getCell(4)).equals("null")) {
                        privateData.setDdDataSenMax("0");
                    }

                    if (String.valueOf(hssfRow.getCell(3)).equals("null")) {
                        privateData.setDdDataSenMin("0");
                    }

                    if (!String.valueOf(hssfRow.getCell(4)).equals("null")) {
                        privateData.setDdDataSenMax(String.valueOf(hssfRow.getCell(4)));
                    }

                    if (!String.valueOf(hssfRow.getCell(3)).equals("null")) {
                        privateData.setDdDataSenMin(String.valueOf(hssfRow.getCell(3)));
                    }

                    if (!String.valueOf(hssfRow.getCell(6)).equals("null")) {
                        privateData.setDdDataUnit(String.valueOf(hssfRow.getCell(6)));
                    }

                    privateData.setDdDataType(DataType);
                    privateDatalist.add(privateData);
                    this.privateDataDao.addSingleData(privateData);
                }
            }
        }

        return privateDatalist;
    }

    public int importBrandPeriodSort(InputStream in, Long taskId, Long projectId, String taskname) throws Exception {
        List<PrivateData> brandMobileInfos = readBrandPeriodSorXls(in, taskId, projectId, taskname);

        return brandMobileInfos.size();
    }

    public void createToPublish(String dataIds, String parent) {
        String[] tempStr = dataIds.split("[,|，]");
        List<String> listId = Arrays.asList(tempStr);
        List<PrivateData> privateDataList = new ArrayList<PrivateData>();
        for (int i = 0; i < listId.size(); i++) {
            PrivateData privateData = privateDataDao.getDataById(Long.parseLong(listId.get(i)));
            privateDataList.add(privateData);
        }
        //私有到发布
        if (parent.equals("publishpanel")) {
            privateDataDao.updateToPublish(privateDataList);
        }
    }

    public void canOrderToOrder(String dataIds, String parent, Long taskId) {
        String[] tempStr = dataIds.split("[,|，]");
        List<String> listId = Arrays.asList(tempStr);
        List<OrderDataRelation> orderdatarelationList = new ArrayList<>();
        //已订阅到可订阅
        if (parent.equals("canorderpanel")) {
            for (int i = 0; i < listId.size(); i++) {
                QueryParameters queryparameters = new QueryParameters();
                queryparameters.setId(taskId);
                queryparameters.setType(Long.parseLong(listId.get(i)));
                orderDataRelationDao.delDDOrderDataRelation(queryparameters);
            }
        }
        //可订阅到已订阅
        if (parent.equals("orderpanel")) {
            for (int i = 0; i < listId.size(); i++) {
                PrivateData privateData = privateDataDao.getDataById(Long.parseLong(listId.get(i)));
                OrderDataRelation orderdatarelation = new OrderDataRelation();
                orderdatarelation.setDdTaskId(taskId);
                orderdatarelation.setDdDataId(Long.parseLong(listId.get(i)));
                orderdatarelation.setDdOrderDataId(UniqueIdUtil.genId());
                orderdatarelation.setDdDataName(privateData.getDdDataName());
                orderdatarelation.setDdOrderType(1l);
                orderdatarelation.setDdProjectId(privateData.getDdDataProjId());
                orderdatarelationList.add(orderdatarelation);

                List<PrivateData> privateDataList = privateDataDao.getDataListByPId(Long.parseLong(listId.get(i)));
                for (int j=0; j < privateDataList.size(); j++){
                    PrivateData privateData_child = privateDataList.get(j);
                    OrderDataRelation orderdatarelation_child = new OrderDataRelation();
                    orderdatarelation_child.setDdTaskId(taskId);
                    orderdatarelation_child.setDdDataId(privateData_child.getDdDataId());
                    orderdatarelation_child.setDdOrderDataId(UniqueIdUtil.genId());
                    orderdatarelation_child.setDdDataName(privateData_child.getDdDataName());
                    orderdatarelation_child.setDdOrderType(1l);
                    orderdatarelation_child.setDdProjectId(privateData_child.getDdDataProjId());
                    orderdatarelationList.add(orderdatarelation_child);
                }

            }
            orderDataRelationDao.addToOrder(orderdatarelationList);
        }
    }

}
