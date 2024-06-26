package uz.smartup.academy.bloggingplatform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.smartup.academy.bloggingplatform.dao.LikeDAO;
import uz.smartup.academy.bloggingplatform.dao.PostDao;
import uz.smartup.academy.bloggingplatform.dto.LikeDTO;
import uz.smartup.academy.bloggingplatform.dto.LikeDTOUtil;
import uz.smartup.academy.bloggingplatform.entity.Like;
import uz.smartup.academy.bloggingplatform.entity.Post;
import uz.smartup.academy.bloggingplatform.entity.User;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeDAO likeDAO;
    private final PostDao postDao;
    private final LikeDTOUtil likeDTOUtil;
    private final UserService userService;


    @Autowired
    public LikeServiceImpl(LikeDAO likeDAO, PostDao postDao, LikeDTOUtil likeDTOUtil, UserService userService) {
        this.likeDAO = likeDAO;
        this.postDao = postDao;
        this.likeDTOUtil = likeDTOUtil;
        this.userService = userService;

    }


    @Override
    public LikeDTO findByUserAndPost(int userId, int postId) {
        return likeDTOUtil.toDto(likeDAO.findByUserAndPost(userId, postId));
    }

    @Override
    public void addLike(int userId, int postId) {

        User user = userRepository.findById(userId);

        Post post = postDao.getById(postId);

        if (likeDAO.findByUserAndPost(userId, postId)==null) {
            throw new IllegalStateException("Post already liked");
        }

        Like like = new Like();
        like.setAuthor(user);
        like.setPost(post);
        likeDAO.save(like);

    }

    @Override
    public void removeLike(int userId, int postId) {

        User user = userRepository.findById(userId);

        Post post = postDao.getById(postId);

        Like like = likeDAO.findByUserAndPost(userId, postId);

        likeDAO.delete(like);
    }
}