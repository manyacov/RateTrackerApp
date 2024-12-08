package com.manyacov.domain.rate_tracker.usecase

//import android.util.Log
//import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
//import com.manyacov.domain.rate_tracker.model.CurrencySymbols
//import com.manyacov.domain.rate_tracker.repository.RateTrackerRepository
//import com.manyacov.domain.rate_tracker.utils.CustomResult
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext

//class GetRatesUseCase(
//    private val rateTrackerRepository: RateTrackerRepository
//) {
//    suspend operator fun invoke(): Result<Double> = withContext(Dispatchers.IO) {
//        val symbols = getCurrencySymbols(rateTrackerRepository)
//
//        if (!symbols.isNullOrEmpty()) {
//            val selected = symbols.first()
//
//            getLatestRates(
//                base = symbols.first(),
//                repository = rateTrackerRepository
//            )
//        }
//    }
//
//    private suspend fun getCurrencySymbols(repository: RateTrackerRepository): List<CurrencySymbols>? {
//        return when (val result = repository.getCurrencySymbols()) {
//            is CustomResult.Success -> {
//                Log.println(Log.ERROR, "SSSS_s", "")
//
//                result.data
//            }
//
//            is CustomResult.Error -> {
//                Log.println(Log.ERROR, "SSSS_n", "")
//
//                null
//            }
//        }
//    }
//
//    private suspend fun getLatestRates(
//        base: String,
//        repository: RateTrackerRepository
//    ): List<CurrencyRateValue>? {
//        return when (val result = repository.getLatestRates("EUR")) {
//            is CustomResult.Success -> {
//                Log.println(Log.ERROR, "SSSS", result.data.toString())
//
//                result.data
//            }
//
//            is CustomResult.Error -> {
//                Log.println(Log.ERROR, "SSSS_n", "")
//
//                null
//            }
//        }
//    }
//}