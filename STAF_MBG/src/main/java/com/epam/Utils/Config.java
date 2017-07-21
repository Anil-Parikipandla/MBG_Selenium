package com.epam.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by spoduri on 28-12-2016.
 */
public class Config {

   private static Properties objProperties;
    private static void ReadConfig()
    {

        try {
            objProperties = new Properties();
            FileInputStream inputstream = new FileInputStream(new File(System.getProperty("user.dir")+"//src//main//resources//config.properties"));
            if (inputstream != null) {

                objProperties.load(inputstream);

            }
        } catch (Exception e) {
            System.out.print("");

        }

    }

    public static String GetConfigProperty(String strPropertyName)
    {
        String strPropertyValue= null;
        try
        {
            ReadConfig();
            strPropertyValue=  objProperties.getProperty(strPropertyName);
            if(strPropertyValue == null)
            {
                throw new Exception();

            }
        }
        catch(Exception e)
        {
            System.out.print("");
        }
        return strPropertyValue;
    }
}
