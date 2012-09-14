package jerry.c2c.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class SchemaGen
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Configuration cfg = new Configuration().configure();
		new SchemaExport(cfg).create(true, true);
	}

}
