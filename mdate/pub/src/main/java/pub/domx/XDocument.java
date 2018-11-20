package pub.domx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pub.functions.XmlFuncs;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2009-1-11
 * Time: 23:12:18
 */
public class XDocument {

	public Document document;

	public XDocument() {
		try {
			this.document = XmlFuncs.createDocument();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public XDocument(Document document) {
		this.document = document;
	}

	public XDocument(String xml) {
		try {
			document = XmlFuncs.documentFromString(xml);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public XElement createElement(String name) {
		return createElement(name, null);
	}
	public XElement createElement(String name, String content) {
		Element element = document.createElement(name);
		if (content != null) {
			element.setTextContent(content);
		}
		return new XElement(document, element);
	}
	public XElement getRoot() {
		return new XElement(document, document.getDocumentElement());
	}
	public XElement createElementNs(String name, String xmlns) {
		return createElementNs(name, xmlns, null);
	}
	public XElement createElementNs(String name, String xmlns, String content) {
		Element element = document.createElementNS(xmlns, name);
		if (content != null) {
			element.setTextContent(content);
		}
		return new XElement(document, element);
	}
	public void setRoot(XElement element) {
		if (document.getDocumentElement() != null) {
			System.out.println("!!");
		}
		document.appendChild(element.element);
	}

	public String getXml() {
		try {
			String xml = XmlFuncs.documentToString(document);
			return xml;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
