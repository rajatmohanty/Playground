import java.util.Scanner;

/**
 * Created by Kray on 2017/4/14.
 */
public class Test_1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        double realN = n * 1.0;
        double sum = 0.0;
        for (int i = 0; i < m; i++) {
            sum += realN;
            realN = Math.sqrt(realN);
        }
        System.out.printf("%.2f", sum);
    }

}
