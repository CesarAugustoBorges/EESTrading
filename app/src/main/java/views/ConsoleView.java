package views;

import business.AtivoFinanceiro;
import business.CFD;
import business.Utilizador;

import java.util.*;

public class ConsoleView extends View {
    Scanner scanner = new Scanner(System.in);

    public void layout(String title){
        int width = 50;
        StringBuilder res = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < width; i++)
            sb.append('-');
        sb.append('\n');
        String s = sb.toString();
        res.append(s);
        int i = 0;
        sb = new StringBuilder();
        for(; i < ((width - (title.length()+2)) / 2); i++ )
            sb.append('-');
        sb.append(' ');
        sb.append(title);
        sb.append(' ');
        i+= title.length() + 2;
        for(; i < width; i++)
            sb.append('-');
        sb.append('\n');
        res.append(sb.toString());
        res.append(s);
        System.out.println(res.toString());
    }

    public void menuInicial() {
        layout("Menu Inicial");
        System.out.println("1.Login");
        System.out.println("2.Registar");
        System.out.println("3.Sair");
    }

    public void menuRegistar() {
        layout("Menu Inicial");
        System.out.print("Username:");
        String username = scanner.next();
        System.out.print("Password");
        String password = scanner.next();
    }

    /**
     *
     * @param utilizador
     * @param cfd
     */
    public void menuCFDPossuido(Utilizador utilizador, CFD cfd) {
        // TODO - implement ConsoleView.menuCFDPossuido
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param utilizador
     * @param ativos
     */
    public void menuAtivosDisponiveis(Utilizador utilizador, List<AtivoFinanceiro> ativos) {
        layout(utilizador.getUsername() + " $: " + utilizador.getMoney());
        System.out.println("0.Retroceder");
        for(int i = 0; i < ativos.size(); i++){
            AtivoFinanceiro ativo = ativos.get(i);
            //Adicionar o tipo do ativo?
            System.out.println((i+1) + ". " + ativo.getCompany() + "- " + ativo.getValue() + " $" );
        }
    }

    /**
     *
     * @param utilizador
     * @param cfd
     */
    public void menuDeCompraCFD(Utilizador utilizador, CFD cfd) {
        layout(utilizador.getUsername() + " CFD: " + cfd.getName() +  " " + cfd.getValue() + " $");
        System.out.print("Valor de compra: ");
        String valor = scanner.next();
        System.out.print("Deseja definir um Top Profit? [Y/N] ");
        String answer = scanner.next();
        if(answer.equals("Y")){
            System.out.print("Introduza o valor: ");
            String value = scanner.next();
        }
        System.out.print("Deseja definir um Stop Profit= [Y/N] ");
        answer = scanner.next();
        if(answer.equals("Y")){
            System.out.print("Introduza o valor: ");
            String value = scanner.next();
        }
    }

    /**
     *
     * @param utilizador
     * @param cfds
     */
    public void menuMeusCFDs(Utilizador utilizador, List<CFD> cfds) {
        // TODO - implement ConsoleView.menuMeusCFDs
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param utilizador
     */
    public void menuUtilizador(Utilizador utilizador) {
        layout("Olá " + utilizador.getUsername());
        System.out.println("1.Comprar CFD");
        System.out.println("2.Ver portfolio");
        System.out.println("3.Ver CFDs em posse");
        System.out.println("4.Depositar dinheiro");
        System.out.println("5.Levantar dinheiro");
        System.out.println("6.Sair");
    }

    public static void main(String[] args) {
        View view = new ConsoleView();
        view.menuInicial();
    }
}