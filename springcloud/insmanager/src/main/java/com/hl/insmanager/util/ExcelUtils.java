package com.hl.insmanager.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * @author ivan.huang
 * @date 2016年11月18日
 * 
 */
public class ExcelUtils {

	/**
	 * 格式化表格数据
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String cellValue = null;
		if (cell != null) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			int cellType = cell.getCellType();

			switch (cellType) {
			case Cell.CELL_TYPE_STRING: // 文本
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC: // 数字、日期
				if (DateUtil.isCellDateFormatted(cell)) {
					cellValue = fmt.format(cell.getDateCellValue()); // 日期型
				} else {
					DecimalFormat df = new DecimalFormat("0");
					cellValue = df.format(cell.getNumericCellValue()); // 数字
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN: // 布尔型
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_BLANK: // 空白
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_ERROR: // 错误
				cellValue = "error";
				break;
			case Cell.CELL_TYPE_FORMULA: // 公式
				cellValue = "error";
				break;
			default:
				cellValue = "error";
			}
		}
		return cellValue;
	}

}
