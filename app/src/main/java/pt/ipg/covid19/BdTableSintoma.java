package pt.ipg.covid19;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTableSintoma implements BaseColumns {
    public static final String NOME_TABELA = "Sintoma";

    public static final String CAMPO_DATA = "Data";
    public static final String CAMPO_DORES_CABECA = "DoresCabeca";
    public static final String CAMPO_DORES_MUSCULARES = "DoresMusculares";
    public static final String CAMPO_CANSACO = "Cansaco";
    public static final String CAMPO_DORES_GARGANTA = "DoresGarganta";
    public static final String CAMPO_TOSSE = "Tosse";
    public static final String CAMPO_TEMPERATURA = "Temperatura";
    public static final String CAMPO_RESPIRACAO = "Respiracao";
    public static final String CAMPO_CORRIMENTO_NASAL = "CorrimentoNasal";
    public static final String CAMPO_PERFIL = "Perfil";
    public static final String CAMPO_ID_PERFIL = "IdPerfil";

    public static final String CAMPO_ID_COMPLETO = NOME_TABELA + "." + _ID;
    public static final String CAMPO_DATA_COMPLETO = NOME_TABELA + "." + CAMPO_DATA;
    public static final String CAMPO_DORES_CABECA_COMPLETO = NOME_TABELA + "." + CAMPO_DORES_CABECA;
    public static final String CAMPO_DORES_MUSCULARES_COMPLETO = NOME_TABELA + "." + CAMPO_DORES_MUSCULARES;
    public static final String CAMPO_CANSACO_COMPLETO = NOME_TABELA + "." + CAMPO_CANSACO;
    public static final String CAMPO_DORES_GARGANTA_COMPLETO = NOME_TABELA + "." + CAMPO_DORES_GARGANTA;
    public static final String CAMPO_TOSSE_COMPLETO = NOME_TABELA + "." + CAMPO_TOSSE;
    public static final String CAMPO_TEMPERATURA_COMPLETO = NOME_TABELA + "." + CAMPO_TEMPERATURA;
    public static final String CAMPO_RESPIRACAO_COMPLETO = NOME_TABELA + "." + CAMPO_RESPIRACAO;
    public static final String CAMPO_CORRIMENTO_NASAL_COMPLETO = NOME_TABELA + "." + CAMPO_CORRIMENTO_NASAL;
    public static final String CAMPO_PERFIL_COMPLETO = BdTablePerfil.CAMPO_NOME_COMPLETO + "." + CAMPO_PERFIL;
    public static final String CAMPO_ID_PERFIL_COMPLETO = NOME_TABELA + "." + CAMPO_ID_PERFIL;

    public static final String[] TODOS_CAMPOS = {CAMPO_ID_COMPLETO, CAMPO_DATA_COMPLETO, CAMPO_DORES_CABECA_COMPLETO, CAMPO_DORES_MUSCULARES_COMPLETO, CAMPO_CANSACO_COMPLETO, CAMPO_DORES_GARGANTA_COMPLETO, CAMPO_TOSSE_COMPLETO, CAMPO_TEMPERATURA_COMPLETO, CAMPO_RESPIRACAO_COMPLETO, CAMPO_CORRIMENTO_NASAL_COMPLETO};

    private SQLiteDatabase db;

    public BdTableSintoma(SQLiteDatabase db) {
        this.db = db;
    }

    public void cria(){
        db.execSQL("CREATE TABLE " + NOME_TABELA + " ("+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_DATA + " TEXT NOT NULL," +
                CAMPO_DORES_CABECA + " TEXT NOT NULL," +
                CAMPO_DORES_MUSCULARES + " TEXT NOT NULL," +
                CAMPO_CANSACO + " TEXT NOT NULL," +
                CAMPO_DORES_GARGANTA + " TEXT NOT NULL," +
                CAMPO_TOSSE + " TEXT NOT NULL," +
                CAMPO_TEMPERATURA + " FLOAT NOT NULL," +
                CAMPO_RESPIRACAO + " TEXT NOT NULL," +
                CAMPO_CORRIMENTO_NASAL + " TEXT NOT NULL," +
                CAMPO_ID_PERFIL + " INTEGER NOT NULL," +
                "FOREIGN KEY (" + CAMPO_ID_PERFIL + ") REFERENCES " +
                BdTablePerfil.NOME_TABELA + "(" + BdTablePerfil._ID + ")" +
                ")");
    }

    /**
     * Convenience method for inserting a row into the database.
     *
     * @param values this map contains the initial column values for the
     *            row. The keys should be the column names and the values the
     *            column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    /**
     * Query the given table, returning a {@link Cursor} over the result set.
     *
     * @param columns A list of which columns to return. Passing null will
     *            return all columns, which is discouraged to prevent reading
     *            data from storage that isn't going to be used.
     * @param selection A filter declaring which rows to return, formatted as an
     *            SQL WHERE clause (excluding the WHERE itself). Passing null
     *            will return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be
     *         replaced by the values from selectionArgs, in order that they
     *         appear in the selection. The values will be bound as Strings.
     * @param groupBy A filter declaring how to group rows, formatted as an SQL
     *            GROUP BY clause (excluding the GROUP BY itself). Passing null
     *            will cause the rows to not be grouped.
     * @param having A filter declare which row groups to include in the cursor,
     *            if row grouping is being used, formatted as an SQL HAVING
     *            clause (excluding the HAVING itself). Passing null will cause
     *            all row groups to be included, and is required when row
     *            grouping is not being used.
     * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
     *            (excluding the ORDER BY itself). Passing null will use the
     *            default sort order, which may be unordered.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that
     * {@link Cursor}s are not synchronized, see the documentation for more details.
     * @see Cursor
     */
    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        if (!Arrays.asList(columns).contains(CAMPO_PERFIL_COMPLETO)) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTablePerfil.NOME_TABELA;
        sql += " ON " + CAMPO_ID_PERFIL_COMPLETO + "=" + BdTablePerfil.CAMPO_ID_COMPLETO;

        if (selection != null) {
            sql += " WHERE " + selection;
        }

        if (groupBy != null) {
            sql += " GROUP BY " + groupBy;

            if (having != null) {
                sql += " HAVING " + having;
            }
        }

        if (orderBy != null) {
            sql += " ORDER BY " + orderBy;
        }

        return db.rawQuery(sql, selectionArgs);
    }
    /**
     * Convenience method for updating rows in the database.
     *
     * @param values a map from column names to new column values. null is a
     *            valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *            Passing null will update all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected
     */
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param whereClause the optional WHERE clause to apply when deleting.
     *            Passing null will delete all rows.
     * @param whereArgs You may include ?s in the where clause, which
     *            will be replaced by the values from whereArgs. The values
     *            will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     *         otherwise. To remove all rows and get a count pass "1" as the
     *         whereClause.
     */
    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
