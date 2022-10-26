package dev.mfazio.androidbaseballleague.util

import android.app.Application
import dev.mfazio.androidbaseballleague.R
import dev.mfazio.androidbaseballleague.data.BaseballRepository

/**
 * display the messages about any error
 */
fun BaseballRepository.ResultStatus.getErrorMessage(application: Application) = when (this) {
    BaseballRepository.ResultStatus.NetworkException ->
        application.resources.getString(R.string.network_exception_message)
    BaseballRepository.ResultStatus.RequestException ->
        application.resources.getString(R.string.request_exception_message)
    BaseballRepository.ResultStatus.GeneralException ->
        application.resources.getString(R.string.general_exception_message)
    else -> null
}