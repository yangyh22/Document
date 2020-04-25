# 概览

1. String的底层结构是字符数组

```java
    /** The value is used for character storage. */
    private final char value[];
```

2. String是final修饰的类，是不可变的，所以是线程安全的。不能改变状态的意思是，不能改变对象内的成员变量，包括基本数据类型的值不能改变，引用类型的变量不能指向其他的对象，引用类型指向的对象的状态也不能改变。