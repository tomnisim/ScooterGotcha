export class Response {
    constructor(response) {
        this.value = response.value;
        this.was_exception = response.was_exception;
        this.message = response.message;
    }
    static create(value, was_exception, message) {
        return new Response({
            value: value,
            was_exception: was_exception,
            message: message,
        })

    }
}