package com.example.scribbledash.features.oneroundwonder.state

import androidx.compose.ui.geometry.Offset

/**
 * Events that drive the One Round Wonder state machine.
 */
sealed class OneRoundEvent {
    /** Kick off the flow: pick a random drawing & enter the 3s preview */
    object StartGame : OneRoundEvent()

    /** Fired once per second during Preview to decrement the countdown */
    object Tick : OneRoundEvent()

    /** Emitted by the Draw screen whenever the user’s stroke list changes */
    data class PathRecorded(val paths: List<List<Offset>>) : OneRoundEvent()

    /** User tapped “Done” — submit for evaluation */
    object Submit : OneRoundEvent()

    /** User tapped “Try Again” on the results screen */
    object Retry : OneRoundEvent()

    /** User tapped the close/back icon to exit the One Round flow */
    object Back : OneRoundEvent()
}
