package com.bajp.submissionthree.data.source.remote.vo


class ApiResponse<RESPONSE>(val status: StatusResponse, val body: RESPONSE, val message: String?) {

    companion object {
        fun <RESPONSE> success(body: RESPONSE): ApiResponse<RESPONSE> =
            ApiResponse(StatusResponse.SUCCESS, body = body, message = null)

        fun <RESPONSE> error(msg: String?, body: RESPONSE): ApiResponse<RESPONSE> =
            ApiResponse(StatusResponse.ERROR, body = body, msg)
    }
}