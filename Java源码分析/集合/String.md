# 概览

1. String的底层结构是字符数组

```java
    /** The value is used for character storage. */
    private final char value[];
```

2. String是final修饰的类，是不可变的，所以是线程安全的。不可变的意思是，不能改变对象内的成员变量，包括基本数据类型的值不能改变，引用类型的变量不能指向其他的对象，引用类型指向的对象的状态也不能改变。

3. JVM对String做过特殊处理，使String的数据保存在常量池中，常量池位于方法区。

   ```java
       public static void main(String[] args) {
   
           String s1 = "abc";
           String s2 = "abc";
           System.out.println(s1 == s2); // true 因为s1，s2指向的都是常量池中的"abc"
   
   
           String s3 = new String("abc"); // 1、在常量池中查找是否有"abc"这个对象，没有则创建。2、new一个对象。3、创建一个引用
           System.out.println(s1 == s3); // false,因为s3 指向的是堆中的对象
   
   
       }
   ```

   

