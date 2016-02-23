/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sigeve;

import java.util.List;

/**
 *
 * @author Paulo Henrique
 */
public class Jornada
{
    private int id;
    private Loja loja;
    private String dataAbertura;
    private String horaAbertura;
    private String semanaAbertura;
    private String dataFechamento;
    private String horaFechamento;
    private Turno turno;
    private List<String> funcionarios;
    private float caixaInicial;
    private float caixaFinal;
    private float reforcos;
    private float sangrias;
    private float despesas;
    private float encaminhamento;

    public Jornada()
    {
    }

    public Jornada( int id, Loja loja, String dataAbertura, String horaAbertura,
        String semanaAbertura, Turno turno,
        List<String> funcionarios, float caixaInicial )
    {
        this.id = id;
        this.loja = loja;
        this.dataAbertura = dataAbertura;
        this.horaAbertura = horaAbertura;
        this.semanaAbertura = semanaAbertura;
        this.turno = turno;
        this.funcionarios = funcionarios;
        this.caixaInicial = caixaInicial;

        this.reforcos = 0;
        this.sangrias = 0;
        this.encaminhamento = 0;
        this.despesas = 0;
    }

    public float getCaixaFinal()
    {
        return caixaFinal;
    }

    public void setCaixaFinal( float caixaFinal )
    {
        this.caixaFinal = caixaFinal;
    }

    public float getCaixaInicial()
    {
        return caixaInicial;
    }

    public void setCaixaInicial( float caixaInicial )
    {
        this.caixaInicial = caixaInicial;
    }

    public String getDataAbertura()
    {
        return dataAbertura;
    }

    public void setDataAbertura( String dataAbertura )
    {
        this.dataAbertura = dataAbertura;
    }

    public String getDataFechamento()
    {
        return dataFechamento;
    }

    public void setDataFechamento( String dataFechamento )
    {
        this.dataFechamento = dataFechamento;
    }

    public float getEncaminhamento()
    {
        return encaminhamento;
    }

    public void setEncaminhamento( float encaminhamento )
    {
        this.encaminhamento = encaminhamento;
    }

    public List<String> getFuncionarios()
    {
        return funcionarios;
    }

    public void setFuncionarios( List<String> funcionarios )
    {
        this.funcionarios = funcionarios;
    }

    public String getHoraAbertura()
    {
        return horaAbertura;
    }

    public void setHoraAbertura( String horaAbertura )
    {
        this.horaAbertura = horaAbertura;
    }

    public String getHoraFechamento()
    {
        return horaFechamento;
    }

    public void setHoraFechamento( String horaFechamento )
    {
        this.horaFechamento = horaFechamento;
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public Loja getLoja()
    {
        return loja;
    }

    public void setLoja( Loja loja )
    {
        this.loja = loja;
    }

    public float getReforcos()
    {
        return reforcos;
    }

    public void setReforcos( float reforcos )
    {
        this.reforcos = reforcos;
    }

    public float getSangrias()
    {
        return sangrias;
    }

    public void setSangrias( float sangrias )
    {
        this.sangrias = sangrias;
    }

    public float getDespesas()
    {
        return despesas;
    }

    public void setDespesas( float despesas )
    {
        this.despesas = despesas;
    }

    public String getSemanaAbertura()
    {
        return semanaAbertura;
    }

    public void setSemanaAbertura( String semanaAbertura )
    {
        this.semanaAbertura = semanaAbertura;
    }

    public Turno getTurno()
    {
        return turno;
    }

    public void setTurno( Turno turno )
    {
        this.turno = turno;
    }
    
    /**
     * Cadastra as informações necessárias para o fechamento da jornada.
     *
     * @param dataFechamento
     * @param horaFechamento
     * @param caixaFinal
     * @param reforcos
     * @param sangrias
     * @param despesas
     */
    public void fechar( String dataFechamento, String horaFechamento,
        float caixaFinal, float reforcos, float sangrias, float despesas )
    {
        this.dataFechamento = dataFechamento;
        this.horaFechamento = horaFechamento;
        this.caixaFinal = caixaFinal;
        this.reforcos = reforcos;
        this.sangrias = sangrias;
        this.despesas = despesas;
    }
}
