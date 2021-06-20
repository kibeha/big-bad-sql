package kibeha.practical;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;

public class PickList {
	
	private List<PickLine> pickLines;

	public PickList() {
		this.pickLines = new ArrayList<PickLine>();
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

	public void fetchPickLinesFifo(Session session, Long orderId ) {
        Order order = Order.fetchById(session, orderId);
        List<OrderLine> orderLines = OrderLine.fetchByOrder(session, order);

        orderLines.forEach((line) -> {
        	BigDecimal pickedQty = BigDecimal.ZERO;
        	List<Inventory> inventories = Inventory.fetchByProduct(session, line.getProduct());
        	
        	Collections.sort(inventories, Comparator.comparing(Inventory::getPurchase, (p1, p2) -> {return p1.getPurchased().compareTo(p2.getPurchased());})
        										.thenComparing(Inventory::getQty));

        	for (int i = 0; i < inventories.size(); i++) {
        		Inventory inv = inventories.get(i);
        		PickLine pickLine = new PickLine(line, inv, inv.getQty().min(line.getQty().subtract(pickedQty)), null);
        		pickedQty = pickedQty.add(pickLine.getQty());
        		this.pickLines.add(pickLine);

        		if (pickedQty.compareTo(line.getQty()) == 0) { break; }
        	}
        });
	}
	
	public void sortPickLinesByRoute() {
        Collections.sort(this.pickLines, Comparator.comparing(PickLine::getInventory, (i1, i2) -> {return i1.getLocation().getWarehouse().compareTo(i2.getLocation().getWarehouse());})
				  .thenComparing(PickLine::getInventory, (i1, i2) -> {return i1.getLocation().getAisle().compareTo(i2.getLocation().getAisle());})
		);
		
		int visitedAisle = 0;
		Long lastWarehouse = (long) 0;
		String lastAisle = "";
		for (int i = 0; i < this.pickLines.size(); i++) {
			PickLine pick = this.pickLines.get(i);
			if (pick.getInventory().getLocation().getWarehouse().compareTo(lastWarehouse) != 0 || pick.getInventory().getLocation().getAisle().compareTo(lastAisle) != 0) {
				visitedAisle += 1;
				lastWarehouse = pick.getInventory().getLocation().getWarehouse();
				lastAisle = pick.getInventory().getLocation().getAisle();
			}
			if (visitedAisle % 2 == 0) {
				pick.setSortPosition(- pick.getInventory().getLocation().getPosition());
			} else {
				pick.setSortPosition(+ pick.getInventory().getLocation().getPosition());
			}
		}
		
		Collections.sort(this.pickLines, Comparator.comparing(PickLine::getInventory, (i1, i2) -> {return i1.getLocation().getWarehouse().compareTo(i2.getLocation().getWarehouse());})
						  .thenComparing(PickLine::getInventory, (i1, i2) -> {return i1.getLocation().getAisle().compareTo(i2.getLocation().getAisle());})
						  .thenComparing(PickLine::getSortPosition)
		);
		
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
        		pick.getInventory().getLocation().getWarehouse(),
        		pick.getInventory().getLocation().getAisle(),
        		pick.getInventory().getLocation().getPosition(),
        		pick.getInventory().getProduct().getName(),
        		pick.getQty(),
        		pick.getOrderLine().getOrder().getId(),
        		pick.getOrderLine().getOrder().getCustomer().getName()
        	);
        });
		System.out.println();
	}

}
