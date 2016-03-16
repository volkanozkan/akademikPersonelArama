package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.AkademikPersonel;

public class DAOManager
{
	Connection con = null;
	Statement stmt = null;

	public void insertRow(String personelAdSoyad, String link, String akademikUnvan, String birim, String kadroUnvanı)
			throws SQLException
	{

		String sql = "INSERT INTO MOBIL_AKADEMIKPERSONELARAMA (PERSONELADSOYAD, LINK, AKADEMIKUNVAN, BIRIM, KADROUNVANI) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, personelAdSoyad);
		pstmt.setString(2, link);
		pstmt.setString(3, akademikUnvan);
		pstmt.setString(4, birim);
		pstmt.setString(5, kadroUnvanı);

		pstmt.executeUpdate();
	}

	public ArrayList<AkademikPersonel> search(String personel) throws SQLException
	{

		AkademikPersonel aka = new AkademikPersonel();

		ArrayList<AkademikPersonel> akaList = new ArrayList<>();

		connection();

		System.out.println(personel + " Girilen Yazı");

		if (personel.contains("i"))
		{
			personel = personel.replaceAll("i", "İ");
		}

		String aramaYapılanPersonel = personel.toUpperCase();

		if (aramaYapılanPersonel.contains(" "))
		{
			// aramaYapılanPersonel = convertName(aramaYapılanPersonel);
			aka.setPersonelAdSoyad("Lütfen Sadece Aradığınız Personelin Soyadını Giriniz.");
			akaList.add(aka);
			return akaList;
		}

		if (aramaYapılanPersonel.length() == 1)
		{
			aka.setPersonelAdSoyad("Lütfen Aradığınız Personelin Soyadını Giriniz.");
			akaList.add(aka);
			return akaList;
		}

		aramaYapılanPersonel = aramaYapılanPersonel.trim();

		System.out.println(aramaYapılanPersonel + " Aranan Yazı");
		String name = "";
		String link = "";
		String birim = "";
		String akademikUnvan = "";
		String kadroUnvanı = "";

		String jsql = "select * FROM MOBIL_AKADEMIKPERSONELARAMA WHERE PERSONELADSOYAD LIKE ?";

		PreparedStatement pstmt = con.prepareStatement(jsql);

		pstmt = con.prepareStatement(jsql);
		// pstmt.setString(1, "%" + aramaYapılanPersonel + "%");
		pstmt.setString(1, aramaYapılanPersonel + " " + "%");

		ResultSet resultset = pstmt.executeQuery();

		if (resultset.next())
		{
			do
			{
				aka = new AkademikPersonel();

				name = resultset.getString("PERSONELADSOYAD");
				link = resultset.getString("LINK");
				birim = resultset.getString("BIRIM");
				akademikUnvan = resultset.getString("AKADEMIKUNVAN");
				kadroUnvanı = resultset.getString("KADROUNVANI");

				aka.setPersonelAdSoyad(name);
				aka.setBirim(birim);
				aka.setAkademikUnvan(akademikUnvan);
				aka.setKadroUnvanı(kadroUnvanı);
				aka.setLink(link);
				akaList.add(aka);
				System.out.println(akaList + "*-*");

			} while (resultset.next());
		}
		else
		{
			System.out.println("Personel Bulunamadı");
			System.out.println(name + "*");
			aka.setPersonelAdSoyad("Personel Bulunamadı.. Lütfen Sadece Aradığınız Personelin Soyadını Giriniz.");
			akaList.add(aka);
		}
		return akaList;
	}

	public void deleteRecords()
	{
		try
		{
			stmt = con.createStatement();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		String sql = "DELETE FROM MOBIL_AKADEMIKPERSONELARAMA ";

		try
		{
			stmt.executeUpdate(sql);
			System.out.println("Tüm Kayıtlar Database den Başarı ile Silindi..");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void connection()
	{

		String url = "***db url***";
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			con = DriverManager.getConnection(url, "***", "***");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		try
		{
			stmt = con.createStatement();
			System.out.println("**db baglantısı basarılı**");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}