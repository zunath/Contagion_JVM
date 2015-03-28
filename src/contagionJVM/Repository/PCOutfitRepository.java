package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PCOutfitEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("UnusedDeclaration")
public class PCOutfitRepository {

    public PCOutfitEntity GetByUUID(String uuid)
    {
        PCOutfitEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCOutfitEntity.class);

            entity = (PCOutfitEntity)criteria
                    .add(Restrictions.eq("playerID", uuid))
                    .uniqueResult();
        }

        return entity;
    }

    public void Save(PCOutfitEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }
}
