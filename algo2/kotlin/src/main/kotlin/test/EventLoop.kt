package test

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import kotlin.random.Random

fun main() {
    val eventLoop = EventLoop()
    val masterThread = MasterThread(eventLoop)
    val th = Thread(masterThread)
    th.start()
    eventLoop.run()
}

class MasterThread(val eventLoop: EventLoop) : Runnable {

    val threadPool = Executors.newFixedThreadPool(8)

    override fun run() {
        for (i in 0..150) {
            val future: Future<String> = threadPool.submit(Task(i))
            eventLoop.queue.add(future)
        }
    }


}

class Task(val round: Int) : Callable<String> {
    companion object {
        val random = Random(100)
    }

    override fun call(): String {
        val rnd = random.nextInt(200, 1000)
        Thread.sleep(rnd.toLong())
        return "$round - waits: $rnd ms on  ${Thread.currentThread().name}"
    }

}


class EventLoop {

    @Volatile
    var isRunning = true

    val queue = LinkedBlockingQueue<Future<*>>()

    fun run() {
        while (isRunning) {
            if (queue.isNotEmpty()) {
                val next = queue.poll()
                if (next.isDone) {
                    val result = next.get()
                    println(result)
                } else {
                    queue.add(next)
                }
            }
        }
    }
}