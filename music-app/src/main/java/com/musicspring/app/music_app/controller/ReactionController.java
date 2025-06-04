package com.musicspring.app.music_app.controller;

import com.musicspring.app.music_app.exception.ErrorDetails;
import com.musicspring.app.music_app.model.dto.request.ReactionRequest;
import com.musicspring.app.music_app.model.dto.response.ReactionResponse;
import com.musicspring.app.music_app.model.enums.ReactedType;
import com.musicspring.app.music_app.model.enums.ReactionType;
import com.musicspring.app.music_app.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Reactions", description = "Endpoints related to reactions on reviews and comments")
public class ReactionController {

    private final ReactionService reactionService;

    @Autowired
    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @Operation(
            summary = "Get all reactions (paginated)",
            description = "Returns a paginated list of all reactions."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reactions retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @GetMapping("/reactions")
    public ResponseEntity<Page<ReactionResponse>> getAllReactions(
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int pageNumber,
            @Parameter(description = "Field to sort by", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sort) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        Page<ReactionResponse> reactions = reactionService.findAll(pageable);
        return ResponseEntity.ok(reactions);
    }

    @Operation(
            summary = "Get a reaction by ID",
            description = "Retrieves a reaction by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reaction found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReactionResponse.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Reaction not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @GetMapping("/reactions/{id}")
    public ResponseEntity<ReactionResponse> getReactionById(
            @Parameter(description = "Reaction ID", example = "1")
            @PathVariable Long id) {
        ReactionResponse reaction = reactionService.findById(id);
        return ResponseEntity.ok(reaction);
    }

    @Operation(
            summary = "Get reactions for a specific review (paginated)",
            description = "Returns all reactions associated with the specified review."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reactions retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Review not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @GetMapping("/reviews/{reviewId}/reactions")
    public ResponseEntity<Page<ReactionResponse>> getReactionsByReview(
            @Parameter(description = "Review ID", example = "10")
            @PathVariable Long reviewId,
            @Parameter(description = "Pagination information", hidden = true)
            Pageable pageable) {
        Page<ReactionResponse> reactions = reactionService.findByReviewId(reviewId, pageable);
        return ResponseEntity.ok(reactions);
    }

    @Operation(
            summary = "Get reactions by comment ID",
            description = "Returns a paginated list of all reactions on a specific comment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reactions retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid comment ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/comments/{commentId}/reactions")
    public ResponseEntity<Page<ReactionResponse>> getReactionsByComment(
            @Parameter(description = "Comment ID", example = "15")
            @PathVariable Long commentId,
            Pageable pageable
    ) {
        Page<ReactionResponse> reactions = reactionService.findByCommentId(commentId, pageable);
        return ResponseEntity.ok(reactions);
    }


    @Operation(
            summary = "Get reactions filtered by type and target",
            description = "Returns a paginated list of reactions filtered by reaction type (like, love, etc.) and reacted target (review or comment)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reactions retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/reactions/filter")
    public ResponseEntity<Page<ReactionResponse>> getReactionsByTypeAndTarget(
            @Parameter(description = "Reaction type", example = "LIKE")
            @RequestParam ReactionType reactionType,
            @Parameter(description = "Reacted type (REVIEW or COMMENT)", example = "REVIEW")
            @RequestParam ReactedType reactedType,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Page number to retrieve (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int pageNumber,
            @Parameter(description = "Field to sort by", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sort
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort));
        Page<ReactionResponse> reactions = reactionService.findReactionsByTypeAndTarget(reactionType, reactedType, pageable);
        return ResponseEntity.ok(reactions);
    }

    @Operation(
            summary = "Get reactions by user ID",
            description = "Returns a paginated list of all reactions made by a specific user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reactions retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid user ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    })
    @GetMapping("/users/{userId}/reactions")
    public ResponseEntity<Page<ReactionResponse>> getReactionsByUserId(
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long userId,
            Pageable pageable
    ) {
        Page<ReactionResponse> reactions = reactionService.findReactionsByUserId(userId, pageable);
        return ResponseEntity.ok(reactions);
    }

    @Operation(
            summary = "Create a reaction to a review",
            description = "Creates a new reaction associated to the specified review."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reaction processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReactionResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid reaction data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Review not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @PostMapping("/reviews/{reviewId}/reactions")
    public ResponseEntity<?> createReactionToReview(
            @Parameter(description = "Review ID", example = "10")
            @PathVariable Long reviewId,
            @Parameter(description = "Reaction request data")
            @RequestBody ReactionRequest request) {

        request.setReactedType(ReactedType.REVIEW);
        ReactionResponse response = reactionService.createReaction(request, reviewId);
        // Help me Edu
        return response != null 
            ? ResponseEntity.ok(response)
            : ResponseEntity.ok().build(); // Return 200 OK with empty body for deletions
    }

    @Operation(
            summary = "Create a reaction to a comment",
            description = "Creates a new reaction associated to the specified comment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reaction processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReactionResponse.class))
            ),
            @ApiResponse(responseCode = "400",
                    description = "Invalid reaction data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Comment not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @PostMapping("/comments/{commentId}/reactions")
    public ResponseEntity<?> createReactionToComment(
            @Parameter(description = "Comment ID", example = "15")
            @PathVariable Long commentId,
            @Parameter(description = "Reaction request data")
            @RequestBody ReactionRequest request) {
        // Help me Edu
        request.setReactedType(ReactedType.COMMENT);
        ReactionResponse response = reactionService.createReaction(request, commentId);
        
        return response != null 
            ? ResponseEntity.ok(response)
            : ResponseEntity.ok().build(); // Return 200 OK with empty body for deletions
    }

    @Operation(
            summary = "Update a reaction to a review",
            description = "Updates the reaction type of a reaction by user on a specific review."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reaction updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReactionResponse.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Reaction not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @PutMapping("/reviews/{reviewId}/reactions/users/{userId}")
    public ResponseEntity<ReactionResponse> updateReactionToReview(
            @Parameter(description = "Review ID", example = "10")
            @PathVariable Long reviewId,
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "New reaction type", example = "LIKE")
            @RequestParam ReactionType newReactionType) {

        ReactionResponse updated = reactionService.updateReactionType(userId, reviewId, ReactedType.REVIEW, newReactionType);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Update a reaction to a comment",
            description = "Updates the reaction type of a reaction by user on a specific comment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Reaction updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReactionResponse.class))
            ),
            @ApiResponse(responseCode = "404",
                    description = "Reaction not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @PutMapping("/comments/{commentId}/reactions/users/{userId}")
    public ResponseEntity<ReactionResponse> updateReactionToComment(
            @Parameter(description = "Comment ID", example = "15")
            @PathVariable Long commentId,
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "New reaction type", example = "DISLIKE")
            @RequestParam ReactionType newReactionType) {

        ReactionResponse updated = reactionService.updateReactionType(userId, commentId, ReactedType.COMMENT, newReactionType);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete a reaction to a review",
            description = "Deletes a reaction made by a user on a specific review."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Reaction deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Reaction not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @DeleteMapping("/reviews/{reviewId}/reactions/users/{userId}")
    public ResponseEntity<Void> deleteReactionToReview(
            @Parameter(description = "Review ID", example = "10")
            @PathVariable Long reviewId,
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long userId) {

        reactionService.deleteReactionToReview(userId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Delete a reaction to a comment",
            description = "Deletes a reaction made by a user on a specific comment."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Reaction deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Reaction not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            ),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetails.class))
            )
    })
    @DeleteMapping("/comments/{commentId}/reactions/users/{userId}")
    public ResponseEntity<Void> deleteReactionToComment(
            @Parameter(description = "Comment ID", example = "15")
            @PathVariable Long commentId,
            @Parameter(description = "User ID", example = "1")
            @PathVariable Long userId) {

        reactionService.deleteReactionToComment(userId, commentId);
        return ResponseEntity.noContent().build();
    }
}
