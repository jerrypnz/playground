package com.jstudio.jrestoa.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.jstudio.jrestoa.domain.Department;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.Supervisor;
import com.jstudio.jrestoa.hbm.InTransaction;

public class SystemInit
{

	private static Log log = LogFactory.getLog(SystemInit.class);
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		log.debug("about to generate database schema.");
		Configuration config =  new AnnotationConfiguration()
				.configure();
		SchemaExport export = new SchemaExport(config);
		export.create(true, true);
		prepareData();
		log.debug("exported");
	}
	
	public static void prepareData()
	{
		Employee.doInTransaction(new InTransaction(){

			@Override
			public void execute() throws Exception
			{
				log.debug("preparing initial data");
				Department dep1 = new Department();
				dep1.setName("程序组");
				
				Department dep2 = new Department();
				dep2.setName("美术组");
				
				Department dep3 = new Department();
				dep3.setName("策划组");
				
				Department dep4 = new Department();
				dep4.setName("测试组");
				
				Supervisor sup1 = new Supervisor();
				sup1.setLoginName("root");
				sup1.setLoginPassword("root");
				sup1.setName("Jerry");
				sup1.setDepartment(dep1);
				dep1.setSupervisor(sup1);
				dep1.save();
				sup1.save();
				
				Supervisor sup2 = new Supervisor();
				sup2.setLoginName("admin");
				sup2.setLoginPassword("admin");
				sup2.setName("Jessica");
				sup2.setDepartment(dep2);
				dep2.setSupervisor(sup2);
				dep2.save();
				sup2.save();
				
				Supervisor sup3 = new Supervisor();
				sup3.setLoginName("leader");
				sup3.setLoginPassword("leader");
				sup3.setName("Jerry");
				sup3.setDepartment(dep3);
				dep3.setSupervisor(sup3);
				dep3.save();
				sup3.save();
				
				Supervisor sup4 = new Supervisor();
				sup4.setLoginName("hello");
				sup4.setLoginPassword("hello");
				sup4.setName("Jerry");
				sup4.setDepartment(dep4);
				dep4.setSupervisor(sup4);
				dep4.save();
				sup4.save();
			}

			@Override
			public void onException(Throwable e) throws Exception
			{
				log.error("error creating initial data.",e);
			}
			
		});
	}

}
