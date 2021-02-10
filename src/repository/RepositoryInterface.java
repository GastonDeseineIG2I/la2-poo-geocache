package repository;


import java.util.List;

public interface RepositoryInterface
{
    public Object findById(int id);
    public void deleteById (int id);
    public List<?> getAll();

}
