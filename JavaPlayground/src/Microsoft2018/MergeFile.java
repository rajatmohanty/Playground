package Microsoft2018;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class MergeFile {
    public File mergeFile(File f1, File f2) {
        if (f1 == null || f2 == null) {
            return null;
        }

        try {
            BufferedReader bf1 = new BufferedReader(new FileReader(f1));
            BufferedReader bf2 = new BufferedReader(new FileReader(f2));

            String firstLine1 = bf1.readLine();
            String firstLine2 = bf2.readLine();
            if (firstLine1 == null || firstLine2 == null) {
                return null;
            }

            String[] keys1 = firstLine1.split("\t");
            String[] keys2 = firstLine2.split("\t");

            HashMap<String, Integer> map1 = new HashMap<>();
            HashMap<Integer, Integer> map2 = new HashMap<>();

            for (int i = 0; i < keys1.length; i++) {
                map1.put(keys1[i], i);
            }
            for (int i = 0; i < keys2.length; i++) {
                if (map1.get(keys2[i]) != null) {
                    map2.put(i, map1.get(keys2[i]));
                } else {
                    map1.put(keys2[i], map1.size());
                    map2.put(i, map1.get(keys2[i]));
                }
            }

            File outputFile = new File("path");
            FileWriter fw = new FileWriter(outputFile);

            String tempString;
            while ((tempString = bf1.readLine()) != null) {
                String newLine = tempString;
                int map1Size = map1.size();
                int tempStringSize = tempString.split("\t").length;
                while (map1Size > tempStringSize) {
                    newLine += "\t";
                    map1Size--;
                }
                fw.write(newLine);
            }
            while ((tempString = bf2.readLine()) != null) {
                ArrayList<String> keyArray = new ArrayList<>(map1.size());
                String[] tempStringArray = tempString.split("\t");
                for (int i = 0; i < tempStringArray.length; i++) {
                    keyArray.add(map2.get(i), tempStringArray[i]);
                }
                String newLine = "";
                for (int s = 0; s < keyArray.size(); s++) {
                    newLine += keyArray.get(s);
                    if (s != keyArray.size() - 1) {
                        newLine += "\t";
                    }
                }
                fw.write(newLine);
            }
            fw.close();

            return outputFile;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
