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
@Table(name ="ORDERLINES")
public class OrderLine {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
 
    @ManyToOne
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private Product product;

    @Column(name = "QTY", unique = false, nullable = false)
    private BigDecimal qty;

    @Column(name = "AMOUNT", unique = false, nullable = false)
    private BigDecimal amount;

	public static List<OrderLine> fetchByOrder(Session session, Order order) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<OrderLine> cq = cb.createQuery(OrderLine.class);
        Root<OrderLine> root = cq.from(OrderLine.class);

        cq.select(root).where(cb.equal(root.get("order"), order));

        Query<OrderLine> q = session.createQuery(cq);
        return q.getResultList();
	}

	public OrderLine() {
	}

	public OrderLine(Long id, Order order, Product product, BigDecimal qty, BigDecimal amount) {
		super();
		this.id = id;
		this.order = order;
		this.product = product;
		this.qty = qty;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "OrderLine [id=" + id + ", order=" + order + ", product=" + product + ", qty=" + qty + ", amount="
				+ amount + "]";
	}

}
