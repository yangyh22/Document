# 概览

1. String的底层结构是字符数组

```java
    /** The value is used for character storage. */
    private final char value[];
```

2. String是final修饰的类，是不可变的，所以是线程安全的。