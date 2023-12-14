/*
-> Pavan bu Utility classını hazır olarak paylaştı, biz yazmadık
   (45. dersteki 7.adım olan "Data Driven Login Test" için).
-> Pavan, bu Utility classındaki metotları açıklamadı.
   Sadece, öncekilerden farklı olarak metotların static olmadığını söyledi.
*/

package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility  // İkinci Utility File/Class.
{
	public FileInputStream fileInput;  // Pavan "fi" olarak isimlendirmişti.
	public FileOutputStream fileOutput;  // Pavan "fo" olarak isimlendirmişti.
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;
	
	public ExcelUtility (String path)  // Excel belgesinin dosya yolu.
	{
		this.path = path;
	}
		
	public int getRowCount (String sheetName) throws IOException
	{
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		workbook.close();
		fileInput.close();

		return rowCount;
	}
	
	public int getCellCount (String sheetName,int rownum) throws IOException
	{
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int cellCount = row.getLastCellNum();
		workbook.close();
		fileInput.close();

		return cellCount;
	}
	
	public String getCellData (String sheetName,int rownum,int colnum) throws IOException
	{
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		
		DataFormatter formatter = new DataFormatter();

		String data;
		try
		{
			data = formatter.formatCellValue(cell); //Returns the formatted value of a cell as a String regardless of the cell type.
		}
		catch (Exception e)
		{
			data = "";
		}

		workbook.close();
		fileInput.close();

		return data;
	}
	
	public void setCellData (String sheetName,int rownum,int colnum,String data) throws IOException
	{
		File xlFile = new File(path);

		if (!xlFile.exists())  // If file not exists then create new file.
		{
		workbook = new XSSFWorkbook();
		fileOutput = new FileOutputStream(path);
		workbook.write(fileOutput);
		}
				
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);

		// Bu bir if bloğu; ancak Pavan nedense böyle yazmış, "{}" işaretlerini kullanmamış.
		if (workbook.getSheetIndex(sheetName) == -1)  // If sheet not exists then create new Sheet
			workbook.createSheet(sheetName);
			sheet = workbook.getSheet(sheetName);

		// Bu bir if bloğu; ancak Pavan nedense böyle yazmış, "{}" işaretlerini kullanmamış.
		if (sheet.getRow(rownum) == null)   // If row not exists then create new Row
			sheet.createRow(rownum);
			row = sheet.getRow(rownum);
		
		cell = row.createCell(colnum);
		cell.setCellValue(data);
		fileOutput = new FileOutputStream(path);
		workbook.write(fileOutput);
		workbook.close();
		fileInput.close();
		fileOutput.close();
	}

	public void fillGreenColor (String sheetName,int rownum,int colnum) throws IOException
	{
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);
		sheet = workbook.getSheet(sheetName);
		
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		
		style = workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
				
		cell.setCellStyle(style);
		workbook.write(fileOutput);
		workbook.close();
		fileInput.close();
		fileOutput.close();
	}
	
	public void fillRedColor (String sheetName,int rownum,int colnum) throws IOException
	{
		fileInput = new FileInputStream(path);
		workbook = new XSSFWorkbook(fileInput);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		
		style = workbook.createCellStyle();
		
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		
		cell.setCellStyle(style);		
		workbook.write(fileOutput);
		workbook.close();
		fileInput.close();
		fileOutput.close();
	}
	
}