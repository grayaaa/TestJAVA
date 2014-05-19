package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(1);

		Future<String> future = executor.submit(new Callable<String>() {
			public String call() {
				return "Hello futue!";
			}
		});

		try {
			Thread.sleep(1000);
			System.out.println(future.get());
			System.out.println("exit..");
		} catch (InterruptedException e) {
			future.cancel(true);
		} catch (ExecutionException e) {
			future.cancel(true);
		} finally {
			executor.shutdown();
		}
	}
}
