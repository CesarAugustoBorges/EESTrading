package views.consoleView;

import business.Acao;
import business.CFD;
import business.EESTrading;
import business.Utilizador;
import views.IView;
import views.ViewManager;

import java.util.Observable;

public class ConsoleViewManager extends ViewManager {
    public ConsoleViewManager(String view, EESTrading trading){
        super(trading, view);
    }

    private Utilizador utilizador = new Utilizador("", "", 0);

    public static void setSelectedCFD(int cfd) {
        selectedCFD = cfd;
    }

    public static void setSelectedAtivo(String selectedAtiv) {
        selectedAtivo = selectedAtiv;
    }

    private static int selectedCFD;
    private static String selectedAtivo;

    @Override
    protected IView getView(String viewId) {
        if(viewId == null) return null;
        switch (viewId){
            case ConsoleView.INICIAL:
                return new ViewInicial(trading, utilizador);
            case ConsoleView.ATIVOS_DISPONIVEIS:
                return new ViewAtivosDisponiveis(trading, utilizador, trading.getAtivos());
            case ConsoleView.CFD_POSSUIDO:
                return new ViewCFDPossuido(trading, utilizador, trading.getCFD(selectedCFD));
            case ConsoleView.COMPRA_CFD:
                return new ViewCompraCFD(trading, utilizador, trading.getAtivo(selectedAtivo));
            case ConsoleView.DEPOSITAR:
                return new ViewDepositar(trading, utilizador);
            case ConsoleView.LOGIN:
                return new ViewLogin(trading, utilizador);
            case ConsoleView.MEUS_CFDS:
                return new ViewMeusCFDs(trading, utilizador ,trading.getPortfolio(utilizador));
            case ConsoleView.REGISTAR:
                return new ViewRegistar(trading, utilizador);
            case ConsoleView.TRANSACOES_ANTIGAS:
                return new ViewTransacoesAntigas(trading, utilizador, trading.getTransacoesAntigas(utilizador));
            case ConsoleView.UTILIZADOR:
                return new ViewUtilizador(trading, utilizador);
            case ConsoleView.WITHDRAW:
                return new ViewWithdraw(trading, utilizador);
            default: System.out.println("ERROR: No view with name " + viewId + "was found !!! Exiting..." ); return null;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
