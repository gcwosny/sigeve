/*
 * SigeveView.java
 *
 */

/*
 * TODO
 *
 * Definir ordem do foco: FocusTraversalPolicy;
 *
 * Possibilitar a escolha do produto na tela "BD" via digitando no campo
 * "Código";
 *
 * Implementar geração de relatórios;
 *
 * Escrever mensagens pop-up padronizadas no tratamento dos erros;
 *
 * Dar título à tela de Despesas (não aparece! Bug?);
 *
 * Padronizar nomenclatura das variáveis: abreviar prefixos (tanto o dos
 * elementos gráficos quanto os que indicam tipos primários (String, float);
 *
 * Por todas as referências de FontePadrao, FonteDestaque e CorNivelX para
 * Aplicativo, e não Classe';
 *
 * Documentar utilizando Microsoft Windows Help Formats
 * http://java.sun.com/docs/windows_format.html
 *
 * Impedir que se guarde mais comandas do que o número de espaços reservados.
 *
 */
package sigeve;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import static sigeve.Constantes.*;

/**
 * The application's main frame.
 */
public class SigeveView
    extends FrameView
    implements KeyListener
{
    private Jornada jornadaAtual;
    private String codigoCategoria;
    private BancoDeDados bd;
    private JToggleButton[] arrayBotoesProdutoFrente;
    private JToggleButton[] arrayBotoesProdutoBanco;
    private JToggleButton[] arrayBotoesCategoriaFrente;
    private JToggleButton[] arrayBotoesCategoriaBanco;
    private JToggleButton[] arrayBotoesComandas;
    private JToggleButton[] arrayBotoesTempo;
    private JTextField[] arrayCamposFrente;
    private JTextField[] arrayCamposBanco;
    public NumberFormat nfMoeda = new DecimalFormat( "¤ #,##0.00" );
    public NumberFormat nf2d = new DecimalFormat( "#,##0.00" );
    private NumberFormat nf3d = new DecimalFormat( "#,##0.000" );
    private NumberFormat nfComanda = new DecimalFormat( "#0" );
    private DefaultComboBoxModel comboBoxModelLojas = new DefaultComboBoxModel();
    private DefaultListSelectionModel listSelModelFuncionarios =
        new DefaultListSelectionModel()
        {
            /* JList com selectionMode=MULTIPLE_INTERVAL dispensando Ctrl
             * ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯
             * Descr: Sobrescreve método de seleção dos itens.
             * Fonte: http://forums.sun.com/thread.jspa?threadID=5313897
             */
            @Override
            public void setSelectionInterval( int index0, int index1 )
            {
                if ( isSelectedIndex( index0 ) )
                {
                    super.removeSelectionInterval( index0, index1 );
                }
                else
                {
                    super.addSelectionInterval( index0, index1 );
                }
            }
        };
    private DefaultListModel listModelFuncionariosBanco =
        new DefaultListModel();
    /*
     * listModelFuncionariosAbertura é uma ListModel de CheckListItems
     * existentes na jListFuncionariosAbertura.
     */
    private DefaultListModel listModelFuncionariosAbertura =
        new DefaultListModel();
//    /*
//     * listNomeFuncionariosAbertura é uma List dos labels das CheckListItems 
//     * que compõem a ListModel listModelFuncionariosAbertura.
//     */
//    private List<String> listNomeFuncionariosAbertura = new ArrayList<String>();
    private DefaultTableModel tableModel = new DefaultTableModel(
        new String[][]
        {
            {
                "", "", ""
            },
            {
                "", "", ""
            },
            {
                "", "", ""
            },
            {
                "", "", ""
            },
            {
                "", "", ""
            },
            {
                "", "", ""
            },
            {
                "", "", ""
            }
        },
        new String[]
        {
            "", "", ""
        } )
    {
        Class[] types = new Class[]
        {
            java.lang.Float.class,
            java.lang.String.class,
            java.lang.Float.class
        };
        boolean[] canEdit = new boolean[]
        {
            false, false, false
        };

        @Override
        public Class getColumnClass( int columnIndex )
        {
            return types[columnIndex];
        }

        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex )
        {
            return canEdit[columnIndex];
        }
    };

    public SigeveView( SingleFrameApplication app )
    {
        super( app );
        initComponents();
        final ResourceMap resourceMap = getResourceMap();

        iniciarMeusComponentes( resourceMap );

        // status bar initialization - message timeout, idle icon and busy animation, etc

//        int messageTimeout = resourceMap.getInteger( "StatusBar.messageTimeout" );
//        messageTimer = new Timer( messageTimeout, new ActionListener()
//        {
//            public void actionPerformed( ActionEvent e )
//            {
//                statusMessageLabel.setText( "" );
//            }
//        } );
//        messageTimer.setRepeats( false );
//        int busyAnimationRate = resourceMap.getInteger( "StatusBar.busyAnimationRate" );
//        for ( int i = 0; i < busyIcons.length; i++ )
//        {
//            busyIcons[i] = resourceMap.getIcon( "StatusBar.busyIcons[" + i + "]" );
//        }
//        busyIconTimer = new Timer( busyAnimationRate, new ActionListener()
//        {
//            public void actionPerformed( ActionEvent e )
//            {
//                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
//                statusAnimationLabel.setIcon( busyIcons[busyIconIndex] );
//            }
//        } );
//        idleIcon = resourceMap.getIcon( "StatusBar.idleIcon" );
//        statusAnimationLabel.setIcon( idleIcon );
//        progressBar.setVisible( false );
//
//        // connecting action tasks to status bar via TaskMonitor
//        TaskMonitor taskMonitor = new TaskMonitor( getApplication().getContext() );
//        taskMonitor.addPropertyChangeListener( new java.beans.PropertyChangeListener()
//        {
//            public void propertyChange( java.beans.PropertyChangeEvent evt )
//            {
//                String propertyName = evt.getPropertyName();
//                if ( "started".equals( propertyName ) )
//                {
//                    if ( !busyIconTimer.isRunning() )
//                    {
//                        statusAnimationLabel.setIcon( busyIcons[0] );
//                        busyIconIndex = 0;
//                        busyIconTimer.start();
//                    }
//                    progressBar.setVisible( true );
//                    progressBar.setIndeterminate( true );
//                }
//                else if ( "done".equals( propertyName ) )
//                {
//                    busyIconTimer.stop();
//                    statusAnimationLabel.setIcon( idleIcon );
//                    progressBar.setVisible( false );
//                    progressBar.setValue( 0 );
//                }
//                else if ( "message".equals( propertyName ) )
//                {
//                    String text = ( String ) (evt.getNewValue());
//                    statusMessageLabel.setText( (text == null) ? "" : text );
//                    messageTimer.restart();
//                }
//                else if ( "progress".equals( propertyName ) )
//                {
//                    int value = ( Integer ) (evt.getNewValue());
//                    progressBar.setVisible( true );
//                    progressBar.setIndeterminate( false );
//                    progressBar.setValue( value );
//                }
//            }
//        } );



        /*
         * Descr: Aviso de jornada aberta ao sair do programa.
         * Fonte: http://www.kodejava.org/examples/185.html
         */
        app.getMainFrame().setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        this.getFrame().setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        this.getFrame().addWindowListener(
            new WindowAdapter()
            {
                @Override
                public void windowClosing( WindowEvent e )
                {
                    sair();
                }
            } );
    }

    @Action
    public void abrirCaixa()
    {
        try
        {
            if ( estaPreenchidaTelaAbertura() )
            {
                // Atualizar horários
                jTextFieldData.setText( CurrentDateAndTime.getDate() );
                jTextFieldHora.setText( CurrentDateAndTime.getTime() );
                jTextFieldSemana.setText( CurrentDateAndTime.getDay() );

                adicionarJornada();
                relacionarFuncionariosJornada();
                bloquearTelaAbertura();
                jTabbedPane.setEnabledAt( 1, true ); // Habilita tela "Frente"
                jTabbedPane.setSelectedComponent( jPanelFrente );

                // Transfere para a jornada atual comandas que ficaram em aberto:
                if ( bd.existeComandaAbertaBD() )
                {
                    bd.transfereComandasAbertasParaJornadaAberta();
                    carregarComandasGuardadas();

                    // Por haver comanda(s) aberta(s):
                    jButtonSelecionar.setEnabled( true );
                    jButtonJuntar.setEnabled( true );

                    if ( bd.existeComandaAtivaBD() )
                    {
                        carregarComandaAtiva();

                        // Por haver comanda ativa:
                        jButtonExcluirItem.setEnabled( true );
                        jButtonSeparar.setEnabled( true );
                        jButtonFinalizar.setEnabled( true );
                        jButtonGuardar.setEnabled( true );
                    }
                }
            }
            else
            {
                JOptionPane.showMessageDialog(
                    jPanelComanda,
                    "Faltou informar ou os funcionários que irão trabalhar "
                    + "ou o valor em caixa inicial.",
                    "Atenção",
                    JOptionPane.ERROR_MESSAGE );
            }
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }

    }

    private void adicionarComanda()
    {
        try
        {
            bd.adicionarComandaBD(
                CurrentDateAndTime.getDate() + " " + CurrentDateAndTime.getTime() ); //,

            // Por haver comanda ativa:
            jButtonExcluirItem.setEnabled( true );
            jButtonSeparar.setEnabled( true );
            jButtonFinalizar.setEnabled( true );
            jButtonGuardar.setEnabled( true );

            // Por haver comanda(s) aberta(s):
            jButtonSelecionar.setEnabled( true );
            jButtonJuntar.setEnabled( true );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    @Action
    public void adicionarDespesa()
    {
        try
        {
            TelaDespesas tela = new TelaDespesas( this, true );
            tela.pack();
            tela.setLocationRelativeTo( this.getComponent() );
            tela.setVisible( true );

            if ( tela.foiDespesaRealizada() )
                bd.adicionarDespesaBD( tela.getDescricao(), tela.getValor() );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * Insere nova jornada na tabela Jornada do BD.
     */
    private void adicionarJornada()
    {
        try
        {
            bd.adicionarJornadaBD(
                jTextFieldLoja.getText(),
                jTextFieldData.getText() + " " + jTextFieldHora.getText(),
                ( String ) jComboBoxTurno.getSelectedItem(),
                nfMoeda.parse( jFormattedTextFieldCaixa.getText() ).floatValue() );
        }
        catch ( ParseException pe )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, pe );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * Adição de key listeners aos elementos, quando selecionados, devem ficar
     * de prontidão para receber um dos comandos de atalho de teclado.
     * 
     */
    private void adicionarKeyListeners()
    {
        jTabbedPane.addKeyListener( this );
        jPanelFrente.addKeyListener( this );

        // Elementos do painel jPanelEntradaFrente:
        jPanelEntradaFrente.addKeyListener( this );
        //jLabelCodigo.addKeyListener( this );
        jFormattedTextFieldQtdeFrente.addKeyListener( this );
        jButtonConfirmar.addKeyListener( this );

        // Elementros do painel jPanelCategoriasFrente:
        jPanelCategoriasFrente.addKeyListener( this );
        for ( JToggleButton jtb : arrayBotoesCategoriaFrente )
            jtb.addKeyListener( this );

        // Elementros do painel jPanelProdutosFrente:
        jPanelProdutosFrente.addKeyListener( this );
        for ( JToggleButton jtb : arrayBotoesProdutoFrente )
            jtb.addKeyListener( this );

        // Elementos do painel jPanelComanda:
        jPanelComanda.addKeyListener( this );
        jTableComanda.addKeyListener( this );
        jButtonExcluirItem.addKeyListener( this );
        jButtonGuardar.addKeyListener( this );
        jButtonFinalizar.addKeyListener( this );

        // Elementos do painel jPanelClientes:
        jPanelClientes.addKeyListener( this );
        for ( JToggleButton jtb : arrayBotoesComandas )
            jtb.addKeyListener( this );
        jButtonSelecionar.addKeyListener( this );
        jButtonJuntar.addKeyListener( this );
        jButtonSeparar.addKeyListener( this );

        // Elementos do painel jPanelTempo:
        jPanelTempo.addKeyListener( this );
        jToggleButtonEnsolarado.addKeyListener( this );
        jToggleButtonNublado.addKeyListener( this );
        jToggleButtonInstavel.addKeyListener( this );
        jToggleButtonChuvoso.addKeyListener( this );

        // Elementos do painel jPanelDespesas:
        jButtonAbastec.addKeyListener( this );
        jButtonReforco.addKeyListener( this );
        jButtonSangria.addKeyListener( this );
        jButtonDespesas.addKeyListener( this );
        jButtonComandas.addKeyListener( this );
        jButtonFechamento.addKeyListener( this );
    }

    /**
     * Avalia se os campos <i>Total</i> e <i>Produto</i> não estão vazios.
     * Insere nova comanda, caso não haja nenhuma ativa.
     * Adiciona novo pedido à comanda ativa.
     * 
     */
    @Action
    public void adicionarPedido()
    {
        try
        {
            String descr = jTextFieldProdutoFrente.getText();
            String strQtde = jFormattedTextFieldQtdeFrente.getText();
            String strTotal = jFormattedTextFieldTotalFrente.getText();

            if ( !strTotal.isEmpty() && !descr.isEmpty() )
            {
                if ( !bd.existeComandaAtivaBD() )
                    adicionarComanda();

                float qtde = nf3d.parse( strQtde ).floatValue();
                Produto produtoRegistrado = bd.obterProdutoBD( descr );
                Pedido pedidoNovo = new Pedido( produtoRegistrado, qtde );
                adicionarPedido( true, pedidoNovo );
                jTextFieldTotalComanda.setText( nfMoeda.format(
                    bd.obterTotalComandaBD() ) );
            }
            deselecionarCategoria();
            jFormattedTextFieldQtdeFrente.setEnabled( false );
            jButtonConfirmar.setEnabled( false );
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    /**
     * Insere pedidos na comanda. Existem duas situações em que este método será
     * chamado: (a) após a criação de uma nova comanda (novos pedidos são
     * realizados e devem ser cadastrados no BD) e (b) após solicitar que uma
     * comanda já aberta mas guardada/minimizada seja ativada (os pedidos já
     * cadastrados devem ser apenas reexibidos na tabela.)
     *
     * @param saoPedidosNaoCadastrados
     * @param novoPedido
     */
    @SuppressWarnings("unchecked")
    private void adicionarPedido( boolean saoPedidosNaoCadastrados,
        Pedido novoPedido )
    {
        boolean procura = true;
        int linha = 0;
        Vector<String> registro = new Vector<String>();
        Vector<String> novoRegistro = new Vector<String>();

        try
        {
            if ( saoPedidosNaoCadastrados )
                bd.adicionarPedidoBD( novoPedido );

            // Identifica onde inserir o novo registro na jTableComanda:
            while ( procura )
            {
                if ( linha < N_LINHAS_COMANDA )
                {
                    // Aqui ocorre o warning "unchecked":
                    registro = ( Vector<String> ) tableModel.getDataVector().elementAt( linha );
                    if ( !registro.elementAt( 0 ).isEmpty() )
                        linha++;
                    else
                        procura = false;
                }
                else
                    procura = false;
            }
            // Se o número de registros já tiver alcançado o número padrão de
            // linhas, insere uma nova linha com o registro:
            if ( linha == N_LINHAS_COMANDA )
            {
                novoRegistro.add( 0, nf3d.format( novoPedido.getQtde() ) );
                novoRegistro.add( 1, novoPedido.getProduto().getNome() );
                novoRegistro.add( 2, nfMoeda.format( novoPedido.getQtde()
                    * novoPedido.getProduto().getPreco() ) );
                tableModel.addRow( novoRegistro );
                // Ajuste do scrollbar da tabela:
                Dimension ps = jTableComanda.getPreferredSize();
                jTableComanda.setPreferredSize( new Dimension(
                    ps.width,
                    ps.height + jTableComanda.getRowHeight() ) );
                // Rolagem da tabela para a última linha inserida:
                jTableComanda.scrollRectToVisible(
                    jTableComanda.getCellRect(
                    jTableComanda.getRowCount() - 1,
                    jTableComanda.getColumnCount(),
                    true ) );
            }
            else
            {
                tableModel.removeRow( linha );
                novoRegistro.add( 0, nf3d.format( novoPedido.getQtde() ) );
                novoRegistro.add( 1, novoPedido.getProduto().getNome() );
                novoRegistro.add( 2, nfMoeda.format( novoPedido.getQtde()
                    * novoPedido.getProduto().getPreco() ) );
                tableModel.insertRow( linha, novoRegistro );
            }
            // O total deve ser atualizado após cada inserção de novo produto.
            //jTextFieldTotalComanda.setText( nfMoeda.format(
            //    bd.obterTotalComandaBD() ) );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * Cadastra produto no BD.
     */
    @Action
    public void adicionarProduto()
    {
        try
        {
            String precoString = jFormattedTextFieldValorBanco.getText();
            float preco = nfMoeda.parse( precoString ).floatValue();
            String codigo = buttonGroupProdutosBanco.getSelection().getActionCommand();
            String nome = jTextFieldProdutoBanco.getText();
            Produto novoProduto = new Produto( codigoCategoria, codigo, nome, preco );
            bd.adicionarProdutoBD( novoProduto );
        }
        catch ( ParseException pe )
        { // Caso ocorra erro na conversão de preco para float:
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, pe );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
        selecionarCategoriaBanco();
        arrayBotoesCategoriaBanco[Integer.parseInt( codigoCategoria )].requestFocusInWindow();
    }

    /**
     * Ajusta o número inserido para o formato moeda. Utilizado após a edição
     * de campos em que o usuário deve entrar um valor monetário.
     *
     * @param evt
     * @return
     */
    public void ajustarCampoParaMoeda( AWTEvent evt )
    {
        // Para colocar no formato correto (Monetário):
        JTextField tf = ( JTextField ) evt.getSource();
        String str = tf.getText();
        float num;
        if ( !str.isEmpty() )
        {
            try
            {
                // Tenta tratar string como decimal com 2 casas:
                num = nf2d.parse( str ).floatValue();
                tf.setText( nfMoeda.format( num ) );
            }
            catch ( ParseException ex1 )
            {
                try
                {
                    // Tenta tratar string como moeda:
                    num = nfMoeda.parse( str ).floatValue();
                    tf.setText( nfMoeda.format( num ) );
                }
                catch ( ParseException ex2 )
                {
                    Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex1 );
                    Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex2 );
                }
            }
        }
        else
        {
            num = 0;
            tf.setText( nfMoeda.format( num ) );
        }
    }

    /**
     * Define qual loja é a atual, alterando o seu estado na tabela
     * <i>Lojas</i> do BD para <i>Verdadeiro</i>.
     *
     * @param nome
     */
    public void alterarLoja( String nome )
    {
        try
        {
            bd.alterarLojaBD( nome );
            jTextFieldLoja.setText( nome );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    public void alterarNomeCliente( String nome )
    {
        try
        {
            bd.alterarNomeClienteBD( nome );
            int intPosicao = bd.obterPosicaoAtivaBD();
            arrayBotoesComandas[intPosicao].setText( intPosicao + ". " + nome );
            jTextFieldCodigoFrente.grabFocus();
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * 
     */
    @Action
    public void alterarSenha()
    {
        // Confere se a senha nova foi repetida corretamente:
        if ( new String( jPasswordFieldSenhaNova.getPassword() ).equals(
            new String( jPasswordFieldSenhaRepetir.getPassword() ) ) )
        {
            // Confere se a senha atual é válida
            try
            {
                if ( bd.eSenhaValidaBD( new String( jPasswordFieldSenhaAtual.getPassword() ) ) )
                {
                    bd.alterarSenhaBD( new String( jPasswordFieldSenhaNova.getPassword() ) );
                    JOptionPane.showMessageDialog( jPanelSenha,
                        "Senha alterada com sucesso.",
                        "Alteração de senha",
                        JOptionPane.INFORMATION_MESSAGE );
                }
                else
                {
                    JOptionPane.showMessageDialog( jPanelSenha,
                        "Senha atual inválida. Esta operação foi cancelada.",
                        "Alteração de senha",
                        JOptionPane.WARNING_MESSAGE );
                }
            }
            catch ( SQLException ex )
            {
                Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
                JOptionPane.showMessageDialog( jPanelSenha,
                    "Ocorreu um erro no processamento. Esta operação foi cancelada.",
                    "Alteração de senha",
                    JOptionPane.WARNING_MESSAGE );
            }
        }
        else
            JOptionPane.showMessageDialog( jPanelSenha,
                "A senha nova não foi repetida corretamente. Esta operação foi cancelada.",
                "Alteração de senha",
                JOptionPane.WARNING_MESSAGE );

        jPasswordFieldSenhaAtual.setText( "" );
        jPasswordFieldSenhaNova.setText( "" );
        jPasswordFieldSenhaRepetir.setText( "" );
    }

    /**
     *
     * @param evt
     * @param origem (1 - tela Frente de loja, 2 - tela Cadastramento)
     */
    public synchronized void analisarCodigo( KeyEvent evt, int origem )
    {
        String strTecla = Character.toString( evt.getKeyChar() );
        int keyCode = evt.getKeyCode();
        boolean houveDelecao = (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE);

        // Se o código foi digitado na tela "Frente de loja":
        if ( origem == 1 )
        {
            // Se for alguma das teclas de atalho do programa:
            if ( strTecla.matches( "[\\*\\+\\-\\/]" ) || keyCode == KeyEvent.VK_ESCAPE )
            {
                jTextFieldCodigoFrente.setText( "" );
                buttonGroupCategoriasFrente.setSelected( jToggleButtonFCFantasma.getModel(), true );
                keyReleased( evt );
            }
            // Se for alguma tecla que gera caractere:
            else if ( strTecla.matches( "\\p{Print}" ) )
            {
                if ( strTecla.matches( "[0-9]" ) )
                {
                    // Se ainda nenhuma categoria foi selecionada (seleção = fantasma):
                    if ( buttonGroupCategoriasFrente.isSelected( jToggleButtonFCFantasma.getModel() ) )
                    {
                        int intCodigoCategoria = Integer.parseInt( strTecla );
                        buttonGroupCategoriasFrente.setSelected(
                            arrayBotoesCategoriaFrente[intCodigoCategoria].getModel(),
                            true );
                        selecionarCategoriaFrente();
                    }
                    else
                    {
                        int intCodigoProduto = Integer.parseInt( strTecla );
                        buttonGroupProdutosFrente.setSelected(
                            arrayBotoesProdutoFrente[intCodigoProduto].getModel(),
                            true );
                        selecionarProdutoFrente();
                        jFormattedTextFieldQtdeFrente.setEnabled( true );
                        jFormattedTextFieldQtdeFrente.setText( "1" );
                        jFormattedTextFieldQtdeFrente.grabFocus();
                        jFormattedTextFieldQtdeFrente.selectAll();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog( jPanelFrente,
                        "O campo \"Código\" deve receber apenas números.",
                        "Definição do pedido",
                        JOptionPane.WARNING_MESSAGE );
                    jTextFieldCodigoFrente.setText( "" );
                }
            }
            else if ( houveDelecao )
            {
                buttonGroupCategoriasFrente.setSelected( jToggleButtonFCFantasma.getModel(), true );
            }
        }
        // Se o código foi digitado na tela "Cadastramento":
        else if ( origem == 2 )
        {
            if ( strTecla.matches( "\\p{Print}" ) )
            {
                if ( strTecla.matches( "[0-9]" ) )
                {
                    // Se ainda nenhuma categoria foi selecionada (seleção = fantasma):
                    if ( buttonGroupCategoriasBanco.isSelected( jToggleButtonBCFantasma.getModel() ) )
                    {
                        int intCodigoCategoria = Integer.parseInt( strTecla );
                        buttonGroupCategoriasBanco.setSelected(
                            arrayBotoesCategoriaBanco[intCodigoCategoria].getModel(),
                            true );
                        selecionarCategoriaBanco();
                    }
                    else
                    {
                        int intCodigoProduto = Integer.parseInt( strTecla );
                        buttonGroupProdutosBanco.setSelected(
                            arrayBotoesProdutoBanco[intCodigoProduto].getModel(),
                            true );
                        selecionarProdutoBanco();
                        jFormattedTextFieldValorBanco.grabFocus();
                        jFormattedTextFieldValorBanco.selectAll();
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog( jPanelFrente,
                        "O campo \"Código\" deve receber apenas números.",
                        "Cadastranto de produtos",
                        JOptionPane.WARNING_MESSAGE );
                    jTextFieldCodigoBanco.setText( "" );
                }
            }
            else if ( houveDelecao )
            {
                buttonGroupCategoriasBanco.setSelected( jToggleButtonBCFantasma.getModel(), true );
            }
        }
    }
    
    public void atualizarVersao()
    {
        // Nova tabela inserida: ListaSolicitacoes
        bd.atualizarVersao();
    }

    private void bloquearTelaAbertura()
    {
        jComboBoxTurno.setEnabled( false );
        jListFuncionariosAbertura.setEnabled( false );
        jFormattedTextFieldCaixa.setEnabled( false );
        jButtonCaixa.setEnabled( false );
    }

    /**
     * Ativa a comanda na posição dada e a carrega.
     * @param posicao
     */
    private void carregarComanda( int posicao )
    {
        assert (posicao >= 1 && posicao <= N_BOTOES_COMANDAS);

        try
        {
            if ( bd.existeComandaAtivaBD() )
                guardarComanda();
            if ( bd.estaPosicaoOcupadaBD( posicao ) )
            {
                bd.ativarComandaBD( posicao );
                carregarComandaAtiva();
            }
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    /**
     * Carrega a comanda ativa.
     */
    private void carregarComandaAtiva()
    {
        try
        {
            bd.atualizarTotalComandaBD();
            jTextFieldCliente.setText( bd.obterNomeClienteBD() );

            // Preencher a tabela da comanda ativa com os pedidos:
            Iterator<Pedido> it = bd.obterPedidosDaComandaAtivaBD().iterator();
            while ( it.hasNext() )
                adicionarPedido( false, it.next() );

            jTextFieldTotalComanda.setText( nfMoeda.format(
                bd.obterTotalComandaBD() ) );

            // Por haver comanda ativa:
            jButtonExcluirItem.setEnabled( true );
            jButtonSeparar.setEnabled( true );
            jButtonFinalizar.setEnabled( true );
            jButtonGuardar.setEnabled( true );

            // Por haver comanda(s) aberta(s):
            jButtonSelecionar.setEnabled( true );
            jButtonJuntar.setEnabled( true );
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    private void carregarComandasGuardadas()
    {
        try
        {
            Comanda comandaAberta;
            Iterator<Comanda> it = bd.listarComandasAbertasBD().iterator();
            while ( it.hasNext() )
            {
                comandaAberta = it.next();

                assert (comandaAberta.getPosicao() >= 1 ) :
                    "Posição da comanda é " + comandaAberta.getPosicao() +
                    " (não pode ser menor que 1)";
                assert (comandaAberta.getPosicao() <= N_BOTOES_COMANDAS) :
                    "Posição da comanda é " + comandaAberta.getPosicao() +
                    " (não pode ser maior que " + N_BOTOES_COMANDAS + ")";

                arrayBotoesComandas[comandaAberta.getPosicao()].setText(
                    arrayBotoesComandas[comandaAberta.getPosicao()].getActionCommand()
                    + ". "
                    + comandaAberta.getNome() );
            }
            // Por haver comanda(s) aberta(s):
            jButtonSelecionar.setEnabled( true );
            jButtonJuntar.setEnabled( true );
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    /**
     * Inicia <i>jTableFuncionarios</i> com os valores contidos na tabela
     * <i>Funcionarios</i> do BD.
     */
    private void carregarListaFuncionarios()
    {
        try
        {
            String nome;
            Iterator<String> it = bd.listarNomesBD( "Funcionarios" ).iterator();
            while ( it.hasNext() )
            {
                nome = it.next();
                listModelFuncionariosBanco.addElement( nome );
                listModelFuncionariosAbertura.addElement( new CheckListItem( nome ) );
            }
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * Iniciar <i>jTableFuncionarios</i> com os valores contidos na
     * tabela <i>Funcionarios</i> do BD.
     */
    private void carregarListaLojas()
    {
        try
        {
            String nome = bd.identificarLojaBD();
            jTextFieldLoja.setText( nome );
            comboBoxModelLojas.setSelectedItem( nome );
            Iterator<String> it = bd.listarNomesBD( "Lojas" ).iterator();
            while ( it.hasNext() )
                comboBoxModelLojas.addElement( it.next() );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * Constrói array com os botões de seleção dos produtos
     */
    private void construirArrays()
    {
        arrayBotoesProdutoBanco = new JToggleButton[N_BOTOES_PRODUTOS];
        arrayBotoesProdutoBanco[0] = jToggleButtonBP0;
        arrayBotoesProdutoBanco[1] = jToggleButtonBP1;
        arrayBotoesProdutoBanco[2] = jToggleButtonBP2;
        arrayBotoesProdutoBanco[3] = jToggleButtonBP3;
        arrayBotoesProdutoBanco[4] = jToggleButtonBP4;
        arrayBotoesProdutoBanco[5] = jToggleButtonBP5;
        arrayBotoesProdutoBanco[6] = jToggleButtonBP6;
        arrayBotoesProdutoBanco[7] = jToggleButtonBP7;
        arrayBotoesProdutoBanco[8] = jToggleButtonBP8;
        arrayBotoesProdutoBanco[9] = jToggleButtonBP9;

        arrayBotoesProdutoFrente = new JToggleButton[N_BOTOES_PRODUTOS];
        arrayBotoesProdutoFrente[0] = jToggleButtonFP0;
        arrayBotoesProdutoFrente[1] = jToggleButtonFP1;
        arrayBotoesProdutoFrente[2] = jToggleButtonFP2;
        arrayBotoesProdutoFrente[3] = jToggleButtonFP3;
        arrayBotoesProdutoFrente[4] = jToggleButtonFP4;
        arrayBotoesProdutoFrente[5] = jToggleButtonFP5;
        arrayBotoesProdutoFrente[6] = jToggleButtonFP6;
        arrayBotoesProdutoFrente[7] = jToggleButtonFP7;
        arrayBotoesProdutoFrente[8] = jToggleButtonFP8;
        arrayBotoesProdutoFrente[9] = jToggleButtonFP9;

        arrayBotoesCategoriaFrente = new JToggleButton[N_BOTOES_CATEGORIAS];
        arrayBotoesCategoriaFrente[0] = jToggleButtonFC0;
        arrayBotoesCategoriaFrente[1] = jToggleButtonFC1;
        arrayBotoesCategoriaFrente[2] = jToggleButtonFC2;
        arrayBotoesCategoriaFrente[3] = jToggleButtonFC3;
        arrayBotoesCategoriaFrente[4] = jToggleButtonFC4;
        arrayBotoesCategoriaFrente[5] = jToggleButtonFC5;
        arrayBotoesCategoriaFrente[6] = jToggleButtonFC6;
        arrayBotoesCategoriaFrente[7] = jToggleButtonFC7;
        arrayBotoesCategoriaFrente[8] = jToggleButtonFC8;
        arrayBotoesCategoriaFrente[9] = jToggleButtonFC9;

        arrayBotoesCategoriaBanco = new JToggleButton[N_BOTOES_CATEGORIAS];
        arrayBotoesCategoriaBanco[0] = jToggleButtonBC0;
        arrayBotoesCategoriaBanco[1] = jToggleButtonBC1;
        arrayBotoesCategoriaBanco[2] = jToggleButtonBC2;
        arrayBotoesCategoriaBanco[3] = jToggleButtonBC3;
        arrayBotoesCategoriaBanco[4] = jToggleButtonBC4;
        arrayBotoesCategoriaBanco[5] = jToggleButtonBC5;
        arrayBotoesCategoriaBanco[6] = jToggleButtonBC6;
        arrayBotoesCategoriaBanco[7] = jToggleButtonBC7;
        arrayBotoesCategoriaBanco[8] = jToggleButtonBC8;
        arrayBotoesCategoriaBanco[9] = jToggleButtonBC9;

        arrayBotoesComandas = new JToggleButton[N_BOTOES_COMANDAS + 1];
        arrayBotoesComandas[0] = jToggleButtonComandaFantasma;
        arrayBotoesComandas[1] = jToggleButtonComanda01;
        arrayBotoesComandas[2] = jToggleButtonComanda02;
        arrayBotoesComandas[3] = jToggleButtonComanda03;
        arrayBotoesComandas[4] = jToggleButtonComanda04;
        arrayBotoesComandas[5] = jToggleButtonComanda05;
        arrayBotoesComandas[6] = jToggleButtonComanda06;
        arrayBotoesComandas[7] = jToggleButtonComanda07;
        arrayBotoesComandas[8] = jToggleButtonComanda08;
        arrayBotoesComandas[9] = jToggleButtonComanda09;
        arrayBotoesComandas[10] = jToggleButtonComanda10;
        arrayBotoesComandas[11] = jToggleButtonComanda11;
        arrayBotoesComandas[12] = jToggleButtonComanda12;
        arrayBotoesComandas[13] = jToggleButtonComanda13;
        arrayBotoesComandas[14] = jToggleButtonComanda14;
        arrayBotoesComandas[15] = jToggleButtonComanda15;

        arrayBotoesTempo = new JToggleButton[N_BOTOES_TEMPO + 1];
        arrayBotoesTempo[0] = jToggleButtonTempoFantasma;
        arrayBotoesTempo[1] = jToggleButtonEnsolarado;
        arrayBotoesTempo[2] = jToggleButtonNublado;
        arrayBotoesTempo[3] = jToggleButtonInstavel;
        arrayBotoesTempo[4] = jToggleButtonChuvoso;

        arrayCamposFrente = new JTextField[N_CAMPOS_FRENTE];
        arrayCamposFrente[0] = jTextFieldCodigoFrente;
        arrayCamposFrente[1] = ( JTextField ) jFormattedTextFieldQtdeFrente;
        arrayCamposFrente[2] = ( JTextField ) jFormattedTextFieldValorFrente;
        arrayCamposFrente[3] = ( JTextField ) jFormattedTextFieldTotalFrente;
        arrayCamposFrente[4] = jTextFieldProdutoFrente;

        arrayCamposBanco = new JTextField[N_CAMPOS_BANCO];
        arrayCamposBanco[0] = jTextFieldCodigoBanco;
        arrayCamposBanco[1] = ( JTextField ) jFormattedTextFieldValorBanco;
        arrayCamposBanco[2] = jTextFieldProdutoBanco;
    }

    public void definirAbaAberta()
    {
        jTabbedPane.setSelectedComponent( jPanelAbertura );
    }

    public void sugerirCaixaInicial( float novoCaixa )
    {
        jFormattedTextFieldCaixa.setText( nfMoeda.format( novoCaixa ) );
    }

    /**
     * Deseleciona categoria (após a inserção de um produto na comanda, deixando
     * o sistema pronto para receber um novo código)
     */
    private void deselecionarCategoria()
    {
        buttonGroupCategoriasFrente.setSelected( jToggleButtonFCFantasma.getModel(), true );
        jFormattedTextFieldQtdeFrente.setText( "" );
        jTextFieldCodigoFrente.requestFocusInWindow();
        selecionarCategoriaFrente();
    }

    @Action
    public void dividirComanda()
    {
        int posicao = 0;
        try
        {
            if ( bd.existeComandaAtivaBD() )
            {
                // Se não houverem pedidos selecionados:
                if ( jTableComanda.getSelectedRows().length == 0 )
                    JOptionPane.showMessageDialog( jPanelComanda,
                        "Selecione antes os pedidos que irão compor uma nova comanda.",
                        "Separação de comandas",
                        JOptionPane.WARNING_MESSAGE );
                // Se houverem pedidos selecionados:
                else
                {
                    TelaGuardarComanda tela = new TelaGuardarComanda( this, true, bd );
                    tela.pack();
                    tela.setLocationRelativeTo( this.getComponent() );
                    tela.setVisible( true );
                    String nome = tela.obterIdentificacao();
                    // Se o inputDialog não tiver sido cancelado:
                    if ( nome != null )
                    {
                        // Se o nome entrado estiver em branco:
                        if ( nome.equals( "" ) )
                        {
                            JOptionPane.showMessageDialog( jPanelComanda,
                                "Não foi informado o dado solicitado. Operação cancelada.",
                                "Separação de comandas",
                                JOptionPane.WARNING_MESSAGE );
                        }
                        // Se foi digitado um número:
                        else if ( nome.matches( "[0-9]+" ) )
                        {
                            posicao = nfComanda.parse( nome ).intValue();
                            if ( !bd.estaPosicaoOcupadaBD( posicao ) )
                                JOptionPane.showMessageDialog( jPanelComanda,
                                    "Nenhum cliente está armazenado na posição "
                                    + posicao + ". Operação cancelada.",
                                    "Separação de comandas",
                                    JOptionPane.WARNING_MESSAGE );
                        }
                        // Se foi digitado um nome ou um número válido:
                        if ( !nome.equals( "" )
                            || !nome.matches( "[0-9]+" )
                            || bd.estaPosicaoOcupadaBD( posicao ) )
                        {
                            // Id da comanda que irá perder os pedidos selecionados:
                            int comandaIdOrigem = bd.obterComandaIdBD();

                            // Identifica pedidos a serem trocados de comanda e armazena-os numa coleção:
                            float fltQtde;
                            String nomeProduto;
                            String strQtde;
                            Produto produtoRegistrado;
                            Pedido pedidoRegistrado;
                            List<Pedido> pedidosSelecionados = new ArrayList<Pedido>();
                            for ( int intLinhaPedido : jTableComanda.getSelectedRows() )
                            {
                                nomeProduto = ( String ) jTableComanda.getValueAt( intLinhaPedido, COL_DESCR );
                                strQtde = ( String ) jTableComanda.getValueAt( intLinhaPedido, COL_QTDE );
                                fltQtde = nf3d.parse( strQtde ).floatValue();

                                produtoRegistrado = bd.obterProdutoBD( nomeProduto );
                                pedidoRegistrado = bd.obterPedidoBD( comandaIdOrigem, produtoRegistrado, fltQtde );
                                pedidosSelecionados.add( pedidoRegistrado );
                            }

                            // Guardar comanda que perdeu os pedidos e prepara a que irá recebe-los:
                            boolean comandaGuardada = false;
                            while ( !comandaGuardada )
                                comandaGuardada = guardarComanda();

                            // Se foi digitado um nome:
                            if ( !nome.matches( "[0-9]+" ) )
                            {
                                adicionarComanda();
                                posicao = bd.obterPosicaoDisponivelBD();
                                bd.alterarPosicaoClienteBD( posicao );
                                bd.alterarNomeClienteBD( nome );
                                arrayBotoesComandas[posicao].setText( posicao + ". " + nome );
                                jTextFieldCliente.setText( nome );
                            }
                            // Se foi digitado um número válido:
                            else
                                carregarComanda( posicao );

                            Iterator<Pedido> it = pedidosSelecionados.iterator();
                            while ( it.hasNext() )
                            {
                                pedidoRegistrado = it.next();
                                bd.moverPedidoBD( comandaIdOrigem, pedidoRegistrado );
                            }
                            limparTabelaComanda();
                            carregarComandaAtiva();
                        }
                    }
                }
            }
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
            JOptionPane.showMessageDialog( jPanelComanda,
                "Selecione apenas pedidos válidos. Tente novamente.",
                "Separação de comandas",
                JOptionPane.WARNING_MESSAGE );
        }
    }

    private boolean estaPreenchidaTelaAbertura()
    {
        if ( !jListFuncionariosAbertura.isSelectionEmpty()
            && !jFormattedTextFieldCaixa.getText().isEmpty() )
            return true;
        else
            return false;
    }
    
    @Action
    public void exportarBanco()
    {
        TelaExportarTabelas tela = new TelaExportarTabelas( this, true, bd);
        tela.pack();
        tela.setLocationRelativeTo( this.getComponent() );
        tela.setVisible( true );
    }

    @Action
    public void fecharCaixa()
    {
        try
        {
            if ( bd.obterTempoBD() == 0 )
                JOptionPane.showMessageDialog( mainPanel,
                    "Marcar opção de tempo antes de fechar o caixa.",
                    "Fechamento do caixa",
                    JOptionPane.WARNING_MESSAGE );
            else
            {
                String mensagem;
                if ( bd.existeComandaAbertaBD() )
                    mensagem = "<html>Existem comandas em aberto. "
                        + "Deseja continuar com o fechamento do caixa?<p>"
                        + "(Tais comandas serão transferidas para o próximo "
                        + "turno.)</html>";
                else
                    mensagem = "Confirma fechamento do caixa?";

                // Confirmar fechamento do caixa:
                int opcao = JOptionPane.showConfirmDialog(
                    mainPanel,
                    mensagem,
                    "Fechamento do caixa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE );

                if ( opcao == JOptionPane.YES_OPTION )
                {
                    Jornada jornada = bd.obterJornadaAbertaBD();

                    String dataFechamento = CurrentDateAndTime.getDate();
                    String horaFechamento = CurrentDateAndTime.getTime();
                    float reforcos = bd.obterReforcos( jornada );
                    float sangrias = bd.obterSangrias( jornada );
                    float despesas = bd.obterDespesas( jornada );
                    float caixaFinal = jornada.getCaixaInicial() + bd.obterVendasBD( jornada )
                        + reforcos - sangrias - despesas;

                    jornada.fechar( dataFechamento, horaFechamento, caixaFinal, 
                        reforcos, sangrias, despesas );
                    bd.fecharJornadaBD( dataFechamento + " " + horaFechamento );

                    limparTabelaComanda();
                    limparTelaAbertura();
                    buttonGroupTempo.clearSelection();

                    // Por NÃO haver comanda ativa:
                    jButtonExcluirItem.setEnabled( false );
                    jButtonSeparar.setEnabled( false );
                    jButtonFinalizar.setEnabled( false );
                    jButtonGuardar.setEnabled( false );

                    // Por NÃO haver comanda(s) aberta(s):
                    jButtonSelecionar.setEnabled( false );
                    jButtonJuntar.setEnabled( false );

                    jTabbedPane.setSelectedComponent( jPanelAbertura );
                    jTabbedPane.setEnabledAt( 1, false ); // Desabilita tela "Frente"

                    TelaResultadoJornada tela = new TelaResultadoJornada(
                        this,
                        true,
                        jornada,
                        bd );
                    tela.pack();
                    tela.setLocationRelativeTo( this.getComponent() );
                    tela.setVisible( true );
                }
            }
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
            JOptionPane.showMessageDialog( mainPanel,
                "Alerta: Ocorreu um erro no fechamento do caixa. Informe ao responsável.",
                "Fechamento do caixa",
                JOptionPane.WARNING_MESSAGE );
        }
    }

    @Action
    public void fecharComanda()
    {
        try
        {
            if ( bd.existeComandaAtivaBD() )
            {
                TelaFecharComanda tela = new TelaFecharComanda(
                    this,
                    true,
                    bd.obterNomeClienteBD(),
                    nfMoeda.parse( jTextFieldTotalComanda.getText() ).floatValue() );
                tela.pack();
                tela.setLocationRelativeTo( this.getComponent() );
                tela.setVisible( true );

                if ( tela.estaComandaFechada() )
                {
                    // Limpar informações antigas da tela:
                    int posicao = bd.obterPosicaoAtivaBD();
                    arrayBotoesComandas[posicao].setText( posicao + "." );
                    limparTabelaComanda();

                    // Por NÃO haver comanda ativa:
                    jButtonExcluirItem.setEnabled( false );
                    jButtonSeparar.setEnabled( false );
                    jButtonFinalizar.setEnabled( false );
                    jButtonGuardar.setEnabled( false );

                    if ( !bd.existeComandaAbertaBD() )
                    {
                        // Por NÃO haver comanda(s) aberta(s):
                        jButtonSelecionar.setEnabled( false );
                        jButtonJuntar.setEnabled( false );
                    }

                    // Atualizar BD
                    bd.alterarDescontoComandaBD( tela.getDesconto() );
                    bd.fecharComandaBD();
                }
            }
            else
            {

            }
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    @Action
    public boolean guardarComanda()
    {
        boolean operacaoRealizada = false;
        int posicao;
        String nome;
        try
        {
            if ( bd.existeComandaAtivaBD() )
            {
                // Se a comanda ativa ainda não tiver uma posição (isto é, posição = 0):
                if ( bd.obterPosicaoAtivaBD() == 0 )
                {
                    TelaGuardarComanda tela = new TelaGuardarComanda( this, true, bd );
                    tela.pack();
                    tela.setLocationRelativeTo( this.getComponent() );
                    tela.setVisible( true );
                    nome = tela.obterIdentificacao();

                    // Se o InputDialog não tiver sido cancelado:
                    if ( nome != null )
                    {
                        // Se foi digitado um número, anexar comanda atual com a do cliente:
                        if ( nome.matches( "[0-9]+" ) )
                        {
                            posicao = nfComanda.parse( nome ).intValue();
                            operacaoRealizada = unirComandaAtiva( posicao );
                        }
                        // Senão, atribuir nome ao cliente, definir um botão e guardar a comanda:
                        else
                        {
                            bd.alterarNomeClienteBD( nome );
                            posicao = bd.obterPosicaoDisponivelBD();
                            bd.alterarPosicaoClienteBD( posicao );
                            arrayBotoesComandas[posicao].setText( posicao + ". " + nome );
                            operacaoRealizada = true;
                        }
                    }
                    // Caso a inputDialog tenha sido cancelada:
                    else
                        operacaoRealizada = false;
                }
                // Se a comanda ativa já tiver uma posição:
                else
                    operacaoRealizada = true;
                // Se uma das operações anteriores foi realizada, limpa a tabela e desativa a comanda:
                if ( operacaoRealizada )
                {
                    // Por NÃO haver comanda ativa:
                    jButtonGuardar.setEnabled( false );
                    jButtonFinalizar.setEnabled( false );
                    jButtonExcluirItem.setEnabled( false );
                    jButtonSeparar.setEnabled( false );

                    arrayBotoesComandas[COMANDA_FANTASMA].setSelected( true );
                    limparTabelaComanda();
                    bd.desativarComandaBD();
                }
            }
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
            operacaoRealizada = false;
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
            operacaoRealizada = false;
        }
        finally
        {
            return operacaoRealizada;
        }
    }

    /**
     * Inicia BD, criando tabelas em branco.
     */
    private void iniciarBanco()
    {
        try
        {
            bd.iniciarBD();
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * Rotina para conectar-se com o banco de dados e povoar as tabelas e listas
     * do programa.
     */
    private void iniciarMeusComponentes( ResourceMap resourceMap )
    {
        try
        {
            this.getFrame().setIconImage(
                resourceMap.getImageIcon( "Application.icon" ).getImage() );

            File arquivo = new File( ARQUIVO_BD );
            if ( !arquivo.isFile() )
            {
                bd = new BancoDeDados( ARQUIVO_BD );
                iniciarBanco();
            }
            else
            {
                bd = new BancoDeDados( ARQUIVO_BD );
                atualizarVersao();
            }
                
            construirArrays();
            adicionarKeyListeners();
            carregarListaLojas();
            carregarListaFuncionarios();
            if ( bd.existeJornadaAbertaBD() == true )
            {
                JOptionPane.showMessageDialog(
                    jPanelAbertura,
                    "<html>Não houve fechamento do caixa na última sessão aberta.<p>"
                    + "As informações passadas serão recuperadas.</html>",
                    resourceMap.getString( "Application.title" ),
                    JOptionPane.INFORMATION_MESSAGE );

                // Carregar as informações da tela de abertura:
                jornadaAtual = bd.obterJornadaAbertaBD();
                jTextFieldData.setText( jornadaAtual.getDataAbertura() );
                jTextFieldHora.setText( jornadaAtual.getHoraAbertura() );
                jTextFieldLoja.setText( jornadaAtual.getLoja().getNome() );
                jTextFieldSemana.setText( jornadaAtual.getSemanaAbertura() );
                jFormattedTextFieldCaixa.setText( nfMoeda.format( jornadaAtual.getCaixaInicial() ) );
                CheckListItem cli;
                for ( String func : jornadaAtual.getFuncionarios() )
                {
                    cli = ( CheckListItem ) jListFuncionariosAbertura.getModel().
                        getElementAt( listModelFuncionariosBanco.indexOf( func ) );
                    cli.setSelected( true );
                }
                bloquearTelaAbertura();
                buttonGroupTempo.setSelected(
                    arrayBotoesTempo[bd.obterTempoBD()].getModel(), true );
                if ( bd.existeComandaAtivaBD() )
                    carregarComandaAtiva();
                carregarComandasGuardadas();

            }
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
        catch ( ClassNotFoundException cnfe )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, cnfe );
        }
        finally
        {
            jCheckBoxAdministrador.setState( false );
        }
    }

    private void limparBotoesBanco()
    {
        buttonGroupProdutosBanco.setSelected( jToggleButtonBPFantasma.getModel(), true );
        for ( JToggleButton botao : arrayBotoesProdutoBanco )
            botao.setText( "" );
    }

    private void limparBotoesFrente()
    {
        buttonGroupProdutosFrente.setSelected( jToggleButtonFPFantasma.getModel(), true );
        for ( JToggleButton botao : arrayBotoesProdutoFrente )
        {
            botao.setText( "" );
            botao.setEnabled( false );
        }
    }

    private void limparCamposBanco()
    {
        for ( JTextField tf : arrayCamposBanco )
            tf.setText( "" );
    }

    private void limparCamposFrente()
    {
        for ( JTextField tf : arrayCamposFrente )
            tf.setText( "" );
    }

    private void limparTabelaComanda( int linhaInicial, int linhaFinal )
    {
        // Modelo de registro a ser inserido após remoção dos pedidos
        // selecionados (3 colunas em branco):
        Vector<String> novoRegistro = new Vector<String>();
        novoRegistro.add( "" );
        novoRegistro.add( "" );
        novoRegistro.add( "" );
        
        for ( int linha = linhaFinal; linha >= linhaInicial; linha-- )
            tableModel.removeRow( linha );

        // Adiciona linhas vazias para preencher espaço em branco:
        while ( tableModel.getRowCount() < N_LINHAS_COMANDA )
            tableModel.addRow( novoRegistro );

        jTableComanda.setPreferredSize( jTableComanda.getMinimumSize() );
    }

    private void limparTabelaComanda( int linha )
    {
        limparTabelaComanda( linha, linha );
    }

    private void limparTabelaComanda()
    {
        limparTabelaComanda( 0, tableModel.getRowCount() - 1 );
        jTextFieldTotalComanda.setText( "" );
        jTextFieldCliente.setText( "" );
    }

    private void limparTelaAbertura()
    {
        jTextFieldData.setText( CurrentDateAndTime.getDate() );
        jTextFieldHora.setText( CurrentDateAndTime.getTime() );
        jTextFieldSemana.setText( CurrentDateAndTime.getDay() );
        jFormattedTextFieldCaixa.setText( "" );

        Object funcionarios[] = jListFuncionariosAbertura.getSelectedValues();
        for ( Object f : funcionarios )
        {
            if ( f instanceof CheckListItem )
            {
                CheckListItem fitem = ( CheckListItem ) f;
                fitem.setSelected( false );
            }
        }
        listSelModelFuncionarios.clearSelection();

        jComboBoxTurno.setEnabled( true );
        jListFuncionariosAbertura.setEnabled( true );
        jFormattedTextFieldCaixa.setEnabled( true );
        jButtonCaixa.setEnabled( true );
    }

    public JToggleButton[] obterArrayBotoes()
    {
        return arrayBotoesComandas;
    }

    @Action
    public void realizarReforco()
    {
        try
        {
            TelaReforco tela = new TelaReforco( this, true );
            tela.pack();
            tela.setLocationRelativeTo( this.getComponent() );
            tela.setVisible( true );

            if ( tela.houveAcao() )
                bd.realizarReforco( tela.getValor() );
        }
        catch ( SQLException se )
        {
            JOptionPane.showMessageDialog( jPanelFrente,
                "Erro no acesso ao banco de dados. Informe ao responsável.",
                "Reforço",
                JOptionPane.ERROR_MESSAGE );
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    @Action
    public void realizarSangria()
    {
        try
        {
            TelaSangria tela = new TelaSangria( this, true );
            tela.pack();
            tela.setLocationRelativeTo( this.getComponent() );
            tela.setVisible( true );

            if ( tela.houveAcao() )
                bd.realizarSangria( tela.getValor() );
        }
        catch ( SQLException se )
        {
            JOptionPane.showMessageDialog( jPanelFrente,
                "Erro no acesso ao banco de dados. Informe ao responsável.",
                "Sangria",
                JOptionPane.ERROR_MESSAGE );
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    private void relacionarFuncionariosJornada()
    {
        Object funcionarios[] = jListFuncionariosAbertura.getSelectedValues();
        for ( Object f : funcionarios )
        {
            if ( f instanceof CheckListItem )
            {
                CheckListItem fitem = ( CheckListItem ) f;
                String nome = fitem.toString();
                try
                {
                    bd.relacionarFuncionarioJornadaBD( nome );
                }
                catch ( SQLException se )
                {
                    Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
                }
            }
        }

    }

    /**
     * - Apresenta uma <i>JOptionPane.showConfirmDialog</i> para confirmar exclusão;
     * - Exclui o funcionário selecionado da tabela <i>Funcionarios</i> do BD;
     * - Exclui o funcionário selecionado da <i>jTableFuncionarios</i>.
     */
    @Action
    public void removerFuncionario()
    {
        String nome = ( String ) jListFuncionariosBanco.getSelectedValue();
        int indice = jListFuncionariosBanco.getSelectedIndex();
        if ( indice != -1 )
        {
            int confirma = JOptionPane.showConfirmDialog(
                jPanelComanda,
                "Confirma exclusão de " + nome + "?",
                "Exclusão de funcionário",
                JOptionPane.OK_CANCEL_OPTION );
            if ( confirma == 0 )
            { // Sim = 0, Não = 1
                try
                {
                    bd.removerNomeBD( nome, "Funcionarios" );
                    listModelFuncionariosBanco.remove( indice );
                    listModelFuncionariosAbertura.remove( indice );
                }
                catch ( SQLException se )
                {
                    Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
                }
            }
        }
    }

    /**
     * - Apresenta uma <i>JOptionPane.showConfirmDialog</i> para confirmar exclusão;
     * - Exclui a loja selecionada da tabela <i>Lojas</i> do BD;
     * - Exclui a loja selecionada da <i>jComboBoxLojas</i>.
     */
    @Action
    public void removerLoja()
    {
        String nome = ( String ) jComboBoxLojas.getSelectedItem();
        int indice = jComboBoxLojas.getSelectedIndex();
        if ( indice != -1 )
        {
            int confirma = JOptionPane.showConfirmDialog(
                jPanelComanda,
                "Confirma exclusão de " + nome + "?",
                "Exclusão de loja",
                JOptionPane.OK_CANCEL_OPTION );
            if ( confirma == 0 )
            { // Sim = 0, Não = 1
                try
                {
                    bd.removerNomeBD( nome, "Lojas" );
                    comboBoxModelLojas.removeElementAt( indice );
                }
                catch ( SQLException se )
                {
                    Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
                }
            }
        }
    }

    /**
     * Remove produto do BD.
     */
    @Action
    public void removerProduto()
    {
        try
        {
            String codigoProduto = buttonGroupProdutosBanco.getSelection().getActionCommand();
            bd.removerProdutoBD( codigoCategoria, codigoProduto );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
        selecionarCategoriaBanco();
    }

    /**
     * Remove produto da comanda
     */
    @Action
    public void removerPedido()
    {
        // Modelo de registro a ser inserido após remoção dos pedidos
        // selecionados (3 colunas em branco):
//        Vector<String> novoRegistro = new Vector<String>();
//        novoRegistro.add( "" );
//        novoRegistro.add( "" );
//        novoRegistro.add( "" );

        // Remove linhas selecionadas:
        int linhaPedido;
        float valorComandaFloat;
        float valorPedidoFloat;
        float novoValorComandaFloat;
        float qtdePedidaFloat;
        String valorComandaString;
        String valorPedidoString;
        String novoValorComandaString;
        String produtoPedido;
        String qtdePedidaString;

        while ( jTableComanda.getSelectedRows().length > 0 )
        {
            try
            {
                linhaPedido = jTableComanda.getSelectedRow();

                // Identifica pedido a ser removido:
                produtoPedido = ( String ) jTableComanda.getValueAt( linhaPedido, COL_DESCR );
                if ( ! produtoPedido.isEmpty() )
                {
                    qtdePedidaString = ( String ) jTableComanda.getValueAt( linhaPedido, COL_QTDE );
                    qtdePedidaFloat = nf3d.parse( qtdePedidaString ).floatValue();

                    // Remove pedido do BD (já atualizando o total da comanda no BD):
                    bd.removerPedidoBD(
                        produtoPedido,
                        qtdePedidaFloat );

                    // Remove pedido da tabela:
                    valorComandaString = jTextFieldTotalComanda.getText();
                    valorComandaFloat = nfMoeda.parse( valorComandaString ).floatValue();
                    valorPedidoString = ( String ) jTableComanda.getValueAt( linhaPedido, COL_VALOR );
                    valorPedidoFloat = nfMoeda.parse( valorPedidoString ).floatValue();
                    novoValorComandaFloat = valorComandaFloat - valorPedidoFloat;
                    limparTabelaComanda( linhaPedido );
                    novoValorComandaString = nfMoeda.format( novoValorComandaFloat );
                    jTextFieldTotalComanda.setText( novoValorComandaString );
                }
            }
            catch ( ParseException pe )
            {
                Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, pe );
            }
            catch ( SQLException se )
            {
                Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
            }
        }
    }

    @Action
    public void sair()
    {
        try
        {
            final ResourceMap resourceMap = getResourceMap();
            String[] opcoes =
            {
                "Sim", "Não"
            };
            if ( bd.existeJornadaAbertaBD() == true )
            {
                int escolha = JOptionPane.showOptionDialog( mainPanel, "O caixa não foi fechado. Deseja, ainda sim, sair do programa?", resourceMap.getString( "Application.title" ), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, opcoes, 1 );
                if ( escolha == 0 )
                    System.exit( 0 );
                else
                    return;
            }
            else
                System.exit( 0 );
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
            System.exit( 0 );
        }
    }

    /**
     * Seleciona categoria. Pode ser tanto da tela "Frente de loja" quanto da
     * tela "Cadastramento" (depende do método que chamou).
     *
     */
    private void selecionarCategoria(
        javax.swing.ButtonGroup bgCategoria,
        javax.swing.JTextField tfCodigo,
        javax.swing.JToggleButton tbFantasma,
        JToggleButton[] aBotoesProdutos )
    {
        codigoCategoria = bgCategoria.getSelection().getActionCommand();
        tfCodigo.setText( codigoCategoria );

        // Evitar que, após digitação do primeiro número, o cursor volte
        // indevidamente para a primeira posição):
        tfCodigo.setCaretPosition( tfCodigo.getText().length() );

        // Garantir que nenhum dos botões de seleção dos produtos esteja selecionado:
        tbFantasma.setSelected( true );

        // Mostrar produtos cadastrados:
        try
        {
            Iterator<Produto> it = bd.obterProdutosBD( codigoCategoria ).iterator();
            while ( it.hasNext() )
            {
                Produto produtoListado = it.next();
                int indiceBotao = Integer.parseInt( produtoListado.getCodigoProduto() );
                aBotoesProdutos[indiceBotao].setText( "<html><center>" + produtoListado.getNome() + "</center></html>" );
                aBotoesProdutos[indiceBotao].setEnabled( true );
            }
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * Seleciona a categoria na tela "Banco de dados"
     */
    @Action
    public void selecionarCategoriaBanco()
    {
        limparCamposBanco();
        limparBotoesBanco();
        selecionarCategoria(
            buttonGroupCategoriasBanco,
            jTextFieldCodigoBanco,
            jToggleButtonBPFantasma,
            arrayBotoesProdutoBanco );
    }

    /**
     * Seleciona a categoria na tela "Frente de caixa"
     */
    @Action
    public void selecionarCategoriaFrente()
    {
        limparCamposFrente();
        limparBotoesFrente();
        selecionarCategoria(
            buttonGroupCategoriasFrente,
            jTextFieldCodigoFrente,
            jToggleButtonFPFantasma,
            arrayBotoesProdutoFrente );
    }

    @Action
    public void selecionarComanda()
    {
        try
        {
            TelaSelecionarComanda tela = new TelaSelecionarComanda( this, true, bd );
            tela.pack();
            tela.setLocationRelativeTo( this.getComponent() );
            tela.setVisible( true );
            String comanda = tela.obterIdentificacao();

            if ( comanda != null )
            {
                if ( comanda.matches( "[0-9]+" ) )
                {
                    int posicao = nfComanda.parse( comanda ).intValue();
                    selecionarComanda( posicao );
                }
                else
                    JOptionPane.showMessageDialog( mainPanel,
                        "Número de comanda inválido.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE );
            }
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    private void selecionarComanda( int posicao )
        throws SQLException
    {
        if ( !bd.estaPosicaoOcupadaBD( posicao ) )
            JOptionPane.showMessageDialog( mainPanel,
                "Nenhum cliente está armazenado na posição " + posicao + ".",
                "Atenção",
                JOptionPane.WARNING_MESSAGE );
        else
            carregarComanda( posicao );
    }

    /**
     * Seleciona produto.
     */
    private void selecionarProduto(
        ButtonGroup bgProdutos,
        JTextField tfCodigo,
        JFormattedTextField ftfValor,
        JTextField tfProduto,
        JFormattedTextField ftfParaSelecionar )
    {
        try
        {
            // Obtem o codigo da categoria e do produto e procura pelo produto no BD:
            String codigoProduto = bgProdutos.getSelection().getActionCommand();
            tfCodigo.setText( codigoCategoria + codigoProduto );
            Iterator<Produto> it = bd.obterProdutosBD( codigoCategoria, codigoProduto ).iterator();

            // Após consulta, preenche os campos Valor e Nome:
            if ( it.hasNext() )
            {
                Produto produtoSelecionado = it.next();
                float preco = produtoSelecionado.getPreco();

                if ( ftfValor.getParent().getName().equals( "jPanelEntradaBanco" ) )
                {
                    String precoString = nfMoeda.format( preco );
                    ftfValor.setText( precoString );
                    ftfValor.selectAll();
                }
                else
                {
                    String precoString = nfMoeda.format( preco );
                    ftfValor.setText( precoString );
                }
                tfProduto.setText( produtoSelecionado.getNome() );
            }
            else
            {
                // Se não houver produto cadastrado, deixa os campos em branco:
                ftfValor.setText( "" );
                tfProduto.setText( "" );
            }
            // Se a tela for "Banco", habilita e põe o foco no campo "Valor".
            // Se a tela for "Frente de caixa", ... no campo "Qtde".
            ftfParaSelecionar.setEnabled( true );
            ftfParaSelecionar.grabFocus();
        }
        catch ( SQLException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
        }
    }

    /**
     * Seleciona produto na tela "Banco de dados"
     */
    @Action
    public void selecionarProdutoBanco()
    {
        limparCamposBanco();
        selecionarProduto(
            buttonGroupProdutosBanco,
            jTextFieldCodigoBanco,
            jFormattedTextFieldValorBanco,
            jTextFieldProdutoBanco,
            jFormattedTextFieldValorBanco );
    }

    /**
     * Seleciona produto na tela "Frente de loja"
     */
    @Action
    public void selecionarProdutoFrente()
    {
        limparCamposFrente();
        selecionarProduto(
            buttonGroupProdutosFrente,
            jTextFieldCodigoFrente,
            jFormattedTextFieldValorFrente,
            jTextFieldProdutoFrente,
            jFormattedTextFieldQtdeFrente );
        jFormattedTextFieldQtdeFrente.setText( "1" );
        jFormattedTextFieldQtdeFrente.selectAll();
    }

    @Action
    public void selecionarTempo()
    {
        try
        {
            bd.alterarTempoBD( Integer.parseInt( buttonGroupTempo.getSelection().getActionCommand() ) );
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
            JOptionPane.showMessageDialog(
                mainPanel,
                "Alerta: Ocorreu um erro na escolha do tempo. Informe ao responsável.",
                "Escolha do tempo",
                JOptionPane.WARNING_MESSAGE );
        }
    }

    @Action
    public void solicitarAbastecimento()
    {
        TelaAbastec tela = new TelaAbastec( this, true, jornadaAtual, bd );
        tela.pack();
        tela.setLocationRelativeTo( this.getComponent() );
        tela.setVisible( true );
    }
    
    /**
     * - Apresenta uma <i>JOptionPane.showInputDialog</i> para cadastro do funcionário;
     * - Mostra nome do funcionário na <i>jListFuncionariosBanco</i>;
     * - Insere funcionário na tabela <i>Funcionarios</i> do BD.
     */
    @Action
    public void showAdicionarFuncionario()
    {
        String nome = JOptionPane.showInputDialog(
            jPanelComanda,
            "Informe o nome do novo funcionário:",
            "Cadastrar funcionário",
            JOptionPane.QUESTION_MESSAGE );
        if ( nome != null && !nome.equals( "" ) )
        {
            listModelFuncionariosBanco.addElement( nome );
            try
            {
                bd.adicionarFuncionarioBD( nome );
            }
            catch ( SQLException se )
            {
                Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
            }
        }
    }

    /**
     * - Apresenta uma JOptionPane.showInputDialog para cadastro de nova loja;
     * - Mostra nome da loja na <i>jComboBoxLojas</i>;
     * - Insere loja na tabela <i>Lojas</i> do BD.
     */
    @Action
    public void showAdicionarLoja()
    {
        String nome = JOptionPane.showInputDialog(
            jPanelComanda,
            "Informe o nome da nova loja:",
            "Cadastrar loja",
            JOptionPane.QUESTION_MESSAGE );
        if ( !(nome == null) && !nome.equals( "" ) )
        {
            try
            {
                bd.adicionarLojaBD( nome );
                comboBoxModelLojas.addElement( nome );
                comboBoxModelLojas.setSelectedItem( nome );
            }
            catch ( SQLException se )
            {
                Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
            }
        }
    }

    @Action
    public void showAboutBox()
    {
        if ( aboutBox == null )
        {
            JFrame mainFrame = SigeveApp.getApplication().getMainFrame();
            aboutBox = new SigeveSobre( mainFrame );
            aboutBox.setLocationRelativeTo( mainFrame );
        }
        SigeveApp.getApplication().show( aboutBox );
    }

    /**
     * Concatena os pedidos da comanda atual na comanda informada através da
     * caixa de diálogo, desativando, fechando e removendo a comanda atual do BD.
     * Ativa a comanda informada.
     *
     */
    @Action
    public void unirComandaAtiva()
    {
        try
        {
            if ( bd.existeComandaAtivaBD() )
            {
                JOptionPane.showMessageDialog( mainPanel,
                    "Antes de unir comandas, a comanda atual deverá ser guardada.",
                    "União de comandas",
                    JOptionPane.WARNING_MESSAGE );
            }
            else
            {
                TelaJuntarComandas tela = new TelaJuntarComandas( this, true, bd );
                tela.pack();
                tela.setLocationRelativeTo( this.getComponent() );
                tela.setVisible( true );

                if ( tela.foiUniaoRealizada() )
                {
                    int destino = tela.getDestino();
                    List<Integer> posicoes = tela.getPosicoes();

                    // Para garantir que a comanda destino não esteja entre as selecionadas
                    posicoes.remove( ( Object ) destino );

                    if ( destino != 0 && !posicoes.isEmpty() )
                    {
                        // Ativa comanda escolhida para ser o destino dos pedidos:
                        bd.ativarComandaBD( destino );

                        // Transfere para a comanda ativada os pedidos das comandas selecionadas:
                        bd.unirComandasBD( posicoes );

                        // Apresenta a comanda atualizada:
                        carregarComanda( destino );

                        // Apagar referências às comandas removidas:
                        for ( Integer p : posicoes )
                            arrayBotoesComandas[p].setText( p + "." );

                        // Atualizar total:
                        bd.atualizarTotalComandaBD();
                        jTextFieldTotalComanda.setText( nfMoeda.format(
                            bd.obterTotalComandaBD() ) );
                    }
                    else
                        JOptionPane.showMessageDialog( mainPanel,
                            "<html>Não foram selecionadas as comandas a serem "
                            + "unidas ou o seu destino.<p>"
                            + "Operação cancelada.</html>",
                            "União de comandas",
                            JOptionPane.WARNING_MESSAGE );
                }
            }
            //
            //        try
            //        {
            //            boolean uniaoRealizada = false;
            //            int posicaoReceptora;
            //            int posicaoDoadora;
            //            String comanda;
            //
            //            if ( bd.existeComandaAtivaBD() )
            //            {
            //                comanda = JOptionPane.showInputDialog( mainPanel,
            //                    "Digite o número da comanda que receberá os pedidos da comanda atual:",
            //                    "União de comandas",
            //                    JOptionPane.QUESTION_MESSAGE );
            //                if ( comanda != null )
            //                {
            //                    if ( comanda.matches( "[0-9]+" ) )
            //                    {
            //                        posicaoReceptora = nfComanda.parse( comanda ).intValue();
            //                        posicaoDoadora = bd.obterPosicaoAtivaBD();
            //
            //                        if ( bd.estaPosicaoOcupadaBD( posicaoReceptora )
            //                            && posicaoDoadora != posicaoReceptora )
            //                        {
            //                            uniaoRealizada = unirComandaAtiva( posicaoReceptora );
            //                            if ( uniaoRealizada )
            //                            {
            //                                arrayBotoesComandas[posicaoDoadora].setText( posicaoDoadora + "." );
            //                                bd.ativarComandaBD( posicaoReceptora );
            //                                bd.atualizarTotalComandaBD();
            //                                selecionarComanda( posicaoReceptora );
            //                            }
            //                        }
            //                        else
            //                            JOptionPane.showMessageDialog( mainPanel,
            //                                "Número de comanda inválido. A união não foi realizada.",
            //                                "União de comandas",
            //                                JOptionPane.WARNING_MESSAGE );
            //                    }
            //                    else
            //                        JOptionPane.showMessageDialog( mainPanel,
            //                            "Não foi digitado um número. A união não foi realizada.",
            //                            "União de comandas",
            //                            JOptionPane.WARNING_MESSAGE );
            //                }
            //            }
            //        }
            //        catch ( SQLException ex )
            //        {
            //            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
            //        }
            //        catch ( ParseException ex )
            //        {
            //        }
            //        }
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    /**
     * Concatena os pedidos da comanda atual na comanda informada através da
     * caixa de diálogo, desativando, fechando e removendo a comanda atual do BD.
     *
     * @param posicao
     * @return Booleano indicando se as comandas foram unidas com sucesso.
     */
    private boolean unirComandaAtiva( int posicao )
    {
        try
        {
            // Checar se a posicao escolhida para anexar a comanda não está vazia:
            if ( !bd.estaPosicaoOcupadaBD( posicao ) )
            {
                JOptionPane.showMessageDialog( mainPanel,
                    "Nenhum cliente está armazenado na posição " + posicao + ".",
                    "União de comandas",
                    JOptionPane.WARNING_MESSAGE );
                return false;
            }
            else
            {
                bd.unirComandaAtivaBD( posicao );
                return true;
            }
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
            return false;
        }
    }

    @Action
    public void verComandasFechadas()
    {
        try
        {
            Jornada jornada = bd.obterJornadaAbertaBD();
            float vendas = bd.obterVendasBD( jornada );
            jornada.setReforcos( bd.obterReforcos( jornada ) );
            jornada.setSangrias( bd.obterSangrias( jornada ) );
            jornada.setDespesas( bd.obterDespesas( jornada ) );
            float caixaFinal = jornada.getCaixaInicial() + vendas +
                jornada.getReforcos() - jornada.getSangrias() -
                jornada.getDespesas();
            jornada.setCaixaFinal( caixaFinal );

            TelaComandasFechadas tela = new TelaComandasFechadas( this, true, jornada, bd );
            tela.pack();
            tela.setLocationRelativeTo( this.getComponent() );
            tela.setVisible( true );
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelAbertura = new javax.swing.JPanel();
        jPanelJornada = new javax.swing.JPanel();
        jLabelLoja = new javax.swing.JLabel();
        jLabelData = new javax.swing.JLabel();
        jLabelHora = new javax.swing.JLabel();
        jLabelSemana = new javax.swing.JLabel();
        jLabelTurno = new javax.swing.JLabel();
        jLabelFuncionarios = new javax.swing.JLabel();
        jLabelDinheiro = new javax.swing.JLabel();
        jTextFieldLoja = new javax.swing.JTextField();
        jTextFieldData = new javax.swing.JTextField();
        jTextFieldHora = new javax.swing.JTextField();
        jTextFieldSemana = new javax.swing.JTextField();
        jComboBoxTurno = new javax.swing.JComboBox();
        jScrollPaneFuncionariosAbertura = new javax.swing.JScrollPane();
        jListFuncionariosAbertura = new javax.swing.JList();
        jFormattedTextFieldCaixa = new javax.swing.JFormattedTextField();
        jButtonCaixa = new javax.swing.JButton();
        jPanelFrente = new javax.swing.JPanel();
        jPanelEntradaFrente = new javax.swing.JPanel();
        jLabelCodigo = new javax.swing.JLabel();
        jLabelQtde = new javax.swing.JLabel();
        jLabelValor = new javax.swing.JLabel();
        jLabelTotal = new javax.swing.JLabel();
        jLabelProduto = new javax.swing.JLabel();
        jTextFieldCodigoFrente = new javax.swing.JTextField();
        jFormattedTextFieldQtdeFrente = new javax.swing.JFormattedTextField();
        jFormattedTextFieldValorFrente = new javax.swing.JFormattedTextField();
        jFormattedTextFieldTotalFrente = new javax.swing.JFormattedTextField();
        jTextFieldProdutoFrente = new javax.swing.JTextField();
        jButtonConfirmar = new javax.swing.JButton();
        jPanelCategoriasFrente = new javax.swing.JPanel();
        jToggleButtonFC1 = new javax.swing.JToggleButton();
        jToggleButtonFC2 = new javax.swing.JToggleButton();
        jToggleButtonFC3 = new javax.swing.JToggleButton();
        jToggleButtonFC4 = new javax.swing.JToggleButton();
        jToggleButtonFC5 = new javax.swing.JToggleButton();
        jToggleButtonFC6 = new javax.swing.JToggleButton();
        jToggleButtonFC7 = new javax.swing.JToggleButton();
        jToggleButtonFC8 = new javax.swing.JToggleButton();
        jToggleButtonFC9 = new javax.swing.JToggleButton();
        jToggleButtonFC0 = new javax.swing.JToggleButton();
        jToggleButtonFCFantasma = new javax.swing.JToggleButton();
        jPanelProdutosFrente = new javax.swing.JPanel();
        jToggleButtonFP1 = new javax.swing.JToggleButton();
        jToggleButtonFP2 = new javax.swing.JToggleButton();
        jToggleButtonFP3 = new javax.swing.JToggleButton();
        jToggleButtonFP4 = new javax.swing.JToggleButton();
        jToggleButtonFP5 = new javax.swing.JToggleButton();
        jToggleButtonFP6 = new javax.swing.JToggleButton();
        jToggleButtonFP7 = new javax.swing.JToggleButton();
        jToggleButtonFP8 = new javax.swing.JToggleButton();
        jToggleButtonFP9 = new javax.swing.JToggleButton();
        jToggleButtonFP0 = new javax.swing.JToggleButton();
        jToggleButtonFPFantasma = new javax.swing.JToggleButton();
        jPanelComanda = new javax.swing.JPanel();
        jLabelCliente = new javax.swing.JLabel();
        jTextFieldCliente = new javax.swing.JTextField();
        jScrollPaneComanda = new javax.swing.JScrollPane();
        jTableComanda = new javax.swing.JTable() {
            Color corLinhaImpar = new Color(244, 71, 107);
            Color corLinhaPar = new Color(216, 28, 63);
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {

                // Código adicional para colorir linhas
                // Fonte: http://www.java-forums.org/awt-swing/17436-color-cell-jtable.html
                JLabel label = (JLabel) super.prepareRenderer(renderer, row, column);
                if (row % 2 == 0) {
                    label.setBackground(corLinhaImpar);
                } else {
                    label.setBackground(corLinhaPar);
                }
                // Código que já existia
                Object value = getValueAt(row, column);
                boolean isSelected = false;
                boolean hasFocus = false;
                if (!isPaintingForPrint()) {
                    isSelected = isCellSelected(row, column);
                    boolean rowIsLead =
                    (selectionModel.getLeadSelectionIndex() == row);
                    boolean colIsLead =
                    (columnModel.getSelectionModel().getLeadSelectionIndex() == column);
                    hasFocus = (rowIsLead && colIsLead) && isFocusOwner();
                }
                return renderer.getTableCellRendererComponent(this, value,
                    isSelected, hasFocus,
                    row, column);
            }
        }
        ;
        jLabelTotalComanda = new javax.swing.JLabel();
        jTextFieldTotalComanda = new javax.swing.JTextField();
        jButtonExcluirItem = new javax.swing.JButton();
        jButtonGuardar = new javax.swing.JButton();
        jButtonFinalizar = new javax.swing.JButton();
        jPanelClientes = new javax.swing.JPanel();
        jToggleButtonComanda01 = new javax.swing.JToggleButton();
        jToggleButtonComanda02 = new javax.swing.JToggleButton();
        jToggleButtonComanda03 = new javax.swing.JToggleButton();
        jToggleButtonComanda04 = new javax.swing.JToggleButton();
        jToggleButtonComanda05 = new javax.swing.JToggleButton();
        jToggleButtonComanda06 = new javax.swing.JToggleButton();
        jToggleButtonComanda07 = new javax.swing.JToggleButton();
        jToggleButtonComanda08 = new javax.swing.JToggleButton();
        jToggleButtonComanda09 = new javax.swing.JToggleButton();
        jToggleButtonComanda10 = new javax.swing.JToggleButton();
        jToggleButtonComanda11 = new javax.swing.JToggleButton();
        jToggleButtonComanda12 = new javax.swing.JToggleButton();
        jToggleButtonComanda13 = new javax.swing.JToggleButton();
        jToggleButtonComanda14 = new javax.swing.JToggleButton();
        jToggleButtonComanda15 = new javax.swing.JToggleButton();
        jToggleButtonComandaFantasma = new javax.swing.JToggleButton();
        jButtonSelecionar = new javax.swing.JButton();
        jButtonJuntar = new javax.swing.JButton();
        jButtonSeparar = new javax.swing.JButton();
        jPanelTempo = new javax.swing.JPanel();
        jToggleButtonEnsolarado = new javax.swing.JToggleButton();
        jToggleButtonNublado = new javax.swing.JToggleButton();
        jToggleButtonInstavel = new javax.swing.JToggleButton();
        jToggleButtonChuvoso = new javax.swing.JToggleButton();
        jToggleButtonTempoFantasma = new javax.swing.JToggleButton();
        jButtonAbastec = new javax.swing.JButton();
        jButtonReforco = new javax.swing.JButton();
        jButtonSangria = new javax.swing.JButton();
        jButtonDespesas = new javax.swing.JButton();
        jButtonComandas = new javax.swing.JButton();
        jButtonFechamento = new javax.swing.JButton();
        jLabelLogo = new javax.swing.JLabel();
        jPanelCadastramento = new javax.swing.JPanel();
        jPanelEntradaBanco = new javax.swing.JPanel();
        jLabelCodigoBanco = new javax.swing.JLabel();
        jLabelValorBanco = new javax.swing.JLabel();
        jLabelProdutoBanco = new javax.swing.JLabel();
        jFormattedTextFieldValorBanco = new javax.swing.JFormattedTextField();
        jTextFieldProdutoBanco = new javax.swing.JTextField();
        jButtonAdicionarEntradaBanco = new javax.swing.JButton();
        jButtonRemoverEntradaBanco = new javax.swing.JButton();
        jTextFieldCodigoBanco = new javax.swing.JTextField();
        jPanelCategoriasBanco = new javax.swing.JPanel();
        jToggleButtonBC1 = new javax.swing.JToggleButton();
        jToggleButtonBC2 = new javax.swing.JToggleButton();
        jToggleButtonBC3 = new javax.swing.JToggleButton();
        jToggleButtonBC4 = new javax.swing.JToggleButton();
        jToggleButtonBC5 = new javax.swing.JToggleButton();
        jToggleButtonBC6 = new javax.swing.JToggleButton();
        jToggleButtonBC7 = new javax.swing.JToggleButton();
        jToggleButtonBC8 = new javax.swing.JToggleButton();
        jToggleButtonBC9 = new javax.swing.JToggleButton();
        jToggleButtonBC0 = new javax.swing.JToggleButton();
        jToggleButtonBCFantasma = new javax.swing.JToggleButton();
        jPaneProdutosBanco = new javax.swing.JPanel();
        jToggleButtonBP1 = new javax.swing.JToggleButton();
        jToggleButtonBP2 = new javax.swing.JToggleButton();
        jToggleButtonBP3 = new javax.swing.JToggleButton();
        jToggleButtonBP4 = new javax.swing.JToggleButton();
        jToggleButtonBP5 = new javax.swing.JToggleButton();
        jToggleButtonBP6 = new javax.swing.JToggleButton();
        jToggleButtonBP7 = new javax.swing.JToggleButton();
        jToggleButtonBP8 = new javax.swing.JToggleButton();
        jToggleButtonBP9 = new javax.swing.JToggleButton();
        jToggleButtonBP0 = new javax.swing.JToggleButton();
        jToggleButtonBPFantasma = new javax.swing.JToggleButton();
        jPanelDados = new javax.swing.JPanel();
        jLabelLojaBanco = new javax.swing.JLabel();
        jComboBoxLojas = new javax.swing.JComboBox();
        jButtonAdicionarLoja = new javax.swing.JButton();
        jButtonRemoverLoja = new javax.swing.JButton();
        jLabelFuncionariosBanco = new javax.swing.JLabel();
        jScrollPaneFuncionariosBanco = new javax.swing.JScrollPane();
        jListFuncionariosBanco = new javax.swing.JList();
        jButtonAdicionarFuncionarios = new javax.swing.JButton();
        jButtonRemoverFuncionarios = new javax.swing.JButton();
        jPanelSenha = new javax.swing.JPanel();
        jLabelSenhaAtual = new javax.swing.JLabel();
        jLabelSenhaNova = new javax.swing.JLabel();
        jLabelSenhaRepetir = new javax.swing.JLabel();
        jPasswordFieldSenhaAtual = new javax.swing.JPasswordField();
        jPasswordFieldSenhaNova = new javax.swing.JPasswordField();
        jPasswordFieldSenhaRepetir = new javax.swing.JPasswordField();
        jButtonAlterar = new javax.swing.JButton();
        jPanelRelatorios = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jCheckBoxAdministrador = new javax.swing.JCheckBoxMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        buttonGroupTempo = new javax.swing.ButtonGroup();
        buttonGroupClientes = new javax.swing.ButtonGroup();
        buttonGroupCategoriasFrente = new javax.swing.ButtonGroup();
        buttonGroupProdutosFrente = new javax.swing.ButtonGroup();
        buttonGroupCategoriasBanco = new javax.swing.ButtonGroup();
        buttonGroupProdutosBanco = new javax.swing.ButtonGroup();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getResourceMap(SigeveView.class);
        mainPanel.setBackground(resourceMap.getColor("CorNivel1")); // NOI18N
        mainPanel.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        mainPanel.setMaximumSize(new java.awt.Dimension(920, 580));
        mainPanel.setMinimumSize(new java.awt.Dimension(920, 580));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(920, 580));

        jTabbedPane.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTabbedPane.setMaximumSize(new java.awt.Dimension(1140, 580));
        jTabbedPane.setMinimumSize(new java.awt.Dimension(1140, 580));
        jTabbedPane.setName("jTabbedPane"); // NOI18N
        jTabbedPane.setPreferredSize(new java.awt.Dimension(910, 560));
        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        jPanelAbertura.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanelAbertura.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelAbertura.setName("jPanelAbertura"); // NOI18N

        jPanelJornada.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelJornada.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanelJornada.setName("jPanelJornada"); // NOI18N

        jLabelLoja.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelLoja.setText(resourceMap.getString("jLabelLoja.text")); // NOI18N
        jLabelLoja.setFocusable(false);
        jLabelLoja.setName("jLabelLoja"); // NOI18N

        jLabelData.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelData.setText(resourceMap.getString("jLabelData.text")); // NOI18N
        jLabelData.setFocusable(false);
        jLabelData.setName("jLabelData"); // NOI18N

        jLabelHora.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelHora.setText(resourceMap.getString("jLabelHora.text")); // NOI18N
        jLabelHora.setFocusable(false);
        jLabelHora.setName("jLabelHora"); // NOI18N

        jLabelSemana.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelSemana.setText(resourceMap.getString("jLabelSemana.text")); // NOI18N
        jLabelSemana.setFocusable(false);
        jLabelSemana.setName("jLabelSemana"); // NOI18N

        jLabelTurno.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelTurno.setText(resourceMap.getString("jLabelTurno.text")); // NOI18N
        jLabelTurno.setFocusable(false);
        jLabelTurno.setName("jLabelTurno"); // NOI18N

        jLabelFuncionarios.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelFuncionarios.setText(resourceMap.getString("jLabelFuncionarios.text")); // NOI18N
        jLabelFuncionarios.setFocusable(false);
        jLabelFuncionarios.setName("jLabelFuncionarios"); // NOI18N

        jLabelDinheiro.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelDinheiro.setText(resourceMap.getString("jLabelDinheiro.text")); // NOI18N
        jLabelDinheiro.setFocusable(false);
        jLabelDinheiro.setName("jLabelDinheiro"); // NOI18N

        jTextFieldLoja.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldLoja.setEditable(false);
        jTextFieldLoja.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTextFieldLoja.setText(resourceMap.getString("jTextFieldLoja.text")); // NOI18N
        jTextFieldLoja.setEnabled(false);
        jTextFieldLoja.setFocusable(false);
        jTextFieldLoja.setName("jTextFieldLoja"); // NOI18N

        jTextFieldData.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldData.setEditable(false);
        jTextFieldData.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTextFieldData.setText(sigeve.CurrentDateAndTime.getDate());
        jTextFieldData.setEnabled(false);
        jTextFieldData.setFocusable(false);
        jTextFieldData.setName("jTextFieldData"); // NOI18N

        jTextFieldHora.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldHora.setEditable(false);
        jTextFieldHora.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTextFieldHora.setText(sigeve.CurrentDateAndTime.getTime());
        jTextFieldHora.setEnabled(false);
        jTextFieldHora.setFocusable(false);
        jTextFieldHora.setName("jTextFieldHora"); // NOI18N

        jTextFieldSemana.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldSemana.setEditable(false);
        jTextFieldSemana.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTextFieldSemana.setText(sigeve.CurrentDateAndTime.getDay());
        jTextFieldSemana.setEnabled(false);
        jTextFieldSemana.setFocusable(false);
        jTextFieldSemana.setName("jTextFieldSemana"); // NOI18N

        jComboBoxTurno.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jComboBoxTurno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Diurno", "Noturno", "Único" }));
        jComboBoxTurno.setName("jComboBoxTurno"); // NOI18N

        jScrollPaneFuncionariosAbertura.setName("jScrollPaneFuncionariosAbertura"); // NOI18N

        jListFuncionariosAbertura.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jListFuncionariosAbertura.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jListFuncionariosAbertura.setModel(listModelFuncionariosAbertura);
        jListFuncionariosAbertura.setToolTipText(resourceMap.getString("jListFuncionariosAbertura.toolTipText")); // NOI18N
        jListFuncionariosAbertura.setCellRenderer(new CheckListRenderer());
        jListFuncionariosAbertura.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        jListFuncionariosAbertura.setName("jListFuncionariosAbertura"); // NOI18N
        jListFuncionariosAbertura.setSelectionModel(listSelModelFuncionarios);
        jListFuncionariosAbertura.setVisibleRowCount(5);
        jScrollPaneFuncionariosAbertura.setViewportView(jListFuncionariosAbertura);
        /*
        * JList contendo elementos JCheckBox
        * ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯
        * "Add a mouse listener to handle changing selection"
        *
        * Fonte: http://helpdesk.objects.com.au/java/how-do-add-a-checkbox-to-items-in-a-jlist
        */
        jListFuncionariosAbertura.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();

                // Get index of item clicked
                int index = list.locationToIndex(event.getPoint());
                CheckListItem item = (CheckListItem) list.getModel().getElementAt(index);

                // Toggle selected state
                item.setSelected(! item.isSelected());

                // Repaint cell
                list.repaint(list.getCellBounds(index, index));
            }
        });

        jFormattedTextFieldCaixa.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jFormattedTextFieldCaixa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextFieldCaixa.setText(resourceMap.getString("jFormattedTextFieldCaixa.text")); // NOI18N
        jFormattedTextFieldCaixa.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        jFormattedTextFieldCaixa.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jFormattedTextFieldCaixa.setName("jFormattedTextFieldCaixa"); // NOI18N
        jFormattedTextFieldCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldCaixaActionPerformed(evt);
            }
        });
        jFormattedTextFieldCaixa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldCaixaFocusLost(evt);
            }
        });

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getActionMap(SigeveView.class, this);
        jButtonCaixa.setAction(actionMap.get("abrirCaixa")); // NOI18N
        jButtonCaixa.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonCaixa.setText(resourceMap.getString("jButtonCaixa.text")); // NOI18N
        jButtonCaixa.setName("jButtonCaixa"); // NOI18N

        javax.swing.GroupLayout jPanelJornadaLayout = new javax.swing.GroupLayout(jPanelJornada);
        jPanelJornada.setLayout(jPanelJornadaLayout);
        jPanelJornadaLayout.setHorizontalGroup(
            jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJornadaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelJornadaLayout.createSequentialGroup()
                            .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelJornadaLayout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(jLabelDinheiro))
                                .addComponent(jLabelData)
                                .addComponent(jLabelLoja)
                                .addComponent(jLabelTurno)
                                .addComponent(jLabelFuncionarios))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jComboBoxTurno, javax.swing.GroupLayout.Alignment.LEADING, 0, 300, Short.MAX_VALUE)
                                .addComponent(jFormattedTextFieldCaixa, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPaneFuncionariosAbertura, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldData, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                                .addComponent(jTextFieldLoja)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelJornadaLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonCaixa)))
                    .addGroup(jPanelJornadaLayout.createSequentialGroup()
                        .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelSemana)
                            .addComponent(jLabelHora))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldSemana, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                            .addComponent(jTextFieldHora, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanelJornadaLayout.setVerticalGroup(
            jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJornadaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLoja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLoja))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSemana))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelHora)
                    .addComponent(jTextFieldHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTurno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFuncionarios)
                    .addComponent(jScrollPaneFuncionariosAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelJornadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDinheiro)
                    .addComponent(jFormattedTextFieldCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCaixa))
        );

        javax.swing.GroupLayout jPanelAberturaLayout = new javax.swing.GroupLayout(jPanelAbertura);
        jPanelAbertura.setLayout(jPanelAberturaLayout);
        jPanelAberturaLayout.setHorizontalGroup(
            jPanelAberturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAberturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(438, Short.MAX_VALUE))
        );
        jPanelAberturaLayout.setVerticalGroup(
            jPanelAberturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAberturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelJornada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
        );

        jTabbedPane.addTab(resourceMap.getString("jPanelAbertura.TabConstraints.tabTitle"), jPanelAbertura); // NOI18N

        jPanelFrente.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanelFrente.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelFrente.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelFrente.setName("jPanelFrente"); // NOI18N
        jPanelFrente.setPreferredSize(new java.awt.Dimension(910, 560));
        jPanelFrente.setRequestFocusEnabled(false);
        jPanelFrente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanelFrenteFocusGained(evt);
            }
        });

        jPanelEntradaFrente.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelEntradaFrente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelEntradaFrente.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelEntradaFrente.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelEntradaFrente.setName("jPanelEntradaFrente"); // NOI18N
        jPanelEntradaFrente.setPreferredSize(new java.awt.Dimension(810, 90));

        jLabelCodigo.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jLabelCodigo.setText(resourceMap.getString("jLabelCodigo.text")); // NOI18N
        jLabelCodigo.setFocusable(false);
        jLabelCodigo.setName("jLabelCodigo"); // NOI18N

        jLabelQtde.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jLabelQtde.setText(resourceMap.getString("jLabelQtde.text")); // NOI18N
        jLabelQtde.setFocusable(false);
        jLabelQtde.setName("jLabelQtde"); // NOI18N

        jLabelValor.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jLabelValor.setText(resourceMap.getString("jLabelValor.text")); // NOI18N
        jLabelValor.setFocusable(false);
        jLabelValor.setName("jLabelValor"); // NOI18N

        jLabelTotal.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jLabelTotal.setText(resourceMap.getString("jLabelTotal.text")); // NOI18N
        jLabelTotal.setFocusable(false);
        jLabelTotal.setName("jLabelTotal"); // NOI18N

        jLabelProduto.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jLabelProduto.setText(resourceMap.getString("jLabelProduto.text")); // NOI18N
        jLabelProduto.setFocusable(false);
        jLabelProduto.setName("jLabelProduto"); // NOI18N

        jTextFieldCodigoFrente.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldCodigoFrente.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jTextFieldCodigoFrente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldCodigoFrente.setText(resourceMap.getString("jTextFieldCodigoFrente.text")); // NOI18N
        jTextFieldCodigoFrente.setName("jTextFieldCodigoFrente"); // NOI18N
        jTextFieldCodigoFrente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldCodigoFrenteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldCodigoFrenteFocusLost(evt);
            }
        });
        jTextFieldCodigoFrente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCodigoFrenteKeyReleased(evt);
            }
        });

        jFormattedTextFieldQtdeFrente.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jFormattedTextFieldQtdeFrente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.000"))));
        jFormattedTextFieldQtdeFrente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldQtdeFrente.setText(resourceMap.getString("jFormattedTextFieldQtdeFrente.text")); // NOI18N
        jFormattedTextFieldQtdeFrente.setEnabled(false);
        jFormattedTextFieldQtdeFrente.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        jFormattedTextFieldQtdeFrente.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jFormattedTextFieldQtdeFrente.setName("jFormattedTextFieldQtdeFrente"); // NOI18N
        jFormattedTextFieldQtdeFrente.setPreferredSize(new java.awt.Dimension(80, 23));
        jFormattedTextFieldQtdeFrente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldQtdeFrenteActionPerformed(evt);
            }
        });
        jFormattedTextFieldQtdeFrente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldQtdeFrenteFocusGained(evt);
            }
        });
        jFormattedTextFieldQtdeFrente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextFieldQtdeFrenteKeyReleased(evt);
            }
        });

        jFormattedTextFieldValorFrente.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jFormattedTextFieldValorFrente.setEditable(false);
        jFormattedTextFieldValorFrente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        jFormattedTextFieldValorFrente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldValorFrente.setText(resourceMap.getString("jFormattedTextField3.text")); // NOI18N
        jFormattedTextFieldValorFrente.setEnabled(false);
        jFormattedTextFieldValorFrente.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jFormattedTextFieldValorFrente.setName("jFormattedTextFieldValorFrente"); // NOI18N
        jFormattedTextFieldValorFrente.setPreferredSize(new java.awt.Dimension(80, 23));

        jFormattedTextFieldTotalFrente.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jFormattedTextFieldTotalFrente.setEditable(false);
        jFormattedTextFieldTotalFrente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        jFormattedTextFieldTotalFrente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldTotalFrente.setText(resourceMap.getString("jFormattedTextFieldTotalFrente.text")); // NOI18N
        jFormattedTextFieldTotalFrente.setEnabled(false);
        jFormattedTextFieldTotalFrente.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jFormattedTextFieldTotalFrente.setName("jFormattedTextFieldTotalFrente"); // NOI18N
        jFormattedTextFieldTotalFrente.setPreferredSize(new java.awt.Dimension(80, 23));

        jTextFieldProdutoFrente.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldProdutoFrente.setEditable(false);
        jTextFieldProdutoFrente.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jTextFieldProdutoFrente.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldProdutoFrente.setText(resourceMap.getString("jTextFieldProdutoFrente.text")); // NOI18N
        jTextFieldProdutoFrente.setEnabled(false);
        jTextFieldProdutoFrente.setMaximumSize(new java.awt.Dimension(454, 23));
        jTextFieldProdutoFrente.setName("jTextFieldProdutoFrente"); // NOI18N

        jButtonConfirmar.setAction(actionMap.get("adicionarPedido")); // NOI18N
        jButtonConfirmar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonConfirmar.setText(resourceMap.getString("jButtonConfirmar.text")); // NOI18N
        jButtonConfirmar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonConfirmar.setName("jButtonConfirmar"); // NOI18N

        javax.swing.GroupLayout jPanelEntradaFrenteLayout = new javax.swing.GroupLayout(jPanelEntradaFrente);
        jPanelEntradaFrente.setLayout(jPanelEntradaFrenteLayout);
        jPanelEntradaFrenteLayout.setHorizontalGroup(
            jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEntradaFrenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCodigo)
                    .addComponent(jTextFieldCodigoFrente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelQtde)
                    .addComponent(jFormattedTextFieldQtdeFrente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelValor)
                    .addComponent(jFormattedTextFieldValorFrente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextFieldTotalFrente, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelProduto)
                    .addComponent(jTextFieldProdutoFrente, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonConfirmar)
                .addContainerGap())
        );
        jPanelEntradaFrenteLayout.setVerticalGroup(
            jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEntradaFrenteLayout.createSequentialGroup()
                .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelCodigo)
                        .addComponent(jLabelQtde))
                    .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButtonConfirmar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelEntradaFrenteLayout.createSequentialGroup()
                            .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelValor)
                                .addComponent(jLabelTotal)
                                .addComponent(jLabelProduto))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanelEntradaFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jFormattedTextFieldValorFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jFormattedTextFieldTotalFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldProdutoFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jFormattedTextFieldQtdeFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldCodigoFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelCategoriasFrente.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelCategoriasFrente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelCategoriasFrente.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelCategoriasFrente.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelCategoriasFrente.setName("jPanelCategoriasFrente"); // NOI18N

        jToggleButtonFC1.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC1);
        jToggleButtonFC1.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC1.setIcon(resourceMap.getIcon("jToggleButtonFC1.icon")); // NOI18N
        jToggleButtonFC1.setText(resourceMap.getString("jToggleButtonFC1.text")); // NOI18N
        jToggleButtonFC1.setActionCommand(resourceMap.getString("jToggleButtonFC1.actionCommand")); // NOI18N
        jToggleButtonFC1.setName("jToggleButtonFC1"); // NOI18N
        jToggleButtonFC1.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC2.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC2);
        jToggleButtonFC2.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC2.setIcon(resourceMap.getIcon("jToggleButtonFC2.icon")); // NOI18N
        jToggleButtonFC2.setText(resourceMap.getString("jToggleButtonFC2.text")); // NOI18N
        jToggleButtonFC2.setActionCommand(resourceMap.getString("jToggleButtonFC2.actionCommand")); // NOI18N
        jToggleButtonFC2.setName("jToggleButtonFC2"); // NOI18N
        jToggleButtonFC2.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC3.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC3);
        jToggleButtonFC3.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC3.setIcon(resourceMap.getIcon("jToggleButtonFC3.icon")); // NOI18N
        jToggleButtonFC3.setText(resourceMap.getString("jToggleButtonFC3.text")); // NOI18N
        jToggleButtonFC3.setActionCommand(resourceMap.getString("jToggleButtonFC3.actionCommand")); // NOI18N
        jToggleButtonFC3.setName("jToggleButtonFC3"); // NOI18N
        jToggleButtonFC3.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC4.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC4);
        jToggleButtonFC4.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC4.setIcon(resourceMap.getIcon("jToggleButtonFC4.icon")); // NOI18N
        jToggleButtonFC4.setText(resourceMap.getString("jToggleButtonFC4.text")); // NOI18N
        jToggleButtonFC4.setActionCommand(resourceMap.getString("jToggleButtonFC4.actionCommand")); // NOI18N
        jToggleButtonFC4.setName("jToggleButtonFC4"); // NOI18N
        jToggleButtonFC4.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC5.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC5);
        jToggleButtonFC5.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC5.setIcon(resourceMap.getIcon("jToggleButtonFC5.icon")); // NOI18N
        jToggleButtonFC5.setText(resourceMap.getString("jToggleButtonFC5.text")); // NOI18N
        jToggleButtonFC5.setActionCommand(resourceMap.getString("jToggleButtonFC5.actionCommand")); // NOI18N
        jToggleButtonFC5.setName("jToggleButtonFC5"); // NOI18N
        jToggleButtonFC5.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC6.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC6);
        jToggleButtonFC6.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC6.setIcon(resourceMap.getIcon("jToggleButtonFC6.icon")); // NOI18N
        jToggleButtonFC6.setText(resourceMap.getString("jToggleButtonFC6.text")); // NOI18N
        jToggleButtonFC6.setActionCommand(resourceMap.getString("jToggleButtonFC6.actionCommand")); // NOI18N
        jToggleButtonFC6.setName("jToggleButtonFC6"); // NOI18N
        jToggleButtonFC6.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC7.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC7);
        jToggleButtonFC7.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC7.setIcon(resourceMap.getIcon("jToggleButtonFC7.icon")); // NOI18N
        jToggleButtonFC7.setText(resourceMap.getString("jToggleButtonFC7.text")); // NOI18N
        jToggleButtonFC7.setActionCommand(resourceMap.getString("jToggleButtonFC7.actionCommand")); // NOI18N
        jToggleButtonFC7.setName("jToggleButtonFC7"); // NOI18N
        jToggleButtonFC7.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC8.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC8);
        jToggleButtonFC8.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC8.setIcon(resourceMap.getIcon("jToggleButtonFC8.icon")); // NOI18N
        jToggleButtonFC8.setText(resourceMap.getString("jToggleButtonFC8.text")); // NOI18N
        jToggleButtonFC8.setActionCommand(resourceMap.getString("jToggleButtonFC8.actionCommand")); // NOI18N
        jToggleButtonFC8.setName("jToggleButtonFC8"); // NOI18N
        jToggleButtonFC8.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC9.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC9);
        jToggleButtonFC9.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC9.setIcon(resourceMap.getIcon("jToggleButtonFC9.icon")); // NOI18N
        jToggleButtonFC9.setText(resourceMap.getString("jToggleButtonFC9.text")); // NOI18N
        jToggleButtonFC9.setActionCommand(resourceMap.getString("jToggleButtonFC9.actionCommand")); // NOI18N
        jToggleButtonFC9.setName("jToggleButtonFC9"); // NOI18N
        jToggleButtonFC9.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFC0.setAction(actionMap.get("selecionarCategoriaFrente")); // NOI18N
        buttonGroupCategoriasFrente.add(jToggleButtonFC0);
        jToggleButtonFC0.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFC0.setText(resourceMap.getString("jToggleButtonFC0.text")); // NOI18N
        jToggleButtonFC0.setActionCommand(resourceMap.getString("jToggleButtonFC0.actionCommand")); // NOI18N
        jToggleButtonFC0.setName("jToggleButtonFC0"); // NOI18N
        jToggleButtonFC0.setPreferredSize(new java.awt.Dimension(85, 85));

        buttonGroupCategoriasFrente.add(jToggleButtonFCFantasma);
        jToggleButtonFCFantasma.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFCFantasma.setText(resourceMap.getString("jToggleButtonFCFantasma.text")); // NOI18N
        jToggleButtonFCFantasma.setName("jToggleButtonFCFantasma"); // NOI18N

        javax.swing.GroupLayout jPanelCategoriasFrenteLayout = new javax.swing.GroupLayout(jPanelCategoriasFrente);
        jPanelCategoriasFrente.setLayout(jPanelCategoriasFrenteLayout);
        jPanelCategoriasFrenteLayout.setHorizontalGroup(
            jPanelCategoriasFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCategoriasFrenteLayout.createSequentialGroup()
                .addComponent(jToggleButtonFC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelCategoriasFrenteLayout.createSequentialGroup()
                .addComponent(jToggleButtonFC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFC0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelCategoriasFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelCategoriasFrenteLayout.createSequentialGroup()
                    .addGap(214, 214, 214)
                    .addComponent(jToggleButtonFCFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(215, Short.MAX_VALUE)))
        );
        jPanelCategoriasFrenteLayout.setVerticalGroup(
            jPanelCategoriasFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCategoriasFrenteLayout.createSequentialGroup()
                .addGroup(jPanelCategoriasFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonFC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelCategoriasFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonFC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFC0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanelCategoriasFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelCategoriasFrenteLayout.createSequentialGroup()
                    .addGap(85, 85, 85)
                    .addComponent(jToggleButtonFCFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(86, Short.MAX_VALUE)))
        );

        jPanelProdutosFrente.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelProdutosFrente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelProdutosFrente.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelProdutosFrente.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelProdutosFrente.setName("jPanelProdutosFrente"); // NOI18N

        jToggleButtonFP1.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP1);
        jToggleButtonFP1.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP1.setText(resourceMap.getString("jToggleButtonFP1.text")); // NOI18N
        jToggleButtonFP1.setActionCommand(resourceMap.getString("jToggleButtonFP1.actionCommand")); // NOI18N
        jToggleButtonFP1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP1.setName("jToggleButtonFP1"); // NOI18N
        jToggleButtonFP1.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP2.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP2);
        jToggleButtonFP2.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP2.setText(resourceMap.getString("jToggleButtonFP2.text")); // NOI18N
        jToggleButtonFP2.setActionCommand(resourceMap.getString("jToggleButtonFP2.actionCommand")); // NOI18N
        jToggleButtonFP2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP2.setName("jToggleButtonFP2"); // NOI18N
        jToggleButtonFP2.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP3.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP3);
        jToggleButtonFP3.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP3.setText(resourceMap.getString("jToggleButtonFP3.text")); // NOI18N
        jToggleButtonFP3.setActionCommand(resourceMap.getString("jToggleButtonFP3.actionCommand")); // NOI18N
        jToggleButtonFP3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP3.setName("jToggleButtonFP3"); // NOI18N
        jToggleButtonFP3.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP4.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP4);
        jToggleButtonFP4.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP4.setText(resourceMap.getString("jToggleButtonFP4.text")); // NOI18N
        jToggleButtonFP4.setActionCommand(resourceMap.getString("jToggleButtonFP4.actionCommand")); // NOI18N
        jToggleButtonFP4.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP4.setName("jToggleButtonFP4"); // NOI18N
        jToggleButtonFP4.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP5.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP5);
        jToggleButtonFP5.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP5.setText(resourceMap.getString("jToggleButtonFP5.text")); // NOI18N
        jToggleButtonFP5.setActionCommand(resourceMap.getString("jToggleButtonFP5.actionCommand")); // NOI18N
        jToggleButtonFP5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP5.setName("jToggleButtonFP5"); // NOI18N
        jToggleButtonFP5.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP6.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP6);
        jToggleButtonFP6.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP6.setText(resourceMap.getString("jToggleButtonFP6.text")); // NOI18N
        jToggleButtonFP6.setActionCommand(resourceMap.getString("jToggleButtonFP6.actionCommand")); // NOI18N
        jToggleButtonFP6.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP6.setName("jToggleButtonFP6"); // NOI18N
        jToggleButtonFP6.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP7.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP7);
        jToggleButtonFP7.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP7.setText(resourceMap.getString("jToggleButtonFP7.text")); // NOI18N
        jToggleButtonFP7.setActionCommand(resourceMap.getString("jToggleButtonFP7.actionCommand")); // NOI18N
        jToggleButtonFP7.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP7.setName("jToggleButtonFP7"); // NOI18N
        jToggleButtonFP7.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP8.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP8);
        jToggleButtonFP8.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP8.setText(resourceMap.getString("jToggleButtonFP8.text")); // NOI18N
        jToggleButtonFP8.setActionCommand(resourceMap.getString("jToggleButtonFP8.actionCommand")); // NOI18N
        jToggleButtonFP8.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP8.setName("jToggleButtonFP8"); // NOI18N
        jToggleButtonFP8.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP9.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP9);
        jToggleButtonFP9.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP9.setText(resourceMap.getString("jToggleButtonFP9.text")); // NOI18N
        jToggleButtonFP9.setActionCommand(resourceMap.getString("jToggleButtonFP9.actionCommand")); // NOI18N
        jToggleButtonFP9.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP9.setName("jToggleButtonFP9"); // NOI18N
        jToggleButtonFP9.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonFP0.setAction(actionMap.get("selecionarProdutoFrente")); // NOI18N
        buttonGroupProdutosFrente.add(jToggleButtonFP0);
        jToggleButtonFP0.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFP0.setText(resourceMap.getString("jToggleButtonFP0.text")); // NOI18N
        jToggleButtonFP0.setActionCommand(resourceMap.getString("jToggleButtonFP0.actionCommand")); // NOI18N
        jToggleButtonFP0.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonFP0.setName("jToggleButtonFP0"); // NOI18N
        jToggleButtonFP0.setPreferredSize(new java.awt.Dimension(85, 85));

        buttonGroupProdutosFrente.add(jToggleButtonFPFantasma);
        jToggleButtonFPFantasma.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonFPFantasma.setText(resourceMap.getString("jToggleButtonFPFantasma.text")); // NOI18N
        jToggleButtonFPFantasma.setEnabled(false);
        jToggleButtonFPFantasma.setName("jToggleButtonFPFantasma"); // NOI18N

        javax.swing.GroupLayout jPanelProdutosFrenteLayout = new javax.swing.GroupLayout(jPanelProdutosFrente);
        jPanelProdutosFrente.setLayout(jPanelProdutosFrenteLayout);
        jPanelProdutosFrenteLayout.setHorizontalGroup(
            jPanelProdutosFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProdutosFrenteLayout.createSequentialGroup()
                .addComponent(jToggleButtonFP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFP4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFP5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelProdutosFrenteLayout.createSequentialGroup()
                .addComponent(jToggleButtonFP6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFP7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFP8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFP9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonFP0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelProdutosFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelProdutosFrenteLayout.createSequentialGroup()
                    .addGap(214, 214, 214)
                    .addComponent(jToggleButtonFPFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(215, Short.MAX_VALUE)))
        );
        jPanelProdutosFrenteLayout.setVerticalGroup(
            jPanelProdutosFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProdutosFrenteLayout.createSequentialGroup()
                .addGroup(jPanelProdutosFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonFP4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFP5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelProdutosFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonFP6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFP7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFP8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFP9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonFP0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanelProdutosFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelProdutosFrenteLayout.createSequentialGroup()
                    .addGap(85, 85, 85)
                    .addComponent(jToggleButtonFPFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(86, Short.MAX_VALUE)))
        );

        jPanelComanda.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelComanda.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelComanda.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelComanda.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelComanda.setName("jPanelComanda"); // NOI18N
        jPanelComanda.setPreferredSize(new java.awt.Dimension(500, 278));

        jLabelCliente.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelCliente.setText(resourceMap.getString("jLabelCliente.text")); // NOI18N
        jLabelCliente.setFocusable(false);
        jLabelCliente.setName("jLabelCliente"); // NOI18N

        jTextFieldCliente.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldCliente.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jTextFieldCliente.setName("jTextFieldCliente"); // NOI18N
        jTextFieldCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClienteActionPerformed(evt);
            }
        });

        jScrollPaneComanda.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneComanda.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPaneComanda.setMaximumSize(new java.awt.Dimension(336, 113));
        jScrollPaneComanda.setMinimumSize(new java.awt.Dimension(336, 113));
        jScrollPaneComanda.setName("jScrollPaneComanda"); // NOI18N
        jScrollPaneComanda.setPreferredSize(new java.awt.Dimension(336, 113));

        jTableComanda.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jTableComanda.setForeground(resourceMap.getColor("jTableComanda.foreground")); // NOI18N
        jTableComanda.setModel(tableModel);
        jTableComanda.setToolTipText(resourceMap.getString("jTableComanda.toolTipText")); // NOI18N
        jTableComanda.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableComanda.setColumnSelectionAllowed(false);
        jTableComanda.setMaximumSize(new java.awt.Dimension(316, 111));
        jTableComanda.setMinimumSize(new java.awt.Dimension(316, 111));
        jTableComanda.setName("jTableComanda"); // NOI18N
        jTableComanda.setPreferredSize(new java.awt.Dimension(316, 111));
        jTableComanda.setRowSelectionAllowed(true);
        jTableComanda.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTableComanda.setShowHorizontalLines(false);
        jTableComanda.setShowVerticalLines(false);
        jTableComanda.setTableHeader(null);
        jScrollPaneComanda.setViewportView(jTableComanda);
        jTableComanda.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // Comandos para definir a largura das colunas:
        jTableComanda.getColumnModel().getColumn(0).setResizable(false);
        jTableComanda.getColumnModel().getColumn(0).setPreferredWidth(65);
        jTableComanda.getColumnModel().getColumn(0).setMinWidth(65);
        jTableComanda.getColumnModel().getColumn(0).setMaxWidth(65);
        jTableComanda.getColumnModel().getColumn(1).setResizable(true);
        //jTableComanda.getColumnModel().getColumn(1).setPreferredWidth(215);
        jTableComanda.getColumnModel().getColumn(2).setResizable(false);
        jTableComanda.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTableComanda.getColumnModel().getColumn(2).setMinWidth(80);
        jTableComanda.getColumnModel().getColumn(2).setMaxWidth(80);
        /* Comandos para organizar o formato e o alinhamento de colunas da jTableComanda
        * Fonte: http://tips4java.wordpress.com/2008/10/11/table-format-renderers/
        */
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(SwingConstants.CENTER);
        jTableComanda.getColumnModel().getColumn(0).setCellRenderer(FormatRenderer.getFloat3CasasRenderer());
        jTableComanda.getColumnModel().getColumn(1).setCellRenderer(cr);
        jTableComanda.getColumnModel().getColumn(2).setCellRenderer(FormatRenderer.getCurrencyRenderer());

        jLabelTotalComanda.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelTotalComanda.setText(resourceMap.getString("jLabelTotalComanda.text")); // NOI18N
        jLabelTotalComanda.setFocusable(false);
        jLabelTotalComanda.setName("jLabelTotalComanda"); // NOI18N

        jTextFieldTotalComanda.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldTotalComanda.setEditable(false);
        jTextFieldTotalComanda.setFont(resourceMap.getFont("FonteDestaque2")); // NOI18N
        jTextFieldTotalComanda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldTotalComanda.setText(resourceMap.getString("jTextFieldTotalComanda.text")); // NOI18N
        jTextFieldTotalComanda.setEnabled(false);
        jTextFieldTotalComanda.setName("jTextFieldTotalComanda"); // NOI18N

        jButtonExcluirItem.setAction(actionMap.get("removerPedido")); // NOI18N
        jButtonExcluirItem.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonExcluirItem.setText(resourceMap.getString("jButtonExcluirItem.text")); // NOI18N
        jButtonExcluirItem.setName("jButtonExcluirItem"); // NOI18N

        jButtonGuardar.setAction(actionMap.get("guardarComanda")); // NOI18N
        jButtonGuardar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonGuardar.setText(resourceMap.getString("jButtonGuardar.text")); // NOI18N
        jButtonGuardar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonGuardar.setName("jButtonGuardar"); // NOI18N
        jButtonGuardar.setPreferredSize(new java.awt.Dimension(97, 25));

        jButtonFinalizar.setAction(actionMap.get("fecharComanda")); // NOI18N
        jButtonFinalizar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonFinalizar.setText(resourceMap.getString("jButtonFinalizar.text")); // NOI18N
        jButtonFinalizar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonFinalizar.setName("jButtonFinalizar"); // NOI18N

        javax.swing.GroupLayout jPanelComandaLayout = new javax.swing.GroupLayout(jPanelComanda);
        jPanelComanda.setLayout(jPanelComandaLayout);
        jPanelComandaLayout.setHorizontalGroup(
            jPanelComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelComandaLayout.createSequentialGroup()
                    .addComponent(jLabelCliente)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jTextFieldCliente))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelComandaLayout.createSequentialGroup()
                    .addComponent(jLabelTotalComanda)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextFieldTotalComanda))
                .addComponent(jScrollPaneComanda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelComandaLayout.createSequentialGroup()
                .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jButtonFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jButtonExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelComandaLayout.setVerticalGroup(
            jPanelComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComandaLayout.createSequentialGroup()
                .addGroup(jPanelComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCliente)
                    .addComponent(jTextFieldCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneComanda, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTotalComanda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTotalComanda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelComandaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonExcluirItem)
                    .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonFinalizar)))
        );

        jPanelClientes.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelClientes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelClientes.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelClientes.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelClientes.setName("jPanelClientes"); // NOI18N
        jPanelClientes.setPreferredSize(new java.awt.Dimension(1050, 160));

        buttonGroupClientes.add(jToggleButtonComanda01);
        jToggleButtonComanda01.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda01.setText(resourceMap.getString("jToggleButtonComanda01.text")); // NOI18N
        jToggleButtonComanda01.setActionCommand(resourceMap.getString("jToggleButtonComanda01.actionCommand")); // NOI18N
        jToggleButtonComanda01.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda01.setContentAreaFilled(false);
        jToggleButtonComanda01.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda01.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda01.setName("jToggleButtonComanda01"); // NOI18N
        jToggleButtonComanda01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda01MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda02);
        jToggleButtonComanda02.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda02.setText(resourceMap.getString("jToggleButtonComanda02.text")); // NOI18N
        jToggleButtonComanda02.setActionCommand(resourceMap.getString("jToggleButtonComanda02.actionCommand")); // NOI18N
        jToggleButtonComanda02.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda02.setContentAreaFilled(false);
        jToggleButtonComanda02.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda02.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda02.setName("jToggleButtonComanda02"); // NOI18N
        jToggleButtonComanda02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda02MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda03);
        jToggleButtonComanda03.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda03.setText(resourceMap.getString("jToggleButtonComanda03.text")); // NOI18N
        jToggleButtonComanda03.setActionCommand(resourceMap.getString("jToggleButtonComanda03.actionCommand")); // NOI18N
        jToggleButtonComanda03.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda03.setContentAreaFilled(false);
        jToggleButtonComanda03.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda03.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda03.setName("jToggleButtonComanda03"); // NOI18N
        jToggleButtonComanda03.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda03MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda04);
        jToggleButtonComanda04.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda04.setText(resourceMap.getString("jToggleButtonComanda04.text")); // NOI18N
        jToggleButtonComanda04.setActionCommand(resourceMap.getString("jToggleButtonComanda04.actionCommand")); // NOI18N
        jToggleButtonComanda04.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda04.setContentAreaFilled(false);
        jToggleButtonComanda04.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda04.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda04.setName("jToggleButtonComanda04"); // NOI18N
        jToggleButtonComanda04.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda04MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda05);
        jToggleButtonComanda05.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda05.setText(resourceMap.getString("jToggleButtonComanda05.text")); // NOI18N
        jToggleButtonComanda05.setActionCommand(resourceMap.getString("jToggleButtonComanda05.actionCommand")); // NOI18N
        jToggleButtonComanda05.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda05.setContentAreaFilled(false);
        jToggleButtonComanda05.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda05.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda05.setName("jToggleButtonComanda05"); // NOI18N
        jToggleButtonComanda05.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda05MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda06);
        jToggleButtonComanda06.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda06.setText(resourceMap.getString("jToggleButtonComanda06.text")); // NOI18N
        jToggleButtonComanda06.setActionCommand(resourceMap.getString("jToggleButtonComanda06.actionCommand")); // NOI18N
        jToggleButtonComanda06.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda06.setContentAreaFilled(false);
        jToggleButtonComanda06.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda06.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda06.setName("jToggleButtonComanda06"); // NOI18N
        jToggleButtonComanda06.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda06MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda07);
        jToggleButtonComanda07.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda07.setText(resourceMap.getString("jToggleButtonComanda07.text")); // NOI18N
        jToggleButtonComanda07.setActionCommand(resourceMap.getString("jToggleButtonComanda07.actionCommand")); // NOI18N
        jToggleButtonComanda07.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda07.setContentAreaFilled(false);
        jToggleButtonComanda07.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda07.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda07.setName("jToggleButtonComanda07"); // NOI18N
        jToggleButtonComanda07.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda07MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda08);
        jToggleButtonComanda08.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda08.setText(resourceMap.getString("jToggleButtonComanda08.text")); // NOI18N
        jToggleButtonComanda08.setActionCommand(resourceMap.getString("jToggleButtonComanda08.actionCommand")); // NOI18N
        jToggleButtonComanda08.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda08.setContentAreaFilled(false);
        jToggleButtonComanda08.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda08.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda08.setName("jToggleButtonComanda08"); // NOI18N
        jToggleButtonComanda08.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda08MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda09);
        jToggleButtonComanda09.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda09.setText(resourceMap.getString("jToggleButtonComanda09.text")); // NOI18N
        jToggleButtonComanda09.setActionCommand(resourceMap.getString("jToggleButtonComanda09.actionCommand")); // NOI18N
        jToggleButtonComanda09.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda09.setContentAreaFilled(false);
        jToggleButtonComanda09.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda09.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda09.setName("jToggleButtonComanda09"); // NOI18N
        jToggleButtonComanda09.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda09MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda10);
        jToggleButtonComanda10.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda10.setText(resourceMap.getString("jToggleButtonComanda10.text")); // NOI18N
        jToggleButtonComanda10.setActionCommand(resourceMap.getString("jToggleButtonComanda10.actionCommand")); // NOI18N
        jToggleButtonComanda10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda10.setContentAreaFilled(false);
        jToggleButtonComanda10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda10.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda10.setName("jToggleButtonComanda10"); // NOI18N
        jToggleButtonComanda10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda10MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda11);
        jToggleButtonComanda11.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda11.setText(resourceMap.getString("jToggleButtonComanda11.text")); // NOI18N
        jToggleButtonComanda11.setActionCommand(resourceMap.getString("jToggleButtonComanda11.actionCommand")); // NOI18N
        jToggleButtonComanda11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda11.setContentAreaFilled(false);
        jToggleButtonComanda11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda11.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda11.setName("jToggleButtonComanda11"); // NOI18N
        jToggleButtonComanda11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda11MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda12);
        jToggleButtonComanda12.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda12.setText(resourceMap.getString("jToggleButtonComanda12.text")); // NOI18N
        jToggleButtonComanda12.setActionCommand(resourceMap.getString("jToggleButtonComanda12.actionCommand")); // NOI18N
        jToggleButtonComanda12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda12.setContentAreaFilled(false);
        jToggleButtonComanda12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda12.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda12.setName("jToggleButtonComanda12"); // NOI18N
        jToggleButtonComanda12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda12MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda13);
        jToggleButtonComanda13.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda13.setText(resourceMap.getString("jToggleButtonComanda13.text")); // NOI18N
        jToggleButtonComanda13.setActionCommand(resourceMap.getString("jToggleButtonComanda13.actionCommand")); // NOI18N
        jToggleButtonComanda13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda13.setContentAreaFilled(false);
        jToggleButtonComanda13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda13.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda13.setName("jToggleButtonComanda13"); // NOI18N
        jToggleButtonComanda13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda13MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda14);
        jToggleButtonComanda14.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda14.setText(resourceMap.getString("jToggleButtonComanda14.text")); // NOI18N
        jToggleButtonComanda14.setActionCommand(resourceMap.getString("jToggleButtonComanda14.actionCommand")); // NOI18N
        jToggleButtonComanda14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda14.setContentAreaFilled(false);
        jToggleButtonComanda14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda14.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda14.setName("jToggleButtonComanda14"); // NOI18N
        jToggleButtonComanda14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda14MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComanda15);
        jToggleButtonComanda15.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComanda15.setText(resourceMap.getString("jToggleButtonComanda15.text")); // NOI18N
        jToggleButtonComanda15.setActionCommand(resourceMap.getString("jToggleButtonComanda15.actionCommand")); // NOI18N
        jToggleButtonComanda15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonComanda15.setContentAreaFilled(false);
        jToggleButtonComanda15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jToggleButtonComanda15.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonComanda15.setName("jToggleButtonComanda15"); // NOI18N
        jToggleButtonComanda15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButtonComanda15MouseClicked(evt);
            }
        });

        buttonGroupClientes.add(jToggleButtonComandaFantasma);
        jToggleButtonComandaFantasma.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonComandaFantasma.setText(resourceMap.getString("jToggleButtonComandaFantasma.text")); // NOI18N
        jToggleButtonComandaFantasma.setName("jToggleButtonComandaFantasma"); // NOI18N

        jButtonSelecionar.setAction(actionMap.get("selecionarComanda")); // NOI18N
        jButtonSelecionar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonSelecionar.setText(resourceMap.getString("jButtonSelecionar.text")); // NOI18N
        jButtonSelecionar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonSelecionar.setName("jButtonSelecionar"); // NOI18N

        jButtonJuntar.setAction(actionMap.get("unirComandaAtiva")); // NOI18N
        jButtonJuntar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonJuntar.setText(resourceMap.getString("jButtonJuntar.text")); // NOI18N
        jButtonJuntar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonJuntar.setName("jButtonJuntar"); // NOI18N

        jButtonSeparar.setAction(actionMap.get("dividirComanda")); // NOI18N
        jButtonSeparar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonSeparar.setText(resourceMap.getString("jButtonSeparar.text")); // NOI18N
        jButtonSeparar.setActionCommand(resourceMap.getString("jButtonSeparar.actionCommand")); // NOI18N
        jButtonSeparar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonSeparar.setName("jButtonSeparar"); // NOI18N

        javax.swing.GroupLayout jPanelClientesLayout = new javax.swing.GroupLayout(jPanelClientes);
        jPanelClientes.setLayout(jPanelClientesLayout);
        jPanelClientesLayout.setHorizontalGroup(
            jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientesLayout.createSequentialGroup()
                .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelClientesLayout.createSequentialGroup()
                        .addComponent(jToggleButtonComanda01, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jToggleButtonComanda06, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelClientesLayout.createSequentialGroup()
                        .addComponent(jToggleButtonComanda02, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jToggleButtonComanda07, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelClientesLayout.createSequentialGroup()
                        .addComponent(jToggleButtonComanda03, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jToggleButtonComanda08, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelClientesLayout.createSequentialGroup()
                        .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButtonSelecionar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToggleButtonComanda05, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanelClientesLayout.createSequentialGroup()
                                .addComponent(jToggleButtonComanda10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addComponent(jButtonJuntar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelClientesLayout.createSequentialGroup()
                        .addComponent(jToggleButtonComanda04, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jToggleButtonComanda09, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToggleButtonComanda12, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jToggleButtonComanda11, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jToggleButtonComanda13, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jToggleButtonComanda14, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jToggleButtonComanda15, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jButtonSeparar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelClientesLayout.createSequentialGroup()
                    .addGap(168, 168, 168)
                    .addComponent(jToggleButtonComandaFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(168, Short.MAX_VALUE)))
        );
        jPanelClientesLayout.setVerticalGroup(
            jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientesLayout.createSequentialGroup()
                .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonComanda01, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda06, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonComanda02, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda07, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonComanda03, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda08, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonComanda04, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda09, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonComanda05, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonComanda10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSelecionar)
                    .addComponent(jButtonJuntar)
                    .addComponent(jButtonSeparar)))
            .addGroup(jPanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelClientesLayout.createSequentialGroup()
                    .addGap(82, 82, 82)
                    .addComponent(jToggleButtonComandaFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(82, Short.MAX_VALUE)))
        );

        jPanelTempo.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelTempo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelTempo.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelTempo.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelTempo.setName("jPanelTempo"); // NOI18N

        jToggleButtonEnsolarado.setAction(actionMap.get("selecionarTempo")); // NOI18N
        buttonGroupTempo.add(jToggleButtonEnsolarado);
        jToggleButtonEnsolarado.setIcon(resourceMap.getIcon("jToggleButtonEnsolarado.icon")); // NOI18N
        jToggleButtonEnsolarado.setText(resourceMap.getString("jToggleButtonEnsolarado.text")); // NOI18N
        jToggleButtonEnsolarado.setToolTipText(resourceMap.getString("jToggleButtonEnsolarado.toolTipText")); // NOI18N
        jToggleButtonEnsolarado.setActionCommand(resourceMap.getString("jToggleButtonEnsolarado.actionCommand")); // NOI18N
        jToggleButtonEnsolarado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonEnsolarado.setContentAreaFilled(false);
        jToggleButtonEnsolarado.setName("jToggleButtonEnsolarado"); // NOI18N
        jToggleButtonEnsolarado.setSelectedIcon(resourceMap.getIcon("jToggleButtonEnsolarado.selectedIcon")); // NOI18N

        jToggleButtonNublado.setAction(actionMap.get("selecionarTempo")); // NOI18N
        buttonGroupTempo.add(jToggleButtonNublado);
        jToggleButtonNublado.setIcon(resourceMap.getIcon("jToggleButtonNublado.icon")); // NOI18N
        jToggleButtonNublado.setText(resourceMap.getString("jToggleButtonNublado.text")); // NOI18N
        jToggleButtonNublado.setToolTipText(resourceMap.getString("jToggleButtonNublado.toolTipText")); // NOI18N
        jToggleButtonNublado.setActionCommand(resourceMap.getString("jToggleButtonNublado.actionCommand")); // NOI18N
        jToggleButtonNublado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonNublado.setContentAreaFilled(false);
        jToggleButtonNublado.setName("jToggleButtonNublado"); // NOI18N
        jToggleButtonNublado.setSelectedIcon(resourceMap.getIcon("jToggleButtonNublado.selectedIcon")); // NOI18N

        jToggleButtonInstavel.setAction(actionMap.get("selecionarTempo")); // NOI18N
        buttonGroupTempo.add(jToggleButtonInstavel);
        jToggleButtonInstavel.setIcon(resourceMap.getIcon("jToggleButtonInstavel.icon")); // NOI18N
        jToggleButtonInstavel.setText(resourceMap.getString("jToggleButtonInstavel.text")); // NOI18N
        jToggleButtonInstavel.setToolTipText(resourceMap.getString("jToggleButtonInstavel.toolTipText")); // NOI18N
        jToggleButtonInstavel.setActionCommand(resourceMap.getString("jToggleButtonInstavel.actionCommand")); // NOI18N
        jToggleButtonInstavel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonInstavel.setContentAreaFilled(false);
        jToggleButtonInstavel.setName("jToggleButtonInstavel"); // NOI18N
        jToggleButtonInstavel.setSelectedIcon(resourceMap.getIcon("jToggleButtonInstavel.selectedIcon")); // NOI18N

        jToggleButtonChuvoso.setAction(actionMap.get("selecionarTempo")); // NOI18N
        buttonGroupTempo.add(jToggleButtonChuvoso);
        jToggleButtonChuvoso.setIcon(resourceMap.getIcon("jToggleButtonChuvoso.icon")); // NOI18N
        jToggleButtonChuvoso.setText(resourceMap.getString("jToggleButtonChuvoso.text")); // NOI18N
        jToggleButtonChuvoso.setToolTipText(resourceMap.getString("jToggleButtonChuvoso.toolTipText")); // NOI18N
        jToggleButtonChuvoso.setActionCommand(resourceMap.getString("jToggleButtonChuvoso.actionCommand")); // NOI18N
        jToggleButtonChuvoso.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jToggleButtonChuvoso.setContentAreaFilled(false);
        jToggleButtonChuvoso.setName("jToggleButtonChuvoso"); // NOI18N
        jToggleButtonChuvoso.setSelectedIcon(resourceMap.getIcon("jToggleButtonChuvoso.selectedIcon")); // NOI18N

        jToggleButtonTempoFantasma.setText(resourceMap.getString("jToggleButtonTempoFantasma.text")); // NOI18N
        jToggleButtonTempoFantasma.setActionCommand(resourceMap.getString("jToggleButtonTempoFantasma.actionCommand")); // NOI18N
        jToggleButtonTempoFantasma.setName("jToggleButtonTempoFantasma"); // NOI18N
        jToggleButtonTempoFantasma.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanelTempoLayout = new javax.swing.GroupLayout(jPanelTempo);
        jPanelTempo.setLayout(jPanelTempoLayout);
        jPanelTempoLayout.setHorizontalGroup(
            jPanelTempoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTempoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTempoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jToggleButtonChuvoso)
                    .addComponent(jToggleButtonInstavel)
                    .addComponent(jToggleButtonNublado)
                    .addComponent(jToggleButtonEnsolarado))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelTempoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelTempoLayout.createSequentialGroup()
                    .addGap(0, 37, Short.MAX_VALUE)
                    .addComponent(jToggleButtonTempoFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 36, Short.MAX_VALUE)))
        );
        jPanelTempoLayout.setVerticalGroup(
            jPanelTempoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTempoLayout.createSequentialGroup()
                .addComponent(jToggleButtonEnsolarado)
                .addGap(2, 2, 2)
                .addComponent(jToggleButtonNublado)
                .addGap(2, 2, 2)
                .addComponent(jToggleButtonInstavel)
                .addGap(2, 2, 2)
                .addComponent(jToggleButtonChuvoso))
            .addGroup(jPanelTempoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelTempoLayout.createSequentialGroup()
                    .addGap(0, 99, Short.MAX_VALUE)
                    .addComponent(jToggleButtonTempoFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 87, Short.MAX_VALUE)))
        );

        jButtonAbastec.setAction(actionMap.get("solicitarAbastecimento")); // NOI18N
        jButtonAbastec.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonAbastec.setText(resourceMap.getString("jButtonAbastec.text")); // NOI18N
        jButtonAbastec.setToolTipText(resourceMap.getString("jButtonAbastec.toolTipText")); // NOI18N
        jButtonAbastec.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonAbastec.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonAbastec.setName("jButtonAbastec"); // NOI18N
        jButtonAbastec.setPreferredSize(new java.awt.Dimension(63, 25));

        jButtonReforco.setAction(actionMap.get("realizarReforco")); // NOI18N
        jButtonReforco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonReforco.setText(resourceMap.getString("jButtonReforco.text")); // NOI18N
        jButtonReforco.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonReforco.setName("jButtonReforco"); // NOI18N
        jButtonReforco.setPreferredSize(new java.awt.Dimension(81, 25));

        jButtonSangria.setAction(actionMap.get("realizarSangria")); // NOI18N
        jButtonSangria.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonSangria.setText(resourceMap.getString("jButtonSangria.text")); // NOI18N
        jButtonSangria.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonSangria.setName("jButtonSangria"); // NOI18N

        jButtonDespesas.setAction(actionMap.get("adicionarDespesa")); // NOI18N
        jButtonDespesas.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonDespesas.setText(resourceMap.getString("jButtonDespesas.text")); // NOI18N
        jButtonDespesas.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonDespesas.setName("jButtonDespesas"); // NOI18N
        jButtonDespesas.setPreferredSize(new java.awt.Dimension(167, 25));

        jButtonComandas.setAction(actionMap.get("verComandasFechadas")); // NOI18N
        jButtonComandas.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonComandas.setText(resourceMap.getString("jButtonComandas.text")); // NOI18N
        jButtonComandas.setActionCommand(resourceMap.getString("jButtonComandas.actionCommand")); // NOI18N
        jButtonComandas.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonComandas.setName("jButtonComandas"); // NOI18N

        jButtonFechamento.setAction(actionMap.get("fecharCaixa")); // NOI18N
        jButtonFechamento.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonFechamento.setText(resourceMap.getString("jButtonFechamento.text")); // NOI18N
        jButtonFechamento.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonFechamento.setMargin(new java.awt.Insets(2, 5, 2, 5));
        jButtonFechamento.setMaximumSize(new java.awt.Dimension(151, 23));
        jButtonFechamento.setMinimumSize(new java.awt.Dimension(151, 23));
        jButtonFechamento.setName("jButtonFechamento"); // NOI18N
        jButtonFechamento.setPreferredSize(new java.awt.Dimension(151, 23));

        jLabelLogo.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelLogo.setIcon(resourceMap.getIcon("jLabelLogo.icon")); // NOI18N
        jLabelLogo.setText(resourceMap.getString("jLabelLogo.text")); // NOI18N
        jLabelLogo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelLogo.setName("jLabelLogo"); // NOI18N

        javax.swing.GroupLayout jPanelFrenteLayout = new javax.swing.GroupLayout(jPanelFrente);
        jPanelFrente.setLayout(jPanelFrenteLayout);
        jPanelFrenteLayout.setHorizontalGroup(
            jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFrenteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFrenteLayout.createSequentialGroup()
                        .addGroup(jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelCategoriasFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelProdutosFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanelEntradaFrente, 0, 791, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLogo, 0, 0, Short.MAX_VALUE)
                    .addComponent(jButtonComandas, 0, 85, Short.MAX_VALUE)
                    .addComponent(jButtonFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jButtonDespesas, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jButtonSangria, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAbastec, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReforco, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelFrenteLayout.setVerticalGroup(
            jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFrenteLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFrenteLayout.createSequentialGroup()
                        .addGroup(jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelFrenteLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jPanelEntradaFrente, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelFrenteLayout.createSequentialGroup()
                                .addComponent(jPanelCategoriasFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jPanelProdutosFrente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelFrenteLayout.createSequentialGroup()
                                .addComponent(jPanelComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addGroup(jPanelFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanelFrenteLayout.createSequentialGroup()
                                        .addComponent(jButtonReforco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jButtonSangria)
                                        .addGap(2, 2, 2)
                                        .addComponent(jButtonDespesas, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jButtonComandas, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jButtonFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanelClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanelFrenteLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jPanelTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonAbastec, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane.addTab(resourceMap.getString("jPanelFrente.TabConstraints.tabTitle"), jPanelFrente); // NOI18N

        jPanelCadastramento.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanelCadastramento.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelCadastramento.setName("jPanelCadastramento"); // NOI18N

        jPanelEntradaBanco.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelEntradaBanco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelEntradaBanco.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelEntradaBanco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelEntradaBanco.setName("jPanelEntradaBanco"); // NOI18N

        jLabelCodigoBanco.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jLabelCodigoBanco.setText(resourceMap.getString("jLabelCodigoBanco.text")); // NOI18N
        jLabelCodigoBanco.setFocusable(false);
        jLabelCodigoBanco.setName("jLabelCodigoBanco"); // NOI18N

        jLabelValorBanco.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jLabelValorBanco.setText(resourceMap.getString("jLabelValorBanco.text")); // NOI18N
        jLabelValorBanco.setFocusable(false);
        jLabelValorBanco.setName("jLabelValorBanco"); // NOI18N

        jLabelProdutoBanco.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jLabelProdutoBanco.setText(resourceMap.getString("jLabelProdutoBanco.text")); // NOI18N
        jLabelProdutoBanco.setFocusable(false);
        jLabelProdutoBanco.setName("jLabelProdutoBanco"); // NOI18N

        jFormattedTextFieldValorBanco.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jFormattedTextFieldValorBanco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        jFormattedTextFieldValorBanco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFormattedTextFieldValorBanco.setText(resourceMap.getString("jFormattedTextFieldValorBanco.text")); // NOI18N
        jFormattedTextFieldValorBanco.setFocusCycleRoot(true);
        jFormattedTextFieldValorBanco.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        jFormattedTextFieldValorBanco.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jFormattedTextFieldValorBanco.setName("jFormattedTextFieldValorBanco"); // NOI18N
        jFormattedTextFieldValorBanco.setPreferredSize(new java.awt.Dimension(80, 23));
        jFormattedTextFieldValorBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextFieldValorBancoActionPerformed(evt);
            }
        });
        jFormattedTextFieldValorBanco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldValorBancoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldValorBancoFocusLost(evt);
            }
        });

        jTextFieldProdutoBanco.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jTextFieldProdutoBanco.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jTextFieldProdutoBanco.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTextFieldProdutoBanco.setText(resourceMap.getString("jTextFieldProdutoBanco.text")); // NOI18N
        jTextFieldProdutoBanco.setName("jTextFieldProdutoBanco"); // NOI18N
        jTextFieldProdutoBanco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldProdutoBancoActionPerformed(evt);
            }
        });

        jButtonAdicionarEntradaBanco.setAction(actionMap.get("adicionarProduto")); // NOI18N
        jButtonAdicionarEntradaBanco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonAdicionarEntradaBanco.setText(resourceMap.getString("jButtonAdicionarEntradaBanco.text")); // NOI18N
        jButtonAdicionarEntradaBanco.setName("jButtonAdicionarEntradaBanco"); // NOI18N

        jButtonRemoverEntradaBanco.setAction(actionMap.get("removerProduto")); // NOI18N
        jButtonRemoverEntradaBanco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonRemoverEntradaBanco.setText(resourceMap.getString("jButtonRemoverEntradaBanco.text")); // NOI18N
        jButtonRemoverEntradaBanco.setName("jButtonRemoverEntradaBanco"); // NOI18N

        jTextFieldCodigoBanco.setBackground(resourceMap.getColor("jTextFieldCodigoBanco.background")); // NOI18N
        jTextFieldCodigoBanco.setFont(resourceMap.getFont("FonteDestaque")); // NOI18N
        jTextFieldCodigoBanco.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldCodigoBanco.setName("jTextFieldCodigoBanco"); // NOI18N
        jTextFieldCodigoBanco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldCodigoBancoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldCodigoBancoFocusLost(evt);
            }
        });
        jTextFieldCodigoBanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCodigoBancoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanelEntradaBancoLayout = new javax.swing.GroupLayout(jPanelEntradaBanco);
        jPanelEntradaBanco.setLayout(jPanelEntradaBancoLayout);
        jPanelEntradaBancoLayout.setHorizontalGroup(
            jPanelEntradaBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEntradaBancoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelEntradaBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCodigoBanco)
                    .addComponent(jTextFieldCodigoBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradaBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFormattedTextFieldValorBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelValorBanco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradaBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelProdutoBanco)
                    .addComponent(jTextFieldProdutoBanco, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradaBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonAdicionarEntradaBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRemoverEntradaBanco)))
        );
        jPanelEntradaBancoLayout.setVerticalGroup(
            jPanelEntradaBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEntradaBancoLayout.createSequentialGroup()
                .addComponent(jLabelProdutoBanco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldProdutoBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanelEntradaBancoLayout.createSequentialGroup()
                .addComponent(jButtonAdicionarEntradaBanco)
                .addGap(2, 2, 2)
                .addComponent(jButtonRemoverEntradaBanco))
            .addGroup(jPanelEntradaBancoLayout.createSequentialGroup()
                .addGroup(jPanelEntradaBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelValorBanco)
                    .addComponent(jLabelCodigoBanco))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEntradaBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldCodigoBanco)
                    .addComponent(jFormattedTextFieldValorBanco, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
        );

        jPanelCategoriasBanco.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelCategoriasBanco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelCategoriasBanco.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelCategoriasBanco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelCategoriasBanco.setName("jPanelCategoriasBanco"); // NOI18N

        jToggleButtonBC1.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC1);
        jToggleButtonBC1.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC1.setIcon(resourceMap.getIcon("jToggleButtonBC1.icon")); // NOI18N
        jToggleButtonBC1.setText(resourceMap.getString("jToggleButtonBC1.text")); // NOI18N
        jToggleButtonBC1.setActionCommand(resourceMap.getString("jToggleButtonBC1.actionCommand")); // NOI18N
        jToggleButtonBC1.setName("jToggleButtonBC1"); // NOI18N
        jToggleButtonBC1.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC2.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC2);
        jToggleButtonBC2.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC2.setIcon(resourceMap.getIcon("jToggleButtonBC2.icon")); // NOI18N
        jToggleButtonBC2.setText(resourceMap.getString("jToggleButtonBC2.text")); // NOI18N
        jToggleButtonBC2.setActionCommand(resourceMap.getString("jToggleButtonBC2.actionCommand")); // NOI18N
        jToggleButtonBC2.setName("jToggleButtonBC2"); // NOI18N
        jToggleButtonBC2.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC3.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC3);
        jToggleButtonBC3.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC3.setIcon(resourceMap.getIcon("jToggleButtonBC3.icon")); // NOI18N
        jToggleButtonBC3.setText(resourceMap.getString("jToggleButtonBC3.text")); // NOI18N
        jToggleButtonBC3.setActionCommand(resourceMap.getString("jToggleButtonBC3.actionCommand")); // NOI18N
        jToggleButtonBC3.setName("jToggleButtonBC3"); // NOI18N
        jToggleButtonBC3.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC4.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC4);
        jToggleButtonBC4.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC4.setIcon(resourceMap.getIcon("jToggleButtonBC4.icon")); // NOI18N
        jToggleButtonBC4.setText(resourceMap.getString("jToggleButtonBC4.text")); // NOI18N
        jToggleButtonBC4.setActionCommand(resourceMap.getString("jToggleButtonBC4.actionCommand")); // NOI18N
        jToggleButtonBC4.setName("jToggleButtonBC4"); // NOI18N
        jToggleButtonBC4.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC5.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC5);
        jToggleButtonBC5.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC5.setIcon(resourceMap.getIcon("jToggleButtonBC5.icon")); // NOI18N
        jToggleButtonBC5.setText(resourceMap.getString("jToggleButtonBC5.text")); // NOI18N
        jToggleButtonBC5.setActionCommand(resourceMap.getString("jToggleButtonBC5.actionCommand")); // NOI18N
        jToggleButtonBC5.setName("jToggleButtonBC5"); // NOI18N
        jToggleButtonBC5.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC6.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC6);
        jToggleButtonBC6.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC6.setIcon(resourceMap.getIcon("jToggleButtonBC6.icon")); // NOI18N
        jToggleButtonBC6.setText(resourceMap.getString("jToggleButtonBC6.text")); // NOI18N
        jToggleButtonBC6.setActionCommand(resourceMap.getString("jToggleButtonBC6.actionCommand")); // NOI18N
        jToggleButtonBC6.setName("jToggleButtonBC6"); // NOI18N
        jToggleButtonBC6.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC7.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC7);
        jToggleButtonBC7.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC7.setIcon(resourceMap.getIcon("jToggleButtonBC7.icon")); // NOI18N
        jToggleButtonBC7.setText(resourceMap.getString("jToggleButtonBC7.text")); // NOI18N
        jToggleButtonBC7.setActionCommand(resourceMap.getString("jToggleButtonBC7.actionCommand")); // NOI18N
        jToggleButtonBC7.setName("jToggleButtonBC7"); // NOI18N
        jToggleButtonBC7.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC8.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC8);
        jToggleButtonBC8.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC8.setIcon(resourceMap.getIcon("jToggleButtonBC8.icon")); // NOI18N
        jToggleButtonBC8.setText(resourceMap.getString("jToggleButtonBC8.text")); // NOI18N
        jToggleButtonBC8.setActionCommand(resourceMap.getString("jToggleButtonBC8.actionCommand")); // NOI18N
        jToggleButtonBC8.setName("jToggleButtonBC8"); // NOI18N
        jToggleButtonBC8.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC9.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC9);
        jToggleButtonBC9.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC9.setIcon(resourceMap.getIcon("jToggleButtonBC9.icon")); // NOI18N
        jToggleButtonBC9.setText(resourceMap.getString("jToggleButtonBC9.text")); // NOI18N
        jToggleButtonBC9.setActionCommand(resourceMap.getString("jToggleButtonBC9.actionCommand")); // NOI18N
        jToggleButtonBC9.setName("jToggleButtonBC9"); // NOI18N
        jToggleButtonBC9.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBC0.setAction(actionMap.get("selecionarCategoriaBanco")); // NOI18N
        buttonGroupCategoriasBanco.add(jToggleButtonBC0);
        jToggleButtonBC0.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBC0.setText(resourceMap.getString("jToggleButtonBC0.text")); // NOI18N
        jToggleButtonBC0.setActionCommand(resourceMap.getString("jToggleButtonBC0.actionCommand")); // NOI18N
        jToggleButtonBC0.setName("jToggleButtonBC0"); // NOI18N
        jToggleButtonBC0.setPreferredSize(new java.awt.Dimension(85, 85));

        buttonGroupProdutosBanco.add(jToggleButtonBCFantasma);
        jToggleButtonBCFantasma.setFont(resourceMap.getFont("jToggleButtonBCFantasma.font")); // NOI18N
        jToggleButtonBCFantasma.setName("jToggleButtonBCFantasma"); // NOI18N
        jToggleButtonBCFantasma.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPanelCategoriasBancoLayout = new javax.swing.GroupLayout(jPanelCategoriasBanco);
        jPanelCategoriasBanco.setLayout(jPanelCategoriasBancoLayout);
        jPanelCategoriasBancoLayout.setHorizontalGroup(
            jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCategoriasBancoLayout.createSequentialGroup()
                .addGroup(jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonBC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCategoriasBancoLayout.createSequentialGroup()
                        .addComponent(jToggleButtonBC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jToggleButtonBC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jToggleButtonBC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jToggleButtonBC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCategoriasBancoLayout.createSequentialGroup()
                        .addComponent(jToggleButtonBC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jToggleButtonBC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jToggleButtonBC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jToggleButtonBC0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelCategoriasBancoLayout.createSequentialGroup()
                    .addGap(214, 214, 214)
                    .addComponent(jToggleButtonBCFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(215, Short.MAX_VALUE)))
        );
        jPanelCategoriasBancoLayout.setVerticalGroup(
            jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCategoriasBancoLayout.createSequentialGroup()
                .addGroup(jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonBC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonBC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jToggleButtonBC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButtonBC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButtonBC9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jToggleButtonBC0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(jPanelCategoriasBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelCategoriasBancoLayout.createSequentialGroup()
                    .addGap(85, 85, 85)
                    .addComponent(jToggleButtonBCFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(86, Short.MAX_VALUE)))
        );

        jPaneProdutosBanco.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPaneProdutosBanco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPaneProdutosBanco.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPaneProdutosBanco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPaneProdutosBanco.setName("jPaneProdutosBanco"); // NOI18N
        jPaneProdutosBanco.setPreferredSize(new java.awt.Dimension(441, 201));

        jToggleButtonBP1.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP1);
        jToggleButtonBP1.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP1.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP1.setActionCommand(resourceMap.getString("jToggleButtonBP1.actionCommand")); // NOI18N
        jToggleButtonBP1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP1.setName("jToggleButtonBP1"); // NOI18N
        jToggleButtonBP1.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP2.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP2);
        jToggleButtonBP2.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP2.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP2.setActionCommand(resourceMap.getString("jToggleButtonBP2.actionCommand")); // NOI18N
        jToggleButtonBP2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP2.setName("jToggleButtonBP2"); // NOI18N
        jToggleButtonBP2.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP3.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP3);
        jToggleButtonBP3.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP3.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP3.setActionCommand(resourceMap.getString("jToggleButtonBP3.actionCommand")); // NOI18N
        jToggleButtonBP3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP3.setName("jToggleButtonBP3"); // NOI18N
        jToggleButtonBP3.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP4.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP4);
        jToggleButtonBP4.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP4.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP4.setActionCommand(resourceMap.getString("jToggleButtonBP4.actionCommand")); // NOI18N
        jToggleButtonBP4.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP4.setName("jToggleButtonBP4"); // NOI18N
        jToggleButtonBP4.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP5.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP5);
        jToggleButtonBP5.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP5.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP5.setActionCommand(resourceMap.getString("jToggleButtonBP5.actionCommand")); // NOI18N
        jToggleButtonBP5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP5.setName("jToggleButtonBP5"); // NOI18N
        jToggleButtonBP5.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP6.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP6);
        jToggleButtonBP6.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP6.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP6.setActionCommand(resourceMap.getString("jToggleButtonBP6.actionCommand")); // NOI18N
        jToggleButtonBP6.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP6.setName("jToggleButtonBP6"); // NOI18N
        jToggleButtonBP6.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP7.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP7);
        jToggleButtonBP7.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP7.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP7.setActionCommand(resourceMap.getString("jToggleButtonBP7.actionCommand")); // NOI18N
        jToggleButtonBP7.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP7.setName("jToggleButtonBP7"); // NOI18N
        jToggleButtonBP7.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP8.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP8);
        jToggleButtonBP8.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP8.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP8.setActionCommand(resourceMap.getString("jToggleButtonBP8.actionCommand")); // NOI18N
        jToggleButtonBP8.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP8.setName("jToggleButtonBP8"); // NOI18N
        jToggleButtonBP8.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP9.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP9);
        jToggleButtonBP9.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP9.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP9.setActionCommand(resourceMap.getString("jToggleButtonBP9.actionCommand")); // NOI18N
        jToggleButtonBP9.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP9.setName("jToggleButtonBP9"); // NOI18N
        jToggleButtonBP9.setPreferredSize(new java.awt.Dimension(85, 85));

        jToggleButtonBP0.setAction(actionMap.get("selecionarProdutoBanco")); // NOI18N
        buttonGroupProdutosBanco.add(jToggleButtonBP0);
        jToggleButtonBP0.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBP0.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBP0.setActionCommand(resourceMap.getString("jToggleButtonBP0.actionCommand")); // NOI18N
        jToggleButtonBP0.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jToggleButtonBP0.setName("jToggleButtonBP0"); // NOI18N
        jToggleButtonBP0.setPreferredSize(new java.awt.Dimension(85, 85));

        buttonGroupProdutosBanco.add(jToggleButtonBPFantasma);
        jToggleButtonBPFantasma.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jToggleButtonBPFantasma.setText(resourceMap.getString("jToggleButtonBP1.text")); // NOI18N
        jToggleButtonBPFantasma.setName("jToggleButtonBPFantasma"); // NOI18N
        jToggleButtonBPFantasma.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout jPaneProdutosBancoLayout = new javax.swing.GroupLayout(jPaneProdutosBanco);
        jPaneProdutosBanco.setLayout(jPaneProdutosBancoLayout);
        jPaneProdutosBancoLayout.setHorizontalGroup(
            jPaneProdutosBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneProdutosBancoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButtonBPFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPaneProdutosBancoLayout.createSequentialGroup()
                .addComponent(jToggleButtonBP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonBP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonBP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonBP4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonBP5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPaneProdutosBancoLayout.createSequentialGroup()
                .addComponent(jToggleButtonBP6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonBP7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonBP8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonBP9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jToggleButtonBP0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPaneProdutosBancoLayout.setVerticalGroup(
            jPaneProdutosBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPaneProdutosBancoLayout.createSequentialGroup()
                .addGroup(jPaneProdutosBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToggleButtonBP4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBP5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPaneProdutosBancoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButtonBP6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBP7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBP8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBP9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBP0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonBPFantasma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanelDados.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelDados.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelDados.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelDados.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelDados.setName("jPanelDados"); // NOI18N

        jLabelLojaBanco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelLojaBanco.setText(resourceMap.getString("jLabelLojaBanco.text")); // NOI18N
        jLabelLojaBanco.setName("jLabelLojaBanco"); // NOI18N

        jComboBoxLojas.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jComboBoxLojas.setModel(comboBoxModelLojas);
        jComboBoxLojas.setToolTipText(resourceMap.getString("jComboBoxLojas.toolTipText")); // NOI18N
        jComboBoxLojas.setName("jComboBoxLojas"); // NOI18N
        jComboBoxLojas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLojasActionPerformed(evt);
            }
        });

        jButtonAdicionarLoja.setAction(actionMap.get("showAdicionarLoja")); // NOI18N
        jButtonAdicionarLoja.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonAdicionarLoja.setText(resourceMap.getString("jButtonAdicionarLoja.text")); // NOI18N
        jButtonAdicionarLoja.setName("jButtonAdicionarLoja"); // NOI18N

        jButtonRemoverLoja.setAction(actionMap.get("removerLoja")); // NOI18N
        jButtonRemoverLoja.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonRemoverLoja.setText(resourceMap.getString("jButtonRemoverLoja.text")); // NOI18N
        jButtonRemoverLoja.setName("jButtonRemoverLoja"); // NOI18N

        jLabelFuncionariosBanco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelFuncionariosBanco.setText(resourceMap.getString("jLabelFuncionariosBanco.text")); // NOI18N
        jLabelFuncionariosBanco.setName("jLabelFuncionariosBanco"); // NOI18N

        jScrollPaneFuncionariosBanco.setName("jScrollPaneFuncionariosBanco"); // NOI18N

        jListFuncionariosBanco.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jListFuncionariosBanco.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jListFuncionariosBanco.setModel(listModelFuncionariosBanco);
        jListFuncionariosBanco.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListFuncionariosBanco.setToolTipText(resourceMap.getString("jListFuncionariosBanco.toolTipText")); // NOI18N
        jListFuncionariosBanco.setName("jListFuncionariosBanco"); // NOI18N
        jScrollPaneFuncionariosBanco.setViewportView(jListFuncionariosBanco);

        jButtonAdicionarFuncionarios.setAction(actionMap.get("showAdicionarFuncionario")); // NOI18N
        jButtonAdicionarFuncionarios.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonAdicionarFuncionarios.setText(resourceMap.getString("jButtonAdicionarFuncionarios.text")); // NOI18N
        jButtonAdicionarFuncionarios.setName("jButtonAdicionarFuncionarios"); // NOI18N

        jButtonRemoverFuncionarios.setAction(actionMap.get("removerFuncionario")); // NOI18N
        jButtonRemoverFuncionarios.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonRemoverFuncionarios.setText(resourceMap.getString("jButtonRemoverFuncionarios.text")); // NOI18N
        jButtonRemoverFuncionarios.setName("jButtonRemoverFuncionarios"); // NOI18N

        javax.swing.GroupLayout jPanelDadosLayout = new javax.swing.GroupLayout(jPanelDados);
        jPanelDados.setLayout(jPanelDadosLayout);
        jPanelDadosLayout.setHorizontalGroup(
            jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneFuncionariosBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxLojas, 0, 228, Short.MAX_VALUE)
                    .addComponent(jLabelLojaBanco)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jButtonAdicionarLoja)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoverLoja))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jLabelFuncionariosBanco)
                        .addGap(147, 147, 147))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosLayout.createSequentialGroup()
                        .addComponent(jButtonAdicionarFuncionarios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoverFuncionarios)))
                .addContainerGap())
        );
        jPanelDadosLayout.setVerticalGroup(
            jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosLayout.createSequentialGroup()
                .addComponent(jLabelLojaBanco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxLojas, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRemoverLoja)
                    .addComponent(jButtonAdicionarLoja))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelFuncionariosBanco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneFuncionariosBanco, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRemoverFuncionarios)
                    .addComponent(jButtonAdicionarFuncionarios)))
        );

        jPanelSenha.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        jPanelSenha.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanelSenha.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        jPanelSenha.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelSenha.setName("jPanelSenha"); // NOI18N

        jLabelSenhaAtual.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelSenhaAtual.setText(resourceMap.getString("jLabelSenhaAtual.text")); // NOI18N
        jLabelSenhaAtual.setName("jLabelSenhaAtual"); // NOI18N

        jLabelSenhaNova.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelSenhaNova.setText(resourceMap.getString("jLabelSenhaNova.text")); // NOI18N
        jLabelSenhaNova.setName("jLabelSenhaNova"); // NOI18N

        jLabelSenhaRepetir.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jLabelSenhaRepetir.setText(resourceMap.getString("jLabelSenhaRepetir.text")); // NOI18N
        jLabelSenhaRepetir.setName("jLabelSenhaRepetir"); // NOI18N

        jPasswordFieldSenhaAtual.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jPasswordFieldSenhaAtual.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPasswordFieldSenhaAtual.setName("jPasswordFieldSenhaAtual"); // NOI18N

        jPasswordFieldSenhaNova.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jPasswordFieldSenhaNova.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPasswordFieldSenhaNova.setName("jPasswordFieldSenhaNova"); // NOI18N

        jPasswordFieldSenhaRepetir.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        jPasswordFieldSenhaRepetir.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPasswordFieldSenhaRepetir.setName("jPasswordFieldSenhaRepetir"); // NOI18N

        jButtonAlterar.setAction(actionMap.get("alterarSenha")); // NOI18N
        jButtonAlterar.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jButtonAlterar.setText(resourceMap.getString("jButtonAlterar.text")); // NOI18N
        jButtonAlterar.setName("jButtonAlterar"); // NOI18N

        javax.swing.GroupLayout jPanelSenhaLayout = new javax.swing.GroupLayout(jPanelSenha);
        jPanelSenha.setLayout(jPanelSenhaLayout);
        jPanelSenhaLayout.setHorizontalGroup(
            jPanelSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSenhaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSenhaLayout.createSequentialGroup()
                        .addGroup(jPanelSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelSenhaRepetir)
                            .addComponent(jLabelSenhaNova)
                            .addComponent(jLabelSenhaAtual))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordFieldSenhaRepetir, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jPasswordFieldSenhaNova, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jPasswordFieldSenhaAtual, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))
                    .addComponent(jButtonAlterar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanelSenhaLayout.setVerticalGroup(
            jPanelSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSenhaLayout.createSequentialGroup()
                .addGroup(jPanelSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSenhaAtual)
                    .addComponent(jPasswordFieldSenhaAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSenhaNova)
                    .addComponent(jPasswordFieldSenhaNova, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSenhaRepetir)
                    .addComponent(jPasswordFieldSenhaRepetir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAlterar))
        );

        javax.swing.GroupLayout jPanelCadastramentoLayout = new javax.swing.GroupLayout(jPanelCadastramento);
        jPanelCadastramento.setLayout(jPanelCadastramentoLayout);
        jPanelCadastramentoLayout.setHorizontalGroup(
            jPanelCadastramentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadastramentoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelCadastramentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanelEntradaBanco, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelCadastramentoLayout.createSequentialGroup()
                        .addGroup(jPanelCadastramentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPaneProdutosBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelCategoriasBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanelCadastramentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(177, Short.MAX_VALUE))
        );
        jPanelCadastramentoLayout.setVerticalGroup(
            jPanelCadastramentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadastramentoLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanelEntradaBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanelCadastramentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCadastramentoLayout.createSequentialGroup()
                        .addComponent(jPanelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCadastramentoLayout.createSequentialGroup()
                        .addComponent(jPanelCategoriasBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jPaneProdutosBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane.addTab(resourceMap.getString("jPanelCadastramento.TabConstraints.tabTitle"), jPanelCadastramento); // NOI18N

        jPanelRelatorios.setBackground(resourceMap.getColor("CorNivel2")); // NOI18N
        jPanelRelatorios.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        jPanelRelatorios.setName("jPanelRelatorios"); // NOI18N

        javax.swing.GroupLayout jPanelRelatoriosLayout = new javax.swing.GroupLayout(jPanelRelatorios);
        jPanelRelatorios.setLayout(jPanelRelatoriosLayout);
        jPanelRelatoriosLayout.setHorizontalGroup(
            jPanelRelatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 894, Short.MAX_VALUE)
        );
        jPanelRelatoriosLayout.setVerticalGroup(
            jPanelRelatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );

        jTabbedPane.addTab(resourceMap.getString("jPanelRelatorios.TabConstraints.tabTitle"), jPanelRelatorios); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setMnemonic('a');
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setFont(resourceMap.getFont("fileMenu.font")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("sair")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setMnemonic('f');
        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jCheckBoxAdministrador.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jCheckBoxAdministrador.setSelected(true);
        jCheckBoxAdministrador.setText(resourceMap.getString("jCheckBoxAdministrador.text")); // NOI18N
        jCheckBoxAdministrador.setName("jCheckBoxAdministrador"); // NOI18N
        jCheckBoxAdministrador.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxAdministradorItemStateChanged(evt);
            }
        });
        jMenu1.add(jCheckBoxAdministrador);

        jMenuItem2.setAction(actionMap.get("exportarBanco")); // NOI18N
        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenu1.add(jMenuItem2);

        menuBar.add(jMenu1);

        helpMenu.setMnemonic('j');
        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 992, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxLojasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLojasActionPerformed
        JComboBox cb = ( JComboBox ) evt.getSource();
        String nome = ( String ) cb.getSelectedItem();
        alterarLoja( nome );
    }//GEN-LAST:event_jComboBoxLojasActionPerformed

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
//        JTabbedPane tabbedPane = ( JTabbedPane ) evt.getSource();
//        JPanel panel = ( JPanel ) tabbedPane.getSelectedComponent();
        // Se a aba jPanelFrente ("Frente de loja") for selecionada, dar foco
        // no campo jFormattedTextFieldCodigo ("Código"):
