package views.consoleView;

import business.EESTrading;
import business.Utilizador;
import views.IView;

import java.util.Scanner;
import java.util.function.Consumer;

public abstract class ConsoleView implements IView {
    public static final String ATIVOS_DISPONIVEIS = "ativosDisponiveis";
    public static final String CFD_POSSUIDO = "cfdPossuido";
    public static final String COMPRA_CFD = "compraCFD";
    public static final String DEPOSITAR = "depositar";
    public static final String INICIAL = "inicial";
    public static final String LOGIN = "login";
    public static final String MEUS_CFDS = "meusCFDS";
    public static final String REGISTAR = "registar";
    public static final String TRANSACOES_ANTIGAS = "transacoesAntigas";
    public static final String UTILIZADOR = "utilizador";
    public static final String WITHDRAW = "withdraw";
    public static final String EXIT = null;


    protected EESTrading trading;
    protected Scanner scanner;
    protected Utilizador utilizador;
    private boolean abortRead = false;
    private volatile boolean updated = false;

    public ConsoleView(EESTrading trading, Utilizador utilizador){
        this.scanner = new Scanner(System.in);
        this.utilizador = utilizador;
        this.trading = trading;
    }

    protected void layout(String title){
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

    protected int getSelectedOption(){
        try {
            return scanner.nextInt();
        } catch (Exception e){
            e.printStackTrace();
            clearInput();
            System.out.println("ERROR: Insira apenas um número na consola");
            return getSelectedOption();
        }
    }

    protected double getDouble(){
        try{
            return scanner.nextDouble();
        } catch (Exception e){
            //clearInput();
            System.out.println("ERROR: Insira um número válido na comsola");
            return getDouble();
        }
    }

    private void clearInput(){
        scanner.next();
    }

    protected void yesOrNoQuestion(String message, Consumer<Boolean> booleanConsumer){
        try{
            System.out.print(message + " [Y/N]: ");
            if(scanner.next().toUpperCase().equals("Y")){
                booleanConsumer.accept(true);
            }else{
                booleanConsumer.accept(false);
            }
        } catch (Exception e){
            yesOrNoQuestion(message, booleanConsumer);
        }
    }

    protected boolean yesOrNoQuestion(String message){
        try{
            System.out.print(message + " [Y/N]: ");
            if(scanner.next().toUpperCase().equals("Y")){
                return true;
            }else{
                return false;
            }
        } catch (Exception e){
            return yesOrNoQuestion(message);
        }
    }


    public abstract String render();

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}