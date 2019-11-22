package views.consoleView;

import business.CFD;
import business.EESTrading;
import business.Utilizador;

import java.util.List;

public class ViewTransacoesAntigas extends ConsoleView {
    private List<CFD> cfds;
    public ViewTransacoesAntigas(EESTrading trading, Utilizador utilizador, List<CFD> cfds) {
        super(trading, utilizador);
        this.cfds = cfds;
    }

    @Override
    public String render() {
        layout("Transações antigas");
        int i;
        for(i = 0; i < cfds.size(); i++){
            CFD cfd = cfds.get(i);
            System.out.println((i+1)+".( "+ cfd.getData() + ") " + cfd.getName() + " - " + cfd.getValue());
        }
        System.out.println((i+1)+ ".Retroceder");
        int option = getSelectedOption();
        if(option > 0 && option <= cfds.size()){
            return CFD_POSSUIDO;
        }else if(option == i+1){
            return UTILIZADOR;
        }
        else {
            System.out.println("Não existe esse CFD");
            return TRANSACOES_ANTIGAS;
        }
    }
}
