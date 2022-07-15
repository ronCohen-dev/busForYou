package core.service;

import java.util.List;
import java.util.Set;
import core.dao.Station;
import core.exceptions.ApplicationExceptions;

public interface UserInterface extends StationInterface {

	List<Station> getAllStations(String stationName) throws ApplicationExceptions;

	Set<String> getStationNameWithNoDuplicate() throws ApplicationExceptions;
}
