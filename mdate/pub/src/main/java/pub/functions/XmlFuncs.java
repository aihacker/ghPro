package pub.functions;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-1-3
 */
public class XmlFuncs {
	private static final DocumentBuilderFactory _docBuilderFactory;

	static {
		//System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
		_docBuilderFactory = DocumentBuilderFactory.newInstance();//
	}

	private static final TransformerFactory _transFactory = TransformerFactory.newInstance();

	public static Document documentFromString(String xml) throws Exception {
		DocumentBuilder db = _docBuilderFactory.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(xml)));
		return doc;
	}
	public static String documentToString(Document doc) throws
														ClassNotFoundException,
														IllegalAccessException,
														InstantiationException,
														TransformerException,
														IOException {
		Transformer trans = _transFactory.newTransformer();
		StringWriter sw = new StringWriter();
		trans.transform(new DOMSource(doc), new StreamResult(sw));
		sw.close();
		//info: special treatment
		StringBuffer sb = sw.getBuffer();
		int pos = sb.indexOf("UTF-8");
		if (pos > 0) {
//			sb.replace(pos, pos + "UTF-8".length(), "GBK");
		}
		return sw.getBuffer().toString();
	}

	public static Document documentFromFile(File file) throws Exception {
		DocumentBuilder db = _docBuilderFactory.newDocumentBuilder();
		//Document doc = db.parse(new InputSource(new FileReader(file)));
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		Document doc = db.parse(new InputSource(is));
		is.close();
		return doc;
	}

    public static Document documentFromStream(InputStream is) throws Exception {
        DocumentBuilder db = _docBuilderFactory.newDocumentBuilder();
        Document doc = db.parse(new InputSource(is));
        return doc;
    }

	public static Document createDocument() throws Exception {
		DocumentBuilder db = _docBuilderFactory.newDocumentBuilder();
		return db.newDocument();
	}

	public static Document documentFromURL(URL url) throws Exception {
		DocumentBuilder db = _docBuilderFactory.newDocumentBuilder();
		Document doc = db.parse(url.openStream());
		return doc;
	}

	public static Node duplicateNode(Document destDoc, Node srcNode) {
		Node destNode = null;
		switch (srcNode.getNodeType()) {
		case Node.ELEMENT_NODE:
			destNode = destDoc.createElement(srcNode.getNodeName());
			break;
		case Node.TEXT_NODE:
			destNode = destDoc.createTextNode(srcNode.getNodeValue());
			break;
		case Node.CDATA_SECTION_NODE:
			destNode = destDoc.createCDATASection(srcNode.getNodeValue());
			break;
		case Node.ATTRIBUTE_NODE:
			destNode = destDoc.createAttribute(srcNode.getNodeName());
			//destNode.setNodeValue(srcNode.getNodeValue());
			break;
		default:
			assert (false);
		}
		NamedNodeMap attrs = srcNode.getAttributes();
		if (attrs != null) {
			for (int n = 0; n < attrs.getLength(); n++) {
				Node attr = duplicateNode(destDoc, attrs.item(n));
				destNode.getAttributes().setNamedItem(attr);
			}
		}
		NodeList childs = srcNode.getChildNodes();
		for (int n = 0; n < childs.getLength(); n++) {
			Node nd = duplicateNode(destDoc, childs.item(n));
			destNode.appendChild(nd);
		}
		return destNode;
	}

	public static void documentToFile(Document doc, String fileName) throws
																	 IOException,
																	 TransformerException,
																	 ClassNotFoundException,
																	 InstantiationException,
																	 IllegalAccessException {
		FileWriter out = new FileWriter(fileName);
		out.write(documentToString(doc));
		out.close();
	}
}
