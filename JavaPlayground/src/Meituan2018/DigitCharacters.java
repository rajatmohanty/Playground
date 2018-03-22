package Meituan2018;

import java.util.Scanner;

public class DigitCharacters {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String chars = scanner.nextLine();

        // 1-9 中缺少任何一个，就选那个最小的
        int simple1 = 0;
        for (int i = 1; i <= 9; i++) {
            if (!chars.contains(i + "")) {
                simple1 = i;
                break;
            }
        }
        if (simple1 > 0) {
            System.out.println(simple1);
            return;
        }

        // 123456789
        if (!chars.contains("0")) {
            System.out.println("10");
            return;
        }

        // 1234567890
        int[] counts = new int[10];
        for (int i = 0; i < chars.length(); i++) {
            char c = chars.charAt(i);
            counts[c - '0']++;
        }

        int minLength = counts[0];
        int minLengthIndex = 0;

        int anotherMinLengthIndex = -1;

        for (int i = 0; i < 10; i++) {
            if (counts[i] < minLength) {
                minLength = counts[i];
                minLengthIndex = i;
            }
        }
        for (int i = 1; i < 10; i++) {
            if (counts[i] == minLength && anotherMinLengthIndex < 0 && i > minLengthIndex) {
                anotherMinLengthIndex = i;
            }
        }
        minLength++;

        String result = "1";
        if (anotherMinLengthIndex > 0) {
            result = "";
            while (minLength > 0) {
                result +=
                        (minLengthIndex == 0 ? anotherMinLengthIndex : Integer.min(minLengthIndex, anotherMinLengthIndex)) + "";
                minLength--;
            }
            System.out.println(result);
            return;
        }

        if (minLengthIndex == 0) {
            while (minLength > 0) {
                result += "0";
                minLength--;
            }
            System.out.println(result);
            return;
        }
        result = "";
        while (minLength > 0) {
            result += minLengthIndex + "";
            minLength--;
        }
        System.out.println(result);

    }

}
