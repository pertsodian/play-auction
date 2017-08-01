package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import services.ResultReporter;
import services.TicketsManager;
import views.html.result;

import javax.inject.Inject;
import javax.inject.Singleton;

import static play.libs.Json.toJson;

/**
 * Created by Harry Tran (tt41630) on 02-Apr-16.
 */
@Singleton
public class ResultController extends Controller {

    private final ResultReporter resultReporter;

    @Inject
    public ResultController(TicketsManager ticketManager) {
        this.resultReporter = ticketManager;
    }

    public Result index() {
        return ok(result.render("Tikam Tikam Auction Interim Results"));
    }

    public Result finalResults() {
        return ok(result.render("Tikam Tikam Auction Final Results"));
    }

    public Result getResults() {
        return ok(toJson(resultReporter.getSlotSummaries()));
    }
}
