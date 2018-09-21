package com.casic.datadriver.service.index;


import com.casic.datadriver.dao.index.IndexInfoDao;
import com.casic.datadriver.publicClass.PageInfo;
import com.casic.datadriver.model.index.IndexInfo;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
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
import java.util.List;

/**
 * Created by Administrator on 2016/11/18 0018.
 */
@Service
public class IndexService extends BaseService<IndexInfo> {


    public IndexService() {
    }

    @Resource
    private IndexInfoDao indexDao;

    public boolean addDDIndex(IndexInfo index) {
        this.indexDao.add(index);
        return true;
    }

    public List<IndexInfo> getByProjectId(long projectId) {
        return this.indexDao.getByProjectId(projectId);
    }

    @Override
    protected IEntityDao<IndexInfo, Long> getEntityDao() {
        // TODO Auto-generated method stub
        return this.indexDao;
    }

    public List<IndexInfo> getByProjectIdF(PageInfo pageInfo){return this.indexDao.getByProjectIdF(pageInfo);}

    private static String getValue(HSSFCell cell) {
        String value = "";
        if (cell == null) {
            value = "";
        } else {
            value = cell.getStringCellValue();
        }
        return value;
    }

    private List<IndexInfo> readBrandPeriodSorXls(InputStream is)
            throws IOException, ParseException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<IndexInfo> brandMobileInfos = new ArrayList<IndexInfo>();
        IndexInfo indexInfo;
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            //根据表单序号获取表单
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }

            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                indexInfo = new IndexInfo();
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                indexInfo.setDdIndexId(UniqueIdUtil.genId());
                for (int i = 0; i < hssfRow.getLastCellNum(); i++) {

                }
                brandMobileInfos.add(indexInfo);
            }
        }
        return brandMobileInfos;
    }

    public List<IndexInfo> importIndexXml(InputStream in) throws Exception {
        List<IndexInfo> brandMobileInfos = readBrandPeriodSorXls(in);
        for (int a = 0; a < brandMobileInfos.size(); a++) {
            this.indexDao.add(brandMobileInfos.get(a));
        }
        return brandMobileInfos;
    }
}
