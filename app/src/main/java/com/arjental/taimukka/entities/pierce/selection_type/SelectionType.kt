package com.arjental.taimukka.entities.pierce.selection_type

/**
 * Types of selections to filter applications.
 *
 * !!! Not rename this ones, stored in a DB. You can add new, but not to rename previous. Or make a migration.
 */

enum class SelectionType(
    /** Means that this item implemented for filtering applications values. */
    val isFilterable: Boolean
) {

    SCREEN_TIME(isFilterable = true),

    NOTIFICATIONS(isFilterable = true),

    SEANCES(isFilterable = true),

//    class FOREGROUND_SERVICES_LAUNCHES : SelectionType(isFilterable = false)
//    class NOTIFICATIONS_WATCHED : SelectionType(isFilterable = false)

}


