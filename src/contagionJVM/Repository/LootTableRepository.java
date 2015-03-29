package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.LootTableEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class LootTableRepository {

    public LootTableEntity GetByLootTableID(int lootTableID)
    {
        LootTableEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(LootTableEntity.class)
                    .add(Restrictions.eq("lootTableID", lootTableID));

            entity = (LootTableEntity)criteria.uniqueResult();
        }

        return entity;
    }
}
