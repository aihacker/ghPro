package pub.types;

/**
 * Created by zzl on 2015/11/6.
 */
public class IntPair {

    private Integer a;
    private Integer b;

    public IntPair() {
        //
    }

    public IntPair(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntPair)) return false;

        IntPair intPair = (IntPair) o;

        if (a != null ? !a.equals(intPair.a) : intPair.a != null) return false;
        return !(b != null ? !b.equals(intPair.b) : intPair.b != null);

    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
