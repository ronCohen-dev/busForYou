package core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class AdminStationControl implements AdminStationInterface {

	@Autowired
	AdminControlStationInterface adminStation;

	@Autowired
	AdminControlBusesCompanyInterface adminCompany;

	@Autowired
	AdminControlBuses adminBus;

	private static final String adminEmail = "admin@administrator.com";

	private static final String adminPassword = "admin123";

	private static final int arrivalTimeForNextStop = 5;

	@Override
	public boolean adminLogin(String email, String password) throws ApplicationExceptions {
		try {
			if (email.equals(adminEmail) && password.equals(adminPassword)) {
				return true;
			} else {
				throw new ApplicationExceptions(HttpStatus.UNAUTHORIZED, "plz enter admin user..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationExceptions(HttpStatus.UNAUTHORIZED, "you have entere wrong credentials..",
					e.getCause());
		}
	}

	@Override
	public BusesCompany addBusCompany(BusesCompany company) throws ApplicationExceptions {
		try {
			if (!adminCompany.existsByCompanyName(company.getCompanyName())) {
				adminCompany.save(company);
				return company;
			} else {
				throw new ApplicationExceptions(HttpStatus.FOUND, "This company all redy exists");
			}
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.UNAUTHORIZED, "You need to do login first", e.getCause());
		}

	}

	@Override
	public BusesCompany deleteBusCompany(int companyId) throws ApplicationExceptions {
		try {
			Optional<BusesCompany> optional = adminCompany.findById(companyId);
			if (optional.isEmpty()) {
				throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "This company id must not be null");
			}
			BusesCompany company = optional.get();
			adminCompany.deleteById(companyId);
			return company;

		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_FOUND, "This company must not be null", e.getCause());
		}
	}

	@Override
	public BusesCompany updateBusCompany(BusesCompany company, int companyId) throws ApplicationExceptions {
		try {
			BusesCompany currentCompany = adminCompany.findById(companyId).orElse(null);
			if (adminCompany.existsById(companyId) && currentCompany != null) {
				System.out.println("company id : " + companyId);
				currentCompany.setCompanyName(company.getCompanyName());
				adminCompany.save(currentCompany);
			}
			return currentCompany;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.BAD_REQUEST, "This paramaters must not be null", e.getCause());
		}
	}

	@Override
	public Bus addBus(Bus bus, String busesCompanyName) throws ApplicationExceptions {
		try {
			BusesCompany company = adminCompany.getByCompanyName(busesCompanyName);
			List<Bus> listBuses = company.getBuses();
			if (company != null) {
				for (Bus bus2 : listBuses) {
					if (bus2.getBusNumber() == bus.getBusNumber()) {
						throw new ApplicationExceptions(HttpStatus.FOUND, "You have allredy this bus line number");
					}
				}
				company.addBusToList(bus);
				adminBus.save(bus);
			}
			return bus;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "You need to check you insert paramaters",
					e.getCause());
		}
	}

	@Override
	public Bus removeBus(int busId) throws ApplicationExceptions {
		try {
			Optional<Bus> optional = adminBus.findById(busId);
			if (optional.isEmpty()) {
				throw new ApplicationExceptions(HttpStatus.NOT_FOUND, "This bus id must not be null !");
			}
			Bus bus = optional.get();
			adminBus.deleteById(busId);
			return bus;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "you need to login first !", e.getCause());
		}
	}

	@Override
	public Bus updateBusLine(Bus bus, int oldBusLine, String busesCompanyName) throws ApplicationExceptions {
		try {
			List<Integer> busesLine = new ArrayList<Integer>();
			BusesCompany busCompany = adminCompany.getByCompanyName(busesCompanyName);
			Bus currentUpdateBus = adminBus.getBusByBusNumber(oldBusLine);
			List<Bus> listOfCompanyBuses = busCompany.getBuses();
			for (Bus bus2 : listOfCompanyBuses) {
				busesLine.add(bus2.getBusNumber());
			}
			if (listOfCompanyBuses.isEmpty() || busesLine.get(bus.getBusNumber()) == null) {
				currentUpdateBus.setBusNumber(bus.getBusNumber());
			}
			return currentUpdateBus;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_FOUND, "You need to check your inserst !");

		}
	}

	@Override
	public Station addStation(Station station, int busNumberId, String companyName) throws ApplicationExceptions {
		try {
			Bus currentBus = null;
			List<Bus> busesList = getBusesByOneCompany(companyName);
			for (Bus bus : busesList) {
				if ((!busesList.isEmpty()) && bus.getBusNumber() == busNumberId) {
					System.out.println("WOW - it works i found my bus ! ");
					currentBus = bus;
				}
			}
			List<Station> stations = currentBus.getBusStations();
			for (Station station2 : stations) {
				if ((station2.getStationNumber() == station.getStationNumber()
						|| station2.getStationName() == station.getStationName())) {
					System.out.println("You have this station number allredy");
					throw new ApplicationExceptions(HttpStatus.FOUND, "You have this station number allredy");
				}
			}
			int lastStationArrivalTime = stations.get(stations.size() - 1).getArrivalTime();
			station.setArrivalTime(lastStationArrivalTime + arrivalTimeForNextStop);
			currentBus.addStation(station);
			adminStation.save(station);
			return station;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "You need to login first !");
		}
	}

	@Override
	public Station removeStation(int stationId) throws ApplicationExceptions {
		try {
			Optional<Station> optional = adminStation.findById(stationId);
			if (optional.isEmpty()) {
				throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "This station id must exists !");
			}
			Station currentStation_DB = optional.get();
			adminStation.deleteById(stationId);
			return currentStation_DB;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "This station must found !");
		}
	}

	@Override
	public Station updateStation(Station station, String companyName, int busNumber, int stationNumber,
			Boolean chioseForSwichStationNumber) throws ApplicationExceptions {
		try {
			List<Station> currentBusStations = new ArrayList<Station>();
			List<Bus> buses = getBusesByOneCompany(companyName);
			for (Bus bus : buses) {
				if (bus.getBusNumber() == busNumber) {
					currentBusStations = bus.getBusStations();
				}
			}
			Station currentUpdateStation = currentBusStations.get(stationNumber - 1);
			int arrivalTime = currentUpdateStation.getArrivalTime();
			Station stationForSwap = currentBusStations.get(station.getStationNumber() - 1);
			currentUpdateStation.setArrivalTime(station.getStationNumber() * arrivalTimeForNextStop);
			currentUpdateStation.setStationName(station.getStationName());

			if (chioseForSwichStationNumber == true && stationForSwap != null) {
				stationForSwap.setArrivalTime(arrivalTime);
				stationForSwap.setStationNumber(stationNumber);
				currentUpdateStation.setStationNumber(station.getStationNumber());
			}
			return currentUpdateStation;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "This station must not be null", e.getCause());
		}
	}

	@Override
	public List<BusesCompany> getAllCompanies() throws ApplicationExceptions {
		try {
			return adminCompany.findAll();
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.EXPECTATION_FAILED, "This station must not be null",
					e.getCause());
		}
	}

	@Override
	public List<Bus> getBusesByOneCompany(String companyName) throws ApplicationExceptions {
		try {
			List<Bus> list = adminCompany.getByCompanyName(companyName).getBuses();
			return list;
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
	public Station getSpecificStation(String stationName, int busNumber, String companyName)
			throws ApplicationExceptions {
		try {
			List<Station> stations = new ArrayList<Station>();
			Station station = new Station();
			stations = getAllStationsOfSpecificBusLine(busNumber, companyName);
			for (Station station1 : stations) {
				if (station1.getStationName().equals(stationName)) {
					station = station1;
				}
			}
			return station;
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.NOT_ACCEPTABLE, "This station must not be null", e.getCause());
		}

	}

}
