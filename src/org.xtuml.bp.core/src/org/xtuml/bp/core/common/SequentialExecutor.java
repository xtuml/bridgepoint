package org.xtuml.bp.core.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

/** This class is an implementation of the Executor interface based on an
 *  underlying executor. This executor ensures that only one command is running at
 *  a time, however the underlying executor may be multithreaded. The `callAndWait`
 *  method allows individual tasks being executed to pause and wait for some
 *  condition to be met.
 */

public class SequentialExecutor implements Executor {

	private final ReentrantLock runLock = new ReentrantLock(true);
	private final Executor internalExecutor;
	private final Set<Thread> waitingThreads = new HashSet<>();
	private final Map<Thread, Integer> waitingThreadCount = new HashMap<>();

	private boolean isShutdown = false;

	public SequentialExecutor(final Executor underlyingExecutor) {
		internalExecutor = underlyingExecutor;
	}

	@Override
	public void execute(Runnable command) {
		if (!isShutdown) {
			internalExecutor.execute(() -> {
				runLock.lock();
				try {
					command.run();
				} finally {
					runLock.unlock();
				}
			});
		}
	}
	
	public <T> T callAndWaitNullable(Callable<T> callable) throws Exception {
		return callAndWait(() -> Optional.ofNullable(callable.call()));
	}

	public <T> T callAndWait(Callable<Optional<T>> callable) throws Exception {
		if (runLock.isHeldByCurrentThread()) {
			while (true) {
				// attempt the call
				final Optional<T> value = callable.call();
				if (value.isPresent()) {
					waitingThreads.remove(Thread.currentThread());
					return value.get();
				}

				// if this call must be cancelled, throw an exception
				if (isShutdown && waitingThreads.size() == Optional
						.ofNullable(waitingThreadCount.get(Thread.currentThread())).orElse(-1)) {
					throw new RuntimeException("Execution cancelled due to prevent deadlock scenario ("
							+ Thread.currentThread().getName() + ")");
				}

				// add this thread to the set of threads waiting on some result
				waitingThreads.add(Thread.currentThread());
				waitingThreadCount.put(Thread.currentThread(), waitingThreads.size());

				// release and re-acquire the lock to allow someone else a turn
				runLock.unlock();
				runLock.lock();
			}
		} else {
			throw new IllegalStateException("Thread must already hold this executor's lock");
		}
	}

	public void shutdown() {
		isShutdown = true;
	}

}
