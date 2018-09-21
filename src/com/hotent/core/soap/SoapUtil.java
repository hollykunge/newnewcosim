package com.hotent.core.soap;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.hotent.core.engine.GroovyScriptEngine;
import com.hotent.core.page.PageBean;
import com.hotent.core.soap.type.SoapType;
import com.hotent.core.soap.type.SoapTypes;
import com.hotent.core.util.StringUtil;


/**
 * SOAP调用工具类
 * 
 * @author wwz
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SoapUtil {

	private static Logger logger = LoggerFactory.getLogger(SoapUtil.class);

	/**
	 * 调用出错异常
	 * 
	 */
	@SuppressWarnings("serial")
	public static class InvokeException extends Exception {

		/**
		 * 调用webservice出错时返回的{@link SOAPFault#getFaultCode()}值.
		 */
		private String code;

		/**
		 * 调用webservice出错时返回的{@link SOAPFault#getFaultString()}值.
		 */
		private String msg;

		public InvokeException(String code, String msg) {
			this(code, msg, null);
		}

		public InvokeException(String code, String msg, Throwable e) {
			super("[" + code + "]" + msg, e);
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}

	/**
	 * webservice入参处理器<br>
	 * 
	 * <pre>
	 * request配置属性说明.
	 * +-------------+---------------------------+-------------------------+
	 * +  字段名                     + 说明                                                                      + 备注                                                                +
	 * +-------------+---------------------------+-------------------------+
	 * + namespace   + 命名空间                                                              +                         +
	 * +-------------+---------------------------+-------------------------+
	 * + method      + 调用方法                                                              +                         +
	 * +-------------+---------------------------+-------------------------+
	 * + prefix      + 所说服务                                                              +                         +
	 * +-------------+---------------------------+-------------------------+
	 * + binding     + 绑定参数（优先级比脚本高）                          + 支持级联表达式,例如:          +
	 * +             +                           + a.b ; a[1].b ;          +
	 * +-------------+---------------------------+-------------------------+
	 * + defaultValue+ 默认值                                                                  + 默认值                                                            +
	 * +-------------+---------------------------+-------------------------+
	 * + script      + 是否使用脚本                                                      + 当值为true时，调用脚本.       +
	 * +             +                           + 脚本在当前节点下                                        +
	 * +-------------+---------------------------+-------------------------+
	 * + soapType    + soap类型（与beanType2选1）            + 一般有：string，int，date，     +
	 * +             +                           + anyType等几种                                       +
	 * +-------------+---------------------------+-------------------------+
	 * + beanType    + Java类型,可以是自定义复杂类型,    + 如：java.lang.String，            +
	 * +             + 也可以是基本类型。                                          + com.hotent.core.PageBean+
	 * +-------------+---------------------------+-------------------------+
	 * </pre>
	 * 
	 * <pre>
	 * <![CDATA[
	 * 		<request namespace="http://api.webservice.platform.hotent.com/" method="getTasksByRunId" prefix="processService">
	 * 			<!--soapType和beanType属性2选1,目的是找到转换器-->
	 * 			<arg0 binding="test.test1" soapType="string" beanType=""></arg0>
	 * 			<arg1>
	 * 				<page default="1"/>
	 * 			</arg1>
	 * 			<arg2 beanType="" script="true">
	 * 				<![CDATA[
	 * 					//some script return an object types "beanType".
	 * 				]]>
	 * 			</arg2>
	 * 		</request>
	 * ]]>
	 * </pre>
	 * 
	 */
	private static class RequestBuilder {

		public static SOAPMessage build(JSONArray jarray,String namespace,String method, Map variables) throws SOAPException, SAXException,
				IOException, ParserConfigurationException, IllegalAccessException, InvocationTargetException,
				NoSuchMethodException, ClassNotFoundException {
			return buildRequest(createRequest(jarray,namespace,method ,variables));
		}

		/**
		 * 构建SOAP消息。 
		 * @param element	soap body实体对象。
		 * @return
		 * @throws SOAPException
		 */
		private static SOAPMessage buildRequest(SOAPElement element) throws SOAPException {
			// 创建消息工厂
			MessageFactory messageFactory = MessageFactory.newInstance();
			// 根据消息工厂创建SoapMessage
			SOAPMessage message = messageFactory.createMessage();
			// message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");

			// 创建soap消息主体
			SOAPPart soapPart = message.getSOAPPart();// 创建soap部分
			SOAPEnvelope envelope = soapPart.getEnvelope();
			// 可以通过SoapEnvelope有效的获取相应的Body和Header等信息
			SOAPBody body = envelope.getBody();
			body.addChildElement(element);

			// Save the message
			message.saveChanges();
			return message;
		}

		private static void buildSoapElementValue(SOAPElement soapElement, JSONObject jobject, Map variables)
				throws SOAPException, ClassNotFoundException {

			if(jobject==null)return;
			// 设值
			String binding = jobject.getString("bindingVal");// 绑定参数
			String soapType = jobject.getString("soapType");// soap类型
			String beanType = jobject.getString("javaType");// java类型
			Long bindingType = jobject.getLong("bindingType");//绑定类型
			binding = StringUtil.jsonUnescape(binding);

			// 设置默认值,优先级最低
			if (bindingType==1&&StringUtil.isNotEmpty(binding)) {
				soapElement.setTextContent(binding);
			}

			// 设置变量
			if (binding != null || bindingType == 3) {
				Object obj = null;
				try {
					if (bindingType == 3) {// 使用脚本设置
						GroovyScriptEngine scriptEngine = new GroovyScriptEngine();
						
						String scriptContent = binding;
						obj = scriptEngine.executeObject(scriptContent, variables);
					} else if (binding != null) {// 使用变量名设置
						obj = PropertyUtils.getProperty(variables, binding);
					}
				} catch (Exception e) {
					logger.error("动态设值出错.", e);
				}

				if (obj != null) {// 有值时才设置
					SoapType converter;
					if (soapType != null) {
						converter = SoapTypes.getTypeBySoap(soapType);
						converter.setValue(soapElement, obj);
					} else if (beanType != null) {
						Class klass = Class.forName(beanType);
						converter = SoapTypes.getTypeByBean(klass);
						converter.setValue(soapElement, obj, klass);
					} else {
						soapElement.setTextContent(obj.toString());
					}
				}
			}
		}

		/**
		 * 根据模板创建{@link SOAPElement}对象.<br>
		 * 
		 * @param document
		 * @param variables
		 * @return
		 * @throws SOAPException
		 * @throws SAXException
		 * @throws IOException
		 * @throws ParserConfigurationException
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 * @throws NoSuchMethodException
		 * @throws ClassNotFoundException
		 */
		private static SOAPElement createRequest(JSONArray jarray,String namespace,String method ,Map variables) throws SOAPException, SAXException,
				IOException, ParserConfigurationException, IllegalAccessException, InvocationTargetException,
				NoSuchMethodException, ClassNotFoundException {

			String prefix = "api";
			SOAPElement bodyElement = SOAPFactory.newInstance().createElement(method, prefix, namespace);

			for (Object obj : jarray) {
				JSONObject jobject = (JSONObject)obj;
				if(jobject==null)
					continue;
				SOAPElement element = bodyElement.addChildElement(jobject.getString("name"));
				// 递归绑定
				buildSoapElementValue(element, jobject, variables);
			}

			return bodyElement;
		}
	}

	/**
	 * webservice出参处理器<br>
	 * 
	 * <pre>
	 * response配置属性说明.
	 * +-------------+---------------------------+-------------------------+
	 * +  字段名                     + 说明                                                                      + 备注                                                                +
	 * +-------------+---------------------------+-------------------------+
	 * + binding     + 绑定参数                                                              + 支持级联表达式,例如:          +
	 * +             +                           + a.b ; a[1].b ;          +
	 * +-------------+---------------------------+-------------------------+
	 * + defaultValue+ 默认值                                                                  + 默认值                                                            +
	 * +-------------+---------------------------+-------------------------+
	 * + soapType    + soap类型（与beanType2选1）            + 一般有：string，int，date，     +
	 * +             +                           + anyType等几种                                       +
	 * +-------------+---------------------------+-------------------------+
	 * + beanType    + Java类型,可以是自定义复杂类型,    + 如：java.lang.String，            +
	 * +             + 也可以是基本类型。                                          + com.hotent.core.PageBean+
	 * +-------------+---------------------------+-------------------------+
	 * </pre>
	 * 
	 * <pre>
	 * <![CDATA[
	 * 	<response>
	 * 		<return binding="e" soapType="string" />
	 * 	</response>
	 * ]]>
	 * </pre>
	 */
	private static class ResponseBuilder {
		/**
		 * 
		 * @param variables
		 * @param document
		 * @param message
		 * @throws SOAPException
		 * @throws InvokeException
		 */
		public static void build(Map variables, JSONArray jarray, SOAPMessage message) throws SOAPException,
				InvokeException {
			// 校验是否失败
			checkFault(message);

			// 获取返回对象
			NodeList nodeList = message.getSOAPBody().getFirstChild().getOwnerDocument().getElementsByTagName("return");
			if (nodeList == null || nodeList.getLength() < 1) {// 无返回,什么都不用处理
				return;
			}

			// 准备好返回数据
			SOAPElement[] elements = new SOAPElement[nodeList.getLength()];
			for (int i = 0; i < elements.length; i++) {
				elements[i] = (SOAPElement) nodeList.item(i);
			}

			for(Object obj : jarray){
				JSONObject jobject = (JSONObject)obj;
				build(variables, elements, jobject);
			}
		}

		/**
		 * 
		 * @param variables
		 * @param elements
		 * @param node
		 */
		private static void build(Map variables, SOAPElement[] elements, JSONObject jobject) {
			if(jobject==null)return;

			// 设值
			String binding = jobject.getString("bindingVal");// 绑定参数
			String soapType = jobject.getString("soapType");// soap类型
			String beanType = jobject.getString("javaType");// java类型
			Long bindingType = jobject.getLong("bindingType");//绑定类型
			binding = StringUtil.jsonUnescape(binding);
			// 设置变量
			if (binding != null) {
				try {
					Object obj;
					SoapType converter;
					if (soapType != null) {
						converter = SoapTypes.getTypeBySoap(soapType);
						obj = converter.convertToBean(elements);
						PropertyUtils.setProperty(variables, binding, obj);
					} else if (beanType != null) {
						Class klass = Class.forName(beanType);
						converter = SoapTypes.getTypeByBean(klass);
						obj = converter.convertToBean(klass, elements);
						PropertyUtils.setProperty(variables, binding, obj);
					} else {
						if (elements.length > 1) {
							List list = new ArrayList();
							for (SOAPElement element : elements) {
								list.add(element.getTextContent());
							}
							PropertyUtils.setProperty(variables, binding, list);
						} else {
							PropertyUtils.setProperty(variables, binding, elements[0].getTextContent());
						}
					}

					// 设置默认值,优先级最低
					if (bindingType == 1 && PropertyUtils.getProperty(variables, binding) == null) {
						PropertyUtils.setProperty(variables, binding, binding);
					}

				} catch (Exception e) {
					logger.warn("获取值不成功,跳过.", e);
				}
			}
		}

		/**
		 * 校验是否调用失败
		 * 
		 * @param message
		 * @throws SOAPException
		 * @throws InvokeException
		 */
		private static void checkFault(SOAPMessage message) throws SOAPException, InvokeException {
			SOAPBody body = message.getSOAPBody();
			SOAPFault fault = body.getFault();
			if (fault != null && fault.getFaultCode() != null) {// 出现异常
				throw new InvokeException(fault.getFaultCode(), fault.getFaultString());
			}
		}
	}

	/**
	 * 节点设值
	 * 
	 * @param node
	 * @param name
	 * @return
	 */
	private static String getAttribute(Node node, String name) {
		Node tmp = node.getAttributes().getNamedItem(name);
		return tmp != null ? tmp.getTextContent() : null;
	}

	/**
	 * webservice 调用。
	 * 指定webservice地址发送soap消息。
	 * 
	 * @param invokeURL
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private static SOAPMessage invoke(URL invokeURL, SOAPMessage request) throws Exception {
		// 创建连接
		SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = null;
		try {
			URL endpoint = new URL(null,
								   invokeURL.toString(),
								   new URLStreamHandler() {
									@Override
									protected URLConnection openConnection(URL u) throws IOException {
										URL clone_url = new URL(u.toString());
										HttpURLConnection clone_urlconnection = (HttpURLConnection) clone_url.openConnection();
										clone_urlconnection.setConnectTimeout(2000);
										clone_urlconnection.setReadTimeout(3000);
										return(clone_urlconnection);
									}
								});
			
			connection = soapConnFactory.createConnection();
			// 响应消息
			SOAPMessage reply = connection.call(request, endpoint);
			
			// 设置字符编码
			// reply.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
			return reply;
		}
		catch(Exception ex){
			throw ex;
		} 
		finally {
			if(connection != null)
				connection.close();
		}
	}

	/**
	 * 根据配置模板调用webservice.<br>
	 * 
	 * @param variables
	 * @param document
	 *            符合规范的json
	 * @throws Exception 
	 */
	public static void invoke(Map variables, JSONArray jArray) throws Exception {
		if (jArray.size() == 0) {
			logger.warn("没有找到webservice的调用配置.", jArray);
			return;
		}
		try{
			//遍历所有的webservice调用
			for(Object obj:jArray){
				JSONObject jObject = (JSONObject)obj;
				JSONArray inputs = jObject.getJSONArray("inputs");
				JSONArray outputs = jObject.getJSONArray("outputs");
				String url = jObject.getString("url");
				String namespace = jObject.getString("namespace");
				String method = jObject.getString("method");
				if(StringUtil.isEmpty(url)||StringUtil.isEmpty(namespace)||StringUtil.isEmpty(method)){
					logger.warn("没有获取到webservice的调用地址、名称空间或调用方法.", jObject);			
					continue;
				}
				SOAPMessage requestMessage = RequestBuilder.build(inputs,namespace,method,variables);
				Long t1 = System.currentTimeMillis();
				// 调用
				SOAPMessage responseMessage = invoke(new URL(url), requestMessage);
				
				Long t2 = System.currentTimeMillis();
				
				System.out.println("[获取返回值所用时间为:]"+(t2-t1)+"毫秒");
				// 构造response
				ResponseBuilder.build(variables, outputs, responseMessage);
			}
		}catch (Exception e) {
			logger.error("调用webservice出错.", e);
			throw e;
		}
	}

	public static void main(String[] args) throws Exception {
		// 准备模拟数据
		Map map = new HashMap();		
		map.put("a", new PageBean(100, 100));
		map.put("abc", 10);
		
		String jsonStr = "[{\"url\":\"http://172.29.20.111:8080/bpm/service/ProcessService\",\"serviceName\":\"ProcessServiceImplService\",\"method\":\"endProcessByTaskId\",\"namespace\":\"http://api.webservice.platform.hotent.com/\",\"inputs\":[{\"name\":\"arg0\",\"soapType\":\"string\",\"bindingType\":\"3\",\"bindingVal\":\"if(1==1)&nuot;     return &quot;abc:123,,,,.....[}&quot;;\",\"javaType\":\"\"}],\"outputs\":[{\"name\":\"return\",\"soapType\":\"boolean\",\"bindingType\":\"2\",\"bindingVal\":\"abc\",\"javaType\":\"number\"}]}]";
		JSONArray jArray = (JSONArray)JSONArray.fromObject(jsonStr);
		
		// 调用
		invoke(map, jArray);

		// 验证
		System.out.println(map.get("abc"));

	}
}