//        if ( panel.getName().equals( jPanelFrente.getName() ) )
//            jTextFieldCodigoFrente.grabFocus();
    }//GEN-LAST:event_jTabbedPaneStateChanged

    /**
     * Atualiza o campo <i>Valor</i> a medida em que a informação no campo
     * <i>Qtde</i> for sendo digitada.
     *
     * @param evt
     */
    private void jFormattedTextFieldQtdeFrenteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldQtdeFrenteKeyReleased
        try
        {
            String strTecla = Character.toString( evt.getKeyChar() );
            int intCodigoTecla = evt.getKeyCode();
            boolean houveDelecao = (intCodigoTecla == KeyEvent.VK_BACK_SPACE || intCodigoTecla == KeyEvent.VK_DELETE);

            // Se for alguma das teclas de atalho do programa:
            if ( strTecla.matches( "[\\*\\+\\-\\/]" ) || intCodigoTecla == KeyEvent.VK_ESCAPE )
            {
                jTextFieldCodigoFrente.setText( "" );
                keyReleased( evt );
                jTextFieldCodigoFrente.grabFocus();
            }
            // Processa apenas a tecla que gera algum caractere:
            else if ( strTecla.matches( "\\p{Print}" ) || houveDelecao )
            {
                if ( strTecla.matches( "[0-9\\,]" ) || houveDelecao )
                {
                    String strQtde = jFormattedTextFieldQtdeFrente.getText();
                    String strValor = jFormattedTextFieldValorFrente.getText();

                    //if ( !strQtde.isEmpty() && !strValor.isEmpty() )
                    if ( !strValor.isEmpty() )
                    {
                        float qtde;
                        if ( strQtde.isEmpty() )
                            qtde = 0;
                        else
                            qtde = nf3d.parse( strQtde ).floatValue();
                        float valor = nfMoeda.parse( strValor ).floatValue();
                        float total = qtde * valor;
                        jFormattedTextFieldTotalFrente.setText( nfMoeda.format( total ) );
                        jButtonConfirmar.setEnabled( true );
                    }
                }
                else if ( strTecla.matches( "\\." ) )
                {
                    JOptionPane.showMessageDialog( jPanelFrente,
                        "O separador decimal é a vírgula, e não o ponto.",
                        "Definição do pedido",
                        JOptionPane.WARNING_MESSAGE );
                    jFormattedTextFieldQtdeFrente.setText( "" );
                }
                else
                {
                    JOptionPane.showMessageDialog( jPanelFrente,
                        "O campo \"Qtde\" deve receber apenas números.",
                        "Definição do pedido",
                        JOptionPane.WARNING_MESSAGE );
                    jFormattedTextFieldQtdeFrente.setText( "" );
                }

            }
            else if ( intCodigoTecla == KeyEvent.VK_BACK_SPACE || intCodigoTecla == KeyEvent.VK_DELETE )
            {
            }
        }
        catch ( ParseException pe )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, pe );
        }
        finally
        {
            if ( jFormattedTextFieldQtdeFrente.getText().isEmpty() )
                jButtonConfirmar.setEnabled( false );
        }
    }//GEN-LAST:event_jFormattedTextFieldQtdeFrenteKeyReleased

    /**
     * Evento disparado após "Enter" no campo "Quantidade"
     *
     * @param evt
     */
    private void jFormattedTextFieldQtdeFrenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldQtdeFrenteActionPerformed
        adicionarPedido();
    }//GEN-LAST:event_jFormattedTextFieldQtdeFrenteActionPerformed

    private void jFormattedTextFieldValorBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextFieldValorBancoActionPerformed
        ajustarCampoParaMoeda( evt );
        jTextFieldProdutoBanco.requestFocus();
        jTextFieldProdutoBanco.selectAll();
    }//GEN-LAST:event_jFormattedTextFieldValorBancoActionPerformed

    private void jFormattedTextFieldValorBancoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextFieldValorBancoFocusGained
        try
        {
            if ( !jFormattedTextFieldValorBanco.getText().isEmpty() )
                jFormattedTextFieldValorBanco.setText(
                    nf2d.format(
                    nfMoeda.parse(
                    jFormattedTextFieldValorBanco.getText() ) ) );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.OFF, null, ex );
        }
        finally
        {
            jFormattedTextFieldValorBanco.selectAll();
        }
    }//GEN-LAST:event_jFormattedTextFieldValorBancoFocusGained

    private void jTextFieldProdutoBancoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProdutoBancoActionPerformed
        adicionarProduto();
    }//GEN-LAST:event_jTextFieldProdutoBancoActionPerformed

    private void jFormattedTextFieldCaixaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextFieldCaixaFocusLost
        ajustarCampoParaMoeda( evt );
    }//GEN-LAST:event_jFormattedTextFieldCaixaFocusLost

    private void jFormattedTextFieldValorBancoFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jFormattedTextFieldValorBancoFocusLost
    {//GEN-HEADEREND:event_jFormattedTextFieldValorBancoFocusLost
        ajustarCampoParaMoeda( evt );
        //jFormattedTextFieldValorBanco.setEnabled( false );
    }//GEN-LAST:event_jFormattedTextFieldValorBancoFocusLost

    /**
     * Se o <n>Modo Administrador</n> for ativado, habilita todas as abas. Se
     * for desativado e não existir jornada aberta, habilita apenas a aba
     * <n>Abertura de caixa</n>. Se existir jornada aberta, habilita também a
     * aba <n>Frente de loja</n>.
     *
     * @param evt ChangeEvent
     */
    private void jTextFieldClienteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextFieldClienteActionPerformed
    {//GEN-HEADEREND:event_jTextFieldClienteActionPerformed
        alterarNomeCliente( jTextFieldCliente.getText() );
    }//GEN-LAST:event_jTextFieldClienteActionPerformed

    private void jToggleButtonComanda01MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda01MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda01MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda01MouseClicked

    private void jToggleButtonComanda02MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda02MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda02MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda02MouseClicked

    private void jToggleButtonComanda03MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda03MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda03MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda03MouseClicked

    private void jToggleButtonComanda05MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda05MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda05MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda05MouseClicked

    private void jToggleButtonComanda06MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda06MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda06MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda06MouseClicked

    private void jToggleButtonComanda07MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda07MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda07MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda07MouseClicked

    private void jToggleButtonComanda08MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda08MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda08MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda08MouseClicked

    private void jToggleButtonComanda09MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda09MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda09MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda09MouseClicked

    private void jToggleButtonComanda10MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda10MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda10MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda10MouseClicked

    private void jToggleButtonComanda11MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda11MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda11MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda11MouseClicked

    private void jToggleButtonComanda12MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda12MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda12MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda12MouseClicked

    private void jToggleButtonComanda13MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda13MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda13MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda13MouseClicked

    private void jToggleButtonComanda14MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda14MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda14MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda14MouseClicked

    private void jToggleButtonComanda15MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda15MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda15MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda15MouseClicked

    private void tratarComandaMouseClicked( MouseEvent evt )
    {
        JToggleButton jtb = ( JToggleButton ) evt.getSource();
        int posicao = Integer.parseInt( jtb.getActionCommand() );
        carregarComanda( posicao );
    }

    private void jPanelFrenteFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jPanelFrenteFocusGained
    {//GEN-HEADEREND:event_jPanelFrenteFocusGained
        jTextFieldCodigoFrente.grabFocus();
    }//GEN-LAST:event_jPanelFrenteFocusGained

    private void jTextFieldCodigoFrenteFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jTextFieldCodigoFrenteFocusGained
    {//GEN-HEADEREND:event_jTextFieldCodigoFrenteFocusGained
        limparCamposFrente();
        limparBotoesFrente();
        buttonGroupCategoriasFrente.setSelected( jToggleButtonFCFantasma.getModel(), true );
    }//GEN-LAST:event_jTextFieldCodigoFrenteFocusGained

    private void jTextFieldCodigoFrenteKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldCodigoFrenteKeyReleased
    {//GEN-HEADEREND:event_jTextFieldCodigoFrenteKeyReleased
        tratarCodigoKeyReleased( evt, 1 );
    }//GEN-LAST:event_jTextFieldCodigoFrenteKeyReleased

    /**
     * Tratamento do evendo KeyReleased no campo código das telas
     * "Frente de loja" e "Cadastramento".
     * Dispara evento que trata da análise do código digitado em uma thread
     * separada para evitar problemas por tempo de processamento.
     *
     * @param evt
     * @param origem (1 - tela Frente de loja, 2 - tela Cadastramento)
     */
    private void tratarCodigoKeyReleased( KeyEvent evt, int origem )
    {
        Object args[] = new Object[2];
        args[0] = evt;
        args[1] = origem;
        Method metodo;
        Class tipos[] = new Class[2];
        tipos[0] = KeyEvent.class;
        tipos[1] = Integer.TYPE;

        try
        {
            metodo = this.getClass().getMethod( "analisarCodigo", tipos );

            ThreadCreator novaThread = new ThreadCreator( this, metodo, args );
            ExecutorService executor = Executors.newCachedThreadPool();
            executor.execute( novaThread );
            executor.shutdown();
        }
        catch ( NoSuchMethodException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( SecurityException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }

    private void jTextFieldCodigoFrenteFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jTextFieldCodigoFrenteFocusLost
    {//GEN-HEADEREND:event_jTextFieldCodigoFrenteFocusLost
        if ( jTextFieldCodigoFrente.getText().length() != 2 )
            jFormattedTextFieldQtdeFrente.setEnabled( false );
    }//GEN-LAST:event_jTextFieldCodigoFrenteFocusLost

    private void jFormattedTextFieldCaixaActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jFormattedTextFieldCaixaActionPerformed
    {//GEN-HEADEREND:event_jFormattedTextFieldCaixaActionPerformed
        ajustarCampoParaMoeda( evt );
        //jButtonCaixa.grabFocus();
        abrirCaixa();
    }//GEN-LAST:event_jFormattedTextFieldCaixaActionPerformed

    private void jCheckBoxAdministradorItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_jCheckBoxAdministradorItemStateChanged
    {//GEN-HEADEREND:event_jCheckBoxAdministradorItemStateChanged
        if ( jCheckBoxAdministrador.getState() == true )
        {
            try
            {
                if ( bd.existeJornadaAbertaBD() )
                {
                    JOptionPane.showMessageDialog( jPanelSenha,
                        "É necessário fechar o caixa antes de entrar no Modo Administrador.",
                        "Mudança para o Modo Administrador",
                        JOptionPane.WARNING_MESSAGE );
                    jCheckBoxAdministrador.setState( false );
                }
                else
                {
                    JLabel label = new JLabel( "Insira a senha:" );
                    JPasswordField jpf = new JPasswordField();
                    JOptionPane.showConfirmDialog( null,
                        new Object[]
                        {
                            label, jpf
                        },
                        "Mudança para o Modo Administrador",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE );
                           
                    if( jpf.getPassword().length != 0 )
                    {
                        if ( bd.eSenhaValidaBD( new String( jpf.getPassword() ) ) )
                        {
                            // Não possibilitar acesso do "frente de loja" nem
                            // "Abertura de caixa" no modo administrador:
                            jTabbedPane.setEnabledAt( 0, false);
                            jTabbedPane.setEnabledAt( 1, false);
                            jTabbedPane.setEnabledAt( 2, true);
                            jTabbedPane.setEnabledAt( 3, true);
                            jTabbedPane.setSelectedComponent( jPanelCadastramento );
                        }
                        else
                        {
                            JOptionPane.showMessageDialog( jPanelSenha,
                                "Senha inválida.",
                                "Mudança para o Modo Administrador",
                                JOptionPane.WARNING_MESSAGE );
                            jCheckBoxAdministrador.setState( false );
                        }
                    }
                    else
                        jCheckBoxAdministrador.setState( false );
                }
            }
            catch ( SQLException ex )
            {
                Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
                JOptionPane.showMessageDialog( jPanelSenha,
                    "Não foi possível verificar validade da senha. Esta operação foi cancelada.",
                    "Mudança para o Modo Administrador",
                    JOptionPane.WARNING_MESSAGE );
                jCheckBoxAdministrador.setState( false );
            }
        }
        else
        {
            jTabbedPane.setEnabledAt( 0, true );
            try
            {
                if ( bd.existeJornadaAbertaBD() == true )
                {
                    jTabbedPane.setEnabledAt( 1, true );
                    jTabbedPane.setSelectedComponent( jPanelFrente );
                }
                else
                {
                    jTabbedPane.setEnabledAt( 1, false );
                    jTabbedPane.setSelectedComponent( jPanelAbertura );
                }
            }
            catch ( SQLException se )
            {
                Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
                jTabbedPane.setEnabledAt( 1, false );
                jTabbedPane.setSelectedComponent( jPanelAbertura );
            }
            finally
            {
                for ( int tab = 2; tab < jTabbedPane.getTabCount(); tab++ )
                    jTabbedPane.setEnabledAt( tab, false );
            }
        }
    }//GEN-LAST:event_jCheckBoxAdministradorItemStateChanged

    private void jFormattedTextFieldQtdeFrenteFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jFormattedTextFieldQtdeFrenteFocusGained
    {//GEN-HEADEREND:event_jFormattedTextFieldQtdeFrenteFocusGained
        String strQtde = jFormattedTextFieldQtdeFrente.getText();
        String strValor = jFormattedTextFieldValorFrente.getText();

        if ( !strValor.isEmpty() )
        {
            try
            {
                float qtde;
                if ( strQtde.isEmpty() )
                    qtde = 0;
                else
                    qtde = nf3d.parse( strQtde ).floatValue();
                float valor = nfMoeda.parse( strValor ).floatValue();
                float total = qtde * valor;
                jFormattedTextFieldTotalFrente.setText( nfMoeda.format( total ) );
                jButtonConfirmar.setEnabled( true );
            }
            catch ( ParseException ex )
            {
                Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, ex );
            }
        }
    }//GEN-LAST:event_jFormattedTextFieldQtdeFrenteFocusGained

    private void jToggleButtonComanda04MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jToggleButtonComanda04MouseClicked
    {//GEN-HEADEREND:event_jToggleButtonComanda04MouseClicked
        tratarComandaMouseClicked( evt );
    }//GEN-LAST:event_jToggleButtonComanda04MouseClicked

    private void jTextFieldCodigoBancoFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jTextFieldCodigoBancoFocusGained
    {//GEN-HEADEREND:event_jTextFieldCodigoBancoFocusGained
        limparCamposBanco();
        limparBotoesBanco();
        buttonGroupCategoriasBanco.setSelected( jToggleButtonBCFantasma.getModel(), true );
    }//GEN-LAST:event_jTextFieldCodigoBancoFocusGained

    private void jTextFieldCodigoBancoFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_jTextFieldCodigoBancoFocusLost
    {//GEN-HEADEREND:event_jTextFieldCodigoBancoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCodigoBancoFocusLost

    private void jTextFieldCodigoBancoKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldCodigoBancoKeyReleased
    {//GEN-HEADEREND:event_jTextFieldCodigoBancoKeyReleased
        tratarCodigoKeyReleased( evt, 2 );
    }//GEN-LAST:event_jTextFieldCodigoBancoKeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupCategoriasBanco;
    private javax.swing.ButtonGroup buttonGroupCategoriasFrente;
    private javax.swing.ButtonGroup buttonGroupClientes;
    private javax.swing.ButtonGroup buttonGroupProdutosBanco;
    private javax.swing.ButtonGroup buttonGroupProdutosFrente;
    private javax.swing.ButtonGroup buttonGroupTempo;
    private javax.swing.JButton jButtonAbastec;
    private javax.swing.JButton jButtonAdicionarEntradaBanco;
    private javax.swing.JButton jButtonAdicionarFuncionarios;
    private javax.swing.JButton jButtonAdicionarLoja;
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonCaixa;
    private javax.swing.JButton jButtonComandas;
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JButton jButtonDespesas;
    private javax.swing.JButton jButtonExcluirItem;
    private javax.swing.JButton jButtonFechamento;
    private javax.swing.JButton jButtonFinalizar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonJuntar;
    private javax.swing.JButton jButtonReforco;
    private javax.swing.JButton jButtonRemoverEntradaBanco;
    private javax.swing.JButton jButtonRemoverFuncionarios;
    private javax.swing.JButton jButtonRemoverLoja;
    private javax.swing.JButton jButtonSangria;
    private javax.swing.JButton jButtonSelecionar;
    private javax.swing.JButton jButtonSeparar;
    private javax.swing.JCheckBoxMenuItem jCheckBoxAdministrador;
    private javax.swing.JComboBox jComboBoxLojas;
    private javax.swing.JComboBox jComboBoxTurno;
    private javax.swing.JFormattedTextField jFormattedTextFieldCaixa;
    private javax.swing.JFormattedTextField jFormattedTextFieldQtdeFrente;
    private javax.swing.JFormattedTextField jFormattedTextFieldTotalFrente;
    private javax.swing.JFormattedTextField jFormattedTextFieldValorBanco;
    private javax.swing.JFormattedTextField jFormattedTextFieldValorFrente;
    private javax.swing.JLabel jLabelCliente;
    private javax.swing.JLabel jLabelCodigo;
    private javax.swing.JLabel jLabelCodigoBanco;
    private javax.swing.JLabel jLabelData;
    private javax.swing.JLabel jLabelDinheiro;
    private javax.swing.JLabel jLabelFuncionarios;
    private javax.swing.JLabel jLabelFuncionariosBanco;
    private javax.swing.JLabel jLabelHora;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JLabel jLabelLoja;
    private javax.swing.JLabel jLabelLojaBanco;
    private javax.swing.JLabel jLabelProduto;
    private javax.swing.JLabel jLabelProdutoBanco;
    private javax.swing.JLabel jLabelQtde;
    private javax.swing.JLabel jLabelSemana;
    private javax.swing.JLabel jLabelSenhaAtual;
    private javax.swing.JLabel jLabelSenhaNova;
    private javax.swing.JLabel jLabelSenhaRepetir;
    private javax.swing.JLabel jLabelTotal;
    private javax.swing.JLabel jLabelTotalComanda;
    private javax.swing.JLabel jLabelTurno;
    private javax.swing.JLabel jLabelValor;
    private javax.swing.JLabel jLabelValorBanco;
    private javax.swing.JList jListFuncionariosAbertura;
    private javax.swing.JList jListFuncionariosBanco;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPaneProdutosBanco;
    private javax.swing.JPanel jPanelAbertura;
    private javax.swing.JPanel jPanelCadastramento;
    private javax.swing.JPanel jPanelCategoriasBanco;
    private javax.swing.JPanel jPanelCategoriasFrente;
    private javax.swing.JPanel jPanelClientes;
    private javax.swing.JPanel jPanelComanda;
    private javax.swing.JPanel jPanelDados;
    private javax.swing.JPanel jPanelEntradaBanco;
    private javax.swing.JPanel jPanelEntradaFrente;
    private javax.swing.JPanel jPanelFrente;
    private javax.swing.JPanel jPanelJornada;
    private javax.swing.JPanel jPanelProdutosFrente;
    private javax.swing.JPanel jPanelRelatorios;
    private javax.swing.JPanel jPanelSenha;
    private javax.swing.JPanel jPanelTempo;
    private javax.swing.JPasswordField jPasswordFieldSenhaAtual;
    private javax.swing.JPasswordField jPasswordFieldSenhaNova;
    private javax.swing.JPasswordField jPasswordFieldSenhaRepetir;
    private javax.swing.JScrollPane jScrollPaneComanda;
    private javax.swing.JScrollPane jScrollPaneFuncionariosAbertura;
    private javax.swing.JScrollPane jScrollPaneFuncionariosBanco;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableComanda;
    private javax.swing.JTextField jTextFieldCliente;
    private javax.swing.JTextField jTextFieldCodigoBanco;
    private javax.swing.JTextField jTextFieldCodigoFrente;
    private javax.swing.JTextField jTextFieldData;
    private javax.swing.JTextField jTextFieldHora;
    private javax.swing.JTextField jTextFieldLoja;
    private javax.swing.JTextField jTextFieldProdutoBanco;
    private javax.swing.JTextField jTextFieldProdutoFrente;
    private javax.swing.JTextField jTextFieldSemana;
    private javax.swing.JTextField jTextFieldTotalComanda;
    private javax.swing.JToggleButton jToggleButtonBC0;
    private javax.swing.JToggleButton jToggleButtonBC1;
    private javax.swing.JToggleButton jToggleButtonBC2;
    private javax.swing.JToggleButton jToggleButtonBC3;
    private javax.swing.JToggleButton jToggleButtonBC4;
    private javax.swing.JToggleButton jToggleButtonBC5;
    private javax.swing.JToggleButton jToggleButtonBC6;
    private javax.swing.JToggleButton jToggleButtonBC7;
    private javax.swing.JToggleButton jToggleButtonBC8;
    private javax.swing.JToggleButton jToggleButtonBC9;
    private javax.swing.JToggleButton jToggleButtonBCFantasma;
    private javax.swing.JToggleButton jToggleButtonBP0;
    private javax.swing.JToggleButton jToggleButtonBP1;
    private javax.swing.JToggleButton jToggleButtonBP2;
    private javax.swing.JToggleButton jToggleButtonBP3;
    private javax.swing.JToggleButton jToggleButtonBP4;
    private javax.swing.JToggleButton jToggleButtonBP5;
    private javax.swing.JToggleButton jToggleButtonBP6;
    private javax.swing.JToggleButton jToggleButtonBP7;
    private javax.swing.JToggleButton jToggleButtonBP8;
    private javax.swing.JToggleButton jToggleButtonBP9;
    private javax.swing.JToggleButton jToggleButtonBPFantasma;
    private javax.swing.JToggleButton jToggleButtonChuvoso;
    private javax.swing.JToggleButton jToggleButtonComanda01;
    private javax.swing.JToggleButton jToggleButtonComanda02;
    private javax.swing.JToggleButton jToggleButtonComanda03;
    private javax.swing.JToggleButton jToggleButtonComanda04;
    private javax.swing.JToggleButton jToggleButtonComanda05;
    private javax.swing.JToggleButton jToggleButtonComanda06;
    private javax.swing.JToggleButton jToggleButtonComanda07;
    private javax.swing.JToggleButton jToggleButtonComanda08;
    private javax.swing.JToggleButton jToggleButtonComanda09;
    private javax.swing.JToggleButton jToggleButtonComanda10;
    private javax.swing.JToggleButton jToggleButtonComanda11;
    private javax.swing.JToggleButton jToggleButtonComanda12;
    private javax.swing.JToggleButton jToggleButtonComanda13;
    private javax.swing.JToggleButton jToggleButtonComanda14;
    private javax.swing.JToggleButton jToggleButtonComanda15;
    private javax.swing.JToggleButton jToggleButtonComandaFantasma;
    private javax.swing.JToggleButton jToggleButtonEnsolarado;
    private javax.swing.JToggleButton jToggleButtonFC0;
    private javax.swing.JToggleButton jToggleButtonFC1;
    private javax.swing.JToggleButton jToggleButtonFC2;
    private javax.swing.JToggleButton jToggleButtonFC3;
    private javax.swing.JToggleButton jToggleButtonFC4;
    private javax.swing.JToggleButton jToggleButtonFC5;
    private javax.swing.JToggleButton jToggleButtonFC6;
    private javax.swing.JToggleButton jToggleButtonFC7;
    private javax.swing.JToggleButton jToggleButtonFC8;
    private javax.swing.JToggleButton jToggleButtonFC9;
    private javax.swing.JToggleButton jToggleButtonFCFantasma;
    private javax.swing.JToggleButton jToggleButtonFP0;
    private javax.swing.JToggleButton jToggleButtonFP1;
    private javax.swing.JToggleButton jToggleButtonFP2;
    private javax.swing.JToggleButton jToggleButtonFP3;
    private javax.swing.JToggleButton jToggleButtonFP4;
    private javax.swing.JToggleButton jToggleButtonFP5;
    private javax.swing.JToggleButton jToggleButtonFP6;
    private javax.swing.JToggleButton jToggleButtonFP7;
    private javax.swing.JToggleButton jToggleButtonFP8;
    private javax.swing.JToggleButton jToggleButtonFP9;
    private javax.swing.JToggleButton jToggleButtonFPFantasma;
    private javax.swing.JToggleButton jToggleButtonInstavel;
    private javax.swing.JToggleButton jToggleButtonNublado;
    private javax.swing.JToggleButton jToggleButtonTempoFantasma;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
//    private final Timer messageTimer;
//    private final Timer busyIconTimer;
//    private final Icon idleIcon;
//    private final Icon[] busyIcons = new Icon[15];
//    private int busyIconIndex = 0;
    private JDialog aboutBox;

    /**
     * Implementação dos métodos da interface KeyListener
     * 
     * @param e
     */

    public void keyTyped( KeyEvent e )
    {
    }

    public void keyPressed( KeyEvent e )
    {
        //throw new UnsupportedOperationException( "Not supported yet." );
    }

    public void keyReleased( KeyEvent e )
    {
//        jTextFieldCodigoFrenteFocusGained( null );
//        codigoCategoria = "";

        if ( jTabbedPane.getSelectedComponent() == jPanelFrente )
        {
            if ( e.getKeyCode() == KeyEvent.VK_ESCAPE )
            {
//                codigoCategoria = "";
//                jTextFieldCodigoFrente.setText( "" );
                jTextFieldCodigoFrente.grabFocus();
            }
            else if ( e.getKeyChar() == '+' )
                unirComandaAtiva();
            else if ( e.getKeyChar() == '*' )
                selecionarComanda();
            else if ( e.getKeyChar() == '/' )
                guardarComanda();
            else if ( e.getKeyChar() == '-' )
                fecharComanda();
            else if ( e.getKeyCode() == KeyEvent.VK_F11 )
            {
                solicitarAbastecimento();
            }
        }
    }
}

/* JList contendo elementos JCheckBox
 * ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯
 * Descr: "Represents items in the list that can be selected"
 * Fonte: http://helpdesk.objects.com.au/java/how-do-add-a-checkbox-to-items-in-a-jlist
 */
class CheckListItem
    extends Component
{
    private String label;
    private boolean isSelected = false;

    public CheckListItem( String label )
    {
        this.label = label;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected( boolean isSelected )
    {
        this.isSelected = isSelected;
    }

    @Override
    public String toString()
    {
        return label;
    }
}

/* JList contendo elementos JCheckBox
 * ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯
 * Descr: "Handles rendering cells in the list using a check box"
 * Fonte: http://helpdesk.objects.com.au/java/how-do-add-a-checkbox-to-items-in-a-jlist
 */
//class CheckListRenderer
//    extends JCheckBox
//    implements ListCellRenderer
//{
//    public Component getListCellRendererComponent(
//        JList list, Object value, int index,
//        boolean isSelected, boolean hasFocus )
//    {
//        setEnabled( list.isEnabled() );
//        setSelected( (( CheckListItem ) value).isSelected() );
//        setFont( list.getFont() );
//        setBackground( list.getBackground() );
//        setForeground( list.getForeground() );
//        setText( value.toString() );
//        return this;
//    }
//}

