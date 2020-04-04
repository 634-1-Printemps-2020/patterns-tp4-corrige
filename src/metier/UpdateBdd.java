package metier;

import outils.AccessBdd;
import java.util.concurrent.CountDownLatch;

public class UpdateBdd implements Runnable {
    private CountDownLatch latch;
    private String nomSuccursale;
    private String nomMaster;

    public UpdateBdd(CountDownLatch latch, String nomSuccursale, String nomMaster) {
        this.latch = latch;
        this.nomSuccursale = nomSuccursale;
        this.nomMaster = nomMaster;
    }

    @Override
    public void run() {
        AccessBdd.update(nomSuccursale, nomMaster);
        latch.countDown();
    }
}