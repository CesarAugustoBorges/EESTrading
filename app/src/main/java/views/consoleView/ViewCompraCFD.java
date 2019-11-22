package views.consoleView;

import business.AtivoFinanceiro;
import business.CFD;
import business.EESTrading;
import business.Utilizador;

public class ViewCompraCFD extends ConsoleView {
    private AtivoFinanceiro ativo;

    public ViewCompraCFD(EESTrading trading, Utilizador utilizador, AtivoFinanceiro ativoFinanceiro) {
        super(trading, utilizador);
        this.ativo = ativoFinanceiro;
    }

    @Override
    public String render() {
        CFD cfd = new CFD();
        cfd.setUtilizador(utilizador);
        cfd.setAtivoFinanceiro(ativo);

        layout(utilizador.getUsername() + " CFD: " + ativo.getCompany() +  " " + ativo.getValue() + " $");
        System.out.print("Valor de compra: ");
        double valor = getDouble();
        cfd.setBoughtValue(valor);
        cfd.setUnits(valor / ativo.getValue());
        System.out.print("Deseja definir um Top Profit? [Y/N] ");
        String answer = scanner.next();
        if(answer.equals("Y")){
            System.out.print("Introduza o valor: ");
            double topProfit = getDouble();
            cfd.setTopProfit(topProfit);
        } else cfd.setTopProfit(0);
        System.out.print("Deseja definir um Stop Profit= [Y/N] ");
        answer = scanner.next();
        if(answer.equals("Y")){
            System.out.print("Introduza o valor: ");
            double stoploss = getDouble();
            cfd.setStopLoss(stoploss);
        } else cfd.setStopLoss(0);

        if(isUpdated()){
            boolean yes = yesOrNoQuestion("O valor atual do CFD foi alterado, quer dar refresh?");
            if(yes) return COMPRA_CFD;
        }


        if(trading.buy(utilizador, cfd)){
            System.out.println("CFD bought sucessfully");
        } else {
            System.out.println("CFD not bought");
        }
        return UTILIZADOR;
    }
}
