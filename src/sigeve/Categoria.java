/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sigeve;

/**
 *
 * @author Paulo Henrique
 */
public class Categoria {

    private int id;
    private String codigoCategoria;
    private String nome;

    public Categoria( int id, String codigoCategoria, String nome )
    {
        this.id = id;
        this.codigoCategoria = codigoCategoria;
        this.nome = nome;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getCodigoCategoria()
    {
        return codigoCategoria;
    }

    public void setCodigoCategoria( String codigoCategoria )
    {
        this.codigoCategoria = codigoCategoria;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome( String nome )
    {
        this.nome = nome;
    }
}
