/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TelaAbastec.java
 *
 * Created on 02/02/2011, 23:37:28
 */

package sigeve;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;

/**
 *
 * @author Paulo Henrique
 */
public class TelaAbastec extends javax.swing.JDialog {

    private BancoDeDados bd;
    private ListaSolicitacoes lista;
    private Jornada jornadaAtual;
    
    /** Creates new form TelaAbastec
     * @param parent
     * @param modal
     * @param jornadaAtual
     * @param bd  
     */
    public TelaAbastec( 
        SigeveView parent, 
        boolean modal, 
        Jornada jornadaAtual, 
        BancoDeDados bd )
    {
        super( parent.getFrame(), modal );
        initComponents();
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        this.bd = bd;
        lista = new ListaSolicitacoes();
        obterListaSolicitacoes();
        
        jTextField1.setText( jornadaAtual.getLoja().getNome() );
        jTextField2.setText( jornadaAtual.getDataAbertura() );
    }
    
    public void atualizarListaSolicitacoes()
    {
        lista.setCb001( jCheckBox1.isSelected() );
        lista.setCb002( jCheckBox2.isSelected() );
        lista.setCb003( jCheckBox3.isSelected() );
        lista.setCb004( jCheckBox4.isSelected() );
        lista.setCb005( jCheckBox5.isSelected() );
        lista.setCb006( jCheckBox6.isSelected() );
        lista.setCb007( jCheckBox7.isSelected() );
        lista.setCb008( jCheckBox8.isSelected() );
        lista.setCb009( jCheckBox9.isSelected() );
        lista.setCb010( jCheckBox10.isSelected() );
        lista.setCb011( jCheckBox11.isSelected() );
        lista.setCb012( jCheckBox12.isSelected() );
        lista.setCb013( jCheckBox13.isSelected() );
        lista.setCb014( jCheckBox14.isSelected() );
        lista.setCb015( jCheckBox15.isSelected() );
        lista.setCb016( jCheckBox16.isSelected() );
        lista.setCb017( jCheckBox17.isSelected() );
        lista.setCb018( jCheckBox18.isSelected() );
        lista.setCb019( jCheckBox19.isSelected() );
        lista.setCb020( jCheckBox20.isSelected() );
        lista.setCb021( jCheckBox21.isSelected() );
        lista.setCb022( jCheckBox22.isSelected() );
        lista.setCb023( jCheckBox23.isSelected() );
        lista.setCb024( jCheckBox24.isSelected() );
        lista.setCb025( jCheckBox25.isSelected() );
        lista.setCb026( jCheckBox26.isSelected() );
        lista.setCb027( jCheckBox27.isSelected() );
        lista.setCb028( jCheckBox28.isSelected() );
        lista.setCb029( jCheckBox29.isSelected() );
        lista.setCb030( jCheckBox30.isSelected() );
        lista.setCb031( jCheckBox31.isSelected() );
        lista.setCb032( jCheckBox32.isSelected() );
        lista.setCb033( jCheckBox33.isSelected() );
        lista.setCb034( jCheckBox34.isSelected() );
        lista.setCb035( jCheckBox35.isSelected() );
        lista.setCb036( jCheckBox36.isSelected() );
        lista.setCb037( jCheckBox37.isSelected() );
        lista.setCb038( jCheckBox38.isSelected() );
        lista.setCb039( jCheckBox39.isSelected() );
        lista.setCb040( jCheckBox40.isSelected() );
        lista.setCb041( jCheckBox41.isSelected() );
        lista.setCb042( jCheckBox42.isSelected() );
        lista.setCb043( jCheckBox43.isSelected() );
        lista.setCb044( jCheckBox44.isSelected() );
        lista.setCb045( jCheckBox45.isSelected() );
        lista.setCb046( jCheckBox46.isSelected() );
        lista.setCb047( jCheckBox47.isSelected() );
        lista.setCb048( jCheckBox48.isSelected() );
        lista.setCb049( jCheckBox49.isSelected() );
        lista.setCb050( jCheckBox50.isSelected() );
        lista.setCb051( jCheckBox51.isSelected() );
        lista.setCb052( jCheckBox52.isSelected() );
        lista.setCb053( jCheckBox53.isSelected() );
        lista.setCb054( jCheckBox54.isSelected() );
        lista.setCb055( jCheckBox55.isSelected() );
        lista.setCb056( jCheckBox56.isSelected() );
        lista.setCb057( jCheckBox57.isSelected() );
        lista.setCb058( jCheckBox58.isSelected() );
        lista.setCb059( jCheckBox59.isSelected() );
        lista.setCb060( jCheckBox60.isSelected() );
        lista.setCb061( jCheckBox61.isSelected() );
        lista.setCb062( jCheckBox62.isSelected() );
        lista.setCb063( jCheckBox63.isSelected() );
        lista.setCb064( jCheckBox64.isSelected() );
        lista.setCb065( jCheckBox65.isSelected() );
        lista.setCb066( jCheckBox66.isSelected() );
        lista.setCb067( jCheckBox67.isSelected() );
        lista.setCb068( jCheckBox68.isSelected() );
        lista.setCb069( jCheckBox69.isSelected() );
        lista.setCb070( jCheckBox70.isSelected() );
        lista.setCb071( jCheckBox71.isSelected() );
        lista.setCb072( jCheckBox72.isSelected() );
        lista.setCb073( jCheckBox73.isSelected() );
        lista.setCb074( jCheckBox74.isSelected() );
        lista.setCb075( jCheckBox75.isSelected() );
        lista.setCb076( jCheckBox76.isSelected() );
        lista.setCb077( jCheckBox77.isSelected() );
        lista.setCb078( jCheckBox78.isSelected() );
        lista.setCb079( jCheckBox79.isSelected() );
        lista.setCb080( jCheckBox80.isSelected() );
        lista.setCb081( jCheckBox81.isSelected() );
        lista.setCb082( jCheckBox82.isSelected() );
        lista.setCb083( jCheckBox83.isSelected() );
        lista.setCb084( jCheckBox84.isSelected() );
        lista.setCb085( jCheckBox85.isSelected() );
        lista.setCb086( jCheckBox86.isSelected() );
        lista.setCb087( jCheckBox87.isSelected() );
        lista.setCb088( jCheckBox88.isSelected() );
        lista.setCb089( jCheckBox89.isSelected() );
        lista.setCb090( jCheckBox90.isSelected() );
        lista.setCb091( jCheckBox91.isSelected() );
        lista.setCb092( jCheckBox92.isSelected() );
        lista.setCb093( jCheckBox93.isSelected() );
        lista.setCb094( jCheckBox94.isSelected() );
        lista.setCb095( jCheckBox95.isSelected() );
        lista.setCb096( jCheckBox96.isSelected() );
        lista.setCb097( jCheckBox97.isSelected() );
        lista.setCb098( jCheckBox98.isSelected() );
        lista.setCb099( jCheckBox99.isSelected() );
        lista.setCb100( jCheckBox100.isSelected() );
        lista.setCb101( jCheckBox101.isSelected() );
        lista.setCb102( jCheckBox102.isSelected() );
        lista.setCb103( jCheckBox103.isSelected() );
        lista.setCb104( jCheckBox104.isSelected() );
        lista.setCb105( jCheckBox105.isSelected() );
        lista.setCb106( jCheckBox106.isSelected() );
        lista.setCb107( jCheckBox107.isSelected() );
        lista.setCb108( jCheckBox108.isSelected() );
        lista.setCb109( jCheckBox109.isSelected() );
        lista.setResponsavel( jTextField3.getText() );
        lista.setTf04( jTextField4.getText() );
        lista.setTf05( jTextField5.getText() );
        lista.setTf06( jTextField6.getText() );
        lista.setTf07( jTextField7.getText() );
        lista.setTf08( jTextField8.getText() );
        lista.setTf09( jTextField9.getText() );
        lista.setTf10( jTextField10.getText() );
        lista.setTf11( jTextField11.getText() );
        lista.setTf12( jTextField12.getText() );
        lista.setTf13( jTextField13.getText() );
        lista.setTf14( jTextField14.getText() );
        lista.setTf15( jTextField15.getText() );
        lista.setTf16( jTextField16.getText() );
        lista.setTa01( jTextArea1.getText() );
        
        try
        {
            bd.atualizarListaSolicitacoes( lista );
        }
        catch ( SQLException ex )
        {
            JOptionPane.showMessageDialog( 
                jPanel1, 
                "Atenção: Não foi possível guardar as solicitações realizadas.", 
                "Abastecimento", 
                JOptionPane.WARNING_MESSAGE );
            Logger.getLogger( TelaAbastec.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }
    
    @Action
    public void cancelar()
    {
        dispose();
    }
    
    @Action
    public void limpar()
    {
        jCheckBox1.setSelected( false );
        jCheckBox2.setSelected( false );
        jCheckBox3.setSelected( false );
        jCheckBox4.setSelected( false );
        jCheckBox5.setSelected( false );
        jCheckBox6.setSelected( false );
        jCheckBox7.setSelected( false );
        jCheckBox8.setSelected( false );
        jCheckBox9.setSelected( false );
        jCheckBox10.setSelected( false );
        jCheckBox11.setSelected( false );
        jCheckBox12.setSelected( false );
        jCheckBox13.setSelected( false );
        jCheckBox14.setSelected( false );
        jCheckBox15.setSelected( false );
        jCheckBox16.setSelected( false );
        jCheckBox17.setSelected( false );
        jCheckBox18.setSelected( false );
        jCheckBox19.setSelected( false );
        jCheckBox20.setSelected( false );
        jCheckBox21.setSelected( false );
        jCheckBox22.setSelected( false );
        jCheckBox23.setSelected( false );
        jCheckBox24.setSelected( false );
        jCheckBox25.setSelected( false );
        jCheckBox26.setSelected( false );
        jCheckBox27.setSelected( false );
        jCheckBox28.setSelected( false );
        jCheckBox29.setSelected( false );
        jCheckBox30.setSelected( false );
        jCheckBox31.setSelected( false );
        jCheckBox32.setSelected( false );
        jCheckBox33.setSelected( false );
        jCheckBox34.setSelected( false );
        jCheckBox35.setSelected( false );
        jCheckBox36.setSelected( false );
        jCheckBox37.setSelected( false );
        jCheckBox38.setSelected( false );
        jCheckBox39.setSelected( false );
        jCheckBox40.setSelected( false );
        jCheckBox41.setSelected( false );
        jCheckBox42.setSelected( false );
        jCheckBox43.setSelected( false );
        jCheckBox44.setSelected( false );
        jCheckBox45.setSelected( false );
        jCheckBox46.setSelected( false );
        jCheckBox47.setSelected( false );
        jCheckBox48.setSelected( false );
        jCheckBox49.setSelected( false );
        jCheckBox50.setSelected( false );
        jCheckBox51.setSelected( false );
        jCheckBox52.setSelected( false );
        jCheckBox53.setSelected( false );
        jCheckBox54.setSelected( false );
        jCheckBox55.setSelected( false );
        jCheckBox56.setSelected( false );
        jCheckBox57.setSelected( false );
        jCheckBox58.setSelected( false );
        jCheckBox59.setSelected( false );
        jCheckBox60.setSelected( false );
        jCheckBox71.setSelected( false );
        jCheckBox72.setSelected( false );
        jCheckBox73.setSelected( false );
        jCheckBox74.setSelected( false );
        jCheckBox75.setSelected( false );
        jCheckBox76.setSelected( false );
        jCheckBox77.setSelected( false );
        jCheckBox78.setSelected( false );
        jCheckBox79.setSelected( false );
        jCheckBox80.setSelected( false );
        jCheckBox81.setSelected( false );
        jCheckBox82.setSelected( false );
        jCheckBox83.setSelected( false );
        jCheckBox84.setSelected( false );
        jCheckBox85.setSelected( false );
        jCheckBox86.setSelected( false );
        jCheckBox87.setSelected( false );
        jCheckBox88.setSelected( false );
        jCheckBox89.setSelected( false );
        jCheckBox90.setSelected( false );
        jCheckBox91.setSelected( false );
        jCheckBox92.setSelected( false );
        jCheckBox93.setSelected( false );
        jCheckBox94.setSelected( false );
        jCheckBox95.setSelected( false );
        jCheckBox96.setSelected( false );
        jCheckBox97.setSelected( false );
        jCheckBox98.setSelected( false );
        jCheckBox99.setSelected( false );
        jCheckBox100.setSelected( false );
        jCheckBox101.setSelected( false );
        jCheckBox102.setSelected( false );
        jCheckBox103.setSelected( false );
        jCheckBox104.setSelected( false );
        jCheckBox105.setSelected( false );
        jCheckBox106.setSelected( false );
        jCheckBox107.setSelected( false );
        jCheckBox108.setSelected( false );
        jCheckBox109.setSelected( false );
        jTextField4.setText( "" );
        jTextField5.setText( "" );
        jTextField6.setText( "" );
        jTextField7.setText( "" );
        jTextField8.setText( "" );
        jTextField9.setText( "" );
        jTextField10.setText( "" );
        jTextField11.setText( "" );
        jTextField12.setText( "" );
        jTextField13.setText( "" );
        jTextField14.setText( "" );
        jTextField15.setText( "" );
        jTextField16.setText( "" );
        jTextArea1.setText( "" );
    }
    
    private void obterListaSolicitacoes()
    {
        try
        {
            lista = bd.obterListaSolicitacoes();
            jCheckBox1.setSelected( lista.isCb001() );
            jCheckBox2.setSelected( lista.isCb002() );
            jCheckBox3.setSelected( lista.isCb003() );
            jCheckBox4.setSelected( lista.isCb004() );
            jCheckBox5.setSelected( lista.isCb005() );
            jCheckBox6.setSelected( lista.isCb006() );
            jCheckBox7.setSelected( lista.isCb007() );
            jCheckBox8.setSelected( lista.isCb008() );
            jCheckBox9.setSelected( lista.isCb009() );
            jCheckBox10.setSelected( lista.isCb010() );
            jCheckBox11.setSelected( lista.isCb011() );
            jCheckBox12.setSelected( lista.isCb012() );
            jCheckBox13.setSelected( lista.isCb013() );
            jCheckBox14.setSelected( lista.isCb014() );
            jCheckBox15.setSelected( lista.isCb015() );
            jCheckBox16.setSelected( lista.isCb016() );
            jCheckBox17.setSelected( lista.isCb017() );
            jCheckBox18.setSelected( lista.isCb018() );
            jCheckBox19.setSelected( lista.isCb019() );
            jCheckBox20.setSelected( lista.isCb020() );
            jCheckBox21.setSelected( lista.isCb021() );
            jCheckBox22.setSelected( lista.isCb022() );
            jCheckBox23.setSelected( lista.isCb023() );
            jCheckBox24.setSelected( lista.isCb024() );
            jCheckBox25.setSelected( lista.isCb025() );
            jCheckBox26.setSelected( lista.isCb026() );
            jCheckBox27.setSelected( lista.isCb027() );
            jCheckBox28.setSelected( lista.isCb028() );
            jCheckBox29.setSelected( lista.isCb029() );
            jCheckBox30.setSelected( lista.isCb030() );
            jCheckBox31.setSelected( lista.isCb031() );
            jCheckBox32.setSelected( lista.isCb032() );
            jCheckBox33.setSelected( lista.isCb033() );
            jCheckBox34.setSelected( lista.isCb034() );
            jCheckBox35.setSelected( lista.isCb035() );
            jCheckBox36.setSelected( lista.isCb036() );
            jCheckBox37.setSelected( lista.isCb037() );
            jCheckBox38.setSelected( lista.isCb038() );
            jCheckBox39.setSelected( lista.isCb039() );
            jCheckBox40.setSelected( lista.isCb040() );
            jCheckBox41.setSelected( lista.isCb041() );
            jCheckBox42.setSelected( lista.isCb042() );
            jCheckBox43.setSelected( lista.isCb043() );
            jCheckBox44.setSelected( lista.isCb044() );
            jCheckBox45.setSelected( lista.isCb045() );
            jCheckBox46.setSelected( lista.isCb046() );
            jCheckBox47.setSelected( lista.isCb047() );
            jCheckBox48.setSelected( lista.isCb048() );
            jCheckBox49.setSelected( lista.isCb049() );
            jCheckBox50.setSelected( lista.isCb050() );
            jCheckBox51.setSelected( lista.isCb051() );
            jCheckBox52.setSelected( lista.isCb052() );
            jCheckBox53.setSelected( lista.isCb053() );
            jCheckBox54.setSelected( lista.isCb054() );
            jCheckBox55.setSelected( lista.isCb055() );
            jCheckBox56.setSelected( lista.isCb056() );
            jCheckBox57.setSelected( lista.isCb057() );
            jCheckBox58.setSelected( lista.isCb058() );
            jCheckBox59.setSelected( lista.isCb059() );
            jCheckBox60.setSelected( lista.isCb060() );
            jCheckBox71.setSelected( lista.isCb071() );
            jCheckBox72.setSelected( lista.isCb072() );
            jCheckBox73.setSelected( lista.isCb073() );
            jCheckBox74.setSelected( lista.isCb074() );
            jCheckBox75.setSelected( lista.isCb075() );
            jCheckBox76.setSelected( lista.isCb076() );
            jCheckBox77.setSelected( lista.isCb077() );
            jCheckBox78.setSelected( lista.isCb078() );
            jCheckBox79.setSelected( lista.isCb079() );
            jCheckBox80.setSelected( lista.isCb080() );
            jCheckBox81.setSelected( lista.isCb081() );
            jCheckBox82.setSelected( lista.isCb082() );
            jCheckBox83.setSelected( lista.isCb083() );
            jCheckBox84.setSelected( lista.isCb084() );
            jCheckBox85.setSelected( lista.isCb085() );
            jCheckBox86.setSelected( lista.isCb086() );
            jCheckBox87.setSelected( lista.isCb087() );
            jCheckBox88.setSelected( lista.isCb088() );
            jCheckBox89.setSelected( lista.isCb089() );
            jCheckBox90.setSelected( lista.isCb090() );
            jCheckBox91.setSelected( lista.isCb091() );
            jCheckBox92.setSelected( lista.isCb092() );
            jCheckBox93.setSelected( lista.isCb093() );
            jCheckBox94.setSelected( lista.isCb094() );
            jCheckBox95.setSelected( lista.isCb095() );
            jCheckBox96.setSelected( lista.isCb096() );
            jCheckBox97.setSelected( lista.isCb097() );
            jCheckBox98.setSelected( lista.isCb098() );
            jCheckBox99.setSelected( lista.isCb099() );
            jCheckBox100.setSelected( lista.isCb100() );
            jCheckBox101.setSelected( lista.isCb101() );
            jCheckBox102.setSelected( lista.isCb102() );
            jCheckBox103.setSelected( lista.isCb103() );
            jCheckBox104.setSelected( lista.isCb104() );
            jCheckBox105.setSelected( lista.isCb105() );
            jCheckBox106.setSelected( lista.isCb106() );
            jCheckBox107.setSelected( lista.isCb107() );
            jCheckBox108.setSelected( lista.isCb108() );
            jCheckBox109.setSelected( lista.isCb109() );
            jTextField3.setText( lista.getResponsavel() );
            jTextField4.setText( lista.getTf04() );
            jTextField5.setText( lista.getTf05() );
            jTextField6.setText( lista.getTf06() );
            jTextField7.setText( lista.getTf07() );
            jTextField8.setText( lista.getTf08() );
            jTextField9.setText( lista.getTf09() );
            jTextField10.setText( lista.getTf10() );
            jTextField11.setText( lista.getTf11() );
            jTextField12.setText( lista.getTf12() );
            jTextField13.setText( lista.getTf13() );
            jTextField14.setText( lista.getTf14() );
            jTextField15.setText( lista.getTf15() );
            jTextField16.setText( lista.getTf16() );
            jTextArea1.setText( lista.getTa01() );
        }
        catch ( SQLException ex )
        {
            JOptionPane.showMessageDialog( 
                jPanel1, 
                "Atenção: Não foi possível reabrir as solicitações armazenadas inseridas anteriormente.", 
                "Abastecimento", 
                JOptionPane.WARNING_MESSAGE );
            Logger.getLogger( TelaAbastec.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }
    
    /**
     * 
     */
    @Action
    public void salvar()
    {
        atualizarListaSolicitacoes();
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jCheckBox10 = new javax.swing.JCheckBox();
        jCheckBox11 = new javax.swing.JCheckBox();
        jCheckBox12 = new javax.swing.JCheckBox();
        jTextField9 = new javax.swing.JTextField();
        jCheckBox13 = new javax.swing.JCheckBox();
        jTextField10 = new javax.swing.JTextField();
        jCheckBox14 = new javax.swing.JCheckBox();
        jCheckBox15 = new javax.swing.JCheckBox();
        jCheckBox16 = new javax.swing.JCheckBox();
        jCheckBox17 = new javax.swing.JCheckBox();
        jCheckBox18 = new javax.swing.JCheckBox();
        jCheckBox19 = new javax.swing.JCheckBox();
        jCheckBox20 = new javax.swing.JCheckBox();
        jCheckBox21 = new javax.swing.JCheckBox();
        jCheckBox22 = new javax.swing.JCheckBox();
        jCheckBox23 = new javax.swing.JCheckBox();
        jCheckBox24 = new javax.swing.JCheckBox();
        jTextField4 = new javax.swing.JTextField();
        jCheckBox25 = new javax.swing.JCheckBox();
        jTextField5 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox26 = new javax.swing.JCheckBox();
        jCheckBox27 = new javax.swing.JCheckBox();
        jCheckBox28 = new javax.swing.JCheckBox();
        jCheckBox29 = new javax.swing.JCheckBox();
        jCheckBox30 = new javax.swing.JCheckBox();
        jCheckBox31 = new javax.swing.JCheckBox();
        jCheckBox32 = new javax.swing.JCheckBox();
        jCheckBox33 = new javax.swing.JCheckBox();
        jCheckBox34 = new javax.swing.JCheckBox();
        jCheckBox35 = new javax.swing.JCheckBox();
        jCheckBox36 = new javax.swing.JCheckBox();
        jCheckBox37 = new javax.swing.JCheckBox();
        jCheckBox38 = new javax.swing.JCheckBox();
        jCheckBox39 = new javax.swing.JCheckBox();
        jCheckBox40 = new javax.swing.JCheckBox();
        jCheckBox41 = new javax.swing.JCheckBox();
        jCheckBox42 = new javax.swing.JCheckBox();
        jCheckBox43 = new javax.swing.JCheckBox();
        jCheckBox44 = new javax.swing.JCheckBox();
        jCheckBox45 = new javax.swing.JCheckBox();
        jTextField6 = new javax.swing.JTextField();
        jCheckBox46 = new javax.swing.JCheckBox();
        jTextField7 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox47 = new javax.swing.JCheckBox();
        jCheckBox48 = new javax.swing.JCheckBox();
        jCheckBox49 = new javax.swing.JCheckBox();
        jCheckBox50 = new javax.swing.JCheckBox();
        jCheckBox51 = new javax.swing.JCheckBox();
        jCheckBox52 = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jCheckBox53 = new javax.swing.JCheckBox();
        jCheckBox54 = new javax.swing.JCheckBox();
        jCheckBox55 = new javax.swing.JCheckBox();
        jCheckBox56 = new javax.swing.JCheckBox();
        jCheckBox57 = new javax.swing.JCheckBox();
        jCheckBox58 = new javax.swing.JCheckBox();
        jTextField8 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jCheckBox59 = new javax.swing.JCheckBox();
        jCheckBox60 = new javax.swing.JCheckBox();
        jCheckBox61 = new javax.swing.JCheckBox();
        jCheckBox62 = new javax.swing.JCheckBox();
        jCheckBox63 = new javax.swing.JCheckBox();
        jCheckBox64 = new javax.swing.JCheckBox();
        jCheckBox65 = new javax.swing.JCheckBox();
        jCheckBox66 = new javax.swing.JCheckBox();
        jCheckBox67 = new javax.swing.JCheckBox();
        jCheckBox68 = new javax.swing.JCheckBox();
        jCheckBox69 = new javax.swing.JCheckBox();
        jCheckBox70 = new javax.swing.JCheckBox();
        jCheckBox71 = new javax.swing.JCheckBox();
        jCheckBox72 = new javax.swing.JCheckBox();
        jCheckBox73 = new javax.swing.JCheckBox();
        jCheckBox74 = new javax.swing.JCheckBox();
        jCheckBox75 = new javax.swing.JCheckBox();
        jCheckBox76 = new javax.swing.JCheckBox();
        jCheckBox77 = new javax.swing.JCheckBox();
        jTextField11 = new javax.swing.JTextField();
        jCheckBox78 = new javax.swing.JCheckBox();
        jTextField12 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jCheckBox79 = new javax.swing.JCheckBox();
        jCheckBox80 = new javax.swing.JCheckBox();
        jCheckBox81 = new javax.swing.JCheckBox();
        jCheckBox82 = new javax.swing.JCheckBox();
        jCheckBox83 = new javax.swing.JCheckBox();
        jCheckBox84 = new javax.swing.JCheckBox();
        jCheckBox85 = new javax.swing.JCheckBox();
        jCheckBox86 = new javax.swing.JCheckBox();
        jCheckBox87 = new javax.swing.JCheckBox();
        jCheckBox88 = new javax.swing.JCheckBox();
        jCheckBox89 = new javax.swing.JCheckBox();
        jCheckBox90 = new javax.swing.JCheckBox();
        jCheckBox91 = new javax.swing.JCheckBox();
        jCheckBox92 = new javax.swing.JCheckBox();
        jCheckBox93 = new javax.swing.JCheckBox();
        jCheckBox94 = new javax.swing.JCheckBox();
        jTextField13 = new javax.swing.JTextField();
        jCheckBox95 = new javax.swing.JCheckBox();
        jTextField14 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jCheckBox96 = new javax.swing.JCheckBox();
        jCheckBox97 = new javax.swing.JCheckBox();
        jCheckBox98 = new javax.swing.JCheckBox();
        jCheckBox99 = new javax.swing.JCheckBox();
        jCheckBox100 = new javax.swing.JCheckBox();
        jCheckBox101 = new javax.swing.JCheckBox();
        jCheckBox102 = new javax.swing.JCheckBox();
        jCheckBox103 = new javax.swing.JCheckBox();
        jCheckBox104 = new javax.swing.JCheckBox();
        jCheckBox105 = new javax.swing.JCheckBox();
        jCheckBox106 = new javax.swing.JCheckBox();
        jCheckBox107 = new javax.swing.JCheckBox();
        jCheckBox108 = new javax.swing.JCheckBox();
        jTextField15 = new javax.swing.JTextField();
        jCheckBox109 = new javax.swing.JCheckBox();
        jTextField16 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getResourceMap(TelaAbastec.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(800, 650));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 650));

        jPanel2.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel2.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 622));

        jCheckBox1.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox1.setText(resourceMap.getString("jCheckBox1.text")); // NOI18N
        jCheckBox1.setName("jCheckBox1"); // NOI18N

        jCheckBox2.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox2.setText(resourceMap.getString("jCheckBox2.text")); // NOI18N
        jCheckBox2.setName("jCheckBox2"); // NOI18N

        jCheckBox3.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox3.setText(resourceMap.getString("jCheckBox3.text")); // NOI18N
        jCheckBox3.setName("jCheckBox3"); // NOI18N

        jCheckBox4.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox4.setText(resourceMap.getString("jCheckBox4.text")); // NOI18N
        jCheckBox4.setName("jCheckBox4"); // NOI18N

        jCheckBox5.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox5.setText(resourceMap.getString("jCheckBox5.text")); // NOI18N
        jCheckBox5.setName("jCheckBox5"); // NOI18N

        jCheckBox6.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox6.setText(resourceMap.getString("jCheckBox6.text")); // NOI18N
        jCheckBox6.setName("jCheckBox6"); // NOI18N

        jCheckBox7.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox7.setText(resourceMap.getString("jCheckBox7.text")); // NOI18N
        jCheckBox7.setName("jCheckBox7"); // NOI18N

        jCheckBox8.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox8.setText(resourceMap.getString("jCheckBox8.text")); // NOI18N
        jCheckBox8.setName("jCheckBox8"); // NOI18N

        jCheckBox9.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox9.setText(resourceMap.getString("jCheckBox9.text")); // NOI18N
        jCheckBox9.setName("jCheckBox9"); // NOI18N

        jCheckBox10.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox10.setText(resourceMap.getString("jCheckBox10.text")); // NOI18N
        jCheckBox10.setName("jCheckBox10"); // NOI18N

        jCheckBox11.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox11.setText(resourceMap.getString("jCheckBox11.text")); // NOI18N
        jCheckBox11.setName("jCheckBox11"); // NOI18N

        jCheckBox12.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox12.setText(resourceMap.getString("jCheckBox12.text")); // NOI18N
        jCheckBox12.setName("jCheckBox12"); // NOI18N

        jTextField9.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField9.setText(resourceMap.getString("jTextField9.text")); // NOI18N
        jTextField9.setName("jTextField9"); // NOI18N

        jCheckBox13.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox13.setText(resourceMap.getString("jCheckBox13.text")); // NOI18N
        jCheckBox13.setName("jCheckBox13"); // NOI18N

        jTextField10.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField10.setText(resourceMap.getString("jTextField10.text")); // NOI18N
        jTextField10.setName("jTextField10"); // NOI18N

        jCheckBox14.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox14.setText(resourceMap.getString("jCheckBox14.text")); // NOI18N
        jCheckBox14.setName("jCheckBox14"); // NOI18N

        jCheckBox15.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox15.setText(resourceMap.getString("jCheckBox15.text")); // NOI18N
        jCheckBox15.setName("jCheckBox15"); // NOI18N

        jCheckBox16.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox16.setText(resourceMap.getString("jCheckBox16.text")); // NOI18N
        jCheckBox16.setName("jCheckBox16"); // NOI18N

        jCheckBox17.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox17.setText(resourceMap.getString("jCheckBox17.text")); // NOI18N
        jCheckBox17.setName("jCheckBox17"); // NOI18N

        jCheckBox18.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox18.setText(resourceMap.getString("jCheckBox18.text")); // NOI18N
        jCheckBox18.setName("jCheckBox18"); // NOI18N

        jCheckBox19.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox19.setText(resourceMap.getString("jCheckBox19.text")); // NOI18N
        jCheckBox19.setName("jCheckBox19"); // NOI18N

        jCheckBox20.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox20.setText(resourceMap.getString("jCheckBox20.text")); // NOI18N
        jCheckBox20.setName("jCheckBox20"); // NOI18N

        jCheckBox21.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox21.setText(resourceMap.getString("jCheckBox21.text")); // NOI18N
        jCheckBox21.setName("jCheckBox21"); // NOI18N

        jCheckBox22.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox22.setText(resourceMap.getString("jCheckBox22.text")); // NOI18N
        jCheckBox22.setName("jCheckBox22"); // NOI18N

        jCheckBox23.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox23.setText(resourceMap.getString("jCheckBox23.text")); // NOI18N
        jCheckBox23.setName("jCheckBox23"); // NOI18N

        jCheckBox24.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox24.setText(resourceMap.getString("jCheckBox24.text")); // NOI18N
        jCheckBox24.setName("jCheckBox24"); // NOI18N

        jTextField4.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField4.setText(resourceMap.getString("jTextField4.text")); // NOI18N
        jTextField4.setName("jTextField4"); // NOI18N

        jCheckBox25.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox25.setText(resourceMap.getString("jCheckBox25.text")); // NOI18N
        jCheckBox25.setName("jCheckBox25"); // NOI18N

        jTextField5.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField5.setText(resourceMap.getString("jTextField5.text")); // NOI18N
        jTextField5.setName("jTextField5"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox5)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox9)
                    .addComponent(jCheckBox10)
                    .addComponent(jCheckBox11)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                    .addComponent(jCheckBox14)
                    .addComponent(jCheckBox15)
                    .addComponent(jCheckBox16)
                    .addComponent(jCheckBox17)
                    .addComponent(jCheckBox18)
                    .addComponent(jCheckBox19)
                    .addComponent(jCheckBox20)
                    .addComponent(jCheckBox21)
                    .addComponent(jCheckBox22)
                    .addComponent(jCheckBox23)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox12)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox13)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox24)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox25)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(5, Short.MAX_VALUE))
        );

        jPanel3.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(150, 467));

        jCheckBox26.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox26.setText(resourceMap.getString("jCheckBox26.text")); // NOI18N
        jCheckBox26.setName("jCheckBox26"); // NOI18N

        jCheckBox27.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox27.setText(resourceMap.getString("jCheckBox27.text")); // NOI18N
        jCheckBox27.setName("jCheckBox27"); // NOI18N

        jCheckBox28.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox28.setText(resourceMap.getString("jCheckBox28.text")); // NOI18N
        jCheckBox28.setName("jCheckBox28"); // NOI18N

        jCheckBox29.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox29.setText(resourceMap.getString("jCheckBox29.text")); // NOI18N
        jCheckBox29.setName("jCheckBox29"); // NOI18N

        jCheckBox30.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox30.setText(resourceMap.getString("jCheckBox30.text")); // NOI18N
        jCheckBox30.setName("jCheckBox30"); // NOI18N

        jCheckBox31.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox31.setText(resourceMap.getString("jCheckBox31.text")); // NOI18N
        jCheckBox31.setName("jCheckBox31"); // NOI18N

        jCheckBox32.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox32.setText(resourceMap.getString("jCheckBox32.text")); // NOI18N
        jCheckBox32.setName("jCheckBox32"); // NOI18N

        jCheckBox33.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox33.setText(resourceMap.getString("jCheckBox33.text")); // NOI18N
        jCheckBox33.setName("jCheckBox33"); // NOI18N

        jCheckBox34.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox34.setText(resourceMap.getString("jCheckBox34.text")); // NOI18N
        jCheckBox34.setName("jCheckBox34"); // NOI18N

        jCheckBox35.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox35.setText(resourceMap.getString("jCheckBox35.text")); // NOI18N
        jCheckBox35.setName("jCheckBox35"); // NOI18N

        jCheckBox36.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox36.setText(resourceMap.getString("jCheckBox36.text")); // NOI18N
        jCheckBox36.setName("jCheckBox36"); // NOI18N

        jCheckBox37.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox37.setText(resourceMap.getString("jCheckBox37.text")); // NOI18N
        jCheckBox37.setName("jCheckBox37"); // NOI18N

        jCheckBox38.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox38.setText(resourceMap.getString("jCheckBox38.text")); // NOI18N
        jCheckBox38.setName("jCheckBox38"); // NOI18N

        jCheckBox39.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox39.setText(resourceMap.getString("jCheckBox39.text")); // NOI18N
        jCheckBox39.setName("jCheckBox39"); // NOI18N

        jCheckBox40.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox40.setText(resourceMap.getString("jCheckBox40.text")); // NOI18N
        jCheckBox40.setName("jCheckBox40"); // NOI18N

        jCheckBox41.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox41.setText(resourceMap.getString("jCheckBox41.text")); // NOI18N
        jCheckBox41.setName("jCheckBox41"); // NOI18N

        jCheckBox42.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox42.setText(resourceMap.getString("jCheckBox42.text")); // NOI18N
        jCheckBox42.setName("jCheckBox42"); // NOI18N

        jCheckBox43.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox43.setText(resourceMap.getString("jCheckBox43.text")); // NOI18N
        jCheckBox43.setName("jCheckBox43"); // NOI18N

        jCheckBox44.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox44.setText(resourceMap.getString("jCheckBox44.text")); // NOI18N
        jCheckBox44.setName("jCheckBox44"); // NOI18N

        jCheckBox45.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox45.setText(resourceMap.getString("jCheckBox45.text")); // NOI18N
        jCheckBox45.setName("jCheckBox45"); // NOI18N

        jTextField6.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField6.setText(resourceMap.getString("jTextField6.text")); // NOI18N
        jTextField6.setName("jTextField6"); // NOI18N

        jCheckBox46.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox46.setText(resourceMap.getString("jCheckBox46.text")); // NOI18N
        jCheckBox46.setName("jCheckBox46"); // NOI18N

        jTextField7.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField7.setText(resourceMap.getString("jTextField7.text")); // NOI18N
        jTextField7.setName("jTextField7"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBox26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox27))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBox28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox29))
                    .addComponent(jCheckBox30)
                    .addComponent(jCheckBox31)
                    .addComponent(jCheckBox34)
                    .addComponent(jCheckBox35)
                    .addComponent(jCheckBox36)
                    .addComponent(jCheckBox37)
                    .addComponent(jCheckBox38)
                    .addComponent(jCheckBox39)
                    .addComponent(jCheckBox40)
                    .addComponent(jCheckBox41)
                    .addComponent(jCheckBox42)
                    .addComponent(jCheckBox43)
                    .addComponent(jCheckBox44)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBox32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox33))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBox45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jCheckBox46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox26)
                    .addComponent(jCheckBox27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox28)
                    .addComponent(jCheckBox29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox32)
                    .addComponent(jCheckBox33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox45)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox46)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        jPanel4.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel4.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(150, 106));

        jCheckBox47.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox47.setText(resourceMap.getString("jCheckBox47.text")); // NOI18N
        jCheckBox47.setName("jCheckBox47"); // NOI18N

        jCheckBox48.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox48.setText(resourceMap.getString("jCheckBox48.text")); // NOI18N
        jCheckBox48.setName("jCheckBox48"); // NOI18N

        jCheckBox49.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox49.setText(resourceMap.getString("jCheckBox49.text")); // NOI18N
        jCheckBox49.setName("jCheckBox49"); // NOI18N

        jCheckBox50.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox50.setText(resourceMap.getString("jCheckBox50.text")); // NOI18N
        jCheckBox50.setName("jCheckBox50"); // NOI18N

        jCheckBox51.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox51.setText(resourceMap.getString("jCheckBox51.text")); // NOI18N
        jCheckBox51.setName("jCheckBox51"); // NOI18N

        jCheckBox52.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox52.setText(resourceMap.getString("jCheckBox52.text")); // NOI18N
        jCheckBox52.setName("jCheckBox52"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jCheckBox47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox48))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jCheckBox49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox50))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jCheckBox51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox52)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox47)
                    .addComponent(jCheckBox48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox49)
                    .addComponent(jCheckBox50))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox51)
                    .addComponent(jCheckBox52))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel5.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(150, 109));

        jCheckBox53.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox53.setText(resourceMap.getString("jCheckBox53.text")); // NOI18N
        jCheckBox53.setName("jCheckBox53"); // NOI18N

        jCheckBox54.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox54.setText(resourceMap.getString("jCheckBox54.text")); // NOI18N
        jCheckBox54.setName("jCheckBox54"); // NOI18N

        jCheckBox55.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox55.setText(resourceMap.getString("jCheckBox55.text")); // NOI18N
        jCheckBox55.setName("jCheckBox55"); // NOI18N

        jCheckBox56.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox56.setText(resourceMap.getString("jCheckBox56.text")); // NOI18N
        jCheckBox56.setName("jCheckBox56"); // NOI18N

        jCheckBox57.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox57.setText(resourceMap.getString("jCheckBox57.text")); // NOI18N
        jCheckBox57.setName("jCheckBox57"); // NOI18N

        jCheckBox58.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox58.setText(resourceMap.getString("jCheckBox58.text")); // NOI18N
        jCheckBox58.setName("jCheckBox58"); // NOI18N

        jTextField8.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField8.setText(resourceMap.getString("jTextField8.text")); // NOI18N
        jTextField8.setName("jTextField8"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jCheckBox53)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox54))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jCheckBox55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox56))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jCheckBox57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox53)
                    .addComponent(jCheckBox54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox55)
                    .addComponent(jCheckBox56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox57)
                    .addComponent(jCheckBox58)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel6.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanel6.setName("jPanel6"); // NOI18N

        jCheckBox59.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox59.setText(resourceMap.getString("jCheckBox59.text")); // NOI18N
        jCheckBox59.setName("jCheckBox59"); // NOI18N

        jCheckBox60.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox60.setText(resourceMap.getString("jCheckBox60.text")); // NOI18N
        jCheckBox60.setName("jCheckBox60"); // NOI18N

        jCheckBox61.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox61.setText(resourceMap.getString("jCheckBox61.text")); // NOI18N
        jCheckBox61.setName("jCheckBox61"); // NOI18N

        jCheckBox62.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox62.setText(resourceMap.getString("jCheckBox62.text")); // NOI18N
        jCheckBox62.setName("jCheckBox62"); // NOI18N

        jCheckBox63.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox63.setText(resourceMap.getString("jCheckBox63.text")); // NOI18N
        jCheckBox63.setName("jCheckBox63"); // NOI18N

        jCheckBox64.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox64.setText(resourceMap.getString("jCheckBox64.text")); // NOI18N
        jCheckBox64.setName("jCheckBox64"); // NOI18N

        jCheckBox65.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox65.setText(resourceMap.getString("jCheckBox65.text")); // NOI18N
        jCheckBox65.setName("jCheckBox65"); // NOI18N

        jCheckBox66.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox66.setText(resourceMap.getString("jCheckBox66.text")); // NOI18N
        jCheckBox66.setName("jCheckBox66"); // NOI18N

        jCheckBox67.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox67.setText(resourceMap.getString("jCheckBox67.text")); // NOI18N
        jCheckBox67.setName("jCheckBox67"); // NOI18N

        jCheckBox68.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox68.setText(resourceMap.getString("jCheckBox68.text")); // NOI18N
        jCheckBox68.setName("jCheckBox68"); // NOI18N

        jCheckBox69.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox69.setText(resourceMap.getString("jCheckBox69.text")); // NOI18N
        jCheckBox69.setName("jCheckBox69"); // NOI18N

        jCheckBox70.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox70.setText(resourceMap.getString("jCheckBox70.text")); // NOI18N
        jCheckBox70.setName("jCheckBox70"); // NOI18N

        jCheckBox71.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox71.setText(resourceMap.getString("jCheckBox71.text")); // NOI18N
        jCheckBox71.setName("jCheckBox71"); // NOI18N

        jCheckBox72.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox72.setText(resourceMap.getString("jCheckBox72.text")); // NOI18N
        jCheckBox72.setName("jCheckBox72"); // NOI18N

        jCheckBox73.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox73.setText(resourceMap.getString("jCheckBox73.text")); // NOI18N
        jCheckBox73.setName("jCheckBox73"); // NOI18N

        jCheckBox74.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox74.setText(resourceMap.getString("jCheckBox74.text")); // NOI18N
        jCheckBox74.setName("jCheckBox74"); // NOI18N

        jCheckBox75.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox75.setText(resourceMap.getString("jCheckBox75.text")); // NOI18N
        jCheckBox75.setName("jCheckBox75"); // NOI18N

        jCheckBox76.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox76.setText(resourceMap.getString("jCheckBox76.text")); // NOI18N
        jCheckBox76.setName("jCheckBox76"); // NOI18N

        jCheckBox77.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox77.setText(resourceMap.getString("jCheckBox77.text")); // NOI18N
        jCheckBox77.setName("jCheckBox77"); // NOI18N

        jTextField11.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField11.setText(resourceMap.getString("jTextField11.text")); // NOI18N
        jTextField11.setName("jTextField11"); // NOI18N

        jCheckBox78.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox78.setText(resourceMap.getString("jCheckBox78.text")); // NOI18N
        jCheckBox78.setName("jCheckBox78"); // NOI18N

        jTextField12.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField12.setText(resourceMap.getString("jTextField12.text")); // NOI18N
        jTextField12.setName("jTextField12"); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jCheckBox59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox60))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jCheckBox61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox62))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jCheckBox63)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox64))
                    .addComponent(jCheckBox65)
                    .addComponent(jCheckBox66)
                    .addComponent(jCheckBox67)
                    .addComponent(jCheckBox68)
                    .addComponent(jCheckBox69)
                    .addComponent(jCheckBox70)
                    .addComponent(jCheckBox71)
                    .addComponent(jCheckBox72)
                    .addComponent(jCheckBox73)
                    .addComponent(jCheckBox74)
                    .addComponent(jCheckBox75)
                    .addComponent(jCheckBox76)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jCheckBox77)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jCheckBox78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox59)
                    .addComponent(jCheckBox60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox61)
                    .addComponent(jCheckBox62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox63)
                    .addComponent(jCheckBox64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox66)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox67)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox68)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox69)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox72)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox73)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox74)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox75)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox77)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox78)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel7.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel7.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanel7.setName("jPanel7"); // NOI18N

        jCheckBox79.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox79.setText(resourceMap.getString("jCheckBox79.text")); // NOI18N
        jCheckBox79.setName("jCheckBox79"); // NOI18N

        jCheckBox80.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox80.setText(resourceMap.getString("jCheckBox80.text")); // NOI18N
        jCheckBox80.setName("jCheckBox80"); // NOI18N

        jCheckBox81.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox81.setText(resourceMap.getString("jCheckBox81.text")); // NOI18N
        jCheckBox81.setName("jCheckBox81"); // NOI18N

        jCheckBox82.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox82.setText(resourceMap.getString("jCheckBox82.text")); // NOI18N
        jCheckBox82.setName("jCheckBox82"); // NOI18N

        jCheckBox83.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox83.setText(resourceMap.getString("jCheckBox83.text")); // NOI18N
        jCheckBox83.setName("jCheckBox83"); // NOI18N

        jCheckBox84.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox84.setText(resourceMap.getString("jCheckBox84.text")); // NOI18N
        jCheckBox84.setName("jCheckBox84"); // NOI18N

        jCheckBox85.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox85.setText(resourceMap.getString("jCheckBox85.text")); // NOI18N
        jCheckBox85.setName("jCheckBox85"); // NOI18N

        jCheckBox86.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox86.setText(resourceMap.getString("jCheckBox86.text")); // NOI18N
        jCheckBox86.setName("jCheckBox86"); // NOI18N

        jCheckBox87.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox87.setText(resourceMap.getString("jCheckBox87.text")); // NOI18N
        jCheckBox87.setName("jCheckBox87"); // NOI18N

        jCheckBox88.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox88.setText(resourceMap.getString("jCheckBox88.text")); // NOI18N
        jCheckBox88.setName("jCheckBox88"); // NOI18N

        jCheckBox89.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox89.setText(resourceMap.getString("jCheckBox89.text")); // NOI18N
        jCheckBox89.setName("jCheckBox89"); // NOI18N

        jCheckBox90.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox90.setText(resourceMap.getString("jCheckBox90.text")); // NOI18N
        jCheckBox90.setName("jCheckBox90"); // NOI18N

        jCheckBox91.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox91.setText(resourceMap.getString("jCheckBox91.text")); // NOI18N
        jCheckBox91.setName("jCheckBox91"); // NOI18N

        jCheckBox92.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox92.setText(resourceMap.getString("jCheckBox92.text")); // NOI18N
        jCheckBox92.setName("jCheckBox92"); // NOI18N

        jCheckBox93.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox93.setText(resourceMap.getString("jCheckBox93.text")); // NOI18N
        jCheckBox93.setName("jCheckBox93"); // NOI18N

        jCheckBox94.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox94.setText(resourceMap.getString("jCheckBox94.text")); // NOI18N
        jCheckBox94.setName("jCheckBox94"); // NOI18N

        jTextField13.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField13.setText(resourceMap.getString("jTextField13.text")); // NOI18N
        jTextField13.setName("jTextField13"); // NOI18N

        jCheckBox95.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox95.setText(resourceMap.getString("jCheckBox95.text")); // NOI18N
        jCheckBox95.setName("jCheckBox95"); // NOI18N

        jTextField14.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField14.setText(resourceMap.getString("jTextField14.text")); // NOI18N
        jTextField14.setName("jTextField14"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox79)
                    .addComponent(jCheckBox80)
                    .addComponent(jCheckBox81)
                    .addComponent(jCheckBox82)
                    .addComponent(jCheckBox83)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jCheckBox84)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox85))
                    .addComponent(jCheckBox86)
                    .addComponent(jCheckBox87)
                    .addComponent(jCheckBox88)
                    .addComponent(jCheckBox89)
                    .addComponent(jCheckBox90)
                    .addComponent(jCheckBox91)
                    .addComponent(jCheckBox92)
                    .addComponent(jCheckBox93)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jCheckBox94)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField13, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jCheckBox95)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField14, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jCheckBox79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox81)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox82)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox83)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox84)
                    .addComponent(jCheckBox85))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox86)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox87)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox88)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox89)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox90)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox91)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox92)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox93)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox94)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox95)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel8.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel8.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanel8.setName("jPanel8"); // NOI18N
        jPanel8.setPreferredSize(new java.awt.Dimension(150, 240));

        jCheckBox96.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox96.setText(resourceMap.getString("jCheckBox96.text")); // NOI18N
        jCheckBox96.setName("jCheckBox96"); // NOI18N

        jCheckBox97.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox97.setText(resourceMap.getString("jCheckBox97.text")); // NOI18N
        jCheckBox97.setName("jCheckBox97"); // NOI18N

        jCheckBox98.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox98.setText(resourceMap.getString("jCheckBox98.text")); // NOI18N
        jCheckBox98.setName("jCheckBox98"); // NOI18N

        jCheckBox99.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox99.setText(resourceMap.getString("jCheckBox99.text")); // NOI18N
        jCheckBox99.setName("jCheckBox99"); // NOI18N

        jCheckBox100.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox100.setText(resourceMap.getString("jCheckBox100.text")); // NOI18N
        jCheckBox100.setName("jCheckBox100"); // NOI18N

        jCheckBox101.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox101.setText(resourceMap.getString("jCheckBox101.text")); // NOI18N
        jCheckBox101.setName("jCheckBox101"); // NOI18N

        jCheckBox102.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox102.setText(resourceMap.getString("jCheckBox102.text")); // NOI18N
        jCheckBox102.setName("jCheckBox102"); // NOI18N

        jCheckBox103.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox103.setText(resourceMap.getString("jCheckBox103.text")); // NOI18N
        jCheckBox103.setName("jCheckBox103"); // NOI18N

        jCheckBox104.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox104.setText(resourceMap.getString("jCheckBox104.text")); // NOI18N
        jCheckBox104.setName("jCheckBox104"); // NOI18N

        jCheckBox105.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox105.setText(resourceMap.getString("jCheckBox105.text")); // NOI18N
        jCheckBox105.setName("jCheckBox105"); // NOI18N

        jCheckBox106.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox106.setText(resourceMap.getString("jCheckBox106.text")); // NOI18N
        jCheckBox106.setName("jCheckBox106"); // NOI18N

        jCheckBox107.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox107.setText(resourceMap.getString("jCheckBox107.text")); // NOI18N
        jCheckBox107.setName("jCheckBox107"); // NOI18N

        jCheckBox108.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox108.setText(resourceMap.getString("jCheckBox108.text")); // NOI18N
        jCheckBox108.setName("jCheckBox108"); // NOI18N

        jTextField15.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField15.setText(resourceMap.getString("jTextField15.text")); // NOI18N
        jTextField15.setName("jTextField15"); // NOI18N

        jCheckBox109.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jCheckBox109.setText(resourceMap.getString("jCheckBox109.text")); // NOI18N
        jCheckBox109.setName("jCheckBox109"); // NOI18N

        jTextField16.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField16.setText(resourceMap.getString("jTextField16.text")); // NOI18N
        jTextField16.setName("jTextField16"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox96)
                    .addComponent(jCheckBox97)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                            .addComponent(jCheckBox100)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox101))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                            .addComponent(jCheckBox98)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jCheckBox99)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCheckBox102)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox103))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCheckBox104)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox105))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCheckBox106)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox107))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCheckBox108)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField15, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCheckBox109)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jCheckBox96)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox97)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox98)
                    .addComponent(jCheckBox99))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox100)
                    .addComponent(jCheckBox101))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox102)
                    .addComponent(jCheckBox103))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox104)
                    .addComponent(jCheckBox105))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox106)
                    .addComponent(jCheckBox107))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox108)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox109)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel9.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel9.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanel9.setName("jPanel9"); // NOI18N
        jPanel9.setPreferredSize(new java.awt.Dimension(300, 155));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextArea1.setColumns(20);
        jTextArea1.setRows(6);
        jTextArea1.setTabSize(6);
        jTextArea1.setText(resourceMap.getString("jTextArea1.text")); // NOI18N
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel10.setName("jPanel10"); // NOI18N
        jPanel10.setPreferredSize(new java.awt.Dimension(777, 45));

        jLabel1.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jTextField1.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField1.setEditable(false);
        jTextField1.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jTextField2.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField2.setEditable(false);
        jTextField2.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTextField2.setText(resourceMap.getString("jTextField2.text")); // NOI18N
        jTextField2.setName("jTextField2"); // NOI18N

        jTextField3.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextField3.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTextField3.setText(resourceMap.getString("jTextField3.text")); // NOI18N
        jTextField3.setName("jTextField3"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel11.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanel11.setName("jPanel11"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getActionMap(TelaAbastec.class, this);
        jButton1.setAction(actionMap.get("salvar")); // NOI18N
        jButton1.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(85, 25));

        jButton2.setAction(actionMap.get("cancelar")); // NOI18N
        jButton2.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setAction(actionMap.get("limpar")); // NOI18N
        jButton3.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setPreferredSize(new java.awt.Dimension(85, 25));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton2)
            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox100;
    private javax.swing.JCheckBox jCheckBox101;
    private javax.swing.JCheckBox jCheckBox102;
    private javax.swing.JCheckBox jCheckBox103;
    private javax.swing.JCheckBox jCheckBox104;
    private javax.swing.JCheckBox jCheckBox105;
    private javax.swing.JCheckBox jCheckBox106;
    private javax.swing.JCheckBox jCheckBox107;
    private javax.swing.JCheckBox jCheckBox108;
    private javax.swing.JCheckBox jCheckBox109;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox16;
    private javax.swing.JCheckBox jCheckBox17;
    private javax.swing.JCheckBox jCheckBox18;
    private javax.swing.JCheckBox jCheckBox19;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox20;
    private javax.swing.JCheckBox jCheckBox21;
    private javax.swing.JCheckBox jCheckBox22;
    private javax.swing.JCheckBox jCheckBox23;
    private javax.swing.JCheckBox jCheckBox24;
    private javax.swing.JCheckBox jCheckBox25;
    private javax.swing.JCheckBox jCheckBox26;
    private javax.swing.JCheckBox jCheckBox27;
    private javax.swing.JCheckBox jCheckBox28;
    private javax.swing.JCheckBox jCheckBox29;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox30;
    private javax.swing.JCheckBox jCheckBox31;
    private javax.swing.JCheckBox jCheckBox32;
    private javax.swing.JCheckBox jCheckBox33;
    private javax.swing.JCheckBox jCheckBox34;
    private javax.swing.JCheckBox jCheckBox35;
    private javax.swing.JCheckBox jCheckBox36;
    private javax.swing.JCheckBox jCheckBox37;
    private javax.swing.JCheckBox jCheckBox38;
    private javax.swing.JCheckBox jCheckBox39;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox40;
    private javax.swing.JCheckBox jCheckBox41;
    private javax.swing.JCheckBox jCheckBox42;
    private javax.swing.JCheckBox jCheckBox43;
    private javax.swing.JCheckBox jCheckBox44;
    private javax.swing.JCheckBox jCheckBox45;
    private javax.swing.JCheckBox jCheckBox46;
    private javax.swing.JCheckBox jCheckBox47;
    private javax.swing.JCheckBox jCheckBox48;
    private javax.swing.JCheckBox jCheckBox49;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox50;
    private javax.swing.JCheckBox jCheckBox51;
    private javax.swing.JCheckBox jCheckBox52;
    private javax.swing.JCheckBox jCheckBox53;
    private javax.swing.JCheckBox jCheckBox54;
    private javax.swing.JCheckBox jCheckBox55;
    private javax.swing.JCheckBox jCheckBox56;
    private javax.swing.JCheckBox jCheckBox57;
    private javax.swing.JCheckBox jCheckBox58;
    private javax.swing.JCheckBox jCheckBox59;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox60;
    private javax.swing.JCheckBox jCheckBox61;
    private javax.swing.JCheckBox jCheckBox62;
    private javax.swing.JCheckBox jCheckBox63;
    private javax.swing.JCheckBox jCheckBox64;
    private javax.swing.JCheckBox jCheckBox65;
    private javax.swing.JCheckBox jCheckBox66;
    private javax.swing.JCheckBox jCheckBox67;
    private javax.swing.JCheckBox jCheckBox68;
    private javax.swing.JCheckBox jCheckBox69;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox70;
    private javax.swing.JCheckBox jCheckBox71;
    private javax.swing.JCheckBox jCheckBox72;
    private javax.swing.JCheckBox jCheckBox73;
    private javax.swing.JCheckBox jCheckBox74;
    private javax.swing.JCheckBox jCheckBox75;
    private javax.swing.JCheckBox jCheckBox76;
    private javax.swing.JCheckBox jCheckBox77;
    private javax.swing.JCheckBox jCheckBox78;
    private javax.swing.JCheckBox jCheckBox79;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox80;
    private javax.swing.JCheckBox jCheckBox81;
    private javax.swing.JCheckBox jCheckBox82;
    private javax.swing.JCheckBox jCheckBox83;
    private javax.swing.JCheckBox jCheckBox84;
    private javax.swing.JCheckBox jCheckBox85;
    private javax.swing.JCheckBox jCheckBox86;
    private javax.swing.JCheckBox jCheckBox87;
    private javax.swing.JCheckBox jCheckBox88;
    private javax.swing.JCheckBox jCheckBox89;
    private javax.swing.JCheckBox jCheckBox9;
    private javax.swing.JCheckBox jCheckBox90;
    private javax.swing.JCheckBox jCheckBox91;
    private javax.swing.JCheckBox jCheckBox92;
    private javax.swing.JCheckBox jCheckBox93;
    private javax.swing.JCheckBox jCheckBox94;
    private javax.swing.JCheckBox jCheckBox95;
    private javax.swing.JCheckBox jCheckBox96;
    private javax.swing.JCheckBox jCheckBox97;
    private javax.swing.JCheckBox jCheckBox98;
    private javax.swing.JCheckBox jCheckBox99;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables

}
