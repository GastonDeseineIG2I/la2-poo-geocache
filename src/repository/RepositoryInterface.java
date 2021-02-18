package repository;


import java.util.HashMap;
import java.util.List;

public interface RepositoryInterface
{
    Object findById(String id);
    void deleteById (String id);
    List<?> getAll();
    void create(HashMap<String, Object> data);
    void update(HashMap<String, Object> data);

}
