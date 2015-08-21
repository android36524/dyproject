package com.dayang.commons.util;

import com.dayang.commons.pojo.ExcelImportErrorPojo;
import com.dayang.commons.pojo.ImportPojo;
import com.jfinal.plugin.activerecord.Model;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类描述：excel工具类
 * <pre>
 * -------------History------------------
 *   DATE                 AUTHOR         VERSION        DESCRIPTION
 *  2015年06月02日            刘建宇      V01.00.001		   新增内容
 * </pre>
 *
 * @author <a href="mailto:liujy@dayanginfo.com">刘建宇</a>
 */
public class ExcelUtil {


    /**
     * 解析xlsx模板文件
     * @param targetFile 模板文件File对象
     * @param pojoClassName 模板文件对应的pojo实体类名
     * @return 模板文件实体类集合
     */
    public static List<ImportPojo> parseXlsx(File targetFile,String pojoClassName){
        XSSFWorkbook xssfWorkbook;
        try {
            xssfWorkbook = new XSSFWorkbook(new FileInputStream(targetFile));
        } catch (IOException e) {
            throw new RuntimeException("文件加载异常....");
        }
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        int rows = xssfSheet.getLastRowNum();
        List<ImportPojo> result = new ArrayList<>();
        for (int i = 1; i <= rows; i++){
            XSSFRow row = xssfSheet.getRow(i);
            if (row != null){
                int cells = row.getLastCellNum();
                ImportPojo importPojo = ImportPojoFactory.create(pojoClassName);
                List<String> cellValueList = new ArrayList<>();
                for (int x = 0; x < cells; x++){
                    XSSFCell cell = row.getCell(x);
                    if (cell != null){
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    String cellValue = cell == null? "" : cell.getStringCellValue();
                    if (cellValue.equals("")){
                        cellValue = null;
                    }
                    cellValueList.add(cellValue);
                }
                importPojo = CommonUtil.setBeanPropertyValue(importPojo,cellValueList);
                importPojo.setRowNumber(i + 1);
                result.add(importPojo);
            }
        }
        return result;
    }

    /**
     * 错误信息写入Excel文件
     * @param targetFile 写入Excel File对象
     * @param errorPojoList 错误信息List
     */
    public static void writeError2Excel(File targetFile,List<ExcelImportErrorPojo> errorPojoList){
        if (errorPojoList == null || errorPojoList.size() == 0){
            return;
        }
        try {
            WritableWorkbook wwb = Workbook.createWorkbook(targetFile);
            WritableSheet sheet = wwb.createSheet("错误信息", 0);
            Label oneCell = new Label(0, 0, "行");
            Label twoCell = new Label(1, 0, "错误信息");
            sheet.addCell(oneCell);
            sheet.addCell(twoCell);
            int autoUpdate = 1;
            for (ExcelImportErrorPojo excelImportErrorPojo : errorPojoList){
                List<String> errorStrList = excelImportErrorPojo.getErrorMsg();
                for (String errorStr : errorStrList){
                    Label tempOne = new Label(0,autoUpdate,"第" + excelImportErrorPojo.getRowNumber() + "行");
                    Label tempTwo = new Label(1,autoUpdate,errorStr);
                    sheet.addCell(tempOne);
                    sheet.addCell(tempTwo);
                    autoUpdate++;
                }
            }
            wwb.write();
            wwb.close();
        } catch (WriteException | IOException e) {
            throw new RuntimeException("错误信息写入文件失败");
        }
    }

    /**
     * 数据源写入excel文件
     * @param dataSource 数据源
     * @param targetFile 写入目标文件File
     * @param importPojo 模板文件对应pojo对象
     */
    public static void exportExcel(List<? extends Model> dataSource,File targetFile, ImportPojo importPojo){
        int row = 0;
        List<String> contentList = getExcelRowContent(importPojo.getTemplatePath(), row);
        try {
            WritableWorkbook wwb = Workbook.createWorkbook(targetFile);
            WritableSheet sheet = wwb.createSheet("Sheet1", 0);
            for (int x = 0; x < contentList.size(); x++){
                Label temp = new Label(x,row,contentList.get(x));
                sheet.addCell(temp);
            }
            Map<Integer,String> relation = importPojo.getMap();
            for (int i = 0; i < dataSource.size(); i++){
                int rowNumber = i + 1;
                int relationSize = relation.size();
                for (int x = 0; x < relationSize; x++){
                    String columnName = relation.get(x);
                    Object columnValue = dataSource.get(i).get(columnName);
                    if(dataSource.get(i).get(columnName+"_showname")!=null)
                        columnValue=dataSource.get(i).get(columnName+"_showname");
                    Label temp = new Label(x,rowNumber,columnValue == null?"":columnValue.toString());
                    sheet.addCell(temp);
                }
            }
            wwb.write();
            wwb.close();
        } catch (IOException | WriteException e) {
            throw new RuntimeException("源数据写入excel文件失败");
        }

    }

    /**
     * 获取excel指定行内容 必须为文本
     * @param templatePath excel文件路径
     * @param rowNumber 行号
     * @return List
     */
    private static List<String> getExcelRowContent(String templatePath, int rowNumber) {
        List<String> contentList = new ArrayList<>();
        File templateFile = new File(templatePath);
        XSSFWorkbook templateBook;
        try {
            templateBook = new XSSFWorkbook(new FileInputStream(templateFile));
        } catch (IOException e) {
            throw new RuntimeException("加载模板文件失败");
        }
        XSSFSheet templateSheet = templateBook.getSheetAt(0);
            XSSFRow templateRowOne = templateSheet.getRow(rowNumber);
            if (templateRowOne == null){
                return contentList;
            }
            int templateCellNumber = templateRowOne.getLastCellNum();
            for (int x = 0; x<templateCellNumber; x++){
                XSSFCell cell = templateRowOne.getCell(x);
                String content = cell.getStringCellValue();
                contentList.add(content);
        }
        return contentList;
    }

}
