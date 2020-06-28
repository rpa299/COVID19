package pt.ipg.covid19;

import android.content.ContentValues;
import android.database.Cursor;

public class Perfil {
    private long id = -1;
    private String nome;
    private String dataNascimento;
    private String sexo;
    private int altura;
    private float peso;
    private String tipoSangue;

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getTipoSangue() {
        return tipoSangue;
    }

    public void setTipoSangue(String tipoSangue) {
        this.tipoSangue = tipoSangue;
    }

    public ContentValues getContentValues(){
        ContentValues valores = new ContentValues();
        valores.put(BdTablePerfil.CAMPO_NOME, nome);
        valores.put(BdTablePerfil.CAMPO_DATA_NASCIMENTO, dataNascimento);
        valores.put(BdTablePerfil.CAMPO_SEXO,sexo);
        valores.put(BdTablePerfil.CAMPO_ALTURA,altura);
        valores.put(BdTablePerfil.CAMPO_PESO,peso);
        valores.put(BdTablePerfil.CAMPO_TIPO_SANGUE,tipoSangue);
        return valores;
    }

    public static Perfil fromCursor(Cursor cursor){

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTablePerfil._ID)
        );
        String Nome = cursor.getString(
                cursor.getColumnIndex(BdTablePerfil.CAMPO_NOME)
        );
        String DataNascimento = cursor.getString(
                cursor.getColumnIndex(BdTablePerfil.CAMPO_DATA_NASCIMENTO)
        );
        String Sexo = cursor.getString(
                cursor.getColumnIndex(BdTablePerfil.CAMPO_SEXO)
        );
        int Altura = cursor.getInt(
                cursor.getColumnIndex(BdTablePerfil.CAMPO_ALTURA)
        );
        float Peso = cursor.getInt(
                cursor.getColumnIndex(BdTablePerfil.CAMPO_PESO)
        );
        String TipoSangue = cursor.getString(
                cursor.getColumnIndex(BdTablePerfil.CAMPO_TIPO_SANGUE)
        );

        Perfil perfil = new Perfil();

        perfil.setId(id);
        perfil.setNome(Nome);
        perfil.setDataNascimento(DataNascimento);
        perfil.setSexo(Sexo);
        perfil.setAltura(Altura);
        perfil.setPeso(Peso);
        perfil.setTipoSangue(TipoSangue);

        return perfil;
    }
}
