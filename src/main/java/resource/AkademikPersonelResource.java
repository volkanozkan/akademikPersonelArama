package resource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import serviceAndJOB.ParseKisiselBilgiler;
import model.AkademikPersonel;
import dao.DAOManager;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("personel")
public class AkademikPersonelResource
{

	DAOManager dao = new DAOManager();
	ParseKisiselBilgiler parseK = new ParseKisiselBilgiler();

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_XML + ";charset=utf-8")
	public ArrayList<AkademikPersonel> getUserDetailsFromAddress(@QueryParam("name") String name)
			throws SQLException, IOException
	{
		return parseK.KisiselBilgiler(dao.search(name));
	}
}
