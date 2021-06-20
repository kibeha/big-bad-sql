package kibeha.practical;

import java.time.LocalTime;
import java.time.temporal.ChronoField;

import org.hibernate.Session;

// V2

public class ExecPickList {

	public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
/*
        HibernateUtil.beginTrace(session, "V2");
        int startMilli = LocalTime.now().get(ChronoField.MILLI_OF_DAY);
*/
        PickList pickList = new PickList();
        
        pickList.fetchPickLinesFifo(session, 421L);
        
        pickList.sortPickLinesByRoute();
        
        pickList.output();
/*        
        int endMilli = LocalTime.now().get(ChronoField.MILLI_OF_DAY);

        System.out.printf("Millisecs: %d", endMilli - startMilli);
        HibernateUtil.endTrace(session);
        System.out.println();
*/        
        HibernateUtil.shutdown();
	}

}
