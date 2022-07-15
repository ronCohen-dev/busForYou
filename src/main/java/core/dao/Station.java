package core.dao;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "station")
public class Station {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String stationName;

	@Column(nullable = false)
	private int stationNumber;

	@JsonIgnore
	private int arrivalTime;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "busStations")
	@JsonIgnore
	private Bus bus;

	public Station() {
		super();
	}

	public Station(String stationName, int stationNumber, int arrivalTime) {
		super();
		this.stationName = stationName;
		this.stationNumber = stationNumber;
		this.arrivalTime = arrivalTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Station(int id, String stationName, int stationNumber, int arrivalTime, Bus bus) {
		super();
		this.id = id;
		this.stationName = stationName;
		this.stationNumber = stationNumber;
		this.arrivalTime = arrivalTime;
		this.bus = bus;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrivalTime, bus, id, stationName, stationNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		return Objects.equals(arrivalTime, other.arrivalTime) && Objects.equals(bus, other.bus) && id == other.id
				&& Objects.equals(stationName, other.stationName) && stationNumber == other.stationNumber;
	}

	@Override
	public String toString() {
		return "Station [id=" + id + ", stationName=" + stationName + ", stationNumber=" + stationNumber
				+ ", arrivalTime=" + arrivalTime + ", bus=" + bus + "]";
	}

}
