package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PlayerProgressionSkillEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import java.util.List;

public class PlayerProgressionSkillsRepository {

    public List<PlayerProgressionSkillEntity> getByPlayerUUID(String uuid)
    {
        List<PlayerProgressionSkillEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PlayerProgressionSkillEntity.class);

            entities = criteria.add(Restrictions.eq("pcID", uuid)).list();
        }

        return entities;
    }

    public PlayerProgressionSkillEntity GetByUUIDAndSkillID(String uuid, int skillID)
    {
        PlayerProgressionSkillEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PlayerProgressionSkillEntity.class);

            entity = (PlayerProgressionSkillEntity)criteria
                    .add(Restrictions.eq("pcID", uuid))
                    .add(Restrictions.eq("progressionSkillID", skillID)).uniqueResult();
        }

        return entity;
    }

    public void deleteAllByPlayerUUID(String uuid)
    {
        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PlayerProgressionSkillEntity.class);

            List<PlayerProgressionSkillEntity> entities = criteria.add(Restrictions.eq("pcID", uuid)).list();
            for(PlayerProgressionSkillEntity entity : entities)
            {
                context.getSession().delete(entity);
            }
        }
    }

    public void save(PlayerProgressionSkillEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

}
