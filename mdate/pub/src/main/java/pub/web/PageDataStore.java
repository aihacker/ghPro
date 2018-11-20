package pub.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pub.functions.MathFuncs;
import pub.functions.ReqFuncs;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-3-4
 */
@SuppressWarnings("unchecked")
public class PageDataStore {

	/*-------------------------------------------------------------------+
	 |                                                                   |
	 |                          INTERNAL STATE                           |
	 |                                                                   |
	 +-------------------------------------------------------------------*/

	private Object data;
	// System.currentTimeMillis
	private long lastVisitTime;

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public long getLastVisitTime() {
		return lastVisitTime;
	}
	public void setLastVisitTime(long lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	/*-------------------------------------------------------------------+
	 |                                                                   |
	 |                         IMPLEMENTATION                            |
	 |                                                                   |
	 +-------------------------------------------------------------------*/

	private static Integer generatePageKey() {
		final HttpSession session = ServletUtils.getSession();
		Integer pageKey = null;
		synchronized (session.getAttribute("SESSION_LOCK")) {
			pageKey = (Integer) session.getAttribute("NEXT_PAGE_KEY");
			if (pageKey == null) {
				pageKey = MathFuncs.randInt();
			}
			session.setAttribute("NEXT_PAGE_KEY", pageKey + 1);
		}
		return pageKey;
	}

	private static void setPageData(Integer key, Object PageDataStore) {
		final HttpSession session = ServletUtils.getSession();
		synchronized (session.getAttribute("SESSION_LOCK")) {
			Map<Integer, PageDataStore> dataInfoMap = (Map<Integer, PageDataStore>)
				session.getAttribute("PAGE_DATA_INFO_MAP");
			PageDataStore pageDataInfo = new PageDataStore();
			pageDataInfo.setData(PageDataStore);
			pageDataInfo.setLastVisitTime(System.currentTimeMillis());
			dataInfoMap.put(key, pageDataInfo);
			System.out.println("info: PageDataStore set with key " + key);
			// passive cleanup
			cleanObsoletePageDatas();
		}
	}

	private static Object getPageData(Integer key) {
		final HttpSession session = ServletUtils.getSession();
		Map<Integer, PageDataStore> storeMap = (Map<Integer, PageDataStore>)
			session.getAttribute("PAGE_DATA_INFO_MAP");
		Object pageData = null;
		synchronized (session.getAttribute("SESSION_LOCK")) {
			PageDataStore store = storeMap.get(key);
			if (store == null) {
				System.out.println("Warning: getPageData with invalid key? " + key);
				//throw new RuntimeException("PageData outdated");
				return null;
			}
			store.setLastVisitTime(System.currentTimeMillis());
			pageData = store.getData();
		}
		return pageData;
	}

	// passive cleanup
	private static void cleanObsoletePageDatas() {
		final HttpSession session = ServletUtils.getSession();
		Long lastCleanTime = (Long) session.getAttribute("LAST_PAGE_DATA_CLEAN_TIME");
		long now = System.currentTimeMillis();

		final int MIN_CLEAN_INTERVAL = 15;  // 15" idle time to avoid unnecessary busy cleaning
		final int MAX_IDLE_TIME = 60;	 //client keep-alive timer is 30"

		if ((now - lastCleanTime) / 1000 < MIN_CLEAN_INTERVAL) {
			return;
		}
		else {
			session.setAttribute("LAST_PAGE_DATA_CLEAN_TIME", now);
		}

		Map<Integer, PageDataStore> dataInfoMap = (Map<Integer, PageDataStore>)
			session.getAttribute("PAGE_DATA_INFO_MAP");
		List<Integer> pageKeys = new ArrayList<Integer>(dataInfoMap.keySet());
		for (Integer key : pageKeys) {
			PageDataStore info = dataInfoMap.get(key);
			if ((now - info.getLastVisitTime()) / 1000 > MAX_IDLE_TIME) {
				dataInfoMap.remove(key);
			}
		}
	}

	/*-------------------------------------------------------------------+
	 |                                                                   |
	 |                            API                                    |
	 |                                                                   |
	 +-------------------------------------------------------------------*/
	public static Integer save(HttpServletRequest request, Object data) {
		Integer pageKey = generatePageKey();
		setPageData(pageKey, data);

		request.setAttribute("pageKey", pageKey);

		return pageKey;
	}

	public static Object load() {
		return load(ServletUtils.getRequest());
	}

	public static Object load(HttpServletRequest request) {
		Integer pageKey = ReqFuncs.getIntegerParam(request, "pageKey");
		request.setAttribute("pageKey", pageKey);
		return getPageData(pageKey);
	}

	public static void keepAlive(HttpServletRequest request) {
		Integer pageKey = ReqFuncs.getIntegerParam(request, "pageKey");
		final HttpSession session = ServletUtils.getSession();
		Map<Integer, PageDataStore> dataInfoMap = (Map<Integer, PageDataStore>)
			session.getAttribute("PAGE_DATA_INFO_MAP");
		synchronized (session.getAttribute("SESSION_LOCK")) {
			PageDataStore pageDataInfo = dataInfoMap.get(pageKey);
			if (pageDataInfo == null) {
				throw new RuntimeException("outdated pageKey? " + pageKey);
			}
			pageDataInfo.setLastVisitTime(System.currentTimeMillis());
//			cleanObsoletePageDatas();
		}
	}

	public static void abandon(HttpServletRequest request) {
		Integer pageKey = ReqFuncs.getIntegerParam(request, "pageKey");
		final HttpSession session = ServletUtils.getSession();
		Map<Integer, PageDataStore> dataInfoMap = (Map<Integer, PageDataStore>)
			session.getAttribute("PAGE_DATA_INFO_MAP");
		synchronized (session.getAttribute("SESSION_LOCK")) {
			dataInfoMap.remove(pageKey);
		}
	}

	public static void save(Object data) {
		save(ServletUtils.getRequest(), data);
	}

	public static void initialize(HttpSession session) {
		session.setAttribute("SESSION_LOCK", new Object());
		session.setAttribute("PAGE_DATA_INFO_MAP", new HashMap<Integer, PageDataStore>());
		session.setAttribute("LAST_PAGE_DATA_CLEAN_TIME", 0L);
	}

	public static void save(HttpServletRequest request, String attrName, Object attrValue) {
		Integer pageKey = (Integer) request.getAttribute("pageKey");
		if (pageKey == null) {
			pageKey = save(request, new HashMap<String, Object>());
		}
		HashMap<String, Object> dataMap = (HashMap<String, Object>) getPageData(pageKey);
		// avoid NPE when stale page with pageKey in url get refreshed 
		if (dataMap == null) {
			HttpSession session = request.getSession();
			synchronized (session.getAttribute("SESSION_LOCK")) {
				Integer nextPageKey = (Integer) session.getAttribute("NEXT_PAGE_KEY");
				if (nextPageKey == null || nextPageKey <= pageKey) {
					nextPageKey = pageKey + 1;
					session.setAttribute("NEXT_PAGE_KEY", nextPageKey);
				}
			}
			dataMap = new HashMap<String, Object>();
			setPageData(pageKey, dataMap);
		}
		dataMap.put(attrName, attrValue);
	}

	public static Object load(HttpServletRequest request, String attrName) {
		HashMap<String, Object> dataMap = (HashMap<String, Object>) load(request);
		if (dataMap == null) {
			return null;
		}
		return dataMap.get(attrName);
	}
}
