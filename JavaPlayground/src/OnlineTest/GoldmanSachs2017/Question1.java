package GoldmanSachs2017;

public class Question1 {

    public static void main(String[] args) {
        System.out.println(reverseSentence("Hello World"));
    }

    static String reverseSentence(String sentence) {
        String[] strings = sentence.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            for (int i = 0; i < s.length(); i++) {
                sb.append(s.charAt(s.length() - 1 - i));
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }


}
