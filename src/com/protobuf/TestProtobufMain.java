package com.protobuf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.protobuf.TestProtos.Class;
import com.protobuf.TestProtos.Student;
import com.protobuf.TestProtos.Teacher;

public class TestProtobufMain {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws Exception {
		Teacher t1 = Teacher.newBuilder().setId(10000).setName("tWang").setEmail("tWang@gmail.com")
				.addPhone(TestProtos.Teacher.PhoneNumber.newBuilder().setNumber("896271174"))
				.addPhone(TestProtos.Teacher.PhoneNumber.newBuilder().setNumber("181000000000")).build();
		Student s1 = TestProtos.Student.newBuilder().setId(9000001).setName("liming").setEmail("liming@gmail.com")
				.build();
		Student s2 = TestProtos.Student.newBuilder().setId(9000002).setName("wangning").setEmail("wangning@gmail.com")
				.build();
		Student s3 = TestProtos.Student.newBuilder().setId(9000003).setName("haogang").setEmail("haogang@gmail.com")
				.build();

		FileOutputStream output = new FileOutputStream("src/protobuf-serializable");
		try {
			Class.newBuilder().setTercher(t1).addStudent(s1).addStudent(s2).addStudent(s3).build().writeTo(output);
		} finally {
			output.close();
		}

		// Class class1 = Class.parseFrom(new
		// FileInputStream("src/protobuf-serializable"));
		// System.out.println(class1.toString());
	}
}
