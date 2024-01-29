package devandroid.wesley.appgaseta.view.controller;


import android.content.ContentValues;
import android.content.SharedPreferences;

import java.util.List;

import devandroid.wesley.appgaseta.view.MainActivity;
import devandroid.wesley.appgaseta.view.database.GasEtaDB;
import devandroid.wesley.appgaseta.view.model.Combustivel;

public class CombustivelController extends GasEtaDB {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor dadosPreferences;
    public static final String NOME_PREFERENCES = "pref_gaseta";

    public CombustivelController(MainActivity mainActivity) {
        super(mainActivity);
        sharedPreferences = mainActivity.getSharedPreferences(NOME_PREFERENCES, 0);
        dadosPreferences = sharedPreferences.edit();
    }
    public void salvar(Combustivel combustivel){
        ContentValues dados = new ContentValues();
        dadosPreferences.putString("combustivel",combustivel.getNomeDoCombustivel());
        dadosPreferences.putString("recomendacao",combustivel.getRecomendacao());
        dadosPreferences.apply();

        dados.put("nomeDoCombustivel", combustivel.getNomeDoCombustivel());
        dados.put("precoDoCombustivel", combustivel.getPrecoDoCombustivel());
        dados.put("recomendacao", combustivel.getRecomendacao());



        salvarObjeto("Combustivel", dados);
    }
    public List<Combustivel> getListaDeDados(){
        return listarDados();
    }
    public void alterar(Combustivel combustivel){

        ContentValues dados = new ContentValues();

        dados.put("id",combustivel.getId());
        dados.put("nomeDoCombustivel", combustivel.getNomeDoCombustivel());
        dados.put("precoDoCombustivel", combustivel.getPrecoDoCombustivel());
        dados.put("recomendacao", combustivel.getRecomendacao());

        alterarObjeto("Combustivel",dados);

    }
    public void deletar(int id){
        deletarObjeto("Combustivel",id);
    }

    public void limpar(){
        dadosPreferences.clear();
        dadosPreferences.apply();
    }
}
