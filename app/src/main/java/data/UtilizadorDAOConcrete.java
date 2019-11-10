package data;

import business.CFD;

import java.util.List;

public class UtilizadorDAOConcrete implements UtilizadorDAO {

    @Override
    public double buyCFD(int user, int cfd) {
        return 0;
    }

    @Override
    public double sellCFD(int user, int cfd) {
        return 0;
    }

    @Override
    public void putPortfolio(int user, int cfd) {

    }

    @Override
    public void registCFDBougth(int user, int cfd) {

    }

    @Override
    public List<CFD> getLastCFDBought(int user, int maxSize) {
        return null;
    }

    @Override
    public void put(Integer obj) {

    }

    @Override
    public Integer get(UtilizadorDAO id) {
        return null;
    }

    @Override
    public void delete(UtilizadorDAO id) {

    }

    @Override
    public void replace(UtilizadorDAO id, Integer obj) {

    }
}