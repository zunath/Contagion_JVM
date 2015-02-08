package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.ItemCombinationEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ItemCombinationRepository {

    public ItemCombinationEntity getByItemResrefs(String itemResrefA, String itemResrefB)
    {
        ItemCombinationEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ItemCombinationEntity.class);

            entity = (ItemCombinationEntity)criteria
                    .add(Restrictions.eq("itemA", itemResrefA))
                    .add(Restrictions.eq("itemB", itemResrefB))
                    .uniqueResult();

            // Try the other way...
            if(entity.equals(null))
            {
                entity = (ItemCombinationEntity)criteria
                        .add(Restrictions.eq("itemA", itemResrefB))
                        .add(Restrictions.eq("itemB", itemResrefA))
                        .uniqueResult();
            }
        }

        return entity;
    }

}
