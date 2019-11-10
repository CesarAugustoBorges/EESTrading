package data;

import business.CFD;
import business.Utilizador;

import java.util.List;

public class CFDDAOConcrete implements CFDDAO {

    @Override
    public double sell(int id) {
        return 0;
    }

    @Override
    public double getValue(int id) {
        return 0;
    }

    @Override
    public List<CFD> get(Utilizador user) {
        return null;
    }

    @Override
    public Integer put(CFD obj) {
        return null;
    }

    @Override
    public CFD get(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void replace(Integer id, CFD obj) {

    }
}