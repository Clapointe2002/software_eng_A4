package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.example.Post;

public class PostTest {

    @Test
    public void testAddPost_ValidPost() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(10), tags, "Difficult", "Highly Needed");
        Assertions.assertTrue(result);
    }

    @Test
    public void testAddPost_InvalidTitle() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("Short", "This is a valid body with more than 250 characters...".repeat(10), tags, "Difficult", "Highly Needed");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidBody() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("ValidTitle123", "Short body", tags, "Difficult", "Highly Needed");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidTags() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2", "tag3", "tag4", "tag5", "tag6"};
        boolean result = post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(10), tags, "Difficult", "Highly Needed");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidUrgency() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(10), tags, "Easy", "Immediately Needed");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidTitleSpecialChars() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("12345Invalid", "This is a valid body with more than 250 characters...".repeat(10), tags, "Difficult", "Highly Needed");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidTagsUpperCase() {
        Post post = new Post();
        String[] tags = {"Tag1", "tag2"};
        boolean result = post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(10), tags, "Difficult", "Highly Needed");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidBodyLengthForDifficulty() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("ValidTitle123", "This body has 250 characters but not 300 which is needed for Difficult and Very Difficult difficulty levels.".repeat(5), tags, "Difficult", "Highly Needed");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddComment_ValidComment() {
        Post post = new Post();
        post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(10), new String[]{"tag1", "tag2"}, "Difficult", "Highly Needed");
        boolean result = post.addComment("This is valid.");
        Assertions.assertTrue(result);
    }

    @Test
    public void testAddComment_InvalidCommentLessWords() {
        Post post = new Post();
        boolean result = post.addComment("Too short");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddComment_InvalidCommentMoreWords() {
        Post post = new Post();
        boolean result = post.addComment("This comment has way too many words and should be invalid.");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddComment_InvalidCommentNotCapitalized() {
        Post post = new Post();
        boolean result = post.addComment("this is not valid.");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddComment_ExceedMaxCommentsEasyPost() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2"};
        post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(10), tags, "Easy", "Ordinary");
        post.addComment("Valid comment one.");
        post.addComment("Valid comment two.");
        post.addComment("Valid comment three.");
        boolean result = post.addComment("Fourth comment not allowed.");
        Assertions.assertFalse(result);
    }

    @Test
    public void testAddComment_ExceedMaxCommentsOrdinaryPost() {
        Post post = new Post();
        String[] tags = {"tag1", "tag2"};
        post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(10), tags, "Difficult", "Ordinary");
        post.addComment("Valid comment one.");
        post.addComment("Valid comment two.");
        post.addComment("Valid comment three.");
        post.addComment("Valid comment four.");
        post.addComment("Valid comment five.");
        boolean result = post.addComment("Sixth comment not allowed.");
        Assertions.assertFalse(result);
    }
}
