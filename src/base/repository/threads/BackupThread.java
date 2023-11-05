package base.repository.threads;

import base.repository.storage.Storable;

public class BackupThread implements Runnable {
    private Storable storage;

    public BackupThread(Storable storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            storage.backUp();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
