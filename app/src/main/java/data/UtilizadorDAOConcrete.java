package data;

import business.CFD;
import business.Utilizador;

import java.util.List;

public class UtilizadorDAOConcrete implements UtilizadorDAO {


    @Override
    public double addCFD(Utilizador user, CFD cfd) {
        return 0;
    }

    @Override
    public double removeCFD(Utilizador user, CFD cfd) {
        return 0;
    }

    @Override
    public void putPortfolio(Utilizador user, CFD cfd) {

    }

    @Override
    public List<CFD> getLastCFDBought(Utilizador user, int maxSize) {
        return null;
    }

    @Override
    public void addMoney(Utilizador user, double value) {

    }

    @Override
    public boolean removeMoney(Utilizador user, double value) {
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }


    @Override
    public Integer put(Utilizador obj) {
        return null;
    }

    @Override
    public Utilizador get(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void replace(Integer id, Utilizador obj) {

    }
}