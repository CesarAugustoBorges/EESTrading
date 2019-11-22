package views.consoleView;

import business.EESTrading;
import business.Utilizador;

public class ViewUtilizador extends ConsoleView {
    public ViewUtilizador(EESTrading trading, Utilizador utilizador) {
        super(trading, utilizador);
    }

    @Override
    public String render() {
        layout("Olá " + utilizador.getUsername() + " - " + utilizador.getMoney() + "$");
        System.out.println("1.Comprar CFD");
        System.out.println("2.Ver portfolio");
        System.out.println("3.Ver transições antigas");
        System.out.println("4.Depositar dinheiro");
        System.out.println("5.Levantar dinheiro");
        System.out.println("6.Sair");

        int option = getSelectedOption();
        switch (option){
            case 1: return ATIVOS_DISPONIVEIS;
            case 2: return MEUS_CFDS;
            case 3: return TRANSACOES_ANTIGAS;
            case 4: return DEPOSITAR;
            case 5: return WITHDRAW;
            case 6:
                this.utilizador = null;
                return INICIAL;
            default:
                System.out.println("ERROR: Não é uma opção válida");
                return UTILIZADOR;
        }
    }
}