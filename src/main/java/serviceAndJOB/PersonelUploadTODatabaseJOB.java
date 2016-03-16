package serviceAndJOB;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import dao.DAOManager;

public class PersonelUploadTODatabaseJOB implements Job
{
	DAOManager dao = new DAOManager();

	String x = "";
	String[] aToZKadarGidilecekSiteKoduYedek = new String[]
	{ "A", "B", "C", "%C7", "D", "E", "F", "G", "H", "I", "%DD", "J", "K", "L", "M", "N", "O", "%D6", "P", "R", "S",
			"%DE", "T", "U", "%DC", "V", "Y", "Z" };

	int hangiHarf = 0;

	ArrayList<String> aToZKadarGidilecekSiteKodu = new ArrayList<>(Arrays.asList(aToZKadarGidilecekSiteKoduYedek));

	public void execute(JobExecutionContext context) throws JobExecutionException
	{

		dao.connection();
		dao.deleteRecords();
		dao.connection();
		//// A DAN Z YE KADAR TUM HARFLERI DOLASIP HEPSINI PERSONELLERINI PARSE
		//// EDIP DATABASE INSERT YAPMAK(XML CONVERTER CLASSI YAPIYOR INSERT).
		do
		{
			Document doc = null;
			try
			{
				doc = Jsoup.connect("http://www.deu.edu.tr/akademiktr/index.php?cat=2&harf="
						+ aToZKadarGidilecekSiteKodu.get(hangiHarf)).get();

			}
			catch (IOException e)
			{

				e.printStackTrace();
			}

			Elements table = doc.select("table");
			Elements row = table.select("tr");

			System.out.println(row.size() + "*******************************");

			StringBuffer sb = new StringBuffer();

			for (int j = 0; j < row.size() - 13; j++)
			{
				Element row2 = table.select("tr").get(12 + j);

				sb.append(row2);
				x = sb.toString();

				if (x.contains("&nbsp;"))
				{
					x = x.replaceAll("&nbsp;", "&#160;");
					sb = new StringBuffer();
					sb.append(x);
				}

			}

			// create xml file

			File file = new File("akademikPersoneller.xml");

			System.out.println(file.getAbsolutePath());
			PrintWriter writer = null;
			try
			{
				writer = new PrintWriter(file, "UTF-8");
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}

			writer.println("");
			writer.println("<html>");
			writer.println(sb.toString());
			writer.println("</html>");
			writer.close();

			DomParserXML dp11 = new DomParserXML();
			dp11.parse(file);

			XMLConverter converter = new XMLConverter();
			try
			{
				converter.element(dp11);
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			aToZKadarGidilecekSiteKodu.remove(hangiHarf);
		} while (!aToZKadarGidilecekSiteKodu.isEmpty());
	}
}