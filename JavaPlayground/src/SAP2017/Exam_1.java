import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Kray on 2017/4/14.
 */
public class Exam_1 {

    static Map<Integer, Integer> lpf = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n == 1) {
            System.out.print(n - 1);
            return;
        }

        int c = 0;
        if (isPrime(n)) {
            for (int i = n; i >= 2; i--) {
                if(isPrime(i)){
                    c++;
                }
            }
            System.out.print(c);
            return;
        }

        boolean hasPrime = false;
        int count = 0;
        int maxprime = 0;
        for (int i = n / 2; i >= 2; i--) {
            if (isPrime(i)) {
                if (hasPrime) {
                    count++;
                    lpf.putIfAbsent(i, count);
                }
                if (n % i == 0) {
                    if (!hasPrime) {
                        hasPrime = true;
                        lpf.putIfAbsent(i, count);
                        maxprime = i;
                    }
                }
            }
        }

        System.out.print(count + 1 - lpf.get(maxprime));
    }

    private static boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
