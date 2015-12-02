package com.oubowu.exerciseprogram.kotlin

import com.oubowu.exerciseprogram.BaseActivity
import com.oubowu.exerciseprogram.R
import com.socks.library.KLog
import java.io.File
import java.util.*
import kotlin.properties.Delegates

/**
 * ClassName:
 * Author:oubowu
 * Fuction:
 * CreateDate:2015/11/30 22:38
 * UpdateUser:
 * UpdateDate:
 */
public open class KotlinActivity : BaseActivity() {

    // 声明public,随处可见
    public var publicVariable: Int = 5
        private set
    // 不指定任何可见性修饰符，那么默认情况下使用internal修饰，这意味着你们将声明在同一个模块(就是这一个Module)中可见;
    internal val internalVariable = 6
    // 声明private，只会是这个包及其子包内可见的，并且只在相同的模块;
    private val privateVariable = 10
    // (只适用于类/接口成员)和"private"一样,但也在子类可见;
    protected val protectedVariable = 10


    // Kotlin语言的特性：在语句的行尾可以不用加分号(加上也不会错)，声明一个方法需要加上fun关键字，
    // 如果函数是重载父类的方法，还必须要加上override关键字，方法的参数是先写形参名后跟冒号再写形参类型。
    override fun provideLayoutId(): Int {
        return R.layout.activity_mvp;
    }

