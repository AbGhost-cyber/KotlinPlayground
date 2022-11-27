package com.example.kotlinplayground.stack

class StackImpl<T> : Stack<T> {
    private val storage = arrayListOf<T>()
    override fun toString() = buildString {
        appendLine("----top----")
        storage.asReversed().forEach {
            appendLine("$it")
        }
        appendLine("-----------")
    }

    override fun push(element: T) {
        storage.add(element)
    }

    override fun pop(): T? {
        if (isEmpty) {
            return null
        }
        return storage.removeAt(count - 1)
    }

    override fun peek(): T? {
        return storage.lastOrNull()
    }

    override val count: Int
        get() = storage.size

    companion object {
        private fun <Element> create(items: Iterable<Element>): Stack<Element> {
            val stack = StackImpl<Element>()
            for (item in items) {
                stack.push(item)
            }
            return stack
        }

        fun <Element> stackOf(vararg elements: Element): Stack<Element> {
            return create(elements.asList())
        }

        fun <Element> Stack<Element>.toList(): List<Element> {
            val list = arrayListOf<Element>()
            for (i in 0..count) {
                if (this.isEmpty) break
                val item = this.pop()!!
                list.add(item)
            }
            return list.reversed()
        }
    }
}
