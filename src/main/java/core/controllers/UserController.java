package core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import core.exceptions.ApplicationExceptions;
import core.service.UserControl;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserControl userControl;

	
	@GetMapping("/companies")
	public ResponseEntity<?> getAllCompanies() throws ApplicationExceptions {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userControl.getAllCompanies());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/stations/bus")
	public ResponseEntity<?> getAllStationsOfSpacificBusLine(@RequestParam int busLine,
			@RequestParam String companyName) throws ApplicationExceptions {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(userControl.getAllStationsOfSpecificBusLine(busLine, companyName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		}
	}

	@GetMapping("/bues")
	public ResponseEntity<?> getAllBuses() throws ApplicationExceptions {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userControl.getAllBuses());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/all/stations")
	public ResponseEntity<?> getAllStationsWithSpacificStationName(@RequestParam String stationName)
			throws ApplicationExceptions {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userControl.getAllStations(stationName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/search")
	public ResponseEntity<?> userSearchingStations() throws ApplicationExceptions {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userControl.getStationNameWithNoDuplicate());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
