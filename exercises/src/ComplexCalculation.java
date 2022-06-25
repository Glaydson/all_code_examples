import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ComplexCalculation {

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result = BigInteger.ZERO;
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        List<PowerCalculatingThread> threads = new ArrayList<>();
        threads.add(new PowerCalculatingThread(base1, power1));
        threads.add(new PowerCalculatingThread(base2,power2));

        for (PowerCalculatingThread thread: threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join(2000);
        }

        for (int i = 0; i < 2; i++) {
            PowerCalculatingThread powerThread = threads.get(i);
            if (powerThread.isFinished()) {
                System.out.println(threads.get(i).base + " powered to " + threads.get(i).power + " is " + powerThread.getResult());
                result = result.add(powerThread.getResult());
                System.out.println(result);
            } else {
                System.out.println("The calculation for " + threads.get(i) + " is still in progress");
            }
        }
        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;
        private boolean isFinished = false;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
           /*
           Implement the calculation of result = base ^ power
           */
            if (power.intValue() > 0) {
                for (int i = 1; i <= power.intValue(); i++) {
                    result = result.multiply(base);
                }
            }
            this.isFinished = true;
        }

        public BigInteger getResult() { return result; }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
