package pt.ipg.covid19;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CovidContentProvider extends ContentProvider {
    private static final String AUTHORITY = "pt.ipg.covid19";
    private static final String PERFIL = "perfil";
    private static final String SINTOMAS = "sintomas";
    private static final String SUSINF = "SuspeitoInfetado";

    private static final Uri ENDERECO_BASE = Uri.parse("content://" + AUTHORITY);
    public static final Uri ENDERECO_PERFIL = Uri.withAppendedPath(ENDERECO_BASE, PERFIL);
    public static final Uri ENDERECO_SINTOMAS = Uri.withAppendedPath(ENDERECO_BASE, SINTOMAS);
    public static final Uri ENDERECO_SUSINF = Uri.withAppendedPath(ENDERECO_BASE, SUSINF);

    private static final int URI_PERFIL = 200;
    private static final int URI_ID_PERFIL = 201;

    private static final int URI_SINTOMAS = 300;
    private static final int URI_ID_SINTOMAS = 301;

    private static final int URI_SUSINF = 400;
    private static final int URI_ID_SUSINF = 401;

    private static final String CURSOR_DIR = "vnd.android.cursor.dir/";
    private static final String CURSOR_ITEM = "vnd.android.cursor.item/";

    private BdCovidOpenHelper openHelper;

    private UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, PERFIL, URI_PERFIL);
        uriMatcher.addURI(AUTHORITY, PERFIL + "/#", URI_ID_PERFIL);

        uriMatcher.addURI(AUTHORITY, SINTOMAS, URI_SINTOMAS);
        uriMatcher.addURI(AUTHORITY, SINTOMAS + "/#", URI_ID_SINTOMAS);

        uriMatcher.addURI(AUTHORITY, SUSINF, URI_SUSINF);
        uriMatcher.addURI(AUTHORITY, SUSINF + "/#", URI_ID_SUSINF);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        openHelper = new BdCovidOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd = openHelper.getReadableDatabase();
        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_PERFIL:
                return new BdTablePerfil(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_PERFIL:
                return new BdTablePerfil(bd).query(projection, BdTablePerfil._ID + "=?", new String[]{id}, null, null, sortOrder);

            case URI_SINTOMAS:
                return new BdTableSintoma(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_SINTOMAS:
                return new BdTableSintoma(bd).query(projection, BdTableSintoma._ID + "=?", new String[]{id}, null, null, sortOrder);

            case URI_SUSINF:
                return new BdTableSusInf(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_SUSINF:
                return new BdTableSusInf(bd).query(projection, BdTableSusInf._ID + "=?", new String[]{id}, null, null, sortOrder);

            default:
                throw new UnsupportedOperationException("Endereço query inválido: " + uri.getPath());
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int codigoUri = getUriMatcher().match(uri);

        switch (codigoUri) {
            case URI_PERFIL:
                return CURSOR_DIR + PERFIL;
            case URI_ID_PERFIL:
                return CURSOR_ITEM + PERFIL;
            case URI_SINTOMAS:
                return CURSOR_DIR + SINTOMAS;
            case URI_ID_SINTOMAS:
                return CURSOR_ITEM + SINTOMAS;
            case URI_SUSINF:
                return CURSOR_DIR + SUSINF;
            case URI_ID_SUSINF:
                return CURSOR_ITEM + SUSINF;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();
        long id;

        switch (getUriMatcher().match(uri)) {
            case URI_PERFIL:
                id = (new BdTablePerfil(bd).insert(values));
                break;
            case URI_SINTOMAS:
                id = (new BdTableSintoma(bd).insert(values));
                break;
            case URI_SUSINF:
                id = (new BdTableSusInf(bd).insert(values));
                break;
            default:
                throw new UnsupportedOperationException("Endereço insert inválido: " + uri.getPath());
        }

        if (id == -1) {
            throw new SQLException("Não foi possível inserir o registo: " + uri.getPath());
        }

        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();
        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_ID_PERFIL:
                return new BdTablePerfil(bd).delete(BdTablePerfil._ID + "=?", new String[]{id});
            case URI_ID_SINTOMAS:
                return new BdTableSintoma(bd).delete(BdTableSintoma._ID + "=?", new String[] { id });
            case URI_ID_SUSINF:
                return new BdTableSusInf(bd).delete(BdTableSusInf._ID + "=?", new String[] { id });
            default:
                throw new UnsupportedOperationException("Endereço delete inválido: " + uri.getPath());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();
        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_ID_PERFIL:
                return new BdTablePerfil(bd).update(values, BdTablePerfil._ID + "=?", new String[] { id });
            case URI_ID_SINTOMAS:
                return new BdTableSintoma(bd).update(values,BdTableSintoma._ID + "=?", new String[] { id });
            case URI_ID_SUSINF:
                return new BdTableSusInf(bd).update(values,BdTableSusInf._ID + "=?", new String[] { id });
            default:
                throw new UnsupportedOperationException("Endereço de update inválido: " + uri.getPath());
        }
    }
}
