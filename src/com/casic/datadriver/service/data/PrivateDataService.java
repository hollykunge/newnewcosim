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
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                //已经订阅该数据
                jsonObject.put("torderState", 1);
            } else {
                //没有订阅该数据
                jsonObject.put("torderState", 0);
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

    /**
     * 数据转换
     */

    private List<Object[]> transData(List<PrivateData> privateDataList, String[] rowsName) {

        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < privateDataList.size(); i++) {
            PrivateData privateData = privateDataList.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i;
            objs[1] = privateData.getDdDataName();
            objs[2] = privateData.getDdDataLastestValue();
            objs[3] = privateData.getDdDataUnit();
            objs[4] = privateData.getDdDataType();
            objs[5] = privateData.getDdDataSenMax();
            objs[6] = privateData.getDdDataSenMin();
            objs[7] = privateData.getDdDataTaskName();
            objs[8] = privateData.getDdDataCreator();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String date = df.format(man.getModiDate());
//            objs[5] = date;
            dataList.add(objs);
        }
        return dataList;
    }

    /**
     * 导出数据
     */
    private HSSFWorkbook writeDataToXls(String[] rowName, String title, Long taskId, String type) {
        HSSFWorkbook workbook = new HSSFWorkbook();// 创建工作簿对象
        try {

            HSSFSheet sheet = workbook.createSheet(title);// 创建工作表

            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTiltle = rowm.createCell(0);

            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook);//单元格样式对象

            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
            cellTiltle.setCellStyle(columnTopStyle);
            cellTiltle.setCellValue(title);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);// 在索引2的位置创建行(最顶端的行开始的第二行)

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n);//创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);//设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);//设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle);//设置列头单元格样式
            }
            List<PrivateData> tempList = new ArrayList<>();
            List<OrderDataRelation> orderDataRelations = orderDataRelationDao.getOrderDataRelationList(taskId);
            for (OrderDataRelation orderDataRelation : orderDataRelations) {
                tempList.add(this.getDataById(orderDataRelation.getDdDataId()));
            }
            List<Object[]> dataList = transData(tempList, rowName);

            //将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {

                Object[] obj = dataList.get(i);//遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);//创建所需的行数

                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;   //设置单元格的数据类型
                    if (j == 0) {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(i + 1);
                    } else {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString());//设置单元格的值
                        }
                    }
                    cell.setCellStyle(style);//设置单元格样式
                }
            }
            //让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 导入数据
     */
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

                if (!String.valueOf(hssfRow.getCell(0)).equals("null") && !String.valueOf(hssfRow.getCell(0)).isEmpty()) {

                    privateData.setDdDataId(UniqueIdUtil.genId());
                    privateData.setDdDataTaskId(taskId);
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

    public HSSFWorkbook exportBrandPeriodSort(String[] rowName, String title, Long taskId, String type) throws Exception {
        HSSFWorkbook workbook = writeDataToXls(rowName, title, taskId, type);
        return workbook;
    }

    /**
     * 递归
     *
     * @return
     */
    private List<PrivateData> recursion(List<PrivateData> privateDataList) {
        List<PrivateData> tempList = new ArrayList<>();
        for (PrivateData privateData : privateDataList) {
            if (privateData != null) {
                //判断当前节点的父节点是否是pid
                if ("0".equals(privateData.getDdDataParentId()) || privateData.getDdDataParentId() == null) {

                    tempList.add(privateData);
                }else {
                    List<PrivateData> privateDataList1 = privateDataDao.getDataListByPId(privateData.getDdDataParentId());
                    this.recursion(privateDataList1);
                }
            }
        }
        return privateDataList;
    }

    public String createToPublish(String dataIds, String parent) {

        String[] tempStr = dataIds.split("[,|，]");
        List<String> listId = Arrays.asList(tempStr);
        switch (parent) {
            case "publishpanel":
                List<PrivateData> privateDataList = recursion(dataIds);
                for (int i = 0; i < privateDataList.size(); i++) {
                    privateDataDao.updateToPublish(privateDataList.get(i));
                }
                break;
            case "createpanel":
                for (int i = 0; i < listId.size(); i++) {
                    List<OrderDataRelation> orderDataRelation = orderDataRelationDao.getBeOrderDataByDataId(Long.parseLong(listId.get(i)));
                    PrivateData privateData = privateDataDao.getDataById(Long.parseLong(listId.get(i)));
                    if (orderDataRelation.isEmpty()) {
                        privateData.setDdDataPublishState((byte) 0);
                        privateDataDao.updateToPrivate(privateData);
                    }
                }
                break;
            default:
                break;
        }
        return "撤销失败";
    }

    public void canOrderToOrder(String dataIds, String parent, Long taskId) {
        String[] tempStr = dataIds.split("[,|，]");
        List<String> listId = Arrays.asList(tempStr);
        List<OrderDataRelation> orderdatarelationList = new ArrayList<>();
        //已订阅到可订阅
        if (("canorderpanel").equals(parent)) {
            for (int i = 0; i < listId.size(); i++) {
                QueryParameters queryparameters = new QueryParameters();
                queryparameters.setId(taskId);
                queryparameters.setType(Long.parseLong(listId.get(i)));
                orderDataRelationDao.delDDOrderDataRelation(queryparameters);
            }
        }
        //可订阅到已订阅
        if (("orderpanel").equals(parent)) {
            for (int i = 0; i < listId.size(); i++) {
                PrivateData privateData = privateDataDao.getDataById(Long.parseLong(listId.get(i)));
                OrderDataRelation orderdatarelation = new OrderDataRelation();
                orderdatarelation.setDdTaskId(taskId);
                orderdatarelation.setDdDataId(Long.parseLong(listId.get(i)));
                orderdatarelation.setDdOrderDataId(UniqueIdUtil.genId());
                orderdatarelation.setDdDataName(privateData.getDdDataName());
                orderdatarelation.setDdOrderType(1L);
                orderdatarelation.setDdProjectId(privateData.getDdDataProjId());
                orderdatarelationList.add(orderdatarelation);

                List<PrivateData> privateDataList = privateDataDao.getDataListByPId(Long.parseLong(listId.get(i)));
                for (int j = 0; j < privateDataList.size(); j++) {
                    PrivateData privateData_child = privateDataList.get(j);
                    OrderDataRelation orderdatarelation_child = new OrderDataRelation();
                    orderdatarelation_child.setDdTaskId(taskId);
                    orderdatarelation_child.setDdDataId(privateData_child.getDdDataId());
                    orderdatarelation_child.setDdOrderDataId(UniqueIdUtil.genId());
                    orderdatarelation_child.setDdDataName(privateData_child.getDdDataName());
                    orderdatarelation_child.setDdOrderType(1L);
                    orderdatarelation_child.setDdProjectId(privateData_child.getDdDataProjId());
                    orderdatarelationList.add(orderdatarelation_child);
                }

            }
            orderDataRelationDao.addToOrder(orderdatarelationList);
        }
    }

    public void delprivate(Long privateId) {
        this.delDataById(privateId);
        if (this.getDataListByPId(privateId).size() == 0) {
            return;
        } else {
            Long pid = privateId;
            List<PrivateData> privateDatalist = this.getDataListByPId(pid);
            this.delDataById(pid);
            for (int i = 0; i < privateDatalist.size(); i++) {
                delprivate(privateDatalist.get(i).getDdDataId());
            }
        }
    }

    /*
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /*
     * 列数据信息单元格样式
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        //设置字体大小
        //font.setFontHeightInPoints((short)10);
        //字体加粗
        //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //设置字体名字
        font.setFontName("Courier New");
        //设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }
}
