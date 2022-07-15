package core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.dao.Bus;
import core.dao.BusesCompany;
import core.dao.Station;
import core.service.AdminStationControl;
import core.system.Session;
import core.system.SessionControl;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	SessionControl sessionControl;

	@Autowired
	private AdminStationControl stationControl;

	@PostMapping("/company")
	public ResponseEntity<?> addcompany(@RequestBody BusesCompany company, @RequestHeader String token)
			throws ApplicationContextException {
		try {
			Session session = this.sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			System.out.println(company);
			return ResponseEntity.status(HttpStatus.CREATED).body(stationControl.addBusCompany(company));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping("/company/{compId}")
	public ResponseEntity<?> deleteCompany(@PathVariable(name = "compId", required = true) int companyId,
			@RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.deleteBusCompany(companyId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PutMapping("/company")
	public ResponseEntity<?> updateCompany(@RequestBody BusesCompany company,
			@RequestParam(required = true) int companyId, @RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.updateBusCompany(company, companyId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PostMapping("/bus")
	public ResponseEntity<?> addBusToCompany(@RequestBody Bus bus, @RequestParam String companyName,
			@RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.addBus(bus, companyName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping("/bus/{busId}")
	public ResponseEntity<?> deleteBus(@PathVariable(name = "busId", required = true) int busId,
			@RequestHeader String token) {

		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.removeBus(busId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PutMapping("/bus")
	public ResponseEntity<?> updateBusOfCompany(@RequestBody Bus bus, @RequestParam(required = true) int oldBusLine,
			@RequestParam String companyName, @RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK)
					.body(stationControl.updateBusLine(bus, oldBusLine, companyName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PostMapping("/station")
	public ResponseEntity<?> addStationToBus(@RequestBody Station station,
			@RequestParam(required = true) int busNumberId, @RequestParam String companyName,
			@RequestHeader String token) {

		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(stationControl.addStation(station, busNumberId, companyName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PutMapping("/staton")
	public ResponseEntity<?> updateStationToBus(@RequestBody Station station, @RequestParam String companyName,
			@RequestParam(required = true) int updateBusNumber, @RequestParam(required = true) int updateStationNumber,
			@RequestParam(required = false) boolean chioseForSwichExistsStationNumber, @RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.updateStation(station, companyName,
					updateBusNumber, updateStationNumber, chioseForSwichExistsStationNumber));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping("/station/{id}")
	public ResponseEntity<?> deleteExistsStation(@PathVariable(required = true, name = "id") int stationId,
			@RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.removeStation(stationId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/companies")
	public ResponseEntity<?> getAllCompanies(@RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.getAllCompanies());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/companyBuses")
	public ResponseEntity<?> getAllBusesBySpecificCompany(@RequestParam(required = true) String companyName,
			@RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.getBusesByOneCompany(companyName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("buses")
	public ResponseEntity<?> getAllBses(@RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK).body(stationControl.getAllBuses());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/stations")
	public ResponseEntity<?> getAllStationBySpacificBusNumber(@RequestParam(required = true) int busNumber,
			@RequestParam(required = true) String companyName, @RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK)
					.body(stationControl.getAllStationsOfSpecificBusLine(busNumber, companyName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/station")
	public ResponseEntity<?> getOneStation(@RequestParam String stationName, @RequestParam int busNumber,
			@RequestParam String companyName, @RequestHeader String token) {
		try {
			Session session = sessionControl.getSessionExsists(token);
			stationControl = (AdminStationControl) session.getsessionsInformation("stationControl");
			return ResponseEntity.status(HttpStatus.OK)
					.body(stationControl.getSpecificStation(stationName, busNumber, companyName));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
}
