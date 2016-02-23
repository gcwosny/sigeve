/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sigeve;

/**
 *
 * @author Paulo Henrique
 */
public class Comanda
{
    private int comandaId;
    private int jornadaId;
    private String nome;
    private int posicao;
    private String horario;
    private float valor;
    private float desconto;

    // Construtor utilizado na exportação dos dados do banco.
    public Comanda()
    {   
    }

    public Comanda(String nome, int posicao)
    {
        this.nome = nome;
        this.posicao = posicao;
    }

    public int getComandaId()
    {
        return comandaId;
    }

    public void setComandaId( int comandaId )
    {
        this.comandaId = comandaId;
    }

    public int getJornadaId()
    {
        return jornadaId;
    }

    public void setJornadaId( int jornadaId )
    {
        this.jornadaId = jornadaId;
    }
    
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getNome()
    {
        return nome;
    }

    public void setPosicao(int posicao)
    {
        this.posicao = posicao;
    }

    public int getPosicao()
    {
        return posicao;
    }

    public float getDesconto()
    {
        return desconto;
    }

    public void setDesconto( float desconto )
    {
        this.desconto = desconto;
    }

    public String getHorario()
    {
        return horario;
    }

    public void setHorario( String horario )
    {
        this.horario = horario;
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
