package pub.types;

import java.io.Serializable;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-5-9
 */
/*
 * 郝金隆 2008-08-20
 * 
 * 修改内容：添加serialVersionUID，以避免因为版本不同引起错误
 */
public class EntityData implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4607770385333326942L;
	protected EntityStatus status;
	private boolean standalone = false;

	public EntityData() {
		status = EntityStatus.UNCHANGED;
	}

	public EntityStatus getStatus() {
		return status;
	}
	public void setStatus(EntityStatus status) {
		this.status = status;
	}

	public boolean getListable() {
		return status != EntityStatus.DELETED && status != EntityStatus.TEMP;
	}

	public boolean getIsNew() {
		return status == EntityStatus.NEW;
	}

	public boolean isStandalone() {
		return standalone;
	}
	public void setStandalone(boolean standalone) {
		this.standalone = standalone;
	}
	public boolean getIsDirty() {
		return status == EntityStatus.NEW ||
			status == EntityStatus.MODIFIED ||
			status == EntityStatus.DELETED;
	}
}