    override fun initView() {
        toast("LoginActivity那里拓展了Activity，这里也可以用到，十分方便")
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

    /*********************************第二章 2.控制流*********************************************/
    /********If表达式********/
    // 在Kotlin中, if{: .keyword }是一个表达式,它会返回一个值. 因此就不需要三元运算符 (如 ? 三元表达式), 因为使用 if{: .keyword } 就可以了。
    /*
    // Traditional usage
    var max = a
    if (a < b)
    max = b

    // With else
    var max: Int
    if (a > b)
    max = a
    else
    max = b

    // As expression
    val max = if (a > b) a else b

    if{: .keyword }的分支可以是代码段, 最后一行的表达式作为段的返回值:

    val max = if (a > b) {
        print("Choose a")
        a
    }
    else {
        print("Choose b")
        b
    }
    当if{: .keyword }仅仅有一个分支, 或者其中一个分支的返回结果Unit, 它的类型Unit.*/

    /*********When表达式*************/
    /*when{: .keyword } 替代了c语言风格的switch操作符. 最简单的形式如下：

    when (x) {
        1 -> print("x == 1")
        2 -> print("x == 2")
        else -> { // Note the block
            print("x is neither 1 nor 2")
        }
    }
    when{: .keyword } 将它的参数和所有的分支条件进行比较，直到某个分支满足条件。 when{: .keyword }既可以被当做表达式使用也可以被当做语句使用。
    如果它被当做表达式, 符合条件的分支的值就是整个表达式的值，如果当做语句使用，则忽略单个分支的值。
    (就像if{: .keyword },每一个分支可以是一个代码块,它的值是最后的表达式的值.)

    else{: .keyword } 分支将被执行如果其他分支都不满足条件。 如果 when{: .keyword } 作为一个表达式被使用,
    else{: .keyword } 分支是必须的，除非编译器能够检测出所有的可能情况都已经覆盖了。

    如果很多分支需要用相同的方式处理，则可以把多个分支条件放在一起, 用,逗号分隔:

    when (x) {
        0, 1 -> print("x == 0 or x == 1")
        else -> print("otherwise")
    }
    我们可以在判断分支条件的地方使用任何表达式，而不仅仅是常量(和switch不同)：

    when (x) {
        parseInt(s) -> print("s encodes x")
        else -> print("s does not encode x")
    }
    我们也可以检查一个值 in{: .keyword } 或者 !in{: .keyword } 一个 范围 或者集合:

    when (x) {
        in 1..10 -> print("x is in the range")
        in validNumbers -> print("x is valid")
        !in 10..20 -> print("x is outside the range")
        else -> print("none of the above")
    }
    另一种用法是可以检查一个值is{: .keyword }或者!is{: .keyword }某种特定类型.注意,由于smart casts, 你可以访问该类型的方法和属性而不用额外的检查。

    val hasPrefix = when(x) {
        is String -> x.startsWith("prefix")
        else -> false
    }
    when{: .keyword } 也可以用来替代if{: .keyword }-else{: .keyword } if{: .keyword }链. 如果不提供参数，所有的分支条件都是简单的布尔值，
    而当一个分支的条件返回true时，则调用该分支：

    when {
        x.isOdd() -> print("x is odd")
        x.isEven() -> print("x is even")
        else -> print("x is funny")
    }*/

    /*******For循环***********/

    /*for{: .keyword } 循环可以对任何提供迭代器(iterator)的集合进行遍历，语法如下:

    for (item in collection)
    print(item)
    循环体可以是一个代码块.

    for (item: Int in ints) {
        // ...
    }
    像上面提到的一样, for{: .keyword }可以循环遍历任何提供了迭代器的集合。例如：

    有一个成员函数或者扩展函数iterator(),它返回一个类型
    有一个成员函数或者扩展函数next(),并且
    有一个成员函数或者扩展函数hasNext()返回 Boolean.
    如果你想要遍历一个数组或者一个list，你可以这么做:

    for (i in array.indices)
    print(array[i])
    注意这种“遍历一个范围”的函数会被编译器优化，不会产生额外的对象。*/

    /********While循环************/
    /*while{: .keyword } 和 do{: .keyword }..while{: .keyword } 的使用方法和其他语言一致

    while (x > 0) {
        x--
    }

    do {
        val y = retrieveData()
    } while (y != null) // y is visible here!*/

    /************Break和continue在循环中的使用********/
    //    在循环中Kotlin支持传统的break{: .keyword }和continue{: .keyword }操作符.

    /*********************************第二章 3.返回与跳转*********************************************/
    /*返回和跳转
    Kotlin 有三种跳出结构

    return{: .keyword }.默认情况下，从最近的一个封闭的方法或者 方法表达式跳出.

    break{: .keyword }.终止最近的封闭循环

    continue{: .keyword }.直接进入循环体的下次循环

    中断和继续标签

    在Kotlin中任何表达式都可以用label{: .keyword } （标签）来标记。
    label的格式是被'@'标识符标记，例如：abc@, fooBar@都是有效的label（参见语法）

    你可以在一个方法前面放一个label。

    loop@ for (i in 1..100) {
        // ...
    }
    现在，我们可以将label与 break{: .keyword } 或者continue{: .keyword }一起使用：

    loop@ for (i in 1..100) {
        for (j in 1..100) {
            if (...)
            break@loop
        }
    }
    break执行后将跳转到标记处。

    continue{: .keyword }将进入循环体的下次循环

    返回标签

    在Kotlin里，函数字面量、局部函数和对象表达式等函数都可以被嵌套在一起 适当的返回方式允许我们从外部方法返回值

    带标签的return，最重要的一个用途，就是让我们可以从函数字面量中返回。

    fun foo() {
        ints.forEach {
            if (it == 0) return
            print(it)
        }
    }
    这个 return{: .keyword }表达式从最近的封闭的方法中返回，例如‘foo’。

    (注意，非全局的返回只支持内部方法，参见内联方法.) 如果我们只是需要跳出内部方法，我们必须标记它并且返回这个标签

    fun foo() {
        ints.forEach lit@ {
            if (it == 0) return@lit
            print(it)
        }
    }
    现在只是从内部方法返回。有时候用匿名的标签将会更加方便 像这样和方法同名的标签是可以的

    fun foo() {
        ints.forEach {
            if (it == 0) return@forEach
            print(it)
        }
    }
    通常，我们用一个方法表达式替代内部匿名方法。在方法内部声明一个return{: .keyword }将从其内部返回

    fun foo() {
        ints.forEach(fun(value: Int) {
            if (value == 0) return
            print(value)
        })
    }
    当要返回一个值得时候，推荐使用描述性的返回，例如：

    return@a 1
    意思是“返回被标记为‘@a’值是‘1’的标签，而不是像‘（@a 1）’的一个标签表达式”

    被命名的方法自动被定义成为标签

    fun outer() {
        fun inner() {
            return@outer // the label @outer was defined automatically
        }
    }*/

    fun looper(i: Int = 10) {
        loop@ for (i in 1..100) {
            for (j in 1..100) {
                if (j == 10)
                // break执行后将跳转到标记处
                    break@loop
            }
        }
    }

    fun foo(ints: Array<Int>) {
        ints.forEach {
            if (it == 0) return@forEach
            KLog.e(it)
        }
    }

    /*********************************第三章 1.类与继承*********************************************/
    /*********类*******/

    /*类声明Kotlin使用关键字*class *{:.keyword}

    class Invoice {
    }
    这个类声明被花括号包围，包括类名、类头(指定其类型参数,主构造函数等)和这个类的主干。类头和主干都是可选的； 如果这个类没有主干，花括号可以被省略。

    class Empty*/

    /*******构造******/

    /*在Kotlin中的类可以有主构造函数和一个或多个二级构造函数。主构造函数是类头的一部分:它跟在这个类名后面（和可选的类型参数）

    class Person constructor(firstName: String) {
    }
    如果这个主构造函数没有任何注解或者可见的修饰符，这个constructor{: .keyword }关键字可以被省略

    class Person(firstName: String) {
    }
    这个主构造函数不能包含任何的代码。初始化的代码可以被放置在initializer blocks（初始的模块），以init为前缀作为关键字{:.keyword}

    class Customer(name: String) {
        init {
            logger.info("Customer initialized with value ${name}")
        }
    }
    请注意，主构造的参数可以在初始化模块中使用。它们也可以在类体内声明初始化的属性：

    class Customer(name: String) {
        val customerKey = name.toUpperCase()
    }
    事实上，声明属性和初始化主构造函数,Kotlin有简洁的语法:

    class Person(val firstName: String, val lastName: String, var age: Int) {
        // ...
    }
    与普通属性一样,主构造函数中声明的属性可以是可变的或者是只读的

    If the constructor has annotations or visibility modifiers, the constructor{: .keyword } keyword is required, and the modifiers go before it: 如果构造函数有注解或可见性修饰符，这个constructor{: .keyword }需要被关键字修饰。

    class Customer public inject constructor(name: String) { ... }
    更多请查看Visibility Modifiers*/

    /*********扩展构造函数******/

    /*类也可以拥有被称为"二级构造函数"(为了实现Kotlin向Java一样拥有多个构造函数)，通常被加上前缀"constructor"

    class Person {
        constructor(parent: Person) {
            parent.children.add(this)
        }
    }
    如果类有一个主构造函数,每个二级构造函数需要委托给主构造函数,直接或间接地通过另一个二级函数。 委托到另一个使用同一个类的构造函数用this{: .keyword }关键字

    class Person(val name: String) {
        constructor(name: String, parent: Person) : this(name) {
            parent.children.add(this)
        }
    }
    如果一个非抽象类没有声明任何构造函数（原发性或继发性），这将有一个生成的主构造函数不带参数。构造函数的可见性是public。如果你不希望你的类有一个公共构造函数，你需要声明与非缺省可见一个空的主构造函数：

    class DontCreateMe private constructor () {
    }
    注意在JVM上，如果所有的主构造函数的参数有默认值，编译器会产生一个额外的参数的构造函数，将使用默认值。 这使得更易于使用kotlin与通过参数构造函数创建类的实例，如使用Jackson或JPA库的时候。

    class Customer(val customerName: String = "")
    {:.info}*/

    class Person constructor(val firstName: String = "Kobe") {
        constructor(firstName: String, email: String) : this(firstName) {

        }

        fun getName(): String {
            return firstName
        }

        fun getName1() = firstName
    }

    /*********创建类的实例**********/
    /*要创建一个类的实例，我们调用构造函数，就好像它是普通的函数：

    val invoice = Invoice()

    val customer = Customer("Joe Smith")
    注意Kotlin不能有“new”关键字

    类成员

    类可以包括

    构造和初始化模块
    函数
    属性
    匿名和内部类
    对象声明
    继承

    在Kotlin所有的类中都有一个共同的父类Any，这是一个默认的父类且没有父类型声明：

    class Example // Implicitly inherits from Any
    Any不属于java.lang.Object;特别是，它并没有任何其他任何成员，甚至连equals()，hashCode()和toString()都没有。

    请参阅Java的互操作性更多的细节部分。

    要声明一个明确的父类，我们把类型放到类头冒号之后：

    open class Base(p: Int)

    class Derived(p: Int) : Base(p)
    如上所见，父类可以（并且必须）在声明继承的地方，用原始构造函数初始化。

    如果类没有主构造，那么每个次级构造函数初始化基本类型 使用super{：.keyword}关键字，或委托给另一个构造函数做到这一点。
    注意，在这种情况下，不同的二级构造函数可以调用基类型的不同的构造：

    class MyView : View {
        constructor(ctx: Context) : super(ctx) {
        }

        constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
        }
    }
    父类上的open{：.keyword}标注可以理解为Java中final{：.keyword}的反面，它允许其他他类 从这个类中继承。默认情况下，在Kotlin所有的类都是final，
    对应于 Effective Java 书中的17条：设计并显示标注继承，否则就禁止它。*/

    // 显示标注继承open，否则就禁止它
    open class Pop {
        // 方法也是要显示的标明才能给子类重写
        open fun play() {
        }
    }

    class KPop : Pop() {
        override fun play() {
        }
    }

    /********覆盖成员***********/
    /*我们之前提到过，Kotlin力求清晰显式。不像Java中，Kotlin需要明确的 标注覆盖的成员（我们称之为open）和重写的函数。
    （继承父类并覆盖父类函数时，Kotlin要求父类必须有open标注，被覆盖的函数必须有open标注，并且子类的函数必须加override标注。）：

    open class Base {
        open fun v() {}
        fun nv() {}
    }
    class Derived() : Base() {
        override fun v() {}
    }
    Derived.v()函数上必须加上override标注。如果没写，编译器将会报错。 如果父类的这个函数没有标注open，则子类中不允许定义同名函数，
    不论加不加override。 在一个final类中（即没有声明open的类），函数上也不允许加open标注。

    成员标记为override{：.keyword}的本身是开放的，也就是说，它可以在子类中重写。如果你想禁止重写的，使用final{：.keyword}关键字：

    open class AnotherDerived() : Base() {
        final override fun v() {}
    }
    等等!!这样我怎么hack我的库？

    我们这样设计继承和覆盖的方式(类和成员默认final)，会让人很难继承第三方的类，因此很难进行hack。

    我们认为这不是一个劣势，原因如下：

    最佳实践已经表明不应该使用这些hacks
    其他的有类似机制的语言(C++, C#)已经证明是成功的
    如果人们实在想hack，仍然有办法：比如某些情况下可以使用Java进行hack，再用Kotlin调用；或者使用面向切面的框架(Aspect)。（请参阅Java的互操作)*/

    /********重写的规则*******/

    // 在Kotlin中，实现继承的调用通过以下规则： 如果一个类继承父类成员的多种实现方法，可以直接在子类中引用，
    // 它必须重写这个成员，并提供其自己的实现（当然也可以使用父类的）。 为了表示从中继承的实现而采取的父类型，
    // 我们使用super{：.keyword}在尖括号，如规范的父名super<Base>：

    open class A {
        open fun f() {
            KLog.e("A")
        }

        fun a() {
            KLog.e("a")
        }
    }

    // 接口函数默认为open
    interface B {
        fun f() {
            KLog.e("B")
        } // interface members are 'open' by default

        fun b() {
            KLog.e("b")
        }
    }

    class C() : A(), B {
        // The compiler requires f() to be overridden:
        override fun f() {
            super<A>.f() // call to A.f()
            super<B>.f() // call to B.f()
        }
    }
    // 类C同时继承A和B是可以的，而且我们在调用a()和b()函数时没有任何问题，因为他们在C的基类中只有一个实现。
    // 但是f()函数则在A,B中都有实现，所以我们必须在C中覆盖f()，并且提供我们的实现以消除歧义。

    /*******抽象类********/
    //    类和其中的某些实现可以声明为abstract{：.keyword}。 抽象成员在本类中可以不用实现。。 因此，当一些子类继承一个抽象的成员，它并不算是一个实现：
    /*abstract class A1 {
        abstract fun f()
    }

    interface B1 {
        open fun f() {
            print("B")
        }
    }

    class C1() : A1(), B1 {
        // We are not required to override f()
    }*/
    // 需要注意的是，我们并不需要标注一个抽象类或者函数为open - 因为这不言而喻。我们可以重写一个open非抽象成员使之为抽象的。
    open class Base {
        open fun f() {
        }
    }

    abstract class Derived : Base() {
        override abstract fun f()
    }

    /*同伴对象*/
    //    在Kotlin中，不像Java或C＃，类没有静态方法。在大多数情况下，它建议简单地使用包级函数。
    //    如果你需要写一个可以调用的函数，而不依赖一个类的实例，但需要访问的内部一个类（例如，一个工厂方法），
    //    你可以写为[对象声明]（object_declarations.html）中的一员里面的那个类。
    //    更具体地讲，如果你声明一个同伴对象在你的的类中， 你就可以在Java/ C＃中调用与它的成员方法相同的语法的静态方法，只使用类名作为一个修饰语。

    /*********************************第三章 2.属性和字段*********************************************/
    /********声明属性*******/
    /*Kotlin的属性. 这些声明是可变的,用关键字var{: .keyword }或者使用只读关键字val{: .keyword }.

    public class Address {
        public var name: String = ...
        public var street: String = ...
        public var city: String = ...
        public var state: String? = ...
        public var zip: String = ...
    }
    要使用一个属性，只需要使用名称引用即可，就相当于Java中的公共字段：

    fun copyAddress(address: Address): Address {
        val result = Address() // there's no 'new' keyword in Kotlin
        result.name = address.name // accessors are called
        result.street = address.street
        // ...
        return result
    }*/

    /*********getters和setters*******/
    /* 声明一个属性的完整语法

     var <propertyName>: <PropertyType> [= <property_initializer>]
     <getter>
     <setter>
     上面的定义中，初始器(initializer)、getter和setter都是可选的。 属性类型(PropertyType)如果可以从初始器或者父类中推导出来，也可以省略。

     例如:

     var allByDefault: Int? // error: explicit initializer required, default getter and setter implied
     var initialized = 1 // has type Int, default getter and setter
     注意公有的API(即public和protected)的属性，类型是不做推导的。 这么设计是为了防止改变初始化器时不小心改变了公有API。比如：

     public val example = 1 // error: a public property must have a type specified explicitly
     一个只读属性的语法和一个可变的语法有两方面的不同：1·只读属性的用val开始代替var 2·只读属性不许setter

     val simple: Int? // has type Int, default getter, must be initialized in constructor
     val inferredType = 1 // has type Int and a default getter
     我们可以编写自定义的访问器,非常像普通函数,对内部属性声明。这里有一个定义的getter的例子:

     val isEmpty: Boolean
         get() = this.size == 0
     一个定义setter的例子:

     var stringRepresentation: String
         get() = this.toString()
         set(value) {
             setDataFromString(value) // parses the string and assigns values to other properties
         }
     按照惯例,setter参数的名称是“value”,但是如果你喜欢你可以选择一个不同的名称。

     如果你需要改变一个访问器或注释的可见性,但是不需要改变默认的实现, 您可以定义访问器而不定义它的实例:

     var setterVisibility: String = "abc" // Initializer required, not a nullable type
         private set // the setter is private and has the default implementation

     var setterWithAnnotation: Any?
         @Inject set // annotate the setter with Inject*/

    // var和val在定义的时候必须初始化
    // 类型为Int，默认getter和setter
    var initialized = 1
    // 公有的API(即public和protected)的属性，类型是不做推导的（将测试没问题啊）
    public val example = 1
    // 类型为Int，默认getter，因为val是只读的
    val inferredType = 1
    // 自定义getter
    val isBelongBaseActivity: Boolean get() = this is BaseActivity
    // 自定义getter和setter
    var stringRepresentation: String
        get() = this.toString()
        set(value) {
            // 在对其赋值的时候可以把该值传递给其他地方
            setDataFromString(value)
        }

    private fun setDataFromString(value: String) {
        KLog.e(value)
    }

    /********实际字段********/
    /*在Kotlin不能有字段。然而,有时有必要有使用一个字段在使用定制的访问器的时候。对于这些目的,Kotlin提供 自动支持,在属性名后面使用* $ *符号。

    (经测试压根编译不过去)
    var counter = 0 // the initializer value is written directly to the backing field
        set(value) {
            if (value >= 0)
            $counter = value
        }
    上面的$counter字段就可以在counter属性的访问器实现里读和写。并且只能在构造函数里赋值。在其他地方，都不能使用或访问$counter

    编译器会查看访问器的内部， 如果他们使用了实际字段（或者访问器使用默认实现），那么将会生成一个实际字段，否则不会生成。

    例如，下面的情况下， 就没有实际字段：

    val isEmpty: Boolean
        get() = this.size == 0*/

    /********支持属性********/
    /*如果你的需求不符合这套“隐式的实际字段“方案，那么总可以使用“后背支持属性”(backing property)的方法：*/
    private var _table: Map<String, Int>? = null //?标示可以为null
    public val table: Map<String, Int>
        get() {
            if (_table == null)
                _table = HashMap() // Type parameters are inferred
            return _table ?: throw AssertionError("Set to null by another thread")
        }
    /*从各种角度看，这和在Java中定义Bean属性的方式一样。因为访问私有的属性的getter和setter函数，会被编译器优化成直接反问其实际字段。*/

    /***********重写属性***********/
    //查看 Overriding Members

    /********委托属性********/
    //    从支持域最常见类型的属性只读(写入)。 另一方面,使用自定义getter和setter属性可以实现任何方法行为。 介于两者之间,有一些常见的模式属性是如何工作的。
    //    一个例子:lazy values,从映射读取关键字,访问一个数据库,访问通知侦听器,等等。
    //    像常见的行为可以从函数库调用像delegated properties。 更多信息在delegated-properties.html。

    /*********************************第三章 3.接口*********************************************/
    /*Kotlin 的接口很像 java 8。它们都可以包含抽象方法，以及方法的实现。和抽象类不同的是，接口不能保存状态。可以有属性但必须是抽象的。

    接口是通过关键字 interface 来定义的：

    // 接口中的成员变量和函数(有函数体的,没有函数体的方法是抽象的)默认是open的，因此可以重写
    interface MyInterface {
        fun bar()
        fun foo() {
            //函数体是可选的
        }
    }
    接口的实现

    一个类或对象可以实现一个或多个接口

    class Child : MyInterface {
        fun bar () {
            //函数体
        }
    }
    接口中的属性

    因为接口没有状态，所以中只允许有无状态的属性。

    interface MyInterface {
        val property: Int //抽象属性
        fun foo() {
            print(property)
        }
    }

    class Child : MyInterface {
        override val property: Int = 29
    }
    解决重写冲突

    当我们在父类中声明了许多类型，有可能出现一个方法的多种实现。比如：

    interface A {
        fun foo() { print("A") }
        fun bar()
    }

    interface B {
        fun foo() { print("B") }
        fun bar() { print("bar") }
    }

    //
    class C : A {
        override fun bar() { print("bar") }
    }

    class D : A, B {
        override fun foo() {
            super<A>.foo()
            super<B>.foo()
        }
        // 这里不重写bar()会报错，因为bar接口在A中没有实现方法故它是抽象的，不知道这里为什么不重写
    }
    A B 接口都有声明了 foo() bar() 函数。它们都实现了 foo() 方法，但只有 B 实现了 bar() ,bar() 在 A 中并没有声明它是抽象的，
    这是因为在接口中如果函数没有函数体，那么默认是抽像的。现在，如果我们从 A 中派生一个 C 实体类，显然我们需要重写 bar() ，并实现它。
    而我们从 A 和 B 派生一个 D ，我们不用重写 bar() 方法，因为我们的一个继承中有一个已经实现了它。
    (这句话不对啊！!!!!!!!!!!!!!! 经测试明明只要其中一个没有实现bar方法都要重写)
    但我们继承了俩个 foo() 的实现，因此编译器不知道应该选哪个，并强制我们重写 foo() 并且明确指出我们想怎么实现。*/

    /*********************************第三章 4.范围修饰符(例如public)*********************************************/
    /*类，对象，接口，构造方法，和它们的setter方法都可以用visibility modifiers来做修饰。(getter一直与属性有着相同的可见性.)

    在Kotlin中有以下四个可见性修饰符:

    private --- 只有在声明的范围及其方法可见(在同一模块);

    protected --- (只适用于类/接口成员)和"private"一样,但也在子类可见;

    internal --- (在默认情况下使用)在同一个模块中可见(如果声明范围的所有者是可见的);

    public --- 随处可见(如果声明范围的所有者是可见的).

    注意: 函数 with expression bodies 所有的属性声明public必须始终显式指定返回类型。 这是必需的，这样我们就不会随意改变一个类型,仅通过改变实现公共API的一部分。

    public val foo: Int = 5    // explicit return type required
    public fun bar(): Int = 5  // explicit return type required
    public fun bar() {}        // block body: return type is Unit and can't be changed accidentally, so not required
    下面将解释不同类型的声明范围。

    包名

    函数，属性和类，对象和接口可以在顶层声明，即直接在包内：

    // file name: example.kt
    package foo

    fun baz() {}
    class Bar {}
    如果你不指定任何可见性修饰符，那么默认情况下使用internal修饰，这意味着你们将声明在同一个模块中可见;

    如果你声明private，只会是这个包及其子包内可见的，并且只在相同的模块;

    如果你声明public,随处可见。

    protected不适用于顶层声明。

    例子:

    // file name: example.kt
    package foo

    private fun foo() {} // visible inside this package and subpackaged

    public var bar: Int = 5 // property is visible everywhere
        private set         // setter is visible only in this package and subpackages

    internal val baz = 6    // visible inside the same module, the modifier can be omitted
    类和接口

    当一个类中声明：

    private 意味着这个类只在内部可见(包含所有成员).

    protected--- 和private一样+在子类可见。

    internal --- 任何客户端 inside this module 谁看到声明类，其internal成员在里面;

    public --- 任何客户端看到声明类看到其public成员。

    注意 对于Java用户:外部类不能访问Kotlin内部类的private成员。

    例子:

    open class Outer {
        private val a = 1
        protected val b = 2
        val c = 3 // internal by default
        public val d: Int = 4 // return type required

        protected class Nested {
            public val e: Int = 5
        }
    }

    class Subclass : Outer() {
        // a is not visible
        // b, c and d are visible
        // Nested and e are visible
    }

    class Unrelated(o: Outer) {
        // o.a, o.b are not visible
        // o.c and o.d are visible (same module)
        // Outer.Nested is not visible, and Nested::e is not visible either
    }
    构造函数

    指定一个类的可见性的主构造函数,使用以下语法(注意你需要添加一个显示构造函数{:.keyword} keyword)：

    class C private constructor(a: Int) { ... }
    这里的构造函数是私有的。不像其他的声明，在默认情况下，所有构造函数是public，这实际上等于他们是随处可见，其中的类是可见(即内部类的构造函数是唯一可见在同一模块内).

    局部声明

    局部变量，函数和类不能有可见性修饰符。*/

    /*********************************第三章 5.拓展*********************************************/
    /*Kotlin 与 C# Gosu 类似，提供了不用从父类继承，或者使用像装饰模式这样的设计模式来给某个类进行扩展。
    这是通过叫扩展的特殊声明来达到的。现在， Kotlin 支持扩展函数和属性。*/

    /***********扩展函数***********/
    /*声明一个扩展函数，我们需要添加一个接收者类型的的前缀。下面是给MutableList<Int>添加一个 swap 函数的例子：

    (MutableList是Kotlin中已有的接口)
    fun MutableList<Int>.swap(x: Int, y: Int) {
        val temp = this[x] // this 对应 list
        this[x] = this[y]
        this[y] = tmp
    }
    在扩展函数中的 this 关键字对应接收者对象。现在我们可以在任何 MutableList<Int> 中使用这个函数了：

    fun <T> MutableList<T>.swap(x: Int, y: Int) {
        val tmp = this[x] // 'this' corresponds to the list
        this[x] = this[y]
        this[y] = tmp
    }*/

    /**************可空的接受者***********/
    /* 值得注意的是扩展可以定义一个可空的接受者。这样的扩展可以算作对象的变量，即使它是空的，你可以在函数体内检查 this == null 。
     这样你就可以在 Kotlin 中不进行空检查就可以调用 toString() 方法：这样的检查是在扩展函数中做的。

     fun Any?.toString(): String {
         if (this == null) return "null"
         return toString()
     }*/

    /***************扩展属性*************/
    /*和函数类似， Kotlin 也支持属性扩展：

    val <T> List<T>.lastIndex:  Int
        get() = size-1
    注意，扩展并不是真正给类添加了成员，没有比给扩展添加备用字段更有效的办法了／这就是为什么初始化函数是不允许扩展属性。
    它们只能通过明确提供 getter setter 来作用。

    例子：

    val Foo.bar = 1 //error: initializers are not allowed for extension properties*/

    /************伴随对象扩展**************/
    /*如果一个对象定义了伴随对象，你也可以给伴随对象添加扩展函数或扩展属性：

    class MyClass {
        companion object {}
    }

    fun Myclass.Companion,foo() {

    }
    和普通伴随对象的成员一样，它们可以只用类的名字就调用：

    MyClass.foo()*/

    /*************扩展的范围**************/
    /*大多数时候我们在 top level 定义扩展，就在包下面直接定义：

    package foo.bar
    fun Baz.goo() { ... }
    在声明的包外面使用这样的扩展，我们需要在 import 时导入：

    package com.example,usage

    import foo.bar.goo//导入所有名字叫 "goo" 的扩展

    import foo.bar.*

    fun usage(baz: Baz) {
        baz.goo()
    }*/

    /**************动机（Motivation）***************/
    /*在 java 中，我经常给类命名为 "*Utils": FileUtils,StringUtils等等。很有名的 java.util.Collections 也是这样的，
    这些 Utils 类不方便的地方就是我们用起来总是像下面这样：

    //java
    Collections.swap(list, Collections.binarySearch(list, Collections.max(otherList)), Collections.max(list))
    这些类名总是通过一样的方式得到的。我我们可以使用静态导入并这样使用：

    swap(list, binarySearch(list, max(otherList)), max(list))
    这样就好很多了，但这样我们就只能从 IDE 自动完成代码那里获得很少或得不到帮助。如果我们可以像下面这样那门就好多了

    list.swap(list.binarySearch(otherList.max()), list.max())
    但我们又不想在 List 类中实现所有可能的方法。这就是扩展带来的好处。*/

    /*********************************第三章 6.数据类(javabean)************************************/
    /***********数据类（javabean）************/
    /*我们经常创建一些只是处理数据的类。在这些类里的功能经常是衍生自他们所持有的数据。在Kotlin中，这样的类可以被称为data：
    data class User(val name: String, val age: Int)
    这被叫做一个 数据类。编译器自动从在主构造函数定义的全部特性中得到以下成员：
    equals()/hashCode(),
    toString() 格式是 "User(name=John, age=42)",
    componentN() functions 对应按声明顺序出现的所有属性,
    copy() 方法 .
    如果有某个函数被明确地定义在类里或者被继承，编译器就不会生成这个函数。
    NOTE如果一个构造参数没有val或者var在前面，它将不会被包括进这些成员里;也不会在类里声明成属性或者继承自父类
    在JVM中，如果生成的类需要含有一个无参的构造函数，则所有的属性必须有默认值。
    data class User(val name: String = "", val age: Int = 0)*/

    /**********复制************/
    /*在很多情况下，我们我们需要对一些属性做修改而其他的不变。这就是copy()这个方法的来源。对于上文的User类，应该是这么实现这个方法的
    fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
    也可以这么写
    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)*/

    data class User(val name: String, val age: Int)

    // 报错
    //    fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
    val jack = User(name = "Jack", age = 1)
    // 使用copy可以bean的修改某些属性
    val olderJack = jack.copy(age = 2)

    /**********数据类和多重声明**********/
    /*成员方法用于使数据类可以多声明：
    val jane = User("Jane", 35)
    val (name, age) = jane
    println("$name, $age years of age") // prints "Jane, 35 years of age"*/

    /********标准数据类*********/
    /*在标准库提供了Pair和Triple。在很多情况下，即使命名数据类是一个更好的设计选择，因为这能让代码可读性更强。*/

    /*********************************第三章 7.泛型************************************/
    /*像 java 一样，Kotlin 中可以所有类型参数：

    class Box<T>(t: T){
        var value = t
    }
    通常来说，创建一个这样类的实例，我们需要提供类型参数：

    val box: Box<Int> = Box<Int>(1)
    但如果类型有可能是推断的，比如来自构造函数的参数或者通过其它的一些方式，一个可以忽略类型的参数：

    val box = Box(1)//1是 Int 型，因此编译器会推导出我们调用的是 Box*/
    class Box<T>(t: T) {
        var value = t
    }

    val box = Box(1)

    /*******变动*********/
    /*java 类型系统最狡猾的一部分就是通配符类型。但 kotlin 没有，代替它的是俩中其它的东西：
    声明变化和类型预测(declaration-site variance and type projections)。

    首先，我们想想为什么 java 需要这些神秘的通配符。这个问题在Effective Java,条目18中是这样解释的：使用界限通配符增加 API 的灵活性。
    首先 java 中的泛型是不变的，这就意味着 List 不是 List 的子类型。为什么呢，如果 List 不是不变的，就会引发下面的问题：

    // Java
    List<String> strs = new ArrayList<String>();
    List<Object> objs = strs; // !!! The cause of the upcoming problem sits here. Java prohibits this!
    objs.add(1); // Here we put an Integer into a list of Strings
    String s = strs.get(0); // !!! ClassCastException: Cannot cast Integer to String
    因此 java 禁止了这样的事情来保证运行时安全。但这有些其它影响。比如，Collection 接口的 addAll() 方法。这个方法的签名在哪呢？直观来讲是这样的：

    //java
    interface Collection<E> ... {
        void addAdd(Collection<E> items);
    }
    但接下来我们就不能做下面这些简单事情了：

    //java
    void copyAll(Collection<Object> to, Collection<String> from){
        to.addAll(from);
    }
    这就是为什么 addAll() 的签名是下面这样的：

    //java
    interface Collection<E> ... {
        void addAll(Colletion<? extend E> items);
    }
    这个通配符参数? extents T意味着这个方法接受一些 T 类型的子类而非 T 类型本身。这就是说我们可以安全的读
    T's(这里表示 T 子类元素的集合)，但不能写，因为我们不知道 T 的子类究竟是什么样的，针对这样的限制，
    我们很想要这样的行为：Collection 是 Collection<? extens Object>的子类。在“智能关键字(不知道这样翻译对不对，原词clever words)”，
    用通配符的扩展绑定（上限），使该类型的协变。
    关键要理解这种方式的工作原理：如果你只能从集合取对象，然后用字符串的集合读取对象是最好的。相反，如果你只能把对象放入集合
    ，那么put一个String对这个集合中：在Java中，我们有目录<？super String>的集合的超类型。 .....
    太复杂了原谅我偷懒 原文地址：http://kotlinlang.org/docs/reference/generics.html*/

    /*********************************第三章 8.内部类************************************/
    /*在类的内部可以嵌套其他的类

    class Outer {
        private val bar: Int = 1
        class Nested {
            fun foo() = 2
        }
    }

    val demo = Outer.Nested().foo() // == 2
    内部类

    为了能被外部类访问一个类可以被标记为内部类（“inner” 关键词）。 内部类会带有一个来自外部类的对象的引用：

    class Outer {
        private val bar: Int = 1
        inner class Inner {
            fun foo() = bar
        }
    }

    val demo = Outer().Inner().foo() // == 1
    参阅this-expressions.html中“this”关键词用法来学习在内部类中如何消除“this”关键词的歧义。*/

    class Outer {
        private val bar: Int = 1

        class Nested {
            fun foo() = 2
        }
    }

    val kk = Outer.Nested().foo()

    /*********************************第三章 9.枚举************************************/
    //    不推荐在Android中使用枚举，这章就不写了

    /*********************************第三章 10.对象表达式和声明************************************/
    /*有时候我们需要创建一个对当前类做轻微修改的对象，而不用重新声明一个子类。java 用匿名内部类来解决这个问题。
    Kotlin 更希望推广用对象表达式和声明来解决这个问题。*/

    /***********对象表达式*************/
    /*我们通过下面这样的方式创建继承自某种(或某些)匿名类的对象：

    window.addMouseListener(object: MouseAdapter () {
        override fun mouseClicked(e: MouseEvent) {
            //...
        }
    })
    如果父类有构造函数，则必须传递相应的构造函数。多个父类可以用逗号隔开，跟在冒号后面：

    open class A(x: Int) {
        public open val y: Int = x
    }

    interface B { ... }

    val ab = object : A(1), B {
        override val y = 14
    }
    有时候我们只是需要一个没有父类的对象，我们可以这样写：

    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }

    print(adHoc.x + adHoc.y)
    像 java 的匿名内部类一样，对象表达式可以访问闭合范围内的变量 (和 java 不一样的是，这些不用声明为 final)

    fun countClicks(windows: JComponent) {
        var clickCount = 0
        var enterCount = 0
        window.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                clickCount++
            }
            override fun mouseEntered(e: MouseEvent){
                enterCount++
            }
        })
    }*/

    open class A1(x: Int) {
        public open val y: Int = x
    }

    // ab类型为对象,并且重写了父类的成员变量，故为对象表达式
    val ab = object : A1(1) {
        override val y = 14
    }

    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }

