package kibeha.practical;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="LOCATIONS")
public class Location {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
 
    @Column(name = "WAREHOUSE", unique = false, nullable = false)
    private Long warehouse;

    @Column(name = "AISLE", unique = false, nullable = false, length = 1)
    private String aisle;
 
    @Column(name = "POSITION", unique = false, nullable = false)
    private Long position;

	public Location() {
	}

	public Location(Long id, Long warehouse, String aisle, Long position) {
		super();
		this.id = id;
		this.warehouse = warehouse;
		this.aisle = aisle;
		this.position = position;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Location [id=" + id + ", warehouse=" + warehouse + ", aisle=" + aisle + ", position=" + position + "]";
	}
	
}
