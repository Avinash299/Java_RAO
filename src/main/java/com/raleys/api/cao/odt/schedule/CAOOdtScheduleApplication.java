package com.raleys.api.cao.odt.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.raleys.api.cao.odt.schedule.model.SwaggerConstants;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * @author abhay.thakur
 *
 */
@SpringBootApplication
@SecurityScheme(name=SwaggerConstants.AUTHORIZATION,scheme = "bearer",bearerFormat="JWT", type = SecuritySchemeType.HTTP)
@OpenAPIDefinition(info = @Info(title = SwaggerConstants.TITILE, version = SwaggerConstants.VERSION, description = SwaggerConstants.DESCRIPTION))
public class CAOOdtScheduleApplication {
	public static void main(String[] args) {
		SpringApplication.run(CAOOdtScheduleApplication.class, args);
	}

}
