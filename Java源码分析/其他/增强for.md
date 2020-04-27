# 概览

增强for的本质是iterator迭代器

```java
public static void main(String[] args) {
    List<String> platformList = new ArrayList<>();
    platformList.add("a");
    platformList.add("b");
    platformList.add("c");

    for (String platform : platformList) {
        if (platform.equals("a")) {
            // 增加for的本质是iterator迭代器 ，因此在迭代器的循环中，直接删除会快速失败
            platformList.remove(platform);
        }
    }

    System.out.println(platformList);
}
```



```java
public static void main(String[] args) {
    List<String> list = new ArrayList<>();
    list.add("a");
    list.add("b");
    list.add("c");


    for (int i = list.size() - 1; i >= 0; i--) {

        // c
        // b
        // a
        System.out.println(list.get(i));
        if (list.get(i).equals("a")) {
            list.remove("a");
        }
    }

    // [b, c]
    System.out.println(list);

}
```



```java
public static void main(String[] args) {
    List<String> list = new ArrayList<>();
    list.add("a");
    list.add("b");
    list.add("c");


    for (int i = 0; i < list.size(); i++) {

        // a
        // b
        // c
        System.out.println(list.get(i));
        if (list.get(i).equals("a")) {
            list.remove("a");
            // 这里必须要i--不然会少一个
            i--;
        }
    }

    // [b, c]
    System.out.println(list);

}
```



字节码

```b
// class version 52.0 (52)
// access flags 0x21
public class Test {

  // compiled from: Test.java

  // access flags 0x1
  public <init>()V
   L0
    LINENUMBER 4 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
    RETURN
   L1
    LOCALVARIABLE this LTest; L0 L1 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x9
  public static main([Ljava/lang/String;)V
   L0
    LINENUMBER 8 L0
    NEW java/util/ArrayList
    DUP
    INVOKESPECIAL java/util/ArrayList.<init> ()V
    ASTORE 1
   L1
    LINENUMBER 9 L1
    ALOAD 1
    LDC "a"
    INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z (itf)
    POP
   L2
    LINENUMBER 10 L2
    ALOAD 1
    LDC "b"
    INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z (itf)
    POP
   L3
    LINENUMBER 11 L3
    ALOAD 1
    LDC "c"
    INVOKEINTERFACE java/util/List.add (Ljava/lang/Object;)Z (itf)
    POP
   L4
    LINENUMBER 13 L4
    ALOAD 1
    INVOKEINTERFACE java/util/List.iterator ()Ljava/util/Iterator; (itf)
    ASTORE 2
   L5
   FRAME APPEND [java/util/List java/util/Iterator]
    ALOAD 2
    INVOKEINTERFACE java/util/Iterator.hasNext ()Z (itf)
    IFEQ L6
    ALOAD 2
    INVOKEINTERFACE java/util/Iterator.next ()Ljava/lang/Object; (itf)
    CHECKCAST java/lang/String
    ASTORE 3
   L7
    LINENUMBER 14 L7
    ALOAD 3
    LDC "a"
    INVOKEVIRTUAL java/lang/String.equals (Ljava/lang/Object;)Z
    IFEQ L8
   L9
    LINENUMBER 15 L9
    ALOAD 1
    ALOAD 3
    INVOKEINTERFACE java/util/List.remove (Ljava/lang/Object;)Z (itf)
    POP
   L8
    LINENUMBER 17 L8
   FRAME SAME
    GOTO L5
   L6
    LINENUMBER 19 L6
   FRAME CHOP 1
    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    ALOAD 1
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/Object;)V
   L10
    LINENUMBER 20 L10
    RETURN
   L11
    LOCALVARIABLE platform Ljava/lang/String; L7 L8 3
    LOCALVARIABLE args [Ljava/lang/String; L0 L11 0
    LOCALVARIABLE platformList Ljava/util/List; L1 L11 1
    // signature Ljava/util/List<Ljava/lang/String;>;
    // declaration: platformList extends java.util.List<java.lang.String>
    MAXSTACK = 2
    MAXLOCALS = 4
}

```

