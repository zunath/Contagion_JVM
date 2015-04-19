package contagionJVM.Event;

import contagionJVM.IScriptEventHandler;
import contagionJVM.NWNX.NWNX_Funcs;
import contagionJVM.NWNX.TriggerScript;
import org.nwnx.nwnx2.jvm.NWObject;
import org.nwnx.nwnx2.jvm.NWScript;

import java.io.PrintWriter;
import java.util.ArrayList;

@SuppressWarnings("UnusedDeclaration")
public class TestEvent implements IScriptEventHandler {
    @Override
    public void runScript(NWObject objSelf) {
        final NWObject oPC = NWScript.getLastUsedBy();

        try
        {
            PrintWriter writer = new PrintWriter("/home/mzs3/Desktop/test.txt", "UTF-8");

            ArrayList<String> badResrefs = new ArrayList<>();
            //badResrefs.add("zz_comm_chest");
            badResrefs.add("mzslore_search");
            badResrefs.add("asb_scavsite");
            badResrefs.add("asb_scavsite001");
            //badResrefs.add("_mzs2_perstor_1");
            //badResrefs.add("zst_chest");
            badResrefs.add("asb_grasstuft");
            //badResrefs.add("d_template003");
            badResrefs.add("sts_fertilesoil");
            badResrefs.add("sts_plantplac");
            //badResrefs.add("mzs2transition");
            badResrefs.add("newgeneric002");
            badResrefs.add("refill_point");
            badResrefs.add("resttrigger");

            NWObject area = NWNX_Funcs.GetFirstArea();
            while(NWScript.getIsObjectValid(area))
            {
                String areaName = NWScript.getName(area, false);
                NWObject[] objs = NWScript.getObjectsInArea(area);
                for(NWObject o : objs)
                {
                    String name = NWScript.getName(o, false);
                    String tag = NWScript.getTag(o);
                    String resref = NWScript.getResRef(o);
                    String scriptName = NWNX_Funcs.GetEventHandler(o, TriggerScript.OnObjectEnter);


                    int storageCount = NWScript.getLocalInt(o, "ZST_COUNT");

                    if(badResrefs.contains(resref) || storageCount > 0 || scriptName.equals("mzs2_trans"))
                    {
                        writer.println(areaName + "; " + name + "; " + resref + "; " + tag);
                    }

                }

                area = NWNX_Funcs.GetNextArea();
            }

            writer.close();
        }
        catch (Exception ex)
        {

        }


    }
}
