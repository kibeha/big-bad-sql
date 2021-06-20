package kibeha.practical;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

@Entity
@Table(name ="ORDERS")
public class Order {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;
    
    @Column(name = "ORDERED", unique = false, nullable = true)
    private Date ordered;

    @Column(name = "DELIVERY", unique = false, nullable = true)
    private Date delivery;

	public static Order fetchById(Session session, Long orderId) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);
        root.fetch("customer",JoinType.INNER);

        cq.select(root).where(cb.equal(root.get("id"), orderId));

        Query<Order> q = session.createQuery(cq);
        return q.getResultList().get(0);
	}

	public Order() {
	}

	public Order(Long id, Customer customer, Date ordered, Date delivery) {
		super();
		this.id = id;
		this.customer = customer;
		this.ordered = ordered;
		this.delivery = delivery;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getOrdered() {
		return ordered;
	}

	public void setOrdered(Date ordered) {
		this.ordered = ordered;
	}

	public Date getDelivery() {
		return delivery;
	}

	public void setDelivery(Date delivery) {
		this.delivery = delivery;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + ", ordered=" + ordered + ", delivery=" + delivery + "]";
	}

}
