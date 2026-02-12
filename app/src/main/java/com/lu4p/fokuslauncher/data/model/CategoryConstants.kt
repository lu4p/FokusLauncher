package com.lu4p.fokuslauncher.data.model

/**
 * Constants for system categories that cannot be modified by users.
 * Only "All apps" and "Private" are protected - predefined categories can be managed.
 */
object CategoryConstants {
    const val ALL_APPS = "All apps"
    const val PRIVATE = "Private"
    
    /**
     * Returns true if the given category name is a system category that cannot be modified.
     * Only "All apps" and "Private" are protected - all other categories can be edited/deleted.
     */
    fun isSystemCategory(categoryName: String): Boolean {
        return categoryName == ALL_APPS || categoryName == PRIVATE
    }
    
    /**
     * Predefined categories that are auto-assigned but can still be managed by users.
     */
    val PREDEFINED_CATEGORIES = listOf(
        "Productivity",
        "Finance",
        "Social",
        "Health",
        "Media",
        "Games",
        "Utilities"
    )
}
