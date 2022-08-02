public class MoviesList {
    private Movie[] items;
    private String  errorMessage;    


    public Movie[] getItems() {   
        return items;
    }

    public void setItems( Movie[] items) {
        this.items = items;
    }

    public String getErrorMessage() {   
        return errorMessage;
    }

    public void setErrorMessage( String  errorMessage) {
        this.errorMessage = errorMessage;
    }
}
