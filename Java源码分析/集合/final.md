# 修饰在变量前面

两种情况：如果是基本数据类型，被final修饰的变量一旦初始化就不能改变；

如果是引用数据类型的变量，初始化之后不能指向另外一个对象。



## 基本数据类型

变量可以初始化，但是初始化之后就不能再修改了

```java
    public static void main(String[] args) {
        final int a;
        int b=234;
        a=b;
        a=b;//编译通不过，会报错建议去掉fianl
        System.out.println(a);
    }
```



## 引用类型

主要表示的是指向的堆中的对象不能改变。对引用对象后续的操作没影响，所以final修饰的引用对象是有改变的风险的。

```java
    public static void main(String[] args) {
        final StringBuffer a = new StringBuffer("a");
        System.out.println(a);// 输出a
        a.append("b");
        System.out.println(a);// 输出ab
        // a = new StringBuffer();// 直接报错

        StringBuffer c = new StringBuffer("c");
        final StringBuffer d = c;
        c.append("2");
        System.out.println(d);// 输出d2，此时c和d指向的都是堆中的同一个对象，c改变了，d也改变了
        d.append("3");
        System.out.println(c);// 输出c23

    }
```

