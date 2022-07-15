package core.service;

import java.util.List;

import core.dao.Bus;
import core.dao.BusesCompany;
import core.dao.Station;
import core.exceptions.ApplicationExceptions;

public interface AdminStationInterface extends StationInterface{

	boolean adminLogin(String email, String password) throws ApplicationExceptions;

	BusesCompany addBusCompany(BusesCompany company) throws ApplicationExceptions;

	BusesCompany deleteBusCompany(int companyId) throws ApplicationExceptions;

	BusesCompany updateBusCompany(BusesCompany company, int companyNumber) throws ApplicationExceptions;

	Bus addBus(Bus bus, String busesCompanyName) throws ApplicationExceptions;

	Bus removeBus(int busId) throws ApplicationExceptions;

	Bus updateBusLine(Bus bus, int oldBusLine, String busesCompanyName) throws ApplicationExceptions;

	Station addStation(Station station, int busId, String companyName) throws ApplicationExceptions;

	Station removeStation(int stationId) throws ApplicationExceptions;

	Station updateStation(Station station, String companyName, int busNumber, int stationNumber,
			Boolean chioseForSwichStationNumber) throws ApplicationExceptions;

	List<Bus> getBusesByOneCompany(String companyName) throws ApplicationExceptions;

	Station getSpecificStation(String stationName, int busNumber, String companyName) throws ApplicationExceptions;

}
