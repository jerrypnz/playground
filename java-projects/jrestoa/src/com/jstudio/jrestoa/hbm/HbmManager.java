package com.jstudio.jrestoa.hbm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;


public class HbmManager
{
	/** 
     * Location of hibernate.cfg.xml file.
     * Location should be on the classpath as Hibernate uses  
     * #resourceAsStream style lookup for its configuration file. 
     * The default classpath location of the hibernate config file is 
     * in the default package. Use #setConfigFile() to update 
     * the location of the configuration file for the current session.   
     */
    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private  static Configuration configuration = new AnnotationConfiguration();
    private static org.hibernate.SessionFactory sessionFactory;
    private static String configFile = CONFIG_FILE_LOCATION;

	static {
    	try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
    }
	
    private HbmManager() {
    }
	
	/**
     * Returns the ThreadLocal Session instance.  Lazy initialize
     * the <code>SessionFactory</code> if needed.
     *
     *  @return Session
     *  @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocal.set(session);
		}

        return session;
    }

	/**
     *  Rebuild hibernate session factory
     *
     */
	public static void rebuildSessionFactory() {
		try {
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			System.err
					.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
     *  Close the single hibernate session instance.
     *
     *  @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }

	/**
     *  return session factory
     *
     */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
     *  return session factory
     *
     *	session factory will be rebuilded in the next call
     */
	public static void setConfigFile(String configFile) {
		HbmManager.configFile = configFile;
		sessionFactory = null;
	}

	/**
     *  return hibernate configuration
     *
     */
	public static Configuration getConfiguration() {
		return configuration;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Object get(Class entityClass, Serializable id)
	{
		return getSession().get(entityClass,id);
	}

	@SuppressWarnings("unchecked")
	public static List listAll(Class entityClass)
	{
		Query query = getSession().createQuery("from " + entityClass.getSimpleName());
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public static List query(Class entityClass,String hql)
	{
		Query query = getSession().createQuery("from " + entityClass.getSimpleName() + " where " + hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public static List query(Class entityClass,String hql,Map<String,Object> params)
	{
		Query query = getSession().createQuery("from " + entityClass.getSimpleName() + " where " + hql);
		for(String paramName: params.keySet())
		{
			query.setParameter(paramName, params.get(paramName));
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public static List query(Class entityClass,String hql,Object[] params)
	{
		Query query = getSession().createQuery("from " + entityClass.getSimpleName() + " where " + hql);
		for(int i=0;i<params.length;i++)
		{
			query.setParameter(i, params[i]);
		}
		return query.list();
	}
}
