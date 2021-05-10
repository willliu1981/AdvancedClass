package com.ForkJoin.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class ForkThreadTest {
	static class User {
		private Integer id;
		private String name;

		public User(Integer id, String name) {
			this.name = name;
			this.id = id;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return String.format("id=%d , name=%s", this.id, this.name);
		}
	}

	static class FindUserTask extends RecursiveTask<List<User>> {
		private static AtomicInteger count = new AtomicInteger();
		private final static String queryName = ForkThreadTest.queryName;
		private final List<User> users;
		private final List<User> finds = new ArrayList<>();
		private final int threshold;
		int begin;
		int end;

		public FindUserTask(List<User> users, int begin, int end, int threshold) {
			this.users = users;
			this.threshold = threshold;
			this.begin = begin;
			this.end = end;
		}

		@Override
		protected List<User> compute() {
			if (end - begin < threshold) {
				long start = new Date().getTime();
				for (int i = begin; i < end; i++) {
					if (users.get(i).getName().equals(queryName)) {
						finds.add(users.get(i));
					}
				}
				long end = new Date().getTime();
				System.out.format("%d: %s | start:%d ,finish:%d time:%d \n", count.incrementAndGet(),
						Thread.currentThread().getName(), start, end, (end - start));
				System.out.println("find  : " + finds.size());
				finds.forEach((x) -> {
					System.out.println("  " + x);
				});
				System.out.println();
				return finds;
			} else {
				int mid = (end - begin) / 2 + begin;
				FindUserTask a1 = new FindUserTask(users, begin, mid, threshold);
				a1.fork();

				FindUserTask a2 = new FindUserTask(users, mid + 1, end, threshold);
				List<User> f=a1.join();
				f.addAll(a2.compute());
				return f;
			}
		}
	}

	static String queryName = "k76N";

	public static void main(String[] args) {

		List<User> users = new ArrayList<>();

		int max = 20000000;
		create(users, max);

		User u = new User(111, queryName);
		users.add(111, u);
		User u2 = new User(222, queryName);
		users.add(222, u2);

		System.out.println("test1>>>");
		test1(max, users, queryName);

		System.out.println("\ntest2>>>");
		test2(max, users, queryName);
	}

	static void test1(int max, List<User> users, String queryName) {
		List<User> finds = new ArrayList<>();
		long start = new Date().getTime();
		System.out.println("start : \t" + start);
		for (int i = 0; i < max; i++) {
			if (users.get(i).getName().equals(queryName)) {
				finds.add(users.get(i));
			}
		}

		long end = new Date().getTime();
		System.out.println("finish :\t" + end);
		System.out.println("time :\t" + (end - start));
		System.out.println("find  : " + finds.size());
		finds.forEach((x) -> {
			System.out.println(x);
		});
	}

	static void test2(int max, List<User> users, String queryName) {
		List<User> finds = new ArrayList<>();
		FindUserTask task = new FindUserTask(users, 0, max, users.size() / 10);
		ForkJoinPool pool = new ForkJoinPool();
		long start = new Date().getTime();
		finds = pool.invoke(task);
		long end = new Date().getTime();
		System.out.println("time  : " + (end - start));
		System.out.println("find  : " + finds.size());
		finds.forEach((x) -> {
			System.out.println(x);
		});
	}

	static void create(List<User> users, int max) {
		System.out.println("creating...");
		for (int i = 0; i < max; i++) {
			int length = (int) (Math.random() * 4 + 1);
			StringBuilder name = new StringBuilder();
			name.append(createChar(true));
			for (int j = 1; j < length; j++) {
				name.append(createChar(false));
			}
			// System.out.println(length+" , "+name);
			User user = new User(i, name.toString());
			users.add(user);
		}
		System.out.println("created :" + max);
	}

	static char createChar(boolean onlyAlphabet) {
		char c = 65;
		if (onlyAlphabet) {
			if (Math.random() > 0.5) {
				c = (char) (65 + (int) (Math.random() * 26));
			} else {
				c = (char) (97 + (int) (Math.random() * 26));
			}
		} else {
			if (Math.random() > 0.3) {
				c = (char) (48 + (int) (Math.random() * 10));
			} else if (Math.random() > 0.6) {
				c = (char) (65 + (int) (Math.random() * 26));
			} else {
				c = (char) (97 + (int) (Math.random() * 26));
			}
		}

		return c;
	}

}
