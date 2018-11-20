package pub.domx;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pub.beans.BeanUtilsBeanEx;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2009-1-11
 * Time: 23:09:29
 */
public class XElement {

	public Document document;
	public Element element;

	public XElement() {
	}

	public XElement(Element element) {
		this.element = element;
	}

	public XElement(Document document, Element element) {
		this.document = document;
		this.element = element;
	}
	public XElement appendChild(XElement childElement) {
		return new XElement(document, (Element) element.appendChild(childElement.element));
	}
	public XElement appendChild(String name) {
		return appendChild(name, null);
	}
	public XElement appendChild(String name, String content) {
		Element child = document.createElement(name);
		if (content != null) {
			child.setTextContent(content);
		}
		return new XElement(document, (Element) element.appendChild(child));
	}
	public void appendChildren(XElement... children) {
		for (XElement xChild : children) {
			element.appendChild(xChild.element);
		}
	}
	public XElement getFirstChild() {
		Node child = element.getFirstChild();
		if (child == null) {
			return null;
		}
		Element childElement = null;
		if (child.getNodeType() == Node.ELEMENT_NODE) {
			childElement = (Element) child;
		}
		else {
			NodeList nodes = element.getChildNodes();
			for (int n = 0; n < nodes.getLength(); n++) {
				if (nodes.item(n).getNodeType() == Node.ELEMENT_NODE) {
					childElement = (Element) nodes.item(n);
					break;
				}
			}
		}
		if (childElement == null) {
			return null;
		}
		return new XElement(document, childElement);
	}
	public XElement getNextSibling() {
		Node nextNode = element.getNextSibling();
		while (nextNode != null) {
			if (nextNode.getNodeType() == Node.ELEMENT_NODE) {
				return new XElement(document, (Element) nextNode);
			}
			nextNode = nextNode.getNextSibling();
		}
		return null;
	}
	public XElement getPrevSibling() {
		Node prevNode = element.getPreviousSibling();
		while (prevNode != null) {
			if (prevNode.getNodeType() == Node.ELEMENT_NODE) {
				return new XElement(document, (Element) prevNode);
			}
			prevNode = prevNode.getPreviousSibling();
		}
		return null;
	}
	public XElement getParent() {
		Node parentNode = element.getParentNode();
		if (parentNode == null) {
			return null;
		}
		if (parentNode.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}
		return new XElement(document, (Element) parentNode);
	}
	public XElement getChild(String name) {
		NodeList nodes = element.getElementsByTagName(name);
		if (nodes.getLength() != 1) {
			return null;
		}
		return new XElement(document, (Element) nodes.item(0));
	}

	public String getChildText(String childName, String defaultValue) {
        String result = getChildText(childName);
        if(result == null) {
            result = defaultValue;
        }
        return result;
    }

	public String getChildText(String childName) {
		XElement child = getChild(childName);
		if(child == null) {
            return null;
        }
		return child.getText();
	}
	public List<XElement> getChildren(String name) {
		ArrayList<XElement> children = new ArrayList<XElement>();
		NodeList nodes = element.getElementsByTagName(name);
		for (int n = 0; n < nodes.getLength(); n++) {
			children.add(new XElement(document, (Element) nodes.item(n)));
		}
		return children;

	}
	public String getText() {
		return element.getTextContent();
	}

	public String getName() {
		//return element.getTagName();
		return element.getLocalName();
	}
	//
	private static Map<String, PropertyDescriptor> buildObjectPropertyMap(Object obj) {
		Map<String, PropertyDescriptor> result = new HashMap<String, PropertyDescriptor>();
		PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(obj);
		for (PropertyDescriptor propDesc : propDescs) {
			result.put(propDesc.getName().toUpperCase(), propDesc);
		}
		return result;
	}
	private static List<Element> getChildElements(Element element) {
		List<Element> result = new ArrayList<Element>();
		NodeList childNodes = element.getChildNodes();
		for (int n = 0; n < childNodes.getLength(); n++) {
			Node childNode = childNodes.item(n);
			if (childNode.getNodeType() == Node.ELEMENT_NODE) {
				result.add((Element) childNode);
			}
		}
		return result;
	}

