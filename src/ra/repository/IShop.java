package ra.repository;

import java.util.List;

public interface IShop<T> {
    void save(T t);

    List<T> getAll();
    T findById(int id);
   void delete(T t);
  void   update(List<T> t);
  int findByIndex(int id) ;
  int autoInc ();
}
