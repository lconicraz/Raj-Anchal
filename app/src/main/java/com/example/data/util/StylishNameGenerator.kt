package com.example.data.util

enum class NameStyleCategory(val displayName: String, val description: String) {
    ALL("All Styles", "100+ Unicode, VIP & Gamer Styles"),
    GAMER("VIP Gamer", "Wings, Clan tags, Weapons & Kill badges"),
    ROYAL("Royal & VIP", "Emperors, Crowns, Sigils & Luxury"),
    AESTHETIC("Aesthetic & Cute", "Hearts, Stars, Cursive, Sparkles & Soft"),
    GOTHIC("Gothic & Dark", "Fraktur, Medieval, Skulls & Shadows"),
    CLASSIC("Classic & Fonts", "Bold, Italic, Sans, Monospace, Typewriter"),
    DECORATIVE("Decor & Symbols", "Circled, Squared, Small Caps & Combining")
}

data class StyledName(
    val styleName: String,
    val styledText: String,
    val category: NameStyleCategory = NameStyleCategory.CLASSIC
)

object StylishNameGenerator {

    private fun mapChars(input: String, transform: (Char) -> String): String {
        val sb = StringBuilder()
        for (c in input) {
            sb.append(transform(c))
        }
        return sb.toString()
    }

    private fun unicodeOffset(c: Char, upperBase: Int, lowerBase: Int, digitBase: Int? = null): String {
        return when (c) {
            in 'A'..'Z' -> {
                val codePoint = upperBase + (c - 'A')
                String(Character.toChars(codePoint))
            }
            in 'a'..'z' -> {
                val codePoint = lowerBase + (c - 'a')
                String(Character.toChars(codePoint))
            }
            in '0'..'9' -> {
                if (digitBase != null) {
                    val codePoint = digitBase + (c - '0')
                    String(Character.toChars(codePoint))
                } else {
                    c.toString()
                }
            }
            else -> c.toString()
        }
    }

    private fun unicodeOffset(input: String, upperBase: Int, lowerBase: Int, digitBase: Int? = null): String {
        val sb = StringBuilder()
        for (c in input) {
            sb.append(unicodeOffset(c, upperBase, lowerBase, digitBase))
        }
        return sb.toString()
    }

    // 1. Mathematical Serif Bold (A: 0x1D400, a: 0x1D41A, 0: 0x1D7CE)
    fun toMathBold(input: String): String = unicodeOffset(input, 0x1D400, 0x1D41A, 0x1D7CE)

    // 2. Mathematical Serif Italic (A: 0x1D434, a: 0x1D44E) - 'h' is 0x210E
    fun toMathItalic(input: String): String {
        return mapChars(input) { c ->
            when (c) {
                'h' -> "\u210E"
                in 'A'..'Z' -> String(Character.toChars(0x1D434 + (c - 'A')))
                in 'a'..'z' -> String(Character.toChars(0x1D44E + (c - 'a')))
                else -> c.toString()
            }
        }
    }

    // 3. Bold Italic (A: 0x1D468, a: 0x1D482)
    fun toBoldItalic(input: String): String = unicodeOffset(input, 0x1D468, 0x1D482)

    // 4. Script (Cursive)
    fun toScript(input: String): String {
        val scriptUpper = mapOf(
            'B' to "\u212C", 'E' to "\u2130", 'F' to "\u2131", 'H' to "\u210B",
            'I' to "\u2110", 'L' to "\u2112", 'M' to "\u2133", 'R' to "\u211B"
        )
        val scriptLower = mapOf('e' to "\u212F", 'g' to "\u210A", 'o' to "\u2134")
        return mapChars(input) { c ->
            when {
                scriptUpper.containsKey(c) -> scriptUpper[c]!!
                scriptLower.containsKey(c) -> scriptLower[c]!!
                c in 'A'..'Z' -> String(Character.toChars(0x1D49C + (c - 'A')))
                c in 'a'..'z' -> String(Character.toChars(0x1D4B6 + (c - 'a')))
                else -> c.toString()
            }
        }
    }

    // 5. Bold Script (A: 0x1D4D0, a: 0x1D4EA)
    fun toBoldScript(input: String): String = unicodeOffset(input, 0x1D4D0, 0x1D4EA)

