package core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApplicationExceptions extends ResponseStatusException {

	/**
	 * This class represent the bags for the proggrammer in runTime and gives the
	 * proggrem option to continue.
	 * Application version 1.
	 * @param status - this psaram represent the status of the activities in the program.
	 * @param reason - this psaram represent the reason of the activities in the program.
	 * @param cause - this psaram represent the cause of the fail method.
	 */
	private static final long serialVersionUID = 1L;

	public ApplicationExceptions(HttpStatus status, String reason, Throwable cause) {
		super(status, reason, cause);
	}

	public ApplicationExceptions(HttpStatus status, String reason) {
		super(status, reason);
	}

	public ApplicationExceptions(HttpStatus status) {
		super(status);
	}

}
