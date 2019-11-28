package views.consoleView;

import business.CFD;
import business.EESTrading;
import business.Utilizador;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewMeusCFDs extends ConsoleView {
    private List<CFD> cfds;

    public ViewMeusCFDs(EESTrading trading, Utilizador utilizador, List<CFD> cfds) {
        super(trading, utilizador);
        this.cfds = cfds;
    }

    @Override
    public String render() {
        layout("Meus CFDs");
        System.out.println("0.Retroceder");
        printPage(0, cfds);

        if(isUpdated()){
            boolean yes = yesOrNoQuestion("Alguns dos seus contratos foram atualizados\nQuer dar refresh?");
            if(yes) return MEUS_CFDS;
        }

        int option = 0;
        boolean optionSelected = false;
        while (!optionSelected){
            String input = scanner.nextLine();
            if(input.matches("[0-9]+")){
                option = Integer.parseInt(input);
                optionSelected = true;
            } else if(input.matches("[ ]*:[ ]*page[ ]+[0-9]+[ ]*")){
                Pattern pattern = Pattern.compile("[ ]*:[ ]*page[ ]+([0-9]+)[ ]*");
                Matcher matcher = pattern.matcher(input);
                if(matcher.find()) {
                    int pageNumber = Integer.parseInt(matcher.group(1));
                    System.out.println("0.Retroceder");
                    printPage(pageNumber, cfds);
                }
            }
        }

        if(option > 0 && option <= cfds.size()){
            ConsoleViewMediator.setSelectedCFD(cfds.get(option-1).getId());
            return CFD_POSSUIDO;
        }else if(option == 0){
            return UTILIZADOR;
        }
        else {
            System.out.println("Não existe esse CFD");
            return MEUS_CFDS;
        }
    }
}
