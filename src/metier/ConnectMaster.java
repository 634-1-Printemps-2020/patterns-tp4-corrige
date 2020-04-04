package metier;

import outils.AccessBdd;

public class ConnectMaster implements Runnable {
    private String nomMaster;

    public String getNomMaster() { return nomMaster; }

    @Override
    public void run() {
        nomMaster = AccessBdd.connectMaster();
    }
}