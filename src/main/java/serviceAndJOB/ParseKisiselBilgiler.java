package serviceAndJOB;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.AkademikPersonel;

public class ParseKisiselBilgiler
{
	public ArrayList<AkademikPersonel> KisiselBilgiler(ArrayList<AkademikPersonel> akademikPersonel) throws IOException
	{

		for (AkademikPersonel akaPersonel : akademikPersonel)
		{
			AkademikPersonel aka = akaPersonel;

			if ((aka.getLink() != null))
			{

				URL url = new URL(aka.getLink());
				Document doc = Jsoup.parse(url, 3 * 1000);
				String text = doc.body().text();

				String[] words = text.split("KİŞİSEL SAYFALAR");
				String[] wordss = null;

				String newWord = words[1];
				wordss = newWord.split("E-Posta : ");
				String isimFakulteVeBirim = "Kişisel Bilgiler: " + wordss[0].trim();
				System.out.println(isimFakulteVeBirim);

				newWord = wordss[1];
				wordss = newWord.split("Telefon : ");
				String eMail = "E-Posta:" + wordss[0].trim();
				System.out.println(eMail);

				newWord = wordss[1];
				wordss = newWord.split("Eğitim / Akademik Bilgisi ");
				String telefon = "Telefon:" + wordss[0].trim();
				System.out.println(telefon);

				Elements trs = doc.select("table");
				Element a = trs.select("tbody").get(5);
				Element aa = null;
				int sizeOfTr = a.select("tr").size();

				StringBuilder sbEgitimBilgileri = new StringBuilder();
				String replace = "";

				int println = 0;

				for (int i = 1; i < sizeOfTr; i++)
				{
					aa = a.select("tr").get(i);
					for (int k = 0; k < 3; k++)
					{
						sbEgitimBilgileri.append(aa.select("td").get(k));
						replace = sbEgitimBilgileri.toString();
						replace = replace.replaceAll("<td>", "");
						replace = replace.replaceAll("</td>", "");
						replace = replace.replaceAll("&nbsp;", " ");
						sbEgitimBilgileri = new StringBuilder();
						sbEgitimBilgileri.append(replace);
						println++;
						if (println == 3)
						{
							sbEgitimBilgileri.append("\n");
							println = 0;
						}
					}
				}
				System.out.println(sbEgitimBilgileri);

				String egitim = sbEgitimBilgileri.toString();
				aka.setEmail(eMail);
				aka.setTelefon(telefon);
				aka.setEgitimBilgileri(egitim);
				System.out.println(aka);
			}
			else
			{
				return akademikPersonel;
			}
		}
		return akademikPersonel;

	}
}
