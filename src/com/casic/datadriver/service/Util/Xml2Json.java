package com.casic.datadriver.service.Util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

public class Xml2Json {
    public static void main(String[] args) throws Exception {
        String xmlStr = readFile("D:/temp/imp/终端维保-模拟测试包11111/123.xml");
//		String xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><task><authKey comments=\"授权码\">6240</authKey><TaskCheck taskType=\"装备巡检\"><data><id comments=\"巡检id\">b6321852ae954933b2d025e8daf3ac32</id><name comments=\"巡检活动名称\">111111</name><customerName comments=\"联系单位名称\">中情局</customerName><contactsName comments=\"联系人信息\">王五,13345678903,北京市海淀区五棵松</contactsName><checkTypeName comments=\"巡检类型名称\">活动检查</checkTypeName><startTime comments=\"巡检开始时间\">2017-11-10 00:00:00</startTime><endTime comments=\"巡检结束时间\">2017-11-10 00:00:00</endTime><content comment=\"巡检内容\">12323</content><implUserName comments=\"实施人员\">韩旭,殷少伟</implUserName><createTime comments=\"创建时间\">2017-11-10 18:33:49</createTime><creatorName comments=\"创建人名称\">殷少伟</creatorName><updateTime comments=\"更新时间\"/><updator comments=\"更新人\"/><leadUserName comments=\"申请人姓名\">殷少伟</leadUserName><solderTypeName comments=\"军兵种\">陆军</solderTypeName><modelNumberName comments=\"型号\">海红旗七号</modelNumberName><address comments=\"任务地点\">北京市海淀区测试地址</address><outsourcestaffName comments=\"辅助人员名称\">孙悟空</outsourcestaffName><ispassback comments=\"是否回传标示（终端回传：1，系统创建：0）\">0</ispassback><templateId comments=\"作业文件模板\">29bdff2b58d64ddb8e7dde737c0bf5f3</templateId><attachment comments=\"任务附件\"><data><path comments=\"装备巡检.xls\">attrs/20171110/105b342e2c654fd0b2afbf121ef6f898.xls</path></data><data><path comment=\"测试.docx\">attrs/20171110/2922932839472348324234.docx</path></data></attachment><taskItem comments=\"作业文件\"><data><id comments=\"作业文件id\">c7334b8a73514c4f9cbfc559f3270f89</id><field_42 comments=\"巡检结论\" taskType=\"textarea\">结论天写1</field_42><field_43 comments=\"巡检目的\" taskType=\"input\">安全防护</field_43><field_44 comments=\"关键节点\" taskType=\"select\">未延期的节点</field_44><formId comments=\"流程表单id\">b6321852ae954933b2d025e8daf3ac32</formId><implUserName comments=\"实施人\">韩旭</implUserName><ispassback comments=\"是否回传标示（终端回传：1，系统创建：0）\">1</ispassback><templateId comments=\"作业文件模板id\">29bdff2b58d64ddb8e7dde737c0bf5f3</templateId><createTime comments=\"创建时间\">2017-11-10 18:34:14</createTime><updateTime comments=\"更新时间\"/><updator comments=\"更新人\"/></data><attachment comments=\"作业文件附件\"><data/></attachment></taskItem></data></TaskCheck></task>";
        Document doc = DocumentHelper.parseText(xmlStr);
        System.out.println(xmlStr);

        JSONObject json = new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        System.out.println("xml2Json:" + json.toJSONString());

    }

    public static String readFile(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(new Long(file.length()).intValue());
        // fc向buffer中读入数据
        fc.read(bb);
        bb.flip();
        String str = new String(bb.array(), "UTF8");
        fc.close();
        fis.close();
        return str;
    }

    public static String readFile(String path) throws Exception {
        File file = new File(path);
        return readFile(file);
    }

    /**
     * xml转json
     *
     * @param xmlStr
     * @return
     * @throws DocumentException
     */
    public static JSONObject xml2Json(String xmlStr) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        JSONObject json = new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        return json;
    }

    /**
     * xml转json
     *
     * @param element
     * @param json
     */
    public static void dom4j2Json(Element element, JSONObject json) {
        // 如果是属性
        for (Object o : element.attributes()) {
            Attribute attr = (Attribute) o;
            if (!isEmpty(attr.getValue())) {
                json.put("@" + attr.getName(), attr.getValue());
            }
        }
        List<Element> chdEl = element.elements();
        if (chdEl.isEmpty() && !isEmpty(element.getText())) {// 如果没有子元素,只有一个值
            json.put(element.getName(), element.getText());
        }

        for (Element e : chdEl) {// 有子元素
            if (!e.elements().isEmpty()) {// 子元素也有子元素
                JSONObject chdjson = new JSONObject();
                dom4j2Json(e, chdjson);
                Object o = json.get(e.getName());
                if (o != null) {
                    JSONArray jsona = null;
                    if (o instanceof JSONObject) {// 如果此元素已存在,则转为jsonArray
                        JSONObject jsono = (JSONObject) o;
                        json.remove(e.getName());
                        jsona = new JSONArray();
                        jsona.add(jsono);
                        jsona.add(chdjson);
                    }
                    if (o instanceof JSONArray) {
                        jsona = (JSONArray) o;
                        jsona.add(chdjson);
                    }
                    json.put(e.getName(), jsona);
                } else {
                    if (!chdjson.isEmpty()) {
                        json.put(e.getName(), chdjson);
                    }
                }

            } else {// 子元素没有子元素
                for (Object o : element.attributes()) {
                    Attribute attr = (Attribute) o;
                    if (!isEmpty(attr.getValue())) {
                        json.put("@" + attr.getName(), attr.getValue());
                    }
                }
                if (!e.getText().isEmpty()) {
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }

    public static boolean isEmpty(String str) {

        if (str == null || str.trim().isEmpty() || "null".equals(str)) {
            return true;
        }
        return false;
    }
}
