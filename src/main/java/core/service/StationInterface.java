package core.service;

import java.util.List;

import core.dao.Bus;
import core.dao.BusesCompany;
import core.dao.Station;
import core.exceptions.ApplicationExceptions;

public interface StationInterface {

	List<BusesCompany> getAllCompanies() throws ApplicationExceptions;
	
	List<Station> getAllStationsOfSpecificBusLine(int busNumber, String companyName) throws ApplicationExceptions;
	
	List<Bus> getAllBuses() throws ApplicationExceptions;
}
