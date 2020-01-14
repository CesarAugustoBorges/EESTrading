package gui;

import business.App;
import business.Checker;
import exceptions.PasswordInvalidException;
import exceptions.StockNotExistsException;
import exceptions.UserExistsException;
import exceptions.UsernameInvalidException;

import java.util.Scanner;

public class GUI {
    private static App ngplt;

    protected GUI(){}

    public static void main(String[] args) {
        ngplt = new App();
        try {
            ngplt.loadStocks();

            Checker check = new Checker(ngplt);
            check.start();

            ngplt.updateBuyObservers();
            ngplt.updateSaleObservers();
        }
        catch (NullPointerException e){
            System.out.println("\n\nUps! There's no data due to API problem...\n\n");
        }

        showMenu();
    }

    /**
     * Método que apresenta o menu principal ao utilizador
     */
    public static void showMenu(){ //Removido parametro que não era utilizado -> Dead code
        System.out.println("----- ESS TRADING APP-----");
        System.out.println("1 - LOGIN");
        System.out.println("2 - SIGN UP ");
        System.out.println("3 - ADD STOCK TO MARKET");
        System.out.println("4 - REMOVE STOCK FROM MARKET");
        System.out.println("5 - LIST STOCKS CURRENTLY ON THE MARKET");
        System.out.println("-------------------");

        boolean handler = true;
        while (handler) {
            handler = menuHandler();
        }
    }

    /**
     * Método auxiliar para a apresentação do menu principal, escolhendo a opção desejada
     * @return
     */
    private static boolean menuHandler(){
        String option = readLine();
        if (option.equals("1")) {
            loginMenu();
            MainMenu.showMenu(ngplt);
        } else if (option.equals("2")) {
            signMenu();
        } else if (option.equals("3")) {
            addStockMenu();
            continuar();
            showMenu();
        } else if (option.equals("4")) {
            removeStockMenu();
            continuar();
            showMenu();
        } else if(option.equals("5")){
            try {
                ngplt.prettyStockList();
            }
            catch (NullPointerException e){
                System.out.println("\n\nUps! There's no data due to API problem...\n\n");
            }
            continuar();
            showMenu();
        } else {
            System.out.println("Input não reconhecido.");
        }
        return true;
    }

