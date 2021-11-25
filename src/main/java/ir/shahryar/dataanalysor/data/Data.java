package ir.shahryar.dataanalysor.data;

import org.jetbrains.annotations.NotNull;

public class Data implements Comparable<Data> {
    private long price;
    private long date;

    public long getPrice() {
        return price;
    }

    public long getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Data{" + "price=" + price +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(@NotNull Data o) {
        return Long.compare(this.date, o.date);
    }
}
