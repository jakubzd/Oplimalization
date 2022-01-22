import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    public static void main(String[] args) {
        double[] packageSizes = {500, 200, 100, 40};
        System.out.println(optimizedPackagingToString(optimizedProductPackaging(690, packageSizes), packageSizes));
    }

    static int calculateAmount(double weight, double packageSize) {
        if ( weight % packageSize != 0) {
            return (int) (weight / packageSize + 1);
        }
        else return (int) (weight / packageSize);
    }
    static double calculateRemainder(double weight, double packageSize, int amount) {
        return weight - packageSize * amount;
    }

    public static StringBuilder optimizedProductPackaging(double weight, double[] packageSizes) {

        boolean firstAppend = true;
        List<Double> calculatedReminders = new ArrayList<>();
        int leastAmount;
        double smallestReminder;
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;

        leastAmount = calculateAmount(weight, packageSizes[0]);
        calculatedReminders.add(calculateRemainder(weight, packageSizes[0], leastAmount));
        smallestReminder = calculatedReminders.get(0);
        while (weight > 0) {

            if (isLastIndex(packageSizes, i)) {
                int amount = calculateAmount(weight, packageSizes[i]);
                append(packageSizes[i], firstAppend, stringBuilder, amount);
                weight -= updateWeight(weight, packageSizes[i] * amount);
            } else {
                int smallestReminderIndex = 0;
                for (int j = 0; j < packageSizes.length; j++) {
                    int amount = calculateAmount(weight, packageSizes[j]);
                    double reminder = calculateRemainder(weight, packageSizes[j], amount);
                    if (reminder == 0) {
                        weight -= updateWeight(packageSizes[j], amount);
                        append(packageSizes[j], firstAppend, stringBuilder, amount);
                        break;
                    } else if (reminder < 0) {
                        if (j != packageSizes.length - 1) {
                            int amountOfSmallerPackage = calculateAmount(weight, packageSizes[j + 1]);
                            if (Math.abs(reminder) > Math.abs(calculateRemainder(weight, packageSizes[j + 1], amountOfSmallerPackage)))
                            amount--;
                        }
                        if (amount <= 0) {
                            continue;
                        } else {
                            weight -= updateWeight(packageSizes[j], amount);
                            append(packageSizes[j], firstAppend, stringBuilder, amount);
                            stringBuilder.append("+");
                        }
                    } else if (reminder < smallestReminder || (reminder == smallestReminder && leastAmount > amount)) {
                      smallestReminder = reminder;
                      leastAmount = amount;
                      smallestReminderIndex = j;
                  }
                    calculatedReminders.add(reminder);
                }
                int amount = calculateAmount(weight, packageSizes[smallestReminderIndex]);

                weight -= updateWeight(weight, amount * packageSizes[smallestReminderIndex]);
            }
        }
        return stringBuilder;
    }

    private static double updateWeight(double weight, double amount) {
        return weight * amount;
    }

    private static void append(double packageSize, boolean firstAppend, StringBuilder stringBuilder, int amount) {
        if (firstAppend) {
            stringBuilder.append(amount).append("x").append(packageSize);
        } else {
            stringBuilder.append("+").append(amount).append("x").append(packageSize);
        }
    }
    private static boolean isLastIndex(double[] packageSizes, int i) {
        return (i == packageSizes.length - 1);
    }

    public static StringBuilder optimizedPackagingToString(StringBuilder stringBuilder, double[] packageSizes) {
        String packaging = stringBuilder.toString();
        String outcome = packaging.substring(0, packaging.length() - 1);
        return new StringBuilder(outcome);
    }
}


