package repository;


import java.util.List;

public interface RepositoryInterface
{
    public Object findById(String id);
    public void deleteById (String id);
    public List<?> getAll();

}
