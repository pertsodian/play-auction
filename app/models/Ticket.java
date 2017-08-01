package models;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Harry Tran (tt41630) on 02-Apr-16.
 */
public class Ticket {

    private static final String DELIMITER = ",";
    public static final int MAX_SLOT = 25;
    private static final int MIN_BID = 50;
    private static final int MAX_TABLE = 100;

    @Constraints.Required
    private int id;
    @Constraints.Required
    private int slot;
    @Constraints.Required
    private double bid;
    @Constraints.Required
    private String bidder;
    @Constraints.Required
    private int table;

    public Ticket() {}

    public Ticket(int id, int slot, double bid, String bidder, int table) {
        this.id = id;
        this.slot = slot;
        this.bid = bid;
        this.bidder = bidder;
        this.table = table;
    }

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (id <= 0)
            errors.add(new ValidationError("id", "Invalid ID: must be positive"));
        if (slot <= 0 || slot > MAX_SLOT)
            errors.add(new ValidationError("slot", String.format("Invalid Slot: must be in range [0,%d]", MAX_SLOT)));
        if (bid < MIN_BID)
            errors.add(new ValidationError("bid", String.format("Invalid Bid: must be at least %f", MIN_BID)));
        if (bidder.trim().isEmpty())
            errors.add(new ValidationError("bidder", "Invalid Name: must be not empty"));
        if (table <= 0 || table > MAX_TABLE)
            errors.add(new ValidationError("table", String.format("Invalid Table: must be in range [0,%d]", MAX_TABLE)));

        return errors.isEmpty() ? null : errors;
    }

    public static Ticket createTicketFromString(String summary) {
        String[] parts = summary.split(DELIMITER);
        if (parts.length != 5)
            throw new IllegalArgumentException(String.format("Invalid ticket input: %s", summary));

        return new Ticket(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                Double.parseDouble(parts[2]), parts[3], Integer.parseInt(parts[4]));
    }

    public int getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }

    public double getBid() {
        return bid;
    }

    public String getBidder() {
        return bidder;
    }

    public int getTable() {
        return table;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public void setTable(int table) {
        this.table = table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return Arrays.asList(id, slot, bid, bidder, table).stream()
                .map(i -> i.toString()).collect(Collectors.joining(DELIMITER));
    }
}
