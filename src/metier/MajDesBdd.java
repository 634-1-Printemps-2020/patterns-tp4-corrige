package metier;

import outils.AccessBdd;

import java.util.List;
import java.util.concurrent.*;

public class MajDesBdd {
    public static void main(String[] args) {
        List<String> lstSuccursales = AccessBdd.listeSuccursales();

        ConnectMaster master = new ConnectMaster();
        Thread threadMaster = new Thread(master);
        threadMaster.start();

        ExecutorService exec = Executors.newFixedThreadPool(lstSuccursales.size());
        CompletionService<String> lstExec = new ExecutorCompletionService<>(exec);

        for (String nomSuccursale : lstSuccursales) {
            lstExec.submit(new ConnectSuccursale(nomSuccursale));
        }

        try {
            threadMaster.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
        String nomMaster = master.getNomMaster();

        CountDownLatch latch = new CountDownLatch(lstSuccursales.size());

        for (int i = 0; i < lstSuccursales.size(); i++) {
            try {
                Future<String> result = lstExec.take();
                String nomSuccursale = result.get();
                new Thread(new UpdateBdd(latch, nomSuccursale, nomMaster)).start();
            } catch (ExecutionException | InterruptedException e) { e.printStackTrace(); }
        }
        try { latch.await(); } catch (InterruptedException E) { }
        System.out.println("Toutes les Bdd ont été mises-à-jour");

        exec.shutdown();
    }
}