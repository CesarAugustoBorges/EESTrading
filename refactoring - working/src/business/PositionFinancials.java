package business;

import java.util.Objects;

public class PositionFinancials {
    private int amount;
    private int units_remaining;
    private float stop_loss;
    private float take_profit;
    private String status;
    private float deal_value;
    private String deal_date;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnits_remaining() {
        return units_remaining;
    }

    public void setUnits_remaining(int units_remaining) {
        this.units_remaining = units_remaining;
    }

    public float getStop_loss() {
        return stop_loss;
    }

    public void setStop_loss(float stop_loss) {
        this.stop_loss = stop_loss;
    }

    public float getTake_profit() {
        return take_profit;
    }

    public void setTake_profit(float take_profit) {
        this.take_profit = take_profit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getDeal_value() {
        return deal_value;
    }

    public void setDeal_value(float deal_value) {
        this.deal_value = deal_value;
    }

    public String getDeal_date() {
        return deal_date;
    }

    public void setDeal_date(String deal_date) {
        this.deal_date = deal_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionFinancials)) return false;
        PositionFinancials that = (PositionFinancials) o;
        return amount == that.amount &&
                units_remaining == that.units_remaining &&
                Float.compare(that.stop_loss, stop_loss) == 0 &&
                Float.compare(that.take_profit, take_profit) == 0 &&
                Float.compare(that.deal_value, deal_value) == 0 &&
                status.equals(that.status) &&
                deal_date.equals(that.deal_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, units_remaining, stop_loss, take_profit, status, deal_value, deal_date);
    }

    @Override
    public String toString(){
        return  "    Amount:          " + amount +"\n"+
                "    Units Remaining: " + units_remaining +"\n"+
                "    StopLoss:        " + stop_loss +"\n"+
                "    TakeProfit:      " + take_profit +"\n"+
                "    Status:          " + status + "\n"+
                "    Deal Value:      " + deal_value +"\n"+
                "    Deal Date:      " + deal_date +"\n";
    }
}
