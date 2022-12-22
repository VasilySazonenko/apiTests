package api;

public class FailedReg {
    private String error;

    public FailedReg(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
