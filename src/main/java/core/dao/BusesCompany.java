package core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bus_company")
public class BusesCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bus_company")
	@JsonIgnore
	private List<Bus> buses;

	@Column(nullable = false)
	private String companyName;

	public BusesCompany() {
		super();
	}

	public String getCompanyName() {
		return companyName;
	}

	public BusesCompany(Integer id, List<Bus> buses, String companyName) {
		super();
		this.id = id;
		this.buses = buses;
		this.companyName = companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getId() {
		return id;
	}

	public void addBusToList(Bus bus) {
		if (buses == null) {
			buses = new ArrayList<Bus>();
		}
		bus.setBus_company(this);
		this.buses.add(bus);
	}

	public List<Bus> getBuses() {
		return buses;
	}

	public void setBuses(List<Bus> buses) {
		this.buses = buses;
	}

	@Override
	public int hashCode() {
		return Objects.hash(buses, companyName, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusesCompany other = (BusesCompany) obj;
		return Objects.equals(buses, other.buses) && Objects.equals(companyName, other.companyName) && id == other.id;
	}

	@Override
	public String toString() {
		return "BusesCompany [id=" + id + ", buses=" + buses + ", companyName=" + companyName + "]";
	}

}
