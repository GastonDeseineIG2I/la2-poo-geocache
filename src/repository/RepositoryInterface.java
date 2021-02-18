package repository;


import java.util.List;

public interface RepositoryInterface<T>
{
    public Object findById(String id);
    public void deleteById (String id);
    public List<?> getAll();
    public void create(T object);
    public void update(T object);

}
