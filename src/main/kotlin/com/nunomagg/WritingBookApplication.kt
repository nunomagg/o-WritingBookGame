package com.nunomagg

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class WritingBookApplication

fun main(args: Array<String>) {
    SpringApplication.run(WritingBookApplication::class.java, *args)
}