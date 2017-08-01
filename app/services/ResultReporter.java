package services;

import models.SlotSummary;

import java.util.List;

/**
 * Created by Harry Tran (tt41630) on 02-Apr-16.
 */
public interface ResultReporter {
    List<SlotSummary> getSlotSummaries();
}
