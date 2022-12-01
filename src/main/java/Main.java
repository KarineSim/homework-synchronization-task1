import java.util.*;

public class Main {

    private static final int NUMBER_OF_THREADS = 1000;
    private static final int LENGTH = 100;
    private static final String LETTERS = "RLRFR";
    private static final char LETTER = 'R';

    public static final Map<Integer, Integer> sizeToFreq = new TreeMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            new Thread(() -> {
                String result = generateRoute(LETTERS, LENGTH);
                int numberOfR = 0;
                for (int j = 0; j < LENGTH; j++) {
                    if (result.charAt(j) == LETTER) {
                        numberOfR++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(numberOfR)) {
                        sizeToFreq.put(numberOfR, sizeToFreq.get(numberOfR) + 1);
                    } else {
                        sizeToFreq.put(numberOfR, 1);
                    }
                }
            }).start();
        }

        int numbersOfReps = 0;
        int maxNumbersOfReps = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxNumbersOfReps) {
                maxNumbersOfReps = entry.getValue();
                numbersOfReps = entry.getKey();
            }
        }

        System.out.println("Самое частое количество повторений " + numbersOfReps + " (встретилось " + maxNumbersOfReps + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != numbersOfReps) {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