    /**
     * Método que lê uma linha do teclado (System.in)
     * @return Linha lida do teclado
     */
    protected static String readLine(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Método que lê um inteiro do teclado
     * @return Inteiro lido do teclado
     */
    protected static int readLineInt(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Método que lê um float do teclado
     * @return Float lido do teclado
     */
    protected static float readLineFloat(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextFloat();
    }

    /**
     * Método que formulário de logIn do utilizador
     */
    private static void loginMenu() {

        System.out.println("---- LOGIN ----");
        System.out.println("Username:");
        String username = readLine();
        System.out.println("Password:");
        String password = readLine();
        try {
            ngplt.startSession(username, password);

        } catch (PasswordInvalidException | UsernameInvalidException e) {
            System.out.println(e.getMessage());
            MainMenu.showMenu(ngplt);
        }
    }

    /**
     * Método que apresenta o formulário de registo do utilizador
     */
    private static void signMenu() {

        System.out.println("---- SIGN UP ----");
        System.out.println("Enter your username:");
        String username = readLine();
        System.out.println("Enter your name:");
        String name = readLine();
        System.out.println("Enter your password:");
        String password = readLine();
        System.out.println("Enter your email:");
        String email = readLine();
        System.out.println("How much money your account starts with?");
        float account_balance = readLineFloat();

        try {
            ngplt.registerUser(username, name, email, password, account_balance);
            System.out.println("Registered with success! Yay");
            continuar();
            showMenu();
        } catch (UserExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método que adiciona um stock ao mercado
     */
    private static void addStockMenu() {
        System.out.println("---- ADD STOCK TO MARKETPLACE ----");
        System.out.println("Enter the Stock's name you want to add to the Market:");
        String name = readLine();

        ngplt.addStock(name);
    }

    /**
     * Método que remove um stock do mercado
     */
    private static void removeStockMenu() {
        System.out.println("---- REMOVE STOCK FROM MARKETPLACE ----");
        System.out.println("Enter the Stock's name you want to remove from the Market:");
        String name = readLine();

        try {
            ngplt.removeStock(name);
        } catch (StockNotExistsException e) {
            e.printStackTrace();
        }
    }

    public static String buyStock(){
        String stockname;
        System.out.println("Name of the Stock you want to buy:");
        stockname = readLine();
        return stockname;
    }


    /**
     * Método que apresenta o formulário para abertura de uma posição de compra
     */
    static void openBuyPositionMenu() { //EXTRACT METHOD E Consolidate Duplicate Conditional Fragments
        Integer amount, id_stk, units_remaining;
        float stop_loss, take_profit;
        String stockname;

        System.out.println("---- OPEN BUY POSITION ----");
        stockname = buyStock();
        while(ngplt.stocksOnSale().contains(ngplt.Mstock_name(stockname)) || ngplt.stocksOnWaiting().contains(ngplt.Mstock_name(stockname))) {
            id_stk = ngplt.Mstock_name(stockname).getId_stock();
            units_remaining = ngplt.unitsRemaining(id_stk);
            if (units_remaining == -1) {
                System.out.println("Ups, you have a pending buy position opened for the " + ngplt.Mstock_name(stockname).getName() + " stock! Wait for that position to get concluded before opening a new position\n");
            }
            else {
                System.out.println("Ups, you still have " + units_remaining.toString() + " units of this stock! Open sale positions for them first\n");
            }
            stockname = buyStock();
        }

        System.out.println("How much you want to buy?");
        amount = readLineInt();
        System.out.println("What's the Stop Loss you are Willing To Define?");
        stop_loss = readLineFloat();
        System.out.println("What's the Take Profit You Are Willing To Define?");
        take_profit = readLineFloat();

        checkProfitBuy(stockname,amount,stop_loss,take_profit);
    }

    public static void checkProfitBuy(String stockname,int amount,float stop_loss,float take_profit){
        String close_buy;
        if ((ngplt.isAbleToBuy(stockname, amount))) {
            if ((ngplt.existsProfitOnBuy(stockname, stop_loss, take_profit))) {
                System.out.println("Profitable Purchase");
                ngplt.openBuyPositionDealt(stockname, amount, stop_loss, take_profit);
                System.out.println("Purchase Done! Check \"MY DEALS\" On The Menu For More Info");
            }
            else {
                System.out.println("This Buy Is Not Profitable For You At This Moment. You Wish To End It Now Anyway ? (yes|no)\n");
                close_buy = readLine();
                close_buy = close_buy.toLowerCase();
                while ((!close_buy.equals("YES")) && (!close_buy.equals("NO"))) {
                    System.out.println("> Input Value Is Not Valid!\n This Buy Is Not Profitable For You At This Moment. You Wish To End It Now Anyway ? (yes|no)\n");
                    close_buy = readLine();
                    close_buy = close_buy.toLowerCase();
                }
                if(close_buy.equals("YES")) {
                    ngplt.openBuyPositionDealt(stockname, amount, stop_loss, take_profit);
                }
                else{
                    ngplt.openBuyPositionWaiting(stockname, amount, stop_loss, take_profit);
                }
            }
        }
        else {
            System.out.println("Not enough funds!\n");
        }
    }

    /**
     * Método que apresenta o formulário para abertura de uma posição de venda
     */
    static void openSalePositionMenu() {
        String stockname, close_sale;
        float stop_loss, take_profit;
        Integer amount,id_stk, units_remaining;

        stockname = insertStockToSell();

        id_stk = ngplt.Mstock_name(stockname).getId_stock();
        units_remaining = ngplt.unitsRemaining(id_stk);

        System.out.println("You May Sell "+units_remaining.toString()+" Units Of Your "+(ngplt.Mstock_name(stockname)).getName()+" Stock\n");
        System.out.println("How Much You Want To Sell?");
        amount = checkAmount(units_remaining);

        System.out.println("What's the Stop Loss You Are Willing To Define?");
        stop_loss = readLineFloat();
        System.out.println("What's the Take Profit You Are Willing To Define?");
        take_profit = readLineFloat();

        checkProfitSale(stockname,stop_loss,take_profit,amount);
    }

    public static void checkProfitSale(String stockname,float stop_loss,float take_profit,int amount){
        String close_sale;
        if ((ngplt.existsProfitOnSale(stockname, stop_loss, take_profit))) {
            System.out.println("Profitable Sale");
            ngplt.openSalePositionDealt(stockname, amount, stop_loss, take_profit);
            System.out.println("Sale Done! Check \"MY DEALS\" On The Menu For More Info");
        }
        else {
            System.out.println("This Sale Is Not Profitable For You At This Moment. You Wish To End It Now Anyway? (yes|no)\n");
            close_sale = readLine();
            close_sale =close_sale.toLowerCase();
            while (!close_sale.equals("yes") && !close_sale.equals("no")) {
                System.out.println("> Input Value Is Not Valid!\n This Sale Is Not Profitable For You At This Moment. You Wish To End It Now Anyway ? (yes|no)\n");
                close_sale = readLine();
                close_sale =close_sale.toLowerCase();
            }
            if(close_sale.equals("yes")) {
                ngplt.openSalePositionDealt(stockname, amount, stop_loss, take_profit);
            }
            else{
                ngplt.openSalePositionWaiting(stockname, amount, stop_loss, take_profit);
            }

        }
    }

    public static String insertStockToSell(){
        String stockname;
        System.out.println("Name of the Stock you want to sell:");
        stockname = readLine();
        while(!ngplt.stocksOnSale().contains(ngplt.Mstock_name(stockname))){
            System.out.println("> Input Value Is Not Valid!\n");
            System.out.println("Name of the Stock you want to sell:");
            stockname = readLine();
        }
        System.out.println("> Value Inserted!\n");
        return stockname;
    }

    public static int checkAmount(int units_remaining){
        int amount=0;
        amount = readLineInt();
        while(amount<0 || amount==0 || amount>units_remaining ){
            System.out.println("> Input Value Is Not Valid!\n");
            System.out.println("How Much You Want To Sell?");
            amount = readLineInt();
        }
        return amount;
    }

    /**
     * Método que apresenta o formulário de adição de fundos
     */
    static void addFundsMenu() {
        float value;

        System.out.println("ADD FUNDS TO ACCOUNT");
        System.out.println("How Much Funds You Would Like To Add?");
        value = readLineFloat();

        ngplt.addFund(value);
    }

    /**
     * Método que define que, para continuar, o utilizador deve carregar no "Enter"
     */
    static void continuar(){
        System.out.println("\nPress Enter to continue");
        while (!readLine().equals("")) {
            System.out.println("Input value is not valid! \nPlease, press Enter to continue");
        }
    }

}
