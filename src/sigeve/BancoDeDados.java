/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sigeve;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sigeve.Constantes.*;

/**
 *
 * @author Paulo Henrique
 */
public class BancoDeDados
{
    public static final int COMANDA_INVALIDA = -1;
    public static final int QUERY_TIMEOUT = 30;
    private Connection conn;
    private Statement stm;
    private ResultSet rs;
    private Formatter output; // Objeto utilizado para gerar saída de texto no arquivo que receberá dados exportados do BD.
    
    /**
     * O construtor cria uma nova conexão com o banco de dados sqlite contido
     * no arquivo passado como parâmetro. A conexão é possibilitada pelo driver
     * JDBC, fornecido por SQLiteJDBC.
     *
     * Foreign_keys triggers foram habilitados (desabilitados por padrão para
     * compatibilidade) conforme apresentado em:
     * http://www.sqlite.org/foreignkeys.html#fk_enable
     *
     * @param file
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public BancoDeDados( String file )
        throws SQLException, ClassNotFoundException
    {
        Class.forName( "org.sqlite.JDBC" );
        conn = DriverManager.getConnection( "jdbc:sqlite:" + file );
        stm = conn.createStatement();
        stm.setQueryTimeout( QUERY_TIMEOUT );
        stm.execute( "PRAGMA foreign_keys = ON" );
    }

    public void atualizarVersao()
    {
        try
        {
            stm.executeUpdate( "CREATE TABLE ListaSolicitacoes ("
                + "listaId          integer     PRIMARY KEY NOT NULL, "            
                + "cb001            boolean, "
                + "cb002            boolean, "
                + "cb003            boolean, "
                + "cb004            boolean, "
                + "cb005            boolean, "
                + "cb006            boolean, "
                + "cb007            boolean, "
                + "cb008            boolean, "
                + "cb009            boolean, "
                + "cb010            boolean, "
                + "cb011            boolean, "
                + "cb012            boolean, "
                + "cb013            boolean, "
                + "cb014            boolean, "
                + "cb015            boolean, "
                + "cb016            boolean, "
                + "cb017            boolean, "
                + "cb018            boolean, "
                + "cb019            boolean, "
                + "cb020            boolean, "
                + "cb021            boolean, "
                + "cb022            boolean, "
                + "cb023            boolean, "
                + "cb024            boolean, "
                + "cb025            boolean, "
                + "cb026            boolean, "
                + "cb027            boolean, "
                + "cb028            boolean, "
                + "cb029            boolean, "
                + "cb030            boolean, "
                + "cb031            boolean, "
                + "cb032            boolean, "
                + "cb033            boolean, "
                + "cb034            boolean, "
                + "cb035            boolean, "
                + "cb036            boolean, "
                + "cb037            boolean, "
                + "cb038            boolean, "
                + "cb039            boolean, "
                + "cb040            boolean, "
                + "cb041            boolean, "
                + "cb042            boolean, "
                + "cb043            boolean, "
                + "cb044            boolean, "
                + "cb045            boolean, "
                + "cb046            boolean, "
                + "cb047            boolean, "
                + "cb048            boolean, "
                + "cb049            boolean, "
                + "cb050            boolean, "
                + "cb051            boolean, "
                + "cb052            boolean, "
                + "cb053            boolean, "
                + "cb054            boolean, "
                + "cb055            boolean, "
                + "cb056            boolean, "
                + "cb057            boolean, "
                + "cb058            boolean, "
                + "cb059            boolean, "
                + "cb060            boolean, "
                + "cb071            boolean, "
                + "cb072            boolean, "
                + "cb073            boolean, "
                + "cb074            boolean, "
                + "cb075            boolean, "
                + "cb076            boolean, "
                + "cb077            boolean, "
                + "cb078            boolean, "
                + "cb079            boolean, "
                + "cb080            boolean, "
                + "cb081            boolean, "
                + "cb082            boolean, "
                + "cb083            boolean, "
                + "cb084            boolean, "
                + "cb085            boolean, "
                + "cb086            boolean, "
                + "cb087            boolean, "
                + "cb088            boolean, "
                + "cb089            boolean, "
                + "cb090            boolean, "
                + "cb091            boolean, "
                + "cb092            boolean, "
                + "cb093            boolean, "
                + "cb094            boolean, "
                + "cb095            boolean, "
                + "cb096            boolean, "
                + "cb097            boolean, "
                + "cb098            boolean, "
                + "cb099            boolean, "
                + "cb100            boolean, "
                + "cb101            boolean, "
                + "cb102            boolean, "
                + "cb103            boolean, "
                + "cb104            boolean, "
                + "cb105            boolean, "
                + "cb106            boolean, "
                + "cb107            boolean, "
                + "cb108            boolean, "
                + "cb109            boolean, "
                + "responsavel      varchar(30), "
                + "tf04             varchar(30), "
                + "tf05             varchar(30), "
                + "tf06             varchar(30), "
                + "tf07             varchar(30), "
                + "tf08             varchar(30), "
                + "tf09             varchar(30), "
                + "tf10             varchar(30), "
                + "tf11             varchar(30), "
                + "tf12             varchar(30), "
                + "tf13             varchar(30), "
                + "tf14             varchar(30), "
                + "tf15             varchar(30), "
                + "tf16             varchar(30), "
                + "ta01             varchar(100))");
        }
        catch ( SQLException ex )
        {
            Logger.getLogger( BancoDeDados.class.getName() ).log( 
                Level.FINE, 
                "Tabela ListaSolicitacoes já está presente no banco.", 
                ex );
        }
    }
    
    /**
     * Adiciona nova linha na tabela <i>Comandas</i> do BD.
     *
     * @param horario
     * @param valor
     * @throws SQLException
     */
    public void adicionarComandaBD( String horario )//, float valor )
        throws SQLException
    {
        stm.executeUpdate( "INSERT INTO Comandas VALUES ("
            + "NULL, "
            + "(SELECT jornadaId FROM JornadaAberta), "
            + "\"" + ConversorFormato.dataHoraBrParaIso( horario ) + "\", "
            + 0 + ", "
            + 0 + ")" );
        rs = stm.executeQuery( "SELECT MAX(comandaId) FROM Comandas "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
        rs.next();
        int comandaId = rs.getInt( "MAX(comandaId)" );
        stm.executeUpdate( "INSERT INTO ComandasAbertas VALUES ("
            + comandaId + ", "
            + "\"\", " // nome do cliente indefinido
            + "0)" );  // posicao da comanda indefinida
        stm.executeUpdate( "INSERT INTO ComandaAtiva VALUES ("
            + comandaId + ")" );
    }

    public void adicionarDespesaBD( String descricao, float valor )
        throws SQLException
    {
        stm.executeUpdate( "INSERT INTO Despesas VALUES ("
            + "NULL, "
            + "(SELECT jornadaId FROM JornadaAberta), "
            + "\"" + descricao + "\", "
            + valor + ")" );
    }

    /**
     * Cadastra na tabela <i>Funcionarios</i> do BD o nome do funcionário.
     *
     * @param nome
     * @throws SQLException
     */
    public void adicionarFuncionarioBD( String nome )
        throws SQLException
    {
        stm.executeUpdate( "INSERT INTO Funcionarios VALUES ("
            + "NULL, "
            + "\"" + nome + "\")" );
    }

    /**
     * Adiciona nova linha na tabela <i>Jornada</i> do BD.
     *
     * @param loja
     * @param abertura
     * @param turno
     * @param caixaInicial
     * @throws SQLException
     */
    public void adicionarJornadaBD(
        String loja,
        String abertura,
        String turno,
        float caixaInicial )
        throws SQLException
    {
        stm.executeUpdate( "INSERT "
            + "INTO Jornadas (jornadaId, lojaId, abertura, turnoId, caixaInicial) "
            + "VALUES ("
            + "NULL, "
            + "(SELECT lojaId FROM Lojas WHERE Lojas.nome = \"" + loja + "\"), "
            + "\"" + ConversorFormato.dataHoraBrParaIso( abertura ) + "\", "
            + "(SELECT turnoId FROM Turnos WHERE Turnos.nome = \"" + turno + "\"), "
            + caixaInicial + ")" );
        rs = stm.executeQuery( "SELECT jornadaId FROM Jornadas "
            + "WHERE lojaId = ("
            + "SELECT lojaId FROM Lojas "
            + "WHERE Lojas.nome = \"" + loja + "\") "
            + "AND abertura = \"" + ConversorFormato.dataHoraBrParaIso( abertura ) + "\"" );
        rs.next();
        int jornadaId = rs.getInt( "jornadaId" );
        stm.executeUpdate( "DELETE FROM JornadaAberta" );
        stm.executeUpdate( "INSERT INTO JornadaAberta "
            + "VALUES (" + jornadaId + ")" );
    }

    /**
     * Cadastra na tabela <i>Lojas</i> do BD o nome da nova loja.
     *
     * @param nome
     * @throws SQLException
     */
    public void adicionarLojaBD( String nome )
        throws SQLException
    {
        stm.executeUpdate( "INSERT INTO Lojas VALUES ("
            + "NULL, "
            + "\"" + nome + "\", "
            + "0)" );
    }

    /**
     * Adiciona nova linha na tabela <i>Pedidos</i> do BD.
     *
     * @param novoPedido (devem estar definidos nome e id do Produto)
     * @throws SQLException
     */
    public void adicionarPedidoBD( Pedido novoPedido )
        throws SQLException
    {
        stm.executeUpdate( "INSERT INTO Pedidos VALUES ("
            + "NULL, "
            + "(SELECT comandaId FROM ComandaAtiva), "
            + novoPedido.getProduto().getId() + ", "
            + novoPedido.getQtde() + ")" );

        atualizarTotalComandaBD();
    }

    /**
     * Cadastra produto na tabela <i>Produtos</i> do BD.
     *
     * @param pr
     * @throws SQLException
     */
    public void adicionarProdutoBD( Produto pr )
        throws SQLException
    {
        removerProdutoBD( pr.getCodigoCategoria(), pr.getCodigoProduto() );
        stm.executeUpdate( "INSERT INTO Produtos VALUES ("
            + "NULL, "
            + "(SELECT categoriaId FROM Categorias "
            + "WHERE codigo = \"" + pr.getCodigoCategoria() + "\"), "
            + "\"" + pr.getCodigoProduto() + "\", "
            + "\"" + pr.getNome() + "\", "
            + pr.getPreco() + ", "
            + "1)" );
    }

    public void alterarDescontoComandaBD( float novoDesconto )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE Comandas "
            + "SET desconto = " + novoDesconto + " "
            + "WHERE comandaId = (SELECT comandaId FROM ComandaAtiva)" );

