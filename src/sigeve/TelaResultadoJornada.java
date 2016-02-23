/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TelaResultadoJornada.java
 *
 * Created on 05/11/2010, 11:34:22
 */

package sigeve;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import org.jdesktop.application.Action;

/**
 *
 * @author Paulo Henrique
 */
public class TelaResultadoJornada extends javax.swing.JDialog implements TreeSelectionListener {

    private BancoDeDados bd;
    private SigeveView sv;
    private Jornada jornada;
    private DefaultMutableTreeNode raiz;

    /** Creates new form TelaResultadoJornada
     * @param parent
     * @param modal
     * @param jornada
     * @param bd
     */
    public TelaResultadoJornada(
        SigeveView parent,
        boolean modal,
        Jornada jornada,
        BancoDeDados bd )
    {
        super( parent.getFrame(), modal );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        this.bd = bd;
        this.sv = parent;
        this.jornada = jornada;
        raiz = new DefaultMutableTreeNode( "Vendas" );
        criarNos();
        initComponents();
        escreverInformacoes( jornada );
    }

    @Action
    public void fecharTela()
    {
        // Tratamento do campo ftfEncaminhamento:
        try
        {
            float valor = sv.nfMoeda.parse( ftfEncaminhado.getText() ).floatValue();
            if ( valor < 0 )
            {
                JOptionPane.showMessageDialog( sv.getComponent(), "O valor encaminhado não pode ser negativo. Tente novamente.", "Resultados", JOptionPane.WARNING_MESSAGE );
                ftfEncaminhado.setText( "" );
                ftfEncaminhado.grabFocus();
            }
            else
            {
                bOk.grabFocus();
                jornada.setEncaminhamento( valor );
                bd.realizarEncaminhamento( valor );
            }
        }
        catch ( ParseException ex )
        {
            JOptionPane.showMessageDialog( sv.getComponent(), "Valor informado inválido. Tente novamente.", "Resultados", JOptionPane.WARNING_MESSAGE );
            Logger.getLogger( TelaResultadoJornada.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( SQLException e )
        {
            JOptionPane.showMessageDialog( sv.getComponent(), "Erro ao escrever valor encaminhado no banco de dados. Informe ao responsável.", "Resultados", JOptionPane.WARNING_MESSAGE );
        }

        // Rotina de fechamento:
        int resposta = JOptionPane.showConfirmDialog( painelPrincipal,
            "Confirma o encaminhameto de " + ftfEncaminhado.getText() + " para a matriz?",
            "Resultados",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE );

        if ( resposta == JOptionPane.YES_OPTION )
        {
            resposta = JOptionPane.showConfirmDialog( painelPrincipal,
                "Os dados de quantidade e valor vendido de cada produto foram anotados?",
                "Resultados",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE );

            if ( resposta == JOptionPane.YES_OPTION )
            {
                try
                {
                    bd.setEncaminhamento( jornada );
                    float novoCaixa = jornada.getCaixaFinal() - jornada.getEncaminhamento();
                    sv.sugerirCaixaInicial( novoCaixa );
                    dispose();
                }
                catch ( SQLException ex )
                {
                    JOptionPane.showMessageDialog( painelPrincipal,
                        "Erro ao gravar valor encaminhado no banco de dados. Informe o responsável.",
                        "Resultados",
                        JOptionPane.WARNING_MESSAGE);
                    Logger.getLogger( TelaResultadoJornada.class.getName() ).log( Level.SEVERE, null, ex );
                }                
            }
        }
    }

    private void criarNos()
    {
        DefaultMutableTreeNode noCategoria = null;
        DefaultMutableTreeNode noProduto = null;
        DefaultMutableTreeNode noQtde = null;
        DefaultMutableTreeNode noValor = null;
        List<Categoria> categorias = bd.obterCategoriasBD();
        List<Produto> produtos;
        Categoria categoria;
        Produto produto;
        float qtde;
        float valor;

        for ( Iterator<Categoria> itCat = categorias.iterator(); itCat.hasNext(); )
        {
            categoria = itCat.next();
            noCategoria = new DefaultMutableTreeNode( categoria.getNome() );
            try
            {
                produtos = bd.obterProdutosBD( categoria.getCodigoCategoria() );
                valor = bd.obterValorVendidoCategoriaBD(
                    jornada,
                    categoria.getCodigoCategoria() );

                // Se tiver ocorrido alguma venda na dada categoria, insere o nó:
                if ( valor > 0 )
                {
                    raiz.add( noCategoria );
                    noValor = new DefaultMutableTreeNode( "valor: " + sv.nfMoeda.format( valor ) );
                    noCategoria.add( noValor );

                    for ( Iterator<Produto> itProd = produtos.iterator(); itProd.hasNext(); )
                    {
                        produto = itProd.next();
                        qtde = bd.obterQtdeVendidaBD(
                            jornada,
                            categoria.getCodigoCategoria(),
                            produto.getCodigoProduto() );

                        // Se tiver ocorrido alguma venda do dado produto, insere o nó:
                        if ( qtde > 0 )
                        {
                            valor = bd.obterValorVendidoProdutoBD(
                                jornada,
                                categoria.getCodigoCategoria(),
                                produto.getCodigoProduto() );

                            noProduto = new DefaultMutableTreeNode( produto.getNome() );
                            noCategoria.add( noProduto );

                            noQtde = new DefaultMutableTreeNode( "qtde: " + qtde );
                            noValor = new DefaultMutableTreeNode( "valor: " + sv.nfMoeda.format( valor ) );
                            noProduto.add( noQtde );
                            noProduto.add( noValor );
                        }
                    }
                }
            }
            catch ( SQLException ex )
            {
                Logger.getLogger( TelaResultadoJornada.class.getName() ).log( Level.SEVERE, null, ex );
            }
        }
    }

    private void escreverInformacoes( Jornada j )
    {
        tfLoja.setText( j.getLoja().getNome() );

        tfCaixaAbertura.setText( sv.nfMoeda.format( j.getCaixaInicial() ) );
        tfCaixaFechamento.setText( sv.nfMoeda.format( j.getCaixaFinal() ) );
        tfReforcos.setText( sv.nfMoeda.format( j.getReforcos() ) );
        tfSangrias.setText( sv.nfMoeda.format( j.getSangrias() ) );
        tfDespesas.setText( sv.nfMoeda.format( j.getDespesas() ) );
        ftfEncaminhado.setText( sv.nfMoeda.format( j.getEncaminhamento() ) );
        float vendas = j.getCaixaFinal() + j.getSangrias() + j.getEncaminhamento()
            + j.getDespesas() - j.getCaixaInicial() - j.getReforcos();
        tfVendas.setText( sv.nfMoeda.format( vendas ) );

        tfDataAbertura.setText( j.getDataAbertura() );
        tfDataFechamento.setText( j.getDataFechamento() );
        tfHoraAbertura.setText( j.getHoraAbertura() );
        tfHoraFechamento.setText( j.getHoraFechamento() );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        lLoja = new javax.swing.JLabel();
        tfLoja = new javax.swing.JTextField();
        painelAbertura = new javax.swing.JPanel();
        lDataAbertura = new javax.swing.JLabel();
        lHoraAbertura = new javax.swing.JLabel();
        tfDataAbertura = new javax.swing.JTextField();
        tfHoraAbertura = new javax.swing.JTextField();
        painelFechamento = new javax.swing.JPanel();
        lDataFechamento = new javax.swing.JLabel();
        lHoraFechamento = new javax.swing.JLabel();
        tfDataFechamento = new javax.swing.JTextField();
        tfHoraFechamento = new javax.swing.JTextField();
        painelResultado = new javax.swing.JPanel();
        lCaixaAbertura = new javax.swing.JLabel();
        lVendas = new javax.swing.JLabel();
        lReforcos = new javax.swing.JLabel();
        lSangrias = new javax.swing.JLabel();
        lDespesas = new javax.swing.JLabel();
        lCaixaFechamento = new javax.swing.JLabel();
        lEncaminhado = new javax.swing.JLabel();
        tfCaixaAbertura = new javax.swing.JTextField();
        tfVendas = new javax.swing.JTextField();
        tfReforcos = new javax.swing.JTextField();
        tfSangrias = new javax.swing.JTextField();
        tfDespesas = new javax.swing.JTextField();
        tfCaixaFechamento = new javax.swing.JTextField();
        ftfEncaminhado = new javax.swing.JFormattedTextField();
        painelScroll = new javax.swing.JScrollPane();
        arvore = new javax.swing.JTree( raiz );
        bOk = new javax.swing.JButton();
        bImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getResourceMap(TelaResultadoJornada.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        painelPrincipal.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        painelPrincipal.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        painelPrincipal.setName("painelPrincipal"); // NOI18N

        lLoja.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lLoja.setText(resourceMap.getString("lLoja.text")); // NOI18N
        lLoja.setFocusable(false);
        lLoja.setName("lLoja"); // NOI18N

        tfLoja.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfLoja.setEditable(false);
        tfLoja.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfLoja.setEnabled(false);
        tfLoja.setFocusable(false);
        tfLoja.setName("tfLoja"); // NOI18N

        painelAbertura.setBackground(resourceMap.getColor("CorNivel3")); // NOI18N
        painelAbertura.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("painelAbertura.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        painelAbertura.setName("painelAbertura"); // NOI18N

        lDataAbertura.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lDataAbertura.setText(resourceMap.getString("lDataAbertura.text")); // NOI18N
        lDataAbertura.setFocusable(false);
        lDataAbertura.setName("lDataAbertura"); // NOI18N

        lHoraAbertura.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lHoraAbertura.setText(resourceMap.getString("lHoraAbertura.text")); // NOI18N
        lHoraAbertura.setFocusable(false);
        lHoraAbertura.setName("lHoraAbertura"); // NOI18N

        tfDataAbertura.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfDataAbertura.setEditable(false);
        tfDataAbertura.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfDataAbertura.setText(resourceMap.getString("tfDataAbertura.text")); // NOI18N
        tfDataAbertura.setEnabled(false);
        tfDataAbertura.setFocusable(false);
        tfDataAbertura.setMaximumSize(new java.awt.Dimension(120, 23));
        tfDataAbertura.setMinimumSize(new java.awt.Dimension(120, 23));
        tfDataAbertura.setName("tfDataAbertura"); // NOI18N
        tfDataAbertura.setPreferredSize(new java.awt.Dimension(120, 23));

        tfHoraAbertura.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfHoraAbertura.setEditable(false);
        tfHoraAbertura.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfHoraAbertura.setText(resourceMap.getString("tfHoraAbertura.text")); // NOI18N
        tfHoraAbertura.setEnabled(false);
        tfHoraAbertura.setFocusable(false);
        tfHoraAbertura.setName("tfHoraAbertura"); // NOI18N

        javax.swing.GroupLayout painelAberturaLayout = new javax.swing.GroupLayout(painelAbertura);
        painelAbertura.setLayout(painelAberturaLayout);
        painelAberturaLayout.setHorizontalGroup(
            painelAberturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAberturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelAberturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelAberturaLayout.createSequentialGroup()
                        .addComponent(lDataAbertura)
                        .addGap(18, 18, 18)
                        .addComponent(tfDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelAberturaLayout.createSequentialGroup()
                        .addComponent(lHoraAbertura)
                        .addGap(18, 18, 18)
                        .addComponent(tfHoraAbertura)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelAberturaLayout.setVerticalGroup(
            painelAberturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAberturaLayout.createSequentialGroup()
                .addGroup(painelAberturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lDataAbertura)
                    .addComponent(tfDataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelAberturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lHoraAbertura)
                    .addComponent(tfHoraAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelFechamento.setBackground(resourceMap.getColor("painelFechamento.background")); // NOI18N
        painelFechamento.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("painelFechamento.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        painelFechamento.setName("painelFechamento"); // NOI18N

        lDataFechamento.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lDataFechamento.setText(resourceMap.getString("lDataFechamento.text")); // NOI18N
        lDataFechamento.setFocusable(false);
        lDataFechamento.setName("lDataFechamento"); // NOI18N

        lHoraFechamento.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lHoraFechamento.setText(resourceMap.getString("lHoraFechamento.text")); // NOI18N
        lHoraFechamento.setFocusable(false);
        lHoraFechamento.setName("lHoraFechamento"); // NOI18N

        tfDataFechamento.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfDataFechamento.setEditable(false);
        tfDataFechamento.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfDataFechamento.setText(resourceMap.getString("tfDataFechamento.text")); // NOI18N
        tfDataFechamento.setEnabled(false);
        tfDataFechamento.setFocusable(false);
        tfDataFechamento.setMaximumSize(new java.awt.Dimension(120, 23));
        tfDataFechamento.setMinimumSize(new java.awt.Dimension(120, 23));
        tfDataFechamento.setName("tfDataFechamento"); // NOI18N
        tfDataFechamento.setPreferredSize(new java.awt.Dimension(120, 23));

        tfHoraFechamento.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfHoraFechamento.setEditable(false);
        tfHoraFechamento.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfHoraFechamento.setText(resourceMap.getString("tfHoraFechamento.text")); // NOI18N
        tfHoraFechamento.setEnabled(false);
        tfHoraFechamento.setFocusable(false);
        tfHoraFechamento.setName("tfHoraFechamento"); // NOI18N

        javax.swing.GroupLayout painelFechamentoLayout = new javax.swing.GroupLayout(painelFechamento);
        painelFechamento.setLayout(painelFechamentoLayout);
        painelFechamentoLayout.setHorizontalGroup(
            painelFechamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFechamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFechamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelFechamentoLayout.createSequentialGroup()
                        .addComponent(lDataFechamento)
                        .addGap(18, 18, 18)
                        .addComponent(tfDataFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelFechamentoLayout.createSequentialGroup()
                        .addComponent(lHoraFechamento)
                        .addGap(18, 18, 18)
                        .addComponent(tfHoraFechamento)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelFechamentoLayout.setVerticalGroup(
            painelFechamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFechamentoLayout.createSequentialGroup()
                .addGroup(painelFechamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lDataFechamento)
                    .addComponent(tfDataFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelFechamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lHoraFechamento)
                    .addComponent(tfHoraFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelResultado.setBackground(resourceMap.getColor("painelResultado.background")); // NOI18N
        painelResultado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("painelResultado.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("FontePadrao"))); // NOI18N
        painelResultado.setName("painelResultado"); // NOI18N

        lCaixaAbertura.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lCaixaAbertura.setText(resourceMap.getString("lCaixaAbertura.text")); // NOI18N
        lCaixaAbertura.setFocusable(false);
        lCaixaAbertura.setName("lCaixaAbertura"); // NOI18N

        lVendas.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lVendas.setText(resourceMap.getString("lVendas.text")); // NOI18N
        lVendas.setFocusable(false);
        lVendas.setName("lVendas"); // NOI18N

        lReforcos.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lReforcos.setText(resourceMap.getString("lReforcos.text")); // NOI18N
        lReforcos.setFocusable(false);
        lReforcos.setName("lReforcos"); // NOI18N

        lSangrias.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lSangrias.setText(resourceMap.getString("lSangrias.text")); // NOI18N
        lSangrias.setFocusable(false);
        lSangrias.setName("lSangrias"); // NOI18N

        lDespesas.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lDespesas.setText(resourceMap.getString("lDespesas.text")); // NOI18N
        lDespesas.setFocusable(false);
        lDespesas.setName("lDespesas"); // NOI18N

        lCaixaFechamento.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lCaixaFechamento.setText(resourceMap.getString("lCaixaFechamento.text")); // NOI18N
        lCaixaFechamento.setFocusable(false);
        lCaixaFechamento.setName("lCaixaFechamento"); // NOI18N

        lEncaminhado.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        lEncaminhado.setText(resourceMap.getString("lEncaminhado.text")); // NOI18N
        lEncaminhado.setFocusable(false);
        lEncaminhado.setName("lEncaminhado"); // NOI18N

        tfCaixaAbertura.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfCaixaAbertura.setEditable(false);
        tfCaixaAbertura.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfCaixaAbertura.setText(resourceMap.getString("tfCaixaAbertura.text")); // NOI18N
        tfCaixaAbertura.setEnabled(false);
        tfCaixaAbertura.setFocusable(false);
        tfCaixaAbertura.setName("tfCaixaAbertura"); // NOI18N

        tfVendas.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfVendas.setEditable(false);
        tfVendas.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfVendas.setText(resourceMap.getString("tfVendas.text")); // NOI18N
        tfVendas.setEnabled(false);
        tfVendas.setFocusable(false);
        tfVendas.setName("tfVendas"); // NOI18N

        tfReforcos.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfReforcos.setEditable(false);
        tfReforcos.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfReforcos.setText(resourceMap.getString("tfReforcos.text")); // NOI18N
        tfReforcos.setEnabled(false);
        tfReforcos.setFocusable(false);
        tfReforcos.setName("tfReforcos"); // NOI18N

        tfSangrias.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfSangrias.setEditable(false);
        tfSangrias.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfSangrias.setText(resourceMap.getString("tfSangrias.text")); // NOI18N
        tfSangrias.setEnabled(false);
        tfSangrias.setFocusable(false);
        tfSangrias.setName("tfSangrias"); // NOI18N

        tfDespesas.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfDespesas.setEditable(false);
        tfDespesas.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfDespesas.setEnabled(false);
        tfDespesas.setFocusable(false);
        tfDespesas.setName("tfDespesas"); // NOI18N

        tfCaixaFechamento.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        tfCaixaFechamento.setEditable(false);
        tfCaixaFechamento.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        tfCaixaFechamento.setText(resourceMap.getString("tfCaixaFechamento.text")); // NOI18N
        tfCaixaFechamento.setEnabled(false);
        tfCaixaFechamento.setFocusable(false);
        tfCaixaFechamento.setName("tfCaixaFechamento"); // NOI18N

        ftfEncaminhado.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        ftfEncaminhado.setText(resourceMap.getString("ftfEncaminhado.text")); // NOI18N
        ftfEncaminhado.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        ftfEncaminhado.setName("ftfEncaminhado"); // NOI18N
        ftfEncaminhado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftfEncaminhadoActionPerformed(evt);
            }
        });
        ftfEncaminhado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ftfEncaminhadoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftfEncaminhadoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout painelResultadoLayout = new javax.swing.GroupLayout(painelResultado);
        painelResultado.setLayout(painelResultadoLayout);
        painelResultadoLayout.setHorizontalGroup(
            painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelResultadoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lVendas)
                    .addComponent(lReforcos)
                    .addComponent(lSangrias)
                    .addComponent(lCaixaFechamento)
                    .addComponent(lEncaminhado)
                    .addComponent(lCaixaAbertura)
                    .addComponent(lDespesas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ftfEncaminhado)
                    .addComponent(tfVendas)
                    .addComponent(tfReforcos)
                    .addComponent(tfSangrias)
                    .addComponent(tfCaixaFechamento)
                    .addComponent(tfDespesas)
                    .addComponent(tfCaixaAbertura, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelResultadoLayout.setVerticalGroup(
            painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelResultadoLayout.createSequentialGroup()
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfCaixaAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lCaixaAbertura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lVendas)
                    .addComponent(tfVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lReforcos)
                    .addComponent(tfReforcos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lSangrias)
                    .addComponent(tfSangrias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lDespesas)
                    .addComponent(tfDespesas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCaixaFechamento)
                    .addComponent(tfCaixaFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelResultadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lEncaminhado)
                    .addComponent(ftfEncaminhado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelScroll.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        painelScroll.setName("painelScroll"); // NOI18N

        arvore.setBackground(resourceMap.getColor("CorNivel4")); // NOI18N
        arvore.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        arvore.setName("arvore"); // NOI18N
        painelScroll.setViewportView(arvore);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(sigeve.SigeveApp.class).getContext().getActionMap(TelaResultadoJornada.class, this);
        bOk.setAction(actionMap.get("fecharTela")); // NOI18N
        bOk.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        bOk.setText(resourceMap.getString("bOk.text")); // NOI18N
        bOk.setName("bOk"); // NOI18N

        bImprimir.setFont(resourceMap.getFont("FontePadrao")); // NOI18N
        bImprimir.setText(resourceMap.getString("bImprimir.text")); // NOI18N
        bImprimir.setToolTipText(resourceMap.getString("bImprimir.toolTipText")); // NOI18N
        bImprimir.setEnabled(false);
        bImprimir.setName("bImprimir"); // NOI18N

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelResultado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(painelPrincipalLayout.createSequentialGroup()
                            .addComponent(lLoja)
                            .addGap(18, 18, 18)
                            .addComponent(tfLoja))
                        .addGroup(painelPrincipalLayout.createSequentialGroup()
                            .addComponent(painelAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(painelFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addGroup(painelPrincipalLayout.createSequentialGroup()
                        .addComponent(bOk, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPrincipalLayout.createSequentialGroup()
                        .addComponent(painelScroll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bOk))
                    .addGroup(painelPrincipalLayout.createSequentialGroup()
                        .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lLoja)
                            .addComponent(tfLoja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(painelAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(painelFechamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelResultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelPrincipalLayout.createSequentialGroup()
                        .addGap(341, 341, 341)
                        .addComponent(bImprimir)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ftfEncaminhadoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_ftfEncaminhadoActionPerformed
    {//GEN-HEADEREND:event_ftfEncaminhadoActionPerformed
        try
        {
            sv.ajustarCampoParaMoeda( evt );
            float valor = sv.nfMoeda.parse( ftfEncaminhado.getText() ).floatValue();
            if ( valor < 0 )
            {
                JOptionPane.showMessageDialog( sv.getComponent(), "O valor encaminhado não pode ser negativo. Tente novamente.", "Resultados", JOptionPane.WARNING_MESSAGE );
                ftfEncaminhado.setText( "" );
                ftfEncaminhado.grabFocus();
            }
            else
            {
                bOk.grabFocus();
                jornada.setEncaminhamento( valor );
                bd.realizarEncaminhamento( valor );
            }
        }
        catch ( ParseException ex )
        {
            JOptionPane.showMessageDialog( sv.getComponent(), "Valor informado inválido. Tente novamente.", "Resultados", JOptionPane.WARNING_MESSAGE );
            Logger.getLogger( TelaResultadoJornada.class.getName() ).log( Level.SEVERE, null, ex );
        }
        catch ( SQLException e )
        {
            JOptionPane.showMessageDialog( sv.getComponent(), "Erro ao escrever valor encaminhado no banco de dados. Informe ao responsável.", "Resultados", JOptionPane.WARNING_MESSAGE );
        }
    }//GEN-LAST:event_ftfEncaminhadoActionPerformed

    private void ftfEncaminhadoFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_ftfEncaminhadoFocusGained
    {//GEN-HEADEREND:event_ftfEncaminhadoFocusGained
        try
        {
            if ( !ftfEncaminhado.getText().isEmpty() )
                ftfEncaminhado.setText(
                    sv.nf2d.format(
                    sv.nfMoeda.parse(
                    ftfEncaminhado.getText() ) ) );
        }
        catch ( ParseException ex )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.OFF, null, ex );
        }
        finally
        {
            ftfEncaminhado.selectAll();
        }
    }//GEN-LAST:event_ftfEncaminhadoFocusGained

    private void ftfEncaminhadoFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_ftfEncaminhadoFocusLost
    {//GEN-HEADEREND:event_ftfEncaminhadoFocusLost
        sv.ajustarCampoParaMoeda( evt );
    }//GEN-LAST:event_ftfEncaminhadoFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arvore;
    private javax.swing.JButton bImprimir;
    private javax.swing.JButton bOk;
    private javax.swing.JFormattedTextField ftfEncaminhado;
    private javax.swing.JLabel lCaixaAbertura;
    private javax.swing.JLabel lCaixaFechamento;
    private javax.swing.JLabel lDataAbertura;
    private javax.swing.JLabel lDataFechamento;
    private javax.swing.JLabel lDespesas;
    private javax.swing.JLabel lEncaminhado;
    private javax.swing.JLabel lHoraAbertura;
    private javax.swing.JLabel lHoraFechamento;
    private javax.swing.JLabel lLoja;
    private javax.swing.JLabel lReforcos;
    private javax.swing.JLabel lSangrias;
    private javax.swing.JLabel lVendas;
    private javax.swing.JPanel painelAbertura;
    private javax.swing.JPanel painelFechamento;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelResultado;
    private javax.swing.JScrollPane painelScroll;
    private javax.swing.JTextField tfCaixaAbertura;
    private javax.swing.JTextField tfCaixaFechamento;
    private javax.swing.JTextField tfDataAbertura;
    private javax.swing.JTextField tfDataFechamento;
    private javax.swing.JTextField tfDespesas;
    private javax.swing.JTextField tfHoraAbertura;
    private javax.swing.JTextField tfHoraFechamento;
    private javax.swing.JTextField tfLoja;
    private javax.swing.JTextField tfReforcos;
    private javax.swing.JTextField tfSangrias;
    private javax.swing.JTextField tfVendas;
    // End of variables declaration//GEN-END:variables

    public void valueChanged( TreeSelectionEvent e )
    {
        //throw new UnsupportedOperationException( "Not supported yet." );
    }

}
