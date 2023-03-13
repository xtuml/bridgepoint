package org.xtuml.bp.core.common;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

public class SequentialExecutor implements Executor {

	private final ReentrantLock runLock = new ReentrantLock(true);
	private final Executor internalExecutor;
	private final Set<Thread> activeThreads = new HashSet<>();
	private final Set<Thread> waitingThreads = new HashSet<>();

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
					activeThreads.add(Thread.currentThread());
					command.run();
				} finally {
					activeThreads.remove(Thread.currentThread());
					runLock.unlock();
				}
			});
		}
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
				if (isShutdown && waitingThreads.size() >= activeThreads.size()) {
					throw new RuntimeException("Execution cancelled due to prevent deadlock scenario (" + Thread.currentThread().getName() + ")");
				}

				// add this thread to the set of threads waiting on some result
				waitingThreads.add(Thread.currentThread());

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
