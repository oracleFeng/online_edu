package com.online.microservice.Utills;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelImportUtil {

    private XSSFFormulaEvaluator formulaEvaluator;
    private XSSFSheet sheet;
    private String pattern;// 日期格式

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public XSSFFormulaEvaluator getFormulaEvaluator() {
        return formulaEvaluator;
    }

    public void setFormulaEvaluator(XSSFFormulaEvaluator formulaEvaluator) {
        this.formulaEvaluator = formulaEvaluator;
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(XSSFSheet sheet) {
        this.sheet = sheet;
    }

    public ExcelImportUtil() {
        super();
    }

    public ExcelImportUtil(InputStream is) throws IOException {
        this(is, 0, true);
    }

    public ExcelImportUtil(InputStream is, int seetIndex) throws IOException {
        this(is, seetIndex, true);
    }

    public ExcelImportUtil(InputStream is, int seetIndex, boolean evaluateFormular) throws IOException {
        super();
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        this.sheet = workbook.getSheetAt(seetIndex);
        if (evaluateFormular) this.formulaEvaluator = new XSSFFormulaEvaluator(workbook);
    }

    public String getCellValue(Cell cell, int cellType) throws Exception {

        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC://0

                if (HSSFDateUtil.isCellDateFormatted(cell)) {//日期
                    Date date = cell.getDateCellValue();
                    if (pattern != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        return sdf.format(date);
                    } else {
                        return date.toString();
                    }
                } else {
                    // 不是日期格式，则防止当数字过长时以科学计数法显示
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    return cell.toString();
                }

            case Cell.CELL_TYPE_STRING://1
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_FORMULA://2

                if (this.formulaEvaluator == null) {//得到公式
                    return cell.getCellFormula();
                } else {//计算公式
                    CellValue evaluate = this.formulaEvaluator.evaluate(cell);
                    cellType = evaluate.getCellType();
                    return String.valueOf(this.getCellValue(cell, cellType));
                }
            case Cell.CELL_TYPE_BLANK://3
                //注意空和没有值不一样，从来没有录入过内容的单元格不属于任何数据类型，不会走这个case
                return "";
            case Cell.CELL_TYPE_BOOLEAN://4
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_ERROR:
                throw new Exception("数据类型错误");
            default:
                throw new Exception("未知数据类型:" + cellType);
        }
    }
}
