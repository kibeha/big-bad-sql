package kibeha.practical;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import oracle.jdbc.OracleTypes;

public class PickList {
	
	private List<PickLine> pickLines;

	public PickList() {
	}
	
	public PickList(List<PickLine> pickLines) {
		super();
		this.pickLines = pickLines;
	}

	public List<PickLine> getPickLines() {
		return pickLines;
	}

	public void setPickLines(List<PickLine> pickLines) {
		this.pickLines = pickLines;
	}

	public void fetchByFifo(Connection conn, Long orderId ) throws SQLException {
		CallableStatement call = null;
		
		try
		{
			call = conn.prepareCall( "{call picklist.fetch_by_fifo (?,?)}" );
			call.setLong(1, orderId);
			call.registerOutParameter(2, OracleTypes.CURSOR);
			
			call.execute();
			
			this.pickLines = PickLine.fromResultSet( (ResultSet)call.getObject(2) );

		} finally {
			if (call != null) call.close();
		}
	}
	
	public void output() {
		System.out.println();
    	System.out.printf(
        		"%2s %2s %3s %-20s %4s %5s %-20s%n",
        		"Wh",
        		"Ai",
        		"Pos",
        		"Product",
        		"Qty",
        		"Order",
        		"Customer"
        	);
		System.out.println();
        pickLines.forEach((pick) -> {
        	System.out.printf(
        		"%2d %2s %3d %-20s %4.0f %5d %-20s%n",
        		pick.getWarehouse(),
        		pick.getAisle(),
        		pick.getPosition(),
        		pick.getProductName(),
        		pick.getPickQty(),
        		pick.getOrderId(),
        		pick.getCustomerName()
        	);
        });
		System.out.println();
	}

}
