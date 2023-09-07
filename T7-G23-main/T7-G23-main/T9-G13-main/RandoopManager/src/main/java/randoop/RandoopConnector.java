package randoop;

import java.util.logging.Logger;
import interfaces.*;
import exceptions.RandoopException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class RandoopConnector implements IRandoopConnector {

    private static final Logger logger = Logger.getLogger(RandoopConnector.class.getName());
    // path Repository delle Classi
    private String repositoryPath;
    // singleton
    private static RandoopConnector instance;

    // gestione dei threads in esecuzione
    private static final int N_MAX = 4;
    private int numberThreads;
    private boolean busyThreads[];
    private Queue<RandoopRequest> requests;

    // implementazione DP Singleton
    public RandoopConnector() {
        repositoryPath = "FileSystem";
        requests = new LinkedList<RandoopRequest>();
        numberThreads = 0;
        busyThreads = new boolean[N_MAX];
        for (boolean b : busyThreads) {
            b = false;
        }

    }

    public static RandoopConnector getInstance() {
        if (instance == null) {
            instance = new RandoopConnector();
        }
        return instance;
    }

    public void setRepositoryDir(String dir) {
        repositoryPath = dir;
    }

    //
    public double generateRandoopTest(String className, int maxNumberLevel) throws RandoopException {
        try {
            RandoopFilter f = new RandoopFilter(className, maxNumberLevel, repositoryPath);
            f.filter();
            return execRandoopTest(className, maxNumberLevel);
        } catch (IOException | InterruptedException e) {

            e.printStackTrace();
            return -1;
        }
    }

    // creazione e gestione Threads
    private synchronized double execRandoopTest(String className, int maxNumberLevel) {
        if (numberThreads < N_MAX) {
            numberThreads++;
            // seleziona thread su cui eseguire
            int threadIndex = 0;
            while (busyThreads[threadIndex]) {
                threadIndex++;
            }
            busyThreads[threadIndex] = true;
            // System.out.println("[RANDOOP CONNECTOR] className="+className+" richiesta
            // inoltrata; indiceThread="+(threadIndex+1));
            RandoopTestGenerator thread = new RandoopTestGenerator(className, this, maxNumberLevel, threadIndex + 1,
                    repositoryPath);
            thread.start();
            double coverage = -3;

            try {
                logger.info("Waiting for threads");
                thread.join();
                logger.info("Thread Over");
                return thread.getCoverage();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return coverage;
            }

        } else {
            // metti la richiesta in coda
            // System.out.println("[RANDOOP CONNECTOR] className="+className+" richiesta in
            // coda");
            requests.add(new RandoopRequest(className, maxNumberLevel));

            return -1;
        }
    }

    public synchronized void operationCompleted(int nSessions, String className, int threadIndex) {
        // System.out.println("[RANDOOP CONNECTOR] generation for class "+className+"
        // completed by thread n"+threadIndex);

        logger.info("in OC");

        numberThreads--;

        busyThreads[threadIndex - 1] = false;

        // vedi se ci sono richieste in coda
        if (!requests.isEmpty()) {
            RandoopRequest r = requests.remove();
            execRandoopTest(r.getClassName(), r.getnTests());
        }

        logger.info("end OC");

    }

}