package com.arjental.taimukka.entities.pierce.selection_type

/**
 * Types of selections to filter applications.
 *
 * !!! Not rename this ones, stored in a DB. You can add new, but not to rename previous. Or make a migration.
 */

//SelectionType

enum class Type(
    /** Means that this item implemented for filtering applications values. */
    val isFilterable: Boolean
) {

    SCREEN_TIME(isFilterable = true),

    NOTIFICATIONS_RECEIVED(isFilterable = true),

    SEANCES(isFilterable = true),

    NOTIFICATIONS_SEEN(isFilterable = false),

    FOREGROUND_STARTS(isFilterable = false),

}


