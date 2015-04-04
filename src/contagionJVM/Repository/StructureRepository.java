package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class StructureRepository {

    public List<ConstructionSiteEntity> GetAllConstructionSites()
    {
        List<ConstructionSiteEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ConstructionSiteEntity.class);
            entities = criteria.list();
        }

        return entities;
    }

    public List<PCTerritoryFlagEntity> GetAllTerritoryFlags()
    {
        List<PCTerritoryFlagEntity> flags;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCTerritoryFlagEntity.class);
            flags = criteria.list();
        }

        return flags;
    }

    public PCTerritoryFlagEntity GetPCTerritoryFlagByID(int pcTerritoryFlagID)
    {
        PCTerritoryFlagEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCTerritoryFlagEntity.class)
                    .add(Restrictions.eq("pcTerritoryFlagID", pcTerritoryFlagID));

            entity = (PCTerritoryFlagEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public ConstructionSiteEntity GetConstructionSiteByID(int constructionSiteID)
    {
        ConstructionSiteEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(ConstructionSiteEntity.class)
                    .add(Restrictions.eq("constructionSiteID", constructionSiteID));
            entity = (ConstructionSiteEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public PCTerritoryFlagStructureEntity GetPCStructureByID(int territoryFlagStructureID)
    {
        PCTerritoryFlagStructureEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCTerritoryFlagStructureEntity.class)
                    .add(Restrictions.eq("pcTerritoryFlagStructureID", territoryFlagStructureID));

            entity = (PCTerritoryFlagStructureEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public void Delete(PCTerritoryFlagStructureEntity entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().delete(entity);
        }
    }

    public List<StructureCategoryEntity> GetAllStructureCategories()
    {
        List<StructureCategoryEntity> categories;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(StructureCategoryEntity.class)
                    .add(Restrictions.eq("isActive", true));

            categories = criteria.list();
        }

        return categories;
    }

}
