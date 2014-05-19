package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class ListenableFutureTest2 {

	public static void main(String[] args) {
		ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));

		final ListenableFuture<String> future = executor.submit(new Callable<String>() {
			public String call() throws Exception {
				return "Hello listenable future";
			}
		});

		Futures.addCallback(future, new FutureCallback<String>() {
			public void onSuccess(String result) {
				System.out.println(result);
			}

			public void onFailure(Throwable t) {
				System.out.println("error: " + t);
			}

		}, Executors.newFixedThreadPool(1));

		System.out.println("exit..");
	}
}
