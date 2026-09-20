package com.example.data.model

data class BioItem(
    val id: String,
    val text: String,
    val category: BioCategory
)

enum class BioCategory(val displayName: String, val description: String) {
    ATTITUDE("Attitude", "Bold, confident, and unapologetic statements"),
    LOVE("Love", "Romantic, poetic, and heartwarming quotes"),
    ROYAL("Royal", "Majestic, classy, and elite personas"),
    SAD("Sad", "Deep emotional reflections and heartache thoughts"),
    INSTAGRAM("Instagram", "Aesthetic captions and profile hooks"),
    SAVAGE("Savage", "Sharp comebacks and fierce energy"),
    CUTE("Cute", "Sweet, adorable, and charming captions"),
    MOTIVATIONAL("Motivational", "Inspiring hustle, resilience, and ambition"),
    FRIENDSHIP("Friendship", "Loyal bonds, squad goals, and brother/sister vibes"),
    GAMING("Gaming", "Clutch plays, esports hype, and gamer tags"),
    AESTHETIC("Aesthetic", "Dreamy vibes, retro nostalgia, and ethereal quotes"),
    SIMPLE("Simple", "Minimalist, crisp, and clean expressions")
}
