package homework01.po;

public class ClassInfo {
	private int classId;
	private String className;
	public ClassInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public ClassInfo(int classId, String className) {
		super();
		this.classId = classId;
		this.className = className;
	}


	

}
