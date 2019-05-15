package au.com.digitalspider.api.io;

public class Response {
	private int status = 0;
	private String message = "";
	private Object data;

	public Response() {
	}

	public Response(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(int status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", message=" + message + ", data=" + data + "]";
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}