package com.company
public class A {
	public void run(){
		if(new C().getVersion().equals("2.0")) {
			System.out.println("A ok");
		}else{
			System.out.println("A error")
		}
	}
}
