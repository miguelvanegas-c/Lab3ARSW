package edu.eci.arsw.blueprints.response;

public record ApiResponse<T>(int code, String message, T data) {

}