    // 6. Fraktur / Gothic
    fun toFraktur(input: String): String {
        val frakturUpper = mapOf(
            'C' to "\u212D", 'H' to "\u210C", 'I' to "\u2111", 'R' to "\u211C", 'Z' to "\u2128"
        )
        return mapChars(input) { c ->
            when {
                frakturUpper.containsKey(c) -> frakturUpper[c]!!
                c in 'A'..'Z' -> String(Character.toChars(0x1D504 + (c - 'A')))
                c in 'a'..'z' -> String(Character.toChars(0x1D51E + (c - 'a')))
                else -> c.toString()
            }
        }
    }

    // 7. Bold Fraktur (A: 0x1D56C, a: 0x1D586)
    fun toBoldFraktur(input: String): String = unicodeOffset(input, 0x1D56C, 0x1D586)

    // 8. Double Struck / Blackboard Bold
    fun toDoubleStruck(input: String): String {
        val bbUpper = mapOf(
            'C' to "\u2102", 'H' to "\u210D", 'N' to "\u2115", 'P' to "\u2119",
            'Q' to "\u211A", 'R' to "\u211D", 'Z' to "\u2124"
        )
        return mapChars(input) { c ->
            when {
                bbUpper.containsKey(c) -> bbUpper[c]!!
                c in 'A'..'Z' -> String(Character.toChars(0x1D538 + (c - 'A')))
                c in 'a'..'z' -> String(Character.toChars(0x1D552 + (c - 'a')))
                c in '0'..'9' -> String(Character.toChars(0x1D7D8 + (c - '0')))
                else -> c.toString()
            }
        }
    }

    // 9. Sans-Serif Regular (A: 0x1D5A0, a: 0x1D5BA, 0: 0x1D7E2)
    fun toSansSerif(input: String): String = unicodeOffset(input, 0x1D5A0, 0x1D5BA, 0x1D7E2)

    // 10. Sans-Serif Bold (A: 0x1D5D4, a: 0x1D5EE, 0: 0x1D7EC)
    fun toSansBold(input: String): String = unicodeOffset(input, 0x1D5D4, 0x1D5EE, 0x1D7EC)

    // 11. Sans-Serif Italic (A: 0x1D608, a: 0x1D622)
    fun toSansItalic(input: String): String = unicodeOffset(input, 0x1D608, 0x1D622)

    // 12. Sans-Serif Bold Italic (A: 0x1D63C, a: 0x1D656)
    fun toSansBoldItalic(input: String): String = unicodeOffset(input, 0x1D63C, 0x1D656)

    // 13. Monospace / Typewriter (A: 0x1D670, a: 0x1D68A, 0: 0x1D7F6)
    fun toMonospace(input: String): String = unicodeOffset(input, 0x1D670, 0x1D68A, 0x1D7F6)

    // 14. Small Caps
    fun toSmallCaps(input: String): String {
        val map = mapOf(
            'a' to "ᴀ", 'b' to "ʙ", 'c' to "ᴄ", 'd' to "ᴅ", 'e' to "ᴇ",
            'f' to "ғ", 'g' to "ɢ", 'h' to "ʜ", 'i' to "ɪ", 'j' to "ᴊ",
            'k' to "ᴋ", 'l' to "ʟ", 'm' to "ᴍ", 'n' to "ɴ", 'o' to "ᴏ",
            'p' to "ᴘ", 'q' to "ǫ", 'r' to "ʀ", 's' to "s", 't' to "ᴛ",
            'u' to "ᴜ", 'v' to "ᴠ", 'w' to "ᴡ", 'x' to "x", 'y' to "ʏ",
            'z' to "ᴢ"
        )
        return mapChars(input) { c ->
            val lower = c.lowercaseChar()
            map[lower] ?: c.toString()
        }
    }

    // 15. Circled text (A: 0x24B6, a: 0x24D0, 1: 0x2460, 0: 0x24EA)
    fun toCircled(input: String): String {
        return mapChars(input) { c ->
            when (c) {
                in 'A'..'Z' -> String(Character.toChars(0x24B6 + (c - 'A')))
                in 'a'..'z' -> String(Character.toChars(0x24D0 + (c - 'a')))
                in '1'..'9' -> String(Character.toChars(0x2460 + (c - '1')))
                '0' -> "\u24EA"
                else -> c.toString()
            }
        }
    }

