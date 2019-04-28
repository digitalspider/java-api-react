package au.com.digitalspider.api.io;

public class Error {
	private int status = 0;
	private String message = "";

	public Error() {
	}

	public Error(int status, String message) {
		this.status = status;
		this.message = message;
	}

	@Override
	public String toString() {
		return "Error [status=" + status + ", message=" + message + "]";
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

}