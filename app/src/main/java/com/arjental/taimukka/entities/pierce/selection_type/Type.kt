package com.arjental.taimukka.entities.pierce.selection_type

/**
 * Types of selections to filter applications.
 *
 * !!! Not rename this ones, stored in a DB. You can add new, but not to rename previous. Or make a migration.
 */

//SelectionType

enum class Type(
    /** Means that this item implemented for filtering applications values. */
    val isFilterable: Boolean,
    /** Means that this filter type can be processed **ONLY** from detailed messages of **UsageStatsManager**,
     * so we need to show user that we cant show more days that cashed */

    val detailedOnly: Boolean,
) {

    SCREEN_TIME(isFilterable = true, detailedOnly = false),

    NOTIFICATIONS_RECEIVED(isFilterable = true, detailedOnly = true),

    SEANCES(isFilterable = true, detailedOnly = true),

    NOTIFICATIONS_SEEN(isFilterable = false, detailedOnly = true),

    FOREGROUND_STARTS(isFilterable = false, detailedOnly = true),

}


