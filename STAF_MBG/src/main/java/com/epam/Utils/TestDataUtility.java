package com.epam.Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by spoduri on 28-12-2016.
 */
public class TestDataUtility {

    private static Workbook workbook = null;
    public static FileInputStream fis = null;
    public static FileOutputStream fos = null;

    public TestDataUtility(){

        try {

            fis = new FileInputStream(Config.GetConfigProperty("TestData"));
            if(Config.GetConfigProperty("TestData").endsWith(".xls"))
            {
                workbook = new HSSFWorkbook(fis);
            }
            else if(Config.GetConfigProperty("TestData").endsWith(".xlsx"))
            {
                workbook = new XSSFWorkbook(fis);
            }
            else
            {
                throw new Exception();
            }
            fis.close();

        }
        catch (Exception e)
        {

        }


    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>> getEntityData(String senarioName,String sheetName)
            throws Exception {
        LinkedHashMap<String, LinkedHashMap<String, String>> scenarioDataMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> rowdata = new LinkedHashMap<String, String>();

        try {

            Row headerRow = null;
            Row tempRow = null;

            // Get first sheet from the workbook
            Sheet sheet = workbook.getSheet(sheetName);

            String iteration;
            headerRow = sheet.getRow(0);
            for (int row =  1; row <= sheet.getLastRowNum(); row++) // for iterating through Rows
            {
                tempRow = sheet.getRow(row);

                if (tempRow!=null && tempRow.getCell(0).toString().equalsIgnoreCase(senarioName) && tempRow.getCell(4).toString().equalsIgnoreCase("Y"))
                {
                    rowdata = new LinkedHashMap<String, String>();
                    iteration = String.valueOf(row);

                    for (int col = (0); col < tempRow.getLastCellNum(); col++) //For iterating through Columns                    {
                    {
                        if(tempRow.getCell(col) == null)
                        {
                            rowdata.put(headerRow.getCell(col).toString(), "");
                        }
                        else
                        {
                            String strValue = tempRow.getCell(col).toString();
                            //Get date
                            if (strValue.toUpperCase().startsWith("CDATE_")) {
                                //strValue = getDate(strValue);
                            }

                            //Get time
                            if (strValue.toUpperCase().startsWith("CTIME_")) {
                                // strValue = getTime(strValue);
                            }

                            //Get unique data with timestamp
                            if (strValue.toUpperCase().startsWith("UNIQUE")) {
                                // strValue = getUniqueValue(strValue);
                            }

                            rowdata.put(headerRow.getCell(col).toString(), strValue);
                        }
                    }

                    scenarioDataMap.put(iteration, rowdata);
                }

            }
        }
        catch(NullPointerException ne)
        {
            System.out.println("Exception while reading test data from sheet "+sheetName);
            throw ne;
        }
        catch(Exception e)
        {
            throw e;
        }
        return scenarioDataMap;
    }


    public static LinkedHashMap<String, String> getEntityData(String senarioName, String sheetName, int row)
            throws Exception {

        LinkedHashMap<String, String> rowdata = new LinkedHashMap<String, String>();
        try {

            Row headerRow = null;
            Row tempRow = null;

            // Get first sheet from the workbook
            Sheet sheet = workbook.getSheet(sheetName);
            headerRow = sheet.getRow(0);
            tempRow = sheet.getRow(row);
            if (tempRow != null)
            {
                for (int col = (0); col < tempRow.getLastCellNum(); col++) //For iterating through Columns                    {
                {
                    if (tempRow.getCell(col) == null) {
                        rowdata.put(headerRow.getCell(col).toString(), "");
                    } else {
                        String strValue = tempRow.getCell(col).toString();
                        //Get date
                        if (strValue.toUpperCase().startsWith("CDATE_")) {
                            //strValue = getDate(strValue);
                        }

                        //Get time
                        if (strValue.toUpperCase().startsWith("CTIME_")) {
                            // strValue = getTime(strValue);
                        }

                        //Get unique data with timestamp
                        if (strValue.toUpperCase().startsWith("UNIQUE")) {
                            // strValue = getUniqueValue(strValue);
                        }

                        rowdata.put(headerRow.getCell(col).toString(), strValue);
                    }
                }


            }
            else
            {
                throw new Exception();
            }


        }
        catch(NullPointerException ne)
        {
            System.out.println("Exception while reading test data from sheet "+sheetName);
            throw ne;
        }
        catch(Exception e)
        {
            throw e;
        }
        return rowdata;
    }

    public void WriteDataToExcel(String strSheetName,String ColumnName,String Value,int RowNumber) throws IOException {
        Sheet sheet = workbook.getSheet(strSheetName);
        String[] strColumns = ColumnName.split(";");
        String[] strValues = Value.split(";");
        try {


            if (strColumns.length == strValues.length) {
                Row objTempRow = sheet.getRow(RowNumber);
                Row objHeaderRow = sheet.getRow(0);
                {
                    if (objTempRow == null) {
                        objTempRow = sheet.createRow(RowNumber);
                    }

                    for (int i = 0; i < objHeaderRow.getLastCellNum(); i++) {
                        for (int j = 0; j < strColumns.length; j++) {
                            if (objHeaderRow.getCell(i).toString().equalsIgnoreCase(strColumns[j])) {
                                Cell objCell = objTempRow.getCell(i);
                                if (objCell == null) {
                                    objCell = objTempRow.createCell(i);
                                }
                                objCell.setCellValue(strColumns[j]);
                            }
                        }
                    }
                }
            }
            fis.close();
            fos = new FileOutputStream(Config.GetConfigProperty("TestData"),true);
            fos.flush();
            workbook.write(fos);

            fos.close();

        }
        catch (Exception e)
        {
            System.out.println("exception while writing the data into sheet");
        }
    }

    }
