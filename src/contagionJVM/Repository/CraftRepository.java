package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
                    .add(Restrictions.eq("craftBlueprintID", blueprintID))
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
            maxLevel = (long)context.getSession()
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

    public List<CraftBlueprintCategoryEntity> GetCategoriesAvailableToPC(String uuid)
    {
        List<CraftBlueprintCategoryEntity> entities;

        try(DataContext context = new DataContext())
        {
            Criteria criteria = context.getSession()
                    .createCriteria(PCBlueprintEntity.class)
                    .createAlias("blueprint", "bp")
                    .createAlias("bp.category", "c")
                    .setProjection(Projections.distinct(Projections.property("c.craftBlueprintCategoryID")))
                    .add(Restrictions.eq("playerID", uuid));
            List<Integer> categories = criteria.list();

            criteria = context.getSession()
                    .createCriteria(CraftBlueprintCategoryEntity.class)
                    .add(Restrictions.in("craftBlueprintCategoryID", categories));
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
