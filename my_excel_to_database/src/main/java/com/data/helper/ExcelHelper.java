package com.data.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.data.model.TeamStructure;

public class ExcelHelper {
	  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "No", "Name", "StaffID", "Team","Project","Position" };
	  static String SHEET = "Sheet1";
	  public static boolean hasExcelFormat(MultipartFile file) {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	    return true;
	  }
	  public static List<TeamStructure> excelToTutorials(InputStream is) {
	    try {
	      Workbook workbook = new XSSFWorkbook(is);
	      Sheet sheet = workbook.getSheet(SHEET);
	      Iterator<Row> rows = sheet.iterator();
	      List<TeamStructure> teamStructureList = new ArrayList<TeamStructure>();
	      int rowNumber = 0;
	      while (rows.hasNext()) {
	        Row currentRow = rows.next();
	        // skip header
	        if (rowNumber == 0) {
	          rowNumber++;
	          continue;
	        }
	        Iterator<Cell> cellsInRow = currentRow.iterator();
	        TeamStructure teamStructure = new TeamStructure();
	        int cellIdx = 0;
	        while (cellsInRow.hasNext()) {
	          Cell currentCell = cellsInRow.next();
	          switch (cellIdx) {
	          case 0:
	        	  teamStructure.setId((long) currentCell.getNumericCellValue());
	            break;
	          case 1:
	        	  teamStructure.setName(currentCell.getStringCellValue());
	            break;
	          case 2:
	        	  teamStructure.setStaffId(currentCell.getStringCellValue());
	            break;
	          case 3:
	        	  teamStructure.setTeam(currentCell.getStringCellValue());
	            break;
	          case 4:
	        	  teamStructure.setProject(currentCell.getStringCellValue());
	            break;
	          case 5:
	        	  teamStructure.setPosition(currentCell.getStringCellValue());
	            break;
	          default:
	            break;
	          }
	          cellIdx++;
	        }
	        teamStructureList.add(teamStructure);
	      }
	      workbook.close();
	      return teamStructureList;
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
	    }
	  }

}
