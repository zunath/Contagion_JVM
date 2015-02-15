package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PCCorpseEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PCCorpseRepository {

    public PCCorpseEntity GetByID(int corpseID)
    {
        PCCorpseEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCCorpseEntity.class);

            entity = (PCCorpseEntity)criteria
                    .add(Restrictions.eq("pcCorpseID", corpseID))
                    .uniqueResult();
        }

        return entity;
    }

    public List<PCCorpseEntity> GetAll()
    {
        List<PCCorpseEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCCorpseEntity.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            entities = criteria.list();
        }

        return entities;
    }

    public void Delete(PCCorpseEntity entity)
    {
        if(entity == null) return;

        try(DataContext context = new DataContext())
        {
            context.getSession().delete(entity);
        }
    }

    public void Save(PCCorpseEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

}
