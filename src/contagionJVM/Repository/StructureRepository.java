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
                    .createCriteria(ConstructionSiteEntity.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

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
                    .createCriteria(PCTerritoryFlagEntity.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
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

    public void Save(Object entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().saveOrUpdate(entity);
        }
    }

    public void Delete(Object entity)
    {
        try(DataContext context = new DataContext())
        {
            context.getSession().delete(entity);
        }
    }


    public StructureBlueprintEntity GetStructureBlueprintByID(int structureID)
    {
        StructureBlueprintEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(StructureBlueprintEntity.class)
                    .add(Restrictions.eq("structureBlueprintID", structureID));

            entity = (StructureBlueprintEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public List<StructureCategoryEntity> GetStructureCategoriesByType(boolean isTerritoryFlagCategory)
    {
        List<StructureCategoryEntity> categories;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(StructureCategoryEntity.class)
                    .add(Restrictions.eq("isActive", true))
                    .add(Restrictions.eq("isTerritoryFlagCategory", isTerritoryFlagCategory))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            categories = criteria.list();
        }

        return categories;
    }

    public List<StructureBlueprintEntity> GetStructuresByCategoryID(int categoryID)
    {
        List<StructureBlueprintEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(StructureBlueprintEntity.class)
                    .add(Restrictions.eq("isActive", true))
                    .add(Restrictions.eq("structureCategoryID", categoryID))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);;

            entities = criteria.list();
        }

        return entities;
    }

    public List<PCTerritoryFlagPermissionEntity> GetPermissionsByFlagID(int flagID)
    {
        List<PCTerritoryFlagPermissionEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCTerritoryFlagPermissionEntity.class)
                    .add(Restrictions.eq("pcTerritoryFlag.pcTerritoryFlagID", flagID))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            entities = criteria.list();
        }

        return entities;
    }

    public List<PCTerritoryFlagPermissionEntity> GetPermissionsByPlayerID(String playerID)
    {
        List<PCTerritoryFlagPermissionEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCTerritoryFlagPermissionEntity.class)
                    .add(Restrictions.eq("player.pcID", playerID))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            entities = criteria.list();
        }

        return entities;
    }

    public List<TerritoryFlagPermissionEntity> GetAllTerritorySelectablePermissions()
    {
        List<TerritoryFlagPermissionEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(TerritoryFlagPermissionEntity.class)
                    .add(Restrictions.eq("isActive", true))
                    .add(Restrictions.eq("isSelectable", true))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            entities = criteria.list();
        }

        return entities;
    }

    public TerritoryFlagPermissionEntity GetTerritoryPermissionByID(int territoryPermissionID)
    {
        TerritoryFlagPermissionEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(TerritoryFlagPermissionEntity.class)
                    .add(Restrictions.eq("territoryFlagPermissionID", territoryPermissionID))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            entity = (TerritoryFlagPermissionEntity)criteria.uniqueResult();
        }

        return entity;
    }

}
