public abstract class A /* A*/<T extends A<T>>  extends B<java.util.Collection<? extends T>> implements C<T> {
  public  A();//  .ctor()



public class Inner /* A.Inner*/<D>  extends B<java.util.Collection<? extends T>> implements C<D> {
  public  Inner();//  .ctor()

}@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, xi = 2, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u0012\u0012\u0004\u0012\u0002H\u00010\u0002R\b\u0012\u0004\u0012\u00028\u00000\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u0005¢\u0006\u0002\u0010\u0005"}, d2 = {"LA$Inner2;", "X", "LA$Inner;", "LA;", "LC;", "(LA;)V"})
public final class Inner2 /* A.Inner2*/<X>  extends A<T>.Inner<X> implements C<X> {
  public  Inner2();//  .ctor()

}}