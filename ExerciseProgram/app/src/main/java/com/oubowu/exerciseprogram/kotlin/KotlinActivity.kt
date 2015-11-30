package com.oubowu.exerciseprogram.kotlin

import com.oubowu.exerciseprogram.BaseActivity
import com.oubowu.exerciseprogram.R
import com.socks.library.KLog
import java.io.File
import java.util.*

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/11/30 22:38
 * UpdateUser:
 * UpdateDate:
 */
public class KotlinActivity : BaseActivity() {

    // Kotlin语言的特性：在语句的行尾可以不用加分号(加上也不会错)，声明一个方法需要加上fun关键字，如果函数是重载父类的方法，还必须要加上override关键字，方法的参数是先写形参名后跟冒号再写形参类型。

    override fun provideLayoutId(): Int {
        return R.layout.activity_mvp;
    }

    override fun initView() {

    }

    fun show(list: ArrayList<String>) {
        for (v in list) {
            KLog.e("Show:$v")
        }
    }

    fun show1(list: List<String>) {
        for (v in list) {
            KLog.e("Show:$v")
        }
    }


    /*********************************第一章 1.基础语法*********************************************/

    /*2.定义函数方法*/
    // 方法包含两个Int参数并返回Int类型值
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    // 方法体只有一条语句，并且自动推测返回类型
    fun sum1(a: Int, b: Int) = a + b

    // 如果方法是一个public的，则必须明确写出返回类型(经测试不用也可以)
    public fun sum2(a: Int, b: Int): Int = a + b

    // 返回一个没有意义的值(Unit类似Java中的void，可省略)
    fun printSum(a: Int, b: Int): Unit {
        //        print(a + b)
        KLog.e(a + b)
    }

    // 如果是返回Unit类型，则可以省略(对于public方法也是这样)：
    public fun printSum1(a: Int, b: Int) {
        //        print(a + b)
        KLog.e(a + b)
    }

    /*3.定义变量*/
    //只能赋值一次的变量(类似Java中final修饰的变量)
    val a: Int = 1
    val b = 1 // 系统自动推断变量类型为Int

    fun evaluate() {
        val c: Int // 如果不在声明时初始化则必须提供变量类型
        c = 1 // 明确赋值

        // val相当于final修饰变量，var相当于普通变量定义

        // 可以多次赋值的变量
        var x = 5 // 系统自动推断变量类型为Int
        x += 1

    }

    /*4.使用泛型Array*/
    fun main(args: Array<String>) {
        if (args.size == 0) return
        print("First argument: ${args[0]}")
    }

    /*5.条件语句*/
    fun max(a: Int, b: Int): Int {
        if (a > b)
            return a
        else
            return b
    }

    //或者也可以把if语句作为省略方法体的方法
    fun max1(a: Int, b: Int) = if (a > b) a else b

    /*6.使用nullable值以及空值检测*/
    //引用或函数返回值如果可能为null值，则必须显式标记nullable。
    // (译者注 ：在类型后面跟一个问号表示这个对象可能为空，跟两个感叹号表示这个类型一定不为空)
    fun nullTest(args: Array<String>) {
        if (args.size < 2) {
            print("Two integers expected")
            return
        }
        val x = parseInt(args[0])
        val y = parseInt(args[1])
        //必须做判断，因为x或y有可能为空
        if (x != null && y != null) {
            // x 和 y 在已经检测不为null时，系统会自动将其转换为非空类型
            /*check*/ KLog.e("x * y = ${x * y}")
        } else {
            KLog.e("其中一个为null")
        }
    }

    /**
     * 如果str不能转为Int类型，则返回null
     */
    fun parseInt(str: String): Int? {
        // 类型后面带？说明可以为null
        var i: Int? = 0
        try {
            i = Integer.parseInt(str)
        } catch(e: Exception) {
            i = null
        }
        return i
    }

    /*7.类型检测并自动转换*/
    // is关键字的用法(类似于Java中的instanceof关键字)
    fun getStringLength(obj: Any): Int? {
        if (obj is String) {
            // 做过类型判断以后，obj会被系统自动转换为String类型
            return obj.length
        }
        // 这里的obj仍然是Any类型的引用
        return null
    }

    fun getStringLength1(obj: Any): Int? {
        // 在左侧obj已经被判断为String类型，所以在&&的右侧可以直接将obj当成String类型来使用
        if (obj is String && obj.length > 0) {
            return obj.length
        }
        return null
    }

