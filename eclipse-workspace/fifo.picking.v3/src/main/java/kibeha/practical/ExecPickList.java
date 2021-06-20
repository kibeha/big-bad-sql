package kibeha.practical;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

// V3

public class ExecPickList {

	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		
		try
		{
			conn = DataSourceUtil.getConnection();
			DataSourceUtil.beginTrace(conn, "V3");
	        int startMilli = LocalTime.now().get(ChronoField.MILLI_OF_DAY);

	        PickList pickList = new PickList();
	        
	        pickList.fetchByFifo(conn, 421L);
	        
	        pickList.output();
	        
	        int endMilli = LocalTime.now().get(ChronoField.MILLI_OF_DAY);
	        System.out.printf("Millisecs: %d", endMilli - startMilli);
	        DataSourceUtil.endTrace(conn);
	        System.out.println();
		}
		finally
		{
			if (conn != null) conn.close();
	        DataSourceUtil.shutdown();
		}
        
	}

}
