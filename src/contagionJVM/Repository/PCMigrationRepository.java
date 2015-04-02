package contagionJVM.Repository;

import contagionJVM.Data.DataContext;
import contagionJVM.Entities.PCMigrationEntity;
import org.hibernate.criterion.Restrictions;

public class PCMigrationRepository {

    public PCMigrationEntity GetByMigrationID(int migrationID)
    {
        PCMigrationEntity entity;

        try(DataContext context = new DataContext())
        {
            entity = (PCMigrationEntity)context.getSession()
                    .createCriteria(PCMigrationEntity.class)
                    .add(Restrictions.eq("pcMigrationID", migrationID)).uniqueResult();
        }

        return entity;
    }


}
