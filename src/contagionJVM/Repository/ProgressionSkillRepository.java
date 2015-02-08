package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.ProgressionLevelEntity;
import contagionJVM.Entities.ProgressionSkillEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class ProgressionSkillRepository {

    public ProgressionSkillEntity getByID(int skillID)
    {
        ProgressionSkillEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ProgressionLevelEntity.class);

            entity = (ProgressionSkillEntity)criteria.add(Restrictions.eq("skillID", skillID)).uniqueResult();
        }

        return entity;
    }

}
