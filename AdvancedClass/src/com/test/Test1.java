package com.test;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
	static class Stu{
		public String name;
		public Stu(String name) {
			this.name=name;
		}
	}

	public static void main(String[] args) {
		List<Stu> s1=new ArrayList<>();
		s1.add(new Stu("A"));
		s1.add(new Stu("B"));
		s1.add(new Stu("C"));
		
		List<Stu> s2=new ArrayList<>();
		s2.add(new Stu("d"));
		s2.add(new Stu("e"));
		s2.add(new Stu("f"));
		
		s1.addAll(s2);
		
		s1.forEach(x->System.out.println(x.name));
	}

}
