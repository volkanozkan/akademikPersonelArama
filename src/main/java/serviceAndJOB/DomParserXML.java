package serviceAndJOB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import oracle.xml.jaxp.JXDocumentBuilder;
import oracle.xml.jaxp.JXDocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomParserXML
{

	private DocumentBuilderFactory factory;
	private DocumentBuilder documentBuilder;
	private Document doc;
	private File sourceFile;
	private Boolean isParsed = false;

	public DomParserXML()
	{
		try
		{
			factory = JXDocumentBuilderFactory.newInstance();
			documentBuilder = factory.newDocumentBuilder();

		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
	}

	public Document getDoc()
	{
		return doc;
	}

	public void parse(File file)
	{
		sourceFile = file;
		try
		{
			doc = documentBuilder.parse(sourceFile);

		}
		catch (IOException | SAXException e)
		{
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();
		isParsed = true;
	}

	// Search a node in all document acording to its tagname and one attribute.
	// Checking its parent is optional.
	public Node findNode(String tagName, String attributeName, String attributeValue, String pTagName,
			String pAttributeName, String pAttributeValue)
	{
		Node rNode = null;
		if (isParsed != null)
		{
			NodeList nlist = doc.getElementsByTagName(tagName);

			for (int c = 0; c < nlist.getLength(); c++)
			{
				Node node = nlist.item(c);

				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;
					if (!attributeName.equalsIgnoreCase(""))
					{
						if (element.getAttribute(attributeName).trim().equalsIgnoreCase(attributeValue))
						{
							if (!pAttributeName.equalsIgnoreCase("") || !pAttributeValue.equalsIgnoreCase("")
									|| !pTagName.equalsIgnoreCase(""))
							{
								Element parentElement = (Element) element.getParentNode();
								if (parentElement.getNodeName().trim().equals(pTagName)
										&& parentElement.getAttribute(pAttributeName).trim().equals(pAttributeValue))
								{
									rNode = node;
									System.out.println("Node bulundu : " + rNode.getNodeName());
									return rNode;

								}
							}
							else
							{
								rNode = node;
								System.out.println("Node bulundu : " + rNode.getNodeName());
								return rNode;
							}
						}
					}
					else
					{
						rNode = node;
						System.out.println("Node bulundu : " + rNode.getNodeName());
						return rNode;
					}
				}
			}
			System.out.println("Node bulunamadı : " + attributeValue);
		}
		return rNode;
	}

	// Search a node under a certain node.
	public Node findChild(String tagName, String attributeName, String attributeValue, String pTagName,
			String pAttributeName, String pAttributeValue)
	{
		Node rNode = null;

		if (isParsed != null)
		{
			Element pElement = (Element) findNode(pTagName, pAttributeName, pAttributeValue, "", "", "");
			NodeList nlist = pElement.getElementsByTagName(tagName);
			for (int c = 0; c < nlist.getLength(); c++)
			{
				Node node = nlist.item(c);

				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;
					if (element.getAttribute(attributeName).trim().equals(attributeValue))
					{
						if (!pAttributeName.equalsIgnoreCase("") || !pAttributeValue.equalsIgnoreCase("")
								|| !pTagName.equalsIgnoreCase(""))
						{
							rNode = node;
							System.out.println("Node bulundu : " + rNode.getNodeName());
							return rNode;
						}
					}
				}
			}
			System.out.println("Node bulunamadı : " + attributeValue);
		}
		return rNode;
	}

	// Add a node under a certain node.
	public Node addChild(Node parentNode, String tagName, String[] attrList, String[] attrValueList)
	{
		Node childNode = null;

		if (isParsed != null)
		{
			childNode = createElement(tagName, attrList, attrValueList);
			if (parentNode == null)
			{
				parentNode = doc.getDocumentElement();
			}
			parentNode.appendChild(childNode);
		}

		return childNode;
	}

	// Add a node under a certain node and before a certain node.
	public Node addChildBefore(Node parentNode, Node afterNode, String tagName, String[] attrList,
			String[] attrValueList)
	{
		Node childNode = null;
		if (isParsed != null)
		{
			childNode = createElement(tagName, attrList, attrValueList);
			if (parentNode == null)
			{
				parentNode = doc.getDocumentElement();
			}
			parentNode.insertBefore(childNode, afterNode);
		}
		return childNode;
	}

	// Create a node object.
	private Node createElement(String tagName, String[] attrList, String[] attrValueList)
	{
		Element element = doc.createElement(tagName);
		for (int c = 0; c < attrList.length; c++)
		{
			element.setAttribute(attrList[c], attrValueList[c]);
		}
		return element;
	}

	// Get all searched attributes for all nodes which has the given tagname in
	// the all document.
	public String[][] getAttributes(String tagName, String[] attributeNames)
	{
		NodeList nlist = doc.getElementsByTagName(tagName);
		String[][] attributeValues = new String[nlist.getLength()][attributeNames.length];

		if (isParsed != null)
		{
			for (int i = 0; i < attributeValues.length; i++)
			{
				Node node = nlist.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;
					for (int j = 0; j < attributeValues[i].length; j++)
					{
						attributeValues[i][j] = element.getAttribute(attributeNames[j]);
					}
				}
			}
		}
		return attributeValues;
	}

	// Save the document.
	public void updateDocument()
	{
		if (isParsed != null)
		{
			try
			{
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer;
				transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new FileOutputStream(sourceFile.getAbsolutePath()));
				transformer.transform(source, result);
			}
			catch (TransformerConfigurationException e)
			{
				e.printStackTrace();
			}
			catch (TransformerException e)
			{
				e.printStackTrace();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void updateDocument(File newFile)
	{

		if (isParsed != false)
		{
			try
			{
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer;
				transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new FileOutputStream(newFile.getAbsolutePath()));
				transformer.transform(source, result);
			}
			catch (TransformerConfigurationException e)
			{
				e.printStackTrace();
			}
			catch (TransformerException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
