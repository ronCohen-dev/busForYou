package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import core.system.LoginFilter;
import core.system.SessionControl;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableScheduling
@CrossOrigin
public class BusForYouApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusForYouApplication.class, args);
		
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("core"))
				.paths(PathSelectors.ant("/**")).build();
	}

	@Bean 
	public FilterRegistrationBean<LoginFilter> registrationBean(SessionControl control){
		FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<LoginFilter>();
		LoginFilter loginFilter = new LoginFilter(control);
		registrationBean.setFilter(loginFilter);
		registrationBean.addUrlPatterns("/admin/**" , "/user/**");
		return registrationBean;
		
	}
}
