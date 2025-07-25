package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utilities.LoggerUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtility {
    
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * Reads data from Excel file and returns as Map of key-value pairs
     * 
     * @param filePath Path to Excel file
     * @param sheetName Name of the sheet to read
     * @param rowNum Row number (1-based index)
     * @return Map containing column headers as keys and cell values as values
     */
    public static Map<String, String> getData(String filePath, String sheetName, int rowNum) {
        Map<String, String> data = new HashMap<>();
        FileInputStream fis = null;
        Workbook workbook = null;

        try {
            LoggerUtility.debug("Reading Excel data from: " + filePath + ", Sheet: " + sheetName + ", Row: " + rowNum);
            
            fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in Excel file");
            }
            
            if (rowNum < 1 || rowNum > sheet.getLastRowNum()) {
                throw new IllegalArgumentException("Row number " + rowNum + " is out of bounds");
            }
            
            Row headerRow = sheet.getRow(0);
            Row dataRow = sheet.getRow(rowNum);
            
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String key = getCellValueAsString(headerRow.getCell(i));
                Cell cell = dataRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String value = getCellValueAsString(cell);
                
                data.put(key, value);
            }
            
        } catch (Exception e) {
            LoggerUtility.error("Error reading Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to read Excel data", e);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                LoggerUtility.error("Error closing Excel resources: " + e.getMessage());
            }
        }
        
        LoggerUtility.debug("Excel data read successfully: " + data);
        return data;
    }
    
    /**
     * Converts Excel cell value to appropriate String representation
     * 
     * @param cell The cell to evaluate
     * @return String representation of cell value
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
        
        CellType cellType = cell.getCellType();
        
        // Handle formula cells by evaluating them
        if (cellType == CellType.FORMULA) {
            cellType = cell.getCachedFormulaResultType();
        }
        
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue().trim();
                
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat(DATE_FORMAT).format(cell.getDateCellValue());
                } else {
                    // Check if numeric value is actually an integer
                    double numValue = cell.getNumericCellValue();
                    if (numValue == (long) numValue) {
                        return String.valueOf((long) numValue);
                    }
                    return String.valueOf(numValue);
                }
                
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
                
            case ERROR:
                return "ERROR: " + cell.getErrorCellValue();
                
            default:
                return "";
        }
    }
    
    /**
     * Gets the number of rows with data in the specified sheet
     * 
     * @param filePath Path to Excel file
     * @param sheetName Name of the sheet
     * @return Number of rows with data (1-based count)
     */
    public static int getRowCount(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in Excel file");
            }
            return sheet.getLastRowNum() + 1; // +1 to convert to 1-based index
            
        } catch (Exception e) {
            LoggerUtility.error("Error getting row count from Excel: " + e.getMessage());
            throw new RuntimeException("Failed to get row count from Excel", e);
        }
    }
}