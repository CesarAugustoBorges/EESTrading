package views.consoleView;

import business.AtivoFinanceiro;
import business.EESTrading;
import business.Utilizador;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewFavoritos extends ConsoleView {
    private List<AtivoFinanceiro> favoritos;


    public ViewFavoritos(EESTrading trading, Utilizador utilizador) {
        super(trading, utilizador);
        this.favoritos = utilizador.getFavoritos();
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
            utilizador.removeFavorito(favoritos.get(option-1));
            trading.update(utilizador);
        }
        return FAVORITOS;
    }
}
