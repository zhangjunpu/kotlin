public final class AnnotatedParameterInInnerClassConstructor /* test.AnnotatedParameterInInnerClassConstructor*/ {
  public  AnnotatedParameterInInnerClassConstructor();//  .ctor()



public final class Inner /* test.AnnotatedParameterInInnerClassConstructor.Inner*/ {
  public  Inner(@org.jetbrains.annotations.NotNull() @test.Anno(x = "a") java.lang.String, @org.jetbrains.annotations.NotNull() @test.Anno(x = "b") java.lang.String);//  .ctor(java.lang.String, java.lang.String)

}@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, xi = 2, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000E\n\u0000\b\u0086\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0019\u0012\b\b\u0001\u0010\u0003\u001A\u00028\u0000\u0012\b\b\u0001\u0010\u0004\u001A\u00020\u0005¢\u0006\u0002\u0010\u0006"}, d2 = {"Ltest/AnnotatedParameterInInnerClassConstructor$InnerGeneric;", "T", "", "a", "b", "", "(Ltest/AnnotatedParameterInInnerClassConstructor;Ljava/lang/Object;Ljava/lang/String;)V"})
public final class InnerGeneric /* test.AnnotatedParameterInInnerClassConstructor.InnerGeneric*/<T>  {
  public  InnerGeneric(@test.Anno(x = "a") T, @org.jetbrains.annotations.NotNull() @test.Anno(x = "b") java.lang.String);//  .ctor(T, java.lang.String)

}}