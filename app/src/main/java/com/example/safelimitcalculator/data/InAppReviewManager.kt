package com.example.safelimitcalculator.data

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory

class InAppReviewManager(
    private val context: Context
) {

    private val reviewManager = ReviewManagerFactory.create(context)

    fun launchReview(activity: Activity) {
        val request = reviewManager.requestReviewFlow()

        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = reviewManager.launchReviewFlow(activity, reviewInfo)

                flow.addOnCompleteListener {

                }
            }
        }
    }
}