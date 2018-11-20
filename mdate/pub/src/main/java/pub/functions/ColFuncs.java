package pub.functions;

import java.lang.reflect.Array;
import java.util.*;

/**
 * describe: Created by IntelliJ IDEA.
 * 
 * @author zzl
 * @version 2008-8-5
 */
@SuppressWarnings("unchecked")
public class ColFuncs {

	public static <T> boolean equals(List<T> list1, List<T> list2) {
        if(list1 == null) {
            list1 = Collections.emptyList();
        }
        if(list2 == null) {
            list2 = Collections.emptyList();
        }
        int count = list1.size();
        if(list2.size() != count) {
            return false;
        }
        for (int n = 0; n < count; n++) {
            T v1 = list1.get(n);
            T v2 = list2.get(n);
            if(v1 == null) {
                if(v2 == null) {
                    continue;
                }
                else {
                    return false;
                }
            }
            if(!v1.equals(v2)) {
                return false;
            }
        }
        return true;
    }

	public static <T> boolean notEmpty(List<T> list) {
		return list != null && list.size() > 0;
	}

	public static <T> boolean notEmpty(T[] arr) {
		return arr != null && arr.length > 0;
	}

	public static <T> List<T> toList(T[] arr) {
		return Arrays.asList(arr);
	}

	public static Integer[] StringArrayToIntegerArray(String[] arr) {
		if (arr == null) {
			return new Integer[0];
		}
		Integer[] result = new Integer[arr.length];
		for (int n = 0; n < arr.length; n++) {
			result[n] = Integer.valueOf(arr[n]);
		}
		return result;
	}

	public static <T> T[] compactArray(T[] arr) {
		List<T> list = new ArrayList<T>();
		for (T t : arr) {
			if (t != null) {
				list.add(t);
			}
		}
		if (list.size() == arr.length) {
			return arr;
		}
		arr = (T[]) Array.newInstance(arr.getClass().getComponentType(), 0);
		return list.toArray(arr);
	}

	public static <T> boolean isLast(T item, T[] items) {
		return items[items.length - 1] == item;
	}

	public static <T> boolean isEmpty(T[] arr) {
		return !notEmpty(arr);
	}

	public static <T> T last(List<T> col) {
		return col.get(col.size() - 1);
	}

	public static <T> Set<T> except(Set<T> targetSet, Set<T> exceptSet) {
		Set<T> set = new HashSet<T>(targetSet);
		set.removeAll(exceptSet);
		return set;
	}

	public static Map<String, Object> map(Object... params) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int n = 0; n < params.length; n += 2) {
			map.put(params[n].toString(), params[n + 1]);
		}
		return map;
	}

	public static<T> T[] removeFirst(T[] arr) {
		return Arrays.copyOfRange(arr, 1, arr.length);
	}

	public static<T> List<T> nvlList(List<T> list) {
		return list == null? Collections.<T>emptyList() : list;
	}

	public static interface Distinctor<T> {
		boolean isDistinct(T prevItem, T thisItem);
	}

	public static <T> void distinct(List<T> data, Distinctor<T> distinctor) {
		List<Integer> indicesToRemove = new ArrayList<Integer>();
		T prevItem = null;
		for (int n = 0; n < data.size(); n++) {
			if (n > 0 && !distinctor.isDistinct(prevItem, data.get(n))) {
				indicesToRemove.add(n);
			}
			prevItem = data.get(n);
		}
		for (int n = indicesToRemove.size() - 1; n >= 0; n--) {
			data.remove((int) indicesToRemove.get(n));
		}
	}

	public static String listToString(List<String> stringList, String regex) {
		String result = "";
		if (ColFuncs.notEmpty(stringList)) {
			for (int index = 0; index < stringList.size(); index++) {
				result += stringList.get(index) + regex;
			}
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static boolean contains(Collection c, Object item) {
		return c.contains(item);
	}
}
