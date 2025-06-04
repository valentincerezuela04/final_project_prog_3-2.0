package com.musicspring.app.music_app.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object indicating reaction action result")
public class ReactionActionResponse {

    @Schema(description = "Action performed", example = "CREATED", allowableValues = {"CREATED", "UPDATED", "DELETED"})
    private String action;

    @Schema(description = "Reaction details (null if deleted)")
    private ReactionResponse reaction;

    @Schema(description = "Success message", example = "Reaction processed successfully")
    private String message;

    public static ReactionActionResponse created(ReactionResponse reaction) {
        return ReactionActionResponse.builder()
                .action("CREATED")
                .reaction(reaction)
                .message("Reaction created successfully")
                .build();
    }

    public static ReactionActionResponse updated(ReactionResponse reaction) {
        return ReactionActionResponse.builder()
                .action("UPDATED")
                .reaction(reaction)
                .message("Reaction updated successfully")
                .build();
    }

    public static ReactionActionResponse deleted() {
        return ReactionActionResponse.builder()
                .action("DELETED")
                .reaction(null)
                .message("Reaction deleted successfully")
                .build();
    }
}
