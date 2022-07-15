package core.reposetory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import core.dao.Station;
import core.exceptions.ApplicationExceptions;

public interface AdminControlStationInterface extends JpaRepository<Station, Integer> {

	boolean existsStationByStationName(String stationName) throws ApplicationExceptions;

	List<Station> getAllstationsByStationName(String stationName) throws ApplicationExceptions;

}
