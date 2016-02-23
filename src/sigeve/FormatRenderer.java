package sigeve;

import java.text.Format;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/*
 *  Use a formatter to format the cell Object
 *  Fonte: http://www.camick.com/java/source/FormatRenderer.java
 */
public class FormatRenderer
    extends DefaultTableCellRenderer
{
    private Format formatter;

    /*
     *   Use the specified formatter to format the Object
     */
    public FormatRenderer( Format formatter, int HorAlign )
    {
        this.formatter = formatter;
        setHorizontalAlignment( HorAlign );
    }

    @Override
    public void setValue( Object value )
    {
        //  Format the Object before setting its value in the renderer

        try
        {
            if ( value != null )
            {
                value = formatter.format( value );
            }
        }
        catch ( IllegalArgumentException e )
        {
        }

        super.setValue( value );
    }

    /*
     *  Use the default date/time formatter for the default locale
     */
    public static FormatRenderer getDateTimeRenderer()
    {
        return new FormatRenderer(
            DateFormat.getDateTimeInstance(),
            SwingConstants.LEFT );
    }

    /*
     * Use the default time formatter for the default locale
     */
    public static FormatRenderer getTimeRenderer()
    {
        return new FormatRenderer(
            DateFormat.getTimeInstance(),
            SwingConstants.LEFT );
    }

    /*
     * Use the default currency formatter for the default locale
     */
    public static FormatRenderer getCurrencyRenderer()
    {
        return new FormatRenderer(
            NumberFormat.getCurrencyInstance(),
            SwingConstants.RIGHT );
    }

    /*
     * Use the default integer formatter for the default locale
     */
    public static FormatRenderer getIntegerRenderer()
    {
        return new FormatRenderer(
            NumberFormat.getIntegerInstance(),
            SwingConstants.RIGHT );
    }

    /*
     * Use the default percent formatter for the default locale
     */
    public static FormatRenderer getPercentRenderer()
    {
        return new FormatRenderer(
            NumberFormat.getPercentInstance(),
            SwingConstants.RIGHT );
    }

    public static FormatRenderer getFloat3CasasRenderer()
    {
        return new FormatRenderer(
            new DecimalFormat( "#,##0.000" ),
            SwingConstants.RIGHT );
    }

    public static FormatRenderer getStringCenterRenderer()
    {
        return new FormatRenderer(
            null,
            SwingConstants.CENTER );
    }
}

