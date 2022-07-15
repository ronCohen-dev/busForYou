package core.system;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

public class LoginFilter implements Filter{

	private SessionControl sessionControl;

	public LoginFilter(SessionControl sessionControl) {
		super();
		this.sessionControl = sessionControl;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = httpServletRequest.getHeader("token");
		if ( token != null && sessionControl.getSessionExsists(token) != null) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
		httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(),"you need to log in first");
	}

	
	
	

}
