package utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	
	public static Map<String, String> getTestData(String filePath, String sheetName, int rowNum){
		Map<String, String> data = new HashMap<>();
		
		try (FileInputStream fis = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(fis)){
				
			Sheet sheet = workbook.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			Row dataRow = sheet.getRow(rowNum);
			
			for(int i=0; i<headerRow.getLastCellNum(); i++) {
				String key = headerRow.getCell(i).getStringCellValue();
				String value = dataRow.getCell(i).toString();
				data.put(key, value);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to read Excel file");
		}
		return data;
	}

}
