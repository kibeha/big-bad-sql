package kibeha.practical;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="PURCHASES")
public class Purchase {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
 
    @Column(name = "PURCHASED", unique = false, nullable = false)
    private Date purchased;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BREWERY_ID", referencedColumnName = "ID")
    private Brewery brewery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private Product product;

    @Column(name = "QTY", unique = false, nullable = false)
    private BigDecimal qty;

    @Column(name = "COST", unique = false, nullable = false)
    private BigDecimal cost;

	public Purchase() {
	}

	public Purchase(Long id, Date purchased, Brewery brewery, Product product, BigDecimal qty, BigDecimal cost) {
		super();
		this.id = id;
		this.purchased = purchased;
		this.brewery = brewery;
		this.product = product;
		this.qty = qty;
		this.cost = cost;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPurchased() {
		return purchased;
	}

	public void setPurchased(Date purchased) {
		this.purchased = purchased;
	}

	public Brewery getBrewery() {
		return brewery;
	}

	public void setBrewery(Brewery brewery) {
		this.brewery = brewery;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", purchased=" + purchased + ", brewery=" + brewery + ", product=" + product
				+ ", qty=" + qty + ", cost=" + cost + "]";
	}

}
