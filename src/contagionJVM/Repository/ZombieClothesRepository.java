package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.ZombieClothesEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ZombieClothesRepository {

    public ZombieClothesEntity GetRandomClothes()
    {
        ZombieClothesEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ZombieClothesEntity.class);

            entity = (ZombieClothesEntity)criteria
                    .add(Restrictions.eq("resref", ""))
                    .add(Restrictions.sqlRestriction("1=1 order by rand()"))
                    .setMaxResults(1)
                    .uniqueResult();
        }

        return entity;
    }

}