    // 16. Circled Black / Dark Bubble
    fun toCircledDark(input: String): String {
        return mapChars(input) { c ->
            val upper = c.uppercaseChar()
            if (upper in 'A'..'Z') {
                String(Character.toChars(0x1F150 + (upper - 'A')))
            } else {
                c.toString()
            }
        }
    }

    // 17. Squared Light
    fun toSquared(input: String): String {
        return mapChars(input) { c ->
            val upper = c.uppercaseChar()
            if (upper in 'A'..'Z') {
                String(Character.toChars(0x1F130 + (upper - 'A')))
            } else {
                c.toString()
            }
        }
    }

    // 18. Squared Dark / Inverted Boxed
    fun toSquaredDark(input: String): String {
        return mapChars(input) { c ->
            val upper = c.uppercaseChar()
            if (upper in 'A'..'Z') {
                String(Character.toChars(0x1F170 + (upper - 'A')))
            } else {
                c.toString()
            }
        }
    }

    // 19. Fullwidth / Aesthetic
    fun toFullwidth(input: String): String {
        return mapChars(input) { c ->
            when (c) {
                in 'A'..'Z' -> String(Character.toChars(0xFF21 + (c - 'A')))
                in 'a'..'z' -> String(Character.toChars(0xFF41 + (c - 'a')))
                in '0'..'9' -> String(Character.toChars(0xFF10 + (c - '0')))
                ' ' -> "\u3000"
                else -> c.toString()
            }
        }
    }

    // 20. Strikethrough
    fun toStrikethrough(input: String): String {
        val sb = StringBuilder()
        for (c in input) {
            sb.append(c).append('\u0336')
        }
        return sb.toString()
    }

    // 21. Subtle Underline
    fun toUnderline(input: String): String {
        val sb = StringBuilder()
        for (c in input) {
            sb.append(c).append('\u0332')
        }
        return sb.toString()
    }

    // 22. Double Underline
    fun toDoubleUnderline(input: String): String {
        val sb = StringBuilder()
        for (c in input) {
            sb.append(c).append('\u0333')
        }
        return sb.toString()
    }

    // 23. Overline / Top Line
    fun toOverline(input: String): String {
        val sb = StringBuilder()
        for (c in input) {
            sb.append(c).append('\u0305')
        }
        return sb.toString()
    }

    // 24. Slashed Matrix
    fun toSlashed(input: String): String {
        val sb = StringBuilder()
        for (c in input) {
            sb.append(c).append('\u0337')
        }
        return sb.toString()
    }

    // 25. Wave Strikethrough
    fun toWaveStrikethrough(input: String): String {
        val sb = StringBuilder()
        for (c in input) {
            sb.append(c).append('\u0334')
        }
        return sb.toString()
    }

    // 26. Upside Down / Inverted
    fun toUpsideDown(input: String): String {
        val flipMap = mapOf(
            'a' to "ɐ", 'b' to "q", 'c' to "ɔ", 'd' to "p", 'e' to "ǝ",
            'f' to "ɟ", 'g' to "ƃ", 'h' to "ɥ", 'i' to "ᴉ", 'j' to "ɾ",
            'k' to "ʞ", 'l' to "l", 'm' to "ɯ", 'n' to "u", 'o' to "o",
            'p' to "d", 'q' to "b", 'r' to "ɹ", 's' to "s", 't' to "ʇ",
            'u' to "n", 'v' to "ʌ", 'w' to "ʍ", 'x' to "x", 'y' to "ʎ",
            'z' to "z",
            'A' to "∀", 'B' to "𐐒", 'C' to "Ɔ", 'D' to "ᗡ", 'E' to "Ǝ",
            'F' to "Ⅎ", 'G' to "⅁", 'H' to "H", 'I' to "I", 'J' to "ſ",
            'K' to "⋊", 'L' to "˥", 'M' to "W", 'N' to "N", 'O' to "O",
            'P' to "Ԁ", 'Q' to "Ό", 'R' to "ᴚ", 'S' to "S", 'T' to "⊥",
            'U' to "∩", 'V' to "Λ", 'W' to "M", 'X' to "X", 'Y' to "⅄",
            'Z' to "Z",
            '?' to "¿", '!' to "¡", '.' to "˙", ',' to "'", '_' to "‾"
        )
        val sb = StringBuilder()
        for (i in input.length - 1 downTo 0) {
            val c = input[i]
            sb.append(flipMap[c] ?: c.toString())
        }
        return sb.toString()
    }

