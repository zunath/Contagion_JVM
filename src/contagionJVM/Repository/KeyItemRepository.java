package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.KeyItemEntity;
import contagionJVM.Entities.PCKeyItemEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class KeyItemRepository {

    public List<PCKeyItemEntity> GetPlayerKeyItemsByCategory(String uuid, int categoryID)
    {
        List<PCKeyItemEntity> playerItems;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCKeyItemEntity.class)
                    .createAlias("keyItem", "k")
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("k.keyItemCategoryID", categoryID));
            playerItems = criteria.list();
        }

        return playerItems;
    }

    public KeyItemEntity GetKeyItemByID(int keyItemID)
    {
        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(KeyItemEntity.class)
                    .add(Restrictions.eq("keyItemID", keyItemID));

            return (KeyItemEntity)criteria.uniqueResult();
        }
    }

    public PCKeyItemEntity GetPCKeyItemByKeyItemID(String uuid, int keyItemID)
    {
        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCKeyItemEntity.class)
                    .add(Restrictions.eq("keyItemID", keyItemID))
                    .add(Restrictions.eq("playerID", uuid));
            return (PCKeyItemEntity)criteria.uniqueResult();
        }
    }

    public void Save(PCKeyItemEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }
}
