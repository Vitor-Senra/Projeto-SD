package Server;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

/**
 * A simple thread pool implementation that manages a fixed number of worker threads
 * to execute submitted tasks concurrently.
 */
public class ThreadPool {
    private Queue<Runnable> taskQueue = new ArrayDeque<Runnable>();
    private Lock queueLock = new ReentrantLock();
    private Condition taskAvailable = queueLock.newCondition();
    private WorkerThread[] workers;

    public ThreadPool(int numThreads) {
        this.taskQueue = new ArrayDeque<>();
        this.workers = new WorkerThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            workers[i] = new WorkerThread();
            workers[i].setName("WorkerThread-" + i);
            workers[i].start();
        }
    }

    /**
     * Submits a task to the thread pool for execution.
     * 
     * @param task The Runnable task to be executed.
     * @return true if the task was successfully added to the queue, false otherwise.
     */
    public boolean submitTask(Runnable task) {
        queueLock.lock();
        try {
            boolean added = taskQueue.add(task);
            taskAvailable.signal();
            return added;
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * Returns the number of pending tasks in the queue.
     * 
     * @return The number of tasks waiting to be executed.
     */    
    public int getPendingTaskCount() {
        queueLock.lock();
        try {
            return taskQueue.size();
        } finally {
            queueLock.unlock();
        }
    }
    
    /**
     * Worker thread that continuously polls for tasks from the queue and executes them.
     */
    class WorkerThread extends Thread {
        public void run() {
            while (true) {
                Runnable task = null;
                queueLock.lock();
                try {
                    while (taskQueue.isEmpty()) {
                        taskAvailable.await();
                    }
                    task = taskQueue.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    queueLock.unlock();
                }
                if (task != null) {
                task.run();
                }
            }   
        }
    }
}
