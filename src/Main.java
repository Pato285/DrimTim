<<<<<<< HEAD
package src;

public class Main {
    static public int levenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;
=======
import java.math.BigInteger;

import com.devewm.pwdstrength.*;
>>>>>>> refs/remotes/Pato285/master

        // the  array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

<<<<<<< HEAD
        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for(int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost; cost = newcost; newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    public static void main(String[] args) {
        System.out.println(levenshteinDistance("hola","dsh"));
=======
    public static void main(String[] args){
        System.out.println(AuxFun.levenshteinDistance("hola","dsh"));
        System.out.println(AuxFun.maxSubsecuence("hola","dshoh"));
        System.out.println(AuxFun.typesOfChars("hoA!la"));
        
        PasswordStrengthMeter passwordStrengthMeter = PasswordStrengthMeter.getInstance();
        
        BigInteger result = passwordStrengthMeter.iterationCount("aaaa");
        System.out.println(result);
        System.out.println(AuxFun.uniqueChars("pass$woQsArñÑA@67d"));
>>>>>>> refs/remotes/Pato285/master
    }

}
