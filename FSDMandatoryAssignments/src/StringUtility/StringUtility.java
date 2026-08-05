package StringUtility;

public class StringUtility {

    // Extract Substring
    public void extractSubstring(String str, int start, int end) {

        if (start >= 0 && end <= str.length() && start < end) {
            String sub = str.substring(start, end);
            System.out.println("Substring: " + sub);
        } else {
            System.out.println("Invalid Index!");
        }
    }

    // Split Sentence
    public void splitSentence(String sentence) {

        String[] words = sentence.split(" ");

        System.out.println("Words are:");

        for (String word : words) {
            System.out.println(word);
        }
    }
}