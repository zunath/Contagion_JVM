package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.BadgeEntity;
import contagionJVM.Entities.PCBadgeEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import java.util.List;

public class BadgeRepository {

    public List<PCBadgeEntity> GetByUUID(String uuid)
    {
        List<PCBadgeEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCBadgeEntity.class)
                    .add(Restrictions.eq("playerID", uuid));

            entities = criteria.list();
        }

        return entities;
    }

    public PCBadgeEntity GetByID(String uuid, int badgeID)
    {
        PCBadgeEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCBadgeEntity.class)
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("badgeID", badgeID));

            entity = (PCBadgeEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public BadgeEntity GetByID(int badgeID)
    {
        BadgeEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(BadgeEntity.class)
                    .add(Restrictions.eq("badgeID", badgeID));

            entity = (BadgeEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public void Save(PCBadgeEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }


}
