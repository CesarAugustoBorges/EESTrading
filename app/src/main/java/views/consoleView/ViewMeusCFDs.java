package views.consoleView;

import business.CFD;
import business.EESTrading;
import business.Utilizador;

import java.util.List;

public class ViewMeusCFDs extends ConsoleView {
    private List<CFD> cfds;

    public ViewMeusCFDs(EESTrading trading, Utilizador utilizador, List<CFD> cfds) {
        super(trading, utilizador);
        this.cfds = cfds;
    }

    @Override
    public String render() {
        layout("Meus CFDs");
        int i;
        for(i = 0; i < cfds.size(); i++){
            System.out.println((i+1)+ "."+ cfds.get(i));
        }
        System.out.println((i+1)+ ".Retroceder");
        int option = getSelectedOption();

        if(isUpdated()){
            boolean yes = yesOrNoQuestion("Alguns dos seus contratos foram atualizados\nQuer dar refresh?");
            if(yes) return MEUS_CFDS;
        }

        if(option > 0 && option <= cfds.size()){
            ConsoleViewManager.setSelectedCFD(cfds.get(i-1).getId());
            return CFD_POSSUIDO;
        }else if(option == i+1){
            return UTILIZADOR;
        }
        else {
            System.out.println("Não existe esse CFD");
            return MEUS_CFDS;
        }
    }
}
