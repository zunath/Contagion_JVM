package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PlayerEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("UnusedDeclaration")
public class PlayerRepository {

    public PlayerEntity getByUUID(String uuid)
    {
        PlayerEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PlayerEntity.class);

            entity = (PlayerEntity)criteria
                    .add(Restrictions.eq("pcID", uuid))
                    .uniqueResult();

        }


        return entity;
    }



    public void save(PlayerEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }


}
