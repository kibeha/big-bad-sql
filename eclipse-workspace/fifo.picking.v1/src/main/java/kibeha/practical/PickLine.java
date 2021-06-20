package kibeha.practical;

import java.math.BigDecimal;

public class PickLine {
	
	private OrderLine orderLine;
	
	private Inventory inventory;

    private BigDecimal qty;

    private Long sortPosition;

    public PickLine() {
	}

	public PickLine(OrderLine orderLine, Inventory inventory, BigDecimal qty, Long sortPosition) {
		super();
		this.orderLine = orderLine;
		this.inventory = inventory;
		this.qty = qty;
		this.sortPosition = sortPosition;
	}

	public OrderLine getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(OrderLine orderLine) {
		this.orderLine = orderLine;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public Long getSortPosition() {
		return sortPosition;
	}

	public void setSortPosition(Long sortPosition) {
		this.sortPosition = sortPosition;
	}

	@Override
	public String toString() {
		return "PickLine [orderLine=" + orderLine + ", inventory=" + inventory + ", qty=" + qty + ", sortPosition="
				+ sortPosition + "]";
	}
}
