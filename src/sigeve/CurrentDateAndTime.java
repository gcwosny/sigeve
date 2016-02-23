/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sigeve;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Paulo Henrique
 */
public class CurrentDateAndTime
{
    public static String getDate()
    {

        DateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
        //get current date time with Date()
        Calendar cal = Calendar.getInstance();
        return dateFormat.format( cal.getTime() );
    }

    public static String getTime()
    {

        DateFormat dateFormat = new SimpleDateFormat( "HH:mm" );
        //get current date time with Date()
        Calendar cal = Calendar.getInstance();
        return dateFormat.format( cal.getTime() );
    }

    public static String getDay()
    {
        DateFormat dateFormat = new SimpleDateFormat( "EEEE" );
        //get current date time with Date()
        Calendar cal = Calendar.getInstance();
        return dateFormat.format( cal.getTime() );
    }
}
