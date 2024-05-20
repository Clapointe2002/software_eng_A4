package org.example;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.example.Post;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {

    private Post post;

    @BeforeEach
    public void setUp() {
        post = new Post();
        cleanUpFiles();
    }

    @AfterEach
    public void tearDown() {
        cleanUpFiles();
    }

    private void cleanUpFiles() {
        new File("src/main/java/org/example/post.txt").delete();
        new File("src/main/java/org/example/comment.txt").delete();
    }

    @Test
    public void testAddPost_ValidPost() {
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("ValidTitle123", "This is a valid body with more than 300 characters...".repeat(6), tags, "Difficult", "Highly Needed");
        assertTrue(result);
    }

    @Test
    public void testAddPost_InvalidTitle() {
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("Short", "This is a valid body with more than 250 characters...".repeat(2), tags, "Difficult", "Highly Needed");
        assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidBody() {
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("ValidTitle123", "Short body", tags, "Difficult", "Highly Needed");
        assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidTags() {
        String[] tags = {"tag1", "tag2", "tag3", "tag4", "tag5", "tag6"};
        boolean result = post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(2), tags, "Difficult", "Highly Needed");
        assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidUrgency() {
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(2), tags, "Easy", "Immediately Needed");
        assertFalse(result);
    }

    @Test
    public void testAddPost_InvalidCharacters() {
        String[] tags = {"tag1", "tag2"};
        boolean result = post.addPost("12345Invalid", "This is a valid body with more than 250 characters...".repeat(2), tags, "Difficult", "Highly Needed");
        assertFalse(result);
    }

    @Test
    public void testAddComment_ValidComment() {
        boolean result = post.addComment("This is valid comment ahah");
        assertTrue(result);
    }

    @Test
    public void testAddComment_InvalidCommentShort() {
        boolean result = post.addComment("Too short");
        assertFalse(result);
    }

    @Test
    public void testAddComment_InvalidCommentLong() {
        boolean result = post.addComment("This comment has way too many words and should be invalid.");
        assertFalse(result);
    }

    @Test
    public void testAddComment_InvalidCommentNoCapital() {
        boolean result = post.addComment("this is not valid.");
        assertFalse(result);
    }

    @Test
    public void testAddComment_ExceedingMaxCommentsEasyPost() {
        String[] tags = {"tag1", "tag2", "tag3"};
        post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(2), tags, "Easy", "Ordinary");
        post.addComment("This is a valid comment.");
        post.addComment("Another valid comment.");
        post.addComment("Yet another valid comment.");
        boolean result = post.addComment("This should fail.");
        assertFalse(result);
    }

    @Test
    public void testAddComment_ExceedingMaxCommentsOrdinaryPost() {
        String[] tags = {"tag1", "tag2"};
        post.addPost("ValidTitle123", "This is a valid body with more than 250 characters...".repeat(2), tags, "Difficult", "Ordinary");
        for (int i = 0; i < 5; i++) {
            post.addComment("Valid comment " + (i + 1));
        }
        boolean result = post.addComment("This should fail.");
        assertFalse(result);
    }
}
