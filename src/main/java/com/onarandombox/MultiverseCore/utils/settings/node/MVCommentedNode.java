package com.onarandombox.MultiverseCore.utils.settings.node;

import java.util.ArrayList;
import java.util.List;

import io.github.townyadvanced.commentedconfiguration.setting.CommentedNode;
import org.jetbrains.annotations.NotNull;

public class MVCommentedNode implements CommentedNode {

    public static <T> Builder<Builder> builder(String path) {
        return new Builder<>(path);
    }

    protected final String path;
    protected final String[] comments;

    protected MVCommentedNode(String path, String[] comments) {
        this.path = path;
        this.comments = comments;
    }

    @Override
    public @NotNull String getPath() {
        return path;
    }

    @Override
    public @NotNull String[] getComments() {
        return comments;
    }

    public static class Builder<B extends Builder> {
        protected final String path;
        protected final List<String> comments;

        protected Builder(String path) {
            this.path = path;
            this.comments = new ArrayList<>();
        }

        public B comment(@NotNull String comment) {
            if (!comment.isEmpty() && !comment.trim().startsWith("#")) {
                // Automatically add a comment prefix if the comment doesn't start with one.
                comment = "# " + comment;
            }
            this.comments.add(comment);
            return (B) this;
        }

        public MVCommentedNode build() {
            return new MVCommentedNode(path, comments.toArray(new String[0]));
        }
    }
}
