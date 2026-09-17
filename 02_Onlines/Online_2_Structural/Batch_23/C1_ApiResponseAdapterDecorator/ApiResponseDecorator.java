/**
 * Base Decorator: Implements the ApiResponse interface and wraps an ApiResponse component.
 */
public abstract class ApiResponseDecorator implements ApiResponse {
    protected final ApiResponse wrappee;

    public ApiResponseDecorator(ApiResponse wrappee) {
        if (wrappee == null) {
            throw new IllegalArgumentException("Wrapped ApiResponse cannot be null");
        }
        this.wrappee = wrappee;
    }

    @Override
    public String getBody() {
        return transform(wrappee.getBody());
    }

    protected abstract String transform(String body);
}
