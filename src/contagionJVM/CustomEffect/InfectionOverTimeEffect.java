package contagionJVM.CustomEffect;

import contagionJVM.System.DiseaseSystem;
import org.nwnx.nwnx2.jvm.NWObject;

import java.util.Random;

@SuppressWarnings("unused")
public class InfectionOverTimeEffect implements ICustomEffectHandler {
    @Override
    public void run(NWObject oPC) {
        Random random = new Random();

        DiseaseSystem.IncreaseDiseaseLevel(oPC, random.nextInt(5));
    }
}
