/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sigeve;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo Henrique
 */
public class ConversorFormato
{
    /**
     * @see http://en.wikipedia.org/wiki/ISO_8601
     * @see http://www.sqlite.org/lang_datefunc.html
     * @param dataHora
     * @return String no formato ISO 8601 (yyyy-MM-dd HH:mm)
     */
    public static String dataHoraBrParaIso( String dataHora )
    {
        try
        {
            DateFormat dfBr = new SimpleDateFormat( "dd/MM/yyyy HH:mm" );
            DateFormat dfIso = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            return dfIso.format( dfBr.parse( dataHora ) );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( CurrentDateAndTime.class.getName() ).log( Level.SEVERE, null, ex );
            return "";
        }
    }

    public static String dataIsoParaBr( String data )
    {
        try
        {
            DateFormat dfIso = new SimpleDateFormat( "yyyy-MM-dd" );
            DateFormat dfBr = new SimpleDateFormat( "dd/MM/yyyy" );
            return dfBr.format( dfIso.parse( data ) );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( ConversorFormato.class.getName() ).log( Level.SEVERE, null, ex );
            return "";
        }
    }

    /**
     *
     * @param dataHora String no formato ISO 8601 (yyyy-MM-dd HH:mm)
     * @return String no formato HH:mm
     */
    public static String dataHoraIsoParaHora( String dataHora )
    {
        try
        {
            DateFormat dfIso = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            DateFormat dfBr = new SimpleDateFormat( "HH:mm" );
            return dfBr.format( dfIso.parse( dataHora ) );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( ConversorFormato.class.getName() ).log( Level.SEVERE, null, ex );
            return "";
        }
    }

    /**
     *
     * @param dataHora
     * @return Dia da semana
     */
    public static String dataHoraIsoParaDia( String dataHora )
    {
        try
        {
            DateFormat dfIso = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            DateFormat dfDia = new SimpleDateFormat( "EEEE" );
            return dfDia.format( dfIso.parse( dataHora ) );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( ConversorFormato.class.getName() ).log( Level.SEVERE, null, ex );
            return null;
        }
    }
}
