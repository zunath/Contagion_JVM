package contagionJVM.Helper;

import org.nwnx.nwnx2.jvm.NWScript;

public class MenuHelper {

    public static String BuildBar(int currentValue, int requiredValue, int numberOfBars)
    {
        String xpBar = "";
        int highlightedBars = NWScript.floatToInt((float) currentValue / (float) requiredValue * numberOfBars);

        for(int bar = 1; bar <= 100; bar++)
        {
            if(bar <= highlightedBars)
            {
                xpBar += ColorToken.Orange() + "|" + ColorToken.End();
            }
            else
            {
                xpBar += ColorToken.White() + "|" + ColorToken.End();
            }
        }


        return xpBar;
    }

}
