package pt.ipg.covid19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BdCovidOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "covid.db";
    private static final int VERSAO_BASE_DADOS = 1;
    private static final boolean DESENVOLVIMENTO = false;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     */
    public BdCovidOpenHelper(@Nullable Context context) {
        super(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        BdTablePerfil tabelaPerfil = new BdTablePerfil(db);
        tabelaPerfil.cria();

        BdTableSintoma tabelaSintoma = new BdTableSintoma(db);
        tabelaSintoma.cria();

        BdTableSusInf tabelaSusInf = new BdTableSusInf(db);
        tabelaSusInf.cria();

        if (DESENVOLVIMENTO) {
            seedData(db);
        }
    }

    private void seedData(SQLiteDatabase db) {

        BdTablePerfil tabelaPerfil = new BdTablePerfil(db);

        Perfil perfil = new Perfil();

        perfil.setNome("Rodrigo Almeida");
        perfil.setDataNascimento("29/09/1999");
        perfil.setSexo("Masculino");
        perfil.setAltura(175);
        perfil.setPeso(76);
        perfil.setTipoSangue("A+");
        tabelaPerfil.insert(Converte.PerfilToContentValues(perfil));
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
