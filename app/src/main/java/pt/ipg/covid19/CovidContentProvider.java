package pt.ipg.covid19;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