	private static void populate(Object obj, Element element) throws
															  InvocationTargetException,
															  IllegalAccessException,
															  InstantiationException {
		Map<String, PropertyDescriptor> propDescMap = buildObjectPropertyMap(obj);
		List<Element> chileElems = getChildElements(element);
		for (int n = 0; n < chileElems.size(); n++) {
			Element childElem = (Element) chileElems.get(n);
			String name = childElem.getTagName().toUpperCase();
			PropertyDescriptor propDesc = propDescMap.get(name);
			if (propDesc == null) {
				continue;
			}
			Class<?> propType = propDesc.getPropertyType();
			String typeName = propType.getName();
			Object propValue = propDesc.getReadMethod().invoke(obj);
			if (propType.isArray()) {
				propValue = populateArray(propType.getComponentType(), childElem);
			}
			else if (!typeName.startsWith("java")) {
				if (propValue == null) {
					propValue = propType.newInstance();
				}
				populate(propValue, childElem);
			}
			else {
				ConvertUtilsBean convertUtils = BeanUtilsBeanEx.getInstance().getConvertUtils();
				propValue = convertUtils.convert(childElem.getTextContent(), propType);
			}
			propDesc.getWriteMethod().invoke(obj, propValue);
		}
	}

	private static <T> T[] populateArray(Class<T> clazz, Element element) throws
																		  InvocationTargetException,
																		  InstantiationException,
																		  IllegalAccessException {
		List<Element> childElements = getChildElements(element);
		T[] result = (T[]) Array.newInstance(clazz, childElements.size());
		for (int n = 0; n < childElements.size(); n++) {
			result[n] = clazz.newInstance();
			populate(result[n], childElements.get(n));
		}
		return result;
	}
	public static <T> List<T> populateList(Class<T> clazz, Element element) throws
																			IllegalAccessException,
																			InstantiationException,
																			InvocationTargetException {
		List<T> result = new ArrayList<T>();
		List<Element> childElements = getChildElements(element);
		for (Element childElem : childElements) {
			T item = clazz.newInstance();
			populate(item, childElem);
			result.add(item);
		}
		return result;
	}
	public void populate(Object obj) {
		try {
			populate(obj, element);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public <T> List<T> populateList(Class<T> clazz) {
		try {
			return populateList(clazz, element);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//
	private Element serialize(Object obj, String name) throws
													   InvocationTargetException,
													   IllegalAccessException {
		if (obj == null) {
			return null;
		}
		Element element = document.createElement(name);
		Class cls = obj.getClass();
		String clsName = cls.getName();
		if (!clsName.startsWith("java")) {
			PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(obj);
			for (PropertyDescriptor propDesc : propDescs) {
				Class<?> propType = propDesc.getPropertyType();
				String propName = propDesc.getName();
				if (propName.equals("class")) {
					continue;
				}
				propName = propName.substring(0, 1).toUpperCase() + propName.substring(1);
				Object propValue = propDesc.getReadMethod().invoke(obj);
				Element childElement = serialize(propValue, propName);
				if (childElement != null) {
					element.appendChild(childElement);
				}
			}
		}
		else if (cls.isArray()) {
			System.out.println("??");
		}
		else {
			ConvertUtilsBean convertUtils = BeanUtilsBeanEx.getInstance().getConvertUtils();
			String text = convertUtils.convert(obj);
			element.setTextContent(text);
		}
		return element;
	}
	public <T> void serializeList(List<T> list, String elementName) {
		try {
			for (T item : list) {
				Element childElem = serialize(item, elementName);
				if (childElem != null) {
					element.appendChild(childElem);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public XElement setText(String text) {
		this.element.appendChild(this.document.createTextNode(text));
		return this;
	}

	public String getAttr(String name) {
        return this.element.getAttribute(name);
    }

	public void setAttr(String name, String value) {
		this.element.setAttribute(name, value);
	}

	public boolean hasAttr() {
		return this.element.getAttributes().getLength() != 0;
	}

	public boolean hasChild() {
		return this.element.hasChildNodes();
	}
}
