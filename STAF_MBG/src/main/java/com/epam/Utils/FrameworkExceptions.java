package com.epam.Utils;

import com.relevantcodes.extentreports.ExtentTest;

import java.util.List;

/**
 * Created by spoduri on 28-12-2016.
 */
public class FrameworkExceptions extends Exception{


    public FrameworkExceptions()
    {
        super();
    }

    public FrameworkExceptions(String strException)
    {
        super(strException);
    }

    public FrameworkExceptions(String strException, List<ExtentTest> objChildren)
    {

    }
}
