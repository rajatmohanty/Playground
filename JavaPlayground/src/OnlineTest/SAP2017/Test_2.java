import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Kray on 2017/4/14.
 */
public class Test_2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();

        List<Integer> result = new ArrayList<>();
        for (int i = m; i <= n; i++) {
            if (isNarcissusNumber(i)) {
                result.add(i);
            }
        }
        if (result.size() == 0) {
            System.out.println("no");
        } else {
            for (int i = 0; i < result.size(); i++) {
                System.out.print(result.get(i));
                if (i != result.size() - 1) {
                    System.out.print(" ");
                }
            }

        }
    }

    private static boolean isNarcissusNumber(int n) {
        return n == Math.pow((n / 100), 3) + Math.pow(n % 10, 3) + Math.pow(((n - n % 10) / 10) % 10, 3);
    }

}