        atualizarTotalComandaBD();
    }

    /**
     * Altera atributo <i>estado</i> do registro selecionado na tabela 
     * <i>Lojas</i> do BD.
     *
     * @param nome
     * @throws SQLException
     */
    public void alterarLojaBD( String nome )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE Lojas "
            + "SET estado = 0 "
            + "WHERE nome <> \"" + nome + "\"" );
        stm.executeUpdate( "UPDATE Lojas "
            + "SET estado = 1 "
            + "WHERE nome = \"" + nome + "\"" );
        stm.executeUpdate( "UPDATE Jornadas "
            + "SET lojaId = "
            + "(SELECT lojaId FROM Lojas WHERE nome = \"" + nome + "\") "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
    }

    public void alterarNomeClienteBD( String nome )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE ComandasAbertas "
            + "SET nome = \"" + nome + "\""
            + "WHERE comandaId = (SELECT comandaId FROM ComandaAtiva)" );
    }

    public void alterarPosicaoClienteBD( int posicao )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE ComandasAbertas "
            + "SET posicao = \"" + posicao + "\""
            + "WHERE comandaId = (SELECT comandaId FROM ComandaAtiva)" );
    }

    public void alterarSenhaBD( String novaSenha )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE Senhas SET senha = \"" + novaSenha + "\"" );
    }

    public void alterarTempoBD( int tempoId )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE Jornadas "
            + "SET tempoId = " + tempoId + " "
            //+ "(SELECT tempoId FROM Tempo WHERE nome = " + tempo + ") "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
    }

    public void atualizarTotalComandaBD()
        throws SQLException
    {
        int comandaId = obterComandaIdBD();
        atualizarTotalComandaBD( comandaId );
    }

    public void atualizarTotalComandaBD( int comandaId )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT SUM(preco * quantidade) "
            + "FROM Pedidos NATURAL JOIN Produtos "
            + "WHERE comandaId = " + comandaId );
        rs.next();
        float pedidos = rs.getFloat( 1 );

        rs = stm.executeQuery( "SELECT desconto FROM Comandas "
            + "WHERE comandaId = " + comandaId );
        rs.next();
        float desconto = rs.getFloat( "desconto" );

        float total = pedidos - desconto;
        stm.executeUpdate( "UPDATE Comandas SET valor = " + total + " "
            + "WHERE comandaId = " + comandaId );
    }

    public void atualizarListaSolicitacoes( ListaSolicitacoes lista )
        throws SQLException
    {
        stm.executeUpdate( "DELETE FROM ListaSolicitacoes");
        stm.executeUpdate( "INSERT INTO ListaSolicitacoes (listaId) VALUES (1)");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb001 = " + ((lista.isCb001())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb002 = " + ((lista.isCb002())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb003 = " + ((lista.isCb003())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb004 = " + ((lista.isCb004())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb005 = " + ((lista.isCb005())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb006 = " + ((lista.isCb006())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb007 = " + ((lista.isCb007())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb008 = " + ((lista.isCb008())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb009 = " + ((lista.isCb009())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb010 = " + ((lista.isCb010())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb011 = " + ((lista.isCb011())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb012 = " + ((lista.isCb012())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb013 = " + ((lista.isCb013())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb014 = " + ((lista.isCb014())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb015 = " + ((lista.isCb015())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb016 = " + ((lista.isCb016())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb017 = " + ((lista.isCb017())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb018 = " + ((lista.isCb018())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb019 = " + ((lista.isCb019())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb020 = " + ((lista.isCb020())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb021 = " + ((lista.isCb021())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb022 = " + ((lista.isCb022())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb023 = " + ((lista.isCb023())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb024 = " + ((lista.isCb024())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb025 = " + ((lista.isCb025())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb026 = " + ((lista.isCb026())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb027 = " + ((lista.isCb027())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb028 = " + ((lista.isCb028())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb029 = " + ((lista.isCb029())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb030 = " + ((lista.isCb030())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb031 = " + ((lista.isCb031())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb032 = " + ((lista.isCb032())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb033 = " + ((lista.isCb033())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb034 = " + ((lista.isCb034())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb035 = " + ((lista.isCb035())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb036 = " + ((lista.isCb036())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb037 = " + ((lista.isCb037())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb038 = " + ((lista.isCb038())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb039 = " + ((lista.isCb039())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb040 = " + ((lista.isCb040())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb041 = " + ((lista.isCb041())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb042 = " + ((lista.isCb042())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb043 = " + ((lista.isCb043())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb044 = " + ((lista.isCb044())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb045 = " + ((lista.isCb045())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb046 = " + ((lista.isCb046())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb047 = " + ((lista.isCb047())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb048 = " + ((lista.isCb048())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb049 = " + ((lista.isCb049())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb050 = " + ((lista.isCb050())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb051 = " + ((lista.isCb051())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb052 = " + ((lista.isCb052())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb053 = " + ((lista.isCb053())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb054 = " + ((lista.isCb054())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb055 = " + ((lista.isCb055())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb056 = " + ((lista.isCb056())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb057 = " + ((lista.isCb057())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb058 = " + ((lista.isCb058())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb059 = " + ((lista.isCb059())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb060 = " + ((lista.isCb060())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb071 = " + ((lista.isCb071())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb072 = " + ((lista.isCb072())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb073 = " + ((lista.isCb073())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb074 = " + ((lista.isCb074())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb075 = " + ((lista.isCb075())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb076 = " + ((lista.isCb076())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb077 = " + ((lista.isCb077())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb078 = " + ((lista.isCb078())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb079 = " + ((lista.isCb079())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb080 = " + ((lista.isCb080())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb081 = " + ((lista.isCb081())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb082 = " + ((lista.isCb082())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb083 = " + ((lista.isCb083())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb084 = " + ((lista.isCb084())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb085 = " + ((lista.isCb085())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb086 = " + ((lista.isCb086())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb087 = " + ((lista.isCb087())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb088 = " + ((lista.isCb088())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb089 = " + ((lista.isCb089())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb090 = " + ((lista.isCb090())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb091 = " + ((lista.isCb091())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb092 = " + ((lista.isCb092())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb093 = " + ((lista.isCb093())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb094 = " + ((lista.isCb094())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb095 = " + ((lista.isCb095())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb096 = " + ((lista.isCb096())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb097 = " + ((lista.isCb097())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb098 = " + ((lista.isCb098())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb099 = " + ((lista.isCb099())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb100 = " + ((lista.isCb100())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb101 = " + ((lista.isCb101())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb102 = " + ((lista.isCb102())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb103 = " + ((lista.isCb103())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb104 = " + ((lista.isCb104())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb105 = " + ((lista.isCb105())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb106 = " + ((lista.isCb106())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb107 = " + ((lista.isCb107())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb108 = " + ((lista.isCb108())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET cb109 = " + ((lista.isCb109())?"1":"0"));
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET responsavel = \"" + lista.getResponsavel() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf04 = \"" + lista.getTf04() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf05 = \"" + lista.getTf05() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf06 = \"" + lista.getTf06() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf07 = \"" + lista.getTf07() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf08 = \"" + lista.getTf08() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf09 = \"" + lista.getTf09() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf10 = \"" + lista.getTf10() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf11 = \"" + lista.getTf11() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf12 = \"" + lista.getTf12() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf13 = \"" + lista.getTf13() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf14 = \"" + lista.getTf14() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf15 = \"" + lista.getTf15() + "\"");
        stm.executeUpdate( "UPDATE ListaSolicitacoes SET tf16 = \"" + lista.getTf16() + "\"");
	stm.executeUpdate( "UPDATE ListaSolicitacoes SET ta01 = \"" + lista.getTa01() + "\"");
        
    }
        
    public void ativarComandaBD( int posicao )
        throws SQLException
    {
        stm.executeUpdate( "INSERT INTO ComandaAtiva VALUES ( "
            + "(SELECT comandaId FROM ComandasAbertas "
            + "WHERE posicao = " + posicao + ") )" );
    }

    public void desativarComandaBD()
        throws SQLException
    {
        stm.executeUpdate( "DELETE FROM ComandaAtiva" );
    }

    public boolean eSenhaValidaBD( String senha )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT COUNT(senha) FROM Senhas "
            + "WHERE senha = \"" + senha + "\"" );
        rs.next();
        int numOcorrencias = rs.getInt( 1 );
        if ( numOcorrencias > 0 )
            return true;
        else
            return false;

    }

    public boolean estaPosicaoOcupadaBD( int posicao )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT COUNT(posicao) FROM ComandasAbertas "
            + "WHERE posicao = " + posicao );
        assert (rs.getInt( "COUNT(comandaId)" ) <= 1);
        // Se não tiver nenhuma comanda armazenada na dada posição:
        if ( rs.getInt( "COUNT(posicao)" ) == 0 )
            return false;
        else
            return true;
    }

    public boolean existeComandaAbertaBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT COUNT(comandaId) FROM ComandasAbertas" );
        rs.next();
        if ( rs.getInt( "COUNT(comandaId)" ) == 0 )
            return false;
        else
            return true;
    }

    public boolean existeComandaAtivaBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT COUNT(comandaId) FROM ComandaAtiva" );
        rs.next();
        assert (rs.getInt( "COUNT(comandaId)" ) <= 1);
        if ( rs.getInt( "COUNT(comandaId)" ) == 0 )
            return false;
        else
            return true;
    }

    public boolean existeJornadaAbertaBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT COUNT(jornadaId) FROM JornadaAberta" );
        rs.next();
        if ( rs.getInt( 1 ) == 0 )
            return false;
        else
            return true;
    }

    public boolean existePosicaoDisponivelBD()
        throws SQLException
    {
        if ( obterPosicaoDisponivelBD() > N_BOTOES_COMANDAS )
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    private boolean abrirArquivoExportacao( String arquivo )
    {
        try
        {
            output = new Formatter( arquivo ); // Abertura do arquivo.
            return true;
        }
        catch ( SecurityException se )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, se );
            System.err.println( "Usuário sem privilégio para acesso de escrita no arquivo " + arquivo );
            return false;
        }
        catch ( FileNotFoundException fnfe )
        {
            Logger.getLogger( SigeveView.class.getName() ).log( Level.SEVERE, null, fnfe );
            System.err.println( "Erro ao abrir ou criar o arquivo " + arquivo );
            return false;
        }
    }
    
    private void fecharArquivoExportacao()
    {
        if ( output != null )    
        {
            output.close();
        }
    }
        
    public void exportarCategorias()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_CATEGORIAS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Categorias" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\t%s\n", 
                    "categoriaId", 
                    "codigo", 
                    "nome");
                while ( rs.next() )
                {
                    output.format("%d\t%s\t%s\n", 
                        rs.getInt( "categoriaId" ),
                        rs.getString( "codigo" ),
                        rs.getString( "nome" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarComandas()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_COMANDAS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM COMANDAS" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\t%s\t%s\t%s\n", 
                    "jornadaId", 
                    "comandaId",
                    "horario", 
                    "valor", 
                    "desconto");
                
                while ( rs.next() )
                {
                    output.format("%d\t%d\t%s\t%.2f\t%.2f\n", 
                        rs.getInt( "jornadaId" ), 
                        rs.getInt( "comandaId" ),
                        rs.getString( "horario"), 
                        rs.getFloat( "valor"), 
                        rs.getFloat( "desconto"));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarDespesas()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_DESPESAS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Despesas" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\t%s\t%s\n", 
                    "despesaId", 
                    "jornadaId", 
                    "descricao", 
                    "valor");
                while ( rs.next() )
                {
                    output.format("%d\t%d\t%s\t%.2f\n", 
                        rs.getInt( "despesaId" ),
                        rs.getInt( "jornadaId" ),
                        rs.getString( "descricao" ),
                        rs.getFloat( "valor" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarFuncionarios()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_FUNCIONARIOS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Funcionarios" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\n", 
                    "funcionarioId", 
                    "nome");
                while ( rs.next() )
                {
                    output.format("%d\t%s\n", 
                        rs.getInt( "funcionarioId" ),
                        rs.getString( "nome" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarFuncionariosJornadas()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_FUNCIONARIOSJORNADAS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM FuncionariosJornadas" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\n", 
                    "funcionarioId", 
                    "jornadaId");
                while ( rs.next() )
                {
                    output.format("%d\t%d\n", 
                        rs.getInt( "funcionarioId" ),
                        rs.getInt( "jornadaId" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarJornadas()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_JORNADAS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Jornadas" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n",
                    "jornadaId",
                    "lojaId",
                    "abertura",
                    "fechamento",
                    "turnoId",
                    "caixaInicial",
                    "caixaFinal",
                    "reforcos",
                    "sangrias",
                    "encaminhamento",
                    "tempoId");
                while ( rs.next() )
                {
                    output.format("%d\t%d\t%s\t%s\t%d\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%d\n", 
                        rs.getInt( "jornadaId" ),
                        rs.getInt( "lojaId" ),
                        rs.getString( "abertura" ),
                        rs.getString( "fechamento" ),
                        rs.getInt( "turnoId" ),
                        rs.getFloat( "caixaInicial" ),
                        rs.getFloat( "caixaFinal" ),
                        rs.getFloat( "reforcos" ),
                        rs.getFloat( "sangrias" ),
                        rs.getFloat( "encaminhamento" ),
                        rs.getInt( "tempoId" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarLojas()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_LOJAS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Lojas" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\t%s\n", 
                    "lojaId", 
                    "nome", 
                    "estado");
                while ( rs.next() )
                {
                    output.format("%d\t%s\t%d\n", 
                        rs.getInt( "lojaId" ),
                        rs.getString( "nome" ),
                        rs.getInt( "estado" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarPedidos()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_PEDIDOS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Pedidos" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\t%s\t%s\n", 
                    "pedidoId", 
                    "produtoId", 
                    "comandaId", 
                    "quantidade");
                while ( rs.next() )
                {
                    output.format("%d\t%d\t%d\t%.3f\n", 
                        rs.getInt( "pedidoId" ),
                        rs.getInt( "produtoId" ),
                        rs.getInt( "comandaId" ),
                        rs.getFloat( "quantidade" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }

    public void exportarProdutos()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_PRODUTOS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Produtos" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\t%s\t%s\t%s\t%s\n", 
                    "produtoId", 
                    "categoriaId", 
                    "codigo", 
                    "nome",
                    "preco",
                    "ativo");
                while ( rs.next() )
                {
                    output.format("%d\t%d\t%s\t%s\t%.2f\t%d\n", 
                        rs.getInt( "produtoId" ),
                        rs.getInt( "categoriaId" ),
                        rs.getString( "codigo" ),
                        rs.getString( "nome" ),
                        rs.getFloat( "preco" ),
                        rs.getInt( "ativo" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarTempo()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_TEMPO ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Tempo" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\n", 
                    "tempoId", 
                    "nome");
                while ( rs.next() )
                {
                    output.format("%d\t%s\n", 
                        rs.getInt( "tempoId" ),
                        rs.getString( "nome" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void exportarTurnos()
        throws SQLException
    {     
        if ( abrirArquivoExportacao( EXPORTA_TURNOS ) )
        {
            try
            {
                rs = stm.executeQuery( "SELECT * FROM Turnos" );
                
                // Nome das colunas (campos)
                output.format("%s\t%s\n", 
                    "turnoId", 
                    "nome");
                while ( rs.next() )
                {
                    output.format("%d\t%s\n", 
                        rs.getInt( "turnoId" ),
                        rs.getString( "nome" ));
                }
            }
            catch ( FormatterClosedException fce )
            {
                System.err.println( "Erro ao escrever no arquivo! ");
            }
            finally
            {
                fecharArquivoExportacao();
            }
        }
        else
        {
            // TODO throw Exception caso não conisga abrir arquivo de exportação.
        }
    }
    
    public void fecharComandaBD()
        throws SQLException
    {
        int comandaId = obterComandaIdBD();
        desativarComandaBD();
        fecharComandaBD( comandaId );
    }

    public void fecharComandaBD( int comandaId )
        throws SQLException
    {
        stm.executeUpdate( "DELETE FROM ComandasAbertas "
            + "WHERE comandaId = " + comandaId );
    }

    public void fecharJornadaBD( String fechamento )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT jornadaId FROM JornadaAberta" );
        rs.next();
        int jornadaId = rs.getInt( "jornadaId" );

        rs = stm.executeQuery( "SELECT SUM(valor) FROM Comandas WHERE jornadaId = " + jornadaId );
        rs.next();
        float somaComandas = rs.getFloat( "SUM(valor)" );

        rs = stm.executeQuery( "SELECT SUM(valor) FROM Despesas WHERE jornadaId = " + jornadaId );
        rs.next();
        float somaDespesas = rs.getFloat( "SUM(valor)" );

        rs = stm.executeQuery( "SELECT caixaInicial FROM Jornadas WHERE jornadaId = " + jornadaId );
        rs.next();
        float caixaInicial = rs.getFloat( "caixaInicial" );

        float caixaFinal = caixaInicial + somaComandas - somaDespesas;

        stm.executeUpdate( "UPDATE Jornadas SET "
            + "fechamento = \"" + ConversorFormato.dataHoraBrParaIso( fechamento ) + "\", "
            + "caixaFinal = " + caixaFinal + " "
            + "WHERE jornadaId = " + jornadaId );
        stm.executeUpdate( "DELETE FROM JornadaAberta" );
    }

    /**
     * Identifica a loja cujo atributo <i>estado</i> na tabela <i>Lojas</i>
     * do BD é <i>Verdadeiro</i>.
     *
     * @return nome
     * @throws SQLException
     */
    public String identificarLojaBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT nome FROM Lojas WHERE estado=1" );
        rs.next();
        return rs.getString( "nome" );
    }

    /**
     * Criação das tabelas.
     * @throws SQLException
     */
    public void iniciarBD()
        throws SQLException
    {
        stm.executeUpdate( "CREATE TABLE Lojas ("
            + "lojaId           integer     PRIMARY KEY NOT NULL, "
            + "nome             varchar(20) NOT NULL UNIQUE, "
            + "estado           boolean     NOT NULL)" ); // 0 - Não ativa, 1 - Ativa
        stm.executeUpdate( "CREATE TABLE Turnos ("
            + "turnoId          integer     PRIMARY KEY NOT NULL, "
            + "nome             varchar(20) NOT NULL UNIQUE)" );
        stm.executeUpdate( "CREATE TABLE Tempo ("
            + "tempoId          integer     PRIMARY KEY NOT NULL, "
            + "nome             varchar(20) NOT NULL UNIQUE)" );
        stm.executeUpdate( "CREATE TABLE Funcionarios ("
            + "funcionarioId    integer     PRIMARY KEY NOT NULL, "
            + "nome             varchar(40) NOT NULL UNIQUE)" );
        stm.executeUpdate( "CREATE TABLE Jornadas ("
            + "jornadaId        integer     PRIMARY KEY NOT NULL, "
            + "lojaId           integer     NOT NULL, "
            + "abertura         varchar(23) NOT NULL, " // "YYYY-MM-DD HH:MM:SS.SSS"
            + "fechamento       varchar(23), " // "YYYY-MM-DD HH:MM:SS.SSS"
            + "turnoId          integer     NOT NULL, "
            + "caixaInicial     float       NOT NULL, "
            + "caixaFinal       float, "
            + "reforcos         float, "
            + "sangrias         float, "
            + "encaminhamento   float, "
            + "tempoId          integer, "
            //+ "numVisComFech    integer, " // Número de visualizações da tela "Comandas Fechadas"
            + "FOREIGN KEY(lojaId)          REFERENCES Lojas(lojaId), "
            + "FOREIGN KEY(turnoId)         REFERENCES Turnos(turnoId), "
            + "FOREIGN KEY(tempoId)         REFERENCES Tempo(tempoId))" );
        stm.executeUpdate( "CREATE TABLE JornadaAberta ("
            + "jornadaId        integer     PRIMARY KEY NOT NULL, "
            + "FOREIGN KEY(jornadaId)       REFERENCES Jornadas(jornadaId))" );
        stm.executeUpdate( "CREATE TABLE FuncionariosJornadas ("
            + "funcionarioId    varchar(40) NOT NULL, "
            + "jornadaId        integer     NOT NULL, "
            + "FOREIGN KEY(funcionarioId)   REFERENCES Funcionarios(funcionarioId), "
            + "FOREIGN KEY(jornadaId)       REFERENCES Jornadas(jornadaId))" );
        stm.executeUpdate( "CREATE TABLE Despesas ("
            + "despesaId        integer     PRIMARY KEY NOT NULL, "
            + "jornadaId        integer     NOT NULL, "
            + "descricao        varchar(30) NOT NULL, "
            + "valor            float       NOT NULL, "
            + "FOREIGN KEY(jornadaId)       REFERENCES Jornadas(jornadaId))" );
        stm.executeUpdate( "CREATE TABLE Comandas ("
            + "comandaId        integer     PRIMARY KEY NOT NULL, "
            + "jornadaId        integer     NOT NULL, "
            + "horario          varchar(23) NOT NULL, " // "YYYY-MM-DD HH:MM:SS.SSS"
            + "valor            float       NOT NULL, "
            + "desconto         float       NOT NULL, "
            + "FOREIGN KEY(jornadaId)       REFERENCES Jornadas(jornadaId))" );
        stm.executeUpdate( "CREATE TABLE ComandasAbertas ("
            + "comandaId        integer     PRIMARY KEY NOT NULL, "
            + "nome             varchar(30) NOT NULL, "
            + "posicao          integer     NOT NULL, "
            + "FOREIGN KEY(comandaId)       REFERENCES Comandas(comandaId))" );
        stm.executeUpdate( "CREATE TABLE ComandaAtiva ("
            + "comandaId        integer     PRIMARY KEY NOT NULL, "
            + "FOREIGN KEY(comandaId)       REFERENCES ComandasAbertas(comandaId))" );
        stm.executeUpdate( "CREATE TABLE Categorias ("
            + "categoriaId      integer     PRIMARY KEY NOT NULL, "
            + "codigo           varchar(3)  NOT NULL UNIQUE, "
            + "nome             varchar(30) NOT NULL UNIQUE)" );
        stm.executeUpdate( "CREATE TABLE Produtos ("
            + "produtoId        integer     PRIMARY KEY NOT NULL, "
            + "categoriaId      integer     NOT NULL, "
            + "codigo           varchar(3), "
            + "nome             varchar(30) NOT NULL, "
            + "preco            float       NOT NULL, "
            + "ativo            boolean     NOT NULL, "
            + "FOREIGN KEY(categoriaId)     REFERENCES Categorias(categoriaId))" );
        stm.executeUpdate( "CREATE TABLE Pedidos ("
            + "pedidoId         integer     PRIMARY KEY NOT NULL, "
            + "comandaId        integer     NOT NULL, "
            + "produtoId        integer     NOT NULL, "
            + "quantidade       float       NOT NULL, "
            + "FOREIGN KEY(comandaId)       REFERENCES Comandas(comandaId), "
            + "FOREIGN KEY(produtoId)       REFERENCES Produtos(produtoId))" );
        stm.executeUpdate( "CREATE TABLE Senhas ("
            + "senha            varchar(30) PRIMARY KEY NOT NULL)" );
        stm.executeUpdate( "CREATE TABLE ListaSolicitacoes ("
            + "listaId          integer     PRIMARY KEY NOT NULL, "            
            + "cb001            boolean, "
            + "cb002            boolean, "
            + "cb003            boolean, "
            + "cb004            boolean, "
            + "cb005            boolean, "
            + "cb006            boolean, "
            + "cb007            boolean, "
            + "cb008            boolean, "
            + "cb009            boolean, "
            + "cb010            boolean, "
            + "cb011            boolean, "
            + "cb012            boolean, "
            + "cb013            boolean, "
            + "cb014            boolean, "
            + "cb015            boolean, "
            + "cb016            boolean, "
            + "cb017            boolean, "
            + "cb018            boolean, "
            + "cb019            boolean, "
            + "cb020            boolean, "
            + "cb021            boolean, "
            + "cb022            boolean, "
            + "cb023            boolean, "
            + "cb024            boolean, "
            + "cb025            boolean, "
            + "cb026            boolean, "
            + "cb027            boolean, "
            + "cb028            boolean, "
            + "cb029            boolean, "
            + "cb030            boolean, "
            + "cb031            boolean, "
            + "cb032            boolean, "
            + "cb033            boolean, "
            + "cb034            boolean, "
            + "cb035            boolean, "
            + "cb036            boolean, "
            + "cb037            boolean, "
            + "cb038            boolean, "
            + "cb039            boolean, "
            + "cb040            boolean, "
            + "cb041            boolean, "
            + "cb042            boolean, "
            + "cb043            boolean, "
            + "cb044            boolean, "
            + "cb045            boolean, "
            + "cb046            boolean, "
            + "cb047            boolean, "
            + "cb048            boolean, "
            + "cb049            boolean, "
            + "cb050            boolean, "
            + "cb051            boolean, "
            + "cb052            boolean, "
            + "cb053            boolean, "
            + "cb054            boolean, "
            + "cb055            boolean, "
            + "cb056            boolean, "
            + "cb057            boolean, "
            + "cb058            boolean, "
            + "cb059            boolean, "
            + "cb060            boolean, "
            + "cb071            boolean, "
            + "cb072            boolean, "
            + "cb073            boolean, "
            + "cb074            boolean, "
            + "cb075            boolean, "
            + "cb076            boolean, "
            + "cb077            boolean, "
            + "cb078            boolean, "
            + "cb079            boolean, "
            + "cb080            boolean, "
            + "cb081            boolean, "
            + "cb082            boolean, "
            + "cb083            boolean, "
            + "cb084            boolean, "
            + "cb085            boolean, "
            + "cb086            boolean, "
            + "cb087            boolean, "
            + "cb088            boolean, "
            + "cb089            boolean, "
            + "cb090            boolean, "
            + "cb091            boolean, "
            + "cb092            boolean, "
            + "cb093            boolean, "
            + "cb094            boolean, "
            + "cb095            boolean, "
            + "cb096            boolean, "
            + "cb097            boolean, "
            + "cb098            boolean, "
            + "cb099            boolean, "
            + "cb100            boolean, "
            + "cb101            boolean, "
            + "cb102            boolean, "
            + "cb103            boolean, "
            + "cb104            boolean, "
            + "cb105            boolean, "
            + "cb106            boolean, "
            + "cb107            boolean, "
            + "cb108            boolean, "
            + "cb109            boolean, "
            + "responsavel      varchar(30), "
            + "tf04             varchar(30), "
            + "tf05             varchar(30), "
            + "tf06             varchar(30), "
            + "tf07             varchar(30), "
            + "tf08             varchar(30), "
            + "tf09             varchar(30), "
            + "tf10             varchar(30), "
            + "tf11             varchar(30), "
            + "tf12             varchar(30), "
            + "tf13             varchar(30), "
            + "tf14             varchar(30), "
            + "tf15             varchar(30), "
            + "tf16             varchar(30), "
            + "ta01             varchar(100))");
        
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 1, \"sorteves\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 2, \"elaborados\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 3, \"picoles\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 4, \"potes\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 5, \"bebidas\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 6, \"drinques\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 7, \"lanches\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 8, \"doces\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 9, \"internet\")" );
        stm.executeUpdate( "INSERT INTO Categorias VALUES ("
            + "NULL, 10, \"outros\")" );
        stm.executeUpdate( "INSERT INTO Turnos VALUES ("
            + "NULL, \"Diurno\")" );
        stm.executeUpdate( "INSERT INTO Turnos VALUES ("
            + "NULL, \"Noturno\")" );
        stm.executeUpdate( "INSERT INTO Turnos VALUES ("
            + "NULL, \"Único\")" );
        stm.executeUpdate( "INSERT INTO Tempo VALUES ("
            + "NULL, \"Ensolarado\")" );
        stm.executeUpdate( "INSERT INTO Tempo VALUES ("
            + "NULL, \"Nublado\")" );
        stm.executeUpdate( "INSERT INTO Tempo VALUES ("
            + "NULL, \"Instável\")" );
        stm.executeUpdate( "INSERT INTO Tempo VALUES ("
            + "NULL, \"Chuvoso\")" );
        stm.executeUpdate( "INSERT INTO Senhas VALUES ("
            + " \"123\" )" );
    }

    public List<Comanda> listarComandasAbertasBD()
        throws SQLException
    {
        Comanda comandaAberta;
        List<Comanda> lista = new ArrayList<Comanda>();
        rs = stm.executeQuery( "SELECT nome, posicao FROM ComandasAbertas" );
        while ( rs.next() )
        {
            comandaAberta = new Comanda(
                rs.getString( "nome" ),
                rs.getInt( "posicao" ) );
            lista.add( comandaAberta );
        }
        return lista;
    }

    /**
     * Lista os elementos da tabela <i>Lojas</i> ou <i>Funcionarios</i>
     * do BD.
     *
     * @param tabela
     * @return
     * @throws SQLException
     */
    public List<String> listarNomesBD( String tabela )
        throws SQLException
    {
        List<String> lista = new ArrayList<String>();
        rs = stm.executeQuery( "SELECT nome FROM " + tabela + " ORDER BY nome" );
        while ( rs.next() )
            lista.add( rs.getString( "nome" ) );
        return lista;
    }

    /**
     * Transferência do pedido informado da comanda cujo Id foi informado para
     * a comanda ativa.
     *
     * @param comandaIdOrigem
     * @param pedidoSelecionado
     * @throws SQLException
     */
    public void moverPedidoBD( int comandaIdOrigem, Pedido pedidoSelecionado )//String strProduto, float fltQtde)
        throws SQLException
    {
        // Identificação do pedido a ser removido:
        rs = stm.executeQuery( "SELECT pedidoId FROM Pedidos "
            + "WHERE comandaId = " + comandaIdOrigem + " "
            + "AND produtoId = " + pedidoSelecionado.getProduto().getId() + " "
            + "AND quantidade = " + pedidoSelecionado.getQtde() );
        rs.next();
        assert (rs.isLast());
        int pedidoId = rs.getInt( "pedidoId" );

        // Transferência do pedido da comanda informada para a atual:
        stm.executeUpdate( "UPDATE Pedidos "
            + "SET comandaId = " + obterComandaIdBD() + " "
            + "WHERE pedidoId = " + pedidoId );

        atualizarTotalComandaBD( comandaIdOrigem );
    }

    public List<Categoria> obterCategoriasBD()
    {
        List<Categoria> lista = new ArrayList<Categoria>();

        lista.add( new Categoria( 1, "1", "Sorvetes" ) );
        lista.add( new Categoria( 2, "2", "Elaborados" ) );
        lista.add( new Categoria( 3, "3", "Picolés" ) );
        lista.add( new Categoria( 4, "4", "Potes" ) );
        lista.add( new Categoria( 5, "5", "Bebidas" ) );
        lista.add( new Categoria( 6, "6", "Drinques" ) );
        lista.add( new Categoria( 7, "7", "Lanches" ) );
        lista.add( new Categoria( 8, "8", "Doces" ) );
        lista.add( new Categoria( 9, "9", "Internet" ) );
        lista.add( new Categoria( 10, "10", "Outros" ) );

        return lista;
    }

    public int obterComandaIdBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT comandaId FROM ComandaAtiva" );
        rs.next();
        return rs.getInt( "comandaId" );
    }

    public float obterDespesas( Jornada jornada )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT SUM(valor) FROM Despesas WHERE "
            + "jornadaId = " + jornada.getId() );
        rs.next();
        return rs.getFloat( 1 );
    }

    public Jornada obterJornadaAbertaBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT jornadaId FROM JornadaAberta" );
        rs.next();

        int jornadaId = rs.getInt( "jornadaId" );

        rs = stm.executeQuery( "SELECT * FROM Jornadas WHERE jornadaId = " + jornadaId );
        rs.next();

        String dataAbertura = ConversorFormato.dataIsoParaBr( rs.getString( "abertura" ) );
        String horaAbertura = ConversorFormato.dataHoraIsoParaHora( rs.getString( "abertura" ) );
        String semanaAbertura = ConversorFormato.dataHoraIsoParaDia( rs.getString( "abertura" ) );
        float caixaInicial = rs.getFloat( "caixaInicial" );
        int lojaId = rs.getInt( "lojaId" );
        int turnoId = rs.getInt( "turnoId" );

        rs = stm.executeQuery( "SELECT nome, estado FROM Lojas WHERE lojaId = " + lojaId );
        rs.next();

        Loja loja = new Loja( lojaId, rs.getString( "nome" ), rs.getBoolean( "estado" ) );

        rs = stm.executeQuery( "SELECT nome FROM Turnos WHERE turnoId = " + turnoId );
        rs.next();

        Turno turno = new Turno( turnoId, rs.getString( "nome" ) );

        rs = stm.executeQuery( "SELECT nome "
            + "FROM Funcionarios "
            + "INNER JOIN FuncionariosJornadas "
            + "ON Funcionarios.funcionarioId = FuncionariosJornadas.funcionarioId "
            + "WHERE jornadaId = " + jornadaId );
        List<String> funcionarios = new ArrayList<String>();
        while ( rs.next() )
            funcionarios.add( rs.getString( "nome" ) );

        Jornada jornada = new Jornada( jornadaId, loja, dataAbertura, horaAbertura,
            semanaAbertura, turno, funcionarios, caixaInicial );

        return jornada;
    }

    public String obterNomeClienteBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT nome FROM ComandasAbertas "
            + "WHERE comandaId = (SELECT comandaId FROM ComandaAtiva)" );
        rs.next();
        return rs.getString( "nome" );
    }

    public Pedido obterPedidoBD( int comandaId, Produto meuProduto, float qtde )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT pedidoId FROM Pedidos "
            + "WHERE comandaId = " + comandaId + " "
            + "AND ( produtoId = " + meuProduto.getId() + " "
            + "AND quantidade = " + qtde + ")" );
        rs.next();
        return new Pedido( rs.getInt( "pedidoId" ), meuProduto, qtde );
    }

    public ArrayList<Pedido> obterPedidosDaComandaAtivaBD()
        throws SQLException
    {
        ArrayList<Pedido> lista = new ArrayList<Pedido>();
        rs = stm.executeQuery( "SELECT quantidade, Produtos.produtoId, nome, preco "
            + "FROM Pedidos "
            + "INNER JOIN Produtos "
            + "ON Pedidos.produtoId = Produtos.produtoId "
            + "WHERE comandaId = (SELECT comandaId FROM ComandaAtiva)" );
        while ( rs.next() )
        {
            Produto meuProduto = new Produto( rs.getInt( 2 ),
                rs.getString( 3 ),
                rs.getFloat( 4 ) );
            Pedido pedido = new Pedido( meuProduto, rs.getFloat( 1 ) );
            lista.add( pedido );
        }
        return lista;
    }

    public int obterPosicaoAtivaBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT posicao FROM ComandasAbertas WHERE "
            + "comandaId = (SELECT comandaId FROM ComandaAtiva)" );
        rs.next();
        return rs.getInt( "posicao" );
    }

    public int obterPosicaoDisponivelBD()
        throws SQLException
    {
        int posicaoCandidata = 1;
        int posicaoOcupada;
        rs = stm.executeQuery( "SELECT posicao FROM ComandasAbertas ORDER BY "
            + "posicao ASC" );
        while ( rs.next() )
        {
            posicaoOcupada = rs.getInt( "posicao" );
            if ( posicaoOcupada != 0 )
            {
                if ( posicaoOcupada == posicaoCandidata )
                    posicaoCandidata++;
                else
                    return posicaoCandidata;
            }
        }
        return posicaoCandidata;
    }

    /**
     * OBSOLETO. Preferir obterProdutoBD( nomeProduto ), que retorna um objeto
     * do tipo Produto com todos os atributos definidos, incluindo o produtoId.
     *
     * @param nomeProduto
     * @return
     * @throws SQLException
     */
    public int obterProdutoIdBD( String nomeProduto )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT produtoId "
            + "FROM Produtos "
            + "WHERE nome = \"" + nomeProduto + "\" "
            + "AND ativo = 1" );
        rs.next();
        assert (rs.isLast());
        return rs.getInt( 1 );
    }

    /**
     * Retorna produto (com todos os atributos definidos) cujo nome foi informado.
     * 
     * @param id
     * @return
     * @throws SQLException
     */
    public Produto obterProdutoBD( int id )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT Categorias.codigo "
            + "FROM Categorias INNER JOIN Produtos "
            + "ON Categorias.categoriaId = Produtos.categoriaId "
            + "WHERE Produtos.produtoId = " + id );
        rs.next();
        String codigoCategoria = rs.getString( "Categorias.codigo" );

        rs = stm.executeQuery( "SELECT codigo, nome, preco "
            + "FROM Produtos "
            + "WHERE produtoId = " + id );
        rs.next();
        assert (rs.isLast());
        return new Produto( id,
            codigoCategoria,
            rs.getString( "codigo" ),
            rs.getString( "nome" ),
            rs.getFloat( "preco" ) );
    }

    /**
     * OBSOLETO: A FUNÇÃO obterProdutoBD JÁ FAZ ESTA OPERAÇÃO!
     * Retorna produto (com todos os atributos definidos) cujo nome foi informado.
     *
     * @param nome
     * @return
     * @throws SQLException
     */
    public Produto obterProdutoBD( String nome )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT Categorias.codigo "
            + "FROM Categorias INNER JOIN Produtos "
            + "ON Categorias.categoriaId = Produtos.categoriaId "
            + "WHERE Produtos.nome = \"" + nome + "\" "
            + "AND Produtos.ativo = 1" );
        rs.next();
        String codigoCategoria = rs.getString( "codigo" );

        rs = stm.executeQuery( "SELECT produtoId, codigo, preco "
            + "FROM Produtos "
            + "WHERE nome = \"" + nome + "\" "
            + "AND ativo = 1" );
        rs.next();
        assert (rs.isLast());

        return new Produto( rs.getInt( "produtoId" ),
            codigoCategoria,
            rs.getString( "codigo" ),
            nome,
            rs.getFloat( "preco" ) );
    }

    /**
     * Lista os produtos da tabela <i>Produtos</i> da categoria informada,
     * mas que o codigo não seja NULL (pois significa que já foi removido).
     *
     * @param codigoCategoria
     * @param codigoProduto
     * @return Lista dos produtos
     * @throws SQLException
     */
    public List<Produto> obterProdutosBD( String codigoCategoria,
        String codigoProduto )
        throws SQLException
    {
        List<Produto> lista = new ArrayList<Produto>();
        String pesquisa =
            "SELECT Produtos.produtoId, Produtos.codigo, Produtos.nome, Produtos.preco "
            + "FROM Produtos "
            + "INNER JOIN Categorias "
            + "ON Categorias.categoriaId = Produtos.categoriaId "
            + "WHERE Categorias.codigo = \"" + codigoCategoria + "\" "
            + "AND Produtos.codigo IS NOT NULL "
            + "AND Produtos.ativo = 1";
        if ( !codigoProduto.isEmpty() )
        {
            pesquisa = pesquisa + " AND Produtos.codigo = \"" + codigoProduto + "\"";
        }
        rs = stm.executeQuery( pesquisa );
        while ( rs.next() )
        {
            Produto produto = new Produto(
                rs.getInt( "produtoId" ),
                codigoCategoria,
                rs.getString( "codigo" ),
                rs.getString( "nome" ),
                rs.getFloat( "preco" ) );
            lista.add( produto );
        }
        return lista;
    }

    /**
     *
     * @param codigoCategoria
     * @return
     * @throws SQLException
     */
    public List<Produto> obterProdutosBD( String codigoCategoria )
        throws SQLException
    {
        return obterProdutosBD( codigoCategoria, "" );
    }

    public float obterReforcos( Jornada jornada )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT reforcos FROM Jornadas "
            + "WHERE jornadaId = " + jornada.getId() );
        rs.next();
        return rs.getFloat( 1 );
    }

    public float obterSangrias( Jornada jornada )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT sangrias FROM Jornadas "
            + "WHERE jornadaId = " + jornada.getId() );
        rs.next();
        return rs.getFloat( 1 );
    }
    
    public ListaSolicitacoes obterListaSolicitacoes()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT * FROM ListaSolicitacoes "
            + "WHERE listaId = 1" );
        rs.next();
        ListaSolicitacoes lista = new ListaSolicitacoes();
        
        lista.setCb001( rs.getBoolean( "cb001" ) );
        lista.setCb002( rs.getBoolean( "cb002" ) );
        lista.setCb003( rs.getBoolean( "cb003" ) );
        lista.setCb004( rs.getBoolean( "cb004" ) );
        lista.setCb005( rs.getBoolean( "cb005" ) );
        lista.setCb006( rs.getBoolean( "cb006" ) );
        lista.setCb007( rs.getBoolean( "cb007" ) );
        lista.setCb008( rs.getBoolean( "cb008" ) );
        lista.setCb009( rs.getBoolean( "cb009" ) );
        lista.setCb010( rs.getBoolean( "cb010" ) );
        lista.setCb011( rs.getBoolean( "cb011" ) );
        lista.setCb012( rs.getBoolean( "cb012" ) );
        lista.setCb013( rs.getBoolean( "cb013" ) );
        lista.setCb014( rs.getBoolean( "cb014" ) );
        lista.setCb015( rs.getBoolean( "cb015" ) );
        lista.setCb016( rs.getBoolean( "cb016" ) );
        lista.setCb017( rs.getBoolean( "cb017" ) );
        lista.setCb018( rs.getBoolean( "cb018" ) );
        lista.setCb019( rs.getBoolean( "cb019" ) );
        lista.setCb020( rs.getBoolean( "cb020" ) );
        lista.setCb021( rs.getBoolean( "cb021" ) );
        lista.setCb022( rs.getBoolean( "cb022" ) );
        lista.setCb023( rs.getBoolean( "cb023" ) );
        lista.setCb024( rs.getBoolean( "cb024" ) );
        lista.setCb025( rs.getBoolean( "cb025" ) );
        lista.setCb026( rs.getBoolean( "cb026" ) );
        lista.setCb027( rs.getBoolean( "cb027" ) );
        lista.setCb028( rs.getBoolean( "cb028" ) );
        lista.setCb029( rs.getBoolean( "cb029" ) );
        lista.setCb030( rs.getBoolean( "cb030" ) );
        lista.setCb031( rs.getBoolean( "cb031" ) );
        lista.setCb032( rs.getBoolean( "cb032" ) );
        lista.setCb033( rs.getBoolean( "cb033" ) );
        lista.setCb034( rs.getBoolean( "cb034" ) );
        lista.setCb035( rs.getBoolean( "cb035" ) );
        lista.setCb036( rs.getBoolean( "cb036" ) );
        lista.setCb037( rs.getBoolean( "cb037" ) );
        lista.setCb038( rs.getBoolean( "cb038" ) );
        lista.setCb039( rs.getBoolean( "cb039" ) );
        lista.setCb040( rs.getBoolean( "cb040" ) );
        lista.setCb041( rs.getBoolean( "cb041" ) );
        lista.setCb042( rs.getBoolean( "cb042" ) );
        lista.setCb043( rs.getBoolean( "cb043" ) );
        lista.setCb044( rs.getBoolean( "cb044" ) );
        lista.setCb045( rs.getBoolean( "cb045" ) );
        lista.setCb046( rs.getBoolean( "cb046" ) );
        lista.setCb047( rs.getBoolean( "cb047" ) );
        lista.setCb048( rs.getBoolean( "cb048" ) );
        lista.setCb049( rs.getBoolean( "cb049" ) );
        lista.setCb050( rs.getBoolean( "cb050" ) );
        lista.setCb051( rs.getBoolean( "cb051" ) );
        lista.setCb052( rs.getBoolean( "cb052" ) );
        lista.setCb053( rs.getBoolean( "cb053" ) );
        lista.setCb054( rs.getBoolean( "cb054" ) );
        lista.setCb055( rs.getBoolean( "cb055" ) );
        lista.setCb056( rs.getBoolean( "cb056" ) );
        lista.setCb057( rs.getBoolean( "cb057" ) );
        lista.setCb058( rs.getBoolean( "cb058" ) );
        lista.setCb059( rs.getBoolean( "cb059" ) );
        lista.setCb060( rs.getBoolean( "cb060" ) );
        lista.setCb071( rs.getBoolean( "cb071" ) );
        lista.setCb072( rs.getBoolean( "cb072" ) );
        lista.setCb073( rs.getBoolean( "cb073" ) );
        lista.setCb074( rs.getBoolean( "cb074" ) );
        lista.setCb075( rs.getBoolean( "cb075" ) );
        lista.setCb076( rs.getBoolean( "cb076" ) );
        lista.setCb077( rs.getBoolean( "cb077" ) );
        lista.setCb078( rs.getBoolean( "cb078" ) );
        lista.setCb079( rs.getBoolean( "cb079" ) );
        lista.setCb080( rs.getBoolean( "cb080" ) );
        lista.setCb081( rs.getBoolean( "cb081" ) );
        lista.setCb082( rs.getBoolean( "cb082" ) );
        lista.setCb083( rs.getBoolean( "cb083" ) );
        lista.setCb084( rs.getBoolean( "cb084" ) );
        lista.setCb085( rs.getBoolean( "cb085" ) );
        lista.setCb086( rs.getBoolean( "cb086" ) );
        lista.setCb087( rs.getBoolean( "cb087" ) );
        lista.setCb088( rs.getBoolean( "cb088" ) );
        lista.setCb089( rs.getBoolean( "cb089" ) );
        lista.setCb090( rs.getBoolean( "cb090" ) );
        lista.setCb091( rs.getBoolean( "cb091" ) );
        lista.setCb092( rs.getBoolean( "cb092" ) );
        lista.setCb093( rs.getBoolean( "cb093" ) );
        lista.setCb094( rs.getBoolean( "cb094" ) );
        lista.setCb095( rs.getBoolean( "cb095" ) );
        lista.setCb096( rs.getBoolean( "cb096" ) );
        lista.setCb097( rs.getBoolean( "cb097" ) );
        lista.setCb098( rs.getBoolean( "cb098" ) );
        lista.setCb099( rs.getBoolean( "cb099" ) );
        lista.setCb100( rs.getBoolean( "cb100" ) );
        lista.setCb101( rs.getBoolean( "cb101" ) );
        lista.setCb102( rs.getBoolean( "cb102" ) );
        lista.setCb103( rs.getBoolean( "cb103" ) );
        lista.setCb104( rs.getBoolean( "cb104" ) );
        lista.setCb105( rs.getBoolean( "cb105" ) );
        lista.setCb106( rs.getBoolean( "cb106" ) );
        lista.setCb107( rs.getBoolean( "cb107" ) );
        lista.setCb108( rs.getBoolean( "cb108" ) );
        lista.setCb109( rs.getBoolean( "cb109" ) );
        lista.setResponsavel( rs.getString( "responsavel" ) );
        lista.setTf04( rs.getString( "tf04" ) );
        lista.setTf05( rs.getString( "tf05" ) );
        lista.setTf06( rs.getString( "tf06" ) );
        lista.setTf07( rs.getString( "tf07" ) );
        lista.setTf08( rs.getString( "tf08" ) );
        lista.setTf09( rs.getString( "tf09" ) );
        lista.setTf10( rs.getString( "tf10" ) );
        lista.setTf11( rs.getString( "tf11" ) );
        lista.setTf12( rs.getString( "tf12" ) );
        lista.setTf13( rs.getString( "tf13" ) );
        lista.setTf14( rs.getString( "tf14" ) );
        lista.setTf15( rs.getString( "tf15" ) );
        lista.setTf16( rs.getString( "tf16" ) );
	lista.setTa01( rs.getString( "ta01" ) );

        return lista;
    }

    public float obterEncaminhamento( Jornada jornada )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT encaminhamento FROM Jornadas "
            + "WHERE jornadaId = " + jornada.getId() );
        rs.next();
        return rs.getFloat( 1 );
    }

    public int obterTempoBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT tempoId FROM Jornadas "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
        rs.next();
        return rs.getInt( "tempoId" );
    }

    public float obterTotalComandaBD()
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT valor FROM Comandas "
            + "WHERE comandaId = (SELECT comandaId FROM ComandaAtiva)" );
        rs.next();
        return rs.getFloat( "valor" );
    }

    /**
     * Obtém o valor total das vendas, em termos monetários, do produto
     * informado pela combinação entre o Código da Categoria e o Código do
     * Produto.
     *
     * @param jornada
     * @param codigoCategoria
     * @return
     * @throws SQLException
     */
    public float obterValorVendidoCategoriaBD( Jornada jornada,
        String codigoCategoria )
        throws SQLException
    {
//        rs = stm.executeQuery( "SELECT SUM(Pedidos.quantidade * Produtos.preco) "
//            + "FROM Pedidos NATURAL JOIN Produtos "
//            + "WHERE comandaId IN (SELECT comandaId FROM Comandas "
//            + "WHERE jornadaId = " + jornada.getId() + " "
//            + "AND comandaId NOT IN (SELECT comandaId FROM ComandasAbertas)) "
//            + "AND categoriaId = (SELECT categoriaId FROM Categorias "
//            + "WHERE codigo = " + codigoCategoria + ")" );
        rs.next();

        rs = stm.executeQuery( "select sum(pe.quantidade * pr.preco) "
            + "from pedidos pe, produtos pr, categorias ca, comandas co, jornadas jo "
            + "where pe.produtoid = pr.produtoid "
            + "and pr.categoriaid = ca.categoriaid "
            + "and ca.codigo = " + codigoCategoria + " "
            + "and pe.comandaid = co.comandaid "
            + "and co.jornadaid = jo.jornadaid "
            + "and jo.jornadaid = " + jornada.getId());

        return rs.getFloat( 1 );
    }

    /**
     * Obtém o valor total das vendas, em termos monetários, do produto
     * informado pela combinação entre o Código da Categoria e o Código do
     * Produto.
     *
     * @param jornada
     * @param codigoCategoria
     * @param codigoProduto
     * @return
     * @throws SQLException
     */
    public float obterValorVendidoProdutoBD( Jornada jornada,
        String codigoCategoria, String codigoProduto )
        throws SQLException
    {
//        rs = stm.executeQuery( "SELECT SUM(Pedidos.quantidade * Produtos.preco) "
//            + "FROM Pedidos NATURAL JOIN Produtos "
//            + "WHERE comandaId IN (SELECT comandaId FROM Comandas "
//            + "WHERE jornadaId = " + jornada.getId() + " "
//            + "AND comandaId NOT IN (SELECT comandaId FROM ComandasAbertas)) "
//            + "AND produtoId = (SELECT produtoId FROM Produtos "
//            + "WHERE codigo = " + codigoProduto + " "
//            + "AND categoriaId = (SELECT categoriaId FROM Categorias "
//            + "WHERE codigo = " + codigoCategoria + ") )" );

        rs = stm.executeQuery( "select sum(pe.quantidade * pr.preco) "
            + "from pedidos pe, produtos pr, categorias ca, comandas co, jornadas jo "
            + "where pe.produtoid = pr.produtoid "
            + "and pr.codigo = " + codigoProduto + " "
            + "and pr.categoriaid = ca.categoriaid "
            + "and ca.codigo = " + codigoCategoria + " "
            + "and pe.comandaid = co.comandaid "
            + "and co.jornadaid = jo.jornadaid "
            + "and jo.jornadaid = " + jornada.getId());

        rs.next();
        return rs.getFloat( 1 );
    }

    /**
     * Obtém a quantidade total das vendas, em termos unitários, do produto
     * informado pela combinação entre o Código da Categoria e o Código do
     * Produto.
     *
     * @param jornada
     * @param codigoCategoria
     * @param codigoProduto
     * @return
     * @throws SQLException
     */
    public float obterQtdeVendidaBD( Jornada jornada, String codigoCategoria,
        String codigoProduto )
        throws SQLException
    {
//        rs = stm.executeQuery( "SELECT SUM(quantidade) FROM Pedidos "
//            + "WHERE comandaId IN (SELECT comandaId FROM Comandas "
//            + "WHERE jornadaId = " + jornada.getId() + " "
//            + "AND comandaId NOT IN (SELECT comandaId FROM ComandasAbertas)) "
//            + "AND produtoId = (SELECT produtoId FROM Produtos "
//            + "WHERE codigo = " + codigoProduto + " "
//            + "AND categoriaId = (SELECT categoriaId FROM Categorias "
//            + "WHERE codigo = " + codigoCategoria + ") )" );

        rs = stm.executeQuery( "select sum(pe.quantidade) "
            + "from pedidos pe, produtos pr, categorias ca, comandas co, jornadas jo "
            + "where pe.produtoid = pr.produtoid "
            + "and pr.codigo = " + codigoProduto + " "
            + "and pr.categoriaid = ca.categoriaid "
            + "and ca.codigo = " + codigoCategoria + " "
            + "and pe.comandaid = co.comandaid "
            + "and co.jornadaid = jo.jornadaid "
            + "and jo.jornadaid = " + jornada.getId());

        rs.next();
        return rs.getFloat( 1 );
    }

    public float obterVendasBD( Jornada jornada )
        throws SQLException
    {
        // Lembrete: A coluna "valor" já é o valor total da compra menos o desconto!
        rs = stm.executeQuery( "SELECT SUM(valor) FROM Comandas "
            + "WHERE jornadaId = " + jornada.getId() + " "
            + "AND comandaId NOT IN (SELECT comandaId FROM ComandasAbertas)" );
        return rs.getFloat( 1 );
    }

    /**
     * Desativa e fecha a comanda atual.
     * Remove a comanda atual do banco de dados.
     * 
     * @throws SQLException
     */
    public void removerComandaBD()
        throws SQLException
    {
        int comandaId = obterComandaIdBD();
        desativarComandaBD();
        removerComandaBD( comandaId );
    }

    /**
     * Desativa e fecha a comanda informada.
     * Remove a comanda atual do banco de dados.
     *
     * @param comandaId
     * @throws SQLException
     */
    public void removerComandaBD( int comandaId )
        throws SQLException
    {
        fecharComandaBD( comandaId );
        stm.executeUpdate( "DELETE FROM Comandas "
            + "WHERE comandaId = " + comandaId );
    }

    public void realizarEncaminhamento( float valor )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE Jornadas SET encaminhamento = " + valor + " "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
    }

    public void realizarReforco( float novoReforco )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT reforcos FROM Jornadas "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
        rs.next();

        float reforcos = rs.getFloat( 1 ) + novoReforco;

        stm.executeUpdate( "UPDATE Jornadas SET reforcos = " + reforcos + " "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
    }

    public void realizarSangria( float novaSangria )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT sangrias FROM Jornadas "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
        rs.next();

        float sangrias = rs.getFloat( 1 ) + novaSangria;

        stm.executeUpdate( "UPDATE Jornadas SET sangrias = " + sangrias + " "
            + "WHERE jornadaId = (SELECT jornadaId FROM JornadaAberta)" );
    }

    /**
     * Adiciona nova linha na tabela <i>FuncionariosJornadas</i> do BD.
     *
     * @param funcionario
     * @throws SQLException
     */
    public void relacionarFuncionarioJornadaBD( String funcionario )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT jornadaId FROM JornadaAberta" );
        rs.next();
        int jornadaId = rs.getInt( "jornadaId" );
        stm.executeUpdate( "INSERT INTO FuncionariosJornadas VALUES ("
            + "(SELECT funcionarioId FROM Funcionarios "
            + "WHERE nome = \"" + funcionario + "\"), "
            + jornadaId + ")" );
    }

    /**
     * Descadastra da tabela <i>Lojas</i> ou <i>Funcionarios</i> do BD
     * o nome da loja ou funcionário.
     *
     * @param nome
     * @param tabela
     * @throws SQLException
     */
    public void removerNomeBD( String nome, String tabela )
        throws SQLException
    {
        stm.executeUpdate( "DELETE FROM " + tabela + " WHERE "
            + "nome=\"" + nome + "\"" );
    }

    /**
     * Remove o pedido através do cruzamento do produtoId, comandaId e
     * quantidade.
     *
     * @param produto
     * @param qtde
     * @throws SQLException
     */
    public void removerPedidoBD( String produto, float qtde )
        throws SQLException
    {
        // Identifica o pedido a ser removido:
        String produtoId = "(SELECT produtoId "
            + "FROM Produtos "
            + "WHERE nome = \"" + produto + "\" "
            + "AND ativo = 1)";
        rs = stm.executeQuery( "SELECT DISTINCT pedidoId FROM Pedidos "
            + "WHERE comandaId = (SELECT comandaId FROM ComandaAtiva) "
            + "AND produtoId = " + produtoId + " "
            + "AND quantidade = " + qtde );
        rs.next();
        assert (rs.isLast() == true);
        int pedidoId = rs.getInt( "pedidoId" );

        // Executa remoção do pedido:
        stm.executeUpdate( "DELETE FROM Pedidos "
            + "WHERE pedidoId = " + pedidoId );

        atualizarTotalComandaBD();
    }

    /**
     * Remove produto do BD.
     *
     * Remove os produto que contiverem o codigoProduto e o codigoCategoria,
     * mas que não tenham sido utilizados na tabela Pedidos. Neste último caso,
     * apenas define como NULL o codigo do produto, para que não seja mais
     * acessado pelo usuário, apenas pela tabela Pedidos.
     *
     * @param codigoCategoria
     * @param codigoProduto
     * @throws SQLException
     */
    public void removerProdutoBD( String codigoCategoria, String codigoProduto )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE Produtos "
            + "SET ativo = 0 "
            + "WHERE codigo = \"" + codigoProduto + "\" "
            + "AND ativo = 1 "
            + "AND categoriaId IN "
            + "(SELECT Categorias.categoriaId "
            + "FROM Categorias "
            + "WHERE Categorias.codigo = \"" + codigoCategoria + "\")" );
    }

    public void setEncaminhamento( Jornada jornada )
        throws SQLException
    {
        stm.executeUpdate( "UPDATE Jornadas "
            + "SET encaminhamento = " + jornada.getEncaminhamento() + " "
            + "WHERE jornadaId = " + jornada.getId() );
    }

    public void transfereComandasAbertasParaJornadaAberta()
        throws SQLException
    {
        stm.executeUpdate( "UPDATE Comandas "
            + "SET jornadaId = (SELECT jornadaId FROM JornadaAberta) "
            + "WHERE comandaId IN (SELECT comandaId FROM ComandasAbertas)" );
    }

    /**
     * Transfere os pedidos da comanda atual para a comanda localizada na
     * posição informada.
     * Desativa, fecha e remove a comanda atual do BD.
     *
     * @param posicao
     * @throws SQLException
     */
    public void unirComandaAtivaBD( int posicao )
        throws SQLException
    {
        rs = stm.executeQuery( "SELECT comandaId FROM ComandasAbertas "
            + "WHERE posicao = " + posicao );
        rs.next();
        int comandaId = rs.getInt( "comandaId" );
        stm.executeUpdate( "UPDATE Pedidos "
            + "SET comandaId = " + comandaId + " "
            + "WHERE comandaId = (SELECT comandaId FROM ComandaAtiva)" );
        removerComandaBD();
    }

    public void unirComandasBD( List<Integer> posicoes )
        throws SQLException
    {
        String strPosicoes = "";
        Iterator<Integer> it = posicoes.iterator();

        while ( it.hasNext() )
            strPosicoes = strPosicoes + ", " + it.next();
        strPosicoes = strPosicoes.replaceFirst( ", ", "" );

        rs = stm.executeQuery( "SELECT comandaId FROM ComandaAtiva" );
        rs.next();
        int comandaAtivaId = rs.getInt( "comandaId" );

        stm.executeUpdate( "UPDATE Pedidos "
            + "SET comandaId = " + comandaAtivaId + " "
            + "WHERE comandaId IN "
            + "(SELECT comandaId FROM ComandasAbertas WHERE posicao IN (" + strPosicoes + "))" );

        it = posicoes.iterator();
        while ( it.hasNext() )
        {
            rs = stm.executeQuery( "SELECT comandaId FROM ComandasAbertas "
                + "WHERE posicao = " + it.next() );
            rs.next();
            if ( rs.getInt( "comandaId" ) != comandaAtivaId )
                removerComandaBD( rs.getInt( "comandaId" ) );
        }
    }
}
