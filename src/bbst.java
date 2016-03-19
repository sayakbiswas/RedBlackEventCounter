import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Sayak Biswas on 3/16/2016.
 * This is the executable class file and provides access to commands needed to perform the required operations.
 * @author Sayak Biswas
 */
public class bbst {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Proper usage: java bbst test_100.txt < commands.txt > out_100.txt");
            System.exit(0);
        } else {
            ArrayList<Event> eventArrayList = null;
            RedBlackTreeEventCounter redBlackTreeEventCounter = null;
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(new FileInputStream(args[0]));
                int lineCount = 0;
                eventArrayList = new ArrayList<>();
                while (fileReader.hasNext()) {
                    String scannedLine = fileReader.nextLine();
                    Scanner scannedToken = new Scanner(scannedLine);
                    if (lineCount > 0) {
                        while (scannedToken.hasNext()) {
                            int ID = scannedToken.nextInt();
                            int count = scannedToken.nextInt();
                            Event event = new Event(ID, count);
                            eventArrayList.add(event);
                        }
                        scannedToken.close();
                    }
                    lineCount++;
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("File " + args[0] + " not found!");
                fileNotFoundException.printStackTrace();
            } finally {
                if(fileReader != null) {
                    fileReader.close();
                }
            }
            if(eventArrayList != null) {
                redBlackTreeEventCounter = new RedBlackTreeEventCounter();
                redBlackTreeEventCounter.buildEventCounter(eventArrayList);
            }

            if(redBlackTreeEventCounter != null) {
                Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String scannedString = scanner.nextLine();
                    Scanner stringScanner = new Scanner(scannedString);
                    while (stringScanner.hasNext()) {
                        String command = stringScanner.next();
                        if("increase".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            int amount = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.increase(ID, amount));
                        } else if("reduce".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            int amount = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.reduce(ID, amount));
                        } else if("count".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.count(ID));
                        } else if("inrange".equalsIgnoreCase(command)) {
                            int ID1 = stringScanner.nextInt();
                            int ID2 = stringScanner.nextInt();
                            System.out.println(redBlackTreeEventCounter.inRange(ID1, ID2));
                        } else if("next".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            Event nextEvent = redBlackTreeEventCounter.next(ID);
                            System.out.println(nextEvent.getID() + " " + nextEvent.getCount());
                        } else if("previous".equalsIgnoreCase(command)) {
                            int ID = stringScanner.nextInt();
                            Event previousEvent = redBlackTreeEventCounter.previous(ID);
                            System.out.println(previousEvent.getID() + " " + previousEvent.getCount());
                        } else if("quit".equalsIgnoreCase(command)) {
                            System.exit(0);
                        } else {
                            System.out.println("Wrong input command!");
                        }
                    }
                    stringScanner.close();
                }
                scanner.close();
            }
        }
    }
}