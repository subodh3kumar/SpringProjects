package workshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import workshop.service.ExcelService;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(MainApplication.class);
	
	private ExcelService service;

	public MainApplication(ExcelService service) {
		this.service = service;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("run() method called");
		service.start();
	}

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
