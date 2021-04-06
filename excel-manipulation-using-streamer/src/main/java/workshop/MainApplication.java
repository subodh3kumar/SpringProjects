package workshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import workshop.service.ExcelService;

@Slf4j
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

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
