package kibeha.practical;

import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class DataSourceUtil {
	private static OracleDataSource dataSource = buildDataSource();
	
	private static OracleDataSource buildDataSource()
	{
		try
		{
			if (dataSource == null)
			{
				dataSource = new OracleDataSource();
				dataSource.setURL("jdbc:oracle:thin:@192.168.56.103:1521/orcl");
				dataSource.setUser("practical");
				dataSource.setPassword("practical");
			}
			return dataSource;
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static OracleDataSource getDataSource() {
		return dataSource;
	}
 
	public static Connection getConnection() throws SQLException {
		return getDataSource().getConnection();
	}
 
	public static void shutdown() throws SQLException {
		getConnection().close();
	}

	public static void beginTrace(Connection conn, String id ) throws SQLException {
		String trcFileId = id + "_" + LocalTime.now().get(ChronoField.SECOND_OF_DAY);
		System.out.println(trcFileId);
		
		conn.createStatement().execute( "alter session set tracefile_identifier='" + trcFileId + "'" );
		conn.createStatement().execute( "alter session set sql_trace = true" );
	}

	public static void endTrace(Connection conn) throws SQLException {
		conn.createStatement().execute( "alter session set sql_trace = false" );
	}
}
