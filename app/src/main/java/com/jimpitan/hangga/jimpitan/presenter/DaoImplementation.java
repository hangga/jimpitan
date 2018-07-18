package com.jimpitan.hangga.jimpitan.presenter;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.jimpitan.hangga.jimpitan.db.DbHelper;
import com.jimpitan.hangga.jimpitan.db.model.Nom;
import com.jimpitan.hangga.jimpitan.db.model.Warga;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sayekti on 7/3/18.
 */

public class DaoImplementation implements DaoPresenter {

    private Context context;
    private DbHelper dbHelper;
    private Dao<Warga,Integer> wargas;
    private Dao<Nom,Integer> noms;

    public DaoImplementation(Context ctx){
        this.context = ctx;
        dbHelper = new DbHelper(ctx);
        try {
            wargas = dbHelper.getWargas();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public int insert(Warga warga) {
        try {
            return wargas.create(warga);
        }catch (SQLException ex){
            return 0;
        }
    }

    @Override
    public int insert(Nom nom) {
        try {
            return noms.create(nom);
        }catch (SQLException ex){
            return 0;
        }
    }

    @Override
    public int update(Warga warga, int id) {
        try {
            UpdateBuilder<Warga,Integer> updateBuilder = wargas.updateBuilder();
            updateBuilder.where().eq("id", id);
            updateBuilder.updateColumnValue("name", warga.getName());

            return updateBuilder.update();
        }catch (SQLException ex){
            //SysLog.getInstance().sendLog(DAOPresenterImpl.class.getSimpleName(),"ex : error : "+ex.getMessage());
            return 0;
        }
    }

    @Override
    public int delete(int id) {
        try {
            return wargas.deleteById(id);
        }catch (SQLException ex){
            return 0;
        }
    }

    @Override
    public Warga getWarga(int id) {
        try {
            return wargas.queryForEq("id", id).get(0);
        }catch (SQLException ex){
            return null;
        }
    }

    @Override
    public List<Warga> getWargas() {
        try {
            return wargas.queryForAll();
        }catch (SQLException ex){
            return null;
        }
    }

    @Override
    public List<Nom> getNoms() {
        try {
            return noms.queryForAll();
        }catch (SQLException ex){
            return null;
        }
    }
}
