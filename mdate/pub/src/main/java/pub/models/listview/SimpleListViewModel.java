package pub.models.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author zzl
 * Date: 2008-9-4
 * Time: 1:25:13
 */
/*
 * titles array decides visibleColIndices.
 * to hide a data column, set null title value at the corresponding index.
 */
@SuppressWarnings("unchecked")
public class SimpleListViewModel implements ListViewModel {

	private List<Object[]> data;
	private String[] titles;
	private List<Integer> visibleColIndices;

	public SimpleListViewModel() {
		visibleColIndices = new ArrayList<Integer>();
	}

	public SimpleListViewModel(List data, String... titles) {
		visibleColIndices = new ArrayList<Integer>();
		this.data = data;
		this.setTitles(titles);
	}

	public SimpleListViewModel(List data, List<String> titles) {
		this(data, titles.toArray(new String[titles.size()]));
	}

	public List<Object[]> getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public String[] getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		setTitles(titles.toArray(new String[titles.size()]));
	}

	public void setTitles(String... titles) {
		this.titles = titles;
		visibleColIndices = new ArrayList<Integer>();
		for (int colIndex = 0; colIndex < titles.length; colIndex++) {
			if (titles[colIndex] != null) {
				visibleColIndices.add(colIndex);
			}
		}
	}
	public void setTitlesEx(String[] allFields, String... fieldAndTitles) {
		Map<String, Integer> fieldIndexMap = new HashMap<String, Integer>();
		for (int colIndex = 0; colIndex < allFields.length; colIndex++) {
			fieldIndexMap.put(allFields[colIndex], colIndex);
		}
		if (fieldAndTitles.length % 2 != 0) {
			throw new RuntimeException("bad parameters");
		}
		int count = fieldAndTitles.length / 2;
		titles = new String[allFields.length];
		visibleColIndices = new ArrayList<Integer>();
		for (int n = 0; n < count; n++) {
			int colIndex = fieldIndexMap.get(fieldAndTitles[n * 2]);
			titles[colIndex] = fieldAndTitles[n * 2 + 1];
			visibleColIndices.add(colIndex);
		}
	}
	//
	public String getColTitle(int col) {
		Integer colIndex = visibleColIndices.get(col);
		return titles[colIndex];
	}

	public Object getCellValue(int row, int col) {
		Integer colIndex = visibleColIndices.get(col);
		return data.get(row)[colIndex];
	}

	public int getRowCount() {
		return data.size();
	}

	public int getColCount() {
		return visibleColIndices.size();
	}
}
