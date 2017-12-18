package homework01.po;

public class ParamInfo {
	private int paramId;
	private int methodId;
	private String paramName;
	private String paramType;
	
	public ParamInfo(int paramId, int methodId, String paramName, String paramType) {
		super();
		this.paramId = paramId;
		this.methodId = methodId;
		this.paramName = paramName;
		this.paramType = paramType;
	}
	public int getParamId() {
		return paramId;
	}
	public void setParamId(int paramId) {
		this.paramId = paramId;
	}
	public int getMethodId() {
		return methodId;
	}
	public void setMethodId(int methodId) {
		this.methodId = methodId;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	
}
