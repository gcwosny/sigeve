/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sigeve;

/**
 *
 * @author Paulo Henrique
 */
public class Loja {

    private int id;
    private String nome;
    private boolean estado; // Se é a loja ativa ou não

    public Loja( int id, String nome, boolean estado )
    {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
    }

    public boolean isEstado()
    {
        return estado;
    }

    public void setEstado( boolean estado )
    {
        this.estado = estado;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
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
