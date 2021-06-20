package kibeha.practical;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="CUSTOMERS")
public class Customer{
 
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
 
    @Column(name = "NAME", unique = false, nullable = false, length = 20)
    private String name;
 
	public Customer() {
	}
	
	public Customer(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + "]";
	}
}