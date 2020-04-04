package metier;

import outils.AccessBdd;
import java.util.concurrent.Callable;

public class ConnectSuccursale implements Callable {
    private String nomSuccursale;

    public ConnectSuccursale(String nomSuccursale) {
        this.nomSuccursale = nomSuccursale;
    }

    @Override
    public Object call() throws Exception {
        AccessBdd.connect(nomSuccursale);
        return nomSuccursale;
    }
}