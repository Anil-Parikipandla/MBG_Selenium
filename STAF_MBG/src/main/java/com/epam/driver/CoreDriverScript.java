package com.epam.driver;

import com.epam.Reports.Reporter;
import com.epam.Utils.Config;
import com.epam.Utils.TestDataUtility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.Assertion;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by spoduri on 28-12-2016.
 //Description
 */
public class CoreDriverScript {

    public static List<ExtentTest> objChildren ;
    public final static SetUpSelenium setupSelenium = new SetUpSelenium();
    public static TestDataUtility dataReader = new TestDataUtility();
    public static WebDriver driver;
    public static int currentiterationrow;
    static  Workbook workbook;
    static Sheet sheet;
    public static  String scenario = "";



    @BeforeClass
    public void setup() throws Exception
    {
        try {


            setupSelenium.initializeDriver(Config.GetConfigProperty("BrowserType"));
            driver = setupSelenium.getDriver();
            setupSelenium.gotoUrl(Config.GetConfigProperty("AppURL"));
            //Reporter.StartReporting();
            Reporter.objExtentReports.loadConfig(new File(ClassLoader.getSystemClassLoader().getResource("extent-config.xml").getPath().toString()));

        }
        catch(Exception e)
        {
            System.out.print("exception here");
        }

    }

    @AfterClass
    public void stopTest() {
        try {

            Reporter.StopReporting(objChildren);
            objChildren.clear();
            setupSelenium.stopEngine();


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private static void executeTestCases(int testScenarioCount) throws ClassNotFoundException, Exception
    {
        try{
            LinkedHashMap<String, LinkedHashMap<String, String>> scenarioDataMap = null;
            for (int TestScenarioID = 1; TestScenarioID <= testScenarioCount; TestScenarioID++) {

                if (sheet.getRow(TestScenarioID).getCell(1).toString().equalsIgnoreCase("Y")) {
                    scenario = sheet.getRow(TestScenarioID).getCell(0).toString();
                    if (scenario.equalsIgnoreCase("NewBusiness")) {
                        scenarioDataMap = dataReader.getEntityData(scenario, scenario);//Scenario name and Sheet name should be equal
                    }

                    //enterset will return map in the form of set ,each element in the set is the combination of key and value in the map.
                    //entry.getkey() will return map's key and entry.getValue() returns map's value.
                    for (Map.Entry<String, LinkedHashMap<String, String>> entry : scenarioDataMap.entrySet())
                    {
                        if (!entry.getValue().get("EXECUTIONFLAG").equalsIgnoreCase("Y")) {
                            continue;
                        }
                        objChildren = new ArrayList<ExtentTest>(); //for Reporing childtest steps.
                        TestNG testng = new TestNG();

                        //Adding Scenario Class to execute to Testng object
                        currentiterationrow = Integer.parseInt(entry.getKey());
                        Class[] objSecnarioToExecute = new Class[1];
                        objSecnarioToExecute[0]=Class.forName("com.epam.scenarios."+scenario);
                        testng.setTestClasses(objSecnarioToExecute);
                        testng.setPreserveOrder(true);
                        testng.run();

                    }
                }
            }

        }catch(Exception e)
        {
            System.out.println("Error occured in CoreDriverScript");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {


        try {

            FileInputStream fis = new FileInputStream(Config.GetConfigProperty("TestData"));
            System.out.print(Config.GetConfigProperty("AppURL"));

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

            sheet = workbook.getSheet("Scenarios");
            int testScenarioCount = sheet.getLastRowNum();
            executeTestCases(testScenarioCount);
        }
        catch (Exception e) {

            e.printStackTrace();
        } finally {
            System.out.println("End of Execution!!!!!!!!");
        }

    }
//adding for comment
}
