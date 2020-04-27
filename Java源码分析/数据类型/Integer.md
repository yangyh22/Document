# 概览

1. Integer，Java为了提高性能提供了和String类一样的对象池机制，当然Java的八种基本类型的包装类（Packaging Type）也有对象池机制。
   Integer i1=40；Java在编译的时候会执行将代码封装成Integer i1=Integer.valueOf(40)

```java
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```