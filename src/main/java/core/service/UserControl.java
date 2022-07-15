package core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import core.dao.Bus;
import core.dao.BusesCompany;
import core.dao.Station;
import core.exceptions.ApplicationExceptions;
import core.reposetory.AdminControlBuses;
import core.reposetory.AdminControlBusesCompanyInterface;
import core.reposetory.AdminControlStationInterface;

@Service
@Transactional
public class UserControl implements UserInterface {

	@Autowired
	private AdminControlBusesCompanyInterface adminCompany;

	@Autowired
	private AdminControlBuses adminBus;

	@Autowired
	private AdminControlStationInterface adminStation;

	@Override
	public List<BusesCompany> getAllCompanies() throws ApplicationExceptions {
		try {
			return adminCompany.findAll();
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.EXPECTATION_FAILED, "you cant get all companies for user");
		}
	}

	@Override
	public List<Station> getAllStationsOfSpecificBusLine(int busNumber, String companyName)
			throws ApplicationExceptions {
		try {
			List<Bus> listOfBuses = new ArrayList<Bus>();
			listOfBuses = getBusesByOneCompany(companyName);
			for (Bus bus : listOfBuses) {
				if (bus.getBusNumberID() == busNumber) {
					List<Station> allBusStations = new ArrayList<Station>();
					allBusStations.addAll(bus.getBusStations());
					return allBusStations;
				}
			}
			throw new ApplicationExceptions(HttpStatus.NOT_FOUND, " The company dose not have any beses at all");
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "This station must not be null", e.getCause());
		}
	}

	@Override
	public List<Bus> getAllBuses() throws ApplicationExceptions {
		try {
			return adminBus.findAll();
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.EXPECTATION_FAILED, "This station must not be null",
					e.getCause());
		}
	}

	@Override
	public List<Station> getAllStations(String StationName) throws ApplicationExceptions {
		try {
			return adminStation.getAllstationsByStationName(StationName);
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.EXPECTATION_FAILED,
					"you cant get all stations by name for user");
		}
	}

	private List<Bus> getBusesByOneCompany(String companyName) {
		List<Bus> list = adminCompany.getByCompanyName(companyName).getBuses();
		return list;
	}

	@Override
	public Set<String> getStationNameWithNoDuplicate() throws ApplicationExceptions {
		try {
			List<Station> originalStationList = adminStation.findAll();
			Set<String> stationsWhithOutDuplications = new HashSet<String>();
			for (Station station : originalStationList) {
				stationsWhithOutDuplications.add(station.getStationName());
			}
			return stationsWhithOutDuplications;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.EXPECTATION_FAILED, "This stations List must not be null",
					e.getCause());
		}
	}
}