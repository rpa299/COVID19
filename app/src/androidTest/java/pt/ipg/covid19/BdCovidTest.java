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

    private long inserePerfil(BdTablePerfil tabelaPerfil, String nome, String dataNascimento, String sexo, int altura, int peso, String tipoSangue) {

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
                               String nome, String dataNascimento, String sexo, int altura, int peso, String tipoSangue) {

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
                               String nome, String dataNascimento, String sexo, int altura, int peso, String tipoSangue) {

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
    public void consegueEliminarPerfil() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        BdTablePerfil tabelaPerfil = new BdTablePerfil(bdCovid);

        long id = inserePerfil(tabelaPerfil,"Mariana","04/01/1999","Feminino",160,50,"AB-");

        int registosEliminados = tabelaPerfil.delete(BdTablePerfil._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bdCovid.close();
    }


    //testes para ler/alterar/inserir/eliminar Sintoma
    @Test
    public void consegueInserirSintoma() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        insereSintoma(bdCovid, "24/06/2020", "sim", "sim", "sim", "sim", "não", 37, "normal", "sim",
                        "Rodrigo", "29/09/1999", "Masculino", 175, 75, "A+");

        bdCovid.close();
    }
    @Test
    public void consegueLerSintoma() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        BdTableSintoma tabelaSintoma = new BdTableSintoma(bdCovid);

        Cursor cursor = tabelaSintoma.query(BdTableSintoma.TODOS_CAMPOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereSintoma(bdCovid, "24/06/2020", "sim", "sim", "sim", "sim", "não", 37, "normal", "sim",
                "Rodrigo", "29/09/1999", "Masculino", 175, 75, "A+");

        cursor = tabelaSintoma.query(BdTableSintoma.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bdCovid.close();
    }
    @Test
    public void consegueAlterarSintoma() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        long idPerfil = insereSintoma(bdCovid, "24/06/2020", "sim", "sim", "sim", "sim", "não", 37, "normal", "sim",
                "Rodrigo", "29/09/1999", "Masculino", 175, 75, "A+");

        BdTableSintoma tabelaSintoma= new BdTableSintoma(bdCovid);

        Cursor cursor = tabelaSintoma.query(BdTableSintoma.TODOS_CAMPOS, BdTableSintoma.CAMPO_ID_COMPLETO + "=?", new String[]{ String.valueOf(idPerfil) }, null, null, null);
        assertEquals(1, cursor.getCount());

        assertTrue(cursor.moveToNext());
        Sintoma sintoma = Converte.cursorToSintoma(cursor);
        cursor.close();

        assertEquals("sim", sintoma.getDoresCabeca());

        sintoma.setDoresGarganta("não");
        int registosAfetados = tabelaSintoma.update(Converte.SintomaToContentValues(sintoma), BdTableSintoma.CAMPO_ID_COMPLETO + "=?", new String[]{String.valueOf(sintoma.getId())});
        assertEquals(1, registosAfetados);

        bdCovid.close();
    }
    @Test
    public void consegueEliminarSintoma() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        long id = insereSintoma(bdCovid, "24/06/2020", "sim", "sim", "sim", "sim", "não", 37, "normal", "sim",
                "Rodrigo", "29/09/1999", "Masculino", 175, 75, "A+");

        BdTableSintoma tabelaSintoma = new BdTableSintoma(bdCovid);
        int registosEliminados = tabelaSintoma.delete(BdTableSintoma._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bdCovid.close();
    }


    ////testes para ler/alterar/inserir/eliminar SusInf
    @Test
    public void consegueInserirSusInf() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        insereSusInf(bdCovid,"David","01/01/2020","20/06/2020",
                        "Rodrigo","29/09/1999","Masculino",175,75,"A+");

        bdCovid.close();
    }
    @Test
    public void consegueLerSusInf() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        BdTableSusInf tabelaSusInf = new BdTableSusInf(bdCovid);

        Cursor cursor = tabelaSusInf.query(BdTableSusInf.TODOS_CAMPOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereSusInf(bdCovid,"David","01/01/2020","20/06/2020",
                "Rodrigo","29/09/1999","Masculino",175,75,"A+");

        cursor = tabelaSusInf.query(BdTableSusInf.TODOS_CAMPOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bdCovid.close();
    }
    @Test
    public void consegueAlterarSusInf() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        long idPerfil = insereSusInf(bdCovid,"David","01/01/2020","20/06/2020",
                "Rodrigo","29/09/1999","Masculino",175,75,"A+");

        BdTableSusInf tabelaSusInf = new BdTableSusInf(bdCovid);

        Cursor cursor = tabelaSusInf.query(BdTableSusInf.TODOS_CAMPOS, BdTableSusInf.CAMPO_ID_COMPLETO + "=?", new String[]{ String.valueOf(idPerfil) }, null, null, null);
        assertEquals(1, cursor.getCount());

        assertTrue(cursor.moveToNext());
        SusInf susInf = Converte.cursorToSusInf(cursor);
        cursor.close();

        assertEquals("David", susInf.getNomeSusInf());

        susInf.setNomeSusInf("João");
        int registosAfetados = tabelaSusInf.update(Converte.SusInfToContentValues(susInf), BdTableSusInf.CAMPO_ID_COMPLETO + "=?", new String[]{String.valueOf(susInf.getId())});
        assertEquals(1, registosAfetados);

        bdCovid.close();
    }
    @Test
    public void consegueEliminarSusInf() {
        Context appContext = getTargetContext();

        BdCovidOpenHelper openHelper = new BdCovidOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        long id = insereSusInf(bdCovid,"David","01/01/2020","20/06/2020",
                "Rodrigo","29/09/1999","Masculino",175,75,"A+");

        BdTableSusInf tabelaSusInf = new BdTableSusInf(bdCovid);
        int registosEliminados = tabelaSusInf.delete(BdTableSusInf._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bdCovid.close();
    }
}
