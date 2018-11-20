package pub.types;

import java.io.Serializable;
import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 * 
 * @author zzl
 * @version 11-6-26
 */
public class TreeNode implements Serializable {

	private Object			id;

	private String			text;

	private Boolean			leaf;

	private Boolean			checked;

	private Boolean			expanded;

	private List<TreeNode>	children;

	private String			iconCls;

	public TreeNode() {

	}

	public TreeNode(Object id, String text) {
		this(id, text, true);
	}

	public TreeNode(Object id, String text, boolean leaf) {
		this.id = id;
		this.text = text;
		this.leaf = leaf;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

}
