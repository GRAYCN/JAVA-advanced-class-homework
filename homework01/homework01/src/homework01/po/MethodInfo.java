package homework01.po;

public class MethodInfo {
	private int methodId;
	private int classId;
	private String methodName;
	private String returnType;
	public int getMethodId() {
		return methodId;
	}
	public MethodInfo(int methodId, int classId, String methodName, String returnType) {
		this.methodId = methodId;
		this.classId = classId;
		this.methodName = methodName;
		this.returnType = returnType;
	}
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
}	
