/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sigeve;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/* JList contendo elementos JCheckBox
 * ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯
 * Descr: "Handles rendering cells in the list using a check box"
 * Fonte: http://helpdesk.objects.com.au/java/how-do-add-a-checkbox-to-items-in-a-jlist
 *
 * @author Paulo Henrique
 */
public class CheckListRenderer
    extends JCheckBox
    implements ListCellRenderer
{
    public Component getListCellRendererComponent(
        JList list, Object value, int index,
        boolean isSelected, boolean hasFocus )
    {
        setEnabled( list.isEnabled() );
        setSelected( (( CheckListItem ) value).isSelected() );
        setFont( list.getFont() );
        setBackground( list.getBackground() );
        setForeground( list.getForeground() );
        setText( value.toString() );
        return this;
    }
}
