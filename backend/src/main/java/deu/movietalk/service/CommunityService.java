package deu.movietalk.service;

import deu.movietalk.domain.CommunityCategory;
import deu.movietalk.domain.CommunityComment;
import deu.movietalk.domain.CommunityPost;
import deu.movietalk.domain.Member;
import deu.movietalk.dto.*;
import deu.movietalk.repository.CommunityCategoryRepository;
import deu.movietalk.repository.CommunityCommentRepository;
import deu.movietalk.repository.CommunityPostRepository;
import deu.movietalk.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityService {

    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;
    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityCategoryRepository communityCategoryRepository;
    @Autowired
    public CommunityService(CommunityPostRepository boardRepository, MemberRepository memberRepository, CommunityCommentRepository communityCommentRepository, CommunityCategoryRepository communityCategoryRepository) {
        this.communityPostRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.communityCommentRepository = communityCommentRepository;
        this.communityCategoryRepository = communityCategoryRepository;
    }
    //게시글 조회(페이징)
    public Page<CommunitySummaryDto> getPostSummaries(Pageable pageable) {
        Page<CommunityPost> postPage = communityPostRepository.findAll(pageable);

        if (pageable.getPageNumber() >= postPage.getTotalPages() && postPage.getTotalPages() > 0) {
            throw new IllegalArgumentException("요청한 페이지 번호가 총 페이지 수를 초과했습니다.");
        }

        return postPage.map(post -> new CommunitySummaryDto(
                post.getPostId(),
                post.getTitle(),
                post.getMember().getNickname(),
                post.getCreatedAt().toString()
        ));
    }
    public Page<CommunitySummaryDto> getPostsByCategory(Long categoryId, Pageable pageable) {
        Page<CommunityPost> postPage = communityPostRepository.findByCommunityCategory_CategoryId(categoryId, pageable);

        return postPage.map(post -> new CommunitySummaryDto(
                post.getPostId(),
                post.getTitle(),
                post.getMember().getNickname(),
                post.getCreatedAt().toString()
        ));
    }

    //게시글 등록
    public CommunityDto createPost(CommunityDto communityDto, String memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("로그인한 회원 정보가 없습니다."));


        CommunityCategory category = communityCategoryRepository.findById(communityDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리 정보 없음"));

        CommunityPost post = CommunityPost.builder()
                .title(communityDto.getTitle())
                .content(communityDto.getContent())
                .member(member)
                .communityCategory(category)
                .createdAt(LocalDateTime.now())
                .build();

        CommunityPost saved = communityPostRepository.save(post);
        return new CommunityDto(saved.getTitle(), saved.getContent(),saved.getCommunityCategory().getCategoryId());
    }
    public Page<CommunitySummaryDto> searchPosts(String keyword, Pageable pageable) {
        Page<CommunityPost> postPage = communityPostRepository
                .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);

        if (pageable.getPageNumber() >= postPage.getTotalPages() && postPage.getTotalPages() > 0) {
            throw new IllegalArgumentException("요청한 페이지 번호가 총 페이지 수를 초과했습니다.");
        }

        return postPage.map(post -> new CommunitySummaryDto(
                post.getPostId(),
                post.getTitle(),
                post.getMember().getNickname(),
                post.getCreatedAt().toString()
        ));
    }
    //댓글 등록
    public void addComment(Long postId, String memberId, CommentCreateDto dto) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));

        CommunityComment comment = CommunityComment.builder()
                .post(post)
                .member(member)
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        communityCommentRepository.save(comment);
    }
    //상세조회
    public CommunityDetailDto getPostWithComments(Long postId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        List<CommentDto> commentDtos = post.getComments().stream()
                .map(comment -> new CommentDto(
                        comment.getCommentId(),
                        comment.getContent(),
                        comment.getMember().getNickname(),
                        comment.getCreatedAt().toString()
                ))
                .toList();

        return new CommunityDetailDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getNickname(),
                post.getCreatedAt().toString(),
                commentDtos
        );
    }

    //게시글 삭제
    public void deletePost(Long postId, String memberId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getMember().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 삭제할 수 있습니다.");
        }

        communityPostRepository.delete(post);
    }

    //댓글 삭제
    public void deleteComment(Long commentId, String memberId) {
        CommunityComment comment = communityCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getMember().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        communityCommentRepository.delete(comment);
    }

    // 게시글 수정
    public void updatePost(Long postId, String memberId, CommunityUpdateDto dto) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (!post.getMember().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("본인이 작성한 게시글만 수정할 수 있습니다.");
        }
        if (dto.getCategoryId() != null) {
            CommunityCategory category = communityCategoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("유효하지 않은 카테고리"));
            post.setCommunityCategory(category);
        }
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        communityPostRepository.save(post);
    }

    // 댓글 수정
    public void updateComment(Long commentId, String memberId, CommentUpdateDto dto) {
        CommunityComment comment = communityCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (!comment.getMember().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(dto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        communityCommentRepository.save(comment);
    }
}

