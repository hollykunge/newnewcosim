/**
 * 
 */
package com.hotent.core.soap.type;

import java.lang.reflect.Field;

import javax.xml.soap.SOAPElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NodeList;

/**
 * @author wwz
 * 
 */
public class BeanSoapType extends BaseSoapType {
	
	private static Log logger = LogFactory.getLog(BaseSoapType.class);

	@Override
	public Class<?>[] getBeanTypes() {
		return new Class[] { Object.class };
	}

	@Override
	public String[] getSoapTypes() {
		return new String[] { "anyType" };
	}

	@SuppressWarnings("rawtypes")
	@Override
	void setCurrentValue(SOAPElement element, Object obj, Class<?> klass) {
		// 通过反射设置obj的值
		for (Field field : klass.getDeclaredFields()) {
			field.setAccessible(true);
			Class fieldType = field.getType();// 获取字段类型
			String fieldName = field.getName();// 字段名
			NodeList fieldNodeList = element.getElementsByTagName(fieldName);
			if (fieldNodeList == null || fieldNodeList.getLength() < 1) {// webservice没有该字段,不需要拷贝
				continue;
			}

			try {
				SoapTypes.getTypeByBean(fieldType).setValue((SOAPElement) fieldNodeList.item(0), field.get(obj),
						fieldType);
			} catch (Exception e) {
				// 设置失败,跳过.
				logger.warn("字段[" + fieldName + "]设置失败.", e);
				continue;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	Object convertCurrent(Class<?> klass, SOAPElement element) {

		Object bean;
		try {
			bean = klass.newInstance();
		} catch (Exception e) {
			logger.error("类别[" + klass + "]无法实例化.", e);
			return null;
		}

		for (Field field : klass.getDeclaredFields()) {
			field.setAccessible(true);
			Class fieldType = field.getType();// 获取字段类型
			String fieldName = field.getName();// 字段名
			NodeList fieldNodeList = element.getElementsByTagName(fieldName);
			if (fieldNodeList == null || fieldNodeList.getLength() < 1) {// webservice没有该字段,不需要拷贝
				continue;
			}
			try {

				Object obj = SoapTypes.getTypeByBean(fieldType).convertToBean(fieldType, element);
				field.set(bean, obj);
			} catch (Exception e) {
				// 设置失败,跳过.
				logger.warn("字段[" + fieldName + "]设置失败.", e);
				continue;
			}
		}

		return bean;
	}

}
