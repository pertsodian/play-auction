package services;

import models.SlotSummary;
import models.Ticket;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Harry Tran (tt41630) on 02-Apr-16.
 */
@Singleton
public class TicketsManager implements ResultReporter {

    private static final String AUDIT_FILE_NAME = "/tickets.log";
    private static final org.slf4j.Logger AUDITOR = LoggerFactory.getLogger("AUDITOR");

    private final Map<Integer, Ticket> tickets = new ConcurrentHashMap<>();

    @Inject
    public TicketsManager() throws Exception {
        try (Stream<String> lines = Files.lines(Paths.get(getClass().getResource(AUDIT_FILE_NAME).toURI()))) {
            lines.forEach(line -> {
                try {
                    add(Ticket.createTicketFromString(line));
                } catch (Exception ex) {
                    Logger.error("Error while adding ticket [{}]. Skipping...", line, ex);
                }
            });
        } catch (Exception ex) {
            Logger.error("Error loading existing tickets!", ex);
            throw new Exception("Failed to load existing tickets");
        }

        Logger.info("Pre-loaded [{}] tickets", tickets.size());
    }

    public void submit(Ticket ticket) {
        AUDITOR.info(ticket.toString());
        add(ticket);
    }

    private void add(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    @Override
    public List<SlotSummary> getSlotSummaries() {
        // GroupBy Slot
        Map<Integer, List<Ticket>> maxBidMap =
                tickets
                    .values()
                    .stream()
                    .collect(Collectors.groupingBy(Ticket::getSlot));

        // Keep only max bid in each slot
        maxBidMap
            .values()
            .stream()
            .forEach(tickets -> {
                Double maxBid = tickets.stream().mapToDouble(Ticket::getBid).max().orElse(0d);
                tickets.removeIf(ticket -> Double.compare(ticket.getBid(), maxBid) < 0);
            });

        // Fill in the blanks and return summaries
        return IntStream
                    .rangeClosed(1, Ticket.MAX_SLOT)
                    .mapToObj(slot -> Pair.of(slot, maxBidMap.computeIfAbsent(slot, k -> new ArrayList<>())))
                    .map(pair -> new SlotSummary(pair.getKey(), pair.getValue()))
                    .collect(Collectors.toList());
    }
}
