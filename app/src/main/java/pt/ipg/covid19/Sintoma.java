package pt.ipg.covid19;

import java.io.Serializable;

public class Sintoma implements Serializable {
    private long id = -1;
    private String data;
    private String doresCabeca;
    private String doresMusculares;
    private String cansaco;
    private String doresGarganta;
    private String Tosse;
    private float temperatura;
    private String respiracao;
    private String corrimentoNasal;
    private long idPerfil = -1;
    private String NomePerfil = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDoresCabeca() {
        return doresCabeca;
    }

    public void setDoresCabeca(String doresCabeca) {
        this.doresCabeca = doresCabeca;
    }

    public String getDoresMusculares() {
        return doresMusculares;
    }

    public void setDoresMusculares(String doresMusculares) {
        this.doresMusculares = doresMusculares;
    }

    public String getCansaco() {
        return cansaco;
    }

    public void setCansaco(String cansaco) {
        this.cansaco = cansaco;
    }

    public String getDoresGarganta() {
        return doresGarganta;
    }

    public void setDoresGarganta(String doresGarganta) {
        this.doresGarganta = doresGarganta;
    }

    public String getTosse() {
        return Tosse;
    }

    public void setTosse(String tosse) {
        Tosse = tosse;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public String getRespiracao() {
        return respiracao;
    }

    public void setRespiracao(String respiracao) {
        this.respiracao = respiracao;
    }

    public String getCorrimentoNasal() {
        return corrimentoNasal;
    }

    public void setCorrimentoNasal(String corrimentoNasal) {
        this.corrimentoNasal = corrimentoNasal;
    }

    public long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(long idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNomePerfil() {
        return NomePerfil;
    }

    public void setNomePerfil(String nomePerfil) {
        NomePerfil = nomePerfil;
    }
}
