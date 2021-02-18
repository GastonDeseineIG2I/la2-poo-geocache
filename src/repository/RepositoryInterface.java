package repository;


import java.util.HashMap;
import java.util.List;

public interface RepositoryInterface
{
    public Object findById(String id);
    public void deleteById (String id);
    public List<?> getAll();
    public void create(HashMap<String, Object> data);
    public void update(HashMap<String, Object> data);

}
