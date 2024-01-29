package devandroid.wesley.appgaseta.view.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import devandroid.wesley.appgaseta.view.model.Combustivel;

public class GasEtaDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "gaseta_db";
    private static final int DB_VERSION = 1;
    Cursor cursor;
    SQLiteDatabase db;

    public GasEtaDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query sql para criar tabela
        String sqlTabelaCombustivel = "CREATE TABLE Combustivel (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nomeDoCombustivel TEXT, " +
                "precoDocombustivel REAL, " +
                "recomendacao TEXT)";

        db.execSQL(sqlTabelaCombustivel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvarObjeto(String tabela,
                             ContentValues dados) {
        db.insert(tabela,null,dados);

    }
    public List<Combustivel> listarDados(){

        List<Combustivel> lista = new ArrayList<>();

        // Representa um registro que está salvo na tabela
        // Combustível do Banco de Dados da Aplicação

        Combustivel registro;

        String querySQL = "SELECT * FROM Combustivel";

        cursor = db.rawQuery(querySQL,null);

        if (cursor.moveToFirst()){
            // True
            do {

                registro = new Combustivel();

                registro.setId(cursor.getInt(0));
                registro.setNomeDoCombustivel(cursor.getString(1));
                registro.setPrecoDoCombustivel(cursor.getDouble(2));
                registro.setRecomendacao(cursor.getString(3));


                lista.add(registro);


            }while(cursor.moveToNext());


        }else{
            // False
        }


        return lista;
    }

    public void alterarObjeto(String tabela, ContentValues dados){
        int id = dados.getAsInteger("id");
        db.update(tabela,dados,"id=?",new String[]{Integer.toString(id)});
    }
    public void deletarObjeto(String tabela, int id){
        db.delete(tabela,"id=?",new String[]{Integer.toString(id)});
    }

}
