package dev.mrbe.kmmapp

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}