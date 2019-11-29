package views.consoleView;

import business.AtivoFinanceiro;
import business.EESTrading;
import business.Utilizador;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewFavoritos extends ConsoleView {
    private List<AtivoFinanceiro> favoritos;


    public ViewFavoritos(EESTrading trading, Utilizador utilizador, List<AtivoFinanceiro> favoritos) {
        super(trading, utilizador);
        this.favoritos = favoritos;
    }

    @Override
    public String render() {
        System.out.println("0. Retroceder");
        printPage(0, favoritos);
        int option = -1;
        while (option < 0 || (option > favoritos.size() && favoritos.size() > 0)){
            option = getOptionInPage(favoritos);
        }
        if(option == 0) return UTILIZADOR;

        boolean yes = yesOrNoQuestion("Quer remover " + favoritos.get(option-1).getCompany() + " dos favoritos?");
        if(yes){
            trading.removeFavorito(utilizador, favoritos.get(option-1));
        }
        return FAVORITOS;
    }
}
