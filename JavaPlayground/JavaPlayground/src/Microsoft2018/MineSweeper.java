package Microsoft2018;

enum State {
    EXCAVATED
}

public class MineSweeper {
    private int[][] test = new int[][]{{-1, 1, 1, 1}, {1, 1, 2, -1}, {0, 0, 2, -1}, {0, 0, 1, 1}};

    private int width = 10;
    private int height = 10;
    private int[][] map = new int[width][height];
    private State[][] states = new State[width][height];

    public void excavate(int i, int j) {
        if (i < 0 || i > map.length || j < 0 || j > map[0].length) {
            return;
        }

        if (map[i][j] > 0) {
            // 标记该格为已挖
            return;
        }

        states[i][j] = State.EXCAVATED;
        int x1, x2, y1, y2 = 0;
        if (i == 0) {
            x1 = 0;
        } else {
            x1 = i - 1;
        }
        if (j == 0) {
            y1 = 0;
        } else {
            y1 = j - 1;
        }
        if (i == map.length - 1) {
            x2 = map.length - 1;
        } else {
            x2 = i + 1;
        }
        if (j == map[0].length - 1) {
            y2 = map[0].length - 1;
        } else {
            y2 = j + 1;
        }

        for (int a = x1; a <= x2; a++) {
            for (int b = y1; b <= y2; b++) {
                if (states[a][b] != State.EXCAVATED) {
                    excavate(a, b);
                }
            }
        }
    }
}