    /*8.循环的使用*/
    fun loop(array: Array<String>) {
        for (item in array)
            KLog.e(item)
        //或者也可以
        for (i in array.indices)
            KLog.e(array[i])
    }

    fun loop1(array: Array<String>) {
        var i = 0
        while (i < array.size) {
            KLog.e(array[i++])
        }
    }

    /*9.when表达式*/
    // 类似于Java中的switch
    fun cases(obj: Any) {
        when (obj) {
            1 -> KLog.e("数字1")
            "hello" -> KLog.e("字符串Hello")
            is Long -> KLog.e("Long类型数")
            !is String -> KLog.e("不是字符串")
            else -> KLog.e("else类似java中的default")
        }
    }

    /*10.in关键字的使用*/
    // 如果一个数字是在某一个区间内，可以使用in关键字
    fun inUseCase() {
        val x = 5
        val y = 10
        if (x in 1..y - 1) {
            KLog.e("OK")
        }

        //如果x不存在于array中，则输出Out(明显不对，这里是索引)
        val array = arrayOf(4, 5, 1, 2, 3, 7)
        if (x !in 0..array.lastIndex)/*0..array.lastIndex应该换为array*/
            KLog.e("Out")

        //打印1到5
        for (x in 1..5)
            KLog.e(x)

        //遍历集合(类似于Java中的for(String name : names))
        val names = arrayOf("ou", "bo", "mu")
        for (name in names)
            KLog.e(name)

        val text = "ou"
        if (text in names)
            KLog.e("集合中确实存在$text")

    }

    /*********************************第一章 2.常用语法*********************************************/

    // 创建DTO(POJO’s/POCO’s)
    data class Customer(val name: String, val email: String)
    //默认提供了以下功能的Customer类： — getters (and setters in case of var’s) for all properties — equals() — hashCode() — toString() — copy() — component1() , component2() , ..., for all properties

    // 为函数参数设置默认值(a默认为0，b默认为空字符串)
    fun foo(a: Int = 0, b: String = "") {
        // 声明一个常量
        val a = foo()
    }

    // 列表过滤器
    fun filter(list: Array<Int>) {
        val positives = list.filter { x -> x > 0 }
        KLog.e("positives = $positives")
        //或者(it是一个iterator迭代器,内置的,只能是it,不能是其它的名字)
        val positives1 = list.filter { it > 0 }
        KLog.e("positives1 = $positives1")
        // 返回的是List<Int>
        for (k in positives) {
            KLog.e(k)
        }
    }

    // 字符串插入
    //译者注：name是一个String变量 println("他的名字是：$name)

    // 对象检测
    /*when (x) {
        is Foo -> ...
        is Bar -> ...
        else -> ...
    }*/

    // 遍历一个map或list
    fun traverse(map: Map<String, String>) {
        for ((k, v) in map) {
            KLog.e("key:$k value:$v")
        }
    }

    // 使用区间(using ranges)
    //    for (i in 1..100) { ... }
    //    for (x in 2..10) { ... }

    // 只读的map或list
    val list = listOf("a", "b", "c")
    val map = mapOf("a" to 1, "b" to 2, "c" to 3)

    // 扩展函数
    //译者注：为String类添加spaceToCamelCase()方法，String所有子类都可以调用这个方法
    fun String.spaceToCamelCase() {
        KLog.e("拓展了String的函数")
    }

    //    创建一个单元素类(singleton)
    object Resource {
        val name = "Name"
    }

    // 空指针安全操作
    fun nullPointerSaftyOperation() {
        //如果files不为空，则打印files.size，否则什么都不做
        val files = File("Test").listFiles();
        // ?会使其做非空判断
        KLog.e(files?.size)

        //如果files为空了，则打印empty，否则打印files.size
        val files1 = File("Test").listFiles()
        // 后面?:代表为空的操作
        println(files?.size ?: "empty")

        //如果data["email"]为空才执行'?:'后面的语句
        //        val data = ...
        //        val email = data["email"] ?: throw IllegalStateException("Email is missing!")

        //如果不为空才执行某些操作
        //        val data = ...
        //        data?.let {
        //            ... //做某些操作
        //        }

    }

