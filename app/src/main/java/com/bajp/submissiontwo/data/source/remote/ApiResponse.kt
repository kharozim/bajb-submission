package com.bajp.submissiontwo.data.source.remote


class ApiResponse<RESPONSE>(val status : StatusResponse,val body: RESPONSE, val message : String?) {

    companion object{
        fun<RESPONSE> success(body: RESPONSE) : ApiResponse<RESPONSE> = ApiResponse(StatusResponse.SUCCESS, body = body, message = null)
        fun <RESPONSE> empty(msg : String, body: RESPONSE) : ApiResponse<RESPONSE> = ApiResponse(
            StatusResponse.EMPTY, body,msg
        )
        fun <RESPONSE> error(msg: String, body: Nothing) : ApiResponse<RESPONSE> = ApiResponse(
            StatusResponse.EMPTY, body,msg
        )
    }
}