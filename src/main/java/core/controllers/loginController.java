package core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.dao.adminUser;
import core.exceptions.ApplicationExceptions;
import core.service.AdminStationControl;
import core.system.AdminLogin;
import core.system.Session;
import core.system.SessionControl;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class loginController {

	private Session session;
	
	@Autowired
	SessionControl sessionControl;
	
	@Autowired
	AdminLogin adminLogin;
	
	@PostMapping("/.log")
	public String administratorLogin (@RequestBody adminUser adminUser) throws ApplicationExceptions{
		
		try {
			AdminStationControl stationControl = adminLogin.doLogin(adminUser.getEmail(), adminUser.getPassword());
			if(stationControl != null) {
				session = sessionControl.creatSession();
				session.setseSionsInformation("stationControl", stationControl);
				System.out.println("good");
				return "{\"token\": \"" + session.token + "\"}";
			}
		} catch (Exception e) {
			throw new ApplicationExceptions(HttpStatus.BAD_REQUEST, "plz try agin", e.getCause());
		}
		throw new ApplicationExceptions(HttpStatus.UNAUTHORIZED, "plz try agin");
	}
	
}
