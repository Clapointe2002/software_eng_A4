package org.example;

import java.util.ArrayList;
import java.util.List;

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
        if (isValidTitle(title) && isValidBody(body, difficulty) && isValidTags(tags, difficulty) && isValidUrgency(difficulty, urgency)) {
            this.title = title;
            this.body = body;
            this.tags = tags;
            this.difficulty = difficulty;
            this.urgency = urgency;
            return true;
        }
        return false;
    }

    private boolean isValidTitle(String title) {
        return title.length() >= 10 && title.length() <= 250 && !Character.isDigit(title.charAt(0));
    }

    private boolean isValidBody(String body, String difficulty) {
        if (difficulty.equals("Very Difficult") || difficulty.equals("Difficult")) {
            return body.length() >= 300;
        }
        return body.length() >= 250;
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
        return !(difficulty.equals("Easy") && tags.length > 3);
    }

    private boolean isValidUrgency(String difficulty, String urgency) {
        if ((difficulty.equals("Easy") && (urgency.equals("Immediately Needed") || urgency.equals("Highly Needed"))) ||
                ((difficulty.equals("Very Difficult") || difficulty.equals("Difficult")) && urgency.equals("Ordinary"))) {
            return false;
        }
        return true;
    }

    public boolean addComment(String comment) {
        if (isValidComment(comment) && comments.size() < getMaxComments()) {
            comments.add(comment);
            return true;
        }
        return false;
    }

    private boolean isValidComment(String comment) {
        String[] words = comment.split(" ");
        return words.length >= 4 && words.length <= 10 && Character.isUpperCase(words[0].charAt(0));
    }

    private int getMaxComments() {
        if (urgency == null) {
            return 0;
        }
        if (urgency.equals("Ordinary")) {
            return 3;
        }
        return 5;
    }
}
