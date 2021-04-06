package workshop.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import workshop.component.ExcelReader;

@Slf4j
@Service
public class ExcelService {

	private ExcelReader readExcel;

	public ExcelService(ExcelReader readExcel) {
		this.readExcel = readExcel;
	}

	public void start() throws Exception {
		log.info("start() method called");

		// readExcel.getAllCustomersUsingWorkbookFactory();
		readExcel.getAllCustomersusingStream();
	}
}
