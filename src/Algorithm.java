import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    public static void main(String[] args) {
        double[] packageSizes = {500, 200, 100, 50}; // 760
        System.out.println(optimizedProductPackaging(650, packageSizes));
    }

    public static StringBuilder optimizedProductPackaging(double weight, double[] packageSizes) {
        boolean firstAppend = true;
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (weight > 0) {
            if (packageSizes[i] <= weight) {
                int amount = (i != packageSizes.length - 1) ? (int) (weight / packageSizes[i]) : (int) (weight / packageSizes[i]) + 1;
                if (weight - amount * packageSizes[i] == packageSizes[i] * -1) {
                    amount--;
                }
                double tempWeight = weight;
                weight -= amount * packageSizes[i];
                if (i - 1 > 0) {
                    if (weight < tempWeight - packageSizes[i - 1]) {
                        i--;
                        weight = tempWeight;
                        amount = (int) (weight / packageSizes[i]) + 1;
                        weight -= amount * packageSizes[i];
                    }
                }
                if (firstAppend) {
                    stringBuilder.append(packageSizes[i]).append("x").append(amount);
                    firstAppend = false;
                } else {
                    stringBuilder.append(" + ").append(packageSizes[i]).append("x").append(amount);
                }
            } else {
                if (i + 1 < packageSizes.length) {
                    i++;
                } else if (packageSizes[packageSizes.length - 1] >= weight) {
                    stringBuilder.append(" + ").append(packageSizes[i]).append("x").append(1);
                    weight -= packageSizes[packageSizes.length - 1];
                }
            }
        }
        return stringBuilder;
    }
}