package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.ProgressionLevelEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProgressionLevelRepository {

    public List<ProgressionLevelEntity> getAll()
    {
        List<ProgressionLevelEntity> entties;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ProgressionLevelEntity.class);
            entties = criteria.list();
        }

        return entties;
    }

    public ProgressionLevelEntity getByLevel(int level)
    {
        ProgressionLevelEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ProgressionLevelEntity.class)
                    .add(Restrictions.eq("level", level));

            entity = (ProgressionLevelEntity)criteria.uniqueResult();
        }

        return entity;
    }

}
