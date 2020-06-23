package pt.ipg.covid19;

import android.content.ContentValues;

public class Converte {
    //para a tabela sintoma
    public static ContentValues SintomaToContentValues(Sintoma sintoma) {
        ContentValues valores = new ContentValues();

        valores.put(BdTableSintoma.CAMPO_DATA, sintoma.getData());
        valores.put(BdTableSintoma.CAMPO_DORES_CABECA, sintoma.getDoresCabeca());
        valores.put(BdTableSintoma.CAMPO_DORES_MUSCULARES, sintoma.getDoresMusculares());
        valores.put(BdTableSintoma.CAMPO_CANSACO, sintoma.getCansaco());
        valores.put(BdTableSintoma.CAMPO_DORES_GARGANTA, sintoma.getDoresGarganta());
        valores.put(BdTableSintoma.CAMPO_TOSSE, sintoma.getTosse());
        valores.put(BdTableSintoma.CAMPO_TEMPERATURA, sintoma.getTemperatura());
        valores.put(BdTableSintoma.CAMPO_RESPIRACAO, sintoma.getRespiracao());
        valores.put(BdTableSintoma.CAMPO_CORRIMENTO_NASAL, sintoma.getCorrimentoNasal());

        return valores;
    }

    public static Sintoma contentValuesToSintoma(ContentValues valores) {
        Sintoma sintoma = new Sintoma();

        sintoma.setId(valores.getAsLong(BdTableSintoma._ID));
        sintoma.setData(valores.getAsString(BdTableSintoma.CAMPO_DATA));
        sintoma.setDoresCabeca(valores.getAsString(BdTableSintoma.CAMPO_DORES_CABECA));
        sintoma.setDoresMusculares(valores.getAsString(BdTableSintoma.CAMPO_DORES_MUSCULARES));
        sintoma.setCansaco(valores.getAsString(BdTableSintoma.CAMPO_CANSACO));
        sintoma.setDoresGarganta(valores.getAsString(BdTableSintoma.CAMPO_DORES_GARGANTA));
        sintoma.setTosse(valores.getAsString(BdTableSintoma.CAMPO_TOSSE));
        sintoma.setTemperatura(valores.getAsFloat(BdTableSintoma.CAMPO_TEMPERATURA));
        sintoma.setRespiracao(valores.getAsString(BdTableSintoma.CAMPO_RESPIRACAO));
        sintoma.setCorrimentoNasal(valores.getAsString(BdTableSintoma.CAMPO_CORRIMENTO_NASAL));

        return sintoma;
    }

    //para a tabela perfil
    public static ContentValues PerfilToContentValues(Perfil perfil) {
        ContentValues valores = new ContentValues();

        valores.put(BdTablePerfil.CAMPO_NOME, perfil.getNome());
        valores.put(BdTablePerfil.CAMPO_DATA_NASCIMENTO, perfil.getDataNascimento());
        valores.put(BdTablePerfil.CAMPO_SEXO, perfil.getSexo());
        valores.put(BdTablePerfil.CAMPO_ALTURA, perfil.getAltura());
        valores.put(BdTablePerfil.CAMPO_PESO, perfil.getPeso());
        valores.put(BdTablePerfil.CAMPO_TIPO_SANGUE, perfil.getTipoSangue());

        return valores;
    }

    public static Perfil contentValuesToPerfil(ContentValues valores) {
        Perfil perfil = new Perfil();

        perfil.setId(valores.getAsLong(BdTablePerfil._ID));
        perfil.setNome(valores.getAsString(BdTablePerfil.CAMPO_NOME));
        perfil.setDataNascimento(valores.getAsString(BdTablePerfil.CAMPO_DATA_NASCIMENTO));
        perfil.setSexo(valores.getAsString(BdTablePerfil.CAMPO_SEXO));
        perfil.setAltura(valores.getAsInteger(BdTablePerfil.CAMPO_ALTURA));
        perfil.setPeso(valores.getAsFloat(BdTablePerfil.CAMPO_PESO));
        perfil.setTipoSangue(valores.getAsString(BdTablePerfil.CAMPO_TIPO_SANGUE));

        return perfil;
    }

    //para a tabela susinf
    public static ContentValues SusInfToContentValues(SusInf susInf) {
        ContentValues valores = new ContentValues();

        valores.put(BdTableSusInf.CAMPO_NOME_SUS_INF, susInf.getNomeSusInf());
        valores.put(BdTableSusInf.CAMPO_DATA_INFECAO, susInf.getDataInfecao());
        valores.put(BdTableSusInf.CAMPO_DATA_NASCIMENTO, susInf.getDataNascimento());

        return valores;
    }

    public static SusInf contentValuesToSusInf(ContentValues valores) {
        SusInf susInf = new SusInf();

        susInf.setId(valores.getAsLong(BdTableSusInf._ID));
        susInf.setNomeSusInf(valores.getAsString(BdTableSusInf.CAMPO_NOME_SUS_INF));
        susInf.setDataInfecao(valores.getAsString(BdTableSusInf.CAMPO_DATA_INFECAO));
        susInf.setDataNascimento(valores.getAsString(BdTableSusInf.CAMPO_DATA_NASCIMENTO));

        return susInf;
    }
}
