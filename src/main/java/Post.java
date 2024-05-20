package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

import java.nio.file.Paths;

import java.io.File;

public class Post {
    private String title;
    private String body;
    private String[] tags;
    private String difficulty;
    private String urgency;
    private List<String> comments;

    public Post() {
        this.comments = new ArrayList<>();
    }

    public boolean addPost(String title, String body, String[] tags, String difficulty, String urgency) {
        System.out.println(isValidBody(body, difficulty));
        System.out.println(isValidTags(tags, difficulty));
        System.out.println(isValidUrgency(urgency, difficulty));
        System.out.println(isValidTitle(title));
        if (isValidTitle(title) && isValidBody(body, difficulty) && isValidTags(tags, difficulty) && isValidUrgency(urgency, difficulty)) {
            this.title = title;
            this.body = body;
            this.tags = tags;
            this.difficulty = difficulty;
            this.urgency = urgency;
            return writeToFile("post.txt", formatPost());
        }
        return false;
    }

    private boolean isValidTitle(String title) {
        return title.length() >= 10 && title.length() <= 250 && !title.substring(0, 5).matches("[0-9@#$%^&*!]+");
    }

    private boolean isValidBody(String body, String difficulty) {
        System.out.println(body.length());
        System.out.println(difficulty);
        int minLength = difficulty.equalsIgnoreCase("Very Difficult") || difficulty.equalsIgnoreCase("Difficult") ? 300 : 250;
        return body.length() >= minLength;
    }

    private boolean isValidTags(String[] tags, String difficulty) {
        if (tags.length < 2 || tags.length > 5) {
            return false;
        }
        for (String tag : tags) {
            if (tag.length() < 2 || tag.length() > 10 || !tag.equals(tag.toLowerCase())) {
                return false;
            }
        }
        return !(difficulty.equalsIgnoreCase("Easy") && tags.length > 3);
    }

    private boolean isValidUrgency(String urgency, String difficulty) {
        return !(difficulty.equalsIgnoreCase("Easy") && (urgency.equalsIgnoreCase("Immediately Needed") || urgency.equalsIgnoreCase("Highly Needed"))) &&
                !((difficulty.equalsIgnoreCase("Very Difficult") || difficulty.equalsIgnoreCase("Difficult")) && urgency.equalsIgnoreCase("Ordinary"));
    }

    public boolean addComment(String comment) {
        System.out.println("Adding comment: " + comment
                + "\nComments count: " + comments.size()
                + "\nMax comments: " + getMaxComments());
        if (isValidComment(comment)){
            comments.add(comment);
            return writeToFile("comment.txt", formatComment(comment));
        }
        return false;
    }

    private boolean isValidComment(String comment) {
        String[] words = comment.split("\\s+");
        return words.length >= 4 && words.length <= 10 && Character.isUpperCase(words[0].charAt(0));
    }

    private int getMaxComments() {
        if (urgency == null) {
            // If urgency isn't set, assume maximum restriction or return an error/exception.
            System.out.println("Urgency not set, assuming maximum comment restrictions apply.");
            return 0; // You can return 0 or handle it differently based on your application logic.
        }
        return urgency.equals("Ordinary") ? 3 : 5;
    }


    private boolean writeToFile(String fileName, String content) {
        String filePath = System.getProperty("user.dir") + "/" + fileName;  // Write directly in the project root
        System.out.println("Attempting to write to the file: " + filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(content);
            writer.newLine();
            System.out.println("Successfully wrote to the file: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Failed to write to the file: " + filePath);
            e.printStackTrace();
            return false;
        }
    }




    private String formatPost() {
        return String.format("Title: %s\nBody: %s\nTags: %s\nDifficulty: %s\nUrgency: %s", title, body, String.join(", ", tags), difficulty, urgency);
    }

    private String formatComment(String comment) {
        return String.format("Comment: %s", comment);
    }
}
