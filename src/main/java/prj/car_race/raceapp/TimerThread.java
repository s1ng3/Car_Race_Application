package prj.car_race.raceapp;

class TimerThread extends Thread {
    private long startTime;
    private boolean running = true;
    private long elapsedTime;

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void run() {
        startTime = System.currentTimeMillis();

        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

    public void stopTimer() {
        running = false;
    }

    public long getTime() {
        return elapsedTime;
    }
}


