package es.dsw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"es.dsw"})
public class JavaSpringBootThymeleafDemo10ApirestJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringBootThymeleafDemo10ApirestJwtApplication.class, args);
	}

}
