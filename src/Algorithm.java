import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    public static void main(String[] args) {
        double[] packageSizes = {500, 200, 100, 50};
//        System.out.println(optimizedProductPackagingTwo(650, packageSizes));
        System.out.println(optimizedPackagingToString(optimizedProductPackagingTwo(820, packageSizes), packageSizes));
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

    public static StringBuilder optimizedProductPackagingTwo(double weight, double[] packageSizes) {
        boolean firstAppend = true;

        int leastAmount;
        double smallestReminder;
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (weight > 0) {

            if (isLastIndex(packageSizes, i)) {
                int amount = calculateAmount(weight, packageSizes[i]);
                append(packageSizes[i], firstAppend, stringBuilder, amount);
                weight = updateWeight(weight, packageSizes[i] * amount);
            } else {
                List<Double> calculatedReminders = new ArrayList<>();
                List<Integer> calculatedAmounts = new ArrayList<>();

                leastAmount = calculateAmount(weight, packageSizes[0]);
                calculatedReminders.add(calculateRemainder(weight, packageSizes[0], leastAmount));
                smallestReminder = calculatedReminders.get(0);

                int smallestReminderIndex = 0;
                for (int j = 1; j < packageSizes.length; j++) {
                    int amount = calculateAmount(weight, packageSizes[j]);
                    double reminder = calculateRemainder(weight, packageSizes[j], amount);
                    if (reminder == 0) {
                        smallestReminder = reminder;
                        leastAmount = amount;
                        smallestReminderIndex = j;
                    } else if (reminder < smallestReminder || (reminder == smallestReminder && leastAmount > amount)) {
                        smallestReminder = reminder;
                        leastAmount = amount;
                        smallestReminderIndex = j;
                    }
                    calculatedReminders.add(reminder);
                }
                int amount = calculateAmount(weight, packageSizes[smallestReminderIndex]);
                append(packageSizes[smallestReminderIndex], firstAppend, stringBuilder, amount);
                weight = updateWeight(weight, amount * packageSizes[smallestReminderIndex]);
            }
        }
        return stringBuilder;
    }

    private static double updateWeight(double weight, double amount) {
        return weight - amount;
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

    private static int findMultipliedSize(double value, double[] packageSizes) {
        for (int i = 0; i < packageSizes.length; i++) {
            if (packageSizes[i] != value && packageSizes[i] % value == 0 && packageSizes[i] / value != 0) {
                return i;
            }
        }
        return -1;
    }

    private static int calculateMultiplier(double smaller, double bigger) {
        return (int) (bigger / smaller);
    }

    public static int calculateMultiplicity(double smaller, double bigger, int multiplier) {
        return (int) (bigger / smaller / multiplier);
    }

    public static StringBuilder optimizedPackagingToString(StringBuilder stringBuilder, double[] packageSizes) {
        StringBuilder sb = new StringBuilder();
        String packaging = stringBuilder.toString();
        String[] split = packaging.split("x");
        for (int i = 0; i < split.length; i += 2) {
            double value = Double.parseDouble(split[i + 1]);
            int multipliedSize = findMultipliedSize(value, packageSizes);
            int newAmount;
            int multiplier;
            if (multipliedSize != -1) {
                if (packageSizes[multipliedSize] > value) {
                    multiplier = calculateMultiplier(value, packageSizes[multipliedSize]);
                    newAmount = calculateMultiplicity(value, packageSizes[multipliedSize], multiplier);
                } else {
                    multiplier = calculateMultiplier(packageSizes[multipliedSize], value);
                    newAmount = calculateMultiplicity(packageSizes[multipliedSize], value, multiplier);
                }
                int reminder = Integer.parseInt(split[i]) - newAmount * multiplier;
                split[i] = String.valueOf(newAmount);
                sb.append(newAmount).append("x").append(packageSizes[multipliedSize]).append(" + ")
                        .append(reminder).append("x").append(value);
            }
        }
        return sb;
    }
}
