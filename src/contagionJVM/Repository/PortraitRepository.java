package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PortraitEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PortraitRepository {

    public PortraitEntity GetByPortraitID(int portraitID)
    {
        PortraitEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PortraitEntity.class);

            entity = (PortraitEntity)criteria.add(Restrictions.eq("portraitID", portraitID)).uniqueResult();
        }

        return entity;
    }

    public PortraitEntity GetBy2DAID(int _2daID)
    {
        PortraitEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PortraitEntity.class);

            entity = (PortraitEntity)criteria.add(Restrictions.eq("_2daID", _2daID)).uniqueResult();
        }

        return entity;
    }

    public PortraitEntity GetByResref(String portraitResref)
    {
        PortraitEntity entity = null;

        try(DataContext context = new DataContext())
        {

            Criteria criteria = context.getSession()
                    .createCriteria(PortraitEntity.class)
                    .add(Restrictions.eq("resref", portraitResref));

            List<PortraitEntity> entities = criteria.list();
            if(entities.size() > 0){
                entity = entities.get(0);
            }
        }

        return entity;
    }
    public int GetNumberOfPortraits()
    {
        try(DataContext context = new DataContext())
        {
            long result = (long)context.getSession()
                    .createCriteria(PortraitEntity.class)
                    .setProjection(Projections.rowCount())
                    .uniqueResult();

            return (int)result;

        }
    }


}
