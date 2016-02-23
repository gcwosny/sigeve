/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sigeve;

/**
 *
 * @author Paulo Henrique
 */
public class Produto
{
    private int id;
    private String codigoCategoria;
    private String codigoProduto;
    private String nome;
    private float preco;
    
    public Produto(
        int id,
        String codigoCategoria,
        String codigoProduto,
        String nome,
        float preco )
    {
        this.id = id;
        this.codigoCategoria = codigoCategoria;
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.preco = preco;
    }
    
    public Produto(
        String codigoCategoria,
        String codigoProduto,
        String nome,
        float preco )
    {
        this.codigoCategoria = codigoCategoria;
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * 
     * @param id
     * @param nome
     * @param preco
     */
    public Produto(
        int id,
        String nome,
        float preco )
    {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    /*
     * Construtor usado na inicialização dos botões de escolha de produtos,
     * após a seleção de uma dada categoria.
     */
    public Produto(
        String codigoProduto,
        String nome )
    {
        this.codigoCategoria = "_";
        this.codigoProduto = codigoProduto;
        this.nome = nome;
        this.preco = 0;
    }

//    public Produto()
//    {
//    }

    public void setCodigoCategoria( String codigoCategoria )
    {
        this.codigoCategoria = codigoCategoria;
    }

    public String getCodigoCategoria()
    {
        return codigoCategoria;
    }

    public void setCodigoProduto( String codigoProduto )
    {
        this.codigoProduto = codigoProduto;
    }

    public String getCodigoProduto()
    {
        return codigoProduto;
    }

    public void setNome( String nome )
    {
        this.nome = nome;
    }

    public String getNome()
    {
        return nome;
    }

    public void setPreco( float preco )
    {
        this.preco = preco;
    }

    public float getPreco()
    {
        return preco;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }
}
