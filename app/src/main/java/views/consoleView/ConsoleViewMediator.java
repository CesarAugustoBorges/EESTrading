package views.consoleView;

import business.*;
import views.IView;
import views.ViewMediator;

import java.util.List;
import java.util.Observable;

public class ConsoleViewMediator extends ViewMediator {
    public ConsoleViewMediator(String view, EESTrading trading){
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


    private volatile Utilizador utilizador = new Utilizador("", "", 0);
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
            case ConsoleView.FAVORITOS:
                lastiView = new ViewFavoritos(trading, utilizador, trading.getAtivosPreferidos(utilizador));
                break;
            default: System.out.println("ERROR: No view with name " + viewId + "was found !!! Exiting..." ); return null;
        }
        //clearConsole();
        return lastiView;
    }

    private void clearConsole(){
        String os = System.getProperty("os.name");
        if(os.toUpperCase().contains("WINDOWS")){
            String[] cls = new String[] {"cmd.exe", "/c", "cls"};
            ProcessBuilder builder = new ProcessBuilder(cls);
            builder.inheritIO();
            try{
                builder.start().waitFor();
            } catch (Exception e){ }
        }
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
        if(utilizador != null && ! utilizador.getUsername().equals("")){
            if(arg instanceof CFDVendido){
                CFDVendido cfdVendido = (CFDVendido) arg;
                System.out.println(cfdVendido + "foi vendido (atingiu treshold)");
            }
            if(arg instanceof AtivoFinanceiro){
                AtivoFinanceiro ativoFinanceiro = (AtivoFinanceiro) arg;
                if(trading.getFavoritos(utilizador).contains(arg))//(utilizador.hasFavorito(ativoFinanceiro))
                    System.out.println("O ativo financeiro " + ativoFinanceiro.getCompany() + " sofreu uma alteração significativa");
            }
        }

        Utilizador util = trading.login(utilizador.getUsername(), utilizador.getPassword());
        if(util != null){
            utilizador.deconstruct(util);
        }
    }
}
