/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tumblr.jumblr;

import com.tumblr.jumblr.responses.ResponseWrapper;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.Resource;
import com.tumblr.jumblr.types.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jc
 */
public class MockResponseWrapper extends ResponseWrapper {

    @Override
    public List<Post> getPosts() {
        return new ArrayList<Post>();
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<User>();
    }

    @Override
    public List<Post> getTaggedPosts() {
        return new ArrayList<Post>();
    }

    @Override
    public List<Post> getLikedPosts() {
        return new ArrayList<Post>();
    }

    @Override
    public List<Blog> getBlogs() {
        return new ArrayList<Blog>();
    }

    @Override
    public Post getPost() {
        return null;
    }

    @Override
    public Blog getBlog() {
        return null;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public Long getId() {
        return 42L;
    }

}
