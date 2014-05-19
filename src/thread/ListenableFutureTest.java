package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ListenableFutureTest {

	public static void main(String[] args) {
		ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));

		final ListenableFuture<String> future = executor.submit(new Callable<String>() {
			public String call() throws Exception {
				return "Hello listenable future";
			}
		});

		future.addListener(new Runnable() {
			public void run() {
				try {
					System.out.println(future.get());
				} catch (InterruptedException e) {
					future.cancel(true);
				} catch (ExecutionException e) {
					future.cancel(true);
				}
			}
		}, Executors.newFixedThreadPool(1));

		System.out.println("exit..");
	}
}
