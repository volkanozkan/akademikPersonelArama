package serviceAndJOB;

import java.sql.SQLException;
import java.util.ArrayList;

import model.AkademikPersonel;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dao.DAOManager;

public class XMLConverter
{

	DAOManager dao = new DAOManager();

	ArrayList<AkademikPersonel> akademikPersonel = new ArrayList<>();
	String personelAdSoyad, link, akademikUnvan, birim, kadroUnvanı;

	public void element(DomParserXML dp) throws SQLException
	{
		int limitOfInsertForOneConnection = 1;
		dao.connection();

		AkademikPersonel ap = new AkademikPersonel(personelAdSoyad, link, akademikUnvan, birim, kadroUnvanı);

		NodeList nlist = dp.getDoc().getElementsByTagName("tr");
		for (int c = 0; c < nlist.getLength(); c++)
		{

			ap = new AkademikPersonel(personelAdSoyad, link, akademikUnvan, birim, kadroUnvanı);

			Node node = nlist.item(c);

			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element) node;
				element.getAttribute("onclick");
				String link = element.getAttribute("onclick");
				String text = link.substring(22, 84);
				System.out.print("****\n" + text);

				ap.setLink(text.trim());

				element.getFirstChild().getTextContent();

				System.out.println();
				NodeList nl = element.getChildNodes();

				for (int i = 0; i < nl.getLength(); i++)
				{
					Node node2 = nl.item(i);
					if (i % 2 == 1)
					{
						Element element2 = (Element) node2;
						System.out.println(element2.getTextContent());

						ap.setAkademikUnvan(nl.item(3).getTextContent().trim());
						ap.setBirim(nl.item(5).getTextContent().trim());
						ap.setKadroUnvanı(nl.item(7).getTextContent().trim());
						ap.setPersonelAdSoyad(nl.item(1).getTextContent().trim());

					}
				}
			}
			akademikPersonel.add(ap);
		}

		// for (AkademikPersonel aka : akademikPersonel) {
		for (int i = 0; i < akademikPersonel.size(); i++)
		{
			System.out.println(akademikPersonel.get(i));

			dao.insertRow(akademikPersonel.get(i).getPersonelAdSoyad(), akademikPersonel.get(i).getLink(),
					akademikPersonel.get(i).getAkademikUnvan(), akademikPersonel.get(i).getBirim(),
					akademikPersonel.get(i).getKadroUnvanı());

			limitOfInsertForOneConnection++;
			if (limitOfInsertForOneConnection == 250)
			{
				dao.connection();
				limitOfInsertForOneConnection = 0;
			}
		}
	}
}
