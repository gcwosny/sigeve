/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TelaReforco.java
 *
 * Created on 21/11/2010, 12:40:12
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
public class TelaReforco extends javax.swing.JDialog implements KeyListener {

    boolean acaoRealizada;
    float valor;
    SigeveView sv;

    /** Creates new form TelaReforco
     * @param parent
     * @param modal 
     */
    public TelaReforco(SigeveView parent, boolean modal) {
        super( parent.getFrame(), modal );
        initComponents();
        adicionarKeyListeners();
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        sv = parent;
    }
    
    private void adicionarKeyListeners()
    {
        bCancelar.addKeyListener( this );
        bOk.addKeyListener( this );
        ftfValor.addKeyListener( this );
    }
        
    @Action
    public void cancelar()
    {
        acaoRealizada = false;
        dispose();
    }

    @Action
    public void confirmar()
    {
        if ( valor > 0 )
        {
            acaoRealizada = true;
            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog( jPanel1,
                "Valor inválido. Tente novamente.",
                "Reforço",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public float getValor()
    {
        return valor;
    }

    public boolean houveAcao()
    {
        return acaoRealizada;
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
        lDescricao = new javax.swing.JLabel();
        ftfValor = new javax.swing.JFormattedTextField();
        bOk = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getResourceMap(TelaReforco.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        lDescricao.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lDescricao.setText(resourceMap.getString("lDescricao.text")); // NOI18N
        lDescricao.setName("lDescricao"); // NOI18N

        ftfValor.setBackground(resourceMap.getColor("ftfValor.background")); // NOI18N
        ftfValor.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        ftfValor.setName("ftfValor"); // NOI18N
        ftfValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftfValorActionPerformed(evt);
            }
        });
        ftfValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ftfValorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftfValorFocusLost(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getActionMap(TelaReforco.class, this);
        bOk.setAction(actionMap.get("confirmar")); // NOI18N
        bOk.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        bOk.setText(resourceMap.getString("bOk.text")); // NOI18N
        bOk.setMaximumSize(new java.awt.Dimension(96, 25));
        bOk.setMinimumSize(new java.awt.Dimension(96, 25));
        bOk.setName("bOk"); // NOI18N
        bOk.setPreferredSize(new java.awt.Dimension(96, 25));

        bCancelar.setAction(actionMap.get("cancelar")); // NOI18N
        bCancelar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        bCancelar.setText(resourceMap.getString("bCancelar.text")); // NOI18N
        bCancelar.setMaximumSize(new java.awt.Dimension(96, 25));
        bCancelar.setMinimumSize(new java.awt.Dimension(96, 25));
        bCancelar.setName("bCancelar"); // NOI18N
        bCancelar.setPreferredSize(new java.awt.Dimension(96, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ftfValor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lDescricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addComponent(bOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lDescricao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ftfValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ftfValorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ftfValorActionPerformed
    {//GEN-HEADEREND:event_ftfValorActionPerformed
        try
        {
            sv.ajustarCampoParaMoeda( evt );
            valor = sv.nfMoeda.parse( ftfValor.getText() ).floatValue();
            if ( valor < 0 )
            {
                JOptionPane.showMessageDialog( sv.getComponent(),
                    "O reforço não pode ter valor negativo. Tente novamente.",
                    "Reforço",
                    JOptionPane.WARNING_MESSAGE );
                ftfValor.setText( "" );
                ftfValor.grabFocus();
            }
            else
                confirmar();
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( TelaDarDesconto.class.getName() ).log( Level.SEVERE, null, ex );
            JOptionPane.showMessageDialog(
                sv.getComponent(),
                "Alerta! Ocorreu um erro ao informar o reforço. Informe ao responsável.",
                "Reforço",
                JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_ftfValorActionPerformed

    private void ftfValorFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_ftfValorFocusGained
    {//GEN-HEADEREND:event_ftfValorFocusGained
        try
        {
            if ( !ftfValor.getText().isEmpty() )
                ftfValor.setText(
                    sv.nf2d.format(
                    sv.nfMoeda.parse(
                    ftfValor.getText() ) ) );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.OFF, null, ex );
        }
        finally
        {
            ftfValor.selectAll();
        }
}//GEN-LAST:event_ftfValorFocusGained

    private void ftfValorFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_ftfValorFocusLost
    {//GEN-HEADEREND:event_ftfValorFocusLost
        sv.ajustarCampoParaMoeda( evt );
        try
        {
            valor = sv.nfMoeda.parse( ftfValor.getText() ).floatValue();
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( TelaReforco.class.getName() ).log( Level.SEVERE, null, ex );
        }
}//GEN-LAST:event_ftfValorFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bOk;
    private javax.swing.JFormattedTextField ftfValor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lDescricao;
    // End of variables declaration//GEN-END:variables

    public void keyTyped( KeyEvent e )
    {
        //throw new UnsupportedOperationException( "Not supported yet." );
    }

    public void keyPressed( KeyEvent e )
    {
        if ( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            cancelar();
    }

    public void keyReleased( KeyEvent e )
    {
        //throw new UnsupportedOperationException( "Not supported yet." );
    }
}
