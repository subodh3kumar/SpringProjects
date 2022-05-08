package workshop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import workshop.component.ExcelReader;

@Service
public class ExcelService {

	private static final Logger log = LoggerFactory.getLogger(ExcelService.class);

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
