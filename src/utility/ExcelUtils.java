package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

public class ExcelUtils {

	private static HSSFSheet ExcelWSheet;
	private static HSSFWorkbook ExcelWBook;
	private static HSSFCell Cell;
	private static HSSFRow Row;
	private static DataFormatter formatter;
	public static List<String> allValues = new ArrayList<String>();
	public static int numberOfCellsInRow;
	public static Vector cellVectorHolder = new Vector();

	public static void setExcelFile(String Path, String SheetName) throws Exception {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new HSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			formatter = new DataFormatter();
		} catch (Exception e) {
			throw (e);
		}

	}

	public static void setExcelFileByPath(String Path) throws Exception {
		try {
			FileInputStream ExcelFile = new FileInputStream(Path);
			ExcelWBook = new HSSFWorkbook(ExcelFile);
		} catch (Exception e) {
			throw (e);
		}

	}

	public static String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = formatter.formatCellValue(Cell);
			return CellData;
		} catch (Exception e) {
			return "";
		}

	}

	public static void setCellData(String Result, int RowNum, int ColNum) throws Exception {
		try {
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(PathsConfig.PATH + PathsConfig.FILENAME);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			throw (e);

		}

	}

	public static void createNewSheet(String nameOfNewSheet, String sheetNameToCheck) throws Exception {

		boolean isNameExists = false;
		setExcelFileByPath(PathsConfig.PATH + PathsConfig.FILENAME);

		List<String> sheetNames = new ArrayList<String>();
		for (int i = 0; i < ExcelWBook.getNumberOfSheets(); i++) {
			sheetNames.add(ExcelWBook.getSheetName(i));
		}

		for (int i = 0; i < ExcelWBook.getNumberOfSheets(); i++) {
			if (sheetNames.get(i).equals(sheetNameToCheck)) {
				isNameExists = true;
				System.out.println("Sheet with that name alrady exists !");
			} else {
				isNameExists = false;
				System.out.println("Sheet dos not have the same name");
			}
		}
		if (!isNameExists) {
			setExcelFile(PathsConfig.PATH + PathsConfig.FILENAME, sheetNameToCheck);
			FileOutputStream fileOut = new FileOutputStream(PathsConfig.PATH + PathsConfig.FILENAME);
			ExcelWSheet = ExcelWBook.getSheet(nameOfNewSheet);
			HSSFSheet newSheet = ExcelWBook.createSheet(nameOfNewSheet);
			ExcelWBook.write(fileOut);
		}
	}

	public static void writeToWorkSheet(String Result, int RowNum, int ColNum) throws IOException {

		try {
			if ((Row = ExcelWSheet.getRow(RowNum)) == null) {
				Row = ExcelWSheet.createRow(RowNum);
			}

			Cell = Row.getCell(ColNum, MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}

			FileOutputStream fileOut = new FileOutputStream(PathsConfig.PATH + PathsConfig.FILENAME);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			throw (e);
		}
	}

	public static void writeToWorkSheetBoolean(boolean bool, int RowNum, int ColNum) throws IOException {

		try {
			if ((Row = ExcelWSheet.getRow(RowNum)) == null) {
				Row = ExcelWSheet.createRow(RowNum);
			}

			Cell = Row.getCell(ColNum, MissingCellPolicy.RETURN_BLANK_AS_NULL);// (Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(bool);
			} else {
				Cell.setCellValue(bool);
			}

			FileOutputStream fileOut = new FileOutputStream(PathsConfig.PATH + PathsConfig.FILENAME);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			throw (e);

		}
	}

	public static void getAllValuesFromExcel() throws IOException {

		int rowcount = ExcelWSheet.getLastRowNum();

		for (int i = 1; i <= rowcount; i++) {
			HSSFRow row = ExcelWSheet.getRow(i);

			if (row != null) {
				int firstSell = row.getFirstCellNum();
				int cellNum = row.getLastCellNum();
				numberOfCellsInRow = cellNum - firstSell;

				for (int j = 0; j < cellNum; j++) {
					HSSFCell cell = row.getCell(j);
					String value = formatter.formatCellValue(cell);
					allValues.add(value);
				}

			} else {
				System.out.println("<Empty row>");
			}
		}
	}

	public static int getLastRow() {
		int rowcount = ExcelWSheet.getLastRowNum();
		return rowcount;
	}

//	public static void getAllValuesFromExcel2() throws IOException {
//
//		Iterator rowIter = ExcelWSheet.rowIterator();
//
//		while (rowIter.hasNext()) {
//			HSSFRow myRow = (HSSFRow) rowIter.next();
//			Iterator cellIter = myRow.cellIterator();
//			Vector cellStoreVector = new Vector();
//			while (cellIter.hasNext()) {
//				HSSFCell myCell = (HSSFCell) cellIter.next();
//				cellStoreVector.addElement(myCell);
//			}
//			cellVectorHolder.addElement(cellStoreVector);
//		}
//
//	}
//
//	public static void getCellData(Vector dataHolder) {
//
//		for (int i = 0; i < dataHolder.size(); i++) {
//			Vector cellStoreVector = (Vector) dataHolder.elementAt(i);
//			for (int j = 0; j < cellStoreVector.size(); j++) {
//				HSSFCell myCell = (HSSFCell) cellStoreVector.elementAt(j);
//				String stringCellValue = myCell.toString();
//				System.out.print(stringCellValue + "\t");
//			}
//			System.out.println();
//		}
//
//	}
}
