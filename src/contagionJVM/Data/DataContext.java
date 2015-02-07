package contagionJVM.Data;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class DataContext implements AutoCloseable {

    Transaction tran;

    public DataContext()
    {
        tran = DataAccess.getSession().beginTransaction();
    }

    public Session getSession()
    {
        return DataAccess.getSession();
    }


    @Override
    public void close()  {
        tran.commit();
    }
}
