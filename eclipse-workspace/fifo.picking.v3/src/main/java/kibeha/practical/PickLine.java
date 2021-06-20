package kibeha.practical;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PickLine {

	private Long warehouse;
	private String aisle;
	private Long position;
	private String productName;
	private BigDecimal pickQty;
	private Long orderId;
	private String customerName;

	public PickLine() {
	}

	public PickLine(Long warehouse, String aisle, Long position, String productName, BigDecimal pickQty, Long orderId,
			String customerName) {
		super();
		this.warehouse = warehouse;
		this.aisle = aisle;
		this.position = position;
		this.productName = productName;
		this.pickQty = pickQty;
		this.orderId = orderId;
		this.customerName = customerName;
	}

    public static List<PickLine> fromResultSet(ResultSet rset ) throws SQLException {
    	List<PickLine> pickLines = new ArrayList<PickLine>();

    	while (rset.next ())
		{
    		pickLines.add(
				new PickLine(
        		rset.getLong(1),
        		rset.getString(2),
        		rset.getLong(3),
        		rset.getString(4),
        		rset.getBigDecimal(5),
        		rset.getLong(6),
        		rset.getString(7)
        	));
		}

    	return pickLines;
    }

    public Long getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Long warehouse) {
		this.warehouse = warehouse;
	}

	public String getAisle() {
		return aisle;
	}

	public void setAisle(String aisle) {
		this.aisle = aisle;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPickQty() {
		return pickQty;
	}

	public void setPickQty(BigDecimal pickQty) {
		this.pickQty = pickQty;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Override
	public String toString() {
		return "PickLine [warehouse=" + warehouse + ", aisle=" + aisle + ", position=" + position + ", productName="
				+ productName + ", pickQty=" + pickQty + ", orderId=" + orderId + ", customerName=" + customerName
				+ "]";
	}

}
