package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PCSearchSiteEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class SearchSiteRepository {

    public PCSearchSiteEntity GetSearchSiteByID(int searchSiteID, String playerID)
    {
        PCSearchSiteEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCSearchSiteEntity.class);

            entity = (PCSearchSiteEntity)criteria
                    .add(Restrictions.eq("playerID", playerID))
                    .add(Restrictions.eq("searchSiteID", searchSiteID))
                    .uniqueResult();
        }

        return entity;
    }

    public void Save(PCSearchSiteEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

    public void Delete(PCSearchSiteEntity entity)
    {
        if(entity == null) return;

        try(DataContext context = new DataContext())
        {
            context.getSession().delete(entity);
        }
    }
}
