package io.github.kabirnayeem99.klean.utility

import io.github.kabirnayeem99.klean.R
import retrofit2.Response


/**
 * Get user friendly message based on the API response type
 * and the response code
 *
 * @receiver [Response]
 * @return error message as a [String]
 */
val Response<*>.userFriendlyMessageRes: Int
    get() {
        return when (code()) {
            200 -> R.string.ok_response
            201 -> R.string.ok_response
            202 -> R.string.accepted
            203 -> R.string.non_auth_info
            204 -> R.string.no_content
            300 -> R.string.multiple_choices
            301 -> R.string.moved_permanantly
            302 -> R.string.moved_temp
            400 -> R.string.bad_request
            401 -> R.string.unauthorised
            402 -> R.string.payment_required
            403 -> R.string.forbidden
            404 -> R.string.not_found
            405 -> R.string.method_not_allowed
            406 -> R.string.not_acceptable
            408 -> R.string.request_timeout
            409 -> R.string.conflict
            415 -> R.string.media_type
            500 -> R.string.internal_server_error
            503 -> R.string.service_unavailable
            else -> R.string.unknown_error
        }
    }


val Response<*>.messageRes: Int
    get() {
        return when (code()) {

            // 1XX: Information
            100 -> R.string.continue_error
            101 -> R.string.switching_protocols_error
            103 -> R.string.early_hints_error

            // 2XX: Successful
            200 -> R.string.ok_response_error
            201 -> R.string.created_error
            202 -> R.string.accepted_error
            203 -> R.string.non_auth_info_error
            204 -> R.string.no_content_error
            205 -> R.string.reset_content_error
            206 -> R.string.partial_content_error

            // 3XX: Redirection
            300 -> R.string.multiple_choices_error
            301 -> R.string.moved_permanantly_error
            302 -> R.string.moved_temp_error
            303 -> R.string.moved_temp_error
            304 -> R.string.not_modified_error
            307 -> R.string.temp_redirect_error
            308 -> R.string.permanent_redirect_error

            // 4XX: Client Error
            400 -> R.string.bad_request_error
            401 -> R.string.unauthorised_error
            402 -> R.string.payment_required_error
            403 -> R.string.forbidden_error
            404 -> R.string.not_found_error
            405 -> R.string.method_not_allowed_error
            406 -> R.string.not_acceptable_error
            407 -> R.string.proxy_auth_req_error
            408 -> R.string.request_timeout_error
            409 -> R.string.conflict_error
            410 -> R.string.gone_error
            411 -> R.string.length_required_error
            412 -> R.string.precond_failed_error
            413 -> R.string.req_large_error
            414 -> R.string.req_uri_too_long
            415 -> R.string.media_type_error
            416 -> R.string.range_not_sat_error
            417 -> R.string.expectation_failed_error

            // 5XX: Server Error
            500 -> R.string.internal_server_error_error
            501 -> R.string.not_implemented_error
            502 -> R.string.bad_gateway_error
            503 -> R.string.service_unavailable_error
            504 -> R.string.gateway_timeout_error
            505 -> R.string.http_ver_error
            511 -> R.string.net_auth_error

            // Unknown
            else -> R.string.unknown_error_error
        }
    }


val Response<*>.message: String
    get() {
        return when (code()) {

            // 1XX: Information
            100 -> "Information. The server has received the request headers, and the client should proceed to send the request body."
            101 -> "Information. The requester has asked the server to switch protocols."
            103 -> "Information. Used with the Link header to allow the browser to start preloading resources while the server prepares a response."

            // 2XX: Successful
            200 -> "Successful. The request is OK (this is the standard response for successful HTTP requests)."


            // 4XX: Client Error
            400 -> "Client Error. The request cannot be fulfilled due to bad syntax"
            401 -> "Client Error. The request was a legal request, but the server is refusing to respond to it. For use when authentication is possible but has failed or not yet been provided."
            402 -> "Client Error. Reserved for future use."
            403 -> "Client Error. The request was a legal request, but the server is refusing to respond to it"
            414 -> "Client Error. The server will not accept the request, because the URI is too long. Occurs when you convert a POST request to a GET request with a long query information."
            415 -> "Client Error. The server will not accept the request, because the media type is not supported."
            416 -> "Client Error. The client has asked for a portion of the file, but the server cannot supply that portion."
            417 -> "Client Error. The server cannot meet the requirements of the Expect request-header field"

            // 5XX: Server Error
            500 -> "Server Error. A generic error message, given when no more specific message is suitable."
            501 -> "Server Error. The server either does not recognize the request method, or it lacks the ability to fulfill the request."
            502 -> "Server Error.  The server was acting as a gateway or proxy and received an invalid response from the upstream server."
            503 -> "Server Error. The server is currently unavailable (overloaded or down)."
            504 -> "Server Error. The server was acting as a gateway or proxy and did not receive a timely response from the upstream server."
            505 -> "Server Error. The server does not support the HTTP protocol version used in the request."
            511 -> "Server Error. The client needs to authenticate to gain network access"

            // Unknown
            else -> "Unknown. Something went wrong."
        }
    }