package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PCAuthorizedCDKeyEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class PCAuthorizedCDKeysRepository {

    public PCAuthorizedCDKeyEntity GetByUUID(String uuid)
    {
        PCAuthorizedCDKeyEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCAuthorizedCDKeyEntity.class);

            entity = (PCAuthorizedCDKeyEntity)criteria
                    .add(Restrictions.eq("playerID", uuid))
                    .uniqueResult();
        }

        return entity;
    }

    public void Save(PCAuthorizedCDKeyEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

}
