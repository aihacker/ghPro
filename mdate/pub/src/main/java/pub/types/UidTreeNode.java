package pub.types;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 12-6-27
 */
public class UidTreeNode extends TreeNode {

    private static AtomicLong _nextUid = new AtomicLong();

    private Long uid;

    public UidTreeNode() {
        uid = _nextUid.incrementAndGet();
    }

    public UidTreeNode(Object id, String text) {
        super(id, text);
    }

    public UidTreeNode(Object id, String text, boolean leaf) {
        super(id, text, leaf);
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
