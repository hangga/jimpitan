package com.jimpitan.hangga.jimpitan.presenter;

import com.jimpitan.hangga.jimpitan.model.Warga;

import java.util.List;

/**
 * Created by sayekti on 7/3/18.
 */

public interface DaoPresenter {
    int insert(Warga value);
    int update(Warga value,int id);
    int delete(int id);
    Warga getWarga(int id);
    List<Warga> getWargas();
}
