package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CraftRepository {

    public boolean AddBlueprintToPC(String uuid, int blueprintID)
    {
        boolean addedSuccessfully = false;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCBlueprintEntity.class)
                    .createAlias("blueprint", "b")
                    .add(Restrictions.eq("b.craftBlueprintID", blueprintID))
                    .add(Restrictions.eq("playerID", uuid));
            PCBlueprintEntity entity = (PCBlueprintEntity)criteria.uniqueResult();

            if(entity == null)
            {
                criteria = context.getSession()
                        .createCriteria(CraftBlueprintEntity.class)
                        .add(Restrictions.eq("craftBlueprintID", blueprintID));
                CraftBlueprintEntity blueprint = (CraftBlueprintEntity)criteria.uniqueResult();

                entity = new PCBlueprintEntity();
                entity.setPlayerID(uuid);
                entity.setBlueprint(blueprint);
                DateTime dt = new DateTime(DateTimeZone.UTC);
                entity.setAcquiredDate(new Timestamp(dt.getMillis()));

                context.getSession().saveOrUpdate(entity);
                addedSuccessfully = true;
            }
        }

        return addedSuccessfully;
    }

    public PCBlueprintEntity GetPCBlueprintByID(String uuid, int blueprintID)
    {
        PCBlueprintEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCBlueprintEntity.class)
                    .createAlias("blueprint", "b")
                    .add(Restrictions.eq("b.craftBlueprintID", blueprintID))
                    .add(Restrictions.eq("playerID", uuid));
            entity = (PCBlueprintEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public CraftBlueprintEntity GetBlueprintByID(int blueprintID)
    {
        CraftBlueprintEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(CraftBlueprintEntity.class)
                    .add(Restrictions.eq("craftBlueprintID", blueprintID));

            entity = (CraftBlueprintEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public PCCraftEntity GetPCCraftByID(String uuid, int craftID)
    {
        PCCraftEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCCraftEntity.class)
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("craftID", craftID));
            entity = (PCCraftEntity)criteria.uniqueResult();

            if(entity == null)
            {
                entity = new PCCraftEntity();
                entity.setExperience(0);
                entity.setLevel(1);
                entity.setPlayerID(uuid);
                entity.setCraftID(craftID);

                context.getSession().saveOrUpdate(entity);
            }

        }

        return entity;
    }

    public CraftLevelEntity GetCraftLevelByLevel(int craftID, int level)
    {
        CraftLevelEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(CraftLevelEntity.class)
                    .add(Restrictions.eq("craftID", craftID))
                    .add(Restrictions.eq("level", level));
            entity = (CraftLevelEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public long GetCraftMaxLevel(int craftID)
    {
        long maxLevel;

        try(DataContext context = new DataContext())
        {
            maxLevel = (int)context.getSession()
                    .createCriteria(CraftLevelEntity.class)
                    .add(Restrictions.eq("craftID", craftID))
                    .setProjection(Projections.max("level")).uniqueResult();
        }

        return maxLevel;
    }

    public CraftEntity GetCraftByID(int craftID)
    {
        CraftEntity entity;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(CraftEntity.class)
                    .add(Restrictions.eq("craftID", craftID));

            entity = (CraftEntity)criteria.uniqueResult();
        }

        return entity;
    }

    public List<CraftBlueprintCategoryEntity> GetCategoriesAvailableToPCByCraftID(String uuid, int craftID)
    {
        List<CraftBlueprintCategoryEntity> entities = new ArrayList<>();

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCBlueprintEntity.class)
                    .createAlias("blueprint", "bp")
                    .createAlias("bp.category", "c")
                    .createAlias("bp.craft", "cr")
                    .setProjection(Projections.distinct(Projections.property("c.craftBlueprintCategoryID")))
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("cr.craftID", craftID));
            List<Integer> categories = criteria.list();

            if(categories.size() > 0)
            {
                criteria = context.getSession()
                        .createCriteria(CraftBlueprintCategoryEntity.class)
                        .add(Restrictions.in("craftBlueprintCategoryID", categories));
                entities = criteria.list();
            }
        }

        return entities;
    }

    public List<CraftBlueprintCategoryEntity> GetCategoriesAvailableToPC(String uuid)
    {
        List<CraftBlueprintCategoryEntity> entities = new ArrayList<>();

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCBlueprintEntity.class)
                    .createAlias("blueprint", "bp")
                    .createAlias("bp.category", "c")
                    .createAlias("bp.craft", "cr")
                    .setProjection(Projections.distinct(Projections.property("c.craftBlueprintCategoryID")))
                    .add(Restrictions.eq("playerID", uuid));
            List<Integer> categories = criteria.list();

            if(categories.size() > 0)
            {
                criteria = context.getSession()
                        .createCriteria(CraftBlueprintCategoryEntity.class)
                        .add(Restrictions.in("craftBlueprintCategoryID", categories));
                entities = criteria.list();
            }
        }

        return entities;
    }


    public List<PCBlueprintEntity> GetPCBlueprintsByCategoryID(String uuid, int categoryID)
    {
        List<PCBlueprintEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCBlueprintEntity.class)
                    .createAlias("blueprint", "bp")
                    .createAlias("bp.category", "c")
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .add(Restrictions.eq("playerID", uuid))
                    .add(Restrictions.eq("c.craftBlueprintCategoryID", categoryID));

            entities = criteria.list();

        }

        return entities;
    }

    public List<CraftEntity> GetAllCrafts()
    {
        List<CraftEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(CraftEntity.class)
                    .add(Restrictions.eq("isActive", true));
            entities = criteria.list();
        }

        return entities;
    }

    public void Save(Object entity)
    {
        try(DataContext contex = new DataContext())
        {
            contex.getSession().saveOrUpdate(entity);
        }
    }

}
