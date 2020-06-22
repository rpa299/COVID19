package pt.ipg.covid19;

public class SusInf {
    private long id = -1;
    private String nome;
    private String dataNascimento;
    private String dataInfecao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataInfecao() {
        return dataInfecao;
    }

    public void setDataInfecao(String dataInfecao) {
        this.dataInfecao = dataInfecao;
    }
}
