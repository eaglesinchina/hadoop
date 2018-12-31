package mr_sort;

import java.util.StringTokenizer;

public class Tokenizer {
    public static void main(String[] args) {
        
        String s="a b c d e\nword";
        StringTokenizer tokenizer = new StringTokenizer(s);

        int i = tokenizer.countTokens();
        System.out.println("count---"+i);
        while (tokenizer.hasMoreElements()){
            System.out.println(tokenizer.nextToken());
        }


        String s1 = tokenizer.nextToken("\n");
        System.out.println("\\ n"+s1);
    }
}
