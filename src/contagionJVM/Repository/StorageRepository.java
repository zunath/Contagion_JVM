package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.StorageContainerEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("UnusedDeclaration")
public class StorageRepository {

    public StorageContainerEntity GetByContainerID(int containerID)
    {
        StorageContainerEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(StorageContainerEntity.class)
                    .add(Restrictions.eq("storageContainerID", containerID));
            entity = (StorageContainerEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public void Save(StorageContainerEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

}
