package Indeed2017;

import java.util.Scanner;

/**
 * Created by Kray on 2017/5/13.
 */
public class Exam3 {


    static int H;
    static int W;
    static int D;
    static int R;
    static char[][] board;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        H = scanner.nextInt();
        W = scanner.nextInt();
        D = scanner.nextInt();
        R = scanner.nextInt();
        board = new char[H][W];
        for (int i = 0; i < H; i++) {
            String temp = scanner.next();
            for (int j = 0; j < W; j++) {
                board[i][j] = temp.charAt(j);
            }
        }

        int x = 0;
        int y = 0;
        int move = 0;
        boolean useWrap = false;
        while (true) {
            System.out.println(x + " , " + y);
            if (x == W - 1 && y == H - 1) {
                break;
            }
            if (canRight(x, y)) {
                y++;
                move++;
                continue;
            }
            if (canDown(x, y)) {
                x++;
                move++;
                continue;
            }
            if (!useWrap) {
                if (canWarp(x, y)) {
                    x += D;
                    y += R;
                    move++;
                    useWrap = true;
                    continue;
                }
            }
            if (canUp(x, y)) {
                x--;
                move++;
                continue;
            }
            if (canLeft(x, y)) {
                y--;
                move++;
                continue;
            }
        }

        System.out.print(move);

    }

    private static boolean canDown(int x, int y) {
        return x + 1 < H && board[x + 1][y] != '#';
    }

    private static boolean canUp(int x, int y) {
        return x - 1 > 0 && board[x - 1][y] != '#';
    }

    private static boolean canLeft(int x, int y) {
        return y - 1 > 0 && board[x][y - 1] != '#';
    }

    private static boolean canRight(int x, int y) {
        return y + 1 < W && board[x][y + 1] != '#';
    }

    private static boolean canWarp(int x, int y) {
        return y + R < H && x + D < W && board[x + D][y + R] != '#';
    }

    private int calDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}

