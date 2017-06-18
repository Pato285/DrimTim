import java.math.BigInteger;

import com.devewm.pwdstrength.*;

public class Main implements AuxFun{

    public static void main(String[] args){
        System.out.println(AuxFun.levenshteinDistance("hola","dsh"));
        System.out.println(AuxFun.maxSubsecuence("hola","dshoh"));
        System.out.println(AuxFun.typesOfChars("hoA!la"));
        
        PasswordStrengthMeter passwordStrengthMeter = PasswordStrengthMeter.getInstance();
        
        BigInteger result = passwordStrengthMeter.iterationCount("aaaa");
        System.out.println(result);
        System.out.println(AuxFun.uniqueChars("pass$woQsArñÑA@67d"));
    }

}
