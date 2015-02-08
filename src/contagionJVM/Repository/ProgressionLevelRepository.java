package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.ProgressionLevelEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ProgressionLevelRepository {

    public ProgressionLevelEntity getByLevel(int level)
    {
        ProgressionLevelEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ProgressionLevelEntity.class);

            entity = (ProgressionLevelEntity)criteria.add(Restrictions.eq("level", level)).uniqueResult();
        }

        return entity;
    }

}
