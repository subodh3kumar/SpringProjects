package workshop.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.monitorjbl.xlsx.StreamingReader;

import lombok.extern.slf4j.Slf4j;
import workshop.model.Shipper;

@Slf4j
@Service
public class ExcelService {

	private static final String SHIPPERSR_EXCEL_PATH = "/Development/Files/Input/Excel/Shippers.xlsx";

	public void start() throws Exception {
		log.info("start() method called");

		File file = new File(SHIPPERSR_EXCEL_PATH);

		try (Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(file)) {
			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter formatter = new DataFormatter();

			List<Shipper> shippers = new ArrayList<>();

			for (Row row : sheet) {

				short firstCellNum = row.getFirstCellNum();
				short lastCellNum = row.getLastCellNum();

				Shipper shipper = new Shipper();

				for (int i = firstCellNum; i < lastCellNum; i++) {
					String cellValue = formatter.formatCellValue(row.getCell(i));
					if (i == 0) {
						shipper.setId(cellValue);
					}
					if (i == 1) {
						shipper.setName(cellValue);
					}
					if (i == 2) {
						shipper.setPhone(cellValue);
					}
				}
				shippers.add(shipper);
			}
			if (CollectionUtils.isNotEmpty(shippers)) {
				shippers.remove(0);
				shippers.forEach(shipper -> log.info(shipper.toString()));
			}
		}
	}
}