    // 使用when声明返回
    fun transform(color: String): Int {
        return when (color) {
            "Red" -> 0
            "Green" -> 1
            "Blue" -> 2
            else -> throw IllegalArgumentException("Invalid color param value")
        }
    }

    //使用try...catch...块返回
    fun test() {
        val result = try {
            //count()
        } catch (e: ArithmeticException) {
            throw IllegalStateException(e)
        }
        // Working with result
    }

    // 使用if返回值
    fun foo(param: Int): String {
        val result = if (param == 1) {
            "one"
        } else if (param == 2) {
            "two"
        } else {
            "three"
        }
        return result
    }

    // 单表达式函数(单语句函数)(Single-expression functions)
    fun theAnswer() = 42

    //上面的单表达式函数等价于下面的函数
    fun theAnswer1(): Int {
        return 42
    }

    //同样的，可以结合其他常用语法，例如：
    fun transform1(color: String): Int = when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }

    /*********************************第一章 3.编码风格*********************************************/
    /*编码风格(Coding Conventions)

    本页介绍当前kotlin语言编码风格。

    命名风格

    如果有争议，则默认以Java编码风格为准，例如：

    使用驼峰法命名(避免使用下划线命名)
    类型(包括Java中的基本数据类型)使用大写开头
    方法和成员变量(属性)以小写字母开头
    使用4个空格缩进
    公有方法(public functions)应该有文档注释 kotlin语言不具有主键概念(primary concept)，应该避免给成员变量(属性)使用前缀，例如""或"m"或其他类型的标记，
    如果你需要保持一个field备份，使用$前缀，例如：$foo，千万不要创建一个私有变量还给命名为_foo冒号

    对于冒号之前的空格，在分隔子父类或接口的时候，或在分隔实例和类型的时候应该有一个空格，其他地方不应该有空格。

    interface Foo<out T : Any> : Bar {
        fun foo(a: Int): T
    }
    代码单元(Unit)

    如果函数返回值是一个代码单元，则应该忽略返回类型，例如

    fun foo() { // 这里的": Unit"是应该被省略掉的
    }*/


    /*********************************第二章 1.基础数据类型*********************************************/
    // 基本类型
    // 在Kotlin中,所有东西都是对象，所以我们可以调用成员函数和属性的任何变量对象。有些类型是内置的,他们的实现被优化过,
    // 但是用户看起来他们就像普通的类. 本节我们会描述这些类型: numbers, characters, booleans 和 arrays.

    /***********Numbers*************/
    /* Kotlin处理numbers和Java很接近,但是并不完全相同. 例如, 对于numbers没有隐式扩大转换(如java中int可以隐式变为long),在一些情况下文字的使用有所不同.

    对于numbers Kotlin提供了如下的内置类型 (与Java很相近):

    Type	Bitwidth
    Double	64
    Float	32
    Long	64
    Int	32
    Short	16
    Byte	8
    注意在kotlin中 characters 不是 numbers

    字面量

    下面是一些常量的写法:

    十进制: 123
    Longs类型用大写 L 标记: 123L
    十六进制: 0x0F
    二进制: 0b00001011
    注意: 不支持8进制

    Kotlin 同样支持浮点数的常规表示方法:

    Doubles 123.5, 123.5e10
    Floats用 f 或者 F 标记: 123.5f*/

    /*存储方式

    在Java平台数字是物理存储为JVM的原始类型,除非我们需要一个可空的引用（例如int？）或泛型. 后者情况下数字被装箱（指的是赋值的时候把实例复制了一下，不是相同实例）。

    装箱数字不会保存它的实例:

    val a: Int = 10000
    print(a identityEquals a) // Prints 'true'
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    print(boxedA identityEquals anotherBoxedA) // !!!Prints 'false'!!!
    另一方面它们值相等:

    val a: Int = 10000
    print(a == a) // Prints 'true'
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    print(boxedA == anotherBoxedA) // Prints 'true'*/

    /*显示转换

    由于不同的存储方式小的类型并不是大类型的子类型。 如果它们是的话，就会出现下述问题（下面的代码不能通过编译）：

    // Hypothetical code, does not actually compile:
    val a: Int? = 1 // A boxed Int (java.lang.Integer)
    val b: Long? = a // implicit conversion yields a boxed Long (java.lang.Long)
    print(a == b) // Surprise! This prints "false" as Long's equals() check for other part to be Long as well
    假设这样是可以的，这里我们就悄无声息的丢掉了一些数据.

    因此较小的类型不能隐式转换为较大的类型。 因此我们不能声明一个 Byte 类型给一个 Int 变量，在不进行显示转换的情况下。

    val b: Byte = 1 // OK, literals are checked statically
    val i: Int = b // ERROR
    我们可以显示转换的去扩大类型

    val i: Int = b.toInt() // OK: explicitly widened
    每个number类型支持如下的转换:

    toByte(): Byte
    toShort(): Short
    toInt(): Int
    toLong(): Long
    toFloat(): Float
    toDouble(): Double
    toChar(): Char
    失去隐式类型转换，其实并没有带来多少困扰，因为使用字面量的时候是没有代价的，因为字面量的类型是推导出来的； 另一方面，算数运算操作都针对不同类型的参数做好了重载，比如：

    val l = 1.toLong() + 3 // Long + Int => Long*/

    /*运算符

    Kotlin支持标准的算数操作符，并在相应的类上定义为成员函数（但编译器会针对运算进行优化，将函数调用优化成直接的算数操作）。 查看 Operator overloading.

    对于按位操作(bitwise operation)，没有特别的符号来表示，而是直接使用命名函数:

    val x = (1 shl 2) and 0x000FF000
    这是完整的位运算操作 (只能对 Int 或者 Long 使用):

    shl(bits) – signed shift left (Java's <<)
    shr(bits) – signed shift right (Java's >>)
    ushr(bits) – unsigned shift right (Java's >>>)
    and(bits) – bitwise and
    or(bits) – bitwise or
    xor(bits) – bitwise xor
    inv() – bitwise inversion*/

    /********Characters**************/
    /*Characters 用 Char来表示. 像对待numbers那样就行

    fun check(c: Char) {
        if (c == 1) { // ERROR: incompatible types
            // ...
        }
    }
    用单引号表示一个Character，例如: '1', '\n', '\uFF00'. 我们可以调用显示转换把Character转换为Int*/

    fun decimalDigitValue(c: Char): Int {
        if (c !in '0'..'9')
            throw IllegalArgumentException("Out of range")
        return c.toInt() - '0'.toInt() // Explicit conversions to numbers
    }
    /*像numbers, characters是被装箱当使用一个可空的引用.这样实例不会被保存。*/

    /********Booleans**********/
    /*类型Boolean有两个值: true{: .keyword } 和 false{: .keyword }.

    Booleans使用nullable时候Boolean也会被装箱.

    内置对Booelan的操作

    || – 短路或
    && – 短路与*/

    /*********数组************/
    /*数组在Kotlin中使用 Array类来表示, Array类定义了set和get函数(使用时可以用[]，通过符号重载的约定转换), 和size等等一些有用的成员函数:

    class Array<T> private () {
        fun size(): Int
        fun get(index: Int): T
        fun set(index: Int, value: T): Unit

        fun iterator(): Iterator<T>
        // ...
    }
    我们可以使用库函数array()来创建一个包含数值的数组, array(1, 2, 3) 创建了 array [1, 2, 3]. 或者, arrayOfNulls()可以创建一个指定大小，元素都为空的数组。
    或者使用函数来创建一个数组:

    // Creates an Array<String> with values ["0", "1", "4", "9", "16"]
    val asc = Array(5, {i -> (i * i).toString()})
    综上, []操作符代表了成员函数get()和set().

    注意: 与Java不同的是, Kotlin中数组不可变. 这意味着我们不能声明 Array<String>到Array<Any>, 否则可能会产生一个运行时错误(但是你可以使用 Array<out Any>, 查看 Type Projections).

    Kotlin有专门的类来表示原始类型的数组，避免了装箱开销: ByteArray, ShortArray, IntArray 等等. 这些类和Array并没有继承关系,但是它们有同样的方法属性集. 它们也都有相应的工厂方法:

    val x: IntArray = intArray(1, 2, 3)
    x[0] = x[1] + x[2]*/

    /*****字符串*****/
    /*字符串用String表示。字符串是不可变的。
    字符串的原始字符可以使用操作符访问: s[i]. 字符串可以使用for{: .keyword }循环遍历:

    for (c in str) {
        println(c)
    }*/

    /*******字符串字面量********/
    /*Kotlin有两种类型的字符串: 转义字符串可能由转义字符、原生字符串、换行和任意文本.转义字符串很像java的String:

    val s = "Hello, world!\n"
    转义方式采用传统的反斜杠.

    原生字符串使用三个引号(""")包括，内部没有转义，可以包含换行和任何其他文本:

    val text = """
    for (c in "foo")
    print(c)
    """*/

    /***********模板***********/
    /*字符串可以包含模板表达式，即一些小段代码，会求值并把结果合并到字符串中。模板表达式以$符号开始，包含一个简单的名称:

    val i = 10
    val s = "i = $i" // evaluates to "i = 10"
    或者用花括号扩起来，内部可以是任意表达式:

    val s = "abc"
    val str = "$s.length is ${s.length}" // evaluates to "abc.length is 3"*/

    /*********************************第二章 1.包*********************************************/
    /*包
    源文件通常以包声明开头:

    package foo.bar

    fun baz() {}

    class Goo {}

    // ...
    源文件所有的(无论是类或者函数)被包声明覆盖. 所以baz()的全名是foo.bar.baz, Goo的全名是foo.bar.Goo.

    如果没有明确声明文件属于"default"且包没有名称.

    导入

    除了模块定义的默认导入之外，每个源文件也可以声明自己的导入。 导入语句的语法定义描述在grammar.

    可以导入一个单独的名称，如.

    import foo.Bar // Bar is now accessible without qualification
    也可以导入一个作用域下的所有内容（包、类、对象等）:

    import foo.* // everything in 'foo' becomes accessible
    如果出现名称冲突，可以使用 as{: .keyword } as关键字来重命名导入的名称：

    import foo.Bar // Bar is accessible
    import bar.Bar as bBar // bBar stands for 'bar.Bar'
    可见性和包嵌套

    如果顶层声明是private{: .keyword }, 它将是私有的(查看 Visibility Modifiers). 尽管Kotlin中可以包嵌套, 如 包foo.bar 是foo的一个成员,但是一些private{: .keyword } 仅仅可以被它的子包所见.

    注意外部包成员不是默认引入的,例如，在foo.bar包的文件中我们不能在不引入的情况下访问foo.*/



    override fun initData() {

        //        val text = """ \n """
        //        KLog.e(text)

        // Creates an Array<String> with values ["0", "1", "4", "9", "16"]
        //        val asc = Array(5, {i -> (i * i).toString()})

        //        KLog.e(decimalDigitValue('9'))

        /*// 测试结果>>与>>>值一毛一样，不解
        var i = 0b11010011
        // >> 将一个数的各二进制位右移两位,移到右端的低位被舍弃,最高位则移入原来高位的值.
        // >>> 将一个数的各二进制位无符号右移若干位,与运算符>>相同,移出的低位被舍弃,但不同的是最高位补0
        // i>>2位, 11010011 有符号右移 => 11110100
        KLog.e(i shr 2)

        var j = 0b11010011
        // i>>>2位, 11010011 无符号右移 => 00110100
        KLog.e(j ushr 2)*/

        // 我们可以显示转换的去扩大类型
        //        val b: Byte = 1 // OK, literals are checked statically
        //        val i: Int = b.toInt() // OK: explicitly widened

        //        KLog.e("Green=${transform("Green")}")

        //        KLog.e("foo=${foo(2)}")

        //        "Convert this to camelcase".spaceToCamelCase()

        //        show1(list)

        //        traverse(mapOf(Pair("Mexi", "Basa"), Pair("C罗", "RM")))

        //        KLog.e(Customer("oubowu", "oubowu@qq.com").toString())

        //        filter(arrayOf(-1, -2, 1, 2, 3))

        /*val list = arrayListOf<String>()
        list.add("Kobe")
        list.add("Pole")
        list.add("Curry")
        show(list)

        KLog.e("sum2= " + sum2(1, 8))
        printSum(3, 4)*/

        //        nullTest(arrayOf("k", "2"))

        //        val s = "ni hao ma"
        //        KLog.e("\"$s\" length is ${getStringLength(s)}")

        //        val s1 = -1
        //        KLog.e("\"$s1\" length is ${getStringLength(s1)}")

        //        loop(arrayOf("cao", "ni", "ma"))
        //        loop1(arrayOf("qu", "ni", "mei"))

        //        cases(1)
        //        cases("hello")
        //        cases(1L)
        //        cases(2.00f)

        //        inUseCase();

    }

}