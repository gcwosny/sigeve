/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sigeve;

/**
 *
 * @author Paulo Henrique
 */
public class Pedido
{
    private int id;
    private int comandaId;
    private Produto produto;
    private float qtde;

    // Construtor utilizado na exportação da tabela.
    public Pedido()
    {
    }
    
    public Pedido( int id, Produto produto, float qtde )
    {
        this.id = id;
        this.produto = produto;
        this.qtde = qtde;
    }

    public Pedido( Produto produto, float qtde )
    {
        this.produto = produto;
        this.qtde = qtde;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public int getComandaId()
    {
        return comandaId;
    }

    public void setComandaId( int comandaId )
    {
        this.comandaId = comandaId;
    }
    
    public void setProduto( Produto produto )
    {
        this.produto = produto;
    }

    public Produto getProduto()
    {
        return produto;
    }

    public void setQtde( float qtde )
    {
        this.qtde = qtde;
    }

    public float getQtde()
    {
        return qtde;
    }
}
