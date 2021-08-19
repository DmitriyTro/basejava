package com.urise.webapp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainConcurrency {
	public static final int THREADS_NUMBERS = 10000;
	private static int counter;

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Thread.currentThread().getName());
		Thread thread0 = new Thread() {
			@Override
			public void run() {
				System.out.println(getName() + ", " + getState());
			}
		};
		thread0.start();
		new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

		System.out.println(thread0.getState());

		final MainConcurrency mainConcurrency = new MainConcurrency();
		CountDownLatch latch = new CountDownLatch(THREADS_NUMBERS);
		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < THREADS_NUMBERS; i++) {
			executorService.submit(() -> {
				for (int j = 0; j < 100; j++) {
					mainConcurrency.inc();
				}
				latch.countDown();
			});
		}

		latch.await(10, TimeUnit.SECONDS);
		executorService.shutdown();
		System.out.println(counter);

		final String lock1 = "lock1";
		final String lock2 = "lock2";

		deadlock(lock1, lock2);
		deadlock(lock2, lock1);
	}

	private static void deadlock(Object lock1, Object lock2) {
		new Thread(() -> {
			System.out.println("Waiting " + lock1);
			synchronized (lock1) {
				System.out.println("Holding " + lock1);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Waiting " + lock2);
				synchronized (lock2) {
					System.out.println("Holding " + lock2);
				}
			}
		}).start();
	}

	private synchronized void inc() {
		counter++;
	}
}
