import com.google.inject.AbstractModule;
import services.TicketsManager;

public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(TicketsManager.class).asEagerSingleton();
    }

}
