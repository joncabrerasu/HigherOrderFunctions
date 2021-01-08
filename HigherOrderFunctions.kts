import java.lang.Exception

// https://www.funkymuse.dev/2020/12/high-order-functions-how-why-and-what.html?m=1

// Regular function with a simple lambda function as a parameter

// Lambda function not receive any param and returns Unit

fun higherOrderFunctionSample1(block: () -> Unit) = block()

higherOrderFunctionSample1 { println("This is a simple lambda function without parameters that returns Unit") }

// Lambda function not receive any param and returns Int

fun higherOrderFunctionSample2(block: () -> Int) = block()

higherOrderFunctionSample2 { 5 + 2 }

// Lambda function not receive params and returns Int

fun highOrderFunctionSample3(block: (Int, Int) -> Int): Int {
  val result = block(3, 5)
  println("Actual result $result")
  return result
}

highOrderFunctionSample3 { x, y -> x + y }

// We can add names to our parameters to provide syntax to our Jetbrains autocomplete

fun calculateResult(block: (width: Int, length: Int) -> Int): Int {
  val result = block(3, 5)
  println("Actual result is $result")
  return result
}

calculateResult { width, length -> width * length }

// In Kotlin functions are first-class, which also means they can be variables
val area: (Int, Int) -> Int = { width: Int, length: Int -> width * length }

calculateResult(area)

// We can add a second lambda to our function
fun highOrderFunctionSample4(
  firstBlock: () -> Int,
  secondBlock: (x: Int, y: Int) -> Int
) = firstBlock() + secondBlock(2, 3)

highOrderFunctionSample4({ 5 }, { x, y -> x + y })

// We can define default functions for higher order functions

// default function for a simple higher order function
fun highOrderFunctionSample5(
  block1: () -> Int = { 5 },
  block2: (Int, Int) -> Unit
) = block2(block1(), 5)


highOrderFunctionSample5 { x, y -> println("${x + y}") }

// default function for a lambda with parameters
fun highOrderFunctionSample6(
  block1: (Int) -> Unit,
  block2: (Int, Int) -> Int = { _, _ -> 5 }
) = block1(block2(1, 2))

highOrderFunctionSample6({ x: Int -> println("Actual result $x") })

// The :: operator is called by function reference
fun div(x: Int, y: Int): Int = x / y

fun operation(
  firstNumber: Int,
  secondNumber: Int,
  mathOperation: (Int, Int) -> Int
) = mathOperation(firstNumber, secondNumber)

operation(6, 3, ::div)

// Exploring the receiver
fun highOrderFunctionSample7(block: Int.() -> Int) = println(block(4))

highOrderFunctionSample7 { this * 2 }

// Using generics to understand let functions
fun <T, R> T.checkNullOrDefault(default: R, block: T.() -> R): R {
  return if(this != null) {
    block(this)
  } else default
}

val name = "dummy"

name.checkNullOrDefault(-1) { if(this == "alien") -1 else this.length }

// Recap

fun sample1(block: () -> Unit) = block()

sample1 { println("Hi") }

fun sample2(block: () -> Int) = block()

sample2 { 5 + 2 }

fun sample3(block: (Int) -> Int) = block(5)

sample3 { x -> x * 2 }

fun sample4(x: Int, y: Int, block: (Int, Int) -> Int) = block(x, y)

sample4(2, 5) { x, y -> (x + y) * 2 }

fun sample5(block1: () -> Unit = {}, block2: (x: Int, y:Int) -> Int) {
  println(block2(2, 5))
  block1()
}

sample5{ x, y -> x * y}

fun area(length: Int, width: Int) = length * width

fun sample6(x: Int, y: Int, operation: (Int, Int) -> Int) = operation(x, y)

sample6(5, 3,::area)

fun sample7(value: String, parser: String.() -> Int):Int = parser(value)

sample7("4") { try { this.toInt() } catch (e: Exception) { -1 } }


fun <R, T> R.customFunction(value: T, block: R.() -> T): T {
  return if (this != null) block(this) else value
}

val sample = "5"

sample.customFunction(0) { this.toInt() + 7 }