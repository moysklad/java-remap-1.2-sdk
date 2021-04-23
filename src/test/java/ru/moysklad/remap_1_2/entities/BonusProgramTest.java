package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.entities.discounts.BonusProgram;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BonusProgramTest extends EntityTestBase {

    @Test
    public void BonusProgramCrudTest() throws IOException, ApiClientException {
        //get all discount
        ListEntity<BonusProgram> bonusProgramList = api.entity().bonusprogram().get();
        assertEquals(0, bonusProgramList.getRows().size());
        //create one discount
        BonusProgram bonusProgram = new BonusProgram();
        String bonusProgramName = "bonusProgram_" + randomStringTail();
        bonusProgram.setName(bonusProgramName);
        bonusProgram.setActive(false);
        bonusProgram.setAgentTags(new ArrayList<>());
        bonusProgram.setAllAgents(true);
        bonusProgram.setEarnRateRoublesToPoint(1);
        bonusProgram.setMaxPaidRatePercents(2);
        bonusProgram.setSpendRatePointsToRouble(3);
        bonusProgram = api.entity().bonusprogram().create(bonusProgram);
        assertEquals(bonusProgramName, bonusProgram.getName());
        assertFalse(bonusProgram.getActive());
        assertTrue(bonusProgram.getAllAgents());
        assertEquals(Integer.valueOf(1), bonusProgram.getEarnRateRoublesToPoint());
        assertEquals(Integer.valueOf(2), bonusProgram.getMaxPaidRatePercents());
        assertEquals(Integer.valueOf(3), bonusProgram.getSpendRatePointsToRouble());
        assertEquals(0, bonusProgram.getAgentTags().size());
        //get all discount
        bonusProgramList = api.entity().bonusprogram().get();
        assertEquals(1, bonusProgramList.getRows().size());
        //get one
        bonusProgram = api.entity().bonusprogram().get(bonusProgram.getId());
        assertEquals(bonusProgramName, bonusProgram.getName());
        //update one
        bonusProgram.setName("new");
        api.entity().bonusprogram().update(bonusProgram.getId(), bonusProgram);
        bonusProgram = api.entity().bonusprogram().get(bonusProgram.getId());
        assertEquals("new", bonusProgram.getName());
        //delete
        api.entity().bonusprogram().delete(bonusProgram.getId());
        bonusProgramList = api.entity().bonusprogram().get();
        assertEquals(0, bonusProgramList.getRows().size());
    }
}
