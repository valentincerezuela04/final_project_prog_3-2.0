package com.musicspring.app.music_app.reaction.common.model.entity;

public enum ReactionType {
    LIKE("Like", "👍"),
    LOVE("Love", "❤️"),
    HAHA("Haha", "😂"),
    WOW("Wow", "😮"),
    SAD("Sad", "😢"),
    ANGRY("Angry", "😠");

    private final String displayName;
    private final String emoji;

    ReactionType(String displayName, String emoji) {
        this.displayName = displayName;
        this.emoji = emoji;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmoji() {
        return emoji;
    }
}
