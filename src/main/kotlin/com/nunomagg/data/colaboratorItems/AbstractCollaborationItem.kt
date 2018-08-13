package com.nunomagg.data.colaboratorItems

import com.nunomagg.data.item.Item

abstract class AbstractCollaborationItem<I : Item, P> : ICollaborationItem {

    internal abstract val item : I
    internal var openCollaboration = true
    internal val participants = mutableSetOf<P>()

    protected fun addParticipant(participant: P): Boolean {
        return openCollaboration && participants.add(participant)
    }

    fun endCollaboration() {
        openCollaboration = false
    }

    fun isOpenToCollaboration() = openCollaboration
}