package workshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import workshop.entity.Customer;

@Slf4j
@Service
public class ExcelService {

	private ExcelReader readExcel;

	public ExcelService(ExcelReader readExcel) {
		this.readExcel = readExcel;
	}

	public void start() {
		log.info("start() method called");
		List<Customer> customers = readExcel.getAllCustomers();
		log.info("custmers size: {}", customers.size());
	}

}
