/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sigeve;

/**
 *
 * @author Paulo Henrique
 */
public class Despesa {

    private int id;
    private String descricao;
    private float valor;

    public Despesa( int id, String descricao, float valor )
    {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao( String descricao )
    {
        this.descricao = descricao;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public float getValor()
    {
        return valor;
    }

    public void setValor( float valor )
    {
        this.valor = valor;
    }

}
