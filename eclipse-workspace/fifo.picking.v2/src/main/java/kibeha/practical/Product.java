package kibeha.practical;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name ="PRODUCTS")
public class Product{
 
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
 
    @Column(name = "NAME", unique = false, nullable = false, length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID")
    private ProductGroup productGroup;

	public Product() {
	}

	public Product(Long id, String name, ProductGroup productGroup) {
		super();
		this.id = id;
		this.name = name;
		this.productGroup = productGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductGroup getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(ProductGroup productGroup) {
		this.productGroup = productGroup;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", productGroup=" + productGroup + "]";
	}
	
}