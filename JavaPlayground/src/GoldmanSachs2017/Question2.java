package GoldmanSachs2017;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Question2 {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        final String fileName = System.getenv("OUTPUT_PATH");
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        int res;

        int _scores_rows = 0;
        int _scores_cols = 0;
        _scores_rows = Integer.parseInt(in.nextLine().trim());
        _scores_cols = Integer.parseInt(in.nextLine().trim());

        String[][] _scores = new String[_scores_rows][_scores_cols];
        for (int _scores_i = 0; _scores_i < _scores_rows; _scores_i++) {
            for (int _scores_j = 0; _scores_j < _scores_cols; _scores_j++) {
                _scores[_scores_i][_scores_j] = in.next();

            }
        }

        if (in.hasNextLine()) {
            in.nextLine();
        }

        res = bestAverageGrade(_scores);
        bw.write(String.valueOf(res));
        bw.newLine();

        bw.close();
    }

    static int bestAverageGrade(String[][] scores) {
        if (scores == null || scores.length <= 0) {
            return 0;
        }
        HashMap<String, Double> map = new HashMap<>();
        HashMap<String, Double> timeMap = new HashMap<>();
        Double max = -Double.MAX_VALUE;
        for (String[] tuple : scores) {
            String name = tuple[0];
            Double score = Double.parseDouble(tuple[1]);
            if (map.get(name) == null) {
                map.put(name, score);
                timeMap.put(name, 1.0);
            } else {
                Double time = timeMap.get(name);
                map.replace(name, (map.get(name) * time + score) / (time + 1));
                timeMap.replace(name, time + 1.0);
            }
        }
        for (Double d : map.values()) {
            if (d >= max) {
                max = d;
            }
        }
        String doubleString = max.toString();

        Integer absoluteInt = Integer.parseInt(doubleString.substring(0, doubleString.indexOf(".")));

        if (absoluteInt > 0) {
            return absoluteInt;
        } else {
            if (Integer.parseInt(doubleString.substring(doubleString.indexOf(".") + 1)) == 0) {
                return absoluteInt;
            } else {
                return absoluteInt - 1;
            }
        }
    }

}
