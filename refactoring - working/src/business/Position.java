package business;

public class Position implements Comparable<Position>, ObserverPosition{
    //------------------------------------ INSTANCE VARIABLES -----------------------------------------------
    private App app;
    private int idPosition;
    private String type;
    private int idUser;
    private int idMarketStock;
    private PositionFinancials positionFinancials;
    //--------------------------------------------------------------------------------------------------------

    //------------------------------------ CONSTRUCTORS -----------------------------------------------------

    public Position(App app){
        this.app = app;
    }

    public Position(int idPosition, String type, int idUser, int idMarketStock, PositionFinancials positionFinancials){
        this.idPosition = idPosition;
        this.type = type;
        this.idUser = idUser;
        this.idMarketStock = idMarketStock;
        this.positionFinancials = positionFinancials;
    }


    public Position(Position pst){
        this.idPosition = pst.getIdPosition();
        this.type = pst.getType();
        this.idUser = pst. getidUser();
        this.idMarketStock = pst.getMarketstock_id();
    }

    public Position(){
        this.idPosition = -1;
        this.type = "";
        this.idUser = -1;
        this.idMarketStock = -1;
        this.positionFinancials = new PositionFinancials();
    }
    //---------------------------------------------------------------------------------------------------------


    //---------------------------------------- SETTERS & GETTERS ----------------------------------------------

    public int getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getidUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getMarketstock_id() {
        return idMarketStock;
    }

    public void setMarketstock_id(int idMarketStock) {
        this.idMarketStock = idMarketStock;
    }

    public int getAmount() {
        return positionFinancials.getAmount();
    }

    public void setAmount(int amount) {
        this.positionFinancials.setAmount(amount);
    }

    public int getUnits_remaining() {
        return positionFinancials.getUnits_remaining();
    }

    public void setUnits_remaining(int units_remaining) {
        positionFinancials.setUnits_remaining(units_remaining);
    }

    public float getStop_loss() {
        return positionFinancials.getStop_loss();
    }

    public void setStop_loss(float stop_loss) {
        this.positionFinancials.setStop_loss(stop_loss);
    }

    public float getTake_profit() {
        return positionFinancials.getTake_profit();
    }

    public void setTake_profit(float take_profit) {
        this.positionFinancials.setTake_profit(take_profit);
    }

    public String getStatus() {
        return positionFinancials.getStatus();
    }

    public void setStatus(String status) {
        this.positionFinancials.setStatus(status);
    }

    public float getDeal_value() {
        return this.positionFinancials.getDeal_value();
    }

    public void setDeal_value(float deal_value) {
        this.positionFinancials.setDeal_value(deal_value);
    }

    public String getDeal_date() {
        return positionFinancials.getDeal_date();
    }

    public void setDeal_date(String deal_date) {
        this.positionFinancials.setDeal_date(deal_date);
    }
    //------------------------------------------------------------------------------------------------

    /**
     * Método clone
     * @return Position
     */

    public Position clone(){return new Position(this);}


    /**
     * Método equals
     * @param o
     * @return boolean
     */


    /**
     * Método hashCode
     * @return int
     */
    public int hashCode() {
        int result = idPosition;
        result = 31 * result + type.hashCode();
        result = 31 * result + idUser;
        result = 31 * result + idMarketStock;
        result = 31 * result + this.positionFinancials.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return idPosition == position.idPosition &&
                idUser == position.idUser &&
                idMarketStock == position.idMarketStock &&
                app.equals(position.app) &&
                type.equals(position.type) &&
                positionFinancials.equals(position.positionFinancials);
    }

    /**
     * Método toString
     * @return String
     */
    public String toString() {
        return "Position{\n" +
                "    Type:           '" + type + '\'' +"\n"+
                "    User Id:         " + idUser + "\n"+
                "    Marketstock Id:  " +  idMarketStock +"\n"+
               positionFinancials.toString() +
                '}'+"\n";
    }


    /**
     * Método compareTo
     * @param position
     * @return int
     */
    public int compareTo(Position position) {
        return idPosition - position.idPosition;
    }

    /**
     * Método que atualiza o estado de uma posição de compra
     * @param mstk
     */
    public void updateBuy(MarketStock mstk) {

        app.updateBuyPositionWaiting(mstk);
    }

    /**
     * Método que atualiza o estado de uma posição de venda
     */
    public void updateSale(MarketStock mstk) {

        app.updateSalePositionWaiting(mstk);
    }
}
