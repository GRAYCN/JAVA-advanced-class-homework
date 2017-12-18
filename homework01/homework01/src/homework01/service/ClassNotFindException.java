package homework01.service;

public class ClassNotFindException extends Exception {

	private String className;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ClassNotFindException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassNotFindException(String s, Throwable ex) {
		super(s, ex);
		// TODO Auto-generated constructor stub
	}

	public ClassNotFindException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "这是ClassNotFindException异常，没有找到输入的类";
	}
	
}
