package core.reposetory;

import org.springframework.data.jpa.repository.JpaRepository;

import core.dao.BusesCompany;
import core.exceptions.ApplicationExceptions;

public interface AdminControlBusesCompanyInterface extends JpaRepository<BusesCompany, Integer>{

	boolean existsByCompanyName (String CompanyName) throws ApplicationExceptions;
	
	BusesCompany getByCompanyName (String CompanyName) throws ApplicationExceptions;
	
	
	
	
	
	
	
}
