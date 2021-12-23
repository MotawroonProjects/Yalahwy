package com.yalahwy.mvp.fragment_comments_mvp;

import com.yalahwy.models.CommentModel;

public interface FragmentCommentView {
    void onSuccess(CommentModel commentModel);
    void onFailed(String msg);


}