    /************对象声明************/
    /*单例模式是一种很有用的模式，Kotln 中声明它很方便：

    object DataProviderManager {
        fun registerDataProvider(provider: Dataprovider) {
            //...
        }
        val allDataProviders : Collection<DataProvider>
            get() = //...
    }
    这叫做对象声明。如果在 object 关键字后面有个名字，我们不能把它当做表达式了。虽然不能把它赋值给变量，，但可以通过名字赋值。这样的对象可以有父类：

    object DefaultListener : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            // ...
        }
        override fun mouseEntered(e: MouseEvent) {
            // ...
        }
    }
    注意：对象声明不可以是局部的(比如不可以直接在函数内部声明)，但可以在其它对象的声明或非内部类中使用*/

    class MouseEvent {
    }

    open class MouseAdapter {
        open fun mouseClicked(e: MouseEvent) {
        }

        open fun mouseEntered(e: MouseEvent) {
        }
    }

    // DefaultListener是一个单例对象，然后父类为MouseAdapter
    object DefaultListener : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
        }

        override fun mouseEntered(e: MouseEvent) {
        }
    }

    /************伴随对象*********/
    /*在类声明内部可以用 companion 关键字标记对象声明：

    class MyClass {
        companion object Factory {
            fun create(): MyClass = MyClass()
        }
    }
    伴随对象的成员可以通过类名做限定词直接使用：

    val instance = MyClass.create()
    在使用了 companion 关键字时，伴随对象的名字可以省略：

    class MyClass {
        companion object {

        }
    }
    注意，尽管伴随对象的成员很像其它语言中的静态成员，但在运行时它们任然是真正对象的成员实例，比如可以实现接口：

    interface Factory<T> {
        fun create(): T
    }

    class MyClass {
        companion object : Factory<MyClass> {
            override fun create(): MyClass = MyClass()
        }
    }*/

    class MyClass {
        companion object Factory {
            // 单例工厂create方法返回一个MyClass的对象
            fun create(): MyClass = MyClass()
        }
    }

    // 伴随对象Factory的成员函数create可以通过类名做限定词直接使用
    // 在使用了 companion 关键字时，伴随对象的名字可以省略，Factory都可以去掉
    val instance = MyClass.create()

    interface Factory1<T> {
        fun create(): T
    }

    class MyClass1 {
        companion object : Factory1<MyClass1> {
            override fun create(): MyClass1 = MyClass1()
        }
    }

    /************对象表达式和声明的区别***********/
    /*他俩之间只有一个特别重要的区别：
    对象声明是 lazily 初始化的，我们只能访问一次 对象表达式在我们使用的地方立即初始化并执行的*/

    /*********************************第三章 11.代理************************************/
    /*类代理

    代理模式 给实现继承提供了很好的代替方式， Kotlin 原生支持它，所以并不需要什么样板代码。Derived 类可以继承 Base 接口并且代理了它全部的公共方法：

    interface Base {
        fun print()
    }

    class BaseImpl(val x: Int) : Base {
        override fun print() { printz(x) }
    }

    class Derived(b: Base) : Base by b

    fun main() {
        val b = BaseImpl(10)
        Derived(b).print()
    }
    在 Derived 的父类列表中的条款意味这 b 将会存储在 Derived 对象中并且编译器会生成 Base 的所有方法并转给 b。*/

    interface Base1 {
        fun print()
    }

    class BaseImpl(val x: Int) : Base1 {
        override fun print() {
            KLog.e(x)
        }
    }

    // Derived1代理b实例 ( Derived1 通过 by 关键字 代理后面的变量 b)
    class Derived1(b: Base1) : Base1 by b

    fun derivedPrint() {
        val b = BaseImpl(10)
        //  b 将会存储在 Derived 对象中并且编译器会生成 Base 的所有方法并转给 b
        Derived1(b).print()
    }

    /*********************************第三章 12.属性代理************************************/
    /*很多常用属性，虽然我们可以在需要的时候手动实现它们，但更好的办法是一次实现多次使用，并放到库。比如：

    延迟属性：只在第一次访问是计算它的值 观察属性：监听者从这获取这个属性更新的通知 在 map 中存储的属性，而不是单独存在分开的字段
    为了满足这些情形，Kotllin 支持代理属性：

    class Example {
        var p: String by Delegate()
    }
    语法结构是： val/var <property name>: <Type> by <expression> 在 by 后面的属性就是代理，这样这个属性的 get() 和 set() 方法就代理给了它。

    属性代理不需要任何接口的实现，但必须要提供 get() 方法(如果是变量还需要 set() 方法)。像这样：

    class Delegate {
        fun get(thisRef: Any?, prop: PropertyMetadata): String {
            return "$thisRef, thank you for delegating '${prop.name}' to me !"
        }

        fun set(thisRef: Any?, prop: PropertyMatada, value: String) {
            println("$value has been assigned to '${prop.name} in $thisRef.'")
        }
    }
    当我们从 p 也就是 Delegate 的代理，中读东西时，会调用 Delegate 的 get() 函数，因此第一个参数是我们从 p 中读取的，第二个参数是 p 自己的一个描述。比如：

    val e = Example()
    pintln(e.p)
    打印结果：　

    Example@33a17727, thank you for delegating ‘p’ to me! 同样当我们分配 p 时 set() 函数就会调动。前俩个参数所以一样的，第三个持有分配的值：

    e.p = "NEW"
    打印结果：　 NEW has been assigned to ‘p’ in Example@33a17727.
    代理属性的要求

    这里总结一些代理对象的要求。

    只读属性 (val)，代理必须提供一个名字叫 get 的方法并接受如下参数：

    接收者--必须是相同的，或者是属性拥有者的子类型 元数据--必须是 PropertyMetadata 或这它的子类型
    这个函数必须返回同样的类型作为属性。

    可变属性 (var)，代理必须添加一个叫 set 的函数并接受如下参数： 接受者--与 get() 一样 元数据--与 get() 一样 新值--必须和属性类型一致或是它的字类型

    标准代理

    kotlin.properties.Delegates 对象是标准库提供的一个工厂方法并提供了很多有用的代理

    延迟

    Delegate.lazy() 是一个接受 lamdba 并返回一个实现延迟属性的代理：第一次调用 get() 执行 lamdba 并传递 lazy() 并记下结果，随后调用 get() 并简单返回之前记下的值。

    import kotlin.properties.Delegates

    val lazy: String by Delegates.lazy {
        println("computed!")
        "Hello"
    }

    fun main(args: Array<String>) {
        println(lazy)
        println(lazy)
    }
    如果你想要线程安全，使用 blockingLazy(): 它还是按照同样的方式工作，但保证了它的值只会在一个线程中计算，并且所有的线程都获取的同一个值。

    观察者

    Delegates.observable() 需要俩个参数：一个初始值和一个修改者的 handler 。每次我们分配属性时都会调用handler (在分配前执行)。它有三个参数：一个分配的属性，旧值，新值：

    class User {
        var name: String by Delegates.observable("<no name>") {
            d.old,new -> println("$old -> $new")
        }
    }

    fun main(args: Array<String>) {
        val user = User()
        user.name = "first"
        user.name = "second"
    }
    打印结果

    -> first first -> second 如果你想能够截取它的分配并取消它，用 vetoable()代替 observable()
    非空

    有时我们有一个非空的 var ，但我们在构造函数中没有一个合适的值，比如它必须稍后再分配。问题是你不能持有一个未初始化并且是非抽象的属性：

    class Foo {
        var bar: Bat //错误必须初始化
    }
    我们可以用 null 初始化它，但我们不用每次访问时都检查它。

    Delegates.notNull()可以解决这个问题

    class Foo {
        var bar: Bar by Delegates.notNull()
    }
    如果这个属性在第一次写之前读，它就会抛出一个异常，只有分配之后才会正常。

    在 Map 中存储属性

    Delegates.mapVal() 拥有一个 map 实例并返回一个可以从 map 中读其中属性的代理。在应用中有很多这样的例子，比如解析 JSON 或者做其它的一些 "动态"的事情：

    class User(val map: Map<String, Any?>) {
        val name: String by Delegates.mapVal(map)
        val age: Int     by Delegates.mapVal(map)
    }
    在这个例子中，构造函数持有一个 map :

    val user = User(mapOf (
            "name" to "John Doe",
            "age" to 25
    ))
    代理从这个 map 中取指(通过属性的名字)：

    println(user.name) // Prints "John Doe"
    println(user.age)  // Prints 25
    var 可以用 mapVar*/

    class Example {
        // p代理了Delegate，当使用KLog.e(e.p)会调用Delegate的get方法，使用e.p = "google"会调用Delegate的set方法
        var p: String by Delegate()
    }

    class Delegate {
        fun get(thisRef: Any?, prop: PropertyMetadata): String {
            return "${thisRef}, thank you for delegating '${prop.name}' to me !"
        }

        fun set(thisRef: Any?, prop: PropertyMetadata, value: String) {
            KLog.e("$value has been assigned to '${prop.name} in $thisRef.'")
        }
    }

    class Player {
        // Delegates.observable() 需要俩个参数：一个初始值和一个修改者的 handler
        // 每次我们分配属性时都会调用handler (在分配前执行)。它有三个参数：一个分配的属性，旧值，新值：
        // <no name>为初始值
        var name: String by Delegates.observable("<no name>") {
            d, old, new ->
            KLog.e("$old -> $new")
        }
    }

    fun setPlayer() {
        val player = Player()
        player.name = "first"
        player.name = "second"
        //  打印 <no name> -> first；然后是 first -> second
    }

    // 有一个非空的 var ，但我们在构造函数中没有一个合适的值，比如它必须稍后再分配。
    // 问题是你不能持有一个未初始化并且是非抽象的属性
    // 我们可以用 null 初始化它，但我们不用每次访问时都检查它。
    //  Delegates.notNull()可以解决这个问题
    // 如果这个属性在第一次写之前读，它就会抛出一个异常，只有分配之后才会正常。
    class Foo {
        var bar: Any by Delegates.notNull<Any>()
    }

    class User1(val map: Map<String, Any?>) {
        // Delegates.mapVal() 拥有一个 map 实例并返回一个可以从 map 中读其中属性的代理
        val name: String by Delegates.mapVal(map)
        val age: Int     by Delegates.mapVal(map)
    }

    val user = User1(mapOf(
            "name" to "John Doe",
            "age" to 25
    ))


    override fun initData() {

        //        for ((a, b) in user.map) {
        //            KLog.e("$a--$b")
        //        }

        //        val foo = Foo()
        //        foo.bar = 1
        //        foo.bar

        //        setPlayer()

        //        val e = Example()
        //        KLog.e(e.p)
        //        e.p = "google"

        //        derivedPrint()

        //        KLog.e(MyClass1.create())

        //        KLog.e(ab.y)

        //        KLog.e("Outer.Nested().foo() = $kk")

        //        KLog.e(box.value)

        //        KLog.e(olderJack)

        //        val jane = User("Jane", 35)
        // 将jane的属性值赋给(name,age)
        //        val (name, age) = jane
        //        KLog.e("$name, $age years of age")

        //        _table = mapOf(Pair("1", 1))
        //        KLog.e("_table的大小为：${_table?.size}")

        //        stringRepresentation = "你好吗"

        //        KLog.e("isBelongBaseActivity=$isBelongBaseActivity")

        //        KLog.e("example=$example")

        //        val c = C();
        //        c.f()

        //        val p = Person()
        //        KLog.e(p.getName() + " " + p.getName1())

        //        foo(arrayOf(1, 2, 3, 4, 0))

        //        var k = 10
        //        while (k > 0) {
        //            k--
        //            KLog.e(k)
        //            // 跳出循环
        //            if (k == 4)
        //                break
        //        }

        //        var y = 10
        //        do {
        //            y--
        //            KLog.e(y)
        //        } while (y > 0)

        //        val ints = arrayOf(1, 2, 3, 4, 5)
        //        // 三种遍历集合方式
        //        for (item: Int in ints) {
        //            KLog.e(item)
        //        }
        //        for (item in ints) {
        //            KLog.e(item)
        //        }
        //        for (item in ints.indices) {
        //            KLog.e(item)
        //        }

        //        val x=11
        //        val s="11"
        //        when (x) {
        //            parseInt(s) -> KLog.e("s encodes x")
        //            else ->  KLog.e("s does not encode x")
        //        }

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


