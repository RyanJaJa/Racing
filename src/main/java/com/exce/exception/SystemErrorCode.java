package com.exce.exception;

public enum SystemErrorCode {
    OK("Completed successfully"),
    INVALID_REQ("Invalid request"),
    AUTH_ERROR("Authentication error"),
    PLAYER_AUTH_ERROR("Player authentication error"),
    PLAYER_REGISTER_ERROR("Player registration error"),
    TRANSFER_FAIL("Transaction failed"),
    API_ERROR("API error"),
    MAINTENANCE("Maintenance"),
    LOGIN_FAIL("Login Failed"),
    NOT_ENOUGH_FUNDS("Insufficient funds"),
    HTTP_CLIENT_SERVER_ERROR("Http client/server error exception"),
    NOT_FOUND_GAME("Search game not found"),
    NOT_FOUND_USER("Search user not found"),
    NOT_FOUND_BALANCE("Search balance not found"),
    NOT_FOUND_TRANSACTION_DETAILS("Search transaction not found"),
    NOT_FOUND_BETORDERDETAIL("Search betOrderDetail not found"),
    NOT_ADD_BANKCARD("can not add bankcard"),
    ACCOUNTNAME_ALREADY_EXIST("Account name is already exist"),
    UNKNOWN_EXCEPTION("Unknown Exception");

    private final String errorDesc;

    SystemErrorCode(String description) {
        this.errorDesc = description;
    }

}
