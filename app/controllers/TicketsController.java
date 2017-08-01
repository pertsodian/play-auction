package controllers;

import models.Ticket;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.TicketsManager;
import views.html.submit;

import javax.inject.Inject;
import javax.inject.Singleton;

;

/**
 * Created by Harry Tran (tt41630) on 02-Apr-16.
 */
@Singleton
public class TicketsController extends Controller {

    private final TicketsManager ticketsManager;

    @Inject
    private FormFactory formFactory;

    @Inject
    public TicketsController(TicketsManager ticketsManager) {
        this.ticketsManager = ticketsManager;
    }

    public Result form() {
        return ok(submit.render());
    }

    public Result submit() {
        Form<Ticket> form = formFactory.form(Ticket.class).bindFromRequest();
        if (form.hasErrors()) {
            Logger.error("Ticket form contains some errors: {}", form.errorsAsJson().toString());
            return badRequest(form.errorsAsJson().toString());
        }

        Ticket ticket = form.get();
        ticketsManager.submit(ticket);
        return redirect(routes.TicketsController.form());
    }
}
