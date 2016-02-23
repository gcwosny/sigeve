/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TelaFecharComanda.java
 *
 * Created on 07/09/2010, 10:43:55
 */
package sigeve;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdesktop.application.Action;

/**
 *
 * @author Paulo Henrique
 */
public class TelaFecharComanda
    extends javax.swing.JDialog
    implements KeyListener
{
    private boolean comandaFechada = false;
    private float total;
    private float novoTotal;
    private float recebido;
    private float troco;
    private float desconto;
    private SigeveView sv;

    /** Creates new form TelaFecharComanda
     * @param parent
     * @param modal
     * @param cliente
     * @param total
     */
    public TelaFecharComanda(
        SigeveView parent,
        boolean modal,
        String cliente,
        float total )
    {
        super( parent.getFrame(), modal );
        initComponents();
        adicionarKeyListeners();
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );

        this.sv = parent;
        if ( cliente.isEmpty() )
            cliente = "(Venda rápida)";
        tfCliente.setText( cliente );
        tfTotal.setText( sv.nfMoeda.format( total ) );
        recebido = total;
        ftfPago.setText( sv.nf2d.format( recebido ) );
        troco = 0;
        tfTroco.setText( sv.nfMoeda.format( troco ) );
        ftfPago.grabFocus();
        ftfPago.selectAll();

        this.total = total;
        this.novoTotal = total;
    }

    private void adicionarKeyListeners()
    {
        tfCliente.addKeyListener( this );
        tfTotal.addKeyListener( this );
        ftfPago.addKeyListener( this );
        tfTroco.addKeyListener( this );
        bOk.addKeyListener( this );
        bCancelar.addKeyListener( this );
        bDesconto.addKeyListener( this );
    }

    @Action
    public void cancelarFechamento()
    {
        comandaFechada = false;
        dispose();
    }

    @Action
    public void confirmarFechamento()
    {
        comandaFechada = true;
        this.dispose();
    }

    public boolean estaComandaFechada()
    {
        return comandaFechada;
    }

    public float getDesconto()
    {
        return desconto;
    }

