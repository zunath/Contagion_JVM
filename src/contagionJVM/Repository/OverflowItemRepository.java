package contagionJVM.Repository;


import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PCOverflowItemEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class OverflowItemRepository {

    public List<PCOverflowItemEntity> GetAllByPlayerID(String uuid)
    {
        List<PCOverflowItemEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCOverflowItemEntity.class)
                    .add(Restrictions.eq("playerID", uuid));
            entities = criteria.list();
        }

        return entities;
    }

    public long GetPlayerOverflowItemCount(String uuid)
    {
        try(DataContext context = new DataContext())
        {
            return (long)context.getSession()
                    .createCriteria(PCOverflowItemEntity.class)
                    .add(Restrictions.eq("playerID", uuid))
                    .setProjection(Projections.rowCount()).uniqueResult();
        }
    }

    public void Save(PCOverflowItemEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

    public void DeleteByID(int entityID)
    {
        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCOverflowItemEntity.class)
                    .add(Restrictions.eq("pcOverflowItemID", entityID));
            PCOverflowItemEntity item = (PCOverflowItemEntity)criteria.uniqueResult();

            if(item != null)
            {
                context.getSession().delete(item);
            }
        }
    }
}
