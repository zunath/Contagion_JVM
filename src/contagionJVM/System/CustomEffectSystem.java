package contagionJVM.System;

import contagionJVM.CustomEffect.ICustomEffectHandler;
import contagionJVM.Entities.CustomEffectEntity;
import contagionJVM.Entities.PCCustomEffectEntity;
import contagionJVM.GameObject.PlayerGO;
import contagionJVM.Helper.EffectHelper;
import contagionJVM.Helper.ErrorHelper;
import contagionJVM.Repository.CustomEffectRepository;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;
import org.nwnx.nwnx2.jvm.Scheduler;
import java.util.List;

public class CustomEffectSystem {

    public static void OnPlayerHeartbeat(NWObject oPC)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        CustomEffectRepository repo = new CustomEffectRepository();
        List<PCCustomEffectEntity> effects = repo.GetPCEffects(pcGO.getUUID());

        for(PCCustomEffectEntity effect : effects)
        {
            PCCustomEffectEntity result = RunCustomEffectProcess(oPC, effect);
            if(result == null)
            {
                repo.Delete(effect);
            }
            else
            {
                repo.Save(effect);
            }
        }
    }

    public static void OnModuleEnter()
    {
        NWObject oPC = NWScript.getEnteringObject();
        if(!NWScript.getIsPC(oPC) || NWScript.getIsDM(oPC)) return;

        PlayerGO pcGO = new PlayerGO(oPC);
        CustomEffectRepository repo = new CustomEffectRepository();
        List<PCCustomEffectEntity> effects = repo.GetPCEffects(pcGO.getUUID());

        for(PCCustomEffectEntity effect : effects)
        {
            EffectHelper.ApplyEffectIcon(oPC, effect.getCustomEffect().getIconID(), effect.getTicks() * 6.0f);
        }

    }

    private static PCCustomEffectEntity RunCustomEffectProcess(NWObject oPC, PCCustomEffectEntity effect)
    {
        effect.setTicks(effect.getTicks() - 1);
        if(effect.getTicks() < 0) return null;

        try {
            Class scriptClass = Class.forName("contagionJVM.CustomEffect." + effect.getCustomEffect().getScriptHandler());
            ICustomEffectHandler script = (ICustomEffectHandler)scriptClass.newInstance();
            script.run(oPC);
            Scheduler.flushQueues();
        }
        catch(Exception ex) {
            ErrorHelper.HandleException(ex, "RunCustomEffectProcess was unable to run specific effect script: " + effect.getCustomEffect().getScriptHandler());
        }

        return effect;
    }

    public static void ApplyCustomEffect(NWObject oPC, int customEffectID, int ticks)
    {
        PlayerGO pcGO = new PlayerGO(oPC);
        CustomEffectRepository repo = new CustomEffectRepository();
        PCCustomEffectEntity entity = repo.GetPCEffectByID(pcGO.getUUID(), customEffectID);
        CustomEffectEntity effectEntity = repo.GetEffectByID(customEffectID);

        if(entity == null)
        {
            entity = new PCCustomEffectEntity();
            entity.setPlayerID(pcGO.getUUID());
            entity.setCustomEffect(effectEntity);
        }

        entity.setTicks(ticks);
        repo.Save(entity);

        EffectHelper.ApplyEffectIcon(oPC, effectEntity.getIconID(), ticks * 6.0f);
    }
}
