package core.reposetory;

import org.springframework.data.jpa.repository.JpaRepository;
import core.dao.Bus;
import core.exceptions.ApplicationExceptions;

public interface AdminControlBuses extends JpaRepository<Bus, Integer> {


	Bus getBusByBusNumber(int busNumber) throws ApplicationExceptions;


}
