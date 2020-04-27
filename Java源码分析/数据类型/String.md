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
   
           String s4 = s3.intern();
           System.out.println(s1 == s4);// true intern()会去常量池中获得对象
   
   
       }
   ```

4. 有个成员变量保存hashcod值，因为String在很多地方需要比较，所以缓存了一个hash值。

   ```java
       /** Cache the hash code for the string */
       private int hash; // Default to 0
   
       // hash默认为0，在第一次调hashCode()的时候，若hash为0，则生成hash值。
       public int hashCode() {
           int h = hash;
           if (h == 0 && value.length > 0) {
               char val[] = value;
   
               for (int i = 0; i < value.length; i++) {
                   h = 31 * h + val[i];
               }
               hash = h;
           }
           return h;
       }
   ```

5. 两个对象equals相等，hashCode一定相等，因为调用的是用一个方法，hashCode相等则两个值equals不一定相等，因为存在hash冲突





