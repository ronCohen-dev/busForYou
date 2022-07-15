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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Bus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int busNumberID;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE })
	@JsonIgnore
	private BusesCompany bus_company;

	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "bus")
	@JsonIgnore
	private List<Station> busStations;

	@Column(nullable = false)
	private int busNumber;

	public Bus() {
		super();
	}

	public Bus(int busNumberID, int busNumber) {
		super();
		this.busNumberID = busNumberID;
		this.busNumber = busNumber;
	}

	public void addStation(Station station) {
		if (busStations == null) {
			busStations = new ArrayList<Station>();
		}
		station.setBus(this);
		this.busStations.add(station);
	}

	public int getBusNumberID() {
		return busNumberID;
	}

	public void setBusNumberID(int busNumberID) {
		this.busNumberID = busNumberID;
	}

	public BusesCompany getBus_company() {
		return bus_company;
	}

	public void setBus_company(BusesCompany bus_company) {
		this.bus_company = bus_company;
	}

	public List<Station> getBusStations() {
		return busStations;
	}

	public void setBusStations(List<Station> busStations) {
		this.busStations = busStations;
	}

	public int getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(int busNumber) {
		this.busNumber = busNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(busNumber, busNumberID, busStations, bus_company);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bus other = (Bus) obj;
		return busNumber == other.busNumber && busNumberID == other.busNumberID
				&& Objects.equals(busStations, other.busStations) && Objects.equals(bus_company, other.bus_company);
	}

	@Override
	public String toString() {
		return "Bus [busNumberID=" + busNumberID + ", bus_company=" + bus_company + ", busNumber=" + busNumber + "]";
	}



}