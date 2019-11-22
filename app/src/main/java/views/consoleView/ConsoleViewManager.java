package views.consoleView;

import business.*;
import views.IView;
import views.ViewManager;

import java.util.List;
import java.util.Observable;

public class ConsoleViewManager extends ViewManager {
    public ConsoleViewManager(String view, EESTrading trading){
        super(trading, view);
    }


    public static void setSelectedCFD(int cfd) {
        selectedCFD = cfd;
    }

    public static void setSelectedAtivo(String selectedAtiv) {
        selectedAtivo = selectedAtiv;
    }

    private static int selectedCFD;
    private static String selectedAtivo;


    private Utilizador utilizador = new Utilizador("", "", 0);
    private ConsoleView lastiView;
    private String lastViewString;
    private CFD lastCFD;
    private AtivoFinanceiro lastAtivo;


    @Override
    protected IView getView(String viewId) {
        if(viewId == null) return null;
        lastViewString = viewId;
        switch (viewId){
            case ConsoleView.INICIAL:
                lastiView = new ViewInicial(trading, utilizador);
                break;
            case ConsoleView.ATIVOS_DISPONIVEIS:
                lastiView = new ViewAtivosDisponiveis(trading, utilizador, trading.getAtivos());
                break;
            case ConsoleView.CFD_POSSUIDO:
                lastCFD = trading.getCFD(selectedCFD);
                lastiView = new ViewCFDPossuido(trading, utilizador, lastCFD );
                break;
            case ConsoleView.COMPRA_CFD:
                lastAtivo = trading.getAtivo(selectedAtivo);
                lastiView = new ViewCompraCFD(trading, utilizador, lastAtivo );
                break;
            case ConsoleView.DEPOSITAR:
                lastiView = new ViewDepositar(trading, utilizador);
                break;
            case ConsoleView.LOGIN:
                lastiView = new ViewLogin(trading, utilizador);
                break;
            case ConsoleView.MEUS_CFDS:
                lastiView = new ViewMeusCFDs(trading, utilizador ,trading.getPortfolio(utilizador));
                break;
            case ConsoleView.REGISTAR:
                lastiView = new ViewRegistar(trading, utilizador);
                break;
            case ConsoleView.TRANSACOES_ANTIGAS:
                lastiView = new ViewTransacoesAntigas(trading, utilizador, trading.getTransacoesAntigas(utilizador));
                break;
            case ConsoleView.UTILIZADOR:
                lastiView = new ViewUtilizador(trading, utilizador);
                break;
            case ConsoleView.WITHDRAW:
                lastiView = new ViewWithdraw(trading, utilizador);
                break;
            default: System.out.println("ERROR: No view with name " + viewId + "was found !!! Exiting..." ); return null;
        }
        return lastiView;
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if(arg instanceof List){
            List list = (List) arg;
            if(list.size() > 0 && list.get(0) instanceof AtivoFinanceiro){
                List<AtivoFinanceiro> ativos = (List<AtivoFinanceiro>) list;
                switch (lastViewString){
                    case ConsoleView.ATIVOS_DISPONIVEIS:
                        lastiView.setUpdated(true);
                        break;
                    case ConsoleView.CFD_POSSUIDO:
                        for(AtivoFinanceiro f : ativos){
                            if(lastCFD.getAtivoFinanceiro().equals(f)){
                                lastiView.setUpdated(true);
                                break;
                            }
                        }
                        break;
                    case ConsoleView.COMPRA_CFD:
                        for(AtivoFinanceiro f : ativos){
                            if(lastAtivo.equals(f)){
                                lastiView.setUpdated(true);
                                break;
                            }
                        }
                        break;
                    case ConsoleView.MEUS_CFDS:
                        List<CFD> cfds = trading.getPortfolio(utilizador);
                        for(CFD cfd : cfds){
                            for(AtivoFinanceiro f : ativos){
                                if(cfd.getAtivoFinanceiro().equals(f)){
                                    lastiView.setUpdated(true);
                                    break;
                                }
                            }
                        }
                }
            }
        }
    }
}