package pub.types;

import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 11-3-24
 */
public class HierarchicalIdText extends IdText {

	private Integer level;
	private HierarchicalIdText parent;
	private List<HierarchicalIdText> children;

	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public HierarchicalIdText getParent() {
		return parent;
	}
	public void setParent(HierarchicalIdText parent) {
		this.parent = parent;
	}
	public List<HierarchicalIdText> getChildren() {
		return children;
	}
	public void setChildren(List<HierarchicalIdText> children) {
		this.children = children;
	}
	public int getChildCount() {
		return children == null? 0: children.size();
	}
}
