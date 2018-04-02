package com.telecom.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传工具类  上传类型：Excel、txt、csv
 * @author Administrator
 *
 */
public class UploadFileUtils {

	/**
	 * 解析Excel
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("resource") 
	public static Map<Integer, List<String>> parseExcel(MultipartFile file) throws Exception{
		if (file == null)
			return null;
		String name = file.getOriginalFilename();
		InputStream is = file.getInputStream();
		String endflag = "数据结束";
		boolean flag = true;
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		if (name.endsWith(".xlsx")) {
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// 循环工作表Sheet //xssfWorkbook.getNumberOfSheets(),只读一个sheet表格
			for (int numSheet = 0; numSheet < 1; numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// 循环列column
				OK: for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow == null) {
						List<String> rowdata = new ArrayList<String>();
						// rowdata.add("xixi");
						rowdata.add(Integer.valueOf(numSheet).toString());
						map.put(100002, rowdata);

						continue;
					}
					//读取第一行
//					XSSFCell first = xssfRow.getCell(0);
//					String firstRow = QString.EMPTY;
//
//					if (first != null) {
//						first.setCellType(Cell.CELL_TYPE_STRING);
//						firstRow = first.getStringCellValue();
//					}
//					String[] arr=firstRow.split(",");
//					List<String> column = Arrays.asList(arr);
					// 循环列
					List<String> rowdata = new ArrayList<String>();
					List<String> logs = new ArrayList<>();
					for (int i = 1; i <= xssfRow.getLastCellNum(); i++)// 读取行
					{
						XSSFCell brandIdXSSFCell = xssfRow.getCell(i);
						String content = QString.EMPTY;

						if (brandIdXSSFCell != null) {
							brandIdXSSFCell.setCellType(Cell.CELL_TYPE_STRING);
							content = brandIdXSSFCell.getStringCellValue();
						}
						// CELL_TYPE_NUMERIC 数值型 0
						// CELL_TYPE_STRING 字符串型 1
						// CELL_TYPE_FORMULA 公式型 2
						// CELL_TYPE_BLANK 空值 3
						// CELL_TYPE_BOOLEAN 布尔型 4
						// CELL_TYPE_ERROR 错误 5
						rowdata.add(content);
						logs.add(String.format("[%s,%s]=>%s", rowNum, i, content));
						if (QString.join(rowdata.toArray()).trim().equals(endflag)) {
							flag = false;
							map.remove(rowNum);
							System.out.println(QString.join(logs.toArray()));
							break OK;
						}
						map.put(rowNum, rowdata);
					}

				}
				if (flag) {
					List<String> rowdata = new ArrayList<String>();
					// rowdata.add("hehe");
					map.put(100001, rowdata);
				}
			}
		} else {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			// 循环工作表Sheet //hssfWorkbook.getNumberOfSheets() 只读一个sheet表格
			for (int numSheet = 0; numSheet < 1; numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				OK:
				// 循环行Row
				for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						List<String> rowdata = new ArrayList<String>();
						// rowdata.add("xixi");
						rowdata.add(Integer.valueOf(numSheet).toString());
						map.put(100002, rowdata);
						continue;
					}
					// 循环列
					List<String> listStr = new ArrayList<String>();
					List<String> logs = new ArrayList<>();
					for (int i = 0; i <= hssfRow.getLastCellNum(); i++) {
						HSSFCell brandIdHSSFCell = hssfRow.getCell(i);
						String content = QString.EMPTY;

						if (brandIdHSSFCell != null) {
							brandIdHSSFCell.setCellType(Cell.CELL_TYPE_STRING);
							content = brandIdHSSFCell.getStringCellValue();
						}
						// CELL_TYPE_NUMERIC 数值型 0
						// CELL_TYPE_STRING 字符串型 1
						// CELL_TYPE_FORMULA 公式型 2
						// CELL_TYPE_BLANK 空值 3
						// CELL_TYPE_BOOLEAN 布尔型 4
						// CELL_TYPE_ERROR 错误 5
						listStr.add(content);
						logs.add(String.format("[%s,%s]=>%s", rowNum, i, content));
						// if (brandIdHSSFCell == null) {
						// continue;
						// }
						//
						// if (brandIdHSSFCell.getCellType() == 0) {
						// brandIdHSSFCell
						// .setCellType(Cell.CELL_TYPE_STRING);
						// listStr.add(brandIdHSSFCell
						// .getStringCellValue());
						// } else if (brandIdHSSFCell.getCellType() == 1) {
						// listStr.add(brandIdHSSFCell
						// .getStringCellValue());
						// }
						System.out.println(QString.join(logs.toArray()));
						if (QString.join(listStr.toArray()).trim().equals(endflag)) {
							flag = false;
							map.remove(rowNum);
							break OK;
						}
						// if (brandIdHSSFCell.getStringCellValue().trim()
						// .equals("数据结束")) {
						// flag=false;
						// break OK;
						// }
						//
						map.put(rowNum, listStr);
					}
				}
				if (flag) {
					List<String> rowdata = new ArrayList<String>();
					// rowdata.add("hehe");
					map.put(100001, rowdata);
				}
			}
		}
		return map;
	}
}
