package com.example.multi_thread;

public class SafeMemoryOrdering {

	private static volatile int x = 0;
	private static volatile int y = 0;
	private static volatile int a = 0;
	private static volatile int b = 0;

	public static void main(String[] args) throws InterruptedException {
		int reorderingCount = 0;
		int totalRuns = 1_000_0;

		boolean isReordering = false;
		while (!isReordering) {
			for (int i = 0; i < totalRuns; i++) {
				x = 0;
				y = 0;
				a = 0;
				b = 0;

				Thread thread1 = new Thread(() -> {
					a = 1;
					x = b;
				});

				Thread thread2 = new Thread(() -> {
					b = 1;
					y = a;
				});

				thread1.start();
				thread2.start();

				thread1.join();
				thread2.join();

				// 上記はjoinで。happens-beforeの関係が成り立つので、順序関係が成り立ってしまう
				if (x == 0 && y == 0) {
					reorderingCount++;
					isReordering = true;
				}
			}

			System.out.println("Reordering observed: " + reorderingCount + " times in " + totalRuns + " runs");
		}
	}
}