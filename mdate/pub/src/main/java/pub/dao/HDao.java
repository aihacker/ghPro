package pub.dao;

public interface HDao<T> extends Dao<T> {

	void flush();
	void clear();
	<T> void evict(T t);
	<T> void merge(T t);	
}
