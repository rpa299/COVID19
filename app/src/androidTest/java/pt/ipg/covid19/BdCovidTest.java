package pt.ipg.covid19;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BdCovidTest {
    @Before
    public void apagaBaseDados() {
        getTargetContext().deleteDatabase(BdCovidOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void consegueAbrirBaseDados() {
        // Context of the app under test.
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getReadableDatabase();
        assertTrue(bdCovid.isOpen());
        bdCovid.close();
    }

    private Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    //funções para o perfil
    private long inserePerfil(BdTablePerfil tabelaPerfil, Perfil perfil) {
        long id = tabelaPerfil.insert(Converte.PerfilToContentValues(perfil));
        assertNotEquals(-1, id);
        return id;
    }

    private long inserePerfil(BdTablePerfil tabelaPerfil, String nome, String dataNascimento, String sexo, int altura, float peso, String tipoSangue) {

        Perfil perfil = new Perfil();
        perfil.setNome(nome);
        perfil.setDataNascimento(dataNascimento);
        perfil.setSexo(sexo);
        perfil.setAltura(altura);
        perfil.setPeso(peso);
        perfil.setTipoSangue(tipoSangue);

        return inserePerfil(tabelaPerfil, perfil);
    }

    //função para os sintomas
    private long insereSintoma(SQLiteDatabase bdCovid, String data, String doresCabeca, String doresMusculares, String cansaco,
                               String doresGarganta, String tosse, float temperatura, String respiracao, String corrimentoNasal,
                               String nome, String dataNascimento, String sexo, int altura, float peso, String tipoSangue) {

        BdTablePerfil tabelaPerfil = new BdTablePerfil(bdCovid);

        long idPerfil = inserePerfil(tabelaPerfil, nome, dataNascimento, sexo, altura, peso, tipoSangue);

        Sintoma sintoma = new Sintoma();
        sintoma.setData(data);
        sintoma.setDoresCabeca(doresCabeca);
        sintoma.setDoresMusculares(doresMusculares);
        sintoma.setCansaco(cansaco);
        sintoma.setDoresGarganta(doresGarganta);
        sintoma.setTosse(tosse);
        sintoma.setTemperatura(temperatura);
        sintoma.setRespiracao(respiracao);
        sintoma.setCorrimentoNasal(corrimentoNasal);
        sintoma.setIdPerfil(idPerfil);

        BdTableSintoma tabelaSintoma = new BdTableSintoma(bdCovid);
        long id = tabelaSintoma.insert(Converte.SintomaToContentValues(sintoma));
        assertNotEquals(-1, id);

        return  id;
    }

    //função para os suspeitos ou infetados
    private long insereSusInf(SQLiteDatabase bdCovid, String nomeSusInf, String dataNascimentoSusInf, String dataInfecao,
                               String nome, String dataNascimento, String sexo, int altura, float peso, String tipoSangue) {

        BdTablePerfil tabelaPerfil = new BdTablePerfil(bdCovid);

        long idPerfil = inserePerfil(tabelaPerfil, nome, dataNascimento, sexo, altura, peso, tipoSangue);

        SusInf susInf = new SusInf();
        susInf.setNomeSusInf(nomeSusInf);
        susInf.setDataNascimento(dataNascimentoSusInf);
        susInf.setDataInfecao(dataInfecao);
        susInf.setIdPerfil(idPerfil);

        BdTableSusInf tabelaSusInf = new BdTableSusInf(bdCovid);
        long id = tabelaSusInf.insert(Converte.SusInfToContentValues(susInf));
        assertNotEquals(-1, id);

        return  id;
    }

    //testes para ler/alterar/inserir/eliminar Perfil
    @Test
    public void consegueInserirPerfil(){
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        BdTablePerfil tabelaPerfil = new BdTablePerfil(bdCovid);

        inserePerfil(tabelaPerfil, "Rodrigo","29/09/1999","Masculino",175,75,"A+");

        bdCovid.close();
    }
    @Test
    public void consegueLerPerfil() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        BdTablePerfil tabelaPerfil = new BdTablePerfil(bdCovid);

        Cursor cursor = tabelaPerfil.query(BdTablePerfil.TODOS_CAMPOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        inserePerfil(tabelaPerfil,"Rodrigo","29/09/1999","Masculino",175,75,"A+");

        cursor = tabelaPerfil.query(BdTablePerfil.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bdCovid.close();
    }
    @Test
    public void consegueAlterarPerfil() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        BdTablePerfil tabelaPerfil = new BdTablePerfil(bdCovid);

        Perfil perfil = new Perfil();
        perfil.setNome("Mariana");
        perfil.setDataNascimento("04/01/1999");
        perfil.setSexo("Feminino");
        perfil.setAltura(160);
        perfil.setPeso(50);
        perfil.setTipoSangue("AB-");

        long id = inserePerfil(tabelaPerfil, perfil);

        int registosAfetados = tabelaPerfil.update(Converte.PerfilToContentValues(perfil), BdTablePerfil._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosAfetados);

        bdCovid.close();
    }
    @Test
    public void consegueEliminarSintomas() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        BdTablePerfil tabelaPerfil = new BdTablePerfil(bdCovid);

        long id = inserePerfil(tabelaPerfil,"Mariana","04/01/1999","Feminino",160,50,"AB-");

        int registosEliminados = tabelaPerfil.delete(BdTablePerfil._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bdCovid.close();
    }
}
