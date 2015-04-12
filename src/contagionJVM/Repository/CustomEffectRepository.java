package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.CustomEffectEntity;
import contagionJVM.Entities.PCCustomEffectEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CustomEffectRepository {

    public List<PCCustomEffectEntity> GetPCEffects(String uuid)
    {
        List<PCCustomEffectEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCCustomEffectEntity.class)
                    .add(Restrictions.eq("playerID", uuid));

            entities = criteria.list();
        }

        return entities;
    }

    public PCCustomEffectEntity GetPCEffectByID(String uuid, int customEffectID)
    {
        PCCustomEffectEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCCustomEffectEntity.class)
                    .createAlias("customEffect", "c")
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("c.customEffectID", customEffectID));
            entity = (PCCustomEffectEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public CustomEffectEntity GetEffectByID(int customEffectID)
    {
        CustomEffectEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(CustomEffectEntity.class)
                    .add(Restrictions.eq("customEffectID", customEffectID));
            entity = (CustomEffectEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public void Save(Object entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

    public void Delete(Object entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().delete(entity);
        }
    }


}
