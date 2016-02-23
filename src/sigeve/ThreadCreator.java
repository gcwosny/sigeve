/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sigeve;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paulo Henrique
 */
public class ThreadCreator
    implements Runnable
{
    SigeveView sv;
    Method metodo;
    Object[] args;

    public ThreadCreator( SigeveView sv, Method metodo, Object[] args )
    {
        this.sv = sv;
        this.metodo = metodo;
        this.args = args;
    }

    public void run()
    {
        try
        {
            metodo.invoke( sv, args );
        }
        catch ( IllegalAccessException ex )
        {
            Logger.getLogger( ThreadCreator.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( IllegalArgumentException ex )
        {
            Logger.getLogger( ThreadCreator.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( InvocationTargetException ex )
        {
            Logger.getLogger( ThreadCreator.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }
}
