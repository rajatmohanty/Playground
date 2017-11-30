import java.util.Scanner;

public class PowerMod {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long a = scanner.nextLong();
        long n = scanner.nextLong();
        long m = scanner.nextLong();

        System.out.println(powerMod(a, n, m));
    }

    private static long powerMod(long a, long n, long m) {
        long result = 1;
        while (n > 0) {
            if ((n & 1) != 0) {
                result *= a;
                result %= m;
            }
            a *= a;
            a %= m;
            n >>= 1;
        }
        return result;
    }

}