    // 27. Spaced / Wide Aesthetic
    fun toSpaced(input: String): String {
        return input.map { "$it " }.joinToString("").trimEnd()
    }

    // Sample names for randomized ideas
    val sampleNamePresets = listOf(
        "Trick Master",
        "Shadow",
        "Ghost",
        "King",
        "Legend",
        "Queen",
        "Phoenix",
        "Sniper",
        "Viper",
        "Titan",
        "Cyber",
        "Blaze",
        "Valkyrie",
        "Phantom",
        "Nexus",
        "Apex",
        "Frost",
        "Raven",
        "Venom",
        "Aura"
    )

    fun getRandomSampleName(): String {
        return sampleNamePresets.random()
    }

    /**
     * Generates 115+ unique stylish Unicode names across Classic, Gothic, Script/Aesthetic,
     * VIP Gamer, Royal, and Decorative categories. Works with any English name.
     */
    fun generateAllStyles(rawName: String): List<StyledName> {
        val name = rawName.trim().ifEmpty { "Trick Master" }
        val bold = toMathBold(name)
        val italic = toMathItalic(name)
        val boldItalic = toBoldItalic(name)
        val script = toScript(name)
        val boldScript = toBoldScript(name)
        val fraktur = toFraktur(name)
        val boldFraktur = toBoldFraktur(name)
        val doubleStruck = toDoubleStruck(name)
        val sans = toSansSerif(name)
        val sansBold = toSansBold(name)
        val sansItalic = toSansItalic(name)
        val sansBoldItalic = toSansBoldItalic(name)
        val monospace = toMonospace(name)
        val smallCaps = toSmallCaps(name)
        val circled = toCircled(name)
        val circledDark = toCircledDark(name)
        val squared = toSquared(name)
        val squaredDark = toSquaredDark(name)
        val fullwidth = toFullwidth(name)
        val strikethrough = toStrikethrough(name)
        val underline = toUnderline(name)
        val doubleUnderline = toDoubleUnderline(name)
        val overline = toOverline(name)
        val slashed = toSlashed(name)
        val waveStrike = toWaveStrikethrough(name)
        val upsideDown = toUpsideDown(name)
        val spaced = toSpaced(name)

        return listOf(
            // ========================================================
            // 1. VIP GAMER (24 STYLES)
            // ========================================================
            StyledName("VIP Cyber Wings", "꧁༺$name༻꧂", NameStyleCategory.GAMER),
            StyledName("Grand Master Clan", "꧁༒☬$bold☬༒꧂", NameStyleCategory.GAMER),
            StyledName("Legend Badge", "༺Leͥgeͣnͫd༻ $name", NameStyleCategory.GAMER),
            StyledName("Electric Thunder", "⚡『$bold』⚡", NameStyleCategory.GAMER),
            StyledName("Beast Clan Boss", "亗 $bold 亗", NameStyleCategory.GAMER),
            StyledName("Sniper Crosshair", "︻デ═一 $bold", NameStyleCategory.GAMER),
            StyledName("Shadow Samurai", "⚔️ $smallCaps ⚔️", NameStyleCategory.GAMER),
            StyledName("Devil Smile", "╰‿╯ $name", NameStyleCategory.GAMER),
            StyledName("Savage Boss", "×͜× $bold", NameStyleCategory.GAMER),
            StyledName("Toxic Biohazard", "☣ $bold ☣", NameStyleCategory.GAMER),
            StyledName("Dragon Claws", "◥꧁ $bold ꧂◤", NameStyleCategory.GAMER),
            StyledName("Cyber Boxed", "〖 $name 〗", NameStyleCategory.GAMER),
            StyledName("Vortex Shuriken", "𖣘 $bold 𖣘", NameStyleCategory.GAMER),
            StyledName("Star Warrior", "꧁☆$bold☆꧂", NameStyleCategory.GAMER),
            StyledName("Sacred Sigil", "༒•$bold•༒", NameStyleCategory.GAMER),
            StyledName("Imperial Seal", "〖ঔৣ☬$bold☬ঔৣ〗", NameStyleCategory.GAMER),
            StyledName("Shooting Stars", "★彡[$bold]彡★", NameStyleCategory.GAMER),
            StyledName("Cross Cut Blade", "乂 $bold 乂", NameStyleCategory.GAMER),
            StyledName("Cyber Brackets", "◤ $bold ◢", NameStyleCategory.GAMER),
            StyledName("Skull Reaper", "☠︎ $bold ☠︎", NameStyleCategory.GAMER),
            StyledName("Ghost Ops", "⫷ $name ⫸", NameStyleCategory.GAMER),
            StyledName("God Mode Title", "〖ɢᴏᴅ〗$bold", NameStyleCategory.GAMER),
            StyledName("Alpha Wolf Claws", "🐺 爪 $bold 爪 🐺", NameStyleCategory.GAMER),
            StyledName("Neon Katana", "🗡️ 彡${bold}彡 🗡️", NameStyleCategory.GAMER),

            // ========================================================
            // 2. ROYAL & VIP (20 STYLES)
            // ========================================================
            StyledName("Golden Crown King", "👑 𝕶𝖎𝖓𝖌 $fraktur 👑", NameStyleCategory.ROYAL),
            StyledName("Queen Empress", "♛ $script ♛", NameStyleCategory.ROYAL),
            StyledName("Imperial Sovereign", "⚜️ $doubleStruck ⚜️", NameStyleCategory.ROYAL),
            StyledName("Majesty Crest", "꧁🏛️ $bold 🏛️꧂", NameStyleCategory.ROYAL),
            StyledName("Royal Dynasty", "✧ 𝕽𝖔𝖞𝖆𝖑 $name ✧", NameStyleCategory.ROYAL),
            StyledName("Diamond Aristocrat", "💎 𝓥𝓘𝓟 $bold 💎", NameStyleCategory.ROYAL),
            StyledName("Fleur-de-lis Elite", "⚜ $smallCaps ⚜", NameStyleCategory.ROYAL),
            StyledName("Golden Eagle Sovereign", "🦅 ༺$bold༻ 🦅", NameStyleCategory.ROYAL),
            StyledName("Noble Chalice", "🍷 $boldFraktur 🍷", NameStyleCategory.ROYAL),
            StyledName("High Crown Monarchy", "𓆩👑𓆪 $name", NameStyleCategory.ROYAL),
            StyledName("Kingdom Guardian", "🛡️ $bold 🛡️", NameStyleCategory.ROYAL),
            StyledName("Celestial Emperor", "🌌 𝕰𝖒𝖕𝖊𝖗𝖔𝖗 $name 🌌", NameStyleCategory.ROYAL),
            StyledName("Crowned Bold Sans", "👑 $sansBold 👑", NameStyleCategory.ROYAL),
            StyledName("Royal Script Crest", "⟦ 𝓡𝓸𝔂𝓪𝓵 $boldScript ⟧", NameStyleCategory.ROYAL),
            StyledName("Golden Laurel Wreath", "🌿 $bold 🌿", NameStyleCategory.ROYAL),
            StyledName("Platinum VIP Tag", "⟨⟨ 𝒱𝐼𝒫 $name ⟩⟩", NameStyleCategory.ROYAL),
            StyledName("Imperial Pillar", "🏛️ 〖 $bold 〗 🏛️", NameStyleCategory.ROYAL),
            StyledName("Sovereign Monogram", "♔ $smallCaps ♔", NameStyleCategory.ROYAL),
            StyledName("Baron of Shadows", "༺ 𝕭𝖆𝖗𝖔𝖓 $boldFraktur ༻", NameStyleCategory.ROYAL),
            StyledName("Gold Signet", "⟦ $doubleStruck ⟧", NameStyleCategory.ROYAL),

            // ========================================================
            // 3. AESTHETIC & CUTE (22 STYLES)
            // ========================================================
            StyledName("Sparkle Angel Script", "✧༺ $script ༻✧", NameStyleCategory.AESTHETIC),
            StyledName("Love Angel Wings", "ʚ $script ɞ", NameStyleCategory.AESTHETIC),
            StyledName("Butterfly Fairy", "🦋 $boldScript 🦋", NameStyleCategory.AESTHETIC),
            StyledName("Soft Cloud Dream", "☁️ $script ☁️", NameStyleCategory.AESTHETIC),
            StyledName("Cherry Blossom", "🌸 $smallCaps 🌸", NameStyleCategory.AESTHETIC),
            StyledName("Pastel Ribbon", "🎀 $script 🎀", NameStyleCategory.AESTHETIC),
            StyledName("Moon & Stars", "🌙 ✧ $name ✧ 🌙", NameStyleCategory.AESTHETIC),
            StyledName("Sweet Honey Script", "🍯 $script 🍯", NameStyleCategory.AESTHETIC),
            StyledName("Cupid Heart", "♡ $bold ♡", NameStyleCategory.AESTHETIC),
            StyledName("Sparkle Magic", "✨ $name ✨", NameStyleCategory.AESTHETIC),
            StyledName("Aesthetic Brackets", "꒰ $script ꒱", NameStyleCategory.AESTHETIC),
            StyledName("Soft Floral Bloom", "✿ $smallCaps ✿", NameStyleCategory.AESTHETIC),
            StyledName("Shooting Star Sparkles", "⋆｡°✩ $name ✩°｡⋆", NameStyleCategory.AESTHETIC),
            StyledName("Kawaii Bear", "ʕ•ᴥ•ʔ $name", NameStyleCategory.AESTHETIC),
            StyledName("Celestial Crescent", "☾ $script ☽", NameStyleCategory.AESTHETIC),
            StyledName("Floating Hearts", "💕 $boldScript 💕", NameStyleCategory.AESTHETIC),
            StyledName("Japanese Corner Quotes", "『 $smallCaps 』", NameStyleCategory.AESTHETIC),
            StyledName("Cosmic Planet", "🪐 $name 🪐", NameStyleCategory.AESTHETIC),
            StyledName("Golden Sunflower", "🌻 $script 🌻", NameStyleCategory.AESTHETIC),
            StyledName("Graceful Swan", "🦢 $italic 🦢", NameStyleCategory.AESTHETIC),
            StyledName("Aesthetic Music Note", "🎵 $script 🎶", NameStyleCategory.AESTHETIC),
            StyledName("Pastel Sparkle Dots", "･ﾟ: * $name *:･ﾟ", NameStyleCategory.AESTHETIC),

            // ========================================================
            // 4. GOTHIC & DARK (18 STYLES)
            // ========================================================
            StyledName("Royal Fraktur", fraktur, NameStyleCategory.GOTHIC),
            StyledName("Bold Gothic Fraktur", boldFraktur, NameStyleCategory.GOTHIC),
            StyledName("Dark Eclipse", "✦ 𝒟𝒶𝓇𝓀 $fraktur ✦", NameStyleCategory.GOTHIC),
            StyledName("Skull Knight Gothic", "☠︎ $boldFraktur ☠︎", NameStyleCategory.GOTHIC),
            StyledName("Dark Monarch", "♛ $boldFraktur ♛", NameStyleCategory.GOTHIC),
            StyledName("Divine Warrior Gothic", "☬ $fraktur ☬", NameStyleCategory.GOTHIC),
            StyledName("Blood Rose Gothic", "🥀 $boldFraktur 🥀", NameStyleCategory.GOTHIC),
            StyledName("Shadow Dagger Gothic", "🗡️ $fraktur 🗡️", NameStyleCategory.GOTHIC),
            StyledName("Night Raven", "𓅓 $boldFraktur 𓅓", NameStyleCategory.GOTHIC),
            StyledName("Grim Phantom", "𓆩🖤𓆪 $fraktur 𓆩🖤𓆪", NameStyleCategory.GOTHIC),
            StyledName("Graveyard Mist", "🕯️ $boldFraktur 🕯️", NameStyleCategory.GOTHIC),
            StyledName("Medieval Nordic Rune", "ᛟ $fraktur ᛟ", NameStyleCategory.GOTHIC),
            StyledName("Void Chains", "⛓️ $fraktur ⛓️", NameStyleCategory.GOTHIC),
            StyledName("Vampire Crest", "🧛 $boldFraktur 🧛", NameStyleCategory.GOTHIC),
            StyledName("Fallen Angel Cross", "𓆩✞𓆪 $boldFraktur 𓆩✞𓆪", NameStyleCategory.GOTHIC),
            StyledName("Occult Sigil", "⛧ $fraktur ⛧", NameStyleCategory.GOTHIC),
            StyledName("Dark Serpent", "🐍 $boldFraktur 🐍", NameStyleCategory.GOTHIC),
            StyledName("Midnight Obsidian", "☾ $fraktur ☽", NameStyleCategory.GOTHIC),

            // ========================================================
            // 5. CLASSIC & FONTS (15 STYLES)
            // ========================================================
            StyledName("Mathematical Bold", bold, NameStyleCategory.CLASSIC),
            StyledName("Mathematical Italic", italic, NameStyleCategory.CLASSIC),
            StyledName("Bold Italic Serif", boldItalic, NameStyleCategory.CLASSIC),
            StyledName("Double Struck Blackboard", doubleStruck, NameStyleCategory.CLASSIC),
            StyledName("Clean Sans-Serif", sans, NameStyleCategory.CLASSIC),
            StyledName("Bold Sans-Serif", sansBold, NameStyleCategory.CLASSIC),
            StyledName("Italic Sans-Serif", sansItalic, NameStyleCategory.CLASSIC),
            StyledName("Bold Italic Sans", sansBoldItalic, NameStyleCategory.CLASSIC),
            StyledName("Retro Monospace", monospace, NameStyleCategory.CLASSIC),
            StyledName("Vaporwave Fullwidth", fullwidth, NameStyleCategory.CLASSIC),
            StyledName("Aesthetic Spaced", spaced, NameStyleCategory.CLASSIC),
            StyledName("Cursive Script", script, NameStyleCategory.CLASSIC),
            StyledName("Bold Script", boldScript, NameStyleCategory.CLASSIC),
            StyledName("Small Capitals", smallCaps, NameStyleCategory.CLASSIC),
            StyledName("Upside Down Inverted", upsideDown, NameStyleCategory.CLASSIC),

            // ========================================================
            // 6. DECORATIVE & SYMBOLS (20 STYLES)
            // ========================================================
            StyledName("Circled Bubble", circled, NameStyleCategory.DECORATIVE),
            StyledName("Circled Dark Bubble", circledDark, NameStyleCategory.DECORATIVE),
            StyledName("Squared Light", squared, NameStyleCategory.DECORATIVE),
            StyledName("Squared Dark Inverted", squaredDark, NameStyleCategory.DECORATIVE),
            StyledName("Strikethrough Cut", strikethrough, NameStyleCategory.DECORATIVE),
            StyledName("Wave Strikethrough", waveStrike, NameStyleCategory.DECORATIVE),
            StyledName("Subtle Underline", underline, NameStyleCategory.DECORATIVE),
            StyledName("Double Underline", doubleUnderline, NameStyleCategory.DECORATIVE),
            StyledName("Overline Skyline", overline, NameStyleCategory.DECORATIVE),
            StyledName("Slashed Matrix", slashed, NameStyleCategory.DECORATIVE),
            StyledName("Diamond Sigil", "❖ $name ❖", NameStyleCategory.DECORATIVE),
            StyledName("Yin Yang Mystic", "☯ $name ☯", NameStyleCategory.DECORATIVE),
            StyledName("Infinity Symbol", "∞ $bold ∞", NameStyleCategory.DECORATIVE),
            StyledName("Target Bullseye", "🎯 $bold 🎯", NameStyleCategory.DECORATIVE),
            StyledName("Flame Energy", "🔥 $bold 🔥", NameStyleCategory.DECORATIVE),
            StyledName("Crystal Prism", "🔮 $name 🔮", NameStyleCategory.DECORATIVE),
            StyledName("Heartbeat Pulse", "ﮩ٨ـﮩﮩʌـﮩ٨ـ $name ﮩ٨ـﮩﮩʌـﮩ٨ـ", NameStyleCategory.DECORATIVE),
            StyledName("Lightning Bolt", "⚡ $bold ⚡", NameStyleCategory.DECORATIVE),
            StyledName("Crescent Moon Star", "☪ $bold ☪", NameStyleCategory.DECORATIVE),
            StyledName("Peace Dove", "🕊️ $smallCaps 🕊️", NameStyleCategory.DECORATIVE)
        )
    }
}
