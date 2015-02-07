package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.AuthorizedDMEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class AuthorizedDMRepository {

    public AuthorizedDMEntity getByCDKey(String cdKey)
    {
        AuthorizedDMEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(AuthorizedDMEntity.class);

            entity = (AuthorizedDMEntity)criteria
                    .add(Restrictions.eq("cdKey", cdKey))
                    .add(Restrictions.eq("isActive", true))
                    .uniqueResult();
        }

        return entity;
    }

}
