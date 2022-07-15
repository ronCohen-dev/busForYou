package core.system;

import org.springframework.stereotype.Component;

import core.exceptions.ApplicationExceptions;
import core.service.AdminStationControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

@Component
public class AdminLogin {

	@Autowired
	private ApplicationContext context;

	public AdminStationControl doLogin(String adminEmail, String adminPassword) throws ApplicationExceptions {
		
		AdminStationControl adminStationControl = context.getBean(AdminStationControl.class);
		if(adminStationControl.adminLogin(adminEmail, adminPassword)) {
			return adminStationControl;
		}
		throw new ApplicationExceptions(HttpStatus.UNAUTHORIZED, "You are not authorized , try agin");
	}
}
