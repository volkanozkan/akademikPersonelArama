package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AkademikPersonel
{
	private String personelAdSoyad;
	private String link;
	private String akademikUnvan;
	private String birim;
	private String kadroUnvanı;
	private String egitimBilgileri;
	private String telefon;
	private String email;

	public AkademikPersonel(String personelAdSoyad, String link, String akademikUnvan, String birim, String kadroUnvanı)
	{
		super();
		this.personelAdSoyad = personelAdSoyad;
		this.link = link;
		this.akademikUnvan = akademikUnvan;
		this.birim = birim;
		this.kadroUnvanı = kadroUnvanı;
	}

	public AkademikPersonel()
	{
		super();
	}

	public String getPersonelAdSoyad()
	{
		return personelAdSoyad;
	}

	public void setPersonelAdSoyad(String personelAdSoyad)
	{
		this.personelAdSoyad = personelAdSoyad;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getAkademikUnvan()
	{
		return akademikUnvan;
	}

	public void setAkademikUnvan(String akademikUnvan)
	{
		this.akademikUnvan = akademikUnvan;
	}

	public String getBirim()
	{
		return birim;
	}

	public void setBirim(String birim)
	{
		this.birim = birim;
	}

	public String getKadroUnvanı()
	{
		return kadroUnvanı;
	}

	public void setKadroUnvanı(String kadroUnvanı)
	{
		this.kadroUnvanı = kadroUnvanı;
	}

	public String getEgitimBilgileri()
	{
		return egitimBilgileri;
	}

	public void setEgitimBilgileri(String egitimBilgileri)
	{
		this.egitimBilgileri = egitimBilgileri;
	}

	public String getTelefon()
	{
		return telefon;
	}

	public void setTelefon(String telefon)
	{
		this.telefon = telefon;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public String toString()
	{
		return "AkademikPersonel [personelAdSoyad=" + personelAdSoyad + ", link=" + link + ", akademikUnvan="
				+ akademikUnvan + ", birim=" + birim + ", kadroUnvanı=" + kadroUnvanı + ", egitimBilgileri="
				+ egitimBilgileri + ", telefon=" + telefon + ", email=" + email + "]";
	}

}
