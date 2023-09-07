package interfaces;

import exceptions.RandoopException;

public interface IRandoopConnector {
    public double generateRandoopTest(String className, int maxNumberLevel) throws RandoopException;

}
