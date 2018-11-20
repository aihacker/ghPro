package pub.types;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 11-6-22
 */
public class SortableData implements Serializable {
	
	private String sort;
	private String dir;

	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
}
