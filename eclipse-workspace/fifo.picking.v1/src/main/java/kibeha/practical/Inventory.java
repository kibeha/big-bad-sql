package kibeha.practical;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

@Entity
@Table(name ="INVENTORY")
public class Inventory {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "PURCHASE_ID", referencedColumnName = "ID")
    private Purchase purchase;

    @Column(name = "QTY", unique = false, nullable = false)
    private BigDecimal qty;

	public static List<Inventory> fetchByProduct(Session session, Product product) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Inventory> cq = cb.createQuery(Inventory.class);
        Root<Inventory> root = cq.from(Inventory.class);

        cq.select(root).where(cb.equal(root.get("product"), product));

        Query<Inventory> q = session.createQuery(cq);
        return q.getResultList();
	}

	public Inventory() {
	}

	public Inventory(Long id, Location location, Product product, Purchase purchase, BigDecimal qty) {
		super();
		this.id = id;
		this.location = location;
		this.product = product;
		this.purchase = purchase;
		this.qty = qty;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", location=" + location + ", product=" + product + ", purchase=" + purchase
				+ ", qty=" + qty + "]";
	}

}