//    public float getTotal()
//    {
//        return novoTotal;
//    }

    /**
     * Abre tela para o usuário inserir ou o valor do desconto,
     * ou o novo valor total.
     * 
     */
    @Action
    public void obterDesconto()
    {
        TelaDarDesconto tela = new TelaDarDesconto( sv, true, total );
        tela.pack();
        tela.setLocationRelativeTo( sv.getComponent() );
        tela.setVisible( true );
        desconto = tela.getDesconto();
        assert (desconto >= 0);
        novoTotal = total - desconto;
        tfTotal.setText( sv.nfMoeda.format( novoTotal ) );

        if ( recebido > 0 )
        {
            troco = recebido - novoTotal;
            assert (troco >= 0);
            tfTroco.setText( sv.nfMoeda.format( troco ) );
            ftfPago.selectAll();
            ftfPago.grabFocus();
        }
        else
            ftfPago.grabFocus();
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
        lCliente = new javax.swing.JLabel();
        lTotal = new javax.swing.JLabel();
        lPago = new javax.swing.JLabel();
        lTroco = new javax.swing.JLabel();
        tfCliente = new javax.swing.JTextField();
        tfTotal = new javax.swing.JTextField();
        ftfPago = new javax.swing.JFormattedTextField();
        tfTroco = new javax.swing.JTextField();
        bOk = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        bDesconto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getResourceMap(TelaFecharComanda.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        lCliente.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lCliente.setText(resourceMap.getString("lCliente.text")); // NOI18N
        lCliente.setName("lCliente"); // NOI18N

        lTotal.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lTotal.setText(resourceMap.getString("lTotal.text")); // NOI18N
        lTotal.setName("lTotal"); // NOI18N

        lPago.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lPago.setText(resourceMap.getString("lPago.text")); // NOI18N
        lPago.setName("lPago"); // NOI18N

        lTroco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lTroco.setText(resourceMap.getString("lTroco.text")); // NOI18N
        lTroco.setName("lTroco"); // NOI18N

        tfCliente.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfCliente.setEditable(false);
        tfCliente.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfCliente.setText(resourceMap.getString("tfCliente.text")); // NOI18N
        tfCliente.setEnabled(false);
        tfCliente.setFocusable(false);
        tfCliente.setName("tfCliente"); // NOI18N

        tfTotal.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfTotal.setEditable(false);
        tfTotal.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfTotal.setEnabled(false);
        tfTotal.setFocusable(false);
        tfTotal.setName("tfTotal"); // NOI18N

        ftfPago.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        ftfPago.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        ftfPago.setText(resourceMap.getString("ftfPago.text")); // NOI18N
        ftfPago.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        ftfPago.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        ftfPago.setName("ftfPago"); // NOI18N
        ftfPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftfPagoActionPerformed(evt);
            }
        });
        ftfPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ftfPagoKeyReleased(evt);
            }
        });

        tfTroco.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfTroco.setEditable(false);
        tfTroco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfTroco.setEnabled(false);
        tfTroco.setFocusable(false);
        tfTroco.setName("tfTroco"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getActionMap(TelaFecharComanda.class, this);
        bOk.setAction(actionMap.get("confirmarFechamento")); // NOI18N
        bOk.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        bOk.setText(resourceMap.getString("bOk.text")); // NOI18N
        bOk.setMaximumSize(new java.awt.Dimension(88, 25));
        bOk.setMinimumSize(new java.awt.Dimension(88, 25));
        bOk.setName("bOk"); // NOI18N
        bOk.setPreferredSize(new java.awt.Dimension(90, 25));

        bCancelar.setAction(actionMap.get("cancelarFechamento")); // NOI18N
        bCancelar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        bCancelar.setText(resourceMap.getString("bCancelar.text")); // NOI18N
        bCancelar.setMaximumSize(new java.awt.Dimension(88, 25));
        bCancelar.setMinimumSize(new java.awt.Dimension(88, 25));
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.setPreferredSize(new java.awt.Dimension(90, 25));

        bDesconto.setAction(actionMap.get("obterDesconto")); // NOI18N
        bDesconto.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        bDesconto.setText(resourceMap.getString("bDesconto.text")); // NOI18N
        bDesconto.setMaximumSize(new java.awt.Dimension(180, 25));
        bDesconto.setMinimumSize(new java.awt.Dimension(180, 25));
        bDesconto.setName("bDesconto"); // NOI18N
        bDesconto.setPreferredSize(new java.awt.Dimension(185, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lPago)
                            .addComponent(lTroco)
                            .addComponent(lTotal)
                            .addComponent(lCliente))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftfPago, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCliente)
                    .addComponent(tfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lTotal)
                    .addComponent(tfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lPago)
                    .addComponent(ftfPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lTroco)
                    .addComponent(tfTroco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ftfPagoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ftfPagoActionPerformed
    {//GEN-HEADEREND:event_ftfPagoActionPerformed
        try
        {
            sv.ajustarCampoParaMoeda( evt );
            recebido = sv.nfMoeda.parse( ftfPago.getText() ).floatValue();
            troco = recebido - novoTotal;
            if ( troco < 0 )
            {
                JOptionPane.showMessageDialog( sv.getComponent(),
                    "O valor pago foi menor que o total. Tente novamente.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE );
                ftfPago.setText( "" );
                tfTroco.setText( "" );
                ftfPago.requestFocus();
            }
//            else if ( troco == 0 )
//            {
//                confirmarFechamento();
//            }
            else
            {
//                tfTroco.setText( sv.nfMoeda.format( troco ) );
                confirmarFechamento();
            }
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( TelaFecharComanda.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }//GEN-LAST:event_ftfPagoActionPerformed

    private void ftfPagoKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_ftfPagoKeyReleased
    {//GEN-HEADEREND:event_ftfPagoKeyReleased
        String strTecla = Character.toString( evt.getKeyChar() );
        int intCodigoTecla = evt.getKeyCode();
        boolean houveDelecao = ( intCodigoTecla == KeyEvent.VK_BACK_SPACE || intCodigoTecla == KeyEvent.VK_DELETE );

        if( strTecla.matches( "\\p{Print}" ) || houveDelecao )
        {
            if ( strTecla.matches( "[0-9\\,]" ) || houveDelecao )
            {
                String strPago = ftfPago.getText();
                if ( strPago.isEmpty() )
                    recebido = 0;
                else
                {
                    try
                    {
                        recebido = sv.nf2d.parse( strPago ).floatValue();
                        troco = recebido - novoTotal;
                        tfTroco.setText( sv.nfMoeda.format( troco ) );
                    }
                    catch ( ParseException ex )
                    {
                        Logger.getLogger( TelaFecharComanda.class.getName() ).log( Level.SEVERE, null, ex );
                    }
                }
            }
        }
    }//GEN-LAST:event_ftfPagoKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bDesconto;
    private javax.swing.JButton bOk;
    private javax.swing.JFormattedTextField ftfPago;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lCliente;
    private javax.swing.JLabel lPago;
    private javax.swing.JLabel lTotal;
    private javax.swing.JLabel lTroco;
    private javax.swing.JTextField tfCliente;
    private javax.swing.JTextField tfTotal;
    private javax.swing.JTextField tfTroco;
    // End of variables declaration//GEN-END:variables

    public void keyTyped( KeyEvent e )
    {
        if ( e.getKeyCode() == KeyEvent.VK_F12 )
            obterDesconto();
        else if ( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            cancelarFechamento();
    }

    public void keyPressed( KeyEvent e )
    {
        //throw new UnsupportedOperationException( "Not supported yet." );
        if ( e.getKeyCode() == KeyEvent.VK_F12 )
            obterDesconto();
        else if ( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            cancelarFechamento();
    }

    public void keyReleased( KeyEvent e )
    {
        //throw new UnsupportedOperationException( "Not supported yet." );
    }
}