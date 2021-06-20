package kibeha.practical;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
   private static SessionFactory sessionFactory = buildSessionFactory();
   
   private static SessionFactory buildSessionFactory() 
   {
      try
      {
         if (sessionFactory == null) 
         {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                  .configure("hibernate.cfg.xml").build();
             
            Metadata metaData = new MetadataSources(standardRegistry)
                  .getMetadataBuilder()
                  .build();
             
            sessionFactory = metaData.getSessionFactoryBuilder().build();
         }
         return sessionFactory;
      } catch (Throwable ex) {
         throw new ExceptionInInitializerError(ex);
      }
   }
 
   public static SessionFactory getSessionFactory() {
      return sessionFactory;
   }
 
   public static void shutdown() {
      getSessionFactory().close();
   }

	public static void beginTrace(Session session, String id ) {
		String trcFileId = id + "_" + LocalTime.now().get(ChronoField.SECOND_OF_DAY);
		System.out.println(trcFileId);

		session.doWork(conn -> conn.createStatement().execute("alter session set tracefile_identifier='" + trcFileId + "'"));
		session.doWork(conn -> conn.createStatement().execute("alter session set sql_trace = true"));
	}

	public static void endTrace(Session session) {
		session.doWork(conn -> conn.createStatement().execute("alter session set sql_trace = false"));
	}
}
