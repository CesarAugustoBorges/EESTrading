package views;

import business.EESTrading;
import business.Utilizador;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

public abstract class ViewManager implements Observer {
    private String currentView;
    protected EESTrading trading;

    public ViewManager(EESTrading trading, String initialView){
        this.trading = trading;
        this.currentView = initialView;
    }

    protected abstract IView getView(String s);

    public void start(){
        trading.addObserver(this);
        IView view = getView(currentView);
        while (view != null)
            view = getView(view.render());
    }

    @Override
    public abstract void update(Observable o, Object arg);
}
