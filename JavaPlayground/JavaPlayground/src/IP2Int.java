public class IP2Int {

    public static void main(String[] args) {
        String ipString = "10.0.3.123";
        String[] subStrings = ipString.split("\\.");

        // Solution1
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : subStrings) {
            int i = Integer.parseInt(s);
            stringBuilder.append(int2Dec(i));
        }
        System.out.println(dec2Int(stringBuilder.toString()));

        // Solution2
        int result = 0;
        int pass = 24;
        for (String s : subStrings) {
            int i = Integer.parseInt(s);
            result += (i & 255) << pass;
            pass -= 8;
        }
        System.out.println(result);
    }

    private static String int2Dec(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        while (i > 0) {
            if (i % 2 == 1) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
            i /= 2;
        }
        while (stringBuilder.length() < 8) {
            stringBuilder.append("0");
        }
        return stringBuilder.reverse().toString();
    }

    private static int dec2Int(String d) {
        int result = 0;
        for (int i = 0; i < d.length(); i++) {
            char c = d.charAt(d.length() - i - 1);
            result += (c - '0') * Math.pow(2, i);
        }
        return result;
    }

}
