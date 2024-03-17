package prj.car_race.raceapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Semaphore");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SemaphorePanel semaphorePanel = new SemaphorePanel();


        frame.getContentPane().add(semaphorePanel);
        frame.pack();
        frame.setVisible(true);

        SemaphoreThread semaphoreThread = new SemaphoreThread(semaphorePanel);
        semaphoreThread.start();


        try {
            semaphoreThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TimerThread timerThread = new TimerThread();
        timerThread.start();

        JFrame frame2 = new JFrame("Car Race");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CarPanel carPanel = new CarPanel();

        frame2.getContentPane().add(carPanel);
        frame2.pack();
        frame2.setSize(500,300);
        frame2.setVisible(true);

        PlaySound ps = new PlaySound();
        ps.playSound();

        Car car1 = new Car("Red car", carPanel);
        Car car2 = new Car("Blue car", carPanel);
        Car car3 = new Car("Green car", carPanel);
        Car car4 = new Car("Yellow car", carPanel);

        car1.start();
        car2.start();
        car3.start();
        car4.start();

        try {
            car1.join();
            car2.join();
            car3.join();
            car4.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        timerThread.stopTimer();
        long raceTime = timerThread.getTime();
        System.out.println("Race duration: " + raceTime + "ms");


        ps.stopSound();

        String[] columnNames = {"Position", "Car Name", "Race Duration (s)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        Map<Car, Double> raceDuration = new HashMap<>();
        raceDuration.put(car1, (double) car1.getDuration()/1000);
        raceDuration.put(car2, (double) car2.getDuration()/1000);
        raceDuration.put(car3, (double) car3.getDuration()/1000);
        raceDuration.put(car4, (double) car4.getDuration()/1000);

        List<Map.Entry<Car, Double>> sortedRaceDuration = new ArrayList<>(raceDuration.entrySet());
        sortedRaceDuration.sort(Map.Entry.comparingByValue());

        for (int i = 0; i < sortedRaceDuration.size(); i++) {
            Car car = sortedRaceDuration.get(i).getKey();
            double duration = sortedRaceDuration.get(i).getValue();
            Object[] rowData = {i + 1, car.getName(), String.format("%.2f", duration)};
            model.addRow(rowData);
        }

        // create table frame and display standings
        JFrame standingsFrame = new JFrame("Race Standings");
        standingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTable table = new JTable(model);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        standingsFrame.getContentPane().add(scrollPane);

        standingsFrame.pack();
        standingsFrame.setVisible(true);

        System.out.println("Race!");
    }
}
