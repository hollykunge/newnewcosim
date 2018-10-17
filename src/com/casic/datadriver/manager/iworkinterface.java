package com.casic.datadriver.manager;

import com.casic.datadriver.model.project.Project;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 * @Author: 忠
 * @Description: 与客户端接口
 * @Date: 创建于 2018/10/17
 */
@Component
public class iworkinterface {
    /**
     * @param receiverId 接收人id
     * @param senderId   发送人id
     * @param msg        消息内容
     * @param type       消息类型
     * @author 忠
     * @date 2018/10/17 14:06
     * @Description 消息发送接口
     */
//    @Override
    public void senMsg(Long senderId, Long receiverId, String msg, Long type) throws IOException {
        JSONObject json = new JSONObject();
        JSONArray jsonMembers = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("senderId", senderId);
        jsonObject.put("receiver", receiverId);
        jsonObject.put("message", msg);
        jsonObject.put("type", type);
        OutputStreamWriter out = null;
        String strURL = "http://10.12.97.30:8083/interfaces/gainSystemMsg";
        URL url = new URL(strURL);
        HttpURLConnection conn = (HttpURLConnection)
                url.openConnection();
        // 发送POST请求必须设置为true
        conn.setDoOutput(true);
        conn.setDoInput(true);
        //设置连接超时时间和读取超时时间
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("contentType", "utf-8");
        out = new OutputStreamWriter(conn.getOutputStream());
        out.write("content" + "=" + URLEncoder.encode(jsonObject.toString(), "utf-8"));
        // POST的请求参数写在正文中
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        conn.disconnect();
        System.out.println(buffer.toString());
    }

    /**
     * @param project 项目属性
     * @author 忠
     * @date 2018/10/17 14:13
     * @Description 增加讨论组属性
     */
//    @Override
    public String addGroup(Project project) {
        return "创建研讨组成功";
    }

    /**
     * @param project 项目属性
     * @param userId 人员ID
     * @author 忠
     * @date 2018/10/17 14:13
     * @Description 增加讨论组人员
     */
    public String addGroupUser(Project project,String userId) {
        return "增加人员成功";
    }


}
