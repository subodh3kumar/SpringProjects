package workshop.service;

import static workshop.util.Constants.ADDRESS;
import static workshop.util.Constants.CITY;
import static workshop.util.Constants.CONTACT_NAME;
import static workshop.util.Constants.COUNTRY;
import static workshop.util.Constants.CUSTOMER_ID;
import static workshop.util.Constants.CUSTOMER_NAME;
import static workshop.util.Constants.POSTAL_CODE;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import workshop.entity.Customer;
import workshop.repository.ExcelRepository;

@Slf4j
@Component
public class ExcelReader {

	private static final String EXCEL_PATH = "/Development/Files/Input/Excel/Customers.xlsx";

	private ExcelRepository repository;

	public ExcelReader(ExcelRepository repository) {
		this.repository = repository;
	}

	public List<Customer> getAllCustomers() {

		List<Customer> customers = null;

		try (Workbook workbook = WorkbookFactory.create(new File(EXCEL_PATH))) {
			Sheet sheet = workbook.getSheetAt(0);
			Map<String, Integer> columns = getColumnNameWithIndex(sheet);
			columns.forEach((k, v) -> System.out.println(String.format("%-15s%5d", k, v)));

			final int customerIdIndex = columns.get(CUSTOMER_ID) == null ? -1 : columns.get(CUSTOMER_ID);
			final int customerNameIndex = columns.get(CUSTOMER_NAME) == null ? -1 : columns.get(CUSTOMER_NAME);
			final int contactNameIndex = columns.get(CONTACT_NAME) == null ? -1 : columns.get(CONTACT_NAME);
			final int addressIndex = columns.get(ADDRESS) == null ? -1 : columns.get(ADDRESS);
			final int cityIndex = columns.get(CITY) == null ? -1 : columns.get(CITY);
			final int countryIndex = columns.get(COUNTRY) == null ? -1 : columns.get(COUNTRY);
			final int postalCodeIndex = columns.get(POSTAL_CODE) == null ? -1 : columns.get(POSTAL_CODE);

			DataFormatter formatter = new DataFormatter();
			int totalRows = sheet.getPhysicalNumberOfRows();

			customers = new ArrayList<>();

			for (int i = 1; i <= totalRows; i++) {

				Row row = sheet.getRow(i);

				boolean isEmpty = isRowEmpty(row);

				if (isEmpty) {
					continue;
				}

				Customer customer = new Customer();
				if (customerIdIndex >= 0) {
					customer.setCustomerId(Integer.valueOf(formatter.formatCellValue(row.getCell(customerIdIndex))));
				}
				if (customerNameIndex >= 0) {
					customer.setCustomerName(formatter.formatCellValue(row.getCell(customerNameIndex)));
				}
				if (contactNameIndex >= 0) {
					customer.setContactName(formatter.formatCellValue(row.getCell(contactNameIndex)));
				}
				if (addressIndex >= 0) {
					customer.setAddress(formatter.formatCellValue(row.getCell(addressIndex)));
				}
				if (cityIndex >= 0) {
					customer.setCity(formatter.formatCellValue(row.getCell(cityIndex)));
				}
				if (countryIndex >= 0) {
					customer.setCountry(formatter.formatCellValue(row.getCell(countryIndex)));
				}
				if (postalCodeIndex >= 0) {
					customer.setPostalCode(formatter.formatCellValue(row.getCell(postalCodeIndex)));
				}
		
				customers.add(customer);

				if (customers.size() == 100) {
					save(customers);
				}
			}

		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
		
		if (CollectionUtils.isNotEmpty(customers)) {
			save(customers);
		}
		
		return customers;
	}

	private void save(List<Customer> customers) {
		log.info("save() method called");
		repository.save(customers);
		customers.removeAll(customers);
	}

	private boolean isRowEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			if (cell != null && cell.getCellType() != CellType.BLANK && !cell.toString().isBlank()) {
				return false;
			}
		}
		return true;
	}

	private Map<String, Integer> getColumnNameWithIndex(Sheet sheet) {
		Map<String, Integer> columns = new HashMap<>();
		Row row = sheet.getRow(0);

		short firstCellNum = row.getFirstCellNum();
		short lastCellNum = row.getLastCellNum();

		for (int index = firstCellNum; index < lastCellNum; index++) {
			Cell cell = row.getCell(index);
			columns.put(cell.getStringCellValue(), cell.getColumnIndex());
		}
		return columns;
	}

}
