/**
 * 
 */
package com.hotent.core.soap.type;

import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.w3c.dom.Node;

/**
 * @author wwz
 * 
 */
@SuppressWarnings("rawtypes")
abstract class BaseSoapType implements SoapType {

	@Override
	public abstract Class<?>[] getBeanTypes();

	@Override
	public abstract String[] getSoapTypes();

	/**
	 * 获取默认类型
	 * 
	 * @return
	 */
	private final Class<?> getDefaultClass() {
		Class[] klasses = getBeanTypes();
		if (klasses == null || klasses.length == 0) {
			return Object.class;// 默认值
		}

		return klasses[0];
	}

	abstract Object convertCurrent(Class<?> klass, SOAPElement element);

	@SuppressWarnings("unchecked")
	@Override
	public final Object convertToBean(Class<?> klass, SOAPElement... elements) throws SOAPException {
		if (elements == null || elements.length < 1) {
			return null;
		}

		if (elements.length > 1) {
			List list = new ArrayList();
			for (SOAPElement element : elements) {
				Object obj = convertCurrent(klass, element);
				list.add(obj);
			}
			return list;
		} else {
			return convertCurrent(klass, elements[0]);
		}
	}

	abstract void setCurrentValue(SOAPElement element, Object obj, Class<?> klass);

	@Override
	public final void setValue(SOAPElement element, Object obj, Class<?> klass) throws SOAPException {
		if (obj == null) {
			return;
		}

		if (obj instanceof List || obj instanceof ArrayList) {// 处理数组类型
			List list = (List) obj;
			if (list.size() == 0) {
				return;
			} else if (list.size() == 1) {
				setCurrentValue(element, list.get(0), klass);
			} else {
				for (Object vo : list) {
					Node newNode = element.cloneNode(true);
					SOAPElement newElement = element.getParentElement().addChildElement((SOAPElement) newNode);
					setCurrentValue(newElement, vo, klass);
				}
			}
		} else {// 处理普通类型
			setCurrentValue(element, obj, klass);
		}
	}

	@Override
	public final Object convertToBean(SOAPElement... elements) throws SOAPException {
		return convertToBean(getDefaultClass(), elements);
	}

	@Override
	public final void setValue(SOAPElement element, Object obj) throws SOAPException {
		setValue(element, obj, getDefaultClass());
	}

}
