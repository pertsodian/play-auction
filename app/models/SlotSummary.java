package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Harry Tran (tt41630) on 03-Apr-16.
 */
public class SlotSummary {

    private int slot;
    private double maxBid;
    private List<String> maxBidders;
    private List<Integer> maxIDs;

    public SlotSummary(int slot, List<Ticket> maxBidTickets) {
        this.slot = slot;
        this.maxBid = 0;
        this.maxBidders = new ArrayList<>();
        this.maxIDs = new ArrayList<>();

        setSummary(maxBidTickets);
    }

    private void setSummary(List<Ticket> maxBidTickets) {
        if (maxBidTickets != null && !maxBidTickets.isEmpty()) {
            maxBid = maxBidTickets.iterator().next().getBid();
            maxBidTickets
                    .stream()
                    .map(ticket -> String.format("%s[%d]", ticket.getBidder(), ticket.getTable()))
                    .forEach(maxBidders::add);
            maxBidTickets
                    .stream()
                    .map(Ticket::getId)
                    .forEach(maxIDs::add);
        }
    }

    public int getSlot() {
        return slot;
    }

    public double getMaxBid() {
        return maxBid;
    }

    public List<String> getMaxBidders() {
        return maxBidders;
    }

    public List<Integer> getMaxIDs() {
        return maxIDs;
    }
